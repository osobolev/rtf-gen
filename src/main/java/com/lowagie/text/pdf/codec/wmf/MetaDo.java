/*
 * $Id: MetaDo.java 3373 2008-05-12 16:21:24Z xlv $
 *
 * Copyright 2001, 2002 Paulo Soares
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
package com.lowagie.text.pdf.codec.wmf;

import com.lowagie.text.Image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MetaDo {

    public static final int META_SETBKCOLOR = 0x0201;
    public static final int META_SETBKMODE = 0x0102;
    public static final int META_SETMAPMODE = 0x0103;
    public static final int META_SETROP2 = 0x0104;
    public static final int META_SETRELABS = 0x0105;
    public static final int META_SETPOLYFILLMODE = 0x0106;
    public static final int META_SETSTRETCHBLTMODE = 0x0107;
    public static final int META_SETTEXTCHAREXTRA = 0x0108;
    public static final int META_SETTEXTCOLOR = 0x0209;
    public static final int META_SETTEXTJUSTIFICATION = 0x020A;
    public static final int META_SETWINDOWORG = 0x020B;
    public static final int META_SETWINDOWEXT = 0x020C;
    public static final int META_SETVIEWPORTORG = 0x020D;
    public static final int META_SETVIEWPORTEXT = 0x020E;
    public static final int META_OFFSETWINDOWORG = 0x020F;
    public static final int META_SCALEWINDOWEXT = 0x0410;
    public static final int META_OFFSETVIEWPORTORG = 0x0211;
    public static final int META_SCALEVIEWPORTEXT = 0x0412;
    public static final int META_LINETO = 0x0213;
    public static final int META_MOVETO = 0x0214;
    public static final int META_EXCLUDECLIPRECT = 0x0415;
    public static final int META_INTERSECTCLIPRECT = 0x0416;
    public static final int META_ARC = 0x0817;
    public static final int META_ELLIPSE = 0x0418;
    public static final int META_FLOODFILL = 0x0419;
    public static final int META_PIE = 0x081A;
    public static final int META_RECTANGLE = 0x041B;
    public static final int META_ROUNDRECT = 0x061C;
    public static final int META_PATBLT = 0x061D;
    public static final int META_SAVEDC = 0x001E;
    public static final int META_SETPIXEL = 0x041F;
    public static final int META_OFFSETCLIPRGN = 0x0220;
    public static final int META_TEXTOUT = 0x0521;
    public static final int META_BITBLT = 0x0922;
    public static final int META_STRETCHBLT = 0x0B23;
    public static final int META_POLYGON = 0x0324;
    public static final int META_POLYLINE = 0x0325;
    public static final int META_ESCAPE = 0x0626;
    public static final int META_RESTOREDC = 0x0127;
    public static final int META_FILLREGION = 0x0228;
    public static final int META_FRAMEREGION = 0x0429;
    public static final int META_INVERTREGION = 0x012A;
    public static final int META_PAINTREGION = 0x012B;
    public static final int META_SELECTCLIPREGION = 0x012C;
    public static final int META_SELECTOBJECT = 0x012D;
    public static final int META_SETTEXTALIGN = 0x012E;
    public static final int META_CHORD = 0x0830;
    public static final int META_SETMAPPERFLAGS = 0x0231;
    public static final int META_EXTTEXTOUT = 0x0a32;
    public static final int META_SETDIBTODEV = 0x0d33;
    public static final int META_SELECTPALETTE = 0x0234;
    public static final int META_REALIZEPALETTE = 0x0035;
    public static final int META_ANIMATEPALETTE = 0x0436;
    public static final int META_SETPALENTRIES = 0x0037;
    public static final int META_POLYPOLYGON = 0x0538;
    public static final int META_RESIZEPALETTE = 0x0139;
    public static final int META_DIBBITBLT = 0x0940;
    public static final int META_DIBSTRETCHBLT = 0x0b41;
    public static final int META_DIBCREATEPATTERNBRUSH = 0x0142;
    public static final int META_STRETCHDIB = 0x0f43;
    public static final int META_EXTFLOODFILL = 0x0548;
    public static final int META_DELETEOBJECT = 0x01f0;
    public static final int META_CREATEPALETTE = 0x00f7;
    public static final int META_CREATEPATTERNBRUSH = 0x01F9;
    public static final int META_CREATEPENINDIRECT = 0x02FA;
    public static final int META_CREATEFONTINDIRECT = 0x02FB;
    public static final int META_CREATEBRUSHINDIRECT = 0x02FC;
    public static final int META_CREATEREGION = 0x06FF;

    public static byte[] wrapBMP(Image image) throws IOException {
        if (image.getOriginalType() != Image.ORIGINAL_BMP)
            throw new IOException("Only BMP can be wrapped in WMF.");
        byte[] data;
        if (image.getOriginalData() == null) {
            InputStream imgIn = image.getUrl().openStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int b;
            while ((b = imgIn.read()) != -1)
                out.write(b);
            imgIn.close();
            data = out.toByteArray();
        } else
            data = image.getOriginalData();
        int sizeBmpWords = (data.length - 14 + 1) >>> 1;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // write metafile header
        writeWord(os, 1);
        writeWord(os, 9);
        writeWord(os, 0x0300);
        writeDWord(os, 9 + 4 + 5 + 5 + (13 + sizeBmpWords) + 3); // total metafile size
        writeWord(os, 1);
        writeDWord(os, 14 + sizeBmpWords); // max record size
        writeWord(os, 0);
        // write records
        writeDWord(os, 4);
        writeWord(os, META_SETMAPMODE);
        writeWord(os, 8);

        writeDWord(os, 5);
        writeWord(os, META_SETWINDOWORG);
        writeWord(os, 0);
        writeWord(os, 0);

        writeDWord(os, 5);
        writeWord(os, META_SETWINDOWEXT);
        writeWord(os, (int) image.getHeight());
        writeWord(os, (int) image.getWidth());

        writeDWord(os, 13 + sizeBmpWords);
        writeWord(os, META_DIBSTRETCHBLT);
        writeDWord(os, 0x00cc0020);
        writeWord(os, (int) image.getHeight());
        writeWord(os, (int) image.getWidth());
        writeWord(os, 0);
        writeWord(os, 0);
        writeWord(os, (int) image.getHeight());
        writeWord(os, (int) image.getWidth());
        writeWord(os, 0);
        writeWord(os, 0);
        os.write(data, 14, data.length - 14);
        if ((data.length & 1) == 1)
            os.write(0);
//        writeDWord(os, 14 + sizeBmpWords);
//        writeWord(os, META_STRETCHDIB);
//        writeDWord(os, 0x00cc0020);
//        writeWord(os, 0);
//        writeWord(os, (int)image.height());
//        writeWord(os, (int)image.width());
//        writeWord(os, 0);
//        writeWord(os, 0);
//        writeWord(os, (int)image.height());
//        writeWord(os, (int)image.width());
//        writeWord(os, 0);
//        writeWord(os, 0);
//        os.write(data, 14, data.length - 14);
//        if ((data.length & 1) == 1)
//            os.write(0);

        writeDWord(os, 3);
        writeWord(os, 0);
        os.close();
        return os.toByteArray();
    }

    public static void writeWord(OutputStream os, int v) throws IOException {
        os.write(v & 0xff);
        os.write((v >>> 8) & 0xff);
    }

    public static void writeDWord(OutputStream os, int v) throws IOException {
        writeWord(os, v & 0xffff);
        writeWord(os, (v >>> 16) & 0xffff);
    }
}
