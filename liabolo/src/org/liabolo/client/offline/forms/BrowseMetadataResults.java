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
package org.liabolo.client.offline.forms;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.ContextMenu;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.common.ResultTableModel;
import org.liabolo.client.offline.common.WaitDialog;
import org.liabolo.client.offline.dialogs.AddToList;
import org.liabolo.client.offline.dialogs.ConflictManager;
import org.liabolo.common.Configurator;
import org.liabolo.common.LibItem;
import org.liabolo.common.MetaData;

/**
 * @author Jurij Henne
 *
 * This is an impementation of  "browse search results" - form. The user gets a scrollable list of metadata item, he has searched for
 * and can perform some usefull actions on selected entries.
 * This class is is also used for the repository management(items, which were checked out from global databases)
 */
public class BrowseMetadataResults extends DefaultForm implements ActionListener, MouseListener{

	public static final short BROWSE_RESULTS = 0;
	public static final short BROWSE_CLIPBOARD = 1;
	public static final short BROWSE_EDITED_CLIPBOARD = 2;
	
	/** Unsorted collection of search results. */
	private Collection searchResult;
	/** Sorted search results. */
	private Object [] results;

	/**
	 * Table to display results
	 * 
	 * @uml.property name="resultTable"
	 */
	private JTable resultTable;

	/** Cell entry map for browse table */
	private Object[][] tableData;
	/** String, we have seached for. */
	private String searchString;
	/** Indicates that all entries in the browse table are checked */
	private JCheckBox selectAll;
	/** Switches between BROWSE_CLIPBOAD and  BROWSE_EDITED_CLIPBOARD browse types*/
	private JCheckBox showEditedOnly;
	/** Stores selection state of every checkbox in column 0 */
	private boolean [] selectMap;
	/** Index of the column, according to which the results are sorted */
	private int sortedColumn;

	/**
	 * Indicates the type of browse form
	 * 
	 * @uml.property name="browseType"
	 */
	private int browseType;


	/**
	 * Creates and displays form to show search results 
	 * @param index specifies a unique 'position' of  the form in form holder-array
	 * @param searchString String, we searching for. In the case of the branch- /location-/individuallist-/connection search searchString will be predefined
	 * @param searchResult Collection of search results
	 * @param location	position of the form on application desktop
	 */
	public BrowseMetadataResults(int index, String searchString,Collection searchResult, Point location) 
	{	
		super(index, Gui.lang.getString("search_results"),"images/search2.png");
		this.searchResult = searchResult;	
		this.results = Gui.dispatcher.sort(MetaData.DC_TITLE,searchResult, Gui.myLib).toArray();
		this.searchString =  searchString;
		this.sortedColumn = 2;
		this.browseType = BROWSE_RESULTS;
		this.initSelectionMap();
		this.addFormFrame(location);
		Gui.my_forms[index].setMaximizable(true);
		Gui.my_forms[index].setResizable(true);
	 }
	 
	/**
	 * Creates and displays form, that manages clipboard 
	 * @param title Frame title
	 * @param index specifies a unique 'position' of  the form in form holder-array
	 * @param location	position of the form on application desktop
	 */
	public BrowseMetadataResults(int index, String title, Point location) 
	{	
		super(index, title ,"images/clipboard.png");
		this.searchResult = Gui.dispatcher.listWorkspace();	
		this.results = Gui.dispatcher.sort(MetaData.DC_TITLE,searchResult, Gui.myLib).toArray();
		this.searchString =  Gui.lang.getString("entries_clipboard");
		this.sortedColumn = 2;
		this.browseType = BROWSE_CLIPBOARD;
		
		showEditedOnly = new JCheckBox(Gui.lang.getString("show_edited_only"));
		showEditedOnly.setToolTipText(Gui.lang.getString("show_edited_only"));
		showEditedOnly.setActionCommand("show_edited_only");
		showEditedOnly.addActionListener(this);
		this.initSelectionMap();
		this.addFormFrame(location);
		Gui.my_forms[index].setMaximizable(true);
		Gui.my_forms[index].setResizable(true);
	 }
	 
	 private void initSelectionMap()
	 {
		this.selectMap = new boolean[searchResult.size()];
		for (int i = 0; i < searchResult.size(); i++) 
			this.selectMap[i] = false;
	 }
	/**
	 * Creates the form with a result table and function buttons
	 * @return root content panel
	 */
	 public JPanel showFormContent() 
	 {
		JPanel root = getRootPanel(); //inherited	

		if (this.searchResult.isEmpty() && this.browseType == BROWSE_RESULTS) //overriden bei Search-Form and other, should not be invoked
		{
			 Gui.my_forms[index].setPreferredSize(new Dimension(400, 200));
			 JPanel noresultPanel = getNoResultPanel(Gui.lang.getString("no_matches"),Gui.lang.getString("search_for")+ " '" + searchString + "'"); //inherited
			 root.add(noresultPanel, "1,1,3,1,FULL,FULL");
		 } 
		 else 
		 {
			 int border = 0;
			 double size[][] =
					 {{border,TableLayout.PREFERRED, 
								TableLayout.FILL,
									TableLayout.PREFERRED,
					 					TableLayout.PREFERRED,
					 					TableLayout.PREFERRED,
					 						TableLayout.PREFERRED, border}, // Columns
					  {border, TableLayout.PREFERRED, 
									TableLayout.FILL,
					  					TableLayout.PREFERRED,
											TableLayout.PREFERRED, border}}; // Rows
			TableLayout tl = new TableLayout(size);
			tl.setHGap(5);
			tl.setVGap(5);
			 JPanel panel = new JPanel(tl);
			 panel.setBorder
					 (BorderFactory.createTitledBorder(
							 BorderFactory.createEtchedBorder(BevelBorder.LOWERED), Gui.lang.getString("search_for")+ " '" + searchString + "'"));
			
			
			//TODO: Should not be placed in constructor, cause of update problems
			if(this.results.length==0)
					//sortResultArray();
				this.results = Gui.dispatcher.sort(MetaData.DC_TITLE,searchResult, Gui.myLib).toArray();
			
			//String[] columnNames = Gui.lang.getStringArray("column_array");
			
			short [] list = MetaData.getAttributeSequenceList();
			
			tableData = new Object[results.length][list.length+1];
			String [] toolTips = new String[list.length+1];
			if(selectMap==null)
			{
				System.out.println("selectMap may be not initializied");

			}
			 for (int i = 0; i < results.length; i++) 
			 {
				 	MetaData tempMD = ((LibItem) results[i]).getMetaData();		

					tableData[i][0] = new Boolean(selectMap[i]);
					toolTips[0] = Gui.lang.getString("rt_tt_check");
					 for(int j=1;j<list.length+1;j++)
					 {
						 switch(list[j-1])
						 {
							case MetaData.DC_IDENTIFIER : 	tableData[i][j] = tempMD.getDc_identifier();
															toolTips[j] = Gui.lang.getString("rt_tt_identifier");
															break;											
							case MetaData.DC_TITLE : 		tableData[i][j] = tempMD.getDc_title();
															toolTips[j] = Gui.lang.getString("rt_tt_title");
															break; 											
							case MetaData.DC_CREATOR : 		tableData[i][j] = tempMD.getDc_creator();
															toolTips[j] = Gui.lang.getString("rt_tt_author");
															break; 											
							case MetaData.DC_SUBJECT : 		tableData[i][j] = tempMD.getDc_subject();
															toolTips[j] = Gui.lang.getString("rt_tt_subject");
															break; 											
							case MetaData.DC_DESCRIPTION :  tableData[i][j] = tempMD.getDc_description();
															toolTips[j] = Gui.lang.getString("rt_tt_description");
															break; 											
							case MetaData.DC_PUBLISHER : 	tableData[i][j] = tempMD.getDc_publisher();
															toolTips[j] = Gui.lang.getString("rt_tt_publisher");
															break; 											
							case MetaData.DC_CONTRIBUTORS : tableData[i][j] = tempMD.getDc_contributors();
															toolTips[j] = Gui.lang.getString("rt_tt_contributors");
															break; 											
							case MetaData.DC_DATE : 		if(tempMD.getDc_date()!=null)														
															{
																tableData[i][j] = MetaData.convertDate((Date)tempMD.getDc_date());
															}
															else 
															{
																tableData[i][j] = new String("n/a");
															}
															toolTips[j] = Gui.lang.getString("rt_tt_date");
															break;  										
							case MetaData.DC_TYPE : 		tableData[i][j] = Gui.lang.getString(tempMD.getDc_type());
															toolTips[j] = Gui.lang.getString("rt_tt_type");
															break;  
							case MetaData.DC_FORMAT : 		tableData[i][j] = tempMD.getDc_format();
															toolTips[j] = Gui.lang.getString("rt_tt_format");
															break;  
							case MetaData.DC_SOURCE : 		tableData[i][j] = tempMD.getDc_source();
															toolTips[j] = Gui.lang.getString("rt_tt_source");
															break;  
							case MetaData.DC_LANGUAGE :		tableData[i][j] = tempMD.getDc_language();
															toolTips[j] = Gui.lang.getString("rt_tt_language");
															break; 
							case MetaData.DC_RELATION :  	tableData[i][j] = tempMD.getDc_relation();
															toolTips[j] = Gui.lang.getString("rt_tt_relation");
															break; 
							case MetaData.DC_COVERAGE : 	tableData[i][j] = tempMD.getDc_coverage();
															toolTips[j] = Gui.lang.getString("rt_tt_coverage");
															break; 
							case MetaData.DC_RIGHTS : 		tableData[i][j] = tempMD.getDc_rights();
															toolTips[j] = Gui.lang.getString("rt_tt_rights");
															break; 
							case MetaData.LIABOLO_BRANCH : 	tableData[i][j] = tempMD.getLiabolo_branch();
															toolTips[j] = Gui.lang.getString("rt_tt_branch");
															break;  
							case MetaData.LIABOLO_SIGNATURE : tableData[i][j] = tempMD.getLiabolo_signature();
															toolTips[j] = Gui.lang.getString("rt_tt_signature");
															break;  											
						}
					}					
			 }

			 // Show Statistics
			 /*
			 int displayed;
			 if (results.length < 100)
				 displayed = results.length;
			 else
				 displayed = 100;
			*/

			 
			 /*
			String [] columnNames = new String[list.length+1];
			for(int k=0; k<columnNames.length;k++)
			{
				columnNames[k] = new String("-");
			}
			*/
			//
			
			boolean noItems = this.searchResult.isEmpty();
			
			if (noItems && this.browseType != BROWSE_RESULTS)
			{	
				//this.browseType = BROWSE_CLIPBOARD;
				//showEditedOnly.setSelected(false);
				JLabel stats = new JLabel(Gui.lang.getString("no_entries"));
				stats.setFont(new Font("Verdana",Font.BOLD,12));
				JPanel noresultPanel = new JPanel(new FlowLayout());
				noresultPanel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				noresultPanel.setPreferredSize(new Dimension(700, 320));
				noresultPanel.add(stats);
				panel.add(noresultPanel, "1,1,6,2,FULL,FULL");
				
			}
			else
			{		
				String stat_string = (Gui.lang.getString("results")+ " : "+ results.length); 
				JLabel stats = FormElement.getInfiniteLabel(stat_string, Font.PLAIN, null);
				panel.add(stats, "1,1,5,1,LEFT,CENTER");
	
				resultTable = new JTable(new ResultTableModel(tableData));		
				resultTable.setColumnModel(getColumnModel());
				resultTable.setDefaultRenderer(Object.class, new MyRenderer());
				resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				resultTable.addMouseListener(this); 
				
				 
				//Mouselistener for table header
				MouseListener mouseListener = new MouseAdapter() {	
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) {
							TableColumnModel colModel = resultTable.getColumnModel();
							int vColIndex = colModel.getColumnIndexAtX(e.getX());
							sortedColumn = vColIndex;
							sortResultArray(true);
						 }
					}
				};
		
				JTableHeader header = resultTable.getTableHeader();
				ColumnHeaderToolTips tips = new ColumnHeaderToolTips();
	   
				 // Assign a tooltip for each of the columns
				  for (int c=0; c<resultTable.getColumnCount(); c++) {
					  TableColumn col = resultTable.getColumnModel().getColumn(c);
					  tips.setToolTip(col, toolTips[c]);
				  }
				  
				header.addMouseMotionListener(tips);	  
				header.addMouseListener(mouseListener);
	
				resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
				JScrollPane sp = new JScrollPane(resultTable);
				sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				sp.setPreferredSize(new Dimension(700, 300));
	
				panel.add(sp, "1,2,6,2,FULL,FULL");
			
			}// else
			
			
			//System.out.println("TYPE:"+browseType);
			
			//System.out.println("RESULTSIZE:"+searchResult.size());
			
			if(selectAll==null)
			{
				selectAll = new JCheckBox(Gui.lang.getString("select_all"));
				selectAll.setToolTipText(Gui.lang.getString("select_all"));
				selectAll.setActionCommand("select_all");
				selectAll.addActionListener(this);
				if(noItems)
					selectAll.setEnabled(false);
			} 

			panel.add(selectAll, "1,3,1,3,LEFT,CENTER");
			
			if(this.browseType != BROWSE_RESULTS)
			{
				panel.add(showEditedOnly, "1,4,1,4,LEFT,CENTER");
				
				if(noItems && this.browseType == BROWSE_CLIPBOARD)
					showEditedOnly.setEnabled(false);
			}
	
			if(this.browseType == BROWSE_RESULTS)
			{
				JButton newMeta = FormElement.getButton("new", "images/book.png", null, null, false);
				newMeta.setActionCommand("newMeta");
				newMeta.addActionListener(this);
				panel.add(newMeta, "4,3,4,3,FULL,CENTER");				
			}
			else
			{
				
				JButton update_button = FormElement.getButton("update_cb", "images/update.png", null, null, false);
				update_button.setActionCommand("update_cb");
				update_button.addActionListener(this);
				panel.add(update_button, "3,3,3,3,FULL,CENTER");	
				
				if(this.browseType == BROWSE_EDITED_CLIPBOARD || noItems)
				{
					update_button.setEnabled(false);
				}
				

				JButton commit_button = FormElement.getButton("commit_cb", "images/commit.gif", null, null, false);
				commit_button.setActionCommand("commit_cb");
				commit_button.addActionListener(this);
				panel.add(commit_button, "3,4,3,4,FULL,CENTER");
				
				if(this.browseType != BROWSE_EDITED_CLIPBOARD || noItems)
				{
					commit_button.setEnabled(false);
				}
				
				
				JButton clear_button = FormElement.getButton("clear_cb", "images/clear.png", null, null, false);
				clear_button.setActionCommand("clear_cb");
				clear_button.addActionListener(this);
				panel.add(clear_button, "4,3,4,3,FULL,CENTER");	
				if(this.browseType != BROWSE_CLIPBOARD || noItems)
				{
					clear_button.setEnabled(false);
				}
									
			}
			
				
			JButton edit_button = FormElement.getButton("edit", "images/edit.png", null, null, false);
			edit_button.setActionCommand("edit");
			edit_button.addActionListener(this);
			panel.add(edit_button, "5,3,5,3,FULL,CENTER");

			JButton delete_button = FormElement.getButton("delete", "images/delete.png", null, null, false);
			delete_button.setActionCommand("delete");
			delete_button.addActionListener(this);
			panel.add(delete_button, "6,3,6,3,FULL,CENTER");
			
			if(this.browseType == BROWSE_RESULTS)
			{
				JButton checkout_button = FormElement.getButton("checkout", "images/checkout2.png", null, null, true);
				checkout_button.setActionCommand("checkout");
				checkout_button.addActionListener(this);
				panel.add(checkout_button, "3,4,3,4,FULL,CENTER");	
			}
				
			JButton mylist_button = FormElement.getButton("tolist", "images/attach.png", null, null, false);
			mylist_button.setActionCommand("tolist");
			mylist_button.addActionListener(this);
			panel.add(mylist_button, "4,4,4,4,FULL,CENTER");	
			
			JButton referrence = FormElement.getButton("referrence", "images/refer.png", null, null, false);
			referrence.setActionCommand("refer");
			referrence.addActionListener(this);
			panel.add(referrence, "5,4,5,4,FULL,CENTER");	
			
			JButton print = FormElement.getButton("print_preview", "images/print_preview.png", null, new Insets(2,2,2,2), false);
			print.setActionCommand("print_preview");
			print.addActionListener(this);
			panel.add(print, "6,4,6,4,FULL,CENTER");	
			
			if(noItems)
			{
				delete_button.setEnabled(false);
				edit_button.setEnabled(false);
				mylist_button.setEnabled(false);
				referrence.setEnabled(false);
				print.setEnabled(false);
			}
				
		
			root.add(panel, "1,1,3,1,FULL,FULL");
		 }
		 //Ok-Button
		 JButton ok_button = FormElement.getButton("close", "images/ok.png", null, null, false);
		 ok_button.setActionCommand("result_cancel");
		 ok_button.addActionListener(this);
		 root.add(ok_button, "1,2,3,2,CENTER,CENTER");

		 return root;
	 }
	 
	/**
	 * Returns the table model with desired column attributes and header titles
	 * @return 
	 */
	private TableColumnModel getColumnModel()
	{
		DefaultTableColumnModel model = new DefaultTableColumnModel();
		TableColumn column = new TableColumn(0,20);
		column.setHeaderValue("#");
		model.addColumn(column); 	
		short [] list = MetaData.getAttributeSequenceList();
		 for(int i=0;i<list.length;i++)
		 {
			 switch(list[i])
			 {
				case MetaData.DC_IDENTIFIER : 	column = new TableColumn(i+1,70);
												column.setHeaderValue(Gui.lang.getString("identifier"));
												break;											
				case MetaData.DC_TITLE : 		column = new TableColumn(i+1,200);
												column.setHeaderValue(Gui.lang.getString("title"));
												break; 											
				case MetaData.DC_CREATOR : 		column = new TableColumn(i+1,150);
												column.setHeaderValue(Gui.lang.getString("creator"));
												break; 											
				case MetaData.DC_SUBJECT : 		column = new TableColumn(i+1,100);
												column.setHeaderValue(Gui.lang.getString("subject"));
												break; 											
				case MetaData.DC_DESCRIPTION :  column = new TableColumn(i+1,200);
												column.setHeaderValue(Gui.lang.getString("description"));
												break; 											
				case MetaData.DC_PUBLISHER : 	column = new TableColumn(i+1,100);
												column.setHeaderValue(Gui.lang.getString("publisher"));
												break; 											
				case MetaData.DC_CONTRIBUTORS : column = new TableColumn(i+1,100);
												column.setHeaderValue(Gui.lang.getString("contributors"));
												break; 											
				case MetaData.DC_DATE : 		column = new TableColumn(i+1,40);
												column.setHeaderValue(Gui.lang.getString("date"));
												break;  										
				case MetaData.DC_TYPE : 		column = new TableColumn(i+1,60);
												column.setHeaderValue(Gui.lang.getString("type"));
												break;  
				case MetaData.DC_FORMAT : 		column = new TableColumn(i+1,100);
												column.setHeaderValue(Gui.lang.getString("format"));
												break;  
				case MetaData.DC_SOURCE : 		column = new TableColumn(i+1,100);
												column.setHeaderValue(Gui.lang.getString("source"));
												break;  
				case MetaData.DC_LANGUAGE :		column = new TableColumn(i+1,60);
												column.setHeaderValue(Gui.lang.getString("language"));
												break; 
				case MetaData.DC_RELATION :  	column = new TableColumn(i+1,100);
												column.setHeaderValue(Gui.lang.getString("relation"));
												break; 
				case MetaData.DC_COVERAGE : 	column = new TableColumn(i+1,100);
												column.setHeaderValue(Gui.lang.getString("coverage"));
												break; 
				case MetaData.DC_RIGHTS : 		column = new TableColumn(i+1,100);
												column.setHeaderValue(Gui.lang.getString("rights"));
												break; 
				case MetaData.LIABOLO_BRANCH : 	column = new TableColumn(i+1,100);
												column.setHeaderValue(Gui.lang.getString("branch"));
												break;  
				case MetaData.LIABOLO_SIGNATURE : column = new TableColumn(i+1,120);
												column.setHeaderValue(Gui.lang.getString("signature"));
												break;  
	 			}
			model.addColumn(column); 		
 			}
		return model;	
	}
	/**
	 * Sorts the result collection by desired column. See also this.sortedColumn
	 *
	 */
	private void sortResultArray(boolean update)
	{		
		switch(this.sortedColumn)
		{		
			case 0 :selectAll.setSelected(!selectAll.isSelected());
					selectAll();
					break;
					
			case 1 : this.results = Gui.dispatcher.sort(MetaData.DC_CREATOR,searchResult, Gui.myLib).toArray();
					break;
			case 2 :this.results = Gui.dispatcher.sort(MetaData.DC_TITLE,searchResult, Gui.myLib).toArray();
					break;
			case 3:this.results = Gui.dispatcher.sortSignature(searchResult, Gui.myLib).toArray();
					break;
			case 4:this.results = Gui.dispatcher.sort(MetaData.DC_COVERAGE,searchResult, Gui.myLib).toArray();
					break;
			case 5 :this.results = Gui.dispatcher.sort(MetaData.DC_SUBJECT,searchResult, Gui.myLib).toArray();
					break;    
			case 6:this.results = Gui.dispatcher.sort(MetaData.DC_DESCRIPTION,searchResult, Gui.myLib).toArray();
					break;
			case 7 :this.results = Gui.dispatcher.sort(MetaData.DC_DATE,searchResult, Gui.myLib).toArray();
					break;
			case 8:this.results = Gui.dispatcher.sort(MetaData.DC_PUBLISHER,searchResult, Gui.myLib).toArray();
					break;
			case 9:this.results = Gui.dispatcher.sort(MetaData.DC_IDENTIFIER,searchResult, Gui.myLib).toArray();
					break;
			case 10:this.results = Gui.dispatcher.sort(MetaData.DC_SOURCE,searchResult, Gui.myLib).toArray();
					break;
			case 11:this.results = Gui.dispatcher.sort(MetaData.DC_LANGUAGE,searchResult, Gui.myLib).toArray();
					break;
			case 12:this.results = Gui.dispatcher.sort(MetaData.DC_RELATION,searchResult, Gui.myLib).toArray();
					break;
			case 13:this.results = Gui.dispatcher.sort(MetaData.DC_RIGHTS,searchResult, Gui.myLib).toArray();
					break;
			case 14:this.results = Gui.dispatcher.sort(MetaData.DC_TYPE,searchResult, Gui.myLib).toArray();
					break;
			case 15:
					break;
			case 16:this.results = Gui.dispatcher.sort(MetaData.DC_CONTRIBUTORS,searchResult, Gui.myLib).toArray();
					break;
			case 17:this.results = Gui.dispatcher.sort(MetaData.LIABOLO_BRANCH,searchResult, Gui.myLib).toArray();
					break;
			//default : this.results = Gui.dispatcher.sort(MetaData.DC_TITLE,searchResult, Gui.myLib).toArray();
		}
		
		if(this.sortedColumn>0 && update)
		{
			Point location = Gui.my_forms[index].getLocation();
			this.updateFormFrame(location);			
		}
		

	}
	
	/**
	 * Makes an visual update of the edited row
	 * @param metadata Edited metadata set
	 * @param update_index	edited row in the table
	 */
	public void updateEditedEntry(MetaData metadata, int update_index)
	{
		short [] list = MetaData.getAttributeSequenceList();
		for(int j=1;j<list.length+1;j++)
		{
			switch(list[j-1])
			{
			   case MetaData.DC_IDENTIFIER : 	resultTable.setValueAt(metadata.getDc_identifier(),update_index,j);
											   break;											
			   case MetaData.DC_TITLE : 		resultTable.setValueAt(metadata.getDc_title(),update_index,j);;
											   break; 											
			   case MetaData.DC_CREATOR : 		resultTable.setValueAt(metadata.getDc_creator(),update_index,j);;
											   break; 											
			   case MetaData.DC_SUBJECT : 		resultTable.setValueAt(metadata.getDc_subject(),update_index,j);;
											   break; 											
			   case MetaData.DC_DESCRIPTION :  resultTable.setValueAt(metadata.getDc_description(),update_index,j);;
											   break; 											
			   case MetaData.DC_PUBLISHER : 	resultTable.setValueAt(metadata.getDc_publisher(),update_index,j);;
											   break; 											
			   case MetaData.DC_CONTRIBUTORS : resultTable.setValueAt(metadata.getDc_contributors(),update_index,j);;
											   break; 											
			   case MetaData.DC_DATE : 		if(metadata.getDc_date()!=null)														
											   {
												resultTable.setValueAt(MetaData.convertDate((Date)metadata.getDc_date()),update_index,j);
											   }
											   else 
											   {
												resultTable.setValueAt(new String("n/a"),update_index,j);;
											   }
															
											   break;  										
			   case MetaData.DC_TYPE : 		   resultTable.setValueAt(metadata.getDc_type(),update_index,j);;
											   break;  
			   case MetaData.DC_FORMAT : 	   resultTable.setValueAt(metadata.getDc_format(),update_index,j);;
											   break;  
			   case MetaData.DC_SOURCE : 	   resultTable.setValueAt(metadata.getDc_source(),update_index,j);;
											   break;  
			   case MetaData.DC_LANGUAGE :	   resultTable.setValueAt(metadata.getDc_language(),update_index,j);;
											   break; 
			   case MetaData.DC_RELATION :     resultTable.setValueAt(metadata.getDc_relation(),update_index,j);;
											   break; 
			   case MetaData.DC_COVERAGE : 	   resultTable.setValueAt(metadata.getDc_coverage(),update_index,j);;
											   break; 
			   case MetaData.DC_RIGHTS : 	   resultTable.setValueAt(metadata.getDc_rights(),update_index,j);;
											   break; 
			   case MetaData.LIABOLO_BRANCH : resultTable.setValueAt(metadata.getLiabolo_branch(),update_index,j);;
											   break;  
			   case MetaData.LIABOLO_SIGNATURE : resultTable.setValueAt(metadata.getLiabolo_signature(),update_index,j);
											   break;  											
		   }
	   }	
//		Gui.statusBar.setReceiverMessage("Gui: Update successful!");
	}
	
	/**
	 * Removes selected entries from  database
	 * @param selected Is TRUE if some check boxes are checked
	 * @param selectedRow  Selected row of the table, is used only if selected = FALSE
	 */
	private void deleteItems(boolean selected, int selectedRow)
	{
		int confirm_dialog =
				JOptionPane.showConfirmDialog(
					null,
						Gui.lang.getString("delete_items"),
							Gui.lang.getString("deleting"),
								JOptionPane.YES_NO_OPTION);
			if (confirm_dialog == JOptionPane.YES_OPTION) 
			{
				TableModel model = resultTable.getModel();
				Collection fitedResults = new HashSet();
				for(int i=0; i<results.length;i++) 
				{
					if(selected)	//some items are checked, delete them
					{
						Boolean checked = (Boolean)model.getValueAt(i,0);
						if(Boolean.FALSE.equals(checked))
						{
							fitedResults.add(results[i]);	
						}
						else
						{
							LibItem  delItem = (LibItem) results[i];
							Gui.dispatcher.removeLibItem(delItem);
							// TODO System.out.println("gerade am l�schen SELECTED:"+((LibItem)results[i]).getMetaData().getDc_title()+" Zeile");
						}		
					}
					else // no checked items, delete only selected row
					{	
						if((selectedRow>=0 && results[i]!=results[selectedRow]))
						{
							fitedResults.add(results[i]);
							
						}
						else
						{
							LibItem  delItem = (LibItem) results[i];
							Gui.dispatcher.removeLibItem(delItem);
							// TODO System.out.println("gerade am l�schen SINGLESELECTED:"+((LibItem)results[i]).getMetaData().getDc_title()+" Zeile");
						}		
					}
				}
				if (Gui.automaticUpdate) {
					WaitDialog.showDialog();
				//	Location loc = (Location)locationList[locations.getSelectedIndex()];
				//	TreeForm.updateTreeForm(4,(Branch) branchList[branches.getSelectedIndex() - 1],loc);
					WaitDialog.closeDialog();
					try {
						this.setSelected(true);
					} catch (PropertyVetoException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				this.searchResult=fitedResults;
				if(searchResult.size()>0 || this.browseType!=BROWSE_RESULTS )
				{
					initSelectionMap();
					sortResultArray(false); // Sortiert und initialisiert neue results []
					Point location = Gui.my_forms[index].getLocation();
					this.updateFormFrame(location);
						
				}
				else
				{
					this.selectMap = null;
					Gui.my_forms[index].dispose();
					JOptionPane.showMessageDialog(
						Gui.modalParent,
							Gui.lang.getString("no_matches"),
								Gui.lang.getString("warning"),
									JOptionPane.CANCEL_OPTION);	
				}
			}
	}
	
	/**
	 * Collects selected entries(metadata sets) and checks them out into local repository
	 * @param selected Is TRUE if some check boxes are checked
	 * @param selectedRow Selected row of the table, is used only if selected = FALSE
	 */
	private void checkoutItems(boolean selected, int selectedRow)
	{
		TableModel model = resultTable.getModel();
		Collection checkoutResults = new HashSet();
		for(int i=0; i<results.length;i++) 
		{
			if(selected)	//some items are checked, delete them
			{
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
				{
					checkoutResults.add(results[i]);	
				}	
			}
			else // no checked items, delete only selected row
			{	
				if((selectedRow>=0 && results[i]==results[selectedRow]))
				{
					checkoutResults.add(results[i]);
							
				}	
			}
		}
		if(checkoutResults.size()>0)
		{
			Gui.dispatcher.checkoutUnsortedLibItems(checkoutResults, true);	
			int index = Configurator.getIntProperty("browseClipboard",0,"gui-forms");
			DefaultForm form=Gui.getForm(index);
			if(form!=null && !form.isClosed())
			{
				Point location = form.getLocation();
				int browse_typ = ((BrowseMetadataResults)form).browseType;
				form.dispose();
				BrowseMetadataResults clipboardResults = new BrowseMetadataResults(index, Gui.lang.getString("clipboard"), location);
			}

		}
		
	}
		
	/**
	 * Collects selected entries(metadata sets) and available individual lists to 
	 * delegate them to AddToList.java
	 * @param lists available individual lists
	 * @param selected Is TRUE if some check boxes are checked
	 * @param selectedRow Selected row of the table, is used only if selected = FALSE
	 */
	private void addItemsToList(Collection lists,boolean selected, int selectedRow)
	{
		//System.out.println("selected:"+selected+"/selectedrow"+selectedRow);
		TableModel model = resultTable.getModel();
		Collection itemsToAdd = new HashSet();
		for(int i=0; i<results.length;i++) 
		{
			if(selected)	//some items are checked, delete them
			{
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
				{
					itemsToAdd.add(results[i]);	
				}
	
			}
			else // no checked items, delete only selected row
			{	
				if((selectedRow>=0 && results[i]==results[selectedRow]))
				{
					itemsToAdd.add(results[i]);
				}	
			}
		}
		AddToList tolist = new AddToList(lists,itemsToAdd, null);
	}
	
	private void printItems(boolean selected, int selectedRow)
	{
		TableModel model = resultTable.getModel();
		ArrayList viewResults = new ArrayList();
		for(int i=0; i<results.length;i++) 
		{
			if(selected)	//some items are checked, edd them to preview collection
			{
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
				{
					viewResults.add(results[i]);	
				}	
			}
			else // no checked items, delete only selected row
			{	
				if((selectedRow>=0 && results[i]==results[selectedRow]))
				{
					viewResults.add(results[i]);
				}
			}
		}
		
		Point printFormLocation = null;
		
		int printIndex = Configurator.getIntProperty("printMetadata",0,"gui-forms");
		JInternalFrame form=Gui.getForm(printIndex);
		if(form!=null && !form.isClosed())
		{
				int switch_frame =
					JOptionPane.showConfirmDialog(
						Gui.modalParent,
						Gui.lang.getString("md_print_opened"),
						Gui.lang.getString("warning"),
						JOptionPane.YES_NO_OPTION);
				if (switch_frame == JOptionPane.YES_OPTION) 
				{
					printFormLocation = form.getLocation();
					form.dispose();			
				}
				else
				{
					try 
					{
						form.setSelected(true);
					} 
					catch (java.beans.PropertyVetoException ev) 
					{}	
				}
		}	
		
		PrintPreview printPreview = new PrintPreview(printIndex, viewResults, printFormLocation);	
	}

	public void actionPerformed(ActionEvent e) 
	{

		String actionCmd = e.getActionCommand();
		
			
		if (actionCmd.equals("result_cancel")) 
		{
			closeForm();
		}
		
		if(actionCmd.equals("newMeta"))
		{	
			int index = Configurator.getIntProperty("newMetadata",0,"gui-forms");
			DefaultForm form=Gui.getForm(index);
			if(form!=null && !form.isClosed())
			{
				form.pack();
				try 
				{
					form.setSelected(true);
				} 
				catch (java.beans.PropertyVetoException ev) 
				{}	
			}
			else 
			{
				MetadataForm newMetadata = new MetadataForm(index,null);
			}		
		}
		
		if (actionCmd.equals("checkout")) 
		{	
			boolean selected = false;
			for(int i=0; i<results.length;i++) 
			{
				TableModel model = resultTable.getModel();
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
					selected=true;
			}
			int selectedRow = resultTable.getSelectedRow();
			if(selected || (!selected && selectedRow>=0))
				checkoutItems(selected,selectedRow);
			else
			{
				showNoSectionMessage();			
			}
		}	
		
		if(actionCmd.equals("refer"))
		{	
			int selectedRow = resultTable.getSelectedRow();
			if(selectedRow>=0)
			{
				String relation = ((LibItem)results[selectedRow]).getMetaData().getLiabolo_signature();
				int newMetadataIndex = Configurator.getIntProperty("newMetadata",0,"gui-forms");
				if(Gui.my_forms[newMetadataIndex]!=null && !Gui.my_forms[newMetadataIndex].isClosed())
				{
					((MetadataForm)Gui.my_forms[newMetadataIndex]).setReferrence(relation);

				}
				else
				{
					int switch_frame = JOptionPane.showConfirmDialog(
										Gui.modalParent,
											Gui.lang.getString("addMD_not_opened"),
												Gui.lang.getString("warning"),
													JOptionPane.YES_NO_OPTION);	
					if (switch_frame == JOptionPane.YES_OPTION) 
					{
						
						MetadataForm addMetdadata = new MetadataForm(newMetadataIndex, relation,  null);
					}	
				}
			}
			else
			{
				JOptionPane.showMessageDialog(
					Gui.modalParent,
						Gui.lang.getString("no_refer_selection"),
							Gui.lang.getString("warning"),
								JOptionPane.CANCEL_OPTION);
					
			}
		}	

		if (actionCmd.equals("print_preview")) 
		{
			boolean selected = false;
			for(int i=0; i<results.length;i++) 
			{
				TableModel model = resultTable.getModel();
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
					selected=true;
			}
			int selectedRow = resultTable.getSelectedRow();
			if(selected || (!selected && selectedRow>=0))
				printItems(selected,selectedRow);
			else
			{
				JOptionPane.showMessageDialog(
					Gui.modalParent,
						Gui.lang.getString("no_preview_selection"),
							Gui.lang.getString("warning"),
								JOptionPane.CANCEL_OPTION);
					
			}
		}
		
		

		if (actionCmd.equals("edit")) 
		{
			int selectedRow  = resultTable.getSelectedRow();
			if(selectedRow>=0)
			{
				MetaData metaData = ((LibItem) results[selectedRow]).getMetaData();
				int editMetadataIndex = Configurator.getIntProperty("editMetadata",0,"gui-forms");
				JInternalFrame form=Gui.getForm(editMetadataIndex);
				if(form!=null && !form.isClosed())
				{
						int switch_frame =
							JOptionPane.showConfirmDialog(
								Gui.modalParent,
								Gui.lang.getString("md_edit_opened"),
								Gui.lang.getString("warning"),
								JOptionPane.YES_NO_OPTION);
						if (switch_frame == JOptionPane.YES_OPTION) 
						{
							form.dispose();	
							MetadataForm editForm = new MetadataForm(editMetadataIndex,this,selectedRow,metaData, null);					
						}
						else
						{
							try 
							{
								form.setSelected(true);
							} 
							catch (java.beans.PropertyVetoException ev) 
							{}	
						}
				}
				else 
				{
					MetadataForm editForm = new MetadataForm(editMetadataIndex,this,selectedRow,metaData, null);
				}		
			}//if(selectedRow>=0)
			else
			{
				JOptionPane.showMessageDialog(
					Gui.modalParent,
						Gui.lang.getString("no_edit_selection"),
							Gui.lang.getString("warning"),
								JOptionPane.CANCEL_OPTION);				
			}
		}
		
		if(actionCmd.equals("delete"))
		{	
			boolean selected = false;
			for(int i=0; i<results.length;i++) 
			{
				TableModel model = resultTable.getModel();
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
					selected=true;
			}
			int selectedRow = resultTable.getSelectedRow();
			if(selected || (!selected && selectedRow>=0))
			{
				deleteItems(selected,selectedRow);
				if (Gui.automaticUpdate) 
				{
					WaitDialog.showDialog();
	            	//Location loc = (Location)locationList[locations.getSelectedIndex()];
	                TreeForm.updateTreeForm(0,null,null);
	               
	                try 
					{
						this.setSelected(true);
					} catch (PropertyVetoException e1) 
					{
						e1.printStackTrace();
					}
					 WaitDialog.closeDialog();
				}
			}
			else
			{
				JOptionPane.showMessageDialog(
					Gui.modalParent,
						Gui.lang.getString("no_delete_selection"),
							Gui.lang.getString("warning"),
								JOptionPane.CANCEL_OPTION);
					
			}
		}	
		
		if(actionCmd.equals("tolist"))
		{	
			boolean selected = false;
			for(int i=0; i<results.length;i++) 
			{
				TableModel model = resultTable.getModel();
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
					selected=true;
			}
			int selectedRow = resultTable.getSelectedRow();
			if(selected || (!selected && selectedRow>=0))
			{
				Collection myLists = Gui.dispatcher.getAllIndividualLists();
				if(myLists.size()!=0)
				{
					//AddToList tolist = new AddToList(myLists);
					addItemsToList(myLists, selected, selectedRow);
					
				}
				else
				{
					int switch_frame = getAddListDialog();
					if (switch_frame == JOptionPane.YES_OPTION) 
					{
						int index = Configurator.getIntProperty("newList",0,"gui-forms");
						DefaultForm form=Gui.getForm(index);
						if(form!=null && !form.isClosed())
						{
							form.pack();
							try
							{
								form.setSelected(true);
							}
							catch (java.beans.PropertyVetoException ev)
							{}
						}
						else
						{
							ListForm listForm = new ListForm(index,null);
						}
					}					
				}
					
			}
			else
			{
				JOptionPane.showMessageDialog(
					Gui.modalParent,
						Gui.lang.getString("no_tolist_selection"),
							Gui.lang.getString("warning"),
								JOptionPane.CANCEL_OPTION);			
			}
				
		}	
		
		if(actionCmd.equals("clear_cb"))
		{
			int switchOption = getDeleteItemsDialog();
			if(switchOption==JOptionPane.OK_OPTION)
			{
				boolean clean= Gui.dispatcher.clearWorkspace();
				this.searchResult.clear();
				Point location = Gui.my_forms[index].getLocation();
				this.updateFormFrame(location);
				if(clean)
				{
					showActionSuccess();
				}
			}

		}
		
		if(actionCmd.equals("commit_cb"))
		{
			Collection conflictItems = Gui.dispatcher.commitChangesForEditedLibItems(Gui.dispatcher.getLibItemsEditedFromWorkspace());
			if(conflictItems.size()>0)
			{
				ConflictManager conflictManager = new ConflictManager(conflictItems);
			}
			else
			{
				showActionSuccess();
			}
				
		}
		
		if(actionCmd.equals("update_cb"))
		{
			Gui.dispatcher.updateWorkspace(true);
			showActionSuccess();
		}

				
		if (actionCmd.equals("select_all")) 
		{
			selectAll();
		}	
		
		if (actionCmd.equals("select_all_pop")) 
		{
			selectAllPop();
		}				
		
		if (actionCmd.equals("show_edited_only")) 
		{
			showEditedOnly();
		}			
	}
	
	/** Handles select all actions on popup menu */
	private void selectAllPop()
  	{
		if(!selectAll.isSelected())
			for(int i=0;i<results.length;i++)
			{
				selectMap[i] = true;
				resultTable.setValueAt(new Boolean(true),i,0);
			}
		else
			for(int i=0;i<results.length;i++)
			{
				selectMap[i] = false;
				resultTable.setValueAt(new Boolean(false),i,0);
			}
		selectAll.setSelected(!selectAll.isSelected());
  	}
  	
  	/** Handles actions on select all check box */
	private void selectAll()
	{
		if(selectAll.isSelected())
			for(int i=0;i<results.length;i++)
			{
				selectMap[i] = true;
				resultTable.setValueAt(new Boolean(true),i,0);
			}
		else
			for(int i=0;i<results.length;i++)
			{
				selectMap[i] = false;
				resultTable.setValueAt(new Boolean(false),i,0);
			}
	}
	
	/**
	 * Handles actions on showEditedOnly checkbox.
	 * Switches between BROWSE_EDITED_CLIPBOARD and BROWSE_CLIPBOARD browse types

	 */
	private void showEditedOnly()
	{
		if(showEditedOnly.isSelected())
		{
			//System.out.println("BROWSE_EDITED_CLIPBOARD");
			this.searchResult=Gui.dispatcher.getLibItemsEditedFromWorkspace();
			this.initSelectionMap();
			this.sortResultArray(false);		
			this.browseType = BROWSE_EDITED_CLIPBOARD;
			Point location = Gui.my_forms[index].getLocation();
			this.updateFormFrame(location);
		}
		else
		{
			//System.out.println("BROWSE_CLIPBOARD");
			this.searchResult=Gui.dispatcher.listWorkspace();
			this.initSelectionMap();
			this.sortResultArray(false);		
			this.browseType = BROWSE_CLIPBOARD;
			Point location = Gui.my_forms[index].getLocation();
			this.updateFormFrame(location);

		}

	
	}
  /**
   * 
   * @author Jurij Henne
   * The class adds a mouse listener to each column header of the result table
   */
  	
	private class ColumnHeaderToolTips extends MouseMotionAdapter {
		   // Current column whose tooltip is being displayed.
		   // This variable is used to minimize the calls to setToolTipText().
		   TableColumn curCol;
    
		   // Maps TableColumn objects to tooltips
		   Map tips = new HashMap();
    
		   // If tooltip is null, removes any tooltip text.
		   public void setToolTip(TableColumn col, String tooltip) {
			   if (tooltip == null) {
				   tips.remove(col);
			   } else {
				   tips.put(col, tooltip);
			   }
		   }
    
		   public void mouseMoved(MouseEvent evt) {
			   TableColumn col = null;
			   JTableHeader header = (JTableHeader)evt.getSource();
			   JTable table = header.getTable();
			   TableColumnModel colModel = table.getColumnModel();
			   int vColIndex = colModel.getColumnIndexAtX(evt.getX());
    
			   // Return if not clicked on any column header
			   if (vColIndex >= 0) {
				   col = colModel.getColumn(vColIndex);
			   }
    
			   if (col != curCol) {
				   header.setToolTipText((String)tips.get(col));
				   curCol = col;
			   }
		   }
	   }
	  
  /**
   * 
   * @author Jurij Henne
   *
   * The class renders row colors of the resutl table
   */
	private class MyRenderer extends DefaultTableCellRenderer 
	{  
		public Component getTableCellRendererComponent(JTable table, Object value,
		                            boolean isSelected, boolean hasFocus,                                  
		                            int row, int col) 
		{    
			JLabel jl = (JLabel)super.getTableCellRendererComponent(table, value, isSelected,
			                 hasFocus, row, col);    
			jl.setOpaque(true);    
			if(row==table.getSelectedRow()) jl.setBackground(new Color(64,124,156,100));
			else if (row%2 == 0) jl.setBackground(new Color(64,124,156,30));   
			else  jl.setBackground(new Color(255,255,255,255));     
		 return jl;  
		}}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) 
	{
		if(SwingUtilities.isRightMouseButton(e))
		{
			int row = this.resultTable.rowAtPoint(e.getPoint());
			this.resultTable.setRowSelectionInterval(row,row);
			ContextMenu pop = new ContextMenu(this.index, e.getX(),e.getY());
		}
		else if (e.getClickCount() == 2) 
		{
			actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"edit"));
		}
			
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return Returns the resultTable.
	 * 
	 * @uml.property name="resultTable"
	 */
	public JTable getResultTable() {
		return resultTable;
	}

	/**
	 * @return Returns the browseType.
	 * 
	 * @uml.property name="browseType"
	 */
	public int getBrowseType() {
		return browseType;
	}

	//###################################################################################################
	/*
	private static Collection conflictTest(){
		MetaData meta = new MetaData("1000");
		//Das Erste Item
		//alt
		meta.setDc_title("Nummer 1");
		meta.setDc_type("book");
		meta.setDc_creator("alt");
		LibItem item1alt = new LibItem();
		item1alt.setMetaData(meta);
		//alt
		meta = new MetaData("1000");
		meta.setDc_title("Nummer 1");
		meta.setDc_type("book");
		meta.setDc_creator("neu");
		LibItem item1neu = new LibItem();
		item1neu.setMetaData(meta);

		MergingData data1 = new MergingData(item1neu, item1alt);

		//Das zweite Item
		//alt
		meta = new MetaData("2000");
		meta.setDc_title("Nummer 2");
		meta.setDc_type("magazine-article");
		meta.setDc_creator("alt");
		LibItem item2alt = new LibItem();
		item2alt.setMetaData(meta);
		//alt
		meta = new MetaData("2000");
		meta.setDc_title("Nummer 2");
		meta.setDc_type("magazine-article");
		meta.setDc_creator("neu");
		LibItem item2neu = new LibItem();
		item2neu.setMetaData(meta);

		MergingData data2 = new MergingData(item2neu, item2alt);

		//Das dritte Item
		//alt
		meta = new MetaData("3000");
		meta.setDc_title("Nummer 3");
		meta.setDc_type("book-article");
		meta.setDc_creator("alt");
		LibItem item3alt = new LibItem();
		item3alt.setMetaData(meta);
		//alt
		meta = new MetaData("3000");
		meta.setDc_title("Nummer 3");
		meta.setDc_type("book-article");
		meta.setDc_creator("neu");
		LibItem item3neu = new LibItem();
		item3neu.setMetaData(meta);

		MergingData data3 = new MergingData(item3neu, item3alt);

		//create collection
		Collection col = new HashSet();
		col.add(data1);
		col.add(data2);
		col.add(data3);

		return col;
	}
	*/
	//############################################################################################
}
