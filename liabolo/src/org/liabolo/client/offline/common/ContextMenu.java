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
package org.liabolo.client.offline.common;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.forms.BrowseBranch;
import org.liabolo.client.offline.forms.BrowseConnection;
import org.liabolo.client.offline.forms.BrowseList;
import org.liabolo.client.offline.forms.BrowseLocation;
import org.liabolo.client.offline.forms.BrowseMetadataResults;

/**
 * @author Maxim Bauer
 * The popup Menu, which is available with right mouse click
 */
public class ContextMenu extends JPopupMenu {

	/** Creates the popup menu
	 * @param index defined for which form the popoup menu is provided
	 * @param x x-coordinate to open the popup
	 * @param y y-coordinate to open the popup
	 * */	
	public ContextMenu(int index, int x, int y){
		
		super();
		
		switch(index)
		{
			/** For BrowseMetadataResults popup menu*/
			case 5  : 
			{
				showContentForBrowseMetadataResults(index,x,y);
				break;
			}
			case 23 :
			{
				showContentForBrowseMetadataResults(index,x,y);
				break;
			}
			/** For BroweBranch popup menu*/
			case 8 :
			{
				BrowseBranch bb = (BrowseBranch)Gui.my_forms[index];
				
				JMenuItem edit = new JMenuItem(Gui.lang.getString("edit"));
				edit.addActionListener(bb);
				edit.setActionCommand("edit");
				edit.setIcon(FormElement.createImageIcon("images/searchedit.png"));
				this.add(edit);
				
				JMenuItem delete = new JMenuItem(Gui.lang.getString("delete"));
				delete.addActionListener(bb);
				delete.setActionCommand("delete");
				delete.setIcon(FormElement.createImageIcon("images/delete.png"));
				delete.setEnabled(false);
				this.add(delete);
				this.addSeparator();

				JMenuItem branch_content = new JMenuItem(Gui.lang.getString("index"));
				branch_content.addActionListener(bb);
				branch_content.setActionCommand("branch_content");
				branch_content.setIcon(FormElement.createImageIcon("images/content.png"));
				this.add(branch_content);
				
				
				JMenuItem checkout = new JMenuItem(Gui.lang.getString("checkout"));
				checkout.addActionListener(bb);
				checkout.setActionCommand("checkout");
				checkout.setIcon(FormElement.createImageIcon("images/checkout2.png"));
				this.add(checkout);
				this.addSeparator();
				
				JMenuItem select = new JMenuItem(Gui.lang.getString("select_all"));
				select.addActionListener(bb);
				select.setActionCommand("select_all_pop");
				this.add(select);
				
				this.pack();
				//this.show(bb.branchTable,x+20,y+90);
				this.show(bb.getBranchTable(),x,y);
				break;
			}
			/**For BrowseConnection popup-menu*/
			case 17 :
			{
				BrowseConnection bc = (BrowseConnection)Gui.my_forms[index];
				
				JMenuItem edit = new JMenuItem(Gui.lang.getString("edit"));
				edit.addActionListener(bc);
				edit.setActionCommand("edit");
				edit.setIcon(FormElement.createImageIcon("images/searchedit.png"));
				this.add(edit);
				
				JMenuItem delete = new JMenuItem(Gui.lang.getString("delete"));
				delete.addActionListener(bc);
				delete.setActionCommand("delete");
				delete.setIcon(FormElement.createImageIcon("images/delete.png"));
				this.add(delete);
				this.addSeparator();
				
				JMenuItem select = new JMenuItem(Gui.lang.getString("select_all"));
				select.addActionListener(bc);
				select.setActionCommand("select_all_pop");
				this.add(select);

				this.pack();
				//this.show(bc,x+20,y+90);
				this.show(bc.getConTable(),x,y);
				break;
			}
			/**For BrowseList popup menu*/
			case 14 :
			{
				BrowseList bl = (BrowseList)Gui.my_forms[index];
				
				JMenuItem edit = new JMenuItem(Gui.lang.getString("edit"));
				edit.addActionListener(bl);
				edit.setActionCommand("edit");
				edit.setIcon(FormElement.createImageIcon("images/searchedit.png"));
				this.add(edit);
				
				JMenuItem delete = new JMenuItem(Gui.lang.getString("delete"));
				delete.addActionListener(bl);
				delete.setActionCommand("delete");
				delete.setIcon(FormElement.createImageIcon("images/delete.png"));
				this.add(delete);
				this.addSeparator();
				
				JMenuItem list_content = new JMenuItem(Gui.lang.getString("index"));
				list_content.addActionListener(bl);
				list_content.setActionCommand("list_content");
				list_content.setIcon(FormElement.createImageIcon("images/content.png"));
				this.add(list_content);
				
				JMenuItem export = new JMenuItem(Gui.lang.getString("export"));
				export.addActionListener(bl);
				export.setActionCommand("export");
				export.setIcon(FormElement.createImageIcon("images/export.png"));
				this.add(export);
				this.addSeparator();
				
				JMenuItem select = new JMenuItem(Gui.lang.getString("select_all"));
				select.addActionListener(bl);
				select.setActionCommand("select_all_pop");
				this.add(select);

				this.pack();
				//this.show(bl,x+20,y+90);
				this.show(bl.getListTable(),x,y);
				break;
			}
			/**For BrowseLocation popup menu*/
			case 11 :
			{
				BrowseLocation bl = (BrowseLocation)Gui.my_forms[index];
				
				JMenuItem edit = new JMenuItem(Gui.lang.getString("edit"));
				edit.addActionListener(bl);
				edit.setActionCommand("edit");
				edit.setIcon(FormElement.createImageIcon("images/searchedit.png"));
				this.add(edit);
				
				JMenuItem delete = new JMenuItem(Gui.lang.getString("delete"));
				delete.addActionListener(bl);
				delete.setActionCommand("delete");
				delete.setEnabled(false);
				delete.setIcon(FormElement.createImageIcon("images/delete.png"));
				this.add(delete);
				this.addSeparator();
				
				JMenuItem location_content = new JMenuItem(Gui.lang.getString("index"));
				location_content.addActionListener(bl);
				location_content.setActionCommand("location_content");
				location_content.setIcon(FormElement.createImageIcon("images/content.png"));
				this.add(location_content);
				
				JMenuItem select = new JMenuItem(Gui.lang.getString("select_all"));
				select.addActionListener(bl);
				select.setActionCommand("select_all_pop");
				this.add(select);

				this.pack();
				//this.show(bl,x+20,y+90);
				this.show(bl.getLocTable(),x,y);
				break;
			}
		}
		
	}
	
	/** 
	 * Creates and shows the popup-menu for BrowseMetadataResults form
	 * @param index defined for which form the popoup menu is provided
	 * @param x x-coordinate to open the popup
	 * @param y y-coordinate to open the popup
	 * */
	private void showContentForBrowseMetadataResults(int index, int x, int y)
	{
		BrowseMetadataResults md = (BrowseMetadataResults)Gui.my_forms[index];
		
		JMenuItem edit = new JMenuItem(Gui.lang.getString("edit"));
		edit.addActionListener(md);
		edit.setActionCommand("edit");
		edit.setIcon(FormElement.createImageIcon("images/edit.png"));
		this.add(edit);
		
		JMenuItem delete = new JMenuItem(Gui.lang.getString("delete"));
		delete.addActionListener(md);
		delete.setActionCommand("delete");
		delete.setIcon(FormElement.createImageIcon("images/delete.png"));
		this.add(delete);
		this.addSeparator();
		
		JMenuItem tolist = new JMenuItem(Gui.lang.getString("tolist"));
		tolist.addActionListener(md);
		tolist.setActionCommand("tolist");
		tolist.setIcon(FormElement.createImageIcon("images/attach.png"));
		this.add(tolist);
		
		
		JMenuItem referens = new JMenuItem(Gui.lang.getString("add_referrence"));
		referens.addActionListener(md);
		referens.setActionCommand("refer");
		referens.setIcon(FormElement.createImageIcon("images/refer.png"));
		this.add(referens);
		this.addSeparator();

		JMenuItem print_preview = new JMenuItem(Gui.lang.getString("print_preview"));
		print_preview.addActionListener(md);
		print_preview.setActionCommand("print_preview");
		print_preview.setIcon(FormElement.createImageIcon("images/print_preview.png"));
		this.add(print_preview);
		
		
		
		if(md.getBrowseType() == BrowseMetadataResults.BROWSE_RESULTS)
		{
			JMenuItem checkout = new JMenuItem(Gui.lang.getString("checkout"));
			checkout.addActionListener(md);
			checkout.setActionCommand("checkout");
			checkout.setIcon(FormElement.createImageIcon("images/checkout2.png"));
			this.add(checkout);
			this.addSeparator();
		}
		
		else
		{
			JMenuItem clear = new JMenuItem(Gui.lang.getString("clear_cb"));
			clear.addActionListener(md);
			clear.setActionCommand("clear_cb");
			clear.setIcon(FormElement.createImageIcon("images/clear.png"));
			this.add(clear);
			this.addSeparator();
			
			JMenuItem update = new JMenuItem(Gui.lang.getString("update_cb"));
			update.addActionListener(md);
			update.setActionCommand("update_cb");
			update.setIcon(FormElement.createImageIcon("images/update.png"));
			this.add(update);
			
			
			JMenuItem commit = new JMenuItem(Gui.lang.getString("commit_cb"));
			commit.addActionListener(md);
			commit.setActionCommand("commit_cb");
			commit.setIcon(FormElement.createImageIcon("images/commit.gif"));
			this.add(commit);
			this.addSeparator();
			
			if(md.getBrowseType() == BrowseMetadataResults.BROWSE_EDITED_CLIPBOARD)
			{
				update.setEnabled(false);
			}
			
			if(md.getBrowseType() != BrowseMetadataResults.BROWSE_EDITED_CLIPBOARD)
			{
				commit.setEnabled(false);
			}
			
			if(md.getBrowseType()!= BrowseMetadataResults.BROWSE_CLIPBOARD)
			{
				clear.setEnabled(false);
			}
			
		}
		
		JMenuItem select = new JMenuItem(Gui.lang.getString("select_all"));
		select.addActionListener(md);
		select.setActionCommand("select_all_pop");
		this.add(select);
		
		this.pack();
		//this.show(md,x+20,y+90);
		this.show(md.getResultTable(),x,y);
	}
}
