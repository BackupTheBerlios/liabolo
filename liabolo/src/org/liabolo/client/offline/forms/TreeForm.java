/*
 * Created on 04.06.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

/**
 * @author Maxim Bauer
 *
 * The class implements the form library, 
 * which is started at the beginning of application, 
 * and where the overview is contained over the Branches, 
 * locations and Ind.Lists 
 */
package org.liabolo.client.offline.forms;

import info.clearthought.layout.TableLayout;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.common.WaitDialog;
import org.liabolo.common.Branch;
import org.liabolo.common.Configurator;
import org.liabolo.common.IndividualList;
import org.liabolo.common.Location;

public class TreeForm extends DefaultForm implements TreeSelectionListener,MouseListener{

	/** Instance of the object JTree - the Tree*/
	private JTree tree;
	/** Instance of the object DefaultMutableTreeNode - selected node*/
	private DefaultMutableTreeNode node;
	/** Instance of the object DefaultMutableTreeNode - top node*/
	private DefaultMutableTreeNode top;
	/** Instance of the object DefaultMutableTreeNode - branch node*/
	DefaultMutableTreeNode branch = null;
	/** Instance of the object DefaultMutableTreeNode - location node*/
	DefaultMutableTreeNode location = null;
	/** Instance of the object DefaultMutableTreeNode - ind. list node*/
	DefaultMutableTreeNode myList = null;
	/** Instance of the object DefaultMutableTreeNode - child node*/
	DefaultMutableTreeNode element = null;
	/** Description of selected node*/
	String nodeInfo;
	/** Collection of all available branches*/
	Collection allBranches;
	/** Collection of all available locations*/
	Collection allLocations;
	/** Collection of all available ind. lists*/
	Collection allLists;
	/** Instance of the object Branch - current branch and branch to be updated*/
	Branch actBranch;
	/** Instance of the object Branch - current branch and branch to be updated*/
	Branch br;
	/** Index, which determines, what is exactly to be updated */
	int upIndex;
	/** */
	Location loc;
	
	/**
     * Creates a new add-referrence form
     *@param treeFormIndex specifies a unique 'position' of  the form in form holder-array 
     *@param updateIndex specifies, which nodes must be loaded
     */
	public TreeForm(int treeFormIndex, int updateIndex) 
	{
		super(treeFormIndex, Gui.lang.getString("MyLib"),"images/tree.png");
		this.upIndex = updateIndex;
		this.addFormFrame(new Point(-1, -1));
		
	}

	/** Implemetation of inherited method. Returns the layouted  content panel of the form */
	public JPanel showFormContent() 
	{
		int border = 0;
		double root_size[][] = {{border, TableLayout.PREFERRED, border}, // Columns
				{border, TableLayout.PREFERRED, border}}; // Rows

		JPanel root = new JPanel(new TableLayout(root_size));
		top = new DefaultMutableTreeNode(Gui.lang
				.getString("MyLib"));
		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		tree.addMouseListener(this);
		JScrollPane treeView = new JScrollPane(tree);
		treeView.setPreferredSize(new Dimension(280, 550));
		TreeRenderer ren = new TreeRenderer();
		tree.setCellRenderer(ren);
		tree.setSelectionRow(1);
		this.createNodes(top, upIndex, br, loc);
		tree.expandRow(0);
		root.add(treeView, "1,1,1,1,FULL,CENTER");

		return root;

	}

	/** Creates the nodes of tree, as a function of update-index
	 * 
	 * @param top The top of tree
	 * @param upIndex update-index (0 for all, 1 for branches, 2 for locations, 
	 * 3 for ind.list, 4 for particular branch
	 * @param br branch to be updated (relevantly only for index 4)
	 * */
	private void createNodes(DefaultMutableTreeNode top, int upIndex,Branch br,Location loc) {
		
		switch(upIndex)
		{
			case 0 : 
			{
				createBranches();
				createLocations();
				createLists();
				break;
			}
			case 1 :
			{
				createBranches();
				top.add(location);
				top.add(myList);
				break;
			}
			case 2 :
			{
				top.add(branch);
				createLocations();
				top.add(myList);
				break;
			}
			case 3 :
			{
				top.add(branch);
				top.add(location);
				createLists();
				break;
			}
			case 4 :
			{
				updateBranch(br);
				updateLocation(loc);
				top.add(branch);
				top.add(location);
				top.add(myList);
			}
			
		}
	}
	
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent e)
	{
		node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		if (node == null)
			return;
		nodeInfo = node.getUserObject().toString();
	}

	/**
	 *  Show all metadata-items in selected branch
	 * */
	private void showBranchResults()
	{
		StringTokenizer token = new StringTokenizer(nodeInfo);
		String searchBranch = token.nextToken();
		while (token.countTokens() > 1) {
			searchBranch = searchBranch + " " + token.nextToken();
		}
		Iterator allBranchesIter = allBranches.iterator();
		while (allBranchesIter.hasNext()) {
			actBranch = (Branch) allBranchesIter.next();
			if (searchBranch.equals(actBranch.getDescription())) {
				searchBranch = actBranch.getAbbreviation();
			}
		}
		int SMRIndex = Configurator.getIntProperty("searchMetadataResult",0,"gui-forms");
		if (node.isLeaf()) {
			if (Gui.my_forms[SMRIndex] != null
					&& !Gui.my_forms[SMRIndex].isClosed()) {
				int switch_frame = DefaultForm.getNewSearchDialog();
				if (switch_frame == JOptionPane.NO_OPTION) {
					Gui.my_forms[SMRIndex].pack();
					try {
						Gui.my_forms[SMRIndex].setSelected(true);
					} catch (java.beans.PropertyVetoException ev) {
					}
				} else {
					Point location = Gui.my_forms[SMRIndex]
							.getLocation();
					Gui.my_forms[SMRIndex].dispose();
					WaitDialog.showDialog();
					Collection branchResult = Gui.dispatcher
							.getAllLibItemsFromBranch(searchBranch);
					WaitDialog.closeDialog();
					if (branchResult.size() > 0) {
						BrowseMetadataResults mdSearchResult = new BrowseMetadataResults(
								SMRIndex,
								Gui.lang.getString("selected_branch_entries"),
								branchResult, location);
					}
					else
						DefaultForm.showNoResultMessage();
				}
			} else {

				WaitDialog.showDialog();
				Collection branchResult = Gui.dispatcher
						.getAllLibItemsFromBranch(searchBranch);
				WaitDialog.closeDialog();
				if (branchResult.size() > 0) {
					BrowseMetadataResults mdSearchResult = new BrowseMetadataResults(
							SMRIndex,
							Gui.lang.getString("selected_branch_entries"),
							branchResult, null);
				}
				else
					DefaultForm.showNoResultMessage();
			}
		}
	}

	/**
	 * Show all metadata-items in selected location
	 * */
	private void showLocationResults()
	{
		StringTokenizer token = new StringTokenizer(nodeInfo);
		String searchLoc = token.nextToken();
		while (token.countTokens() > 1) {
			searchLoc = searchLoc + " " + token.nextToken();
		}
		int SMRIndex = Configurator.getIntProperty("searchMetadataResult",0,"gui-forms");
		if (node.isLeaf()) {
			if (Gui.my_forms[SMRIndex] != null
					&& !Gui.my_forms[SMRIndex].isClosed()) {
				int switch_frame = DefaultForm.getNewSearchDialog();
				if (switch_frame == JOptionPane.NO_OPTION) {
					Gui.my_forms[SMRIndex].pack();
					try {
						Gui.my_forms[SMRIndex].setSelected(true);
					} catch (java.beans.PropertyVetoException ev) {
					}
				} else {
					Point location = Gui.my_forms[SMRIndex]
							.getLocation();
					Gui.my_forms[SMRIndex].dispose();

					WaitDialog.showDialog();
					Collection listResult = Gui.dispatcher
							.getAllLibItemsFromLocation(new Location(
									searchLoc, ""));
					WaitDialog.closeDialog();
					if (listResult.size() > 0) {
						BrowseMetadataResults mdSearchResult = new BrowseMetadataResults(
								SMRIndex,
								Gui.lang.getString("selected_location_entries"),
								listResult, location);
					}
					else
						DefaultForm.showNoResultMessage();
				}
			} else {

				WaitDialog.showDialog();
				Collection listResult = Gui.dispatcher
						.getAllLibItemsFromLocation(new Location(
								searchLoc, ""));
				WaitDialog.closeDialog();
				if (listResult.size() > 0) {
					BrowseMetadataResults mdSearchResult = new BrowseMetadataResults(
							SMRIndex,
							Gui.lang.getString("selected_location_entries"),
							listResult, null);
				}
				else
					DefaultForm.showNoResultMessage();
			}
		}
	}

	/**
	 * Show all metadata-items in selected ind.List
	 * */
	private void showIndListResults()
	{
		StringTokenizer token = new StringTokenizer(nodeInfo);
		String searchIndList = token.nextToken();
		while (token.countTokens() > 1) {
			searchIndList = searchIndList + " " + token.nextToken();
		}
		int SMRIndex = Configurator.getIntProperty("searchMetadataResult",0,"gui-forms");
		if (node.isLeaf()) {
			if (Gui.my_forms[SMRIndex] != null
					&& !Gui.my_forms[SMRIndex].isClosed()) {
				int switch_frame = DefaultForm.getNewSearchDialog();
				if (switch_frame == JOptionPane.NO_OPTION) {
					Gui.my_forms[SMRIndex].pack();
					try {
						Gui.my_forms[SMRIndex].setSelected(true);
					} catch (java.beans.PropertyVetoException ev) {
					}
				} else {
					Point location = Gui.my_forms[SMRIndex]
							.getLocation();
					Gui.my_forms[SMRIndex].dispose();

					WaitDialog.showDialog();
					Collection listResult = Gui.dispatcher
							.getAllItemsFromIndividualList(searchIndList);
					WaitDialog.closeDialog();
					if (listResult.size() > 0) {
						BrowseMetadataResults mdSearchResult = new BrowseMetadataResults(
								SMRIndex,
								Gui.lang.getString("selected_list_entries"),
								listResult, location);
					}
					else
						DefaultForm.showNoResultMessage();
				}
			} else {

				WaitDialog.showDialog();
				Collection listResult = Gui.dispatcher.getAllItemsFromIndividualList(searchIndList);
				WaitDialog.closeDialog();
				if (listResult.size() > 0) {
					BrowseMetadataResults mdSearchResult = new BrowseMetadataResults(
							SMRIndex,
							Gui.lang.getString("selected_list_entries"),
							listResult, null);
				}
				else
					DefaultForm.showNoResultMessage();
			}
		}
	}

	/**
	 * The method to update the Tree-Form
	 * @param index shows to which node must be updated
	 * @param br shows to which branch-node must be updated
	 * */
	public static void updateTreeForm(int index,Branch br, Location loc)
	{
		/** Update TreeForm*/
		TreeForm tree = (TreeForm)Gui.my_forms[20];
		tree.upIndex = index;
		tree.br = br;
		tree.loc = loc;
    	Point location = Gui.my_forms[20].getLocation();
    	tree.updateFormFrame(location);
	}
	
	/**
	 * This class serves specific for the announcement of the node icons 
	 * */
	private class TreeRenderer extends DefaultTreeCellRenderer {
		ImageIcon branchIcon = FormElement.createImageIcon("images/branch2.png");
	    ImageIcon locationIcon =  FormElement.createImageIcon("images/location.png");
	    ImageIcon listIcon =  FormElement.createImageIcon("images/mylists.png");
	    ImageIcon libIcon =  FormElement.createImageIcon("images/home.png");

        public Component getTreeCellRendererComponent(
                            JTree tree,
                            Object value,
                            boolean sel,
                            boolean expanded,
                            boolean leaf,
                            int row,
                            boolean hasFocus) {

            super.getTreeCellRendererComponent(
                            tree, value, sel,
                            expanded, leaf, row,
                            hasFocus);

            if (value.toString().equals(Gui.lang.getString("MyLib")) && !leaf)

            {
            	setIcon(libIcon);
				setIcon(libIcon);
            }

            else if (value.toString().equals(Gui.lang.getString("branches")) && !leaf )
            {
				setIcon(branchIcon);
				setIcon(branchIcon);
			}
            else if (value.toString().equals(Gui.lang.getString("locations")) && !leaf)
            {
				setIcon(locationIcon);
				setIcon(locationIcon);
			}
            else if (value.toString().equals(Gui.lang.getString("ind_list")) && !leaf )
            {
				setIcon(listIcon);
				setIcon(listIcon);
			}
			else if (leaf) {}

            return this;
        }
       }
	
	/** 
	 * Creates the branch-nodes
	 * */
	private void createBranches()
	{
		allBranches = Gui.dispatcher.sortBranch(Gui.dispatcher.getAllBranches(Gui.myLib),Gui.myLib);
		Iterator allBranchesIter = allBranches.iterator();
		branch = new DefaultMutableTreeNode(Gui.lang.getString("branches"));
		while (allBranchesIter.hasNext()) 
		{
			actBranch = (Branch) allBranchesIter.next();
			int count = Gui.dispatcher.countAllLibItemsFromBranch(actBranch.getAbbreviation());
			element = new DefaultMutableTreeNode(actBranch.getDescription()+ "  [" + count + "]");
			branch.add(element);
		}
		top.add(branch);
	}
	
	/** 
	 * Creates the locations-nodes
	 * */
	private void createLocations()
	{
		allLocations = Gui.dispatcher.sortLocation(Gui.dispatcher.getAllLocations(Gui.myLib),Gui.myLib);
		Iterator allLocationsIter = allLocations.iterator();
		location = new DefaultMutableTreeNode(Gui.lang.getString("locations"));
		while (allLocationsIter.hasNext())
		{
			Location loc = (Location)allLocationsIter.next();
			int count = Gui.dispatcher.countAllLibItemsFromLocation(loc.getName());
			element = new DefaultMutableTreeNode(loc.getName() +"  ["+count+"]");
			location.add(element);
		}
		top.add(location);
	}
	
	/** 
	 * Creates the ind.lists-nodes
	 * */
	private void createLists()
	{
		allLists =  Gui.dispatcher.sortIndividualList(Gui.dispatcher.getAllIndividualLists(),Gui.myLib);
		Iterator allListsIter = allLists.iterator();
		myList = new DefaultMutableTreeNode(Gui.lang.getString("ind_list"));
		while(allListsIter.hasNext())
		{
			IndividualList ind =(IndividualList) allListsIter.next();
			element = new DefaultMutableTreeNode(ind.getListName()+ "  [" + ind.countItems() + "]");
			myList.add(element);
		}
		top.add(myList);
	}
	
	/** 
	 * Update the handed over branch in tree
	 * @param br Branch to be updated
	 * */
	private void updateBranch(Branch br)
	{
		int childCount = branch.getChildCount();
		for(int i=0;i<childCount;i++)
		{
			String info = branch.getChildAt(i).toString();
			StringTokenizer token = new StringTokenizer(info);
			String actChild = token.nextToken();
			while (token.countTokens() > 1) 
			{
				actChild = actChild + " " + token.nextToken();
			}
			if(actChild.equals(br.getDescription()))
			{
				branch.remove(i);
				int count = Gui.dispatcher.countAllLibItemsFromBranch(br.getAbbreviation());
				element = new DefaultMutableTreeNode(br.getDescription()+ "  [" + count + "]");
				branch.insert(element,i);
			}
		}
	}
	
	/** 
	 * Update the handed over location in tree
	 * @param loc location to be updated
	 * */
	private void updateLocation(Location loc)
	{
		int childCount = location.getChildCount();
		for(int i=0;i<childCount;i++)
		{
			String info = location.getChildAt(i).toString();
			StringTokenizer token = new StringTokenizer(info);
			String actChild = token.nextToken();
			while (token.countTokens() > 1) 
			{
				actChild = actChild + " " + token.nextToken();
			}
			if(actChild.equals(loc.getDescription()))
			{
				location.remove(i);
				int count = Gui.dispatcher.countAllLibItemsFromLocation(loc.getName());
				element = new DefaultMutableTreeNode(loc.getName()+ "  [" + count + "]");
				location.insert(element,i);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {

		//DefaultMutableTreeNode node =(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

		//System.out.println( node.getUserObject().toString()+" node Pfad" + node.isLeaf());
		//tree.getLastSelectedPathComponent();


	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		int selRow = tree.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
        if(selRow != -1) {

        	if(e.getClickCount() == 1 && selPath.getPathCount()==3)
        		if(selPath.getParentPath().getLastPathComponent().toString().equals(Gui.lang.getString("ind_list")))
        		{
        			showIndListResults();
        		}
        		else if(selPath.getParentPath().getLastPathComponent().toString().equals(Gui.lang.getString("locations")))
        		{
        			showLocationResults();
        		}
        		else
        			showBranchResults();
        }

	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}



