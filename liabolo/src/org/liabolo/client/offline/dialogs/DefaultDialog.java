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
package org.liabolo.client.offline.dialogs;

import info.clearthought.layout.TableLayout;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.forms.DefaultForm;



/**
 * @author Jurij Henne
 *
 * An implementation of default dialog type, that can be inherited by all other custom dialogs.
 * Default dialog can contain only one component with selectable items.
 * 
 */
public class DefaultDialog  extends JDialog implements ActionListener{
	
	/** Title in the header of the dialog */
	public String title;
	/** If any tips should be shown, place them here */
	public String labelTip;
	/** Combo box component */
	public JComboBox lists;	

	/**
	 *  Creates an new default dialog with specified title, tip tex and selectable component 
	 * @param title Title in the header of the dialog
	 * @param labelTip If any tips should be shown, place them here
	 * @param lists Combo box component
	 */
	public DefaultDialog(String title, String labelTip, JComboBox lists) 
	{	
		super(Gui.modalParent,true);
		this.title =  title;
		this.setTitle(this.title);
		this.labelTip = labelTip;
		this.lists = lists;
	}
	
	
	/**
	 *  Creates an new default dialog with specified title 
	 * @param title Title in the header of the dialog
	 */
	public DefaultDialog(String title) 
	{	
		super(Gui.modalParent,true);
		this.title =  title;
		this.setTitle(this.title);
	}

	
	public void addContentPanel() 
	{		
		int border=5;
		double root_size[][] =
				{{border,TableLayout.PREFERRED,
					TableLayout.PREFERRED,border}, // Columns
				 {border,TableLayout.PREFERRED, 
				 	TableLayout.PREFERRED,
				 		10,TableLayout.PREFERRED,border}}; // Rows
		TableLayout tl = new TableLayout(root_size);
		tl.setHGap(10);
		tl.setVGap(10);
		
		JPanel root = new JPanel(tl);
		
		JLabel tiplogo = new JLabel(this.labelTip);
		root.add(tiplogo,"1,1,2,1,CENTER,CENTER");
		
		root.add(getDialogPanel(),"1,2,2,2,FULL,FULL");
		
		//OK-Button
		JButton ok_button = FormElement.getButton("ok", "images/ok.png", "save", null, true);
		ok_button.setActionCommand("ok");
		ok_button.addActionListener(this);
		root.add(ok_button, "1,4,1,4,FULL,CENTER");

		//Cancel-Button
		JButton cancel_button = FormElement.getButton("cancel", "images/cancel.png", "close_wnd", null, true);
		cancel_button.setActionCommand("cancel");
		cancel_button.addActionListener(this);
		root.add(cancel_button, "2,4,2,4 ,FULL,CENTER");
	
		this.getContentPane().add(root);
		this.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  		Dimension labelSize = this.getPreferredSize();
  		
  		this.setLocation(screenSize.width/2 - (labelSize.width/2),
				 			screenSize.height/2 - (labelSize.height/2));
		this.setVisible(true);	

	}
	public void updateDialog()
	{
		this.getContentPane().removeAll();
		addContentPanel() ;
		this.getContentPane().update(this.getContentPane().getGraphics());
		
	}
	
	public JPanel getDialogPanel()
	{
		return new JPanel();
	}
	public void OKAction(){
	}
	
	public void closeForm()
	{
		if(Gui.showWarnings)
		{
		int switch_frame = DefaultForm.getFormCloseDialog();
		if (switch_frame == JOptionPane.YES_OPTION)
			this.dispose();
		}
		else
			this.dispose();
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		String actionCmd = e.getActionCommand();
		
		if (actionCmd.equals("cancel")) 
		{
			this.closeForm();
		}
		
		if (actionCmd.equals("ok")) 
		{	
			this.OKAction();
		}
	}
	
	
}