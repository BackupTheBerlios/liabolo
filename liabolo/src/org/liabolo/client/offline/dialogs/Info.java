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
package org.liabolo.client.offline.dialogs;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.*;
import org.liabolo.common.Logger;

/**
 * @author Jurij Henne
 *
 * An implementation of the  info dialog
 */
public class Info{
	

	/** Title in dialog header */
	private String title;
	
	private static Logger log = Logger.getLogger(Info.class);	
	
	/** Constructs an new info dialog*/
	public Info() 
	{	
		//super(null,INFORMATION_MESSAGE,DEFAULT_OPTION,FormElement.createImageIcon("images/info.png"));
		//this.frameIcon = FormElement.createImageIcon("images/info.png");
		this.title =  Gui.lang.getString("info");
		//this.setIcon(FormElement.createImageIcon("images/info.png"));
		this.addInfoPanel();
	}

	/** Layouts the components inside the dialog and shows it */
	private void  addInfoPanel() 
	{		
		double root_size[][] =
				{{TableLayout.PREFERRED, 30}, // Columns
				 {10, TableLayout.PREFERRED,10, TableLayout.PREFERRED, 10}}; // Rows

		JPanel root = new JPanel(new TableLayout(root_size));
		JLabel infologo = new JLabel(FormElement.createImageIcon("images/infologo.jpg"));
		
		JPanel infoText = new JPanel(new BorderLayout());
		infoText.setBorder
				(BorderFactory.createTitledBorder(
						BorderFactory.createEtchedBorder(BevelBorder.LOWERED)));
		
		JEditorPane text = new JEditorPane();
		text.setContentType("text/html");	
		text.setEditable(true);
		text.setAutoscrolls(true);
		text.setAlignmentX(JEditorPane.CENTER_ALIGNMENT);
		text.setMargin(new Insets(3, 3, 3, 3));
		text.setText("<html><font size=\"2\" face=\"Tahoma\">"+
					"<strong>Liabolo - die teuflisch gute Literaturverwaltung<br>"+
					  "</strong><br>"+
					  "Liabolo Copyright (c) 2004 P30-Projekt<br>"+
					  "ver&ouml;ffentlicht unter GPL V2 06/1991<br>"+
					  "Lizenz siehe Datei LICENSE.txt<br>"+
					"<br>"+
					  "<strong>Web</strong>: http://www.liabolo.org<br>"+
					  "<strong>E-Mail</strong>: info@liabolo.org<br>"+
					  "<br>"+
					  "<strong>Entwicklerteam</strong><br>"+
					  "Stefan Willer, Jurij Henne, Thorsten Schloermann, Maxim Bauer<br>"+
					  "<br>"+
					  "<strong>Projektkoordination</strong><br>"+
					  "Frank Slotta<br>"+
					  "<br>"+
					  "<strong>Projektbetreuung/Qualit&auml;tssicherung</strong><br>"+
					  "Hendrik Eggers<br></font>"+
					"</html>");
		
		infoText.add(text,BorderLayout.CENTER);
		root.add(infologo,"0,1,0,1,CENTER,CENTER");
		root.add(infoText,"0,3,0,3,FULL,CENTER");
		root.setSize(400,400);	
		JOptionPane.showMessageDialog(Gui.modalParent, root,this.title, JOptionPane.INFORMATION_MESSAGE,FormElement.createImageIcon("images/spacer.gif"));
	}
}
