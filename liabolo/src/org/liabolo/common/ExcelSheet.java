/*
 * Created on May 13, 2004 by Thorsten Schloermann
 *
 * Copyright (c) Projektgruppe P30 Uni Oldenburg Germany
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; see the file COPYING.  If not, write to
 * the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package org.liabolo.common;


import org.liabolo.exception.ImportFailureException;
import org.liabolo.repository.Library;
import org.liabolo.service.Dispatcher;

import java.io.*;
import java.util.*;

import jxl.*;


public class ExcelSheet {

    private Logger log = Logger.getLogger(this.getClass());

    public class ExcelColumns {
        public byte titleCol = -1;
        public byte creatorCol = -1;
        public byte subjectCol = -1;
        public byte descriptionCol = -1;
        public byte publisherCol = -1;
        public byte contributorsCol = -1;
        public byte dateCol = -1;
        public byte typeCol = -1;
        public byte formatCol = -1;
        public byte sourceCol = -1;
        public byte languageCol = -1;
        public byte relationCol = -1;
        public byte coverageCol = -1;
        public byte rightsCol = -1;

    }

    public ExcelColumns columns = new ExcelColumns();

    private Workbook workbook;
    private Sheet sheet;
    public ArrayList rowList;

    public ExcelSheet() {
    }

    public ExcelSheet(String name) throws ImportFailureException {
        this.rowList = this.initSheet(name);
        if (this.rowList == null) throw new ImportFailureException("Excel import failed!");
    }


    private ArrayList initSheet(String name) {

        boolean rowHasEntry;

        // read an Excel-File
        try {
            this.workbook = Workbook.getWorkbook(new File(name));
        } catch (IOException e) {
            log.debug(e);
            return null;
        } catch (Exception e) {
            log.debug(e);
            return null;
        }
        this.sheet = workbook.getSheet(0);

        if (sheet.getRows() == 0) {
            log.error("Excel file is empty");
            return null;
        } else {


            rowList = new ArrayList();

            int col = 0; // help-variables (rows and columns)for running
            int row = 0; //                through the spreadsheet cells
            //boolean formatError = false;
            Cell cell;
            for (row = 0; row < this.sheet.getRows(); row++) {
                rowHasEntry = false;

                // check, if row has some entry anywhere
                for (col = 0; col < this.sheet.getColumns(); col++) {
                    cell = this.sheet.getCell(col, row);
                    String check = cell.getContents().toLowerCase();
                    if (!check.equals("")) {
                        rowHasEntry = true;
                    }
                }
                // add the entries of the cells to list
                if (rowHasEntry) {
                    ArrayList entryList = new ArrayList();
                    for (col = 0; col < this.sheet.getColumns(); col++) {
                        cell = this.sheet.getCell(col, row);
                        String check = cell.getContents();
                        entryList.add(check);
                    }
                    rowList.add(entryList);
                }
            }
            return rowList;
        }


    }

    /** returns the number of entries in the Excel-file
     *
     * @return number of entries in the Excel-file
     */
    public int size() {
        return rowList.size();
    }

    /** returns an ArrayList containing the
     *  entries in the specified row.
     * 	The row index begins with 1.
     * @param entryNumber
     * @return ArrayList
     */
    public ArrayList getEntry(int entryNumber) {

        if (rowList == null)
            return null;
        else {
            ArrayList entries = (ArrayList) this.rowList.get(entryNumber);
            return entries;
        }

    }


    public ArrayList setValues(ExcelColumns columns, String branch, String type, Dispatcher dispatcher, Library actLib) {
        boolean added = false;
        boolean error_occured = false;
        int counter = 0;
        ArrayList errors = new ArrayList();
        this.columns = columns;

        ArrayList entries = new ArrayList();
        for (int row = 0; row < this.rowList.size(); row++) {
            MetaData metadata = MetaData.createNewMetaData(branch, actLib.getDbURI());
            if (metadata != null) {
                metadata.setLiabolo_branch(branch);
                metadata.setDc_type(type);
                entries = this.getEntry(row);
                for (int col = 0; col < entries.size(); col++) {
                    String cellContent = entries.get(col).toString();

                    if (columns.titleCol == col) metadata.setDc_title(cellContent);
                    if (columns.creatorCol == col) metadata.setDc_creator(cellContent);
                    if (columns.subjectCol == col) metadata.setDc_subject(cellContent);
                    if (columns.descriptionCol == col) metadata.setDc_description(cellContent);
                    if (columns.publisherCol == col) metadata.setDc_publisher(cellContent);
                    if (columns.contributorsCol == col) metadata.setDc_contributors(cellContent);
                    if (columns.dateCol == col) metadata.setDc_date(MetaData.convertDate(cellContent));
                    if (columns.typeCol == col) metadata.setDc_type(type);
                    if (columns.formatCol == col) metadata.setDc_format(cellContent);
                    if (columns.sourceCol == col) metadata.setDc_source(cellContent);
                    if (columns.languageCol == col) metadata.setDc_language(cellContent);
                    if (columns.relationCol == col) metadata.setDc_relation(cellContent);
                    if (columns.coverageCol == col) metadata.setDc_coverage(cellContent);
                    if (columns.rightsCol == col) metadata.setDc_rights(cellContent);


                }

                log.pause();
                LibItem newItem = new LibItem();
                newItem.setMetaData(metadata);
//			if (row == 1) newItem = null; // provoke an error for testing
                added = dispatcher.addLibItem(newItem, actLib, false);
                log.resume();
                if (added)
                    counter++;
                else {
                    error_occured = true;
                    String s = "Zeile " + (row + 1);
                    errors.add(s);
                }
                log.info(counter + " Entries added");

            }
        }
        return errors;
    }
}



