/*
 * Created on 15.01.2004 by Easy (Stefan Willer)
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
package org.liabolo.client.offline;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashSet;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.common.Help;
import org.liabolo.client.offline.common.WaitDialog;
import org.liabolo.client.offline.dialogs.ConflictManager;
import org.liabolo.client.offline.dialogs.Info;
import org.liabolo.client.offline.dialogs.PreferencesDialog;
import org.liabolo.client.offline.forms.BackupForm;
import org.liabolo.client.offline.forms.BranchForm;
import org.liabolo.client.offline.forms.BrowseBranch;
import org.liabolo.client.offline.forms.BrowseConnection;
import org.liabolo.client.offline.forms.BrowseList;
import org.liabolo.client.offline.forms.BrowseLocation;
import org.liabolo.client.offline.forms.ConnectionForm;
import org.liabolo.client.offline.forms.DefaultForm;
import org.liabolo.client.offline.forms.ImportForm;
import org.liabolo.client.offline.forms.ListForm;
import org.liabolo.client.offline.forms.LocationForm;
import org.liabolo.client.offline.forms.BrowseMetadataResults;
import org.liabolo.client.offline.forms.MediaTypesEditor;
import org.liabolo.client.offline.forms.MetadataForm;
import org.liabolo.client.offline.forms.MetadataSearchForm;
import org.liabolo.client.offline.forms.TreeForm;
import org.liabolo.common.*;


public class Menu extends JMenuBar implements ActionListener, WindowListener {

	/** Available global connections for connect with connect-button */
	private Object [] myConnections;
	/** Instance of the connection button in menu "Connection(s)" */
	public static  JCheckBoxMenuItem connect;


	/** Creates the menu  liabolo application */
	public Menu()
	{
		this.add(getFileMenu());
		//this.add(getWorkspaceMenu());
	   	this.add(getEnvironmentMenu());
	   	this.add(getConnectionMenu());
		this.add(getExtrasMenu());
	   	this.add(getHelpMenu());

	}

	/**
	 * Creates file menu
	 * @return filemenu  with specified entries
	 */
	public JMenu getFileMenu()
	{
		JMenu filemenu = new JMenu(Gui.lang.getString("file"));
		filemenu.setMnemonic(KeyEvent.VK_D);


		JMenuItem newBook = new JMenuItem(Gui.lang.getString("new"), FormElement.createImageIcon("images/book.png"));
		newBook.setMnemonic(KeyEvent.VK_N);
		newBook.setActionCommand("newBook");
		newBook.addActionListener(this);

		filemenu.add(newBook);

		JMenuItem excel_import = new JMenuItem(Gui.lang.getString("import"), FormElement.createImageIcon("images/import.png"));
		excel_import.setMnemonic(KeyEvent.VK_I);
		excel_import.setActionCommand("import");
		excel_import.addActionListener(this);
		filemenu.add(excel_import);

		JMenuItem export = new JMenuItem(Gui.lang.getString("export"), FormElement.createImageIcon("images/export.png"));
		export.setMnemonic(KeyEvent.VK_I);
		export.setActionCommand("browseList");
		export.addActionListener(this);
		filemenu.add(export);

		filemenu.addSeparator();

		JMenuItem search = new JMenuItem(Gui.lang.getString("search"), FormElement.createImageIcon("images/searchedit.png"));
		search.setMnemonic(KeyEvent.VK_S);
		search.setActionCommand("mdSearch");
		search.addActionListener(this);
		filemenu.add(search);


		filemenu.addSeparator();

		JMenuItem backup = new JMenuItem(Gui.lang.getString("backup"),FormElement.createImageIcon("images/backup.png"));
		backup.setMnemonic(KeyEvent.VK_B);
		backup.setActionCommand("restore");
		backup.addActionListener(this);
		filemenu.add(backup);

		filemenu.addSeparator();

		JMenuItem exit = new JMenuItem(Gui.lang.getString("exit"),FormElement.createImageIcon("images/exit.png"));
		exit.setMnemonic(KeyEvent.VK_E);
		exit.setActionCommand("exit");
		exit.addActionListener(this);
		filemenu.add(exit);

		return filemenu;
	}

	/**
	 * Creates workspace menu
	 * @return ws_menu with specified entries
	 */
	/*
	public JMenu getWorkspaceMenu()
	{
		JMenu ws_menu = new JMenu(Gui.lang.getString("workspace"));
		ws_menu.setMnemonic(KeyEvent.VK_W);

		JMenuItem statistik = new JMenuItem(Gui.lang.getString("MyLib"),FormElement.createImageIcon("images/home.png"));
		statistik.setMnemonic(KeyEvent.VK_S);
		statistik.setActionCommand("MyLib");
		statistik.addActionListener(this);
		ws_menu.add(statistik);

		JMenuItem  ws_content = new JMenuItem(Gui.lang.getString("content"), FormElement.createImageIcon("images/space.gif"));
		ws_content.setMnemonic(KeyEvent.VK_F);
		ws_content.setActionCommand("ws_content");
		ws_content.addActionListener(this);
		ws_menu.add(ws_content);

		JMenuItem  clear_ws = new JMenuItem(Gui.lang.getString("clear_ws"), FormElement.createImageIcon("images/space.gif"));
		clear_ws.setMnemonic(KeyEvent.VK_F);
		clear_ws.setActionCommand("clear_ws");
		clear_ws.addActionListener(this);
		ws_menu.add(ws_content);

		return ws_menu;
	}
	*/

	/**
	 * Creates environment menu
	 * @return envMenu with specified entries
	 */
	public JMenu getEnvironmentMenu()
	{
		JMenu envMenu = new JMenu(Gui.lang.getString("environment"));
		envMenu.setMnemonic(KeyEvent.VK_U);

		JMenuItem statistik = new JMenuItem(Gui.lang.getString("MyLib"),FormElement.createImageIcon("images/tree.png"));
		statistik.setMnemonic(KeyEvent.VK_S);
		statistik.setActionCommand("homeLib");
		statistik.addActionListener(this);
		envMenu.add(statistik);

		envMenu.addSeparator();

		JMenuItem  browseBranch = new JMenuItem(Gui.lang.getString("branches"), FormElement.createImageIcon("images/branch2.png"));
		browseBranch.setMnemonic(KeyEvent.VK_F);
		browseBranch.setActionCommand("browseBranch");
		browseBranch.addActionListener(this);
		envMenu.add(browseBranch);



		JMenuItem  browseLocation= new JMenuItem(Gui.lang.getString("locations"), FormElement.createImageIcon("images/location.png"));
		browseLocation.setMnemonic(KeyEvent.VK_S);
		browseLocation.setActionCommand("browseLocation");
		browseLocation.addActionListener(this);
		envMenu.add(browseLocation);


		JMenuItem  browseList= new JMenuItem(Gui.lang.getString("ind_list"), FormElement.createImageIcon("images/mylists.png"));
		browseList.setMnemonic(KeyEvent.VK_I);
		browseList.setActionCommand("browseList");
		browseList.addActionListener(this);
		envMenu.add(browseList);

		envMenu.addSeparator();


		JMenuItem  clipboard = new JMenuItem(Gui.lang.getString("clipboard"), FormElement.createImageIcon("images/clipboard.png"));
		clipboard.setMnemonic(KeyEvent.VK_F);
		clipboard.setActionCommand("cb_content");
		clipboard.addActionListener(this);
		envMenu.add(clipboard);
/*
		JMenuItem update_cb = new JMenuItem(Gui.lang.getString("update_cb"), FormElement.createImageIcon("images/update.png"));
		update_cb.setMnemonic(KeyEvent.VK_F);
		update_cb.setActionCommand("update_cb");
		update_cb.addActionListener(this);
		envMenu.add(update_cb);

		JMenuItem commit_cb = new JMenuItem(Gui.lang.getString("commit_cb"), FormElement.createImageIcon("images/commit.gif"));
		commit_cb.setMnemonic(KeyEvent.VK_F);
		commit_cb.setActionCommand("commit_cb");
		commit_cb.addActionListener(this);
		envMenu.add(commit_cb);

		JMenuItem  clear_cb = new JMenuItem(Gui.lang.getString("clear_cb"), FormElement.createImageIcon("images/clear.png"));
		clear_cb.setMnemonic(KeyEvent.VK_F);
		clear_cb.setActionCommand("clear_cb");
		clear_cb.addActionListener(this);
		envMenu.add(clear_cb);
*/

		return envMenu;
	}

	/**
	 * Creates connection menu
	 * @return connectionMenu  with specified entries
	 */
	public JMenu getConnectionMenu()
	{
		JMenu connectionMenu = new JMenu(Gui.lang.getString("connection"));
		connectionMenu.setMnemonic(KeyEvent.VK_V);

		connect = new JCheckBoxMenuItem(Gui.lang.getString("connect"),FormElement.createImageIcon("images/connect2da.png"),false);
		connect.setSelectedIcon(FormElement.createImageIcon("images/connect2.png"));
		connect.setMnemonic(KeyEvent.VK_R);
		connect.setActionCommand("connect_menu");
		connect.addActionListener(this);
		connectionMenu.add(connect);

		JMenuItem  newConn = new JMenuItem(Gui.lang.getString("new"), FormElement.createImageIcon("images/connection.png"));
		newConn.setMnemonic(KeyEvent.VK_N);
		newConn.setActionCommand("newConn");
		newConn.addActionListener(this);
		connectionMenu.add(newConn);

		JMenuItem  editConn = new JMenuItem(Gui.lang.getString("edit"), FormElement.createImageIcon("images/searchedit.png"));
		editConn.setMnemonic(KeyEvent.VK_B);
		editConn.setActionCommand("browseConn");
		editConn.addActionListener(this);
		connectionMenu.add(editConn);

		// Get all available connections
		myConnections = Gui.dispatcher.sortConnections(Gui.dispatcher.getAllConnections(),Gui.myLib).toArray();

		return connectionMenu;
	}

	/**
	 * Creates extras menu
	 * @return extrasMenu  with specified entries
	 */
	public JMenu getExtrasMenu()
	{
		JMenu extrasMenu = new JMenu(Gui.lang.getString("extras"));
		extrasMenu.setMnemonic(KeyEvent.VK_E);


		JMenuItem  formEditor= new JMenuItem(Gui.lang.getString("form_editor"), FormElement.createImageIcon("images/editor.png"));
		formEditor.setMnemonic(KeyEvent.VK_F);
		formEditor.setActionCommand("form_editor");
		formEditor.addActionListener(this);
		formEditor.setEnabled(true);

		extrasMenu.add(formEditor);

		JMenuItem preferences = new JMenuItem(Gui.lang.getString("prefs"),FormElement.createImageIcon("images/preferences.png"));
		preferences.setMnemonic(KeyEvent.VK_E);
		preferences.setActionCommand("preferences");
		preferences.addActionListener(this);
		preferences.setEnabled(true);
		extrasMenu.add(preferences);

		return extrasMenu;
	}


	/**
	 * Creates help menu
	 * @return helpMenu  with specified entries
	 */
	public JMenu getHelpMenu()
	{
		JMenu helpMenu = new JMenu(Gui.lang.getString("help"));
		helpMenu.setMnemonic(KeyEvent.VK_H);


		JMenuItem  help= new JMenuItem(Gui.lang.getString("help_content"), FormElement.createImageIcon("images/help.png"));
		help.setMnemonic(KeyEvent.VK_H);
		help.setActionCommand("help");
		help.addActionListener(this);
		//help.setEnabled(false);

		helpMenu.add(help);

		JMenuItem  info = new JMenuItem("  Info", FormElement.createImageIcon("images/info.png"));
		info.setMnemonic(KeyEvent.VK_I);
		info.setActionCommand("info");
		info.addActionListener(this);
		helpMenu.add(info);

		return helpMenu;
	}

 /**
  *  Actionlistener for menu entries. Invoked when an action occurs.
  */
	public void actionPerformed(ActionEvent e)
	{
		String actionCmd = e.getActionCommand();

		if(actionCmd.equals("restore"))
		{
			int index = Configurator.getIntProperty("backup_restore",0,"gui-forms");
			DefaultForm form=Gui.getForm(index);
			if(form!=null && !form.isClosed())
			{
				setFormSelected(form);
			}
			else
			{
				BackupForm backup = new BackupForm(index);
			}

		}



		if(actionCmd.equals("newBook"))
		{
			int index = Configurator.getIntProperty("newMetadata",0,"gui-forms");
			DefaultForm form=Gui.getForm(index);
			if(form!=null && !form.isClosed())
			{
				setFormSelected(form);
			}
			else
			{
				MetadataForm newMetadata = new MetadataForm(index,null);
			}
		}

		if(actionCmd.equals("import"))
		{
			int index = Configurator.getIntProperty("importMetadata",0,"gui-forms");
			DefaultForm form=Gui.getForm(index);
			if(form!=null && !form.isClosed())
			{
				setFormSelected(form);
			}
			else
			{
				Object [] typesList = Gui.dispatcher.getAllMediaTypes(Gui.myLib).toArray();
				if(typesList.length>0)
				{
					ImportForm importForm = new ImportForm(index);
				}
				else
				{
					if(DefaultForm.askToAddType("no_types_av"))
					{
						ImportForm importForm = new ImportForm(index);
					}
				}
			}
		}


		if(actionCmd.equals("preferences")){
			PreferencesDialog prefsDialog = new PreferencesDialog();
		}

		if(actionCmd.equals("mdSearch") || actionCmd.equals("mdBrowse"))
		{
			int index = Configurator.getIntProperty("searchMetadata",0,"gui-forms");
			DefaultForm form=Gui.getForm(index);
			if(form!=null && !form.isClosed())
			{
				setFormSelected(form);
			}
			else
			{
				MetadataSearchForm metadataSearchForm = new MetadataSearchForm(index, null);

			}
		}


		if(actionCmd.equals("newBranch"))
		{
			int index = Configurator.getIntProperty("newBranch",0,"gui-forms");
			DefaultForm form=Gui.getForm(index);
			if(form!=null && !form.isClosed())
			{
				setFormSelected(form);
			}
			else
			{
				BranchForm branchForm = new BranchForm(index,null);
			}
		 }

		if(actionCmd.equals("browseBranch"))
		{
			int index = Configurator.getIntProperty("browseBranch",0,"gui-forms");
			DefaultForm form=Gui.getForm(index);
			if(form!=null && !form.isClosed())
			{
				setFormSelected(form);
			}
			 else
			 {

				BrowseBranch browseBranch = new BrowseBranch(index,null);
			 }
		 }

		if(actionCmd.equals("newLocation"))
		{
		int index = Configurator.getIntProperty("newLocation",0,"gui-forms");
		  DefaultForm form=Gui.getForm(index);
		  if(form!=null && !form.isClosed())
		  {
			  setFormSelected(form);
		  }
		  else
		  {
			 LocationForm locationForm = new LocationForm(index,null);
		  }
		}

		if(actionCmd.equals("browseLocation"))
		{
			int index = Configurator.getIntProperty("browseLocation",0,"gui-forms");
		  	DefaultForm form=Gui.getForm(index);
		  	if(form!=null && !form.isClosed())
			{
				  setFormSelected(form);
			}
		  	else
		 	{
				BrowseLocation browseLocation = new BrowseLocation(index, null);
		  	}
		}

		if(actionCmd.equals("newList"))
		{
			int index = Configurator.getIntProperty("newList",0,"gui-forms");
		  	DefaultForm form=Gui.getForm(index);
			if(form!=null && !form.isClosed())
			{
				setFormSelected(form);
			}
			else
			{
				ListForm listForm = new ListForm(index,null);
			}
		}

		if(actionCmd.equals("browseList"))
		{
			int index = Configurator.getIntProperty("browseList",0,"gui-forms");
			DefaultForm form=Gui.getForm(index);
			if(form!=null && !form.isClosed())
			{
				setFormSelected(form);
			}
		    else
		    {
			  BrowseList browseList = new BrowseList(index, null);
		    }
		}

		if(actionCmd.equals("connect")|| actionCmd.equals("connect_menu"))
		{

			if(actionCmd.equals("connect")) // Button action from toolbar
			{
				ToolBar.connectButton.setSelected(ToolBar.connectButton.isSelected());
				connect.setSelected(!connect.isSelected());
			}
			if(actionCmd.equals("connect_menu"))
			{
				ToolBar.connectButton.setSelected(!ToolBar.connectButton.isSelected());
			}

			if(connect.isSelected() || ToolBar.connectButton.isSelected())
			{
				ArrayList connectedLibs = new ArrayList();
				for(int i = 0; i< this.myConnections.length;i++)
				{
					Connection conn = (Connection)myConnections[i];
					connectedLibs.add(Gui.dispatcher.connect(conn));
				}

				ToolBar.connectButton.setToolTipText(Gui.lang.getString("connected"));
			}

			else
			{
				ToolBar.connectButton.setToolTipText(Gui.lang.getString("not_connected"));
				Gui.dispatcher.disconnectFromAllConnectedLibraries();
			}

			updateOpenedForms();
		}


		if(actionCmd.equals("newConn"))
		{
			int index = Configurator.getIntProperty("newConnection",0,"gui-forms");
		  	DefaultForm form=Gui.getForm(index);
			if(form!=null && !form.isClosed())
			{
				setFormSelected(form);
			}
			else
			{
				ConnectionForm connectionForm = new ConnectionForm(index,null);
			}
		}
		if(actionCmd.equals("browseConn"))
		{
			int index = Configurator.getIntProperty("browseConnection",0,"gui-forms");
			DefaultForm form=Gui.getForm(index);
			if(form!=null && !form.isClosed())
			{
				setFormSelected(form);
			}
		  	else
		  	{
				BrowseConnection browseConn = new BrowseConnection(index, null);
		  	}
		}
		if(actionCmd.equals("homeLib"))
		{
			int index = Configurator.getIntProperty("treeForm",0,"gui-forms");
			DefaultForm form=Gui.getForm(index);
			if(form!=null && !form.isClosed())
			{
				setFormSelected(form);
			}
			else
			{
				WaitDialog.showDialog();
				TreeForm treeform = new TreeForm(index,0);
				WaitDialog.closeDialog();
			}
		}
		if(actionCmd.equals("form_editor"))
		{
			int index = Configurator.getIntProperty("mediaType",0,"gui-forms");
			DefaultForm form=Gui.getForm(index);
			if(form!=null && !form.isClosed())
			{
				setFormSelected(form);
			}
			else
			{
				MediaTypesEditor medEditor = new MediaTypesEditor(index);
			}
		}

		if(actionCmd.equals("help"))
		{
			Help help = new Help();
		}


		if(actionCmd.equals("info"))
		{
			Info info = new Info();
		}


		if(actionCmd.equals("exit"))
		{
			exitApp();
		}


		if(actionCmd.equals("cb_content"))
		{
			int index = Configurator.getIntProperty("browseClipboard",0,"gui-forms");
			DefaultForm form=Gui.getForm(index);
			if(form!=null && !form.isClosed())
			{
				setFormSelected(form);
			}
			else
			{
				BrowseMetadataResults clipboardResults = new BrowseMetadataResults(index, Gui.lang.getString("clipboard"), null);
			}
		}

		if(actionCmd.equals("clear_cb"))
		{
			boolean clipboard = Gui.dispatcher.clearWorkspace();
			//Update opened edited clipboard
			int index = Configurator.getIntProperty("browseClipboard",0,"gui-forms");
			DefaultForm form=Gui.getForm(index);
			Point location = form.getLocation();
			form.updateFormFrame(location);
		}

		if(actionCmd.equals("commit_cb"))
		{
			Collection conflictItems = Gui.dispatcher.commitChangesForEditedLibItems(Gui.dispatcher.getLibItemsEditedFromWorkspace());
			ConflictManager conflictManager = new ConflictManager(conflictItems);
			//Update opened edited clipboard
			int index = Configurator.getIntProperty("browseClipboard",0,"gui-forms");
			DefaultForm form=Gui.getForm(index);
			Point location = form.getLocation();
			form.updateFormFrame(location);
		}

		if(actionCmd.equals("update_cb"))
		{
			Gui.dispatcher.updateWorkspace(true);
		}


	}

	private void updateOpenedForms()
	{
		int index = Configurator.getIntProperty("newMetadata",0,"gui-forms");
		DefaultForm form=Gui.getForm(index);
		if(form!=null && !form.isClosed())
		{
			((MetadataForm)form).initConnections();
			Point formLocation = form.getLocation();
			form.updateFormFrame(formLocation);
		}

		index = Configurator.getIntProperty("importMetadata",0,"gui-forms");
		form=Gui.getForm(index);
		if(form!=null && !form.isClosed())
		{
			((ImportForm)form).initConnections();
			Point formLocation = form.getLocation();
			((ImportForm)form).printMappingPanel(Gui.lang.getString("add_excel"));

		}

	}

	private void setFormSelected(DefaultForm form)
	{
		form.pack();
		try
		{
			form.setSelected(true);
		}
		catch (java.beans.PropertyVetoException ev)
		{}
	}

	private void closeWindow() {
	}

	public void exitApp()
	{
		int exit_frame =
			JOptionPane.showConfirmDialog(
				Gui.modalParent,
				Gui.lang.getString("exit_liabolo2"),
				Gui.lang.getString("exit_liabolo"),
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				FormElement.createImageIcon("images/exit_b.png"));
		if (exit_frame == JOptionPane.YES_OPTION)
		{
			System.exit(0);
		}
	}



	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e)
	{
		if (e.getComponent() instanceof JDialog) {
			((JDialog) (e.getComponent())).dispose();
		}
		else
		{
		exitApp();
		}
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

}
