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

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashSet;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.WaitDialog;
import org.liabolo.client.offline.forms.DefaultForm;
import org.liabolo.client.offline.forms.MetadataForm;
import org.liabolo.common.IndividualList;
import org.liabolo.common.LibItem;

/**
 * @author Jurij Henne
 *
 * This is an impementation of a "add metadata-set to individual list" dialog. 
 * The user can choose individual list shown in a list box and attach the actual metadata set to each of them by 
 * pressing ok button.
 */

public class AddToList  extends DefaultDialog implements  ListSelectionListener
{
	/** Instance of class, that called(creates) this form. 
	 * Actually this dialog can be called also by BrowseMetadataResults. In that case sender can be null.*/
	private MetadataForm sender;
	/** Array of metadata items, which should be added to selected individual lists */
	private Object[] itemsToAdd;
	/** Array of  available individual lists */
	private Object [] myLists;
	/** A gui-represented list of individual lists, the user can choose from */
	private JList lists;
	/**Instance of the  "select all" check box */
	private JCheckBox selectAll;

/**
 * Creates an new "add metadata-set to individual list" dialog 
 * 
 * @param lists The collection of  available individual lists
 * @param itemsToAdd The collection of metadata items, which should be added to selected individual lists
 * @param sender Instance of class , that called(creates) this form
 */
	public AddToList(Collection lists, Collection itemsToAdd, MetadataForm sender) 
	{	
		super(Gui.lang.getString("add_to_list"),Gui.lang.getString("add_list_tip"),null);
		this.itemsToAdd = itemsToAdd.toArray();
		this.myLists = Gui.dispatcher.sortIndividualList(lists,Gui.myLib).toArray();
		this.sender = sender;
		Object [] listNames = new Object[myLists.length];
			
		for(int i=0;i<myLists.length;i++)
		{
			listNames[i] = ((IndividualList)myLists[i]).getListName();
		}
		this.lists = new JList(listNames);
		this.lists.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.lists.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
			{
				if (e.getClickCount() == 2) 
					actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"submit"));
			}
		});
		this.lists.addListSelectionListener(this);
			
		if(selectAll==null)
		{
			selectAll = new JCheckBox(Gui.lang.getString("select_all"));
			selectAll.setToolTipText(Gui.lang.getString("select_all"));
			selectAll.setActionCommand("select_all");
			selectAll.addActionListener(this);
		} 
		
		this.addContentPanel();
	}
	
	/** Implemetation of inherited method. Creates  the layout of the shown dialog */
	public JPanel getDialogPanel()
	{			
		int border = 5;
		double size[][] =
				{{border,
						TableLayout.FILL, 
							border}, // Columns
				 {border, 
					TableLayout.FILL,
						TableLayout.PREFERRED,
				 	
						 border}}; 		// Rows
		TableLayout tl = new TableLayout(size);

		//Space between Columns and Rows
		tl.setHGap(5);
		tl.setVGap(5);
			
		JPanel panel = new JPanel(tl);
		/*		panel.setBorder
						(BorderFactory.createTitledBorder(
								BorderFactory.createEtchedBorder(BevelBorder.LOWERED), Gui.lang.getString("browse_branches")));
	*/
		JScrollPane scrollPane = new JScrollPane(lists);
		//scrollPane.setPreferredSize(new Dimension(200, 150));
		panel.add(scrollPane, "1,1,1,1,FULL,FULL");
		panel.add(selectAll,"1,2,1,2,LEFT,CENTER");
		
		return panel;
	}
	
	/**  Processes ok(save)-action, Implemetation of inherited method.*/
	public void OKAction(){
		
		boolean [] oks = null;
		if(lists.getSelectedIndex()<0)
		{
			DefaultForm.showNoListcontentSection();
		}
		else 
		{
			int [] selectedIndices = lists.getSelectedIndices();
			oks = new boolean[selectedIndices.length];
			Collection searchResult = new HashSet();
			WaitDialog.showDialog();
			for(int i=0;i<selectedIndices.length;i++)
			{
				IndividualList selectedList = (IndividualList)myLists[selectedIndices[i]];
				for(int j=0;j<itemsToAdd.length;j++)
				{
					String item_ID = ((LibItem)itemsToAdd[j]).getMetaData().getLiabolo_signature();
					selectedList.addItem(item_ID,"test"); //TODO: replace Description
				}
				oks[i] = Gui.dispatcher.editIndividualList(selectedList);


			}
			WaitDialog.closeDialog();
			
			boolean global_ok = true;
			for(int k=0; k<oks.length;k++)
			{	
				if(!oks[k])
					global_ok = oks[k];
			}
			if(global_ok)
			{
				this.dispose();
				DefaultForm.showConfirmMessage();
				if(sender!=null)
					sender.setAddedToList(true);
			}
			else 
			JOptionPane.showMessageDialog(
				Gui.modalParent,
					Gui.lang.getString("entry_error"),
						Gui.lang.getString("confirmation"),
							JOptionPane.CANCEL_OPTION);			
		}

	}

	/**  Invoked when list selection was changed. Implemetation of inherited method.*/
	public void valueChanged(ListSelectionEvent lse)
	{
		if(lists.getSelectedIndices().length == myLists.length) 
		 selectAll.setSelected(true);
		 else 
			selectAll.setSelected(false);
	}

	/** Invoked when an action occurs. */
	public void actionPerformed(ActionEvent e) 
	{
		String actionCmd = e.getActionCommand();

		if (actionCmd.equals("cancel")) 
		{
			closeForm();
		}
		
		if (actionCmd.equals("ok")) 
		{	
			this.OKAction();
		}
		
		if (actionCmd.equals("select_all")) 
		{
			if(selectAll.isSelected())
			{
				lists.setSelectionInterval(0,myLists.length-1);		
			}

			else
			{
				lists.removeSelectionInterval(0,myLists.length-1);
			}			
		}
	}
}