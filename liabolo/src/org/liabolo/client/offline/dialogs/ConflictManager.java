/*
 * Created on 05.08.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.liabolo.client.offline.dialogs;

import info.clearthought.layout.TableLayout;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.common.MergingData;
import org.liabolo.common.MetaData;
import org.liabolo.common.TextItem;
import org.liabolo.exception.ServiceNotAvailableException;

/**
 * @author Admin
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ConflictManager extends DefaultDialog implements ActionListener {

	private JSplitPane conflictPanel;
	private List conflictItems = new ArrayList();
	private int selectedMergingSet;


	public ConflictManager(Collection conflictItems) 
	{	
		super("Conflict Manager");
		this.conflictItems.addAll(conflictItems);
		this.selectedMergingSet = 0;	
		addContentPanel();
	}

	
	public void addContentPanel() 
	{		
		int border=5;
		double root_size[][] =
				{{border,TableLayout.PREFERRED,
							TableLayout.PREFERRED,
								TableLayout.PREFERRED,
									TableLayout.PREFERRED,
									border}, // Columns
				 {border,TableLayout.PREFERRED, 
						TableLayout.PREFERRED,
							TableLayout.PREFERRED,border}}; // Rows
		TableLayout tl = new TableLayout(root_size);
		tl.setHGap(10);
		tl.setVGap(10);
			
		JPanel root = new JPanel(tl);
		
		conflictPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
												getLocalView(), getGlobalView());
		
		conflictPanel.setContinuousLayout(true);
		root.add(conflictPanel,"1,1,4,1,FULL,FULL");
	
		//Upload-Button
		JButton upload_button = FormElement.getButton("upload_local", "images/upload_local.png", "upload_local_tt", null, true);
		upload_button.setActionCommand("upload_local");
		upload_button.addActionListener(this);
		root.add(upload_button, "1,2,1,2,FULL,CENTER");
		
		
		//Prev-Button
		JButton prev_button = FormElement.getInfiniteButton("<<", null, "previous_item", null, true);
		prev_button.setActionCommand("prev");
		prev_button.addActionListener(this);
		root.add(prev_button, "2,2,2,2,RIGHT,CENTER");
		
		if((selectedMergingSet)==0)
			prev_button.setEnabled(false);
		
					
		//Next-Button
		JButton next_button = FormElement.getInfiniteButton(">>",null, "next_item", null, true);
		next_button.setActionCommand("next"); 
		next_button.addActionListener(this);
		root.add(next_button, "3,2,3,2,LEFT,CENTER");	
		
		if((selectedMergingSet+1)==conflictItems.size())
			next_button.setEnabled(false);

		//Download-Button
		JButton download_button = FormElement.getButton("download_global", "images/download_global.png", "download_global_tt", null, true);
		download_button.setActionCommand("download_global");
		download_button.addActionListener(this);
		root.add(download_button, "4,2,4,2 ,FULL,CENTER");
	
		//Download-Button
		JButton cancel_button = FormElement.getButton("close", "images/ok.png", "close_wnd", null, true);
		cancel_button.setActionCommand("cancel");
		cancel_button.addActionListener(this);
		root.add(cancel_button, "1,3,4,3 ,CENTER,CENTER");
	
		this.getContentPane().add(root);
		this.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension labelSize = this.getPreferredSize();
  		
		this.setLocation(screenSize.width/2 - (labelSize.width/2),
							screenSize.height/2 - (labelSize.height/2));
							
		this.setVisible(true);	
		conflictPanel.setDividerLocation(0.5);


	}
	private JScrollPane getDefaultViewPane(String content)
	{
		JEditorPane output = new JEditorPane();
		output.setContentType("text/html");	
		output.setEditable(false);
		output.setAutoscrolls(true);
		output.setAlignmentX(JEditorPane.CENTER_ALIGNMENT);
		output.setMargin(new Insets(3, 3, 3, 3));
		output.setText(content);
		JScrollPane scrollPane = new JScrollPane(output);
		scrollPane.setPreferredSize(new Dimension(200,400));
		
		return scrollPane;
	}
	
	private String setDataFields(String outputText, MetaData set)
	{
		String type = set.getDc_type();
		// TODO	 System.out.println("/type:"+type+"/selectedSet:"+this.selectedMergingSet);
		if(type.equals("")) type = "book";
			
		//System.out.println("typeAFTER:"+type);
		ArrayList items = Gui.dispatcher.getMediaTypeItems(Gui.myLib, type);// Form elements for desired Media-type
		// TODO	 System.out.println("itemsize:"+items.size());
		if(items==null)
		{
			try
			  {
				items = TextItem.getGenericMediaTypeItems();
			  }
				catch(ServiceNotAvailableException error){
					Gui.statusBar.setReceiverMessage("No generic media type defined!("+error+")");
				}
		}
		outputText = outputText.concat("<b>Typ: "+type+"</b><br><br>");
		
		for(int j= 0; j<items.size();j++)
		{
			switch(((TextItem)items.get(j)).getDCid())
			{   
				case 0: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+set.getDc_creator()+"<br>"); break;
				case 1: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+set.getDc_title()+"<br>"); break;
				case 2: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+set.getDc_publisher()+"<br>"); break;
				case 3: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+MetaData.convertDate(set.getDc_date())+"<br>"); break;
				case 4: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+set.getDc_relation()+"<br>"); break;
				case 5: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+set.getDc_source()+"<br>"); break;
				case 6: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+set.getDc_coverage()+"<br>"); break;
				case 7: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+set.getDc_language()+"<br>"); break;
				case 8: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+set.getDc_description()+"<br>"); break;
				case 9: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+set.getDc_subject()+"<br>"); break;
				case 10: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+set.getDc_identifier()+"<br>"); break;
				case 11:  break;
				case 12: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+set.getDc_format()+"<br>"); break;
				case 13: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+set.getDc_contributors()+"<br>"); break;
				case 14: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+set.getDc_rights()+"<br>"); break;
			}
		}
	
		// TODO	 System.out.println("OUTPUT:"+outputText);
		return outputText;
	}
	private JScrollPane getLocalView()
	{		
		String outputText = "<html><h2>Lokaler Datensatz: </h2><p font size=\"1\">";
		outputText = setDataFields(outputText, ((MergingData)conflictItems.get(selectedMergingSet)).getLocal().getMetaData());
		outputText = outputText.concat("<br></p></html>");
		
		return getDefaultViewPane(outputText);
	}
	
	private JScrollPane getGlobalView()
	{		
		String outputText = "<html><h2>Globaler Datensatz: </h2><p font size=\"1\">";
		outputText = setDataFields(outputText, ((MergingData)conflictItems.get(selectedMergingSet)).getGlobal().getMetaData());
		outputText = outputText.concat("<br></p></html>");
	// TODO	System.out.println("OUTPUTFINAL:"+outputText);
		
		return getDefaultViewPane(outputText);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		String actionCmd = e.getActionCommand();
		
		if (actionCmd.equals("cancel")) 
		{
			this.closeForm();
		}
		
		if (actionCmd.equals("upload_local")) 
		{	
			Gui.dispatcher.resolveConflict_storeChanges(((MergingData)conflictItems.get(selectedMergingSet)).getLocal());
			this.conflictItems.remove(selectedMergingSet);
			this.getContentPane().removeAll();
			this.addContentPanel();
		}
		
		if (actionCmd.equals("download_global")) 
		{	
			Gui.dispatcher.resolveConflict_looseChanges(((MergingData)conflictItems.get(selectedMergingSet)).getGlobal());
			this.conflictItems.remove(selectedMergingSet);
			this.getContentPane().removeAll();
			this.addContentPanel();
		}
		
		if(actionCmd == "next")
		{
			if((selectedMergingSet+1)<conflictItems.size())
			{
				this.selectedMergingSet+=1;
				this.getContentPane().removeAll();
				this.addContentPanel();
			}
		}
		if(actionCmd == "prev")
		{
			if(selectedMergingSet>0)
			{
				this.selectedMergingSet-=1;
				this.getContentPane().removeAll();
				this.addContentPanel();
			}

		}
	}
}
