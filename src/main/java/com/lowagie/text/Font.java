/*
 * $Id: Font.java 3678 2009-02-07 14:46:01Z blowagie $
 *
 * Copyright 1999, 2000, 2001, 2002 by Bruno Lowagie.
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
 * LGPL license (the "GNU LIBRARY GENERAL PUBLIC LICENSE"), in which case the
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
package com.lowagie.text;

import com.lowagie.text.html.Markup;

import java.awt.Color;
import java.util.Objects;

/**
 * Contains all the specifications of a font: fontfamily, size, style and color.
 * <p>
 * Example: <BLOCKQUOTE>
 *
 * <PRE>
 * <p>
 * Paragraph p = new Paragraph("This is a paragraph", <STRONG>new
 * Font(Font.HELVETICA, 18, Font.BOLDITALIC, new Color(0, 0, 255)) </STRONG>);
 *
 * </PRE>
 *
 * </BLOCKQUOTE>
 */
public class Font {

    // static membervariables for the different families

    /**
     * a possible value of a font family.
     */
    public static final int COURIER = 0;

    /**
     * a possible value of a font family.
     */
    public static final int HELVETICA = 1;

    /**
     * a possible value of a font family.
     */
    public static final int TIMES_ROMAN = 2;

    /**
     * a possible value of a font family.
     */
    public static final int SYMBOL = 3;

    /**
     * a possible value of a font family.
     */
    public static final int ZAPFDINGBATS = 4;

    // static membervariables for the different styles

    /**
     * this is a possible style.
     */
    public static final int NORMAL = 0;

    /**
     * this is a possible style.
     */
    public static final int BOLD = 1;

    /**
     * this is a possible style.
     */
    public static final int ITALIC = 2;

    /**
     * this is a possible style.
     */
    public static final int UNDERLINE = 4;

    /**
     * this is a possible style.
     */
    public static final int STRIKETHRU = 8;

    /**
     * this is a possible style.
     */
    public static final int BOLDITALIC = BOLD | ITALIC;

    // static membervariables

    /**
     * the value of an undefined attribute.
     */
    public static final int UNDEFINED = -1;

    /**
     * the value of the default size.
     */
    public static final int DEFAULTSIZE = 12;

    // membervariables

    /**
     * the value of the fontfamily.
     */
    private int family;

    /**
     * the value of the fontsize.
     */
    private float size;

    /**
     * the value of the style.
     */
    private int style;

    /**
     * the value of the color.
     */
    private Color color;

    // constructors

    /**
     * Copy constructor of a Font
     *
     * @param other the font that has to be copied
     */
    public Font(Font other) {
        this.family = other.family;
        this.size = other.size;
        this.style = other.style;
        this.color = other.color;
    }

    /**
     * Constructs a Font.
     *
     * @param family the family to which this font belongs
     * @param size   the size of this font
     * @param style  the style of this font
     * @param color  the <CODE>Color</CODE> of this font.
     */
    public Font(int family, float size, int style, Color color) {
        this.family = family;
        this.size = size;
        this.style = style;
        this.color = color;
    }

    /**
     * Constructs a Font.
     *
     * @param family the family to which this font belongs
     * @param size   the size of this font
     * @param style  the style of this font
     */
    public Font(int family, float size, int style) {
        this(family, size, style, null);
    }

    /**
     * Constructs a Font.
     *
     * @param family the family to which this font belongs
     * @param size   the size of this font
     */
    public Font(int family, float size) {
        this(family, size, UNDEFINED, null);
    }

    /**
     * Constructs a Font.
     *
     * @param family the family to which this font belongs
     */
    public Font(int family) {
        this(family, UNDEFINED, UNDEFINED, null);
    }

    /**
     * Constructs a Font.
     */
    public Font() {
        this(UNDEFINED, UNDEFINED, UNDEFINED, null);
    }

    /**
     * Compares this <CODE>Font</CODE> with another
     *
     * @param that the other <CODE>Font</CODE>
     * @return a value
     */
    public boolean same(Font that) {
        if (this == that)
            return true;
        return this.family == that.family &&
               this.size == that.size &&
               this.style == that.style &&
               Objects.equals(this.color, that.color);
    }

    // FAMILY

    /**
     * Gets the family of this font.
     *
     * @return the value of the family
     */
    public int getFamily() {
        return family;
    }

    /**
     * Gets the familyname as a String.
     *
     * @return the familyname
     */
    public String getFamilyname() {
        switch (getFamily()) {
        case COURIER:
            return FontFactory.COURIER;
        case HELVETICA:
            return FontFactory.HELVETICA;
        case TIMES_ROMAN:
            return FontFactory.TIMES_ROMAN;
        case SYMBOL:
            return FontFactory.SYMBOL;
        case ZAPFDINGBATS:
            return FontFactory.ZAPFDINGBATS;
        }
        return "unknown";
    }

    /**
     * Sets the family using a <CODE>String</CODE> ("Courier", "Helvetica",
     * "Times New Roman", "Symbol" or "ZapfDingbats").
     *
     * @param family A <CODE>String</CODE> representing a certain font-family.
     */
    public void setFamily(String family) {
        this.family = getFamilyIndex(family);
    }

    /**
     * Translates a <CODE>String</CODE> -value of a certain family into the
     * index that is used for this family in this class.
     *
     * @param family A <CODE>String</CODE> representing a certain font-family
     * @return the corresponding index
     */
    public static int getFamilyIndex(String family) {
        if (family.equalsIgnoreCase(FontFactory.COURIER)) {
            return COURIER;
        }
        if (family.equalsIgnoreCase(FontFactory.HELVETICA)) {
            return HELVETICA;
        }
        if (family.equalsIgnoreCase(FontFactory.TIMES_ROMAN)) {
            return TIMES_ROMAN;
        }
        if (family.equalsIgnoreCase(FontFactory.SYMBOL)) {
            return SYMBOL;
        }
        if (family.equalsIgnoreCase(FontFactory.ZAPFDINGBATS)) {
            return ZAPFDINGBATS;
        }
        return UNDEFINED;
    }

    // SIZE

    /**
     * Gets the size of this font.
     *
     * @return a size
     */
    public float getSize() {
        return size;
    }

    /**
     * Gets the size that can be used with the calculated <CODE>BaseFont
     * </CODE>.
     *
     * @return the size that can be used with the calculated <CODE>BaseFont
     * </CODE>
     */
    public float getCalculatedSize() {
        float s = this.size;
        if (s == UNDEFINED) {
            s = DEFAULTSIZE;
        }
        return s;
    }

    /**
     * Gets the leading that can be used with this font.
     *
     * @param linespacing a certain linespacing
     * @return the height of a line
     */
    public float getCalculatedLeading(float linespacing) {
        return linespacing * getCalculatedSize();
    }

    /**
     * Sets the size.
     *
     * @param size The new size of the font.
     */
    public void setSize(float size) {
        this.size = size;
    }

    // STYLE

    /**
     * Gets the style of this font.
     *
     * @return a size
     */
    public int getStyle() {
        return style;
    }

    /**
     * Gets the style that can be used with the calculated <CODE>BaseFont
     * </CODE>.
     *
     * @return the style that can be used with the calculated <CODE>BaseFont
     * </CODE>
     */
    public int getCalculatedStyle() {
        int style = this.style;
        if (style == UNDEFINED) {
            style = NORMAL;
        }
        if (family == SYMBOL || family == ZAPFDINGBATS) {
            return style;
        } else {
            return style & ~BOLDITALIC;
        }
    }

    /**
     * checks if this font is Bold.
     *
     * @return a <CODE>boolean</CODE>
     */
    public boolean isBold() {
        if (style == UNDEFINED) {
            return false;
        }
        return (style & BOLD) == BOLD;
    }

    /**
     * checks if this font is Bold.
     *
     * @return a <CODE>boolean</CODE>
     */
    public boolean isItalic() {
        if (style == UNDEFINED) {
            return false;
        }
        return (style & ITALIC) == ITALIC;
    }

    /**
     * checks if this font is underlined.
     *
     * @return a <CODE>boolean</CODE>
     */
    public boolean isUnderlined() {
        if (style == UNDEFINED) {
            return false;
        }
        return (style & UNDERLINE) == UNDERLINE;
    }

    /**
     * checks if the style of this font is STRIKETHRU.
     *
     * @return a <CODE>boolean</CODE>
     */
    public boolean isStrikethru() {
        if (style == UNDEFINED) {
            return false;
        }
        return (style & STRIKETHRU) == STRIKETHRU;
    }

    /**
     * Sets the style.
     *
     * @param style the style.
     */
    public void setStyle(int style) {
        this.style = style;
    }

    /**
     * Sets the style using a <CODE>String</CODE> containing one of more of
     * the following values: normal, bold, italic, underline, strike.
     *
     * @param style A <CODE>String</CODE> representing a certain style.
     */
    public void setStyle(String style) {
        if (this.style == UNDEFINED) {
            this.style = NORMAL;
        }
        this.style |= getStyleValue(style);
    }

    /**
     * Translates a <CODE>String</CODE> -value of a certain style into the
     * index value is used for this style in this class.
     *
     * @param style A <CODE>String</CODE>
     * @return the corresponding value
     */
    public static int getStyleValue(String style) {
        int s = 0;
        if (style.contains(Markup.CSS_VALUE_BOLD)) {
            s |= BOLD;
        }
        if (style.contains(Markup.CSS_VALUE_ITALIC)) {
            s |= ITALIC;
        }
        if (style.contains(Markup.CSS_VALUE_OBLIQUE)) {
            s |= ITALIC;
        }
        if (style.contains(Markup.CSS_VALUE_UNDERLINE)) {
            s |= UNDERLINE;
        }
        if (style.contains(Markup.CSS_VALUE_LINETHROUGH)) {
            s |= STRIKETHRU;
        }
        return s;
    }

    // COLOR

    /**
     * Gets the color of this font.
     *
     * @return a color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color.
     *
     * @param color the new color of the font
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the color.
     *
     * @param red   the red-value of the new color
     * @param green the green-value of the new color
     * @param blue  the blue-value of the new color
     */
    public void setColor(int red, int green, int blue) {
        this.color = new Color(red, green, blue);
    }

    // BASEFONT

    // Helper methods

    /**
     * Checks if the properties of this font are undefined or null.
     * <p>
     * If so, the standard should be used.
     *
     * @return a <CODE>boolean</CODE>
     */
    public boolean isStandardFont() {
        return family == UNDEFINED && size == UNDEFINED && style == UNDEFINED && color == null;
    }

    /**
     * Replaces the attributes that are equal to <VAR>null</VAR> with the
     * attributes of a given font.
     *
     * @param font the font of a bigger element class
     * @return a <CODE>Font</CODE>
     */
    public Font difference(Font font) {
        if (font == null)
            return this;
        // size
        float dSize = font.size;
        if (dSize == UNDEFINED) {
            dSize = this.size;
        }
        // style
        int dStyle = UNDEFINED;
        int style1 = this.style;
        int style2 = font.getStyle();
        if (style1 != UNDEFINED || style2 != UNDEFINED) {
            if (style1 == UNDEFINED) {
                style1 = 0;
            }
            if (style2 == UNDEFINED) {
                style2 = 0;
            }
            dStyle = style1 | style2;
        }
        // color
        Color dColor = font.color;
        if (dColor == null) {
            dColor = this.color;
        }
        // family
        if (font.getFamily() != UNDEFINED) {
            return new Font(font.family, dSize, dStyle, dColor);
        }
        return new Font(this.family, dSize, dStyle, dColor);
    }
}
