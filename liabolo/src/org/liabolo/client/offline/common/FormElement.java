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

import java.awt.Color;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.JToggleButton.ToggleButtonModel;

import org.liabolo.client.offline.Gui;

public class FormElement {
		
	/**
	 *  Returns an simple form label with predefined font
	 * @param text Text to display in label
	 * @param fonttype sets the font style. The style can be PLAIN, BOLD or ITALIC 
	 * @param c Color of label text
	 * @return  label
	 */
	public static JLabel getLabel(String text, int style, Color c) {
		JLabel label =new JLabel(Gui.lang.getString(text));
		//label.setFont(new Font("Verdana",style,12));
		if(c!=null)
			label.setForeground(c);
		
		return label;		
	}
	
	/**
	 *  Returns an simple form label with predefined font and without language conversion
	 * @param text Text to display in label
	 * @param fonttype Specifies the font style. The style can be PLAIN, BOLD or ITALIC 
	 * @param c Color of label text
	 * @return  label
	 */
	public static JLabel getInfiniteLabel(String text, int style, Color c) {
		JLabel label = new JLabel(text);
		//label.setFont(new Font("Verdana",style,12));
		if(c!=null)
			label.setForeground(c);
		
		return label;		
	}
	
	/**
	 *  Returns an extended form label(label with concatenated ":" behind it) with predefined font
	 * @param text Text to display in label
	 * @param fonttype sets the font style. The style can be PLAIN, BOLD or ITALIC 
	 * @param c Color of label text
	 * @return  label
	 */
	public static JLabel getExtLabel(String text, int style, Color c) {
		JLabel label =new JLabel((Gui.lang.getString(text))+":");
		//label.setFont(new Font("Verdana",style,12));
		if(c!=null)
			label.setForeground(c);
		
		return label;		
	}
	/**
	 *  Returns an extended form label with predefined font and without language conversion 
	 * @param text Text to display in label
	 * @param fonttype sets the font style. The style can be PLAIN, BOLD or ITALIC 
	 * @param c Color of label text
	 * @return  label
	 */
	public static JLabel getInfiniteExtLabel(String text, int style, Color c) {
		JLabel label = new JLabel(text+":");
		//label.setFont(new Font("Verdana",style,12));
		if(c!=null)
			label.setForeground(c);
		
		return label;		
	}

	/**
	 *  Returns an extended form label with LookAndFeel font
	 * @param text Text to display in label
	 * @param fonttype sets the font style. The style can be PLAIN, BOLD or ITALIC 
	 * @param c Color of label text
	 * @return  label
	 */
	public static JLabel getSystemFormLabel(String text, Color c) {
		JLabel label = new JLabel(Gui.lang.getString(text)+":");
		if(c!=null)
			label.setForeground(c);	
		return label;		
	}
	/**
	 * Returns a form button with predefined font and style
	 * @param label Text to display on button. If label ist null, text will be not displayed
	 * @param iconpath	Path for icon. If iconpath is null, icon will be not displayed
	 * @param ttext	Tool tip text. If ttext is null, tool tip will be not displayed
	 * @param m	defines the insets. If m is null, the default insets will be used
	 * @param focusable	needed for tab and key actions
	 * @return button
	 */	
	public static JButton getButton(String label, String iconpath, String ttext, Insets m, boolean focusable){
		
		JButton button= new JButton();
		//button.setHorizontalAlignment(SwingConstants.LEFT );
		//button.setHorizontalTextPosition(SwingConstants.LEFT );
	
		if(label!=null)
		{
			button.setText(Gui.lang.getString(label));
		//	button.setFont(new Font("Verdana",Font.BOLD,12));
		}
		if(iconpath!=null)
			button.setIcon(createImageIcon(iconpath));
			
		if(ttext!=null)
			button.setToolTipText(Gui.lang.getString(ttext));
		if(m!=null)
			button.setMargin(m);
			
		button.setFocusable(focusable);		
		
		return button;
	}

	/**
	 * Returns a form button with predefined font, style and without language conversion
	 * @param label Text to display on button. If label ist null, text will be not displayed
	 * @param iconpath	Path for icon. If iconpath is null, icon will be not displayed
	 * @param ttext	Tool tip text. If ttext is null, tool tip will be not displayed
	 * @param m	defines the insets. If m is null, the default insets will be used
	 * @param focusable	needed for tab and key actions
	 * @return button
	 */	
	public static JButton getInfiniteButton(String label, String iconpath, String ttext, Insets m, boolean focusable){
		
		JButton button= new JButton();
	
		if(label!=null)
		{
			button.setText(label);
		//	button.setFont(new Font("Verdana",Font.BOLD,12));
		}
		if(iconpath!=null)
			button.setIcon(createImageIcon(iconpath));
			
		if(ttext!=null)
			button.setToolTipText(Gui.lang.getString(ttext));
		if(m!=null)
			button.setMargin(m);
			
		button.setFocusable(focusable);		
		
		return button;
	}
	
	
	/**
	 * Returns a form button with predefined font and style
	 * @param label Text to display on button. If label ist null, text will be not displayed
	 * @param iconpath	Path for icon. If iconpath is null, icon will be not displayed
	 * @param ttext	Tool tip text. If ttext is null, tool tip will be not displayed
	 * @param m	defines the insets. If m is null, the default insets will be used
	 * @param focusable	needed for tab and key actions
	 * @return button
	 */	
	public static JToggleButton getToggleButton(String label, String iconpath, String ttext, Insets m, boolean focusable){
		
		JToggleButton button= new JToggleButton();
		//button.setHorizontalAlignment(SwingConstants.LEFT );
		//button.setHorizontalTextPosition(SwingConstants.LEFT );
	
		if(label!=null)
		{
			button.setText(Gui.lang.getString(label));
		//	button.setFont(new Font("Verdana",Font.BOLD,12));
		}
		if(iconpath!=null)
			button.setIcon(createImageIcon(iconpath));
			
		if(ttext!=null)
			button.setToolTipText(Gui.lang.getString(ttext));
		if(m!=null)
			button.setMargin(m);
			
		button.setFocusable(focusable);		
		button.setModel(new ToggleButtonModel()
		{
			/* (non-Javadoc)
			 * @see javax.swing.JToggleButton.ToggleButtonModel#setPressed(boolean)
			 */
			public void setPressed(boolean arg0) {
				// TODO Auto-generated method stub
				super.setPressed(arg0);
			}

		});
		
		
		
		return button;
	}
/**
 *  Returns  a combo box with predefined font 
 * @param labels Items of the combo box
 * @param index  Speciefies the selected index
 * @param style	Specifies the font style. The style can be PLAIN, BOLD or ITALIC 
 * @param ttext Specifies the tool tip text. If ttext is null, tool tip will not be displayed
 * @return
 */

	public static JComboBox getComboBox(Object[] labels, int index, int style, String ttext)
	{		
		JComboBox combo_button = new JComboBox(labels);
		combo_button.setSelectedIndex(index);
	//	combo_button.setFont(new Font("Verdana",style,12));
		if(ttext!=null)
			combo_button.setToolTipText(Gui.lang.getString(ttext));
			
		return combo_button;
	}
	
	/**
	 * Returns an ImageIcon, or null if the path was invalid.
	 * @param path Path for image
	 * @return component icon
	 */
	public static ImageIcon createImageIcon(String path) 
	{
		java.net.URL imgURL = Gui.class.getResource(path);
		if (imgURL != null) 
			return new ImageIcon(imgURL);
		 else 
		 {
			System.err.println("Couldn't find file: " + path);
			return null;
		  }
	}

	/**
	 * Returns a frame icon , or null if the path was invalid.
	 * @param path Path for image
	 * @return 
	 */
	public static Image getFrameIcon(String path) 
	{
		java.net.URL imgURL = Gui.class.getResource(path);
		if (imgURL != null) 
			return Toolkit.getDefaultToolkit().getImage(imgURL);
		 else 
		 {
			System.err.println("Couldn't find file: " + path);
			return null;
		  }
	}
}
