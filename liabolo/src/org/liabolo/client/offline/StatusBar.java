/*
 * Created on 13.05.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.liabolo.client.offline;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.liabolo.common.Logger;
import org.liabolo.common.MessageReceiver;


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
