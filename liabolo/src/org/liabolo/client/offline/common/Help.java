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


import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.liabolo.client.offline.Gui;

/**
 * @author Jurij Henne
 */
public class Help  extends JDialog implements  ActionListener, HyperlinkListener, Printable{
	
	/** Help content will be displayed inside this panel */
	private JEditorPane output;
		
	/** Creates a frame with navigation toolbar and panel for help content */
	public Help() 
	{	
		super(Gui.modalParent,true);
		this.setTitle(Gui.lang.getString("liabolo_help"));
		this.setContentPane(new JPanel(new BorderLayout()));
		// Init toolbar 
		JToolBar tool = new JToolBar();
		tool.setOrientation(JToolBar.HORIZONTAL);
		tool.setFloatable(false);
		JButton indexButton = FormElement.getButton(null, "images/help_index.png", null, new Insets(1,1,1,1), false);
		indexButton.setActionCommand("index");
		indexButton.addActionListener(this);
		tool.add(indexButton);
											 
		tool.addSeparator();	
		JButton printButton = FormElement.getButton(null, "images/help_print.png", null, new Insets(1,1,1,1), false);
		printButton.setActionCommand("print");
		printButton.addActionListener(this);
		tool.add(printButton);								 

		tool.addSeparator();	
		tool.addSeparator();	
		JButton exitButton = FormElement.getButton(null, "images/exit_b.png", null, new Insets(1,1,1,1), false);
		exitButton.setActionCommand("exit");
		exitButton.addActionListener(this);
		tool.add(exitButton);							 

		this.getContentPane().add(tool, BorderLayout.NORTH);
		
		//Init panel for help content
		output = new JEditorPane();
		initHelp();
		output.setContentType("text/html");	
		output.setEditable(false);
		output.setAutoscrolls(true);
		output.setAlignmentX(JEditorPane.CENTER_ALIGNMENT);
		output.setMargin(new Insets(3, 3, 3, 3));
		showFormContent();
	}
	
	/** Loads the index page of help content */
	private void initHelp()
	{
		java.net.URL helpURL = Gui.class.getResource("help/index.htm");
		try{

		output.setPage(helpURL);
		}
		catch( MalformedURLException me )
		{
			Gui.statusBar.setReceiverMessage( "Malformed URL: " + me );
		}
		catch( IOException ie )
		{
			Gui.statusBar.setReceiverMessage( "IOException: " + ie );
		}
	}
	/** Layouts the components inside the dialog */
	private void showFormContent() 
	{
		int border = 5;
		double root_size[][] =
			{{border, 
				TableLayout.PREFERRED, 
							border}, // Columns
			 {border, 
				TableLayout.PREFERRED, 
						border}}; // Rows

		JPanel root =  new JPanel(new TableLayout(root_size));


		output.addHyperlinkListener( this );
	
		JScrollPane scrollPane = new JScrollPane(output);

		scrollPane.setPreferredSize(new Dimension(600,500));
		root.add(scrollPane, "1,1,1,1");

		this.getContentPane().add(root);
		this.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension labelSize = this.getPreferredSize();
  		
		this.setLocation(screenSize.width/2 - (labelSize.width/2),
							screenSize.height/2 - (labelSize.height/2));
		this.setVisible(true);	

	
	}

	/** Implementation of the inherited method print() */
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
	
	/** Implementation of the inherited method hyperlinkUpdate() */
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
	
	/** Invoked when an action occurs. */	
	public void actionPerformed(ActionEvent e) 
	{
		String actionCmd = e.getActionCommand();
			

		if (actionCmd.equals("index")) 
		{
			initHelp();
		}
		
		if (actionCmd.equals("print")) 
		{	
			PrinterJob printJob = PrinterJob.getPrinterJob();
			printJob.setPrintable(this);
			if (printJob.printDialog())
			  try { 
				printJob.print();
			  } catch(PrinterException pe) {
				Gui.statusBar.setReceiverMessage("Error printing: " + pe);
			  }
		}
		
		if (actionCmd.equals("exit")) 
		{
			this.dispose();
		}
	}

}