/*
 * Created on 08.06.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.liabolo.client.offline.dialogs;

import info.clearthought.layout.TableLayout;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.Menu;
import org.liabolo.client.offline.ToolBar;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.common.Configurator;
import org.liabolo.common.Connection;

/**
 * @author Maxim Bauer
 * This class shows the preferences dialogue
 */
public class PreferencesDialog extends JDialog implements ActionListener {

	/** Title in the header of the dialog */
	private String title;
	/** The checkboxes to differentiate the options*/
	private JCheckBox box1,box2,box3,box4,box5;
	/** Show which language is selected*/
	private JComboBox languages;
	/** The availables languages must be contained in this attribute */
	private String[] lang = {Gui.lang.getString("german"), Gui.lang.getString("english")};
	/** Available global connections for connect with connect-button */
	private Object [] myConnections;
	
	/** Creates preferences dialog*/
	public PreferencesDialog()
	{
		super(Gui.modalParent,true);
		this.setTitle(Gui.lang.getString("prefs"));
		this.addContentPanel();
	}
	
	/** Layots and shows the dialog content */
	private void addContentPanel() {
		int border = 5;
		double root_size[][] =
				{{border, 
					TableLayout.PREFERRED,  
						TableLayout.PREFERRED, 
								border}, // Columns
				 {border, 
					TableLayout.PREFERRED, 
						TableLayout.PREFERRED, 
							border}}; // Rows
		TableLayout tl = new TableLayout(root_size);
		JPanel root = new JPanel(tl);

		double root2[][] =
				{{border,
					TableLayout.PREFERRED,
						TableLayout.PREFERRED,
								border}, // Columns
				 {border,
					TableLayout.PREFERRED, 
						TableLayout.PREFERRED,
							TableLayout.PREFERRED,
								TableLayout.PREFERRED,
									TableLayout.PREFERRED,
										TableLayout.PREFERRED,
											border}}; // Rows
		TableLayout tl1 = new TableLayout(root2);
		tl.setHGap(10);
		tl.setVGap(10);
		
		JPanel panel = new JPanel(tl1);
		
		/**Show toolbar*/
		JLabel label1 = FormElement.getInfiniteExtLabel(Gui.lang.getString("getToolBarVisible"),Font.PLAIN,null);
		panel.add(label1,"1,1,1,1,LEFT,CENTER");
		
		box1 = new JCheckBox();
		if(Gui.showToolbar)
			box1.setSelected(true);
		else
			box1.setSelected(false);
		box1.setEnabled(true);
		box1.setToolTipText(Gui.lang.getString("show_toolbar"));
		panel.add(box1,"2,1,2,1,RIGHT,CENTER");
		
		/** Warnings are on or off*/
		JLabel label2 = FormElement.getInfiniteExtLabel(Gui.lang.getString("getBranchWarning"),Font.PLAIN,null);
		panel.add(label2,"1,2,1,2,LEFT,CENTER");
		
		box2 = new JCheckBox();
		box2.setEnabled(true);
		box2.setSelected(Gui.showWarnings);
		box2.setToolTipText(Gui.lang.getString("show_warnings"));
		panel.add(box2,"2,2,2,2,RIGHT,CENTER");
		
		/** Choose a language*/
		JLabel label3 = FormElement.getInfiniteExtLabel(Gui.lang.getString("Language"),Font.PLAIN,null);
		panel.add(label3,"1,6,1,6,LEFT,CENTER");
		
		languages = new JComboBox(lang);
		if (Gui.language.equals("german"))
			languages.setSelectedItem(Gui.lang.getString("german"));
		else
			languages.setSelectedItem(Gui.lang.getString("english"));
		
		languages.setEnabled(true);
		languages.setToolTipText(Gui.lang.getString("languages_change"));
		panel.add(languages,"2,6,2,6,RIGHT,CENTER");
		
		/** Automatic connect*/
		JLabel label4 = FormElement.getInfiniteExtLabel(Gui.lang.getString("autom_connecten"),Font.PLAIN,null);
		panel.add(label4,"1,5,1,5,LEFT,CENTER");
		
		box3 = new JCheckBox();
		box3.setEnabled(true);
		box3.setSelected(Gui.connect);
		box3.setToolTipText(Gui.lang.getString("connect_to_all_server_aut"));
		panel.add(box3,"2,5,2,5,RIGHT,CENTER");
		
		JLabel label5 = FormElement.getInfiniteExtLabel(Gui.lang.getString("confirm_message"), Font.BOLD, null);
		panel.add(label5,"1,3,2,3");
		
		box4 = new JCheckBox();
		box4.setEnabled(true);
		box4.setSelected(Gui.showConfirmations);
		box4.setToolTipText(Gui.lang.getString("show_confirmations"));
		panel.add(box4,"2,3,2,3,RIGHT,CENTER");
		
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(BevelBorder.LOWERED),Gui.lang.getString("prefs")));
		
		root.add(panel,"1,1,2,1");
		
		JLabel label6 = FormElement.getInfiniteExtLabel(Gui.lang.getString("automatic_update"), Font.BOLD, null);
		panel.add(label6,"1,4,2,4");
		
		box5 = new JCheckBox();
		box5.setEnabled(true);
		box5.setSelected(Gui.automaticUpdate);
		box5.setToolTipText(Gui.lang.getString("automatic_update2"));
		panel.add(box5,"2,4,2,4,RIGHT,CENTER");
		
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(BevelBorder.LOWERED),Gui.lang.getString("prefs")));
		
		root.add(panel,"1,1,2,1");
		
		//OK-Button
		JButton ok_button = FormElement.getButton("ok", "images/ok.png", "save", null, true);
		ok_button.setActionCommand("ok");
		ok_button.addActionListener(this);
		root.add(ok_button, "1,2,1,2,FULL,CENTER");

		//Cancel-Button
		JButton cancel_button = FormElement.getButton("cancel", "images/cancel.png", "close_wnd", null, true);
		cancel_button.setActionCommand("cancel");
		cancel_button.addActionListener(this);
		root.add(cancel_button, "2,2,2,2,FULL,CENTER");
	
		this.getContentPane().add(root);
		this.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  		Dimension labelSize = this.getPreferredSize();
  		
  		this.setLocation(screenSize.width/2 - (labelSize.width/2),
		screenSize.height/2 - (labelSize.height/2));
		this.setVisible(true);	
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 *  Invoked when an action occurs. 
	 */
	public void actionPerformed(ActionEvent e) {
		String actionCmd = e.getActionCommand();
		myConnections = Gui.dispatcher.sortConnections(Gui.dispatcher.getAllConnections(),Gui.myLib).toArray();
		
		if (actionCmd.equals("cancel")) 
		{
			this.dispose();
		}
		
		if (actionCmd.equals("ok")) 
		{
			
			
			if (languages.getSelectedItem().toString() == Gui.lang.getString("german")){
				//System.out.println(languages.getSelectedItem().toString()+"deustch");

				Configurator.setProperty("language","german","gui-options");
				Gui.language ="german";

				//Gui.lang = Gui.dispatcher.initLanguage(Locale.GERMAN);
				
			}
			else {
				//System.out.println(languages.getSelectedItem().toString()+"english");

				Configurator.setProperty("language","english","gui-options");
				Gui.language ="english";

				 }
			
			if(box1.isSelected()){
				Configurator.setProperty("toolbar",Integer.toString(1),"gui-options");
				Gui.toolbar.setVisible(true);
				Gui.modalParent.update(Gui.modalParent.getGraphics());
				Gui.showToolbar = true;
			}
			else
			{

				Configurator.setProperty("toolbar","0","gui-options");

				Gui.toolbar.setVisible(false);
				Gui.modalParent.update(Gui.modalParent.getGraphics());
				Gui.showToolbar = false;
			}
			if(box2.isSelected())
			{
				Configurator.setProperty("warnings","1","gui-options");
				Gui.showWarnings = true;
			}
			else
			{
				Configurator.setProperty("warnings","0","gui-options");
				Gui.showWarnings = false;
			}
			if(box4.isSelected())
			{
				Configurator.setProperty("confirmations","1","gui-options");
				Gui.showConfirmations = true;
			}
			else
			{
				Configurator.setProperty("confirmations","0","gui-options");
				Gui.showConfirmations = false;
			}
			if(box3.isSelected())
			{
				Configurator.setProperty("connect","1","gui-options");
				Gui.connect = true;
				ToolBar.connectButton.setSelected(true);
				Menu.connect.setSelected(true);
				ArrayList connectedLibs = new ArrayList();
				for(int i = 0; i< this.myConnections.length;i++)
				{
					System.out.println("Connect");
					Connection conn = (Connection)myConnections[i];
					connectedLibs.add(Gui.dispatcher.connect(conn));
				}
				
				ToolBar.connectButton.setToolTipText(Gui.lang.getString("connected"));	
			}
			else
			{
				Configurator.setProperty("connect","0","gui-options");
				Gui.connect = false;
				ToolBar.connectButton.setSelected(false);
				Menu.connect.setSelected(false);
				ToolBar.connectButton.setToolTipText(Gui.lang.getString("not_connected"));	
				Gui.dispatcher.disconnectFromAllConnectedLibraries();
			}
			
			if(box5.isSelected())
			{
				Configurator.setProperty("update","1","gui-options");
				Gui.automaticUpdate = true;
			}
			else
			{
				Configurator.setProperty("update","0","gui-options");
				Gui.automaticUpdate = false;
			}
			
			Configurator.store();
			this.dispose(); 
			
			}
		}

}

