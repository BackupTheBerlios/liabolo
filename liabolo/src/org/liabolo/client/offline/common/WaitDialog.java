
package org.liabolo.client.offline.common;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.liabolo.client.offline.Gui;

/**
 * @author Jurij Henne and Maxim Bauer
 *
 * This is an implementation of a "Loading.."(or just "please wait") dialog. It pops up, when the GUI invokes some 
 * backend operations, like search or similar. 
 */
public class WaitDialog  extends JWindow  {
	   
	  // single instance of this class, used through out the scope of the application
	  private static WaitDialog dialog = new  WaitDialog();
	  private final static Cursor defaultCursor=Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	  private final static Cursor waitCursor=Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR );

	  private WaitDialog() {
	    try {
	    	createDialog();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	  }
	  /**
	   * Creates an new wait dialog
	   * @exception Exception if any exception, while creating GUI components and adding them to main desktop
	   */
	  private void createDialog() throws Exception 
	  {

		JPanel root = new JPanel();
		root.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
		JLabel label = new JLabel(Gui.lang.getString("please_wait2"));
		Dimension labelSize = label.getPreferredSize();	
		root.setPreferredSize(new Dimension(labelSize.width+26,labelSize.height+16));
		root.add(label);
		this.setSize(root.getPreferredSize());										
		this.getContentPane().add(root);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screenSize.width/2 - (labelSize.width/2),
								screenSize.height/2 - (labelSize.height/2));
	  }

	  /**
	   * This static method uses pre-created dialog, positions it in the center
	   * and displays it to the user.
	   */
	  public static void showDialog() {	
		Gui.desktop.setCursor( waitCursor );  
	  	dialog.show();
	  	dialog.paint(dialog.getGraphics());
	  }

	  /**

	   * This static method closes the wait dialog.
	   */
	  public static void closeDialog()   {
		Gui.desktop.setCursor(defaultCursor );  
	  	dialog.dispose();
	  }
	 
}
	
