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
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import org.liabolo.client.offline.common.FormElement;
import org.liabolo.common.Connection;


/**
 * @author Jurij Henne
 *
*	Displays a splash screen on the application startup
 */
public class SplashScreen extends JWindow{
	
	/** Instance of main window */
	private Gui gui;

/**
 * Creates and show splash image
 * @param waitTime time to display splash image
 */
	public SplashScreen(int waitTime)
	{
		super();
		JLabel l = new JLabel(FormElement.createImageIcon("images/liabolo_splash.jpg"));
		this.getContentPane().add(l, BorderLayout.CENTER);
		pack();
		Dimension screenSize =
		  Toolkit.getDefaultToolkit().getScreenSize();
		Dimension labelSize = l.getPreferredSize();
		setLocation(screenSize.width/2 - (labelSize.width/2),
					screenSize.height/2 - (labelSize.height/2));

		final int pause = waitTime;
		final Runnable closerRunner = new Runnable()
		{
			public void run()
			{
				showGui();
				setVisible(false);
				dispose();
				//showGui();
			}
		};

		Runnable waitRunner = new Runnable()
		{
			public void run()
			{
				try
				{
					Thread.sleep(pause);
					SwingUtilities.invokeAndWait(closerRunner);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		};
		setVisible(true);
		Thread splashThread = new Thread(waitRunner, "SplashThread");
		splashThread.start();
	}

	/** Initializes connection to local database and if successful starts the main application */
	 private void showGui()
	 {
		Connection localConnection = Gui.dispatcher.getConnection("local");
        Gui.myLib =  Gui.dispatcher.connect(localConnection);
        
        if(Gui.myLib!=null)
	 	{
			gui = new Gui(Gui.lang.getString("liabolo"));
			gui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			gui.setVisible(true);
			if(Gui.connect)
				{
				Object [] myConnections = Gui.dispatcher.sortConnections(Gui.dispatcher.getAllConnections(),Gui.myLib).toArray();
				ArrayList connectedLibs = new ArrayList();
				for(int i = 0; i<myConnections.length;i++)
				{
					System.out.println("Connect");
					Connection conn = (Connection)myConnections[i];
					connectedLibs.add(Gui.dispatcher.connect(conn));
				}
				ToolBar.connectButton.setSelected(true);
				Menu.connect.setSelected(true);
				ToolBar.connectButton.setToolTipText(Gui.lang.getString("connected"));	
				}
		}
		else
		{
			JOptionPane.showConfirmDialog(
					Gui.modalParent,
						Gui.lang.getString("local_db_error"),
							Gui.lang.getString("warning"),
								JOptionPane.CANCEL_OPTION);
		}
		
	 }

}
