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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.ContextMenu;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.common.WaitDialog;
import org.liabolo.common.Configurator;
import org.liabolo.common.Connection;

/**
 * @author Jurij Henne
 *
 * This is an impementation of  "browse connection" - form. The user gets a  scrollable list of available connections
 * and can perform some usefull actions on selected entries.
 */
public class BrowseConnection extends DefaultForm implements ActionListener, KeyListener, MouseListener {

	/** Indicates if all entries were selected */
	private JCheckBox selectAll;
	/** Available conections. Includes also the connection to local db*/
	private Object[] myConnections;

	/**
	 * Table, that displays the attributes of the connections
	 * 
	 * @uml.property name="conTable"
	 */
	private JTable conTable;

	/** Indicates the row, where the active-status checkbox was klicked */
	private int rowClickedActive;

	/**
	 * Creates a new "browse connection" form
	 * @param index specifies a unique 'position' of  the form in form holder-array
	 * @param formLocation specifies a unique 'position' of  the form in form holder-array
	 */
	public BrowseConnection(int index, Point formLocation)
	{
		super(index, Gui.lang.getString("browse_connections"),"images/searchedit.png");
		WaitDialog.showDialog();
		this.myConnections = Gui.dispatcher.sortConnections(Gui.dispatcher.getAllConnections(), Gui.myLib).toArray();
		//this.myConnections = Gui.dispatcher.getAllConnections().toArray();

		WaitDialog.closeDialog();
		if(myConnections.length<1)
			showNoResultMessage();
		else
			this.addFormFrame(formLocation);
	 }

	/** Implemetation of inherited method. Returns the layouted  content panel of the form */
	public JPanel showFormContent()
	{
		JPanel root = getRootPanel(); //inherited

		if(myConnections.length==0)  // should not be invoked
		{
			Gui.my_forms[index].setPreferredSize(new Dimension(300, 200));
			JPanel noresultPanel = getNoResultPanel(Gui.lang.getString("no_connections_avail"),Gui.lang.getString("list_connections")); //inherited
			root.add(noresultPanel, "1,1,3,1,FULL,FULL");
		}
		else
		{
			int border = 3;
			double size[][] =
					{{border,
						TableLayout.PREFERRED,
							TableLayout.FILL,
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
							BorderFactory.createEtchedBorder(BevelBorder.LOWERED), Gui.lang.getString("list_connections")));

/*
		   Object [][] tableData = new Object[myConnections.length][7];

			for (int i = 0; i < myConnections.length; i++)
			{
				tableData[i][0] = new Boolean(false);
				tableData[i][1] = ((Connection)myConnections[i]).getName();
				tableData[i][2] = ((Connection)myConnections[i]).getDbURI();
				tableData[i][3] = ((Connection)myConnections[i]).getUsername();
				tableData[i][4] = ((Connection)myConnections[i]).getPassword();
				tableData[i][5] = ((Connection)myConnections[i]).getDriver();
				tableData[i][6] = new Boolean(((Connection)myConnections[i]).isActive());

			}
			*/


			// Show Statistics
			int displayed;
			if (myConnections.length < 100)
				displayed = myConnections.length;
			else
				displayed = 100;

			String stat_string = (Gui.lang.getString("results")+ " : "+ myConnections.length);
			JLabel stats = FormElement.getInfiniteLabel(stat_string, Font.PLAIN, null);
			panel.add(stats, "1,1,3,1,LEFT,CENTER");

			conTable = new JTable(new AbstractTableModel() {

			/** Is needed by inherited class */
			String [] columnNames= {""};
			/** Data to be shown in the table */
			Object [][] data = getData();

			private Object[][] getData()
			{
				Object [][] tableData = new Object[myConnections.length][7];

				 for (int i = 0; i < myConnections.length; i++)
				 {
					 tableData[i][0] = new Boolean(false);
					 tableData[i][1] = ((Connection)myConnections[i]).getName();
					 tableData[i][2] = ((Connection)myConnections[i]).getDbURI();
					 tableData[i][3] = ((Connection)myConnections[i]).getUsername();
					 tableData[i][4] = ((Connection)myConnections[i]).getPassword();
					 tableData[i][5] = ((Connection)myConnections[i]).getDriver();
					 tableData[i][6] = new Boolean(((Connection)myConnections[i]).isActive());

				 }

				return tableData;
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
			{        if (col >0 && col<6) {
				return false;
			} else {
				return true;
			}

			}

			/** Inherited */
			public void setValueAt(Object value, int row, int col)
			{
				// TODO if(data[row][6].toString().equals("true"))
				data[row][col] = value;
				fireTableCellUpdated(row, col);
			}


		});
			conTable.setColumnModel(getColumnModel());
			conTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			conTable.addMouseListener(this);
			JPanel sroot = new JPanel(new BorderLayout());
			JScrollPane sp = new JScrollPane(conTable);
			sp.setPreferredSize(new Dimension(600, 200));
			sroot.add(sp, BorderLayout.CENTER);
			panel.add(sroot, "1,2,3,2,FULL,FULL");

			selectAll = new JCheckBox(Gui.lang.getString("select_all"));
			selectAll.setToolTipText(Gui.lang.getString("select_all"));
			selectAll.setActionCommand("select_all");
			selectAll.addActionListener(this);
			panel.add(selectAll, "1,3,1,3,LEFT,CENTER");

			JButton new_button = FormElement.getButton("new", "images/connection.png", null, null, true);
			new_button.setActionCommand("new_conn");
			new_button.addActionListener(this);
			new_button.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent event)
				{
					if (event.getKeyCode() == KeyEvent.VK_ESCAPE )
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));
					if (event.getKeyCode() == KeyEvent.VK_ENTER)
						actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"new_conn"));
				}
			});
			panel.add(new_button, "3,3,3,3,FULL,CENTER");

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
			panel.add(edit_button, "3,4,3,4,FULL,CENTER");

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
			panel.add(delete_button, "3,5,3,5,FULL,CENTER");

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

		TableColumn column0 = new TableColumn(0,25);
		column0.setHeaderValue("#");
		model.addColumn(column0);

		TableColumn column1 = new TableColumn(1,150);
		column1.setHeaderValue(Gui.lang.getString("name"));
		model.addColumn(column1);

		TableColumn column2 = new TableColumn(2,250);
		column2.setHeaderValue(Gui.lang.getString("db_uri"));
		model.addColumn(column2);

		TableColumn column3 = new TableColumn(3,100);
		column3.setHeaderValue(Gui.lang.getString("user"));
		model.addColumn(column3);

		TableColumn column4 = new TableColumn(4,100);
		column4.setHeaderValue(Gui.lang.getString("password"));
		model.addColumn(column4);

		TableColumn column5 = new TableColumn(5,200);
		column5.setHeaderValue(Gui.lang.getString("driver"));
		model.addColumn(column5);

		TableColumn column6 = new TableColumn(6,50);
		column6.setHeaderValue(Gui.lang.getString("activ"));
		model.addColumn(column6);


		return model;
	}
/*
	protected void updateEditedEntry(Connection conn, int row)
	{
		conTable.setValueAt(conn.getName(),row,1);
		conTable.setValueAt(conn.getDbURI(),row,2);
		conTable.setValueAt(conn.getUsername(),row,3);
		conTable.setValueAt(conn.getPassword(),row,4);
		conTable.setValueAt(conn.getDriver(),row,5);

	}
*/

	private void changeActiveStatus(int tableRow)
	{

		Boolean klickedBox = (Boolean)this.conTable.getValueAt(tableRow, this.conTable.getColumnCount()-1);
		tableRow+=1;
		//TODO System.out.println("CHECK:"+String.valueOf(!(klickedBox.booleanValue()))+"/connectionActive:"+tableRow);
		Configurator.setProperty("connectionActive" + tableRow,String.valueOf(!(klickedBox.booleanValue()))  ,"connections");
		Configurator.store();
	}

/**
 * Removes selected entries from local database
 * @param selected Is TRUE if some check boxes are checked
 * @param selectedRow  Selected row of the table, is used only if selected = FALSE
 */
	private void deleteItems(boolean selected, int selectedRow)
	{
        Collection connectionsToRemove = new HashSet();
		int confirm_dialog = getDeleteItemsDialog();
			if (confirm_dialog == JOptionPane.YES_OPTION)
			{
				TableModel model = conTable.getModel();
				Collection fitedResults = new HashSet();
				for(int i=0; i<myConnections.length;i++)
				{
					if(selected)	//some items are checked, delete them
					{
						Boolean checked = (Boolean)model.getValueAt(i,0);
						if(Boolean.FALSE.equals(checked))
						{
							fitedResults.add(myConnections[i]);
						}
						else
						{
							connectionsToRemove.add(((Connection)myConnections[i]).getName());
						}
					}
					else // no checked items, delete only selected row
					{
						if((selectedRow>=0 && myConnections[i]!=myConnections[selectedRow]))
						{
							fitedResults.add(myConnections[i]);
						}
						else
						{
                            connectionsToRemove.add(((Connection)myConnections[i]).getName());
						}
					}
				}

                //Now remove ,if there are item to remove
                if(connectionsToRemove.size()!=0)
                    Gui.dispatcher.removeConnections(connectionsToRemove);

				myConnections=fitedResults.toArray();
				if(myConnections.length>0)
					{
						Point location = Gui.my_forms[index].getLocation();
						updateFormFrame(location);
					}
					else
					{
						Gui.my_forms[index].dispose();
						JOptionPane.showMessageDialog(
							Gui.modalParent,
								Gui.lang.getString("no_connections_avail"),
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

		if(actionCmd.equals("new_conn"))
		{
			int index = Configurator.getIntProperty("newConnection",0,"gui-forms");
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
				ConnectionForm connectionForm = new ConnectionForm(index,null);
			}
		}


		if(actionCmd.equals("delete"))
		{
			boolean selected = false;
			for(int i=0; i<myConnections.length;i++)
			{
				TableModel model = conTable.getModel();
				Boolean checked = (Boolean)model.getValueAt(i,0);
				if(Boolean.TRUE.equals(checked))
					selected=true;
			}
			int selectedRow = conTable.getSelectedRow();
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
			int selectedRow  = conTable.getSelectedRow();
			if(selectedRow>=0)
			{
				Connection con = (Connection)myConnections[selectedRow];
				int editConnectionIndex = Configurator.getIntProperty("editConnection",0,"gui-forms");
				JInternalFrame form=Gui.getForm(editConnectionIndex);
				if(form!=null && !form.isClosed())
				{
					int switch_frame = getEditOpenedDialog();
					if (switch_frame == JOptionPane.YES_OPTION)
					{
						formLocation = form.getLocation();
						form.dispose();
						ConnectionForm editConn = new ConnectionForm(editConnectionIndex, con, formLocation);
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
					ConnectionForm editConn = new ConnectionForm(editConnectionIndex, con, formLocation);
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
	/** Toggles all check boxes on action occured by "select all" - checkbox */
	private void selectAll()
	{
		if(selectAll.isSelected())
			for(int i=0;i<myConnections.length;i++)
			{
				conTable.setValueAt(new Boolean(true),i,0);
			}
		else
			for(int i=0;i<myConnections.length;i++)
			{
				conTable.setValueAt(new Boolean(false),i,0);
			}
	}
	/** Toggles all check boxes on action occured by "select all" - popup menu entry */
	private void selectAllPop()
	{
		if(!selectAll.isSelected())
			for(int i=0;i<myConnections.length;i++)
			{
				conTable.setValueAt(new Boolean(true),i,0);
			}
		else
			for(int i=0;i<myConnections.length;i++)
			{
				conTable.setValueAt(new Boolean(false),i,0);
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
			int row = this.conTable.rowAtPoint(e.getPoint());
			this.conTable.setRowSelectionInterval(row,row);
			ContextMenu pop = new ContextMenu(this.index, e.getX(),e.getY());
		}
		else if(this.conTable.columnAtPoint(e.getPoint())==conTable.getColumnCount()-1) //last column was klicked
		{
			int row = this.conTable.rowAtPoint(e.getPoint());
			changeActiveStatus(row);
		}
		else if (e.getClickCount() == 2)
			actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"edit"));
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
	 * @return Returns the conTable.
	 * 
	 * @uml.property name="conTable"
	 */
	public JTable getConTable() {
		return conTable;
	}

}
