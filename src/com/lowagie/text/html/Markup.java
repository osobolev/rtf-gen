/*
 * $Id: Markup.java 3654 2009-01-21 16:11:00Z blowagie $
 *
 * Copyright 2001, 2002 by Bruno Lowagie.
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
 * Contributions by:
 * Lubos Strapko
 *
 * If you didn't download this code from the following link, you should check if
 * you aren't using an obsolete version:
 * https://github.com/osobolev/rtf-gen
 */
package com.lowagie.text.html;

/**
 * A class that contains all the possible tagnames and their attributes.
 */
public class Markup {

    // HTML tags

    /**
     * The SPAN tag.
     */
    public static final String HTML_TAG_SPAN = "span";

    // HTML attributes

    /**
     * attribute for specifying externally defined CSS class
     */
    public static final String HTML_ATTR_CSS_CLASS = "class";

    // HTML values

    /**
     * This is a possible value for the language attribute (SCRIPT tag).
     */
    public static final String HTML_VALUE_JAVASCRIPT = "text/javascript";

    // CSS keys

    /**
     * the CSS tag for text color
     */
    public static final String CSS_KEY_COLOR = "color";

    /**
     * the CSS tag for the font family
     */
    public static final String CSS_KEY_FONTFAMILY = "font-family";

    /**
     * the CSS tag for the font size
     */
    public static final String CSS_KEY_FONTSIZE = "font-size";

    /**
     * the CSS tag for the font style
     */
    public static final String CSS_KEY_FONTSTYLE = "font-style";

    /**
     * the CSS tag for the font weight
     */
    public static final String CSS_KEY_FONTWEIGHT = "font-weight";

    /**
     * the CSS tag for text decorations
     */
    public static final String CSS_KEY_LINEHEIGHT = "line-height";

    /**
     * the CSS tag for adding a page break when the document is printed
     */
    public static final String CSS_KEY_PAGE_BREAK_BEFORE = "page-break-before";

    /**
     * the CSS tag for text decorations
     */
    public static final String CSS_KEY_TEXTDECORATION = "text-decoration";

    // CSS values

    /**
     * value for the CSS tag for adding a page break when the document is
     * printed
     */
    public static final String CSS_VALUE_ALWAYS = "always";

    /**
     * a CSS value for text font weight
     */
    public static final String CSS_VALUE_BOLD = "bold";

    /**
     * a CSS value for text font style
     */
    public static final String CSS_VALUE_ITALIC = "italic";

    /**
     * a CSS value for text font style
     */
    public static final String CSS_VALUE_OBLIQUE = "oblique";

    /**
     * a CSS value for text decoration
     */
    public static final String CSS_VALUE_LINETHROUGH = "line-through";

    /**
     * a CSS value for text decoration
     */
    public static final String CSS_VALUE_UNDERLINE = "underline";
}
