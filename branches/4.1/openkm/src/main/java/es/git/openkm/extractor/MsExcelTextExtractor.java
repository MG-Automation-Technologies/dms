/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006-2010  Paco Avila & Josep Llort
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package es.git.openkm.extractor;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.apache.jackrabbit.extractor.AbstractTextExtractor;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Text extractor for Microsoft Excel sheets.
 */
public class MsExcelTextExtractor extends AbstractTextExtractor {

    /**
     * Logger instance.
     */
    private static final Logger logger = LoggerFactory.getLogger(MsExcelTextExtractor.class);

    /**
     * Force loading of dependent class.
     */
    static {
        POIFSFileSystem.class.getName();
    }

    /**
     * Creates a new <code>MsExcelTextExtractor</code> instance.
     */
    public MsExcelTextExtractor() {
        super(new String[]{"application/vnd.ms-excel", "application/msexcel"});
    }

    //-------------------------------------------------------< TextExtractor >

    /**
     * {@inheritDoc}
     */
    public Reader extractText(InputStream stream, String type, String encoding) throws IOException {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(stream);
            return new StringReader(new ExcelExtractor(fs).getText());
        } catch (RuntimeException e) {
            logger.warn("Failed to extract Excel text content", e);
            return new StringReader("");
        } finally {
            stream.close();
        }
    }
}
