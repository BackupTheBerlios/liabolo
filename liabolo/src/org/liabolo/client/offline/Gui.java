/*
 * Created on 15.01.2004 by Easy (Stefan Willer)
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
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.forms.DefaultForm;
import org.liabolo.client.offline.forms.TreeForm;
import org.liabolo.common.Configurator;
import org.liabolo.common.Connection;
import org.liabolo.common.Logger;
import org.liabolo.repository.Library;
import org.liabolo.service.Dispatcher;

import com.jgoodies.plaf.FontUtils;
import com.jgoodies.plaf.plastic.PlasticLookAndFeel;
import com.jgoodies.plaf.plastic.theme.SkyKrupp;

/**
 *@author Jurij Henne
 */
public class Gui extends JFrame{
	
	/** Main work space */
	public static JDesktopPane desktop;
	/** Status bar of the applications */
	public static StatusBar statusBar;
	/** Instance of the middleware component. Used for communication with backend. */
	public static Dispatcher dispatcher;
	/** Instance of actuall connection */
	public static Library myLib;
	/** Form holder array. The size is defined by config file. */
	public static DefaultForm [] my_forms;
	/** Toolbar instance */
	public static ToolBar toolbar;
	/** Logger intanc e*/
	private static Logger log;
	/** Menubar of main work space */
	private Menu menu;
	/** Referrence of the application instance. Used as parent for modal dialogs. */
	public static JFrame modalParent;
	/** Instance of the language extension */
	public static ResourceBundle lang;
	/** Indicates if any warnings should be shown. */
	public static boolean showWarnings;
	/** Indicates if any Confirmations should be shown.*/
	public static boolean showConfirmations;
	/** Indicates if toolbar should be shown.*/
	public static boolean showToolbar;
	/** Instance of the property object */
	//private static Properties guiProperty = new Properties();
	/** Path to config file */
    public static String configMainDir;
	/** Path to log file */
    public static String logMainDir;
    /** Indicates which language is choosen*/
    public static String language;
    /** Indicates if the automatic connections in preferences is selected*/
    public static boolean connect;
    public static boolean automaticUpdate;
    
    //private static Object [] myConnections;
/**
 * Creates the main frame of  application
 * @param title Title displayed in window header
 */
	 public Gui(String title)
	 {
		super(title);
		modalParent = this;
		this.setIconImage( FormElement.getFrameIcon("images/liabolo_icon.png"));
		//this.setCursor(Cursor.getPredefinedCursor( Cursor.CROSSHAIR_CURSOR ));
		//Make the big window be indented 50 pixels from each edge of the screen.
		int inset = 50;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(inset, inset,
					  screenSize.width  - inset*2,
					  screenSize.height - inset*2);
	
		// Add Menubar
		menu=new Menu();
		this.setJMenuBar(menu);
	
	
		// Create user defined content pane
		JPanel root = new JPanel(new BorderLayout());
		this.setContentPane(root);
		
		//Add Toolbar
		toolbar = new ToolBar(menu);
		root.add(toolbar, BorderLayout.WEST);
		if(Configurator.getIntProperty("toolbar",0,"gui-options")==0)
		{	
			showToolbar = false;
			toolbar.setVisible(showToolbar);
		}
		else
		{
			showToolbar = true;
			toolbar.setVisible(showToolbar);
		}
		if(Configurator.getIntProperty("connect",0,"gui-options")==1)
			connect = true;
		else
			connect = false;
		
		if(Configurator.getIntProperty("update",0,"gui-options")==1)
			automaticUpdate = true;
		else
			automaticUpdate = false;
		//Add Statusbar
		statusBar=new StatusBar();
		root.add(statusBar,BorderLayout.SOUTH);
	
		// Create scrollable layered pane as main workspace
		desktop = new JDesktopPane(); 
		desktop.setPreferredSize(new Dimension(800, 580));
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		desktop.putClientProperty("JDesktopPane.dragMode","outline");
		JScrollPane scrollPane = new JScrollPane(desktop);
		root.add(scrollPane,BorderLayout.CENTER);
		//Make dragging a little faster but perhaps uglier.
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		//Initialize the formular holder array
		int forms = Configurator.getIntProperty("forms",0,"gui-forms");
		my_forms = new DefaultForm[forms];
		// Initialize the Logger
		log = Logger.getLogger(Gui.class);
		//Add window listener for user defined  behavior
		int treeFormIndex = Configurator.getIntProperty("treeForm",0,"gui-forms");
		TreeForm treeform = new TreeForm(treeFormIndex, 0);
		this.addWindowListener(menu);
	
	 }
 
	 public static DefaultForm getForm(int index)
	 {
	 	return my_forms[index];
	 }

    public static void main(String[] args)
    {
		Configurator.config();

		// Setup third party LookAndFeel (JGoodies)
		PlasticLookAndFeel.setMyCurrentTheme(new SkyKrupp());
		FontUtils.useSystemFontSettings();
		try
		{
			//UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
			Gui.dispatcher = new Dispatcher();

            /*if(System.getProperty("liabolo.config")!=null)
                Configurator.configMainDir = System.getProperty("liabolo.config");
			guiProperty.load(new FileInputStream(Configurator.configMainDir + "/gui.properties"));
			*/

            //System.out.println(guiProperty.getProperty("language"));
			language = Configurator.getProperty("language","german","gui-options");
			System.out.println(language);
			if (language.equals("german"))
				lang = dispatcher.initLanguage(Locale.GERMAN);
			else
				lang = dispatcher.initLanguage(Locale.ENGLISH);
			
			if (Configurator.getIntProperty("warnings",0,"gui-options")==1)
				showWarnings = true;
			else
				showWarnings = false;
			
			if(Configurator.getIntProperty("confirmations",0,"gui-options")==1)
				showConfirmations = true;
			else
				showConfirmations = false;
			
			UIManager.setLookAndFeel("com.jgoodies.plaf.plastic.Plastic3DLookAndFeel");
			//Start with splash window
			System.out.println("USER-FOLDER:"+System.getProperty("user.home"));
			SplashScreen sscreen = new SplashScreen(2000);

		} 
		catch (Exception e) 
		{
			log.debug(e.toString(),3);
		}
			
    }

}