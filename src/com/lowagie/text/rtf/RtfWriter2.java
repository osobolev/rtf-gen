/*
 * $Id: RtfWriter2.java 3583 2008-08-12 00:00:09Z xlv $
 *
 * Copyright 2001, 2002, 2003, 2004 by Mark Hall
 *
 * The contents of this file are subject to the Mozilla Public License Version 1.1
 * (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the License.
 *
 * The Original Code is 'iText, a free JAVA-PDF library'.
 *
 * The Initial Developer of the Original Code is Bruno Lowagie. Portions created by
 * the Initial Developer are Copyright (C) 1999, 2000, 2001, 2002 by Bruno Lowagie.
 * All Rights Reserved.
 * Co-Developer of the code is Paulo Soares. Portions created by the Co-Developer
 * are Copyright (C) 2000, 2001, 2002 by Paulo Soares. All Rights Reserved.
 *
 * Contributor(s): all the names of the contributors are added in the source code
 * where applicable.
 *
 * Alternatively, the contents of this file may be used under the terms of the
 * LGPL license (the ?GNU LIBRARY GENERAL PUBLIC LICENSE?), in which case the
 * provisions of LGPL are applicable instead of those above.  If you wish to
 * allow use of your version of this file only under the terms of the LGPL
 * License and not to allow others to use your version of this file under
 * the MPL, indicate your decision by deleting the provisions above and
 * replace them with the notice and other provisions required by the LGPL.
 * If you do not delete the provisions above, a recipient may use your version
 * of this file under either the MPL or the GNU LIBRARY GENERAL PUBLIC LICENSE.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the MPL as stated above or under the terms of the GNU
 * Library General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library general Public License for more
 * details.
 *
 * If you didn't download this code from the following link, you should check if
 * you aren't using an obsolete version:
 * https://github.com/osobolev/rtf-gen
 */
package com.lowagie.text.rtf;

import com.lowagie.text.*;
import com.lowagie.text.rtf.document.RtfDocument;
import com.lowagie.text.rtf.document.RtfDocumentSettings;
import com.lowagie.text.rtf.text.RtfNewPage;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The RtfWriter allows the creation of rtf documents via the iText system
 * <p>
 * Version: $Id: RtfWriter2.java 3583 2008-08-12 00:00:09Z xlv $
 *
 * @author Mark Hall (Mark.Hall@mail.room3b.eu)
 */
public class RtfWriter2 extends DocWriter {

    /**
     * The RtfDocument this RtfWriter is creating
     */
    private RtfDocument rtfDoc;

    /**
     * Constructs a new RtfWriter that listens to the specified Document and
     * writes its output to the OutputStream.
     *
     * @param doc The Document that this RtfWriter listens to
     * @param os  The OutputStream to write to
     */
    protected RtfWriter2(Document doc, OutputStream os) {
        super(doc, os);
        doc.addDocListener(this);
        rtfDoc = new RtfDocument();
    }

    /**
     * Static method to generate RtfWriters
     *
     * @param doc The Document that this RtfWriter listens to
     * @param os  The OutputStream to write to
     * @return The new RtfWriter
     */
    public static RtfWriter2 getInstance(Document doc, OutputStream os) {
        return new RtfWriter2(doc, os);
    }

    /**
     * Sets the header to use
     *
     * @param hf The HeaderFooter to use
     */
    @Override
    public void setHeader(HeaderFooter hf) {
        this.rtfDoc.getDocumentHeader().setHeader(hf);
    }

    /**
     * Resets the header
     */
    @Override
    public void resetHeader() {
        this.rtfDoc.getDocumentHeader().setHeader(null);
    }

    /**
     * Sets the footer to use
     *
     * @param hf The HeaderFooter to use
     */
    @Override
    public void setFooter(HeaderFooter hf) {
        this.rtfDoc.getDocumentHeader().setFooter(hf);
    }

    /**
     * Resets the footer
     */
    @Override
    public void resetFooter() {
        this.rtfDoc.getDocumentHeader().setFooter(null);
    }

    /**
     * This method is not supported in the RtfWriter
     *
     * @param i Unused
     */
    @Override
    public void setPageCount(int i) {
    }

    /**
     * This method is not supported in the RtfWriter
     */
    @Override
    public void resetPageCount() {
    }

    /**
     * This method is not supported in the RtfWriter
     */
    public void clearTextWrap() {
    }

    /**
     * Opens the RtfDocument
     */
    @Override
    public void open() {
        super.open();
        this.rtfDoc.open();
    }

    /**
     * Closes the RtfDocument. This causes the document to be written
     * to the specified OutputStream
     */
    @Override
    public void close() {
        if (open) {
            try {
                rtfDoc.writeDocument(os);
                super.close();
                this.rtfDoc = new RtfDocument();
            } catch (IOException ex) {
                throw new ExceptionConverter(ex);
            }
        }
    }

    /**
     * Adds an Element to the Document
     *
     * @param element The element to be added
     * @return <code>false</code>
     * @throws DocumentException
     */
    @Override
    public boolean add(Element element) throws DocumentException {
        if (pause) {
            return false;
        }
        RtfBasicElement[] rtfElements = rtfDoc.getMapper().mapElement(element);
        if (rtfElements.length != 0) {
            for (RtfBasicElement rtfElement : rtfElements) {
                if (rtfElement != null) {
                    rtfDoc.add(rtfElement);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds a page break
     *
     * @return <code>false</code>
     */
    @Override
    public boolean newPage() {
        rtfDoc.add(new RtfNewPage(rtfDoc));
        return true;
    }

    /**
     * Sets the page margins
     *
     * @param left   The left margin
     * @param right  The right margin
     * @param top    The top margin
     * @param bottom The bottom margin
     * @return <code>false</code>
     */
    @Override
    public boolean setMargins(float left, float right, float top, float bottom) {
        rtfDoc.getDocumentHeader().getPageSetting().setMarginLeft((int) (left * RtfElement.TWIPS_FACTOR));
        rtfDoc.getDocumentHeader().getPageSetting().setMarginRight((int) (right * RtfElement.TWIPS_FACTOR));
        rtfDoc.getDocumentHeader().getPageSetting().setMarginTop((int) (top * RtfElement.TWIPS_FACTOR));
        rtfDoc.getDocumentHeader().getPageSetting().setMarginBottom((int) (bottom * RtfElement.TWIPS_FACTOR));
        return true;
    }

    /**
     * Sets the size of the page
     *
     * @param rect A Rectangle representing the page
     * @return <code>false</code>
     */
    @Override
    public boolean setPageSize(Rectangle rect) {
        rtfDoc.getDocumentHeader().getPageSetting().setPageSize(rect);
        return true;
    }

    /**
     * Whether to automagically generate table of contents entries when
     * adding Chapters or Sections.
     *
     * @param autogenerate Whether to automatically generate TOC entries
     */
    public void setAutogenerateTOCEntries(boolean autogenerate) {
        this.rtfDoc.setAutogenerateTOCEntries(autogenerate);
    }

    /**
     * Gets the RtfDocumentSettings that specify how the rtf document is generated.
     *
     * @return The current RtfDocumentSettings.
     */
    public RtfDocumentSettings getDocumentSettings() {
        return this.rtfDoc.getDocumentSettings();
    }
}
