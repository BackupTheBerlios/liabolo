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
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.common.WaitDialog;
import org.liabolo.common.Branch;
import org.liabolo.common.Configurator;

/**
 * @author Jurij Henne
 *
 * This is an impementation of  "add new branch" and "edit branch" forms. The user can add new branches to database.
 * The same class is used to edit existing branches. 
 */


public class BranchForm extends DefaultForm implements ActionListener, KeyListener {

	/** Description of the branch. Usually its just the name of the branch*/
    private JTextField branchDescription;
	/** Abreaviation of the branch. */
    private JTextField branchAbreviation;
	/** Instance of brach, that should be edit */
  	private Branch editedBranch;
	/** Row of the  "browse branch" table, that should be edit. Used for update purposes. See also BrowseBranch.java. */
  	private int editedRow;

	/**
	 * Creates a new "add new branch"-form 
	 * @param index specifies a unique 'position' of  the form in form holder-array 
	 * @param formLocation Location of the form on application desktop
	 */
	public BranchForm(int index, Point formLocation)
	{
		super(index, Gui.lang.getString("add_branch"),"images/branch2.png");
		this.branchDescription = new JTextField(25);
		this.branchAbreviation = new JTextField(5);
		this.addFormFrame(formLocation);
	}
	

	
	/**
	 * Creates a new "editbranch"-form. Note: the abreviation of a branch can not be changed
	 * @param index specifies a unique 'position' of  the form in form holder-array 
	 * @param branch Instance of the brach object, that should be edit.
	 * @param row Row of the  "browse branch" table, that should be edit
	 * @param formLocation Location of the form on application desktop
	 */
	public BranchForm(int index, Branch branch, int row, Point formLocation)
	{
		super(index, Gui.lang.getString("edit_branch"),"images/edit.png");
		this.editedBranch = branch;
		this.editedRow = row;
		branchDescription = new JTextField(branch.getDescription(),25);
		branchAbreviation = new JTextField(branch.getAbbreviation(),5);
		branchAbreviation.setEnabled(false);
		this.addFormFrame(formLocation);
	}

	/** Implemetation of inherited method. Returns the layouted  content panel of the form */
    public JPanel showFormContent()
    {
       JPanel root = getRootPanel(); //inherited
       int  border = 5;
        // Creating the Grid for TableLayout
        double size[][] =
                {{border,
					TableLayout.PREFERRED,
						TableLayout.PREFERRED,
							border}, // Columns
                 {border,
					TableLayout.PREFERRED,
						TableLayout.PREFERRED,
							TableLayout.PREFERRED, //Connections-CB
                 			border}}; 		// Rows

        TableLayout tl = new TableLayout(size);
        //Space between Columns and Rows
        tl.setHGap(5);
        tl.setVGap(5);

		JPanel panel = new JPanel(tl);

		if(this.editedBranch==null) // Add new List
		{
	        panel.setBorder
	                (BorderFactory.createTitledBorder(
	                        BorderFactory.createEtchedBorder(BevelBorder.LOWERED), Gui.lang.getString("add_branch")));
		}
		else
		{
			panel.setBorder
					(BorderFactory.createTitledBorder(
							BorderFactory.createEtchedBorder(BevelBorder.LOWERED), Gui.lang.getString("edit_branch")));
		}

        JLabel branch = FormElement.getExtLabel("branch", Font.PLAIN, null);
        panel.add(branch, "1,1,1,1,RIGHT,CENTER");
        branchDescription.addKeyListener(this);
        panel.add(branchDescription, "2,1,2,1,LEFT,CENTER");

		JLabel branch_descr = FormElement.getExtLabel("abreviation", Font.PLAIN, null);
        panel.add(branch_descr, "1,2,1,2,RIGHT,CENTER");
		branchAbreviation.addKeyListener(this);
        panel.add(branchAbreviation, "2,2,2,2,LEFT,CENTER");

/*
		JLabel av_conns = FormElement.getExtLabel("savein", Font.PLAIN, null);
		panel.add(av_conns, "1,3,1,3,RIGHT,CENTER");
		connections.addKeyListener(this);
		panel.add(connections, "2,3,2,3,LEFT,CENTER");
*/
        root.add(panel, "1,1,3,1");

        //OK-Button
        JButton ok_button = FormElement.getButton("ok", "images/ok.png", "save_branch", null, true);
        ok_button.setActionCommand("ok");
        ok_button.addActionListener(this);
		ok_button.addKeyListener(this);
        root.add(ok_button, "1,2,1,2,RIGHT,CENTER");

        //Cancel-Button
        JButton cancel_button = FormElement.getButton("cancel", "images/cancel.png", "close_wnd", null, true);
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
	 * Saves new or edited branch to database
	 *
	 */
	private void saveForm()
	{
		boolean ok = false;
		if(legalInput())
		{
			if(this.editedBranch!=null)// Edit Branch
			{
				editedBranch.setDescription(branchDescription.getText());
				ok = Gui.dispatcher.editBranch(editedBranch);
				if(ok)
					Gui.my_forms[index].dispose();

			}
			else	// Add Branch
			{
				//Library saveIn = (Library)myConnectedLibraries[connections.getSelectedIndex()];

				//if(saveIn!=null)
				//{
					ok = Gui.dispatcher.addBranch(new Branch(branchAbreviation.getText(),branchDescription.getText()),Gui.myLib);
				//}

				if(ok)
				{
					branchDescription = new JTextField(25);
					branchAbreviation = new JTextField(5);
					Point location = Gui.my_forms[index].getLocation();
					this.updateFormFrame(location);
				}
			}
			if(ok)
			{
				//BrowseList is opened, update view
				int browseFormIndex = Configurator.getIntProperty("browseBranch",0,"gui-forms");
				BrowseBranch browseForm=(BrowseBranch)Gui.getForm(browseFormIndex);
				if(browseForm!=null && !browseForm.isClosed())
				{
					//browseForm.updateEditedEntry(editedBranch,editedRow);
					Point browseFormLocation = browseForm.getLocation();
					browseForm.dispose();
					BrowseBranch browseBranch = new BrowseBranch(browseFormIndex,browseFormLocation);

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
		if(branchDescription.getText().equals("") ||branchAbreviation.getText().equals(""))
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
    			TreeForm.updateTreeForm(1,null,null);
    			WaitDialog.closeDialog();
    			try 
				{
					this.setSelected(true);
				} catch (PropertyVetoException e1) {		
					e1.printStackTrace();
				}
        	}
		}

		if(actionCmd.equals("cancel"))
		{
			closeForm();
		}
	}

	public void keyPressed (KeyEvent event)
	{
		 if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
		 	actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));

		 if (event.getKeyCode() == KeyEvent.VK_ENTER)
			actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"ok"));
	}

	public void keyReleased(KeyEvent event){}
	public void keyTyped(KeyEvent event){}



}