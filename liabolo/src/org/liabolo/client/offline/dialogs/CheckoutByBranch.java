/*
 * Created on 22.07.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.liabolo.client.offline.dialogs;

import info.clearthought.layout.TableLayout;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.forms.BrowseMetadataResults;
import org.liabolo.client.offline.forms.DefaultForm;
import org.liabolo.common.Configurator;
import org.liabolo.repository.Library;

/**
 *
 * @author Jurij Henne
 *
 * An implementation of the "choose connected library for check out" dialog. See also DefaultDialog.java
 */
public class CheckoutByBranch  extends DefaultDialog {
	
	/** Available libraries */
	private Object [] myLibraries;
	/** Combo box with available connections */
	private JComboBox connectionList;
	/** Branches which should be checked out */
	private Collection checkoutEntries;
	
	/**
	 * Creates a new "choose connected library for check out" dialog
	 * @param checkoutEntries  selected branches which should be checked out
	 */
	public CheckoutByBranch(Collection checkoutEntries) 
	{
        super(Gui.lang.getString("choose_connection"),Gui.lang.getString("choose_connection_tip"),null);
		this.checkoutEntries = checkoutEntries;
        initConnectedLibs();
	}

	/** See DefaultDialog.java */
	public JPanel getDialogPanel()
	{			
		int border = 5;
		double size[][] =
				{{border,
						TableLayout.FILL, 
							border}, // Columns
				 {border, 
					TableLayout.FILL,
						 border}}; 		// Rows
		TableLayout tl = new TableLayout(size);

		//Space between Columns and Rows
		tl.setHGap(5);
		tl.setVGap(5);
			
		JPanel panel = new JPanel(tl);

		panel.add(connectionList, "1,1,1,1,FULL,FULL");
	
		return panel;
	}
	
	/** See DefaultForm.java */
	public void initConnectedLibs()
	{		

		this.myLibraries = Gui.dispatcher.getAllGlobalConnectedLibraries().toArray();

		if(myLibraries.length>0)
		{
			Object [] connectionNames = new Object[myLibraries.length];

			for(int i=0;i<connectionNames.length;i++)
			{
				connectionNames[i] = ((Library)myLibraries[i]).getName();
			}
			this.connectionList = new JComboBox(connectionNames);
		}
		else
		{
			this.connectionList = new JComboBox (new String []{ Gui.lang.getString("not_connected")});
		}
		
		this.addContentPanel();
	}
	
	/**
	 * Handles ok-action
	 */
	public void OKAction()
	{
		Gui.dispatcher.checkoutByBranch((Library)myLibraries[connectionList.getSelectedIndex()],checkoutEntries);
		closeForm();
		int index = Configurator.getIntProperty("browseClipboard",0,"gui-forms");
		DefaultForm form=Gui.getForm(index);
		if(form!=null && !form.isClosed())
		{
			Point location = form.getLocation();
			int browse_typ = ((BrowseMetadataResults)form).getBrowseType();
			form.dispose();
			BrowseMetadataResults clipboardResults = new BrowseMetadataResults(index, Gui.lang.getString("clipboard"), location);
		}
	}

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
	}
}

