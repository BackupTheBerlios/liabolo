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
import org.liabolo.client.offline.dialogs.CheckoutByBranch;
import org.liabolo.common.Branch;
import org.liabolo.common.Configurator;



/**
 * @author Jurij Henne
 *
 * This is an impementation of  "browse branches" - form. The user gets a full, scrollable list of available branches
 * and can perform some usefull actions on selected entries.
 */
public class BrowseBranch extends DefaultForm implements ActionListener, KeyListener, MouseListener {
	
	/** Indicates if all entries were selected */
	private JCheckBox selectAll;
	/** Array of available branches */
	private Object[] myBranches;

	/**
	 * Table, that contains the attributes of the branches
	 * 
	 * @uml.property name="branchTable"
	 */
	private JTable branchTable;
	

/**
 * Creates an new browsing form 
 * @param index specifies a unique 'position' of  the form in form holder-array 
 * @param formLocation specifies a unique 'position' of  the form in form holder-array 
 */
	public BrowseBranch(int index, Point formLocation)
	{	
		super(index, Gui.lang.getString("browse_branches"),"images/search.png");
		Collection allBranches = Gui.dispatcher.getAllBranches(Gui.myLib);
		WaitDialog.showDialog(); 
		this.myBranches = (Gui.dispatcher.sortBranch(allBranches,Gui.myLib)).toArray();
		Object [] branchDescription = new Object[myBranches.length];
		for(int i=0;i<myBranches.length;i++)
		{
			branchDescription[i] = ((Branch)myBranches[i]).getDescription();
		}  
		WaitDialog.closeDialog();
		if(myBranches.length<1)
			showNoResultMessage();
		else
			this.addFormFrame(formLocation);		

	 }
	
	/** Implemetation of inherited method. Returns the layouted  content panel of the form */
	public JPanel showFormContent()
	{
		JPanel root = getRootPanel(); //inherited	
		
   		if(myBranches.length==0) 
   		{
			Gui.my_forms[index].setPreferredSize(new Dimension(300, 200));
			JPanel noresultPanel = getNoResultPanel(Gui.lang.getString("no_branches_avail"),Gui.lang.getString("list_branches")); //inherited
			root.add(noresultPanel, "1,1,3,1,FULL,FULL");
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
									TableLayout.PREFERRED,
							 	    TableLayout.PREFERRED,border}}; // Rows
							 	    
			TableLayout tl = new TableLayout(size);
			tl.setHGap(border);
			tl.setVGap(border);
			JPanel panel = new JPanel(tl);
			panel.setBorder
					(BorderFactory.createTitledBorder(
							BorderFactory.createEtchedBorder(BevelBorder.LOWERED), Gui.lang.getString("list_branches")));
	
		   Object [][] tableData = new Object[myBranches.length][3];

			for (int i = 0; i < myBranches.length; i++) 
			{
				tableData[i][0] = new Boolean(false);
				tableData[i][1] = ((Branch)myBranches[i]).getDescription();
				tableData[i][2] = ((Branch)myBranches[i]).getAbbreviation();
			}

			// Show Statistics
			int displayed;
			if (myBranches.length < 100)
				displayed = myBranches.length;
			else
				displayed = 100;
				
			String stat_string = (Gui.lang.getString("results")+ " : "+ myBranches.length); 
			JLabel stats = FormElement.getInfiniteLabel(stat_string, Font.PLAIN, null);
			panel.add(stats, "1,1,4,1,LEFT,CENTER");

			branchTable = new JTable(new ResultTableModel(tableData));
			branchTable.setColumnModel(getColumnModel());
			branchTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			branchTable.addMouseListener(this);
			
			
			JPanel sroot = new JPanel(new BorderLayout());
			JScrollPane sp = new JScrollPane(branchTable);
			sp.setPreferredSize(new Dimension(300, 300));
			sroot.add(sp, BorderLayout.CENTER);
			panel.add(sroot, "1,2,4,2,FULL,TOP");
			 
			selectAll = new JCheckBox(Gui.lang.getString("select_all"));
			selectAll.setToolTipText(Gui.lang.getString("select_all"));
			selectAll.setActionCommand("select_all");
			selectAll.addActionListener(this);
			panel.add(selectAll, "1,3,1,3,LEFT,CENTER");
		   
			JButton new_button = FormElement.getButton("new", "images/branch2.png",null, null, true);
			new_button.setActionCommand("new_branch");
			new_button.addActionListener(this);
			new_button.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent event)
				{
					if (event.getKeyCode() == KeyEvent.VK_ESCAPE )
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));
					if (event.getKeyCode() == KeyEvent.VK_ENTER)
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"new_branch"));
				}
			});
			panel.add(new_button, "3,3,3,3,FULL,CENTER");
		   
			JButton content_button = FormElement.getButton("content", "images/content.png", "branch_content", null, true);
			content_button.setActionCommand("branch_content");
			content_button.addActionListener(this);
			content_button.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent event)
				{
					if (event.getKeyCode() == KeyEvent.VK_ESCAPE )
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));
					if (event.getKeyCode() == KeyEvent.VK_ENTER)
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"branch_content"));
				}
			});
			panel.add(content_button, "4,3,4,3,FULL,CENTER");
	
			JButton checkout_button = FormElement.getButton("checkout", "images/checkout2.png", null, null, true);
			checkout_button.setActionCommand("checkout");
			checkout_button.addKeyListener(new KeyAdapter()
				{
					public void keyPressed(KeyEvent event)
					{
						if (event.getKeyCode() == KeyEvent.VK_ESCAPE )
							actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));
						if (event.getKeyCode() == KeyEvent.VK_ENTER)
							actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"checkout"));
					}
				});
			checkout_button.addActionListener(this);
			panel.add(checkout_button, "3,4,3,4,FULL,CENTER");
			
			JButton edit_button = FormElement.getButton("edit", "images/searchedit.png", null, null, true);
			edit_button.setActionCommand("edit");
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
			edit_button.addActionListener(this);
			panel.add(edit_button, "4,4,4,4,FULL,CENTER");

/*
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
			panel.add(delete_button, "4,5,4,5,FULL,CENTER");
			
			*/

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
		TableColumn column2 = new TableColumn(1,300);
		column2.setHeaderValue(Gui.lang.getString("branch"));
		TableColumn column3 = new TableColumn(2,50);
		column3.setHeaderValue(Gui.lang.getString("abreviation"));
		model.addColumn(column1);
		model.addColumn(column2);
		model.addColumn(column3);
		return model;
	}
		
	/**
	 * Collects selected entries(branches) to delegate them on  CheckoutByBranch.java
	 * @param selected Is TRUE if some check boxes are checked
	 * @param selectedRow Selected row of the table, is used only if selected = FALSE
	 */
	private void checkoutItems(boolean selected, int selectedRow)
	{
		TableModel model = branchTable.getModel();
		Collection checkoutEntries = new HashSet();
		for(int i=0; i<myBranches.length;i++) 
		{
			if(selected)	//some items are checked, delete them
			{
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
				{
					checkoutEntries.add(myBranches[i]);	
				}

			}
			else // no checked items, delete only selected row
			{	
				if((selectedRow>=0 && myBranches[i]==myBranches[selectedRow]))
				{
					checkoutEntries.add(myBranches[i]);	
				}
			}
		}
		CheckoutByBranch conLib = new CheckoutByBranch(checkoutEntries);
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
				TableModel model = branchTable.getModel();
				Collection fitedResults = new HashSet();
				for(int i=0; i<myBranches.length;i++) 
				{
					if(selected)	//some items are checked, delete them
					{
						Boolean checked = (Boolean)model.getValueAt(i,0);
						if(Boolean.FALSE.equals(checked))
						{
							fitedResults.add(myBranches[i]);		
						}
						else
						{
							Gui.dispatcher.removeBranch((Branch)myBranches[i]);
						}		
					}
					else // no checked items, delete only selected row
					{	
						if((selectedRow>=0 && myBranches[i]!=myBranches[selectedRow]))
						{
							fitedResults.add(myBranches[i]);
						}
						else
						{
							Gui.dispatcher.removeBranch((Branch)myBranches[i]); 
						}		
					}
				}
				myBranches=Gui.dispatcher.sortBranch(fitedResults,Gui.myLib).toArray();
				if(myBranches.length>0)
				{
					Point location = Gui.my_forms[index].getLocation();
					updateFormFrame(location);
				}
				else
				{
					Gui.my_forms[index].dispose();
					JOptionPane.showMessageDialog(
						Gui.modalParent,
							Gui.lang.getString("no_branches_avail"),
								Gui.lang.getString("warning"),
									JOptionPane.CANCEL_OPTION);	
				}
			}
	}
	
	/**
	 * List all metadata items of the selected entries
	 * @param selected Is TRUE if some check boxes are checked
	 * @param selectedRow Selected row of the table, is used only if selected = FALSE
	 */
	private void listItems(boolean selected, int selectedRow)
	{
		TableModel model = branchTable.getModel();
		Collection searchResult = new HashSet();
		if(selected)	//some items are checked, delete them
		{
		//int [] selectedIndices = branches.getSelectedIndices();
			for(int i=0;i<myBranches.length;i++)
			{
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
				{
					WaitDialog.showDialog();
					String branchToBrowse = ((Branch)myBranches[i]).getAbbreviation();
					Collection branchResult = Gui.dispatcher.getAllLibItemsFromBranch(branchToBrowse);
					searchResult.addAll(branchResult);
					WaitDialog.closeDialog();
				}
			}
		}
		else // no checked items, delete only selected row
		{	
			if(selectedRow>=0)
			{
				WaitDialog.showDialog();
				String branchToBrowse = ((Branch)myBranches[selectedRow]).getAbbreviation();
				searchResult = Gui.dispatcher.getAllLibItemsFromBranch(branchToBrowse);
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
			int index = Configurator.getIntProperty("searchMetadataResult",0,"gui-forms");	
			if(Gui.my_forms[index]!=null && !Gui.my_forms[index].isClosed())
			{
				int switch_frame = getNewSearchDialog();
				if (switch_frame == JOptionPane.NO_OPTION) 
				{	
					Gui.my_forms[index].pack();
					try {
						Gui.my_forms[index].setSelected(true);
					} 
					   catch (java.beans.PropertyVetoException ev) {}							
				}
				else
				{
					Point location = Gui.my_forms[index].getLocation();
					Gui.my_forms[index].dispose();
					BrowseMetadataResults mdSearchResult = new BrowseMetadataResults(index, Gui.lang.getString("selected_branch_entries"), searchResult, location);
				}	
			}
			else
			{
				// GEH�RT HIER NICHT REIN // Point location = Gui.my_forms[index].getLocation();
				BrowseMetadataResults mdSearchResult = new BrowseMetadataResults(index, Gui.lang.getString("selected_branch_entries"), searchResult, null);	
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
		
		if (actionCmd.equals("new_branch")) 
		{
			int index = Configurator.getIntProperty("newBranch",0,"gui-forms");
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
				BranchForm branchForm = new BranchForm(index,null);
			}	
		}
		
		if (actionCmd.equals("checkout")) 
		{
			boolean selected = false;
			for(int i=0; i<myBranches.length;i++) 
			{
				TableModel model = branchTable.getModel();
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
					selected=true;
			}
			int selectedRow = branchTable.getSelectedRow();
			if(selected || (!selected && selectedRow>=0))
				checkoutItems(selected,selectedRow);
			else
			{
				showNoSectionMessage();
				
			}			
		}
		 
		if (actionCmd.equals("edit")) 
		{
			Point formLocation = null;
			int selectedRow  = branchTable.getSelectedRow();
			if(selectedRow>=0)
			{
				Branch branchToEdit = (Branch)myBranches[selectedRow];
				int editBranchIndex = Configurator.getIntProperty("editBranch",0,"gui-forms");
				JInternalFrame form=Gui.getForm(editBranchIndex);
				if(form!=null && !form.isClosed())
				{
					int switch_frame = getEditOpenedDialog();
					if (switch_frame == JOptionPane.YES_OPTION) 
					{
						formLocation = form.getLocation();
						form.dispose();	
						BranchForm editBranch = new BranchForm(editBranchIndex, branchToEdit, selectedRow, formLocation);			
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
					BranchForm editBranch = new BranchForm(editBranchIndex, branchToEdit, selectedRow, formLocation);	
				}
			}//if(selectedRow>=0)
			else
			{
				showNoEditSelectionMessage();			
			}	
		}
		
		if(actionCmd.equals("delete"))
		{	
			boolean selected = false;
			for(int i=0; i<myBranches.length;i++) 
			{
				TableModel model = branchTable.getModel();
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
					selected=true;
			}
			int selectedRow = branchTable.getSelectedRow();
			if(selected || (!selected && selectedRow>=0))
				deleteItems(selected,selectedRow);
			else
			{
				showNoDeleteSelectionMessage();
				
			}				
		}
		
		if(actionCmd.equals("branch_content"))
		{	
			boolean selected = false;
			for(int i=0; i<myBranches.length;i++) 
			{
				TableModel model = branchTable.getModel();
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
					selected=true;
			}
			int selectedRow = branchTable.getSelectedRow();
			if(selected || (!selected && selectedRow>=0))
				listItems(selected,selectedRow);
			else
			{
				showNoListcontentSection();
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
	
	/** Toggles all check boxes on action occured by "select all" - checkbox */
	private void selectAll()
	{	
		if(selectAll.isSelected())
			for(int i=0;i<myBranches.length;i++)
			{
				branchTable.setValueAt(new Boolean(true),i,0);
			}
		else
			for(int i=0;i<myBranches.length;i++)
			{
				branchTable.setValueAt(new Boolean(false),i,0);
			}
	}
	
	/** Toggles all check boxes on action occured by "select all" - popup menu entry */
	private void selectAllPop()
	{
		if(!selectAll.isSelected())
			for(int i=0;i<myBranches.length;i++)
			{
				branchTable.setValueAt(new Boolean(true),i,0);
			}
		else
			for(int i=0;i<myBranches.length;i++)
			{
				branchTable.setValueAt(new Boolean(false),i,0);
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
		//if(e.isPopupTrigger())
		if(SwingUtilities.isRightMouseButton(e))
		{
			int row = this.branchTable.rowAtPoint(e.getPoint());
			this.branchTable.setRowSelectionInterval(row,row);
			ContextMenu pop = new ContextMenu(this.index, e.getX(),e.getY());
		}
		else if (e.getClickCount() == 2) 
			actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"branch_content"));
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
	 * @return Returns the branchTable.
	 * 
	 * @uml.property name="branchTable"
	 */
	public JTable getBranchTable() {
		return branchTable;
	}

}
		
