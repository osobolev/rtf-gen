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

    public static final int META_SETMAPMODE = 0x0103;
    public static final int META_SETWINDOWORG = 0x020B;
    public static final int META_SETWINDOWEXT = 0x020C;
    public static final int META_DIBSTRETCHBLT = 0x0b41;

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
