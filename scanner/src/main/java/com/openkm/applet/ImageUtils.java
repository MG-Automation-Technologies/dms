package com.openkm.applet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import uk.co.mmscomputing.imageio.pdf.PDFImageWriter;
import uk.co.mmscomputing.imageio.tiff.TIFFImageWriter;

public class ImageUtils {
	
	/**
	 * PDF Writer
	 */
	public static void writePdf(List<BufferedImage> images, ImageOutputStream ios) throws IOException {
		PDFImageWriter writer = (PDFImageWriter) ImageIO.getImageWritersByFormatName("pdf").next();
		writer.setOutput(ios);
		writer.prepareWriteSequence(null);
		
		for (int i=0; i < images.size(); i++) {
			writer.writeToSequence(images.get(i));
		}
		
		writer.endWriteSequence();
	}
	
	/**
	 * TIFF Writer
	 */
	public static void writeTiff(List<BufferedImage> images, ImageOutputStream ios) throws IOException {
		TIFFImageWriter writer = (TIFFImageWriter) ImageIO.getImageWritersByFormatName("tif").next();
		writer.setOutput(ios);
		writer.prepareWriteSequence(null);
		
		for (int i=0; i < images.size(); i++) {
			writer.writeToSequence(images.get(i), null);
		}
		
		writer.endWriteSequence();
	}
}
