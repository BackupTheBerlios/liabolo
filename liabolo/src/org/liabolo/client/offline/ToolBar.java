
package org.liabolo.client.offline;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import org.liabolo.client.offline.common.*;

/**
 * @author Jurij Henne
 *
 */
public class ToolBar extends JToolBar implements ActionListener {
	
	public static JToggleButton connectButton;
	/**
	 * Creates the tool bar of the application
	 * @param menu action listener instance
	 */
	public ToolBar (Menu menu) 
	{
		//tb.setMargin(new Insets(0,0,0,0));			//Sets the margin between the toolbar's border and its buttons.
			
		this.setOrientation(JToolBar.VERTICAL);
		this.setFloatable(true);

        connectButton = FormElement.getToggleButton(null,"images/connect2da.png", "connect", new Insets(1,1,1,1), false);
		connectButton.setSelectedIcon(FormElement.createImageIcon("images/connect2.png"));
		connectButton.setActionCommand("connect");
		connectButton.setSelected(false);
		connectButton.addActionListener(menu);
		this.add(connectButton);
		
		
		this.addSeparator();	
		
		JButton addNewButton = FormElement.getButton(null, "images/book.png", "add_new_md", new Insets(1,1,1,1), false);
		addNewButton.setActionCommand("newBook");
		addNewButton.addActionListener(menu);
		this.add(addNewButton);	
		
		JButton searchButton = FormElement.getButton(null, "images/searchedit.png", "search_metadata", new Insets(1,1,1,1), false);
		searchButton.setActionCommand("mdSearch");
		searchButton.addActionListener(menu);
		this.add(searchButton);											 

		this.addSeparator();									
						
		JButton statButton = FormElement.getButton(null, "images/tree.png", "MyLib", new Insets(1,1,1,1), false);
		statButton.setActionCommand("homeLib");
		statButton.addActionListener(menu);
		this.add(statButton);		
		
		JButton helpButton = FormElement.getButton(null, "images/help.png", "help_content", new Insets(1,1,1,1), false);
		helpButton.setActionCommand("help");
		helpButton.addActionListener(menu);
		this.add(helpButton);				 

		this.addSeparator();

	}
	
	/**
	 * Invoked when an action occurs.
	 */
	public void actionPerformed(ActionEvent e) 
	{
	}


}
