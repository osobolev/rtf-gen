/*
 * $Id: Image.java 3941 2009-05-28 14:52:26Z blowagie $
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

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * An <CODE>Image</CODE> is the representation of a graphic element (JPEG, PNG
 * or GIF) that has to be inserted into the document
 *
 * @see Element
 * @see Rectangle
 */
public class Image extends Rectangle {

    // static final membervariables

    /**
     * type of image
     */
    public static final int ORIGINAL_NONE = 0;

    /**
     * type of image
     */
    public static final int ORIGINAL_JPEG = 1;

    /**
     * type of image
     */
    public static final int ORIGINAL_PNG = 2;

    /**
     * type of image
     */
    public static final int ORIGINAL_GIF = 3;

    /**
     * type of image
     */
    public static final int ORIGINAL_BMP = 4;

    /**
     * type of image
     */
    public static final int ORIGINAL_WMF = 6;

    // member variables

    /**
     * The URL of the image.
     */
    private final URL url;

    /**
     * Holds value of property originalType.
     */
    private final int originalType;

    /**
     * Holds value of property originalData.
     */
    private final byte[] originalData;

    /**
     * The alignment of the Image.
     */
    private int alignment = Element.ALIGN_LEFT;

    /**
     * Text that can be shown instead of the image.
     */
    private String alt;

    /**
     * This is the width of the image without rotation.
     */
    private float plainWidth;

    /**
     * This is the width of the image without rotation.
     */
    private float plainHeight;

    public Image(URL url, byte[] originalData, int width, int height, int originalType) {
        super(width, height);
        this.url = url;
        this.originalData = originalData;
        this.plainWidth = width;
        this.plainHeight = height;
        this.originalType = originalType;
    }

    private static final class ImageInfo {

        final BufferedImage image;
        final int type;

        ImageInfo(BufferedImage image, int type) {
            this.image = image;
            this.type = type;
        }
    }

    private Image(URL url, byte[] originalData, ImageInfo info) {
        this(url, originalData, info.image.getWidth(), info.image.getHeight(), info.type);
    }

    private static ImageInfo readImage(Object input) throws IOException {
        try (ImageInputStream iis = ImageIO.createImageInputStream(input)) {
            if (iis == null)
                throw new IIOException("Can't create an ImageInputStream!");
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                ImageReadParam param = reader.getDefaultReadParam();
                reader.setInput(iis, true, true);
                BufferedImage bi;
                try {
                    bi = reader.read(0, param);
                } finally {
                    reader.dispose();
                }
                String format = reader.getFormatName();
                int type;
                if ("gif".equalsIgnoreCase(format)) {
                    type = ORIGINAL_GIF;
                } else if ("JPEG".equalsIgnoreCase(format)) {
                    type = ORIGINAL_JPEG;
                } else if ("png".equalsIgnoreCase(format)) {
                    type = ORIGINAL_PNG;
                } else if ("bmp".equalsIgnoreCase(format)) {
                    type = ORIGINAL_BMP;
                } else {
                    type = ORIGINAL_NONE;
                }
                return new ImageInfo(bi, type);
            } else {
                throw new IOException("Cannot detect image format");
            }
        }
    }

    /**
     * Gets an instance of an Image.
     *
     * @param url an URL
     * @return an Image
     * @throws BadElementException
     * @throws MalformedURLException
     * @throws IOException
     */
    public static Image getInstance(URL url) throws MalformedURLException, IOException {
        ImageInfo info;
        try (InputStream is = url.openStream()) {
            info = readImage(is);
        }
        return new Image(url, null, info);
    }

    /**
     * Gets an instance of an Image.
     *
     * @param filename a filename
     * @return an object of type <CODE>Gif</CODE>,<CODE>Jpeg</CODE> or
     * <CODE>Png</CODE>
     * @throws BadElementException
     * @throws MalformedURLException
     * @throws IOException
     */
    public static Image getInstance(String filename) throws MalformedURLException, IOException {
        Path path = Paths.get(filename);
        byte[] data = Files.readAllBytes(path);
        ImageInfo info = readImage(new ByteArrayInputStream(data));
        return new Image(path.toUri().toURL(), data, info);
    }

    /**
     * gets an instance of an Image
     *
     * @param imgb raw image date
     * @return an Image object
     * @throws BadElementException
     * @throws MalformedURLException
     * @throws IOException
     */
    public static Image getInstance(byte[] imgb) throws MalformedURLException, IOException {
        ImageInfo info = readImage(new ByteArrayInputStream(imgb));
        return new Image(null, imgb, info);
    }

    // copy constructor

    public Image(Image image) {
        super(image);
        this.url = image.url;
        this.originalType = image.originalType;
        this.originalData = image.originalData;
        this.alignment = image.alignment;
        this.alt = image.alt;
        this.plainWidth = image.plainWidth;
        this.plainHeight = image.plainHeight;
    }

    /**
     * gets an instance of an Image
     *
     * @param image an Image object
     * @return a new Image object
     */
    public static Image getInstance(Image image) {
        if (image == null)
            return null;
        return new Image(image);
    }

    // implementation of the Element interface

    /**
     * Returns the type.
     *
     * @return a type
     */
    @Override
    public int type() {
        return IMAGE;
    }

    /**
     * @since iText 2.0.8
     * @see com.lowagie.text.Element#isNestable()
     */
    @Override
    public boolean isNestable() {
        return true;
    }

    // getters and setters

    /**
     * Gets the <CODE>String</CODE> -representation of the reference to the
     * image.
     *
     * @return a <CODE>String</CODE>
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Gets the alignment for the image.
     *
     * @return a value
     */
    public int getAlignment() {
        return alignment;
    }

    /**
     * Sets the alignment for the image.
     *
     * @param alignment the alignment
     */
    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    /**
     * Gets the alternative text for the image.
     *
     * @return a <CODE>String</CODE>
     */
    public String getAlt() {
        return alt;
    }

    /**
     * Sets the alternative information for the image.
     *
     * @param alt
     *            the alternative information
     */
    public void setAlt(String alt) {
        this.alt = alt;
    }

    // width and height

    /**
     * Gets the plain width of the image.
     *
     * @return a value
     */
    public float getPlainWidth() {
        return plainWidth;
    }

    /**
     * Gets the plain height of the image.
     *
     * @return a value
     */
    public float getPlainHeight() {
        return plainHeight;
    }

    /**
     * Scale the image to an absolute width and an absolute height.
     *
     * @param newWidth the new width
     * @param newHeight the new height
     */
    public void scaleAbsolute(float newWidth, float newHeight) {
        plainWidth = newWidth;
        plainHeight = newHeight;
    }

    /**
     * Scale the image to an absolute width.
     *
     * @param newWidth the new width
     */
    public void scaleAbsoluteWidth(float newWidth) {
        plainWidth = newWidth;
    }

    /**
     * Scale the image to an absolute height.
     *
     * @param newHeight the new height
     */
    public void scaleAbsoluteHeight(float newHeight) {
        plainHeight = newHeight;
    }

    /**
     * Scale the image to a certain percentage.
     *
     * @param percent the scaling percentage
     */
    public void scalePercent(float percent) {
        scalePercent(percent, percent);
    }

    /**
     * Scale the width and height of an image to a certain percentage.
     *
     * @param percentX the scaling percentage of the width
     * @param percentY the scaling percentage of the height
     */
    public void scalePercent(float percentX, float percentY) {
        plainWidth = (getWidth() * percentX) / 100f;
        plainHeight = (getHeight() * percentY) / 100f;
    }

    /**
     * Scales the image so that it fits a certain width and height.
     *
     * @param fitWidth the width to fit
     * @param fitHeight the height to fit
     */
    public void scaleToFit(float fitWidth, float fitHeight) {
        float percentX = (fitWidth * 100) / getWidth();
        float percentY = (fitHeight * 100) / getHeight();
        scalePercent(Math.min(percentX, percentY));
    }

    /**
     * Getter for property originalType.
     *
     * @return Value of property originalType.
     */
    public int getOriginalType() {
        return this.originalType;
    }

    /**
     * Getter for property originalData.
     *
     * @return Value of property originalData.
     */
    public byte[] getOriginalData() {
        return this.originalData;
    }
}
