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
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.common.WaitDialog;
import org.liabolo.common.Configurator;
import org.liabolo.common.TextItem;
import org.liabolo.exception.ServiceNotAvailableException;

/**
 * @author Maxim Bauer
 *
 * This class implements the editor for the meta data types. 
 * Here the pre-defined Mediatypes can be changed.
 */
public class MediaTypesEditor extends DefaultForm implements ActionListener, KeyListener {
	
	/** A list of Textitems, see also TextItem*/
	private ArrayList textItems;
	/** Check boxes array to edit that mediatypes*/
	private JCheckBox boxen [];
	/** Combobox to select that mediatypes*/
	private JComboBox mediaTypes;
	/** An array of TextItems, see also TextItem*/
	private TextItem newItems [];
	/** A list to store the new TextItems*/
	private ArrayList itemsList;
	/** An array, which those contains mediatypes specific TextItems*/
	private TextItem customItems [];
	/** An array to collect the available mediatypes*/
	private Object [] typesList;
	
	/**
	 * Creates a new "MediaTypesEditor"-form 
	 * @param mediaTypeIndex specifies a unique 'position' of  the form in form holder-array 
	 * 
	 */
	public MediaTypesEditor(int mediaTypeIndex){
		
		super(mediaTypeIndex, Gui.lang.getString("media_editor"),"images/editor.png");
	    //Collect available media types 
		typesList = Gui.dispatcher.getAllMediaTypes(Gui.myLib).toArray(); 
		String [] tempList = new String[typesList.length];
		for(int i=0; i<typesList.length; i++)
		{
			tempList[i] = Gui.lang.getString((String)typesList[i]);
		}
		this.mediaTypes = FormElement.getComboBox(tempList,0,Font.PLAIN,"av_mediatypes");
		
		mediaTypes.setSelectedIndex(0);
		
        mediaTypes.addActionListener(this);
        mediaTypes.setActionCommand("update");
        try
		{
        	this.textItems = TextItem.getGenericMediaTypeItems();
		}
        	catch(ServiceNotAvailableException error)
		{
        		//System.out.println(error);
		}
		this.boxen = new JCheckBox[textItems.size()];
		//this.newItems = new TextItem[textItems.length];
		this.addFormFrame(null);
		//WaitDialog.closeDialog();
	}
	
	/** Implemetation of inherited method. Returns the layouted  content panel of the form */
	 public JPanel showFormContent() 
	    {
	       JPanel root = getMDRootPanel(); //inherited
	       
	       root.add(FormElement.getExtLabel("av_mediatypes", Font.PLAIN, null), "1,1,1,1 ,RIGHT,CENTER"); 
           root.add(mediaTypes, "3,1,3,1 ,LEFT,CENTER");

	       
	       int  border = 5;
	        // Creating the Grid for TableLayout
	       double row_h = TableLayout.PREFERRED; 
	       double size[][] =
	                {{border, 
						0.60,
							0.40,
								border}, // Columns
	                 {border,  
	                 		border}}; 		// Rows
	        TableLayout tl = new TableLayout(size);
	       
	        	for (int i = 0; i < textItems.size()+1; i++)
	        		tl.insertRow(1, row_h);         			
	        //TableLayout tl = new TableLayout(size);
	        //Space between Columns and Rows
	        tl.setHGap(5);
	        tl.setVGap(5);

	        JPanel panel = new JPanel(tl);
	        panel.setBorder
	                (BorderFactory.createTitledBorder(
	                        BorderFactory.createEtchedBorder(BevelBorder.LOWERED), Gui.lang.getString("add_media")));
	       
	      
	      
	        String actSel = (String)typesList[mediaTypes.getSelectedIndex()];
	        ArrayList customItems = Gui.dispatcher.getMediaTypeItems(Gui.myLib, actSel);
	        //ArrayList customItems = Gui.dispatcher.getMediaTypeItems(Gui.myLib, mediaTypes.getSelectedItem().toString());
	         
	        
	        	for(int i=0; i<textItems.size(); i++)
	        	{
		        	JLabel label = FormElement.getInfiniteExtLabel(((TextItem)textItems.get(i)).getLocalizedName(), Font.PLAIN, null);
		        	panel.add(label,"1,"+(i+1)+",1,"+(i+1)+",LEFT,CENTER");
		        	boxen[i] = new JCheckBox();
		        	boxen[i].setToolTipText(((TextItem)textItems.get(i)).getLocalizedDescription());
		        	for(int j=0; j<customItems.size();j++)
		        	{
		        		
		        		if(((TextItem)customItems.get(j)).getLocalizedName().equals(((TextItem)textItems.get(i)).getLocalizedName()))
		        		{
		        			boxen[i].setSelected(true);	
		        			
		        		}
		        	}
		        	panel.add(boxen[i],",2,"+(i+1)+",2,"+(i+1)+",LEFT,CENTER");
		        }
	        	
	        
	        root.add(panel, "1,2,3,2");

	        //OK-Button
	        JButton ok_button = FormElement.getButton("ok", "images/ok.png", "save", null, true);
	        ok_button.setActionCommand("ok");
	        ok_button.addActionListener(this);
			ok_button.addKeyListener(this);
	        root.add(ok_button, "1,3,1,3,FULL,CENTER");

	        //Cancel-Button
	        JButton cancel_button = FormElement.getButton("cancel", "images/cancel.png", "close_wnd", null, true);
	        cancel_button.setActionCommand("cancel");
	        cancel_button.addActionListener(this);
			cancel_button.addKeyListener(this);
	        root.add(cancel_button, "3,3,3,3 ,LEFT,CENTER");

	        return root;
	    }
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		String actionCmd = arg0.getActionCommand();
        
        if (actionCmd.equals("cancel")) 
        {
            Gui.my_forms[index].dispose();
        }
        
        if(actionCmd.equals("update"))
        {
        	Point location = Gui.my_forms[index].getLocation();
        	this.updateFormFrame(location);
        }
        
        if(actionCmd.equals("ok"))
        {
        	
        		itemsList = new ArrayList();	
        		String actSel = (String)typesList[mediaTypes.getSelectedIndex()];
        		boolean removed = Gui.dispatcher.removeMediaType(Gui.myLib,actSel);
        		
	        	for(int i=0; i<textItems.size(); i++)
	        	{
	        		if(boxen[i].isSelected())
	        		{	
	        			itemsList.add(new TextItem(((TextItem)textItems.get(i)).getDCid(), ((TextItem)textItems.get(i)).getKeyName(), ((TextItem)textItems.get(i)).getKeyDescription(), ((TextItem)textItems.get(i)).getType()));
	        			//System.out.println("Name"+((TextItem)textItems.get(i)).getDCid());
	    			}
	        	}
        	
	        	newItems = new TextItem[itemsList.size()];
	        	for(int j=0;j<itemsList.size();j++)
	        	{
	        		newItems[j]  = (TextItem)itemsList.get(j);
	        	}
	        	
	        	boolean added = Gui.dispatcher.addMediaType(Gui.myLib, actSel, newItems);
	        	//boolean aed = Gui.dispatcher.addMediaType(Gui.myLib, "magazine", newItems);
	        	if(Gui.showConfirmations)
	        	{
	        		DefaultForm.showSuccessMessage();
	        	}
	        	
				int addMetadataIndex = Configurator.getIntProperty("newMetadata",0,"gui-forms");
				DefaultForm form=Gui.getForm(addMetadataIndex);
				if(form!=null && !form.isClosed())
				{
					form.pack();
					try 
					{
						form.dispose();
						Point formLocation = form.getLocation();
						MetadataForm addMetadata = new MetadataForm(addMetadataIndex, formLocation);
						this.setSelected(true);
					} 
					catch (java.beans.PropertyVetoException ev) 
					{}	
				}
	
        	}
     	}
        
        
     
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent event) 
	{
			 if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
			 	actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));

			 if (event.getKeyCode() == KeyEvent.VK_ENTER)
				actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"ok"));
	}

	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
