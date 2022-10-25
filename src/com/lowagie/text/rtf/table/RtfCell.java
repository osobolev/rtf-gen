/*
 * $Id: RtfCell.java 3580 2008-08-06 15:52:00Z howard_s $
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
package com.lowagie.text.rtf.table;

import com.lowagie.text.*;
import com.lowagie.text.rtf.RtfBasicElement;
import com.lowagie.text.rtf.RtfExtendedElement;
import com.lowagie.text.rtf.document.RtfDocument;
import com.lowagie.text.rtf.style.RtfColor;
import com.lowagie.text.rtf.style.RtfParagraphStyle;
import com.lowagie.text.rtf.text.RtfParagraph;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import static com.lowagie.text.rtf.RtfElement.intToByteArray;

/**
 * The RtfCell wraps a Cell, but can also be added directly to a Table.
 * The RtfCell is an extension of Cell, that supports a multitude of different
 * borderstyles.
 *
 * @author Mark Hall (Mark.Hall@mail.room3b.eu)
 * @author Steffen Stundzig
 * @author Benoit Wiart
 * @author Thomas Bickel (tmb99@inode.at)
 * @version $Id: RtfCell.java 3580 2008-08-06 15:52:00Z howard_s $
 * @see com.lowagie.text.rtf.table.RtfBorder
 */
public class RtfCell extends Cell implements RtfExtendedElement {

    /**
     * This cell is not merged
     */
    private static final int MERGE_NONE = 0;
    /**
     * This cell is the parent cell of a vertical merge operation
     */
    private static final int MERGE_VERT_PARENT = 1;
    /**
     * This cell is a child cell of a vertical merge operation
     */
    private static final int MERGE_VERT_CHILD = 2;

    /**
     * The parent RtfRow of this RtfCell
     */
    private RtfRow parentRow = null;
    /**
     * The content of this RtfCell
     */
    private java.util.List<RtfBasicElement> content = null;
    /**
     * The right margin of this RtfCell
     */
    private int cellRight = 0;
    /**
     * The width of this RtfCell
     */
    private int cellWidth = 0;
    /**
     * The borders of this RtfCell
     */
    private RtfBorderGroup borders = null;

    /**
     * The background color of this RtfCell
     */
    private RtfColor backgroundColor = null;
    /**
     * The padding of this RtfCell
     */
    private int cellPadding = 0;
    /**
     * The merge type of this RtfCell
     */
    private int mergeType = MERGE_NONE;
    /**
     * The RtfDocument this RtfCell belongs to
     */
    private RtfDocument document = null;
    /**
     * Whether this RtfCell is in a header
     */
    private boolean inHeader = false;
    /**
     * Whether this RtfCell is a placeholder for a removed table cell.
     */
    private boolean deleted = false;

    /**
     * Constructs an empty RtfCell
     */
    public RtfCell() {
        this.borders = new RtfBorderGroup();
        verticalAlignment = ALIGN_MIDDLE;
    }

    /**
     * Constructs a RtfCell based upon a String
     *
     * @param content The String to base the RtfCell on
     */
    public RtfCell(String content) {
        super(content);
        this.borders = new RtfBorderGroup();
        verticalAlignment = ALIGN_MIDDLE;
    }

    /**
     * Constructs a RtfCell based upon an Element
     *
     * @param element The Element to base the RtfCell on
     * @throws BadElementException If the Element is not valid
     */
    public RtfCell(Element element) throws BadElementException {
        super(element);
        this.borders = new RtfBorderGroup();
        verticalAlignment = ALIGN_MIDDLE;
    }

    /**
     * Constructs a deleted RtfCell.
     *
     * @param deleted Whether this RtfCell is actually deleted.
     */
    protected RtfCell(boolean deleted) {
        this.deleted = deleted;
        verticalAlignment = ALIGN_MIDDLE;
    }

    /**
     * Constructs a RtfCell based on a Cell.
     *
     * @param doc  The RtfDocument this RtfCell belongs to
     * @param row  The RtfRow this RtfCell lies in
     * @param cell The Cell to base this RtfCell on
     */
    protected RtfCell(RtfDocument doc, RtfRow row, Cell cell) {
        this.document = doc;
        this.parentRow = row;
        importCell(cell);
    }

    /**
     * Imports the Cell properties into the RtfCell
     *
     * @param cell The Cell to import
     */
    private void importCell(Cell cell) {
        this.content = new ArrayList<>();

        if (cell == null) {
            this.borders = new RtfBorderGroup(this.document, RtfBorder.CELL_BORDER, this.parentRow.getParentTable().getBorders());
            return;
        }

        this.colspan = cell.getColspan();
        this.rowspan = cell.getRowspan();
        if (cell.getRowspan() > 1) {
            this.mergeType = MERGE_VERT_PARENT;
        }
        if (cell instanceof RtfCell) {
            this.borders = new RtfBorderGroup(this.document, RtfBorder.CELL_BORDER, ((RtfCell) cell).getBorders());
        } else {
            this.borders = new RtfBorderGroup(this.document, RtfBorder.CELL_BORDER, cell.getBorder(), cell.getBorderWidth(), cell.getBorderColor());
        }
        this.verticalAlignment = cell.getVerticalAlignment();
        if (cell.getBackgroundColor() == null) {
            this.backgroundColor = new RtfColor(this.document, 255, 255, 255);
        } else {
            this.backgroundColor = new RtfColor(this.document, cell.getBackgroundColor());
        }

        this.cellPadding = (int) this.parentRow.getParentTable().getCellPadding();

        Iterator<Element> cellIterator = cell.getElements();
        Paragraph container = null;
        while (cellIterator.hasNext()) {
            try {
                Element element = cellIterator.next();
                // should we wrap it in a paragraph
                if (!(element instanceof Paragraph) && !(element instanceof List)) {
                    if (container != null) {
                        container.add(element);
                    } else {
                        container = new Paragraph();
                        container.setAlignment(cell.getHorizontalAlignment());
                        container.add(element);
                    }
                } else {
                    if (container != null) {
                        RtfBasicElement[] rtfElements = this.document.getMapper().mapElement(container);
                        for (RtfBasicElement rtfElement : rtfElements) {
                            rtfElement.setInTable(true);
                            this.content.add(rtfElement);
                        }
                        container = null;
                    }
                    // if horizontal alignment is undefined overwrite
                    // with that of enclosing cell
                    if (element instanceof Paragraph && ((Paragraph) element).getAlignment() == Element.ALIGN_UNDEFINED) {
                        ((Paragraph) element).setAlignment(cell.getHorizontalAlignment());
                    }

                    RtfBasicElement[] rtfElements = this.document.getMapper().mapElement(element);
                    for (RtfBasicElement rtfElement : rtfElements) {
                        rtfElement.setInTable(true);
                        this.content.add(rtfElement);
                    }
                }
            } catch (DocumentException de) {
                document.log(de);
            }
        }
        if (container != null) {
            try {
                RtfBasicElement[] rtfElements = this.document.getMapper().mapElement(container);
                for (RtfBasicElement rtfElement : rtfElements) {
                    rtfElement.setInTable(true);
                    this.content.add(rtfElement);
                }
            } catch (DocumentException de) {
                document.log(de);
            }
        }
    }

    /**
     * Write the cell definition part of this RtfCell
     */
    @Override
    public void writeDefinition(OutputStream result) throws IOException {
        if (this.mergeType == MERGE_VERT_PARENT) {
            result.write(DocWriter.getISOBytes("\\clvmgf"));
        } else if (this.mergeType == MERGE_VERT_CHILD) {
            result.write(DocWriter.getISOBytes("\\clvmrg"));
        }
        switch (verticalAlignment) {
        case Element.ALIGN_BOTTOM:
            result.write(DocWriter.getISOBytes("\\clvertalb"));
            break;
        case Element.ALIGN_CENTER:
        case Element.ALIGN_MIDDLE:
            result.write(DocWriter.getISOBytes("\\clvertalc"));
            break;
        case Element.ALIGN_TOP:
            result.write(DocWriter.getISOBytes("\\clvertalt"));
            break;
        }
        this.borders.writeContent(result);

        if (this.backgroundColor != null) {
            result.write(DocWriter.getISOBytes("\\clcbpat"));
            result.write(intToByteArray(this.backgroundColor.getColorNumber()));
        }
        this.document.outputDebugLinebreak(result);

        result.write(DocWriter.getISOBytes("\\clftsWidth3"));
        this.document.outputDebugLinebreak(result);

        result.write(DocWriter.getISOBytes("\\clwWidth"));
        result.write(intToByteArray(this.cellWidth));
        this.document.outputDebugLinebreak(result);

        if (this.cellPadding > 0) {
            result.write(DocWriter.getISOBytes("\\clpadl"));
            result.write(intToByteArray(this.cellPadding / 2));
            result.write(DocWriter.getISOBytes("\\clpadt"));
            result.write(intToByteArray(this.cellPadding / 2));
            result.write(DocWriter.getISOBytes("\\clpadr"));
            result.write(intToByteArray(this.cellPadding / 2));
            result.write(DocWriter.getISOBytes("\\clpadb"));
            result.write(intToByteArray(this.cellPadding / 2));
            result.write(DocWriter.getISOBytes("\\clpadfl3"));
            result.write(DocWriter.getISOBytes("\\clpadft3"));
            result.write(DocWriter.getISOBytes("\\clpadfr3"));
            result.write(DocWriter.getISOBytes("\\clpadfb3"));
        }
        result.write(DocWriter.getISOBytes("\\cellx"));
        result.write(intToByteArray(this.cellRight));
    }

    /**
     * Write the content of this RtfCell
     */
    @Override
    public void writeContent(OutputStream result) throws IOException {
        if (this.content.isEmpty()) {
            result.write(RtfParagraph.PARAGRAPH_DEFAULTS);
            if (this.parentRow.getParentTable().getTableFitToPage()) {
                result.write(RtfParagraphStyle.KEEP_TOGETHER_WITH_NEXT);
            }
            result.write(RtfParagraph.IN_TABLE);
        } else {
            for (int i = 0; i < this.content.size(); i++) {
                RtfBasicElement rtfElement = this.content.get(i);
                if (rtfElement instanceof RtfParagraph) {
                    ((RtfParagraph) rtfElement).setKeepTogetherWithNext(this.parentRow.getParentTable().getTableFitToPage());
                }
                rtfElement.writeContent(result);
                if (rtfElement instanceof RtfParagraph && i < (this.content.size() - 1)) {
                    result.write(RtfParagraph.PARAGRAPH);
                }
            }
        }
        result.write(DocWriter.getISOBytes("\\cell"));
    }

    /**
     * Sets the right margin of this cell. Used in merge operations
     *
     * @param cellRight The right margin to use
     */
    protected void setCellRight(int cellRight) {
        this.cellRight = cellRight;
    }

    /**
     * Gets the right margin of this RtfCell
     *
     * @return The right margin of this RtfCell.
     */
    protected int getCellRight() {
        return this.cellRight;
    }

    /**
     * Sets the cell width of this RtfCell. Used in merge operations.
     *
     * @param cellWidth The cell width to use
     */
    protected void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    /**
     * Gets the cell width of this RtfCell
     *
     * @return The cell width of this RtfCell
     */
    protected int getCellWidth() {
        return this.cellWidth;
    }

    /**
     * Gets the cell padding of this RtfCell
     *
     * @return The cell padding of this RtfCell
     */
    protected int getCellpadding() {
        return this.cellPadding;
    }

    /**
     * Gets the borders of this RtfCell
     *
     * @return The borders of this RtfCell
     */
    protected RtfBorderGroup getBorders() {
        return this.borders;
    }

    /**
     * Set the borders of this RtfCell
     *
     * @param borderGroup The RtfBorderGroup to use as borders
     */
    public void setBorders(RtfBorderGroup borderGroup) {
        this.borders = new RtfBorderGroup(this.document, RtfBorder.CELL_BORDER, borderGroup);
    }

    /**
     * Get the background color of this RtfCell
     *
     * @return The background color of this RtfCell
     */
    protected RtfColor getRtfBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * Merge this cell into the parent cell.
     *
     * @param mergeParent The RtfCell to merge with
     */
    protected void setCellMergeChild(RtfCell mergeParent) {
        this.mergeType = MERGE_VERT_CHILD;
        this.cellWidth = mergeParent.getCellWidth();
        this.cellRight = mergeParent.getCellRight();
        this.cellPadding = mergeParent.getCellpadding();
        this.borders = mergeParent.getBorders();
        this.verticalAlignment = mergeParent.getVerticalAlignment();
        this.backgroundColor = mergeParent.getRtfBackgroundColor();
    }

    /**
     * Sets the RtfDocument this RtfCell belongs to
     *
     * @param doc The RtfDocument to use
     */
    @Override
    public void setRtfDocument(RtfDocument doc) {
        this.document = doc;
    }

    /**
     * Unused
     *
     * @param inTable
     */
    @Override
    public void setInTable(boolean inTable) {
    }

    /**
     * Sets whether this RtfCell is in a header
     *
     * @param inHeader <code>True</code> if this RtfCell is in a header, <code>false</code> otherwise
     */
    @Override
    public void setInHeader(boolean inHeader) {
        this.inHeader = inHeader;
        for (RtfBasicElement rtfElement : this.content) {
            rtfElement.setInHeader(inHeader);
        }
    }

    /**
     * Gets whether this <code>RtfCell</code> is in a header
     *
     * @return <code>True</code> if this <code>RtfCell</code> is in a header, <code>false</code> otherwise
     * @since 2.1.0
     */
    public boolean isInHeader() {
        return this.inHeader;
    }

    /**
     * Checks whether this RtfCell is a placeholder for
     * a table cell that has been removed due to col/row spanning.
     *
     * @return <code>True</code> if this RtfCell is deleted, <code>false</code> otherwise.
     */
    public boolean isDeleted() {
        return this.deleted;
    }
}