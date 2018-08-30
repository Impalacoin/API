package com.impalapay.airtel.util.export;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;

import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Add a header and a footer to a PDF Report
 * <p>
 * Copyright (c) Shujaa Solutions Ltd., Mar 21, 2012
 * 
 * @author <a href="mailto:maureenc@shujaa.co.ke">Maureen Jepchumba</a>
 * @version %I%, %G%
 * 
 */

public class PdfUtil extends PdfPageEventHelper {
	
	/** Alternating phrase for the header. */
    private Phrase[] header = new Phrase[2];
    /** Current page number (will be reset for every chapter). */
    private int pagenumber;

    /**
     * Initialize one of the headers.
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
     *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
     */
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
    	//header[0] = new Phrase("CONFIDENTIAL, © Copyright Shujaa Solutions Ltd", smallBold);
    }

    /**
     * Initialize one of the headers, based on the chapter title;
     * reset the page number.
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onChapter(
     *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document, float,
     *      com.itextpdf.text.Paragraph)
     */
    @Override
    public void onChapter(PdfWriter writer, Document document,
            float paragraphPosition, Paragraph title) {
    	header[1] = new Phrase(title.getContent());
        pagenumber = 1;
    }

    /**
     * Increase the page number.
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onStartPage(
     *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
     */
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        pagenumber++;
    }
    
    /**
     * Adds the header and the footer.
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
     *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
     */
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        Rectangle rect = writer.getBoxSize("art");
        switch(writer.getPageNumber() % 1) {
        
        case 0:
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_RIGHT, header[0],
                    rect.getRight(), rect.getTop(), 0);
            break;
        case 1:
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_LEFT, header[1],
                    rect.getLeft(), rect.getTop(), 0);
            break;
        }
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase(String.format("page %d", pagenumber)),
                (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
    }
        
}

/*
** Local Variables:
**   mode: java
**   c-basic-offset: 2
**   tab-width: 2
**   indent-tabs-mode: nil
** End:
**
** ex: set softtabstop=2 tabstop=2 expandtab:
**
*/

