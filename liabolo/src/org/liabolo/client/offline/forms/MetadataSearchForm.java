/*
 * Created on 08.03.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.liabolo.client.offline.forms;


import info.clearthought.layout.TableLayout;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.common.WaitDialog;
import org.liabolo.common.Branch;
import org.liabolo.common.Configurator;
import org.liabolo.common.MetaData;

/**
 * @author Jurij Henne
 *
 * An implementation of a "search metadata" form.
 * The user can perform the search by string or he can list all items in the selected branches.
 */

public class MetadataSearchForm extends DefaultForm implements ActionListener,ChangeListener, ListSelectionListener, KeyListener
{

	private JTabbedPane tabbedPane;
	private int selectedIndex;
    private JCheckBox creatorCB;
    private JCheckBox titleCB;
	private JCheckBox publisherCB;
	private JCheckBox descriptionCB;
	private Object[] branchList;
	/** Available branches as multiple interval selection list */
	private JList branches;
	private JCheckBox selectAll;
    private JTextField searchString = new JTextField(20);

    
	/**
	 * Constructs a new seach form 
	 * @param index specifies a unique 'position' of  the form in form holder-array
	 * @param position specifies the location on desktop
	 */
    public MetadataSearchForm (int index, Point location) 
    {
		super(index, Gui.lang.getString("search_metadata"),"images/search.png");
		Collection allBranches = Gui.dispatcher.getAllBranches(Gui.myLib);
		this.branchList = (Gui.dispatcher.sortBranch(allBranches,Gui.myLib)).toArray();
		Object [] branchDescription = new Object[branchList.length];
		
		for(int i=0;i<branchList.length;i++)
		{
			branchDescription[i] = ((Branch)branchList[i]).getDescription();
		}
		this.branches = new JList(branchDescription);
		this.branches.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.branches.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
			{
				if (e.getClickCount() == 2) 
					actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"submit"));
			}
		});
		this.branches.addListSelectionListener(this);
		
		if(selectAll==null)
		{
			selectAll = new JCheckBox(Gui.lang.getString("select_all"));
			selectAll.setToolTipText(Gui.lang.getString("select_all"));
			selectAll.setActionCommand("select_all");
			selectAll.addActionListener(this);
		} 
			
		this.tabbedPane = new JTabbedPane();	
    	this.selectedIndex = 0;
		
		this.addFormFrame(location);
    }

 
 /**
  * Switches to  the desired search-panel
  * @param index Index of the desired panel. 0 = simple search(by string), 1 = list items in branches
  */
    public void setSelectedPanel(int index) {
		tabbedPane.setSelectedIndex(index);
    }

	/** Implemetation of inherited method. Returns the layouted  content panel of the form */
    public  JPanel showFormContent() {
		
		JPanel root = getRootPanel(); //inherited	
		JPanel searchPanel = getSearchPanel();
		JPanel advSearchPanel = getAdvSearchPanel();
		JPanel browserPanel = getBrowsePanel();
		tabbedPane.add("  "+Gui.lang.getString("search")+"  ",searchPanel);
		//tabbedPane.add("  "+Gui.lang.getString("adv_search")+"  ",advSearchPanel);
		tabbedPane.add("  "+Gui.lang.getString("browse")+"  ",browserPanel);
		tabbedPane.setSelectedIndex(selectedIndex);
		tabbedPane.addChangeListener(this);
		tabbedPane.setFocusable(false);
		root.add(tabbedPane,"1,1,3,1, FULL,FULL");
		//Submit-Button
		JButton submit_button = FormElement.getButton("submit", "images/search.png", null, null, true);
		submit_button.setActionCommand("submit");
		submit_button.addActionListener(this);
		submit_button.addKeyListener(this);
		root.add(submit_button, "1,2,1,2,FULL,CENTER");

		//Cancel-Button
		JButton cancel_button = FormElement.getButton("cancel", "images/cancel.png", null, null, true);
		cancel_button.setActionCommand("cancel");
		cancel_button.addActionListener(this);
		cancel_button.addKeyListener(this);
		root.add(cancel_button, "3,2,3,2,FULL,CENTER");
		
		return root;
        
    }
  
  /**
   * Return an array of checked search fields
   * @return
   */
	private short[] getSelectedFields() {
		
		short[] types = new short[4];
		if (creatorCB.isSelected())
			types[0] = MetaData.DC_CREATOR;	
		else types[0]=-1;
		
		if (titleCB.isSelected())
			types[1] = MetaData.DC_TITLE;
		else types[1]=-1;
		
		if (publisherCB.isSelected())
			types[2] = MetaData.DC_PUBLISHER;
		else types[2]=-1;
		
		if (descriptionCB.isSelected())
			types[3] = MetaData.DC_DESCRIPTION;
		else types[3]=-1;
			
		return types;
		
	}

	/**
	 * Returns the "simple search" panel
	 * @return
	 */
	private JPanel getSearchPanel(){
		
		int border = 5;
		// Creating the Grid for TableLayout
		double size[][] =
				{{border, 
					TableLayout.PREFERRED,
						TableLayout.PREFERRED,
							TableLayout.PREFERRED, border}, // Columns
				 {border, 
					TableLayout.PREFERRED,
						TableLayout.PREFERRED,
							TableLayout.PREFERRED,  
										border}}; 		// Rows
		TableLayout tl = new TableLayout(size);

		//Space between Columns and Rows
		tl.setHGap(10);
		tl.setVGap(10);
		
		JPanel panel = new JPanel(tl);
		panel.setBorder
				(BorderFactory.createTitledBorder(
						BorderFactory.createEtchedBorder(BevelBorder.LOWERED), Gui.lang.getString("db_search")));

		// first line	
		creatorCB = new JCheckBox(Gui.lang.getString("creator"), true);
		creatorCB.setFocusable(false);
		creatorCB.setFont(new Font("Verdana",Font.PLAIN,12));
		panel.add(creatorCB, "1,1,2,1,LEFT,CENTER");

		titleCB = new JCheckBox(Gui.lang.getString("title"), true);
		titleCB.setFocusable(false);
		titleCB.setFont(new Font("Verdana",Font.PLAIN,12));
		panel.add(titleCB, "3,1,3,1, LEFT, CENTER");
		// second line		
		publisherCB = new JCheckBox(Gui.lang.getString("publisher"), false);
		publisherCB.setFocusable(false);
		publisherCB.setFont(new Font("Verdana",Font.PLAIN,12));
		panel.add(publisherCB, "1,2,2,2,LEFT,CENTER");

		descriptionCB = new JCheckBox(Gui.lang.getString("description"), false);
		descriptionCB.setFocusable(false);
		descriptionCB.setFont(new Font("Verdana",Font.PLAIN,12));
		panel.add(descriptionCB, "3,2,3,2, LEFT, CENTER");

		// third line
		JLabel formLabel = FormElement.getExtLabel("string", Font.PLAIN, null);
		formLabel.setFont(new Font("Verdana",Font.PLAIN,12));
		panel.add(formLabel, "1,3,1,3,RIGHT,CENTER");
		searchString.addKeyListener(this);
		panel.add(searchString, "2,3,3,3,FULL,CENTER");
		
		return panel;
	
	}
	
	/**
	 * Returns the "advanced search" panel. Note: not implemented yet!
	 * @return
	 */
	private JPanel getAdvSearchPanel()
	{			
		int border = 5;
		// Creating the Grid for TableLayout
		double size[][] =
				{{border,
						TableLayout.PREFERRED, 
							border}, // Columns
				 {border, 
					TableLayout.PREFERRED,
						 border}}; 		// Rows
		TableLayout tl = new TableLayout(size);

		//Space between Columns and Rows
		tl.setHGap(5);
		tl.setVGap(5);
			
		JPanel panel = new JPanel(tl);
				panel.setBorder
						(BorderFactory.createTitledBorder(
								BorderFactory.createEtchedBorder(BevelBorder.LOWERED), Gui.lang.getString("x_path_search")));
		
		JLabel xpath = new JLabel("Noch nicht implementiert, kommt aber bestimmt!");
		panel.add(xpath, "1,1,1,1,CENTER,FULL");
		
		return panel;
	}
	
	/**
	 * Returns "list items in branches" panel
	 * @return
	 */
	private JPanel getBrowsePanel()
	{			
		int border = 5;
		double size[][] =
				{{border,
						TableLayout.FILL, 
							border}, // Columns
				 {border, 
				 	TableLayout.FILL,
						TableLayout.PREFERRED,
				 	
				 		 border}}; 		// Rows
		TableLayout tl = new TableLayout(size);

		//Space between Columns and Rows
		tl.setHGap(5);
		tl.setVGap(5);
			
		JPanel panel = new JPanel(tl);
				panel.setBorder
						(BorderFactory.createTitledBorder(
								BorderFactory.createEtchedBorder(BevelBorder.LOWERED), Gui.lang.getString("browse_branches")));
	
		JScrollPane scrollPane = new JScrollPane(branches);
		//scrollPane.setPreferredSize(new Dimension(200, 150));
		panel.add(scrollPane, "1,1,1,1,FULL,FULL");
		panel.add(selectAll,"1,2,1,2,LEFT,CENTER");
		
		return panel;
	}

	/**
	 * 
	 * @param index index specifies a unique 'position' of  the form in form holder-array
	 * @param location position specifies the location on desktop
	 */
	private void doSearch(int index, Point location)
	{
		if(this.selectedIndex==0) // simple search
		{	
			if(searchString.getText().equals(""))
			{
				JOptionPane.showMessageDialog(
					Gui.modalParent,
						Gui.lang.getString("empty_string"),
							Gui.lang.getString("warning"),
								JOptionPane.CANCEL_OPTION);	
			}
			else // Searchstring ist not empty
			{	
				short[] types = getSelectedFields();
				WaitDialog.showDialog();
				Collection searchResult = Gui.dispatcher.search(types, searchString.getText());
				WaitDialog.closeDialog();
				if (searchResult.isEmpty()) 
					showNoResultMessage();
				else
				{				
					BrowseMetadataResults mdSearchResult = new BrowseMetadataResults(index, searchString.getText(), searchResult, location);
				}
			}
		}
		/*
		else if(this.selectedIndex==1) // X-Path
		{//TODO
		}
		*/
		else if(this.selectedIndex==1) // Branch Browse
		{
			if(branches.getSelectedIndex()<0)
			{
				showNoSectionMessage();
			}
			else 
			{
				int [] selectedIndices = branches.getSelectedIndices();
				Collection searchResult = new HashSet();
				WaitDialog.showDialog();
				for(int i=0;i<selectedIndices.length;i++)
				{
					String branchToBrowse = ((Branch)branchList[selectedIndices[i]]).getAbbreviation();
					Collection branchResult = Gui.dispatcher.getAllLibItemsFromBranch(branchToBrowse);
					searchResult.addAll(branchResult);
				}
				WaitDialog.closeDialog();
				if (searchResult.isEmpty()) 
					showNoResultMessage();
				else
				{
					BrowseMetadataResults mdSearchResult = new BrowseMetadataResults(index, "Einträge in ausgewählten Fachbereichen", searchResult, location);		
				}
				
			}
		} // End Branch Browse					   					   

	}
	
	public void stateChanged(ChangeEvent e)
	{
		this.selectedIndex = tabbedPane.getSelectedIndex();

	}
	
	/** Invoked when an action occurs. */
	public void actionPerformed(ActionEvent e) 
	{
		String actionCmd = e.getActionCommand();

		if (actionCmd.equals("cancel")) 
		{
			if(Gui.showWarnings)
			{
				int switch_frame = getFormCloseDialog();
				if (switch_frame == JOptionPane.YES_OPTION) 
					Gui.getForm(index).dispose();
			}else
				Gui.getForm(index).dispose();
		}

		if (actionCmd.equals("submit"))
		{
			int index = Configurator.getIntProperty("searchMetadataResult",0,"gui-forms");	
			DefaultForm form = Gui.getForm(index);
			if(form!=null && !form.isClosed())
			{
				int switch_frame = getNewSearchDialog();
				if (switch_frame == JOptionPane.NO_OPTION) 
				{	
					form.pack();
					try {
						form.setSelected(true);
					} 
					   catch (java.beans.PropertyVetoException ev) {}							
				}
				else
				{
					Point location = form.getLocation();
					form.dispose();
					doSearch(index,location);
				}	
			}
			else
			{
				doSearch(index,null);	
			}
			 		
		}
		if (actionCmd.equals("select_all")) 
		{
			if(selectAll.isSelected())
			{
				branches.setSelectionInterval(0,branchList.length-1);		
			}

			else
			{
				branches.removeSelectionInterval(0,branchList.length-1);
			}			
		}
	}

	public void valueChanged(ListSelectionEvent lse)
	{
		if(branches.getSelectedIndices().length == branchList.length) 
		 selectAll.setSelected(true);
		 else 
			selectAll.setSelected(false);
	}

	public void keyPressed (KeyEvent event)
	{
		 if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
		 	actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"cancel"));
		 
		 if (event.getKeyCode() == KeyEvent.VK_ENTER)
			actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"submit"));
	}

	public void keyReleased(KeyEvent event){}	 
	public void keyTyped(KeyEvent event){}


}
