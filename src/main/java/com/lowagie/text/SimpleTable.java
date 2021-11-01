/*
 * $Id: SimpleTable.java 3752 2009-03-04 18:02:40Z blowagie $
 *
 * Copyright 2005 by Bruno Lowagie.
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
 * of this file under either the MPL or the GNU LIBRARY GENERAL PUBLIC LICENSE
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the MPL as stated above or under the terms of the GNU
 * Library General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU LIBRARY GENERAL PUBLIC LICENSE for more
 * details.
 *
 * If you didn't download this code from the following link, you should check if
 * you aren't using an obsolete version:
 * https://github.com/osobolev/rtf-gen
 */
package com.lowagie.text;

import java.util.ArrayList;

/**
 * Rectangle that can be used for Cells.
 * This Rectangle is padded and knows how to draw itself in a PdfPTable or PdfPcellEvent.
 */
public class SimpleTable extends Rectangle implements TextElementArray {

    /**
     * the content of a Table.
     */
    private final java.util.List<SimpleCell> content = new ArrayList<>();
    /**
     * the width of the Table.
     */
    private float width = 0f;
    /**
     * the widthpercentage of the Table.
     */
    private float widthpercentage = 0f;
    /**
     * the spacing of the Cells.
     */
    private float cellspacing;
    /**
     * the padding of the Cells.
     */
    private float cellpadding;
    /**
     * the alignment of the table.
     */
    private int alignment;

    /**
     * A RectangleCell is always constructed without any dimensions.
     * Dimensions are defined after creation.
     */
    public SimpleTable() {
        super(0f, 0f, 0f, 0f);
        setBorder(BOX);
        setBorderWidth(2f);
    }

    /**
     * Adds content to this object.
     *
     * @param element
     * @throws BadElementException
     */
    public void addElement(SimpleCell element) throws BadElementException {
        if (!element.isCellgroup()) {
            throw new BadElementException("You can't add cells to a table directly, add them to a row first.");
        }
        content.add(element);
    }

    /**
     * Creates a Table object based on this TableAttributes object.
     *
     * @return a com.lowagie.text.Table object
     * @throws BadElementException
     */
    public Table createTable() throws BadElementException {
        if (content.isEmpty())
            throw new BadElementException("Trying to create a table without rows.");
        SimpleCell row = content.get(0);
        SimpleCell cell;
        int columns = 0;
        for (Element value : row.getContent()) {
            cell = (SimpleCell) value;
            columns += cell.getColspan();
        }
        float[] widths = new float[columns];
        float[] widthpercentages = new float[columns];
        Table table = new Table(columns);
        table.setAlignment(alignment);
        table.setSpacing(cellspacing);
        table.setPadding(cellpadding);
        table.cloneNonPositionParameters(this);
        for (SimpleCell simpleCell : content) {
            row = simpleCell;
            int pos = 0;
            for (Element element : row.getContent()) {
                cell = (SimpleCell) element;
                table.addCell(cell.createCell(row));
                if (cell.getColspan() == 1) {
                    if (cell.getWidth() > 0) {
                        widths[pos] = cell.getWidth();
                    }
                    if (cell.getWidthpercentage() > 0) {
                        widthpercentages[pos] = cell.getWidthpercentage();
                    }
                }
                pos += cell.getColspan();
            }
        }
        float sumWidths = 0f;
        for (int i = 0; i < columns; i++) {
            if (widths[i] == 0) {
                sumWidths = 0;
                break;
            }
            sumWidths += widths[i];
        }
        if (sumWidths > 0) {
            table.setWidth(sumWidths);
            table.setLocked(true);
            table.setWidths(widths);
        } else {
            for (int i = 0; i < columns; i++) {
                if (widthpercentages[i] == 0) {
                    sumWidths = 0;
                    break;
                }
                sumWidths += widthpercentages[i];
            }
            if (sumWidths > 0) {
                table.setWidths(widthpercentages);
            }
        }
        if (width > 0) {
            table.setWidth(width);
            table.setLocked(true);
        } else if (widthpercentage > 0) {
            table.setWidth(widthpercentage);
        }
        return table;
    }

    /**
     * @return Returns the cellpadding.
     */
    public float getCellpadding() {
        return cellpadding;
    }

    /**
     * @param cellpadding The cellpadding to set.
     */
    public void setCellpadding(float cellpadding) {
        this.cellpadding = cellpadding;
    }

    /**
     * @return Returns the cellspacing.
     */
    public float getCellspacing() {
        return cellspacing;
    }

    /**
     * @param cellspacing The cellspacing to set.
     */
    public void setCellspacing(float cellspacing) {
        this.cellspacing = cellspacing;
    }

    /**
     * @return Returns the alignment.
     */
    public int getAlignment() {
        return alignment;
    }

    /**
     * @param alignment The alignment to set.
     */
    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    /**
     * @return Returns the width.
     */
    public float getWidth() {
        return width;
    }

    /**
     * @param width The width to set.
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * @return Returns the widthpercentage.
     */
    public float getWidthpercentage() {
        return widthpercentage;
    }

    /**
     * @param widthpercentage The widthpercentage to set.
     */
    public void setWidthpercentage(float widthpercentage) {
        this.widthpercentage = widthpercentage;
    }

    /**
     * @see com.lowagie.text.Element#type()
     */
    public int type() {
        return Element.TABLE;
    }

    /**
     * @since iText 2.0.8
     * @see com.lowagie.text.Element#isNestable()
     */
    public boolean isNestable() {
        return true;
    }

    /**
     * @see com.lowagie.text.TextElementArray#add(Element)
     */
    public boolean add(Element o) {
        try {
            addElement((SimpleCell) o);
            return true;
        } catch (ClassCastException e) {
            return false;
        } catch (BadElementException e) {
            throw new ExceptionConverter(e);
        }
    }
}
