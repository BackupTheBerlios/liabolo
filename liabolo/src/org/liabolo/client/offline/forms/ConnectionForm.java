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

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.common.Configurator;
import org.liabolo.common.Connection;

/**
 * @author Jurij Henne
 *
 * This is an impementation of  "add new connection" and "edit connection" forms. 
 * The user can add new connection to config file.
 * The same class is used to edit existing connection. 
 */

public class ConnectionForm extends DefaultForm implements ActionListener, KeyListener {

	/** Necessary input field */
	private JTextField connName;
	/** Necessary input field */
	private JTextField connDB_uri;
	/** Necessary input field */
	private JTextField connUser;
	/** Necessary input field */
	private JTextField connPass;
	/** Necessary input field */
	private JTextField connDriver;
	/** Indicates if the new connection should be action on connect */
	private JCheckBox active;
	/** Connection object for edit */
	private Connection editedConnection;

/**
 * 
 * @param index
 * @param formLocation
 */

	public ConnectionForm(int index, Point formLocation) {
		super(index, Gui.lang.getString("add_connection"),"images/connection.png");
		this.initTextFields();
		this.addFormFrame(null);
	}

	public ConnectionForm(int index, Connection connection, Point formLocation)
	{
		super(index, Gui.lang.getString("edit_list"),"images/edit.png");
		this.editedConnection = connection;
		this.initTextFields();
		this.addFormFrame(formLocation);
	}
	private void initTextFields() 
	{
		if(editedConnection!=null)
		{
			connName = new JTextField(editedConnection.getName(),20);connName.addKeyListener(this);
			connDB_uri = new JTextField(editedConnection.getDbURI(),20);connDB_uri.addKeyListener(this);
			connUser = new JTextField(editedConnection.getUsername(),20);connUser.addKeyListener(this);
			connPass = new JTextField(editedConnection.getPassword(),20);connPass.addKeyListener(this);
			connDriver = new JTextField(editedConnection.getDriver(),30);connDriver.addKeyListener(this);
			active = new JCheckBox();
			active.setSelected(editedConnection.isActive());
		}
		else
		{
			//System.out.println("LEEREN");
			connName = new JTextField(20);connName.addKeyListener(this);
			connDB_uri = new JTextField(20);connDB_uri.addKeyListener(this);
			connUser = new JTextField(20);connUser.addKeyListener(this);
			connPass = new JTextField(20);connPass.addKeyListener(this);
			connDriver = new JTextField(30);connDriver.addKeyListener(this);
			active = new JCheckBox();
			active.setSelected(true);
		}
	}

	public JPanel showFormContent() {
		JPanel root = getRootPanel(); //inherited
		int border = 5;

		double size[][] = {
				{border, TableLayout.PREFERRED, 
					TableLayout.PREFERRED, border}, // Columns
						{border, TableLayout.PREFERRED, 
									TableLayout.PREFERRED,
										TableLayout.PREFERRED, 
											TableLayout.PREFERRED,
												TableLayout.PREFERRED,
													TableLayout.PREFERRED, border}}; // Rows

		TableLayout tl = new TableLayout(size);
		//Space between Columns and Rows
		tl.setHGap(5);
		tl.setVGap(5);

		JPanel panel = new JPanel(tl);
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(BevelBorder.LOWERED), Gui.lang
				.getString("add_connection")));

		JLabel message = FormElement.getExtLabel("name", Font.PLAIN, null);
		
		panel.add(message, "1,1,1,1,RIGHT,CENTER");
		panel.add(connName, "2,1,2,1,FULL,CENTER");

		message = FormElement.getExtLabel("db_uri", Font.PLAIN, null);
		panel.add(message, "1,2,1,2,RIGHT,CENTER");
		panel.add(connDB_uri, "2,2,2,2,FULL,CENTER");

		message = FormElement.getExtLabel("user", Font.PLAIN, null);
		panel.add(message, "1,3,1,3,RIGHT,CENTER");
		panel.add(connUser, "2,3,2,3,FULL,CENTER");

		message = FormElement.getExtLabel("password", Font.PLAIN, null);
		panel.add(message, "1,4,1,4,RIGHT,CENTER");
		panel.add(connPass, "2,4,2,4,FULL,CENTER");
		
		message = FormElement.getExtLabel("driver", Font.PLAIN, null);
		panel.add(message, "1,5,1,5,RIGHT,CENTER");
		panel.add(connDriver, "2,5,2,5,FULL,CENTER");
		
		message = FormElement.getExtLabel("activ", Font.PLAIN, null);
		panel.add(message, "1,6,1,6,RIGHT,CENTER");
		panel.add(active, "2,6,2,6,FULL,CENTER");

		root.add(panel, "1,1,3,1");

		//OK-Button
		JButton ok_button = FormElement.getButton("ok", "images/ok.png",
				"save_connection", null, true);
		ok_button.setActionCommand("ok");
		ok_button.addActionListener(this);
		ok_button.addKeyListener(this);
		root.add(ok_button, "1,2,1,2,RIGHT,CENTER");

		//Cancel-Button
		JButton cancel_button = FormElement.getButton("cancel",
				"images/cancel.png", "close_wnd", null, true);
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

	public void saveForm() 
	{
		boolean ok = false;
		if(editedConnection!=null)
		{
			editedConnection.setName(connName.getText());
			editedConnection.setDbURI(connDB_uri.getText());
			editedConnection.setUsername(connUser.getText());
			editedConnection.setPassword(connPass.getText());
			editedConnection.setDriver(connDriver.getText());

			editedConnection.setActive(active.isSelected());
			//TODO;System.out.println("ConnectionForm:vor edited con");

			ok = Gui.dispatcher.editConnection(editedConnection);
			//TODO: dispatcher liefert immer false;
			if(ok)
				Gui.my_forms[index].dispose();
		}
		else
		{

			Connection con = new Connection(connName.getText(),
												connDB_uri.getText(),
													connUser.getText(),
														connPass.getText(),
															connDriver.getText(),
																active.isSelected());

			//TODO;
			 System.out.println(connName.getText()+"/"+connDB_uri.getText()+"/"+connUser.getText()+"/"+connPass.getText()+"/"+connDriver.getText());

			ok = Gui.dispatcher.addConnection(con);
			if(ok)
			{		
				initTextFields();
				Point location = Gui.my_forms[index].getLocation();
				this.updateFormFrame(location);
				
			}
		}
		if(ok)
		{
			//BrowseConnection is opened, update view
			int browseFormIndex = Configurator.getIntProperty("browseConnection",0,"gui-forms");
			BrowseConnection browseForm=(BrowseConnection)Gui.getForm(browseFormIndex);
			if(browseForm!=null && !browseForm.isClosed())
			{
				Point browseFormLocation = browseForm.getLocation();
				browseForm.dispose();
				BrowseConnection browseConnection = new BrowseConnection(browseFormIndex, browseFormLocation);
			}	
			if(this!=null && !this.isClosed())
				try {this.setSelected(true);}
				catch (java.beans.PropertyVetoException ev) {}	
			if(Gui.showConfirmations)
				showSuccessMessage();					
		}
	}

	public void actionPerformed(ActionEvent e) 
	{
		String actionCmd = e.getActionCommand();
		if(actionCmd.equals("ok")) 
		{
			//TODO
			//Gui.statusBar.setReceiverMessage("Deaktiviert, da fehlerhaft");
			saveForm();
		}
		
		if(actionCmd.equals("cancel")) 
		{
			closeForm();
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent event) 
	{
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE) 
		{
			actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,"cancel"));
		}
		if (event.getKeyCode() == KeyEvent.VK_ENTER)
		{
			actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,"ok"));
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}