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
import java.beans.PropertyVetoException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.common.Configurator;
import org.liabolo.common.Logger;
import org.liabolo.repository.Library;

/**
 * @author Jurij Henne
 *
 * Implementation of the default form. It is the super class of most other forms and provides form frame
 * constructor. It also provides the form update method. 
 * 
 * Note: under construction! Future version should contain the common functionalities for all derived classes.
 */
public class DefaultForm extends JInternalFrame
{	
	/** specifies a unique 'position' of  "browse location" form in form holder-array */
	public int index;
	/** Frame title */
	public String title;
	/** Frame icon */
	public Icon frameIcon;
	/** Logger instance*/
	public Logger log = Logger.getLogger(this.getClass());
	
	/** Available libraries( see also common/Library.java) */
	public Object [] myLibraries;
	/** Available connections */
	public Object [] myConnections;
	
	
	public JComboBox connectionList;
	
	/** 
	 * Creates a new internal frame on the desktop
	 * @param index specifies a unique 'position' of  "browse location" form in form holder-array 
	 * @param title Frame title
	 * @param icon_path Path to frame icon
	 */
	public DefaultForm(int index, String title, String icon_path)
	{
		super(title,false,true,false,true);
		this.index = index;
		this.title = title;	
		this.frameIcon = FormElement.createImageIcon(icon_path);
	}
	
	
	public DefaultForm(String title, String icon_path)
	{
		this.title = title;	
		this.frameIcon = FormElement.createImageIcon(icon_path);
	}

	/**
	 * Init the frame, adding icon and positioning it on desktop
	 * @param location Upper left corner of the frame.
	 */
	public  void addFormFrame(Point location)
	{ 
		Gui.my_forms[index] = this;
		Gui.my_forms[index].setFrameIcon(frameIcon);
		Gui.desktop.add(Gui.my_forms[index]);
		try 
		{
			Gui.my_forms[index].setSelected(true);
		} 
		catch (java.beans.PropertyVetoException e) 
		{
			log.debug(e);
		}
		//Call visible Form
		Gui.my_forms[index].getContentPane().add(showFormContent());
		if(location==null)
		{
			int openedframes =  Gui.desktop.getAllFrames().length;
			if(openedframes<1) openedframes=1; 
			Gui.my_forms[index].setLocation(15*openedframes,15*openedframes);
		}
		else
			Gui.my_forms[index].setLocation(location);
			
		Gui.my_forms[index].pack();
		Gui.my_forms[index].setVisible(true); //necessary as of 1.3
	}  
	

	/**
	 * Updates frame, redraws all internal components.
	 * @param location Upper left corner of the frame. Use this to place  the updated frame at 
	 * the old location. If location is null, the frame 
	 * will be automaticaly replaced on the desktop.
	 */
	public  void updateFormFrame(Point location)
	{ 
		boolean maximum = Gui.my_forms[index].isMaximum();
		Gui.my_forms[index].getContentPane().removeAll();
		Gui.my_forms[index].getContentPane().add(showFormContent());
		Gui.my_forms[index].update(Gui.my_forms[index].getGraphics());
		try 
		{
			Gui.my_forms[index].setSelected(true);
		} 
		catch (java.beans.PropertyVetoException e) 
		{
			log.debug(e);
		}
		if(location==null)
		{
			int openedframes =  Gui.desktop.getAllFrames().length;
			if(openedframes<1) openedframes=1; 
			Gui.my_forms[index].setLocation(15*openedframes,15*openedframes);
		}
		else
			Gui.my_forms[index].setLocation(location);
			
		Gui.my_forms[index].pack();
		if(maximum)
		try 
		{
			Gui.my_forms[index].setMaximum(true);
		} catch (PropertyVetoException e1) 
		{
			e1.printStackTrace();
		}
		Gui.my_forms[index].setVisible(true);
	}  

	/** 
	 * Returns null. Has to  be implemented in derived classes!
	 * @return
	 */
	public JPanel showFormContent()
	{
		return null;
	}
	
	/**
	 * Return  root panel in a special grid(3x2) 
	 * @return
	 */
	public static JPanel getRootPanel()
	{
		int border = 5;
		double root_size[][] =
				{{border, 
					TableLayout.FILL, 
						10, 
							TableLayout.FILL, 
								border}, // Columns
				 {border, 
					TableLayout.FILL, 
						40, 
							border}}; // Rows

		return new JPanel(new TableLayout(root_size));
	}
	
	/**
	 * Return  root panel in a special grid(3x3)
	 * @return
	 */
	public JPanel getMDRootPanel()
	{
		int border = 5;
		double root_size[][] =
				{{border, 
					TableLayout.FILL, 
						10, 
							TableLayout.FILL, 
								border}, // Columns
				 {border, 
					TableLayout.PREFERRED, 
						TableLayout.PREFERRED, 
							40, 
								border}}; // Rows

		return new JPanel(new TableLayout(root_size));
	}
	
	/**
	 * Returns "no results" panel. Note: will be removed in future!
	 * @param message Message to be displayed
	 * @param title Title in the border of the panel
	 * @return
	 */
	public JPanel getNoResultPanel(String message, String title)
	{
		int border = 3;
		double size[][] =
				{{border, 
					TableLayout.FILL, 
						border}, // Columns
				 {border, 
					TableLayout.FILL, 
						border}}; // Rows
							
		JPanel panel = new JPanel(new TableLayout(size));
		panel.setBorder
				(BorderFactory.createTitledBorder(
						BorderFactory.createEtchedBorder(BevelBorder.LOWERED), title));
		JLabel noResultMessage = FormElement.getInfiniteLabel(message, Font.PLAIN, null);
		panel.add(noResultMessage, "1,1,1,1, CENTER, CENTER");
		
		return panel;
	}
	
	/**
	 *  Init available connections and creates a chooseable combobox with connection names 
	 */
	public void initConnections()
	{
		//get all connected libs and build a combobox
		this.myLibraries = Gui.dispatcher.getAllConnectedLibraries().toArray();
		Object [] connectionNames = new Object[myLibraries.length];

		for(int i=0;i<connectionNames.length;i++)
		{
			connectionNames[i] = ((Library)myLibraries[i]).getName();
		}
		this.connectionList = new JComboBox(connectionNames);
	}

	
	/* ------------Warnings and Dialogs------------------------------------------------*/
	
	/**
	 * Manages a "close frame " event
	 */
	public void closeForm()
	{
		if(Gui.showWarnings)
		{
		int switch_frame = getFormCloseDialog();
		if (switch_frame == JOptionPane.YES_OPTION)
			Gui.my_forms[index].dispose();
		}
		else
			Gui.my_forms[index].dispose();
	}
	

	public static int getFormCloseDialog()
	{
		return JOptionPane.showConfirmDialog
				(Gui.modalParent,
							Gui.lang.getString("form_close"), 
								Gui.lang.getString("close_wnd"),
									 JOptionPane.YES_NO_OPTION); 
	}
	
	public static int getEditOpenedDialog()
	{
		return JOptionPane.showConfirmDialog(
					Gui.modalParent,
						Gui.lang.getString("edit_opened"),
							Gui.lang.getString("warning"),
									JOptionPane.YES_NO_OPTION);
	}
	
	public static int getDeleteItemsDialog()
	{
		return 	JOptionPane.showConfirmDialog(
					Gui.modalParent,
						Gui.lang.getString("delete_items"),
							Gui.lang.getString("deleting"),
								JOptionPane.YES_NO_OPTION);
	}

	public static int getNewSearchDialog()
	{
		return JOptionPane.showConfirmDialog(
					Gui.modalParent,
						Gui.lang.getString("new_search_warning"),
							Gui.lang.getString("warning"),
								JOptionPane.YES_NO_OPTION);
	}
	public static void showNoListcontentSection()
	{
		JOptionPane.showMessageDialog(
			Gui.modalParent,
				Gui.lang.getString("no_entry_selection"),
					Gui.lang.getString("warning"),
						JOptionPane.CANCEL_OPTION);
	}
	
	public static void showNoSectionMessage()
	{
		JOptionPane.showMessageDialog(
			Gui.modalParent,
				Gui.lang.getString("no_selection"),
					Gui.lang.getString("warning"),
						JOptionPane.CANCEL_OPTION);
	}
	
	public static void showNoDeleteSelectionMessage()
	{
		JOptionPane.showMessageDialog(
			Gui.modalParent,
				Gui.lang.getString("no_delete_selection"),
					Gui.lang.getString("warning"),
						JOptionPane.CANCEL_OPTION);
	}

	public static void showNoEditSelectionMessage()
	{
		JOptionPane.showMessageDialog(
			Gui.modalParent,
				Gui.lang.getString("no_edit_selection"),
					Gui.lang.getString("warning"),
						JOptionPane.CANCEL_OPTION);	
	}
	
	public static void showInputErrorMessage()
	{
		JOptionPane.showMessageDialog(
			Gui.modalParent,
				Gui.lang.getString("input_not_correct"),
					Gui.lang.getString("error"),
						JOptionPane.CANCEL_OPTION);
	}
	
	public static void showNoResultMessage()
	{
		JOptionPane.showMessageDialog(
			Gui.modalParent,
				Gui.lang.getString("no_matches"),
					Gui.lang.getString("warning"),
						JOptionPane.CANCEL_OPTION);	
	}
	
	public static void showActionSuccess()
	{
		JOptionPane.showMessageDialog(
			Gui.modalParent,
				Gui.lang.getString("action_success"),
					Gui.lang.getString("confirmation"),
						JOptionPane.CANCEL_OPTION);	
	}			
	
	public static void showSuccessMessage()
	{
		JOptionPane.showMessageDialog(
			Gui.modalParent,
				Gui.lang.getString("entry_success"),
					Gui.lang.getString("confirmation"),
						JOptionPane.CANCEL_OPTION);	
	}				
	
	public static void showConfirmMessage()
	{
		JOptionPane.showMessageDialog(
			Gui.modalParent,
				Gui.lang.getString("entry_success"),
					Gui.lang.getString("confirmation"),
						JOptionPane.CANCEL_OPTION);	
	}
	
	public static int getAddListDialog()
	{
		return JOptionPane.showConfirmDialog(
					Gui.modalParent,
							Gui.lang.getString("no_lists_av")+" "+Gui.lang.getString("add_new?"),
							Gui.lang.getString("warning"),
								JOptionPane.YES_NO_OPTION);
	}

	public static boolean askToAddType(String message)
	{
	  int switch_frame = JOptionPane.showConfirmDialog(
			  Gui.modalParent,
				  Gui.lang.getString(message),
					  Gui.lang.getString("warning"),
						  JOptionPane.YES_NO_OPTION);
								
	  if (switch_frame == JOptionPane.YES_OPTION) 
	  {	
		  int treeFormIndex = Configurator.getIntProperty("mediaType",0,"gui-forms");
		  JInternalFrame form=Gui.getForm(treeFormIndex);
		  if(form!=null && !form.isClosed())
		  {
				form.pack();
				try 
				{
					form.setSelected(true);		
				}
				catch (java.beans.PropertyVetoException ev) {}	
					
		  }
		  else 
		  {
			  MediaTypesEditor medEditor = new MediaTypesEditor(treeFormIndex);
				
		  }	
		return true;					
	  }
	else 
		   return false;
	}
 
}
