/*
 * $Id: Utilities.java 3514 2008-06-27 09:26:36Z blowagie $
 *
 * Copyright 2007 by Bruno Lowagie.
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

/**
 * A collection of convenience methods that were present in many different iText
 * classes.
 */
public final class Utilities {

    /**
     * Utility method to extend an array.
     *
     * @param original the original array or <CODE>null</CODE>
     * @param item     the item to be added to the array
     * @return a new array with the item appended
     */
    public static Object[][] addToArray(Object[][] original, Object[] item) {
        if (original == null) {
            original = new Object[1][];
            original[0] = item;
            return original;
        } else {
            Object[][] original2 = new Object[original.length + 1][];
            System.arraycopy(original, 0, original2, 0, original.length);
            original2[original.length] = item;
            return original2;
        }
    }

    /**
     * Measurement conversion from millimeters to points.
     *
     * @param    value    a value in millimeters
     * @return a value in points
     * @since 2.1.2
     */
    public static float millimetersToPoints(float value) {
        return inchesToPoints(millimetersToInches(value));
    }

    /**
     * Measurement conversion from millimeters to inches.
     *
     * @param    value    a value in millimeters
     * @return a value in inches
     * @since 2.1.2
     */
    public static float millimetersToInches(float value) {
        return value / 25.4f;
    }

    /**
     * Measurement conversion from points to millimeters.
     *
     * @param    value    a value in points
     * @return a value in millimeters
     * @since 2.1.2
     */
    public static float pointsToMillimeters(float value) {
        return inchesToMillimeters(pointsToInches(value));
    }

    /**
     * Measurement conversion from points to inches.
     *
     * @param    value    a value in points
     * @return a value in inches
     * @since 2.1.2
     */
    public static float pointsToInches(float value) {
        return value / 72f;
    }

    /**
     * Measurement conversion from inches to millimeters.
     *
     * @param    value    a value in inches
     * @return a value in millimeters
     * @since 2.1.2
     */
    public static float inchesToMillimeters(float value) {
        return value * 25.4f;
    }

    /**
     * Measurement conversion from inches to points.
     *
     * @param    value    a value in inches
     * @return a value in points
     * @since 2.1.2
     */
    public static float inchesToPoints(float value) {
        return value * 72f;
    }
}
