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
package org.liabolo.client.offline;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.liabolo.common.Logger;
import org.liabolo.common.MessageReceiver;

/**
 * @author Jurij Henne
 *
 *	Displays the status bar 
 */

public class StatusBar extends JPanel implements MessageReceiver{
	
	/** Text to display in status bar */
	private JLabel statusBarText = new JLabel(Gui.lang.getString("connected_with_localDB"));
	
	/** Creates the status bar of the application */
	public StatusBar() 
	{
		//statusBarText = new JLabel(" Connected to local database..");
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.add(statusBarText,BorderLayout.WEST);
		Logger.registerAsReceiver(this);	
	}

	/**
	 *  Displays the specified message inside the statusbar
	 * 	@param message Message to display
	 **/
	public void setReceiverMessage(String message) {
		this.statusBarText.setText(" "+message);
		this.update(this.getGraphics());
		this.repaint();	
	}
}
