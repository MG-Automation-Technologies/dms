package com.openkm.extractor;

import java.io.BufferedInputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.apache.jackrabbit.extractor.AbstractTextExtractor;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Text extractor for Portable Document Format (PDF).
 */
public class PdfTextExtractor extends AbstractTextExtractor {

    /**
     * Logger instance.
     */
    private static final Logger logger =
        LoggerFactory.getLogger(PdfTextExtractor.class);

    /**
     * Force loading of dependent class.
     */
    static {
        PDFParser.class.getName();
    }

    /**
     * Creates a new <code>PdfTextExtractor</code> instance.
     */
    public PdfTextExtractor() {
        super(new String[]{"application/pdf"});
    }

    //-------------------------------------------------------< TextExtractor >

    /**
     * {@inheritDoc}
     */
    public Reader extractText(InputStream stream,
                              String type,
                              String encoding) throws IOException {
        try {
            PDFParser parser = new PDFParser(new BufferedInputStream(stream));
            try {
                parser.parse();
                PDDocument document = parser.getPDDocument();
                CharArrayWriter writer = new CharArrayWriter();

                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setLineSeparator("\n");
                stripper.writeText(document, writer);

                return new CharArrayReader(writer.toCharArray());
            } finally {
                try {
                    PDDocument doc = parser.getPDDocument();
                    if (doc != null) {
                        doc.close();
                    }
                } catch (IOException e) {
                    // ignore
                }
            }
        } catch (Exception e) {
            // it may happen that PDFParser throws a runtime
            // exception when parsing certain pdf documents
            logger.warn("Failed to extract PDF text content", e);
            return new StringReader("");
        } finally {
            stream.close();
        }
    }
}

