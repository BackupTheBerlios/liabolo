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

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.common.LinkLoader;
import org.liabolo.common.LibItem;
import org.liabolo.common.MetaData;
import org.liabolo.common.TextItem;
import org.liabolo.exception.ServiceNotAvailableException;

/**
 * @author Jurij Henne
 *
 * An implementation of a "print preview" form. Selected items can be listed, ready for print
 */
public class PrintPreview extends DefaultForm implements ActionListener, HyperlinkListener, Printable
{
	/** Preview pane */
	private JEditorPane output;
	/** Metadata items for preview and print */
	private MetaData [] printedSet;


/**
 * Creates a "new print" form
 * @param formIndex specifies a unique 'position' of  the form in form holder-array
 * @param dataToPrint Metadata items for preview and print
 * @param location position of the form on application desktop
 */
	public PrintPreview(int formIndex, ArrayList dataToPrint, Point location)
	{
		super(formIndex, Gui.lang.getString("print_md_sets"),"images/print_preview.png");
		int setSize = dataToPrint.size();
		printedSet = new MetaData[setSize];
		for(int i = 0;i<setSize;i++)
		{
			printedSet[i] = ((LibItem)dataToPrint.get(i)).getMetaData();
		}
		output = new JEditorPane();
		output.setContentType("text/html");	
		output.setEditable(false);
		output.setAutoscrolls(true);
		output.setAlignmentX(JEditorPane.CENTER_ALIGNMENT);
		output.setMargin(new Insets(3, 3, 3, 3));
		
		this.addFormFrame(null);
	}

	/** Implemetation of inherited method. Returns the layouted  content panel of the form */
	public JPanel showFormContent() 
	{
	   JPanel root = getRootPanel(); //inherited
	
		int setSize = printedSet.length;
		String outputText = "<html><h2>"+Gui.lang.getString("selected_sets")+": "+setSize+"</h2><p font size=\"1\">";
		for(int i= 0; i<setSize;i++)
		{	
			String type = printedSet[i].getDc_type();
			if(type.equals("")) type = "book"; // TODO: book muss bei Auslieferung definiert werden ind der DB		
			ArrayList items = Gui.dispatcher.getMediaTypeItems(Gui.myLib, type);// Form elements for desired Media-type
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
					case 0: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+printedSet[i].getDc_creator()+"<br>"); break;
					case 1: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+printedSet[i].getDc_title()+"<br>"); break;
					case 2: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+printedSet[i].getDc_publisher()+"<br>"); break;
					case 3: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+MetaData.convertDate(printedSet[i].getDc_date())+"<br>"); break;
					case 4: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+printedSet[i].getDc_relation()+"<br>"); break;
					case 5: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+printedSet[i].getDc_source()+"<br>"); break;
					case 6: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+printedSet[i].getDc_coverage()+"<br>"); break;
					case 7: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+printedSet[i].getDc_language()+"<br>"); break;
					case 8: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+printedSet[i].getDc_description()+"<br>"); break;
					case 9: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+printedSet[i].getDc_subject()+"<br>"); break;
					case 10: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+printedSet[i].getDc_identifier()+"<br>"); break;
					case 11:  break;
					case 12: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+printedSet[i].getDc_format()+"<br>"); break;
					case 13: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+printedSet[i].getDc_contributors()+"<br>"); break;
					case 14: outputText = outputText.concat("<font color=\"#112255\"><b>"+((TextItem)items.get(j)).getLocalizedName()+":</b></font>&nbsp;&nbsp;"+printedSet[i].getDc_rights()+"<br>"); break;
				}
			}
			outputText = outputText.concat("<hr><br>");
			
		}
		outputText = outputText.concat("</p></html>");
		output.setText(outputText);
		output.addHyperlinkListener( this );

		JScrollPane scrollPane = new JScrollPane(output);

		scrollPane.setPreferredSize(new Dimension(400,500));
		root.add(scrollPane, "1,1,3,1");

		//OK-Button
		JButton ok_button = FormElement.getButton("print", "images/print.png", "print", null, true);
		ok_button.setActionCommand("ok");
		ok_button.addActionListener(this);
		root.add(ok_button, "1,2,1,2,RIGHT,CENTER");

		//Cancel-Button
		JButton cancel_button = FormElement.getButton("cancel", "images/cancel.png", "close_wnd", null, true);
		cancel_button.setActionCommand("cancel");
		cancel_button.addActionListener(this);

		root.add(cancel_button, "3,2,3,2 ,LEFT,CENTER");

		return root;
	}

	/** Invoked when an action occurs. */
	public void actionPerformed(ActionEvent e) 
	{
		String actionCmd = e.getActionCommand();
        
		if (actionCmd.equals("cancel")) 
		{
			Gui.my_forms[index].dispose();
		}
        
		if (actionCmd.equals("ok")) 
		{    
			PrinterJob printJob = PrinterJob.getPrinterJob();
			printJob.setPrintable(this);
			if (printJob.printDialog())
			  try { 
				printJob.print();
			  } catch(PrinterException pe) {
				System.out.println("Error printing: " + pe);
			  }
			Gui.my_forms[index].dispose(); 
      	
		}
	}
	
	/** Inherited */
	public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
	  if (pageIndex > 0) {
		return(NO_SUCH_PAGE);
	  } else {
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		// Turn off double buffering
		output.paint(g2d);
		// Turn double buffering back on
		return(PAGE_EXISTS);
	  }
	}
	/** Changes the cursor on hyperlink actions*/
	public void hyperlinkUpdate( HyperlinkEvent event )
	{
		if( event.getEventType() == HyperlinkEvent.EventType.ACTIVATED )
		{
			// Load some cursors
			Cursor cursor = output.getCursor();
			Cursor waitCursor = Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR );
			output.setCursor( waitCursor );

			// Handle the hyperlink change
			SwingUtilities.invokeLater(new LinkLoader( output,
								event.getURL(), cursor ) );
		}
	}

  
}