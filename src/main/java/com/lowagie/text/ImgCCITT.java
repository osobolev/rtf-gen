/*
 * $Id: ImgCCITT.java 3373 2008-05-12 16:21:24Z xlv $
 *
 * Copyright 2000, 2001, 2002 by Paulo Soares.
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
 * http://www.lowagie.com/iText/
 */
package com.lowagie.text;

import java.net.URL;

/**
 * CCITT Image data that has to be inserted into the document
 *
 * @author Paulo Soares
 * @see        Element
 * @see        Image
 */
public class ImgCCITT extends Image {

    ImgCCITT(Image image) {
        super(image);
    }

    /**
     * Creates an Image with CCITT compression.
     *
     * @param width       the exact width of the image
     * @param height      the exact height of the image
     * @param reverseBits reverses the bits in <code>data</code>.
     *                    Bit 0 is swapped with bit 7 and so on.
     * @param typeCCITT   the type of compression in <code>data</code>. It can be
     *                    CCITTG4, CCITTG31D, CCITTG32D
     * @param parameters  parameters associated with this stream. Possible values are
     *                    CCITT_BLACKIS1, CCITT_ENCODEDBYTEALIGN, CCITT_ENDOFLINE and CCITT_ENDOFBLOCK or a
     *                    combination of them
     * @param data        the image data
     * @throws BadElementException on error
     */
    public ImgCCITT(int width, int height, boolean reverseBits, int typeCCITT, int parameters, byte[] data) throws BadElementException {
        super((URL) null);
        if (typeCCITT != CCITTG4 && typeCCITT != CCITTG3_1D && typeCCITT != CCITTG3_2D)
            throw new BadElementException("The CCITT compression type must be CCITTG4, CCITTG3_1D or CCITTG3_2D");
        if (reverseBits)
            reverseBits(data);
        type = IMGRAW;
        scaledHeight = height;
        setTop(scaledHeight);
        scaledWidth = width;
        setRight(scaledWidth);
        colorspace = parameters;
        bpc = typeCCITT;
        rawData = data;
        plainWidth = getWidth();
        plainHeight = getHeight();
    }

    /**
     * Reverses the bits in the array
     *
     * @param b the bits to reverse
     * @since 2.0.7
     */
    public static void reverseBits(byte[] b) {
        for (int k = 0; k < b.length; ++k)
            b[k] = flipTable[b[k] & 0xff];
    }

    // Table to be used when fillOrder = 2, for flipping bytes.
    private static final byte[] flipTable = {
        0, -128, 64, -64, 32, -96, 96, -32,
        16, -112, 80, -48, 48, -80, 112, -16,
        8, -120, 72, -56, 40, -88, 104, -24,
        24, -104, 88, -40, 56, -72, 120, -8,
        4, -124, 68, -60, 36, -92, 100, -28,
        20, -108, 84, -44, 52, -76, 116, -12,
        12, -116, 76, -52, 44, -84, 108, -20,
        28, -100, 92, -36, 60, -68, 124, -4,
        2, -126, 66, -62, 34, -94, 98, -30,
        18, -110, 82, -46, 50, -78, 114, -14,
        10, -118, 74, -54, 42, -86, 106, -22,
        26, -102, 90, -38, 58, -70, 122, -6,
        6, -122, 70, -58, 38, -90, 102, -26,
        22, -106, 86, -42, 54, -74, 118, -10,
        14, -114, 78, -50, 46, -82, 110, -18,
        30, -98, 94, -34, 62, -66, 126, -2,
        1, -127, 65, -63, 33, -95, 97, -31,
        17, -111, 81, -47, 49, -79, 113, -15,
        9, -119, 73, -55, 41, -87, 105, -23,
        25, -103, 89, -39, 57, -71, 121, -7,
        5, -123, 69, -59, 37, -91, 101, -27,
        21, -107, 85, -43, 53, -75, 117, -11,
        13, -115, 77, -51, 45, -83, 109, -19,
        29, -99, 93, -35, 61, -67, 125, -3,
        3, -125, 67, -61, 35, -93, 99, -29,
        19, -109, 83, -45, 51, -77, 115, -13,
        11, -117, 75, -53, 43, -85, 107, -21,
        27, -101, 91, -37, 59, -69, 123, -5,
        7, -121, 71, -57, 39, -89, 103, -25,
        23, -105, 87, -41, 55, -73, 119, -9,
        15, -113, 79, -49, 47, -81, 111, -17,
        31, -97, 95, -33, 63, -65, 127, -1,
    };
}
