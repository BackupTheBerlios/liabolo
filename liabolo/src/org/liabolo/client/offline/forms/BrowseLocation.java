/*
 * Created on 02.06.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

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
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.ContextMenu;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.common.ResultTableModel;
import org.liabolo.client.offline.common.WaitDialog;
import org.liabolo.common.Configurator;
import org.liabolo.common.Location;

/**
 * @author Jurij Henne
 *
 * This is an impementation of  "browse locations" - form. The user gets a full, scrollable list of available locations
 * and can perform some usefull actions on selected entries.
 */
public class BrowseLocation extends DefaultForm implements ActionListener, KeyListener, MouseListener {
	
	/** Indicates if all entries were selected */
	private JCheckBox selectAll;
	/** Available locations */
	private Object[] myLocations;

	/**
	 * Table, which displays the attributes of the lists
	 * 
	 * @uml.property name="locTable"
	 */
	private JTable locTable;
	

 /**
  * Creates a browse location form 
  * @param index specifies a unique 'position' of  "browse location" form in form holder-array
  * @param position specifies the location on desktop
  */
	public BrowseLocation(int index, Point location)
	{	
		super(index, Gui.lang.getString("browse_locations"),"images/searchedit.png");
		WaitDialog.showDialog();
		this.myLocations = Gui.dispatcher.sortLocation(Gui.dispatcher.getAllLocations(Gui.myLib), Gui.myLib).toArray();	
		WaitDialog.closeDialog();	
		if(myLocations.length<1)
			showNoResultMessage();
		else
			this.addFormFrame(location);
				
	 }

	/** Implemetation of inherited method. Returns the layouted  content panel of the form */
	public JPanel showFormContent()
	{
		JPanel root = getRootPanel(); //inherited	
		
		if(	myLocations.length==0) 	// should not be invoked
		{
			Gui.my_forms[index].setPreferredSize(new Dimension(300, 200));
			JPanel noresultPanel = getNoResultPanel(Gui.lang.getString("no_locations_avail"),Gui.lang.getString("list_locations")); //inherited
			root.add(noresultPanel, "1,1,3,1,FULL,FULL");
			Gui.my_forms[index].update(Gui.my_forms[index].getGraphics());
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
							BorderFactory.createEtchedBorder(BevelBorder.LOWERED), Gui.lang.getString("list_locations")));
	
		   Object [][] tableData = new Object[myLocations.length][3];

			for (int i = 0; i < myLocations.length; i++) 
			{
			   if(myLocations[i]!=null)
			   {
				tableData[i][0] =  new Boolean(false);
				tableData[i][1] = ((Location)myLocations[i]).getName();
				tableData[i][2] = ((Location)myLocations[i]).getDescription();
			   }
			}

			// Show Statistics
			int displayed;
			if (myLocations.length < 100)
				displayed = myLocations.length;
			else
				displayed = 100;
				
			String stat_string = (Gui.lang.getString("results")+ " : "+ myLocations.length); 
			JLabel stats = FormElement.getInfiniteLabel(stat_string, Font.PLAIN, null);
			panel.add(stats, "1,1,4,1,LEFT,CENTER");

			locTable = new JTable(new ResultTableModel(tableData));
			locTable.setColumnModel(getColumnModel());
			locTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			locTable.addMouseListener(this);
			JPanel sroot = new JPanel(new BorderLayout());
			JScrollPane sp = new JScrollPane(locTable);
			sp.setPreferredSize(new Dimension(380, 300));

			sroot.add(sp, BorderLayout.CENTER);
			panel.add(sroot, "1,2,4,2,FULL,TOP");
			 
		    selectAll = new JCheckBox(Gui.lang.getString("select_all"));
		    selectAll.setToolTipText(Gui.lang.getString("select_all"));
		    selectAll.setActionCommand("select_all");
		    selectAll.addActionListener(this);
		    panel.add(selectAll, "1,3,1,3,LEFT,CENTER");
	
			JButton new_button = FormElement.getButton("new", "images/location.png",null, null, true);
			new_button.setActionCommand("new_loc");
			new_button.addActionListener(this);
			new_button.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent event)
				{
					if (event.getKeyCode() == KeyEvent.VK_ESCAPE )
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));
					if (event.getKeyCode() == KeyEvent.VK_ENTER)
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"new_loc"));
				}
			});
			panel.add(new_button, "3,3,3,3,FULL,CENTER");
		    
			JButton content_button = FormElement.getButton("content", "images/content.png","location_content", null, true);
			content_button.setActionCommand("location_content");
			content_button.addActionListener(this);
			content_button.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent event)
				{
					if (event.getKeyCode() == KeyEvent.VK_ESCAPE )
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));
					if (event.getKeyCode() == KeyEvent.VK_ENTER)
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"location_content"));
				}
			});
			panel.add(content_button, "3,4,3,4,FULL,CENTER");
		    
			JButton edit_button = FormElement.getButton("edit", "images/searchedit.png", null, null, true);
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
			panel.add(edit_button, "4,3,4,3,FULL,CENTER");

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
			delete_button.setEnabled(false);
			panel.add(delete_button, "4,4,4,4,FULL,CENTER");

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
		
		TableColumn column3 = new TableColumn(2,200);
		column3.setHeaderValue(Gui.lang.getString("description"));
		model.addColumn(column3);
		
		return model;
	}
	
	/**
	 * List all metadata items of the selected entries
	 * @param selected Is TRUE if some check boxes are checked
	 * @param selectedRow Selected row of the table, is used only if selected = FALSE
	 */
	private void listItems(boolean selected, int selectedRow)
	{
		TableModel model = locTable.getModel();
		Collection searchResult = new HashSet();
		if(selected)	//some items are checked, delete them
		{
		//int [] selectedIndices = branches.getSelectedIndices();
			for(int i=0;i<myLocations.length;i++)
			{
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
				{
					Collection listResult = Gui.dispatcher.getAllLibItemsFromLocation((Location)myLocations[i]);
					searchResult.addAll(listResult);
				}
			}
		}
		else // no checked items, delete only selected row
		{	
			if(selectedRow>=0)
			{
				searchResult = Gui.dispatcher.getAllLibItemsFromLocation((Location)myLocations[selectedRow]);
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
					BrowseMetadataResults mdSearchResult = new BrowseMetadataResults(SMRIndex, Gui.lang.getString("selected_location_entries"), searchResult, location);
				}	
			}
			else
			{
				BrowseMetadataResults mdSearchResult = new BrowseMetadataResults(SMRIndex, Gui.lang.getString("selected_location_entries"), searchResult, null);	
			}
		 		
		}
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
				TableModel model = locTable.getModel();
				Collection fitedResults = new HashSet();
				for(int i=0; i<myLocations.length;i++) 
				{
					if(selected)	//some items are checked, delete them
					{
						Boolean checked = (Boolean)model.getValueAt(i,0);
						if(Boolean.FALSE.equals(checked))
						{
							fitedResults.add(myLocations[i]);		
						}
						else
						{
							Gui.dispatcher.removeLocation((Location)myLocations[i]);
						}		
					}
					else // no checked items, delete only selected row
					{	
						if((selectedRow>=0 && myLocations[i]!=myLocations[selectedRow]))
						{
							fitedResults.add(myLocations[i]);
						}
						else
						{
							Gui.dispatcher.removeLocation((Location)myLocations[i]); 
						}		
					}
				}
				myLocations=Gui.dispatcher.sortLocation(fitedResults,Gui.myLib).toArray();
				if(myLocations.length>0)
				{
					Point location = Gui.my_forms[index].getLocation();
					updateFormFrame(location);
				}
				else
				{
					Gui.my_forms[index].dispose();
					JOptionPane.showMessageDialog(
						Gui.modalParent,
							Gui.lang.getString("no_locations_avail"),
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
		
		if (actionCmd.equals("new_loc")) 
		{
			int index = Configurator.getIntProperty("newLocation",0,"gui-forms");
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
				LocationForm locationForm = new LocationForm(index,null);
			}	
		}

		if(actionCmd.equals("location_content"))
		{	
			boolean selected = false;
			for(int i=0; i<myLocations.length;i++) 
			{
				TableModel model = locTable.getModel();
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
					selected=true;
			}
			int selectedRow = locTable.getSelectedRow();
			if(selected || (!selected && selectedRow>=0))
				listItems(selected,selectedRow);
			else
			{
				showNoListcontentSection();			
			}				
		}

		if(actionCmd.equals("delete"))
		{	
			boolean selected = false;
			for(int i=0; i<myLocations.length;i++) 
			{
				TableModel model = locTable.getModel();
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
					selected=true;
			}
			int selectedRow = locTable.getSelectedRow();
			if(selected || (!selected && selectedRow>=0))
				deleteItems(selected,selectedRow);
			else
			{
				showNoDeleteSelectionMessage();		
			}				
		}
		
		if (actionCmd.equals("edit")) 
		{
			Point formLocation = null;
			int selectedRow  = locTable.getSelectedRow();
			if(selectedRow>=0)
			{
				Location myLocation = (Location)myLocations[selectedRow];
				int editLocationIndex = Configurator.getIntProperty("editLocation",0,"gui-forms");
				JInternalFrame form=Gui.getForm(editLocationIndex);
				if(form!=null && !form.isClosed())
				{
					int switch_frame = getEditOpenedDialog();
					if (switch_frame == JOptionPane.YES_OPTION) 
					{
						formLocation=form.getLocation();			
						form.dispose();		
						LocationForm editLocation = new LocationForm(editLocationIndex, myLocation, formLocation);							
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
					LocationForm editLocation = new LocationForm(editLocationIndex, myLocation, formLocation);	
				}
			}//if(selectedRow>=0)
			else
			{
				showNoEditSelectionMessage();		
			}
			
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
	private void selectAll()
	{	
		if(selectAll.isSelected())
			for(int i=0;i<myLocations.length;i++)
			{
				locTable.setValueAt(new Boolean(true),i,0);
			}
		else
			for(int i=0;i<myLocations.length;i++)
			{
				locTable.setValueAt(new Boolean(false),i,0);
			}
	}
	private void selectAllPop()
	{
		if(!selectAll.isSelected())
			for(int i=0;i<myLocations.length;i++)
			{
				locTable.setValueAt(new Boolean(true),i,0);
			}
		else
			for(int i=0;i<myLocations.length;i++)
			{
				locTable.setValueAt(new Boolean(false),i,0);
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
			int row = this.locTable.rowAtPoint(e.getPoint());
			this.locTable.setRowSelectionInterval(row,row);
			ContextMenu pop = new ContextMenu(this.index, e.getX(),e.getY());
		}
		else if (e.getClickCount() == 2) 
			actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"location_content"));
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
	 * @return Returns the locTable.
	 * 
	 * @uml.property name="locTable"
	 */
	public JTable getLocTable() {
		return locTable;
	}

}
