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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.BackupFileFilter;
import org.liabolo.client.offline.common.FormElement;

/**
 * @author Maxim Bauer
 * The Form enables to backup and restore the Database.
 * */

public class BackupForm extends DefaultForm implements ActionListener, ChangeListener {
	
	/** Divide the form in save and restore forms */
	private JTabbedPane tabbedPane;
	/** The Filechooser dialogs*/
	private JFileChooser fcr;
	/** The Filechooser dialogs*/
	private JFileChooser fcs;
	/** Instance of BackupFileFilter*/
	final BackupFileFilter ff;
	/** Indicates which part of Tabbed pane is selected*/
	private int selectedIndex;

	/** Creates Backup/Restore form
	*
	* @param BRindex specifies a unique 'position' of  the form in form holder-array 
	* 
	*/
	public BackupForm(int BRIndex) 
	{	
		super(BRIndex, Gui.lang.getString("backup_restore"),"images/backup_restore.png");
		ff = new BackupFileFilter("xml");	
		tabbedPane = new JTabbedPane();
		this.addFormFrame(null);
	}
	/** Implemetation of inherited method. Returns the layouted  content panel of the form */
	public JPanel showFormContent() 
	{
		JPanel root = new JPanel();
		JPanel savePanel = getSavePanel();
		JPanel restorePanel = getRestorePanel();
		tabbedPane.add(Gui.lang.getString("save"),savePanel);
		tabbedPane.add(Gui.lang.getString("restore"),restorePanel);
		tabbedPane.setIconAt(0,FormElement.createImageIcon("images/backup.png"));
		tabbedPane.setIconAt(1,FormElement.createImageIcon("images/restore.png"));
		tabbedPane.addChangeListener(this);
		tabbedPane.setFocusable(false);
		root.add(tabbedPane);
			
		return root;      
	}
  	/** Creates backup panel
  	 * @return root backup panel
  	 * */
	protected JPanel getSavePanel()
	{
		JPanel root = new JPanel();
		fcs = new JFileChooser();
		ff.accept(new File(""));
		ff.getDescription();
	
		fcs.addChoosableFileFilter(ff);
		fcs.setDialogType(JFileChooser.SAVE_DIALOG);
		fcs.addActionListener(this);
		
		root.add(fcs);
		return root;
	}
	/** 
	 * Creates restore panel
	 * @return restore panel
	 * */
	protected JPanel getRestorePanel()
	{
		JPanel root = new JPanel();
		fcr = new JFileChooser();
		ff.accept(new File(""));
		ff.getDescription();
		fcr.addChoosableFileFilter(ff);
		fcr.setDialogType(JFileChooser.OPEN_DIALOG);
		fcr.addActionListener(this);
		root.add(fcr);
		return root;
	}
	
	/** 
	 * Set the current panel 
	 * @param index Gives the kind of panel
	 * */
	public void setSelectedPanel(int index) 
	{
		tabbedPane.setSelectedIndex(index);
	}
	
	/** 
	 * Switch between the two TabbedPanes
	 * */
	public void stateChanged(ChangeEvent e)
	{
		this.selectedIndex = tabbedPane.getSelectedIndex();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 *  Invoked when an action occurs. 
	 */
	public void actionPerformed(ActionEvent e) 
	{
		String actionCmd = e.getActionCommand();
		
		if(e.getActionCommand() == JFileChooser.CANCEL_SELECTION)
			Gui.my_forms[index].dispose();
		
		if(e.getActionCommand() == JFileChooser.APPROVE_SELECTION )
		{
			if(this.selectedIndex==1)
			{
				boolean restored = Gui.dispatcher.restore(fcr.getSelectedFile().getPath(), "xmldb:exist:///db");
				if (restored){
					JOptionPane.showMessageDialog(
							Gui.modalParent,
								Gui.lang.getString("restore_success"),
									Gui.lang.getString("confirmation"),
										JOptionPane.CANCEL_OPTION);	
				}
				else {
					JOptionPane.showMessageDialog(
							Gui.modalParent,
								Gui.lang.getString("restore_failure"),
									Gui.lang.getString("confirmation"),
										JOptionPane.CANCEL_OPTION);	
				}
			}
			else
			{
				boolean backuped =Gui.dispatcher.backup(fcs.getSelectedFile().getPath(), "xmldb:exist:///db");
				if(backuped){
						JOptionPane.showMessageDialog(
								Gui.modalParent,
									Gui.lang.getString("backup_success"),
										Gui.lang.getString("confirmation"),
											JOptionPane.CANCEL_OPTION);	
					}
					else {
						JOptionPane.showMessageDialog(
								Gui.modalParent,
									Gui.lang.getString("backup_failure"),
										Gui.lang.getString("confirmation"),
											JOptionPane.CANCEL_OPTION);	
					}
				
			}
			Gui.my_forms[index].dispose();
		}
	}
	
}