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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.ContextMenu;
import org.liabolo.client.offline.common.ExportFileFilter;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.common.ResultTableModel;
import org.liabolo.client.offline.common.WaitDialog;
import org.liabolo.common.Configurator;
import org.liabolo.common.IndividualList;

/**
 * @author Jurij Henne
 * 
 * This is an impementation of  "browse individual lists" - form. The user gets a  scrollable list of available individual lists
 * and can perform some usefull actions on selected entries.
 */
public class BrowseList extends DefaultForm implements ActionListener, KeyListener, MouseListener {
	
	/** Indicates if all entries were selected */
	private JCheckBox selectAll;
	/** Available individual lists */
	private Object[] myLists;

	/**
	 * Table, which displays the attributes of the lists
	 * 
	 * @uml.property name="listTable"
	 */
	private JTable listTable;
	
	/** Collection of individuall lists, which should be exported */
	private Collection listsToExport;
	/** For export purposes only */
	private JFileChooser fcs;
	//private int selectedList;
	/** Helps to associate selected filter descrition with its file extension*/
	private String [][] filters;
	/**
	 * Creates a new "browse individual lists" form
	 * @param index specifies a unique 'position' of  the form in form holder-array 
	 * @param formLocation specifies a unique 'position' of  the form in form holder-array 
	 */
	public BrowseList(int BLIndex, Point location)
	{	
		super(BLIndex, Gui.lang.getString("browse_lists"),"images/searchedit.png");
		WaitDialog.showDialog();
		this.myLists = Gui.dispatcher.sortIndividualList(Gui.dispatcher.getAllIndividualLists(),Gui.myLib).toArray();
		WaitDialog.closeDialog();	
		if(myLists.length<1)
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
		else
			this.addFormFrame(location);				
	 }
	 
	/** Implemetation of inherited method. Returns the layouted  content panel of the form */
	public JPanel showFormContent()
	{
		JPanel root = getRootPanel(); //inherited	

		if(myLists.length==0) 
		{
			Gui.my_forms[index].setPreferredSize(new Dimension(300, 200));
			JPanel noresultPanel = getNoResultPanel(Gui.lang.getString("no_lists_avail"),Gui.lang.getString("list_lists")); //inherited
			root.add(noresultPanel, "1,1,3,1,CENTER,FULL");
		} 
		else 
		{
			int border = 3;
			double size[][] =
					{{border,
						TableLayout.PREFERRED,
						TableLayout.PREFERRED,
							TableLayout.PREFERRED,
								TableLayout.PREFERRED, 
									TableLayout.PREFERRED, 
							  border}, // Columns
					 {border,TableLayout.PREFERRED,
								TableLayout.PREFERRED,
								TableLayout.PREFERRED,
									TableLayout.PREFERRED,border}}; // Rows
			TableLayout tl = new TableLayout(size);
			tl.setHGap(border);
			tl.setVGap(border);
			JPanel panel = new JPanel(tl);
			panel.setBorder
					(BorderFactory.createTitledBorder(
							BorderFactory.createEtchedBorder(BevelBorder.LOWERED), Gui.lang.getString("list_lists")));
	
		   Object [][] tableData = new Object[myLists.length][3];

			for (int i = 0; i < myLists.length; i++) 
			{
				tableData[i][0] = new Boolean(false);
				tableData[i][1] = ((IndividualList)myLists[i]).getListName();
				tableData[i][2] = ((IndividualList)myLists[i]).getDescription();
			}

			// Show Statistics
			int displayed;
			if (myLists.length < 100)
				displayed = myLists.length;
			else
				displayed = 100;
				
			String stat_string = (Gui.lang.getString("results")+ " : "+ myLists.length); 
			JLabel stats = FormElement.getInfiniteLabel(stat_string, Font.PLAIN, null);
			panel.add(stats, "1,1,5,1,LEFT,CENTER");

			listTable = new JTable(new ResultTableModel(tableData));
			listTable.setColumnModel(getColumnModel());
			listTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listTable.addMouseListener(this);
			JPanel sroot = new JPanel(new BorderLayout());
			JScrollPane sp = new JScrollPane(listTable);
			sp.setPreferredSize(new Dimension(300, 300));
			sroot.add(sp, BorderLayout.CENTER);
			panel.add(sroot, "1,2,5,2,FULL,TOP");
			 
			selectAll = new JCheckBox(Gui.lang.getString("select_all"));
			selectAll.setToolTipText(Gui.lang.getString("select_all"));
			selectAll.setActionCommand("select_all");
			selectAll.addActionListener(this);
			panel.add(selectAll, "1,3,1,3,LEFT,CENTER");
			
			JButton new_button = FormElement.getButton("new", "images/mylists.png",null, null, true);
			new_button.setActionCommand("new_list");
			new_button.addActionListener(this);
			new_button.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent event)
				{
					if (event.getKeyCode() == KeyEvent.VK_ESCAPE )
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));
					if (event.getKeyCode() == KeyEvent.VK_ENTER)
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"new_list"));
				}
			});
			panel.add(new_button, "3,3,3,3,FULL,CENTER");
			
			JButton content_button = FormElement.getButton("content", "images/content.png","list_content", null, true);
			content_button.setActionCommand("list_content");
			content_button.addActionListener(this);
			content_button.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent event)
				{
					if (event.getKeyCode() == KeyEvent.VK_ESCAPE )
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));
					if (event.getKeyCode() == KeyEvent.VK_ENTER)
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"list_content"));
				}
			});
			panel.add(content_button, "4,3,4,3,FULL,CENTER");

			JButton export_button = FormElement.getButton("export", "images/export.png",null, null, true);
			export_button.setActionCommand("export");
			export_button.addActionListener(this);
			export_button.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent event)
				{
					if (event.getKeyCode() == KeyEvent.VK_ESCAPE )
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));
					if (event.getKeyCode() == KeyEvent.VK_ENTER)
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"export"));
				}
			});
			panel.add(export_button, "4,4,4,4,FULL,CENTER");
			
			JButton edit_button = FormElement.getButton("edit", "images/searchedit.png", null, null, true);
			edit_button.setHorizontalAlignment(SwingConstants.LEFT);
			edit_button.setActionCommand("edit");
			edit_button.addActionListener(this);
			edit_button.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent event)
				{
					if (event.getKeyCode() == KeyEvent.VK_ESCAPE )
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));
					if (event.getKeyCode() == KeyEvent.VK_ENTER)
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"edit"));
				}
			});
			panel.add(edit_button, "5,3,5,3,FULL,CENTER");

			JButton delete_button = FormElement.getButton("delete", "images/delete.png", null, null, true);
			delete_button.setActionCommand("delete");
			delete_button.addActionListener(this);
			delete_button.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent event)
				{
					if (event.getKeyCode() == KeyEvent.VK_ESCAPE )
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));
					if (event.getKeyCode() == KeyEvent.VK_ENTER)
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"delete"));
				}
			});
			panel.add(delete_button, "5,4,5,4,FULL,CENTER");

			root.add(panel, "1,1,3,1,CENTER,CENTER");
		}
		//Ok-Button
		JButton ok_button = FormElement.getButton("close", "images/ok.png", null, null, true);
		ok_button.setActionCommand("cancel");
		ok_button.addActionListener(this);
		ok_button.addKeyListener(this);
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
		
		TableColumn column1 = new TableColumn(0,30);
		column1.setHeaderValue("#");
		model.addColumn(column1);
		
		TableColumn column2 = new TableColumn(1,150);
		column2.setHeaderValue(Gui.lang.getString("name"));
		model.addColumn(column2);
		
		TableColumn column3 = new TableColumn(2,300);
		column3.setHeaderValue(Gui.lang.getString("description"));
		model.addColumn(column3);
	
		return model;
	}
/*
	protected void updateEditedEntry(IndividualList list, int row)
	{
		listTable.setValueAt(list.getListName(),row,1);	
		listTable.setValueAt(list.getDescription(),row,2);				   
	}
	*/
	
	/**
	 * List all metadata items of the selected entries
	 * @param selected Is TRUE if some check boxes are checked
	 * @param selectedRow Selected row of the table, is used only if selected = FALSE
	 */
	private void listItems(boolean selected, int selectedRow)
	{
		TableModel model = listTable.getModel();
		Collection searchResult = new HashSet();
		if(selected)	//some items are checked, delete them
		{
			for(int i=0;i<myLists.length;i++)
			{
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
				{
					WaitDialog.showDialog();
					String listToBrowse = ((IndividualList)myLists[i]).getListName();
					Collection listResult = Gui.dispatcher.getAllItemsFromIndividualList(listToBrowse);
					searchResult.addAll(listResult);
					WaitDialog.closeDialog();
				}
			}
		}
		else // no checked items, delete only selected row
		{	
			if(selectedRow>=0)
			{
				WaitDialog.showDialog();
				String listToBrowse = ((IndividualList)myLists[selectedRow]).getListName();
				searchResult = Gui.dispatcher.getAllItemsFromIndividualList(listToBrowse);
				WaitDialog.closeDialog();
			}
		}
		
		if (searchResult.isEmpty()) // Result ist empty, show Warning
		{
			showNoResultMessage();	
		}
		else //Result is not empty, call BrowseMetadataResults
		{
			//Result-Frame ist allready opened, ask to overwrite
			int SMRIndex = Configurator.getIntProperty("searchMetadataResult",0,"gui-forms");	
			if(Gui.my_forms[SMRIndex]!=null && !Gui.my_forms[SMRIndex].isClosed())
			{
				int switch_frame = getNewSearchDialog();
				if (switch_frame == JOptionPane.NO_OPTION) 
				{	
					Gui.my_forms[SMRIndex].pack();
					try {
						Gui.my_forms[SMRIndex].setSelected(true);
					} 
					   catch (java.beans.PropertyVetoException ev) {}							
				}
				else
				{
					Point location = Gui.my_forms[SMRIndex].getLocation();
					Gui.my_forms[SMRIndex].dispose();
					BrowseMetadataResults mdSearchResult = new BrowseMetadataResults(SMRIndex, Gui.lang.getString("selected_list_entries"), searchResult, location);
				}	
			}
			else
			{
				BrowseMetadataResults mdSearchResult = new BrowseMetadataResults(SMRIndex, Gui.lang.getString("selected_list_entries"), searchResult, null);	
			}
		 		
		}
	}

	/**
	 * Export all metadata items of the selected entries
	 * @param selected Is TRUE if some check boxes are checked
	 * @param selectedRow Selected row of the table, is used only if selected = FALSE
	 */
	private void exportItems(boolean selected, int selectedRow)
	{

		TableModel model = listTable.getModel();
		this.listsToExport = new HashSet();
		for(int i=0; i<myLists.length;i++) 
		{
			if(selected)	//some items are checked, delete them
			{
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
				{
					listsToExport.add(((IndividualList)myLists[i]).getListName());	
					//System.out.println("Checked:"+((IndividualList)myLists[i]).getListName());	
				}	
			}
			else // no checked items, delete only selected row
			{	
			
				if((selectedRow>=0 && myLists[i]==myLists[selectedRow]))
				{
					listsToExport.add(new String(((IndividualList)myLists[selectedRow]).getListName()));	
					//System.out.println("Selected:"+((IndividualList)myLists[i]).getListName());	
				}
			}
		}
					
		//this.selectedList = selectedRow;
		fcs = new JFileChooser();
		int formatCount = Configurator.getIntProperty("formatCount", 0, "export-formats");
		filters = new String[formatCount][3];
		if (formatCount != 0)
		{	
			for (int i = 1; i <= formatCount; i++) 
			{ 
				//System.out.println("FILE ENDING:"+Configurator.getProperty("format" + i, "", "export-formats"));
				//System.out.println("FILE DESC:"+Configurator.getProperty("desc" + i, "", "export-formats"));
				String fileEnding = Configurator.getProperty("format" + i, "", "export-formats");
				String description = Configurator.getProperty("desc" + i, "", "export-formats");		
				String filename = Configurator.getProperty("filename" + i, "", "export-formats");
				filters[i-1][0] = description ;
				filters[i-1][1] = fileEnding;
				filters[i-1][2] = filename;
				ExportFileFilter ff = new ExportFileFilter(fileEnding,description);
				fcs.addChoosableFileFilter(ff);

			}
		}
	
		//fcs.setDialogType(JFileChooser.SAVE_DIALOG);
		fcs.addActionListener(this);
		fcs.showSaveDialog(Gui.modalParent);	
	}

	/**
	 * Removes selected entries from local database
	 * @param selected Is TRUE if some check boxes are checked
	 * @param selectedRow  Selected row of the table, is used only if selected = FALSE
	 */
	private void deleteItems(boolean selected, int selectedRow)
	{
		int confirm_dialog = getDeleteItemsDialog();
			if (confirm_dialog == JOptionPane.YES_OPTION) 
			{
				TableModel model = listTable.getModel();
				Collection fitedResults = new HashSet();
				for(int i=0; i<myLists.length;i++) 
				{
					if(selected)	//some items are checked, delete them
					{
						Boolean checked = (Boolean)model.getValueAt(i,0);
						if(Boolean.FALSE.equals(checked))
						{
							fitedResults.add(myLists[i]);		
						}
						else
						{
							Gui.dispatcher.removeIndividualList(((IndividualList)myLists[i]).getListName());
						}		
					}
					else // no checked items, delete only selected row
					{	
						if((selectedRow>=0 && myLists[i]!=myLists[selectedRow]))
						{
							fitedResults.add(myLists[i]);
						}
						else
						{
							Gui.dispatcher.removeIndividualList(((IndividualList)myLists[i]).getListName());
						}		
					}
				}
				myLists=Gui.dispatcher.sortIndividualList(fitedResults, Gui.myLib).toArray();
				if(myLists.length>0)
					{
						Point location = Gui.my_forms[index].getLocation();
						updateFormFrame(location);
					}
					else
					{
						Gui.my_forms[index].dispose();
						JOptionPane.showMessageDialog(
							Gui.modalParent,
								Gui.lang.getString("no_lists_avail"),
									Gui.lang.getString("warning"),
										JOptionPane.CANCEL_OPTION);	
					}
			}
	}
	
	/** Invoked when an action occurs. */
	public void actionPerformed(ActionEvent e) 
	{
		String actionCmd = e.getActionCommand();
		
		if (actionCmd.equals("cancel")) 
		{
			closeForm();
		}

		if (actionCmd.equals("new_list")) 
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

		if(actionCmd.equals("list_content"))
		{	
			boolean selected = false;
			for(int i=0; i<myLists.length;i++) 
			{
				TableModel model = listTable.getModel();
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
					selected=true;
			}
			int selectedRow = listTable.getSelectedRow();
			if(selected || (!selected && selectedRow>=0))
				listItems(selected,selectedRow);
			else
			{
				showNoListcontentSection();			
			}				
		}
		
		if(actionCmd.equals("export"))
		{	
			boolean selected = false;
			for(int i=0; i<myLists.length;i++) 
			{
				TableModel model = listTable.getModel();
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
					selected=true;
			}
			int selectedRow = listTable.getSelectedRow();
			if(selected || (!selected && selectedRow>=0))
				exportItems(selected,selectedRow);
			else
			{
				JOptionPane.showMessageDialog(
					Gui.modalParent,
						Gui.lang.getString("no_export_selection"),
							Gui.lang.getString("warning"),
								JOptionPane.CANCEL_OPTION);
				
			}				
		}
		
		if(actionCmd.equals("delete"))
		{	
			boolean selected = false;
			for(int i=0; i<myLists.length;i++) 
			{
				TableModel model = listTable.getModel();
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
					selected=true;
			}
			int selectedRow = listTable.getSelectedRow();
			if(selected || (!selected && selectedRow>=0))
			{
				deleteItems(selected,selectedRow);
				if (Gui.automaticUpdate) 
				{
					WaitDialog.showDialog();
	                TreeForm.updateTreeForm(3,null,null);
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
				showNoDeleteSelectionMessage();
			}				
		}
		
		if (actionCmd.equals("edit")) 
		{
			Point formLocation = null;
			int selectedRow  = listTable.getSelectedRow();
			if(selectedRow>=0)
			{
				IndividualList list = (IndividualList)myLists[selectedRow];
				int editListIndex = Configurator.getIntProperty("editList",0,"gui-forms");
				JInternalFrame form=Gui.getForm(editListIndex);
				if(form!=null && !form.isClosed())
				{
					int switch_frame = getEditOpenedDialog();
					if (switch_frame == JOptionPane.YES_OPTION) 
					{
						formLocation = form.getLocation();
						form.dispose();
						ListForm editList = new ListForm(editListIndex, list, selectedRow, formLocation);			
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
					ListForm editList = new ListForm(editListIndex, list, selectedRow, formLocation);
				}
			}//if(selectedRow>=0)
			else
			{
				showNoEditSelectionMessage();		
			}
			
		}
		
		
		if(actionCmd.equals(JFileChooser.APPROVE_SELECTION))
		{
			FileFilter ff= fcs.getFileFilter(); 
			String f_description = ff.getDescription();
			String f_filename="";
			String output_file_extension="";
			for(int i=0; i<filters.length;i++)
			{
				if(filters[i][0]==f_description)
				{
					f_filename = filters[i][2];
					output_file_extension = filters[i][1];
				}
				
			}
			System.out.println("Selected filename for export:"+f_filename);
			Gui.dispatcher.export(this.listsToExport,  fcs.getSelectedFile().getPath()+"."+output_file_extension , f_filename);

		}
	
		
		if (actionCmd.equals("select_all")) 
		{
			selectAll();
		}
		
		if (actionCmd.equals("select_all_pop")) 
		{
			selectAllPop();
		}					
	}
	/** Toggles all check boxes on action occured by "select all" - checkbox */
	private void selectAll()
	{	
		if(selectAll.isSelected())
			for(int i=0;i<myLists.length;i++)
			{
				listTable.setValueAt(new Boolean(true),i,0);
			}
		else
			for(int i=0;i<myLists.length;i++)
			{
				listTable.setValueAt(new Boolean(false),i,0);
			}
	}
	/** Toggles all check boxes on action occured by "select all" - popup menu entry */
	private void selectAllPop()
	{
		if(!selectAll.isSelected())
			for(int i=0;i<myLists.length;i++)
			{
				listTable.setValueAt(new Boolean(true),i,0);
			}
		else
			for(int i=0;i<myLists.length;i++)
			{
				listTable.setValueAt(new Boolean(false),i,0);
			}
		selectAll.setSelected(!selectAll.isSelected());
	}
	
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
		//if (e.getButton() == MouseEvent.BUTTON2_MASK)
		if(SwingUtilities.isRightMouseButton(e))
		{
			int row = this.listTable.rowAtPoint(e.getPoint());
			this.listTable.setRowSelectionInterval(row,row);
			ContextMenu pop = new ContextMenu(this.index, e.getX(),e.getY());
		}
		else if (e.getClickCount() == 2) 
			actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"list_content"));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public void keyPressed (KeyEvent event)
	{
		 if (event.getKeyCode() == KeyEvent.VK_ESCAPE){
			actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));
		 }
		 if (event.getKeyCode() == KeyEvent.VK_ENTER)
			actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));
	}		
	public void keyReleased(KeyEvent event){}	 
	public void keyTyped(KeyEvent event){}

	/**
	 * @return Returns the listTable.
	 * 
	 * @uml.property name="listTable"
	 */
	public JTable getListTable() {
		return listTable;
	}

}
