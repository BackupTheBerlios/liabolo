/*
 * Created on 15.01.2004
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
 *
 *
 */
package org.liabolo.client.offline.common;

import javax.swing.table.AbstractTableModel;

/**
 * @author Jurij Henne
 *
 * An implemetation of a table model for browsing forms, such BrowseBranch etc. 
 * It is used to render checkboxes inside the table cells properly and to disable disired columns for edit
 */
public class ResultTableModel extends AbstractTableModel {
	
	/** Is needed by inherited class */
	String [] columnNames= {""};
	/** Data to be shown in the table */
	Object[][] data;

	/**
	 * Creates new table model with specified data entries
	 * @param data
	 */
	public ResultTableModel(Object[][] data)
	{
		this.data = data;
	}
	
	/** Inherited */
	public int getColumnCount() {
		return columnNames.length;
	}
	/** Inherited */
	public int getRowCount() {
		return data.length;
	}
	/** Inherited */
	public String getColumnName(int col) {
		return columnNames[col];
	}
	

	/** Inherited */
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	/** Inherited */
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
	/** All columns except the first one get editable cells */
	public boolean isCellEditable(int row, int col)
	{        if (col >0) {
		return false;
	} else {
		return true;
	}

	}
	
	/** Inherited */
	public void setValueAt(Object value, int row, int col) 
	{
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}
	

}
