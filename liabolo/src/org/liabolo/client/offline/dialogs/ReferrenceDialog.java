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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.forms.DefaultForm;
import org.liabolo.client.offline.forms.MetadataForm;
import org.liabolo.client.offline.forms.MetadataSearchForm;
import org.liabolo.common.Configurator;
import org.liabolo.common.TextItem;

/**
 * @author Admin
 *
 * This is an implementation of an upstreamed "add referrence"-dialog. This dialog will be called from
 * "add new metadata"-form only. The user can add referrence by
 *  searching existing metadata set or by adding new referrence as new metadata set.
 */
public class ReferrenceDialog  extends DefaultDialog{

	/** Instance of the form, that called this dialog. 
	 * It will be delegate directly on "add referrence"-dialog. 
	 * See also AddReferrence.java
	 */
	private MetadataForm sender;
	/** Array of available locations. This array will only be used, 
	 * if the option of adding new referrence metadata is chosen */
	private Object [] locationList;
	/** Array of available branches. This array will only be used, 
	 * if the option of adding new referrence metadata is chosen */
	private Object [] branchList;
	
	/**
	 * Creates a new upstreamed "add referrence"-dialog"
	 * @param sender Instance of the form, that called this dialog
	 * @param branchList  Array of available locations.
	 * @param locationList Array of available branches.
	 */
	public ReferrenceDialog(MetadataForm sender, Object[] branchList, Object[] locationList) 
	{	
		super(Gui.lang.getString("add_referrence"),Gui.lang.getString("options"),null);
		this.sender = sender;
		this.branchList = branchList;
		this.locationList = locationList;
		this.addContentPanel();
	}

	/** Layots and shows the dialog content */
	public void addContentPanel() 
	{		
		int border=5;
		double root_size[][] =
				{{border,
						TableLayout.PREFERRED,
							border}, // Columns
				 {border,
				 		TableLayout.PREFERRED, 
							TableLayout.PREFERRED,
								TableLayout.PREFERRED,
									border}}; // Rows
									
		TableLayout tl = new TableLayout(root_size);
		tl.setHGap(10);
		tl.setVGap(10);
		
		JPanel root = new JPanel(tl);
		//Insert-Button
		JButton insert_new = FormElement.getButton("insert_new", "images/insert.png", null, null, true);
		insert_new.setActionCommand("insert");
		insert_new.addActionListener(this);
		root.add(insert_new,"1,1,1,1,FULL,CENTER");
		
		//Cancel-Button
		JButton search_button = FormElement.getButton("search", "images/search.png", null, null, true);
		search_button.setActionCommand("search");
		search_button.addActionListener(this);
		root.add(search_button, "1,2,1,2 ,FULL,CENTER");
		
		//Cancel-Button
		JButton cancel_button = FormElement.getButton("cancel", "images/cancel.png", "close_wnd", null, true);
		cancel_button.setActionCommand("cancel");
		cancel_button.addActionListener(this);
		root.add(cancel_button, "1,3,1,3 ,FULL,CENTER");
	
		this.getContentPane().add(root);
		this.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension labelSize = this.getPreferredSize();
  		
		this.setLocation(screenSize.width/2 - (labelSize.width/2),
							screenSize.height/2 - (labelSize.height/2));
		this.setVisible(true);	

	}
	
	/** Invoked when an action occurs. */
	public void actionPerformed(ActionEvent e) 
	{
		String actionCmd = e.getActionCommand();
		
		if (actionCmd.equals("cancel")) 
		{
			this.dispose();
		}
		
		if (actionCmd.equals("insert")) 
		{	
			//TODO: erst gucken, ob "book" als Type angelegt ist, dann erst aufruffen
			ArrayList items = Gui.dispatcher.getMediaTypeItems(Gui.myLib, "book");
			if(items!=null)
			{
				this.setVisible(false);	
				this.dispose();
				AddReferrence addRef = new AddReferrence(sender, branchList, locationList);
			}
			else
			{
				this.setVisible(false);	
				this.dispose();
				DefaultForm.askToAddType("no_book_type_av");
			}			

		}
		if (actionCmd.equals("search"))
		{
			int searchIndex = Configurator.getIntProperty("searchMetadata",0,"gui-forms");
			JInternalFrame form=Gui.getForm(searchIndex);
			if(form!=null && !form.isClosed() )
			{
				form.pack();
				try {
					this.dispose();
					form.setSelected(true);	
					} 
					catch (java.beans.PropertyVetoException ev) {
					//System.out.println(ev);
					}	
			}
			else 
			{
				this.setVisible(false);	
				this.dispose();
				MetadataSearchForm metadataSearchForm = new MetadataSearchForm(searchIndex,null);
			
			}
		}
	}
}