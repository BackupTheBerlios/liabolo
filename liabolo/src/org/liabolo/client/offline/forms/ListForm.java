/*
 * Created on 15.01.2004
 * 
 * Copyright (c) Projektgruppe P30 Uni Oldenburg Germany
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; see the file COPYING. If not, write to the Free Software
 * Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 * 
 *  
 */

package org.liabolo.client.offline.forms;

import info.clearthought.layout.TableLayout;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.common.WaitDialog;
import org.liabolo.common.Configurator;
import org.liabolo.common.IndividualList;

/**
 * @author Jurij Henne
 *
 * This is an impementation of  "add new ind. list" and "edit ind. list" forms. The user can add new ind.list to database.
 * The same class is used to edit existing ind.lists. 
 */

public class ListForm extends DefaultForm implements  ActionListener, KeyListener
{
	/** Description of the ind.list. Usually its just the name of the ind.list*/
	private JTextField listName;
	/** Description of the ind.list.*/
	private JTextArea listDescription;
	/** Instance of ind.list, that should be edit */
	private IndividualList editedList;
	/** Row of the  "browse list" table, that should be edit. Used for update purposes. See also BrowseList.java. */
	private int editedRow;

	/**
	 * Creates a new "add new ind.list"-form 
	 * @param newListIndex specifies a unique 'position' of  the form in form holder-array 
	 * @param formLocation Location of the form on application desktop
	 */
	public ListForm(int newListIndex, Point formLocation)
	{
		super(newListIndex, Gui.lang.getString("add_list"),"images/mylists.png");
		listName = new JTextField(25);
		listDescription = new JTextArea(5,20);
		this.addFormFrame(formLocation);
	}
	
	/**
	 * Creates a new "editlist"-form. 
	 * @param newListIndex specifies a unique 'position' of  the form in form holder-array 
	 * @param editedList Instance of the IndividualList object, that should be edit.
	 * @param row Row of the  "browse branch" table, that should be edit
	 * @param formLocation Location of the form on application desktop
	 */
	public ListForm(int newListIndex, IndividualList editedList, int row,  Point formLocation)
	{
		super(newListIndex, Gui.lang.getString("edit_list"),"images/edit.png");
		this.editedList = editedList;
		this.editedRow = row;
		listName = new JTextField(editedList.getListName(),25);
		listName.setEnabled(false);
		listDescription = new JTextArea(editedList.getDescription(),5,20); //TODO:
		this.addFormFrame(formLocation);
	}
	
	/** Implemetation of inherited method. Returns the layouted  content panel of the form */
	public JPanel showFormContent()
	{	
		JPanel root = getRootPanel(); //inherited		
		int border = 5;	
		// Creating the Grid for TableLayout
		double size[][] =
			{{border, 
				TableLayout.PREFERRED, 
					TableLayout.PREFERRED, 
						border},  // Columns
			 {border,
			 	TableLayout.PREFERRED,
			 		TableLayout.PREFERRED, 
			 			border}}; 		// Rows	
		TableLayout tl = new TableLayout(size);
		
		//Space between Columns and Rows 
		tl.setHGap(5);
		tl.setVGap(5);

		JPanel panel = new JPanel(tl);
		
		if(this.editedList==null) // Add new List
		{
			panel.setBorder
					(BorderFactory.createTitledBorder(
							BorderFactory.createEtchedBorder(BevelBorder.LOWERED), Gui.lang.getString("add_list")));
		}
		else
		{
			panel.setBorder
					(BorderFactory.createTitledBorder(
							BorderFactory.createEtchedBorder(BevelBorder.LOWERED), Gui.lang.getString("edit_list")));
		}		

		JLabel label = FormElement.getExtLabel("name",Font.PLAIN, null);
		panel.add(label,"1,1,1,1,RIGHT,CENTER");
		listName.addKeyListener(this);
		panel.add(listName,"2,1,2,1,FULL,CENTER");
		label = FormElement.getExtLabel("description",Font.PLAIN, null);
		panel.add(label,"1,2,1,2,RIGHT,TOP");
		
		listDescription.setLineWrap(true);
		listDescription.addKeyListener(this);
		JScrollPane sp = new JScrollPane(listDescription);
		sp.setPreferredSize(new Dimension(150,50));
		
		panel.add(sp,"2,2,2,2,FULL,CENTER");
		root.add(panel,"1,1,3,1,");

		//OK-Button
		JButton ok_button = FormElement.getButton("ok","images/ok.png","save_list", null, true);
		ok_button.setActionCommand("ok");
		ok_button.addActionListener(this);
		ok_button.addKeyListener(this);
		root.add(ok_button, "1,2,1,2,RIGHT,CENTER");

		//Cancel-Button
		JButton cancel_button = FormElement.getButton("cancel","images/cancel.png","close_wnd", null, true);
		cancel_button.setActionCommand("cancel");
		cancel_button.addActionListener(this);
		cancel_button.addKeyListener(new KeyAdapter(){
		public void keyPressed(KeyEvent event){
			if (event.getKeyCode() == KeyEvent.VK_ESCAPE || event.getKeyCode() == KeyEvent.VK_ENTER){
				actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));
			}
		}
		});
		root.add(cancel_button, "3,2,3,2 ,LEFT,CENTER");
		return root;
	}
	
	/**
	 * Saves new or edited ind.list to database
	 *
	 */
	private void saveForm()
	{
		boolean ok = false;
		if(legalInput())
		{
			if(this.editedList!=null) // Save edited List
			{
				editedList.setDescription(this.listDescription.getText());
				ok = Gui.dispatcher.editIndividualList(editedList);
				if(ok)
					Gui.my_forms[index].dispose();
			}
			else // Add new List
			{
				IndividualList list = new IndividualList(listName.getText(),listDescription.getText());
				ok = Gui.dispatcher.addIndividualList(list);
				if(ok)
				{
					listName = new JTextField(25);
					listDescription = new JTextArea(5,20);
					Point location = Gui.my_forms[index].getLocation();
					this.updateFormFrame(location);										
				}
			}
			if(ok)
			{
				int browseFormIndex = Configurator.getIntProperty("browseList",0,"gui-forms");
				BrowseList browseForm=(BrowseList)Gui.getForm(browseFormIndex);
				if(browseForm!=null && !browseForm.isClosed())
				{
					//browseForm.updateEditedEntry(editedList,editedRow);
					Point browseFormLocation = browseForm.getLocation();
					browseForm.dispose();
					BrowseList browseList = new BrowseList(browseFormIndex, browseFormLocation);
				}
				if(this!=null && !this.isClosed())
					try {this.setSelected(true);}
					catch (java.beans.PropertyVetoException ev) {}		
					
				if(Gui.showConfirmations)
					showSuccessMessage();			
			}
		}
	}
	
	/**
	 * Takes care of user input errors. It is only a dummy and not proper implemented yet
	 * @return
	 */
	private boolean legalInput()
	{
		if(listName.getText().equals(""))
		{
			showInputErrorMessage();
			return false;	
		}
		else 
			return true;	
	}
	
	/** Invoked when an action occurs. */
	public void actionPerformed(ActionEvent e) 
	{
		String actionCmd = e.getActionCommand();
		if(actionCmd.equals("ok")) 
		{
			saveForm();
			/** Update TreeForm*/
			if(Gui.automaticUpdate)
        	{
            	WaitDialog.showDialog();
    			TreeForm.updateTreeForm(3,null,null);
    			WaitDialog.closeDialog();
    			try 
				{
					this.setSelected(true);
				} catch (PropertyVetoException e1) 
				{
					e1.printStackTrace();
				}
        	}
		}
		
		if(actionCmd.equals("cancel")) 
		{
			closeForm();
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
		 	actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));
		 
		if (event.getKeyCode() == KeyEvent.VK_ENTER)
			actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"ok"));
	

	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}