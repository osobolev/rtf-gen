/*
 * $Id: RtfColor.java 3580 2008-08-06 15:52:00Z howard_s $
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
package com.lowagie.text.rtf.style;

import com.lowagie.text.DocWriter;
import com.lowagie.text.rtf.RtfElement;
import com.lowagie.text.rtf.RtfExtendedElement;
import com.lowagie.text.rtf.document.RtfDocument;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

/**
 * The RtfColor stores one rtf color value for a rtf document
 *
 * @author Mark Hall (Mark.Hall@mail.room3b.eu)
 * @author Thomas Bickel (tmb99@inode.at)
 * @version $Id: RtfColor.java 3580 2008-08-06 15:52:00Z howard_s $
 */
public class RtfColor extends RtfElement implements RtfExtendedElement {

    /**
     * Constant for RED value
     */
    private static final byte[] COLOR_RED = DocWriter.getISOBytes("\\red");
    /**
     * Constant for GREEN value
     */
    private static final byte[] COLOR_GREEN = DocWriter.getISOBytes("\\green");
    /**
     * Constant for BLUE value
     */
    private static final byte[] COLOR_BLUE = DocWriter.getISOBytes("\\blue");
    /**
     * Constant for the end of one color entry
     */
    private static final byte COLON = (byte) ';';
    /**
     * Constant for the number of the color in the list of colors
     */
    private static final byte[] COLOR_NUMBER = DocWriter.getISOBytes("\\cf");

    /**
     * The number of the color in the list of colors
     */
    private int colorNumber = 0;
    /**
     * The red value
     */
    private final int red;
    /**
     * The green value
     */
    private final int green;
    /**
     * The blue value
     */
    private final int blue;

    /**
     * Constructor only for use when initializing the RtfColorList
     *
     * @param doc         The RtfDocument this RtfColor belongs to
     * @param red         The red value to use
     * @param green       The green value to use
     * @param blue        The blue value to use
     * @param colorNumber The number of the color in the color list
     */
    protected RtfColor(RtfDocument doc, int red, int green, int blue, int colorNumber) {
        super(doc);
        this.red = red;
        this.blue = blue;
        this.green = green;
        this.colorNumber = colorNumber;
    }

    /**
     * Constructs a RtfColor as a clone of an existing RtfColor
     *
     * @param doc The RtfDocument this RtfColor belongs to
     * @param col The RtfColor to use as a base
     */
    public RtfColor(RtfDocument doc, RtfColor col) {
        super(doc);
        if (col != null) {
            this.red = col.getRed();
            this.green = col.getGreen();
            this.blue = col.getBlue();
        } else {
            this.red = 0;
            this.green = 0;
            this.blue = 0;
        }
        if (this.document != null) {
            this.colorNumber = this.document.getDocumentHeader().getColorNumber(this);
        }
    }

    /**
     * Constructs a RtfColor based on the Color
     *
     * @param doc The RtfDocument this RtfColor belongs to
     * @param col The Color to base this RtfColor on
     */
    public RtfColor(RtfDocument doc, Color col) {
        super(doc);
        if (col != null) {
            this.red = col.getRed();
            this.green = col.getGreen();
            this.blue = col.getBlue();
        } else {
            this.red = 0;
            this.green = 0;
            this.blue = 0;
        }
        if (this.document != null) {
            this.colorNumber = this.document.getDocumentHeader().getColorNumber(this);
        }
    }

    /**
     * Constructs a RtfColor based on the red/green/blue values
     *
     * @param doc   The RtfDocument this RtfColor belongs to
     * @param red   The red value to use
     * @param green The green value to use
     * @param blue  The blue value to use
     */
    public RtfColor(RtfDocument doc, int red, int green, int blue) {
        super(doc);
        this.red = red;
        this.blue = blue;
        this.green = green;
        if (this.document != null) {
            this.colorNumber = this.document.getDocumentHeader().getColorNumber(this);
        }
    }

    /**
     * unused
     */
    @Override
    public void writeContent(OutputStream out) {
    }

    /**
     * Write the definition part of this RtfColor.
     */
    @Override
    public void writeDefinition(OutputStream result) throws IOException {
        result.write(COLOR_RED);
        result.write(intToByteArray(red));
        result.write(COLOR_GREEN);
        result.write(intToByteArray(green));
        result.write(COLOR_BLUE);
        result.write(intToByteArray(blue));
        result.write(COLON);
    }

    /**
     * Writes the beginning of this RtfColor
     */
    public void writeBegin(OutputStream result) throws IOException {
        result.write(COLOR_NUMBER);
        result.write(intToByteArray(colorNumber));
    }

    /**
     * Unused
     *
     * @param result The <code>OutputStream</code> to which nothing will be written
     */
    public void writeEnd(OutputStream result) {
    }

    /**
     * Tests if this RtfColor is equal to another RtfColor.
     *
     * @param obj another RtfColor
     * @return <code>True</code> if red, green and blue values of the two colors match,
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RtfColor)) {
            return false;
        }
        RtfColor color = (RtfColor) obj;
        return this.red == color.getRed() && this.green == color.getGreen() && this.blue == color.getBlue();
    }

    /**
     * Returns the hash code of this RtfColor. The hash code is
     * an integer with the lowest three bytes containing the values
     * of red, green and blue.
     *
     * @return The hash code of this RtfColor
     */
    @Override
    public int hashCode() {
        return (this.red << 16) | (this.green << 8) | this.blue;
    }

    /**
     * Get the blue value of this RtfColor
     *
     * @return The blue value
     */
    public int getBlue() {
        return blue;
    }

    /**
     * Get the green value of this RtfColor
     *
     * @return The green value
     */
    public int getGreen() {
        return green;
    }

    /**
     * Get the red value of this RtfColor
     *
     * @return The red value
     */
    public int getRed() {
        return red;
    }

    /**
     * Gets the number of this RtfColor in the list of colors
     *
     * @return Returns the colorNumber.
     */
    public int getColorNumber() {
        return colorNumber;
    }

    /**
     * Sets the RtfDocument this RtfColor belongs to
     *
     * @param doc The RtfDocument to use
     */
    @Override
    public void setRtfDocument(RtfDocument doc) {
        super.setRtfDocument(doc);
        if (document != null) {
            this.colorNumber = document.getDocumentHeader().getColorNumber(this);
        }
    }
}
