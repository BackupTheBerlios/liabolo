/*
 * Created on 21.04.2004 by Jurij Henne and Maxim Bauer
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

package org.liabolo.client.offline.forms;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.common.WaitDialog;
import org.liabolo.client.offline.dialogs.AddToList;
import org.liabolo.client.offline.dialogs.ReferrenceDialog;
import org.liabolo.common.Branch;
import org.liabolo.common.LibItem;
import org.liabolo.common.Location;
import org.liabolo.common.MetaData;
import org.liabolo.common.TextItem;
import org.liabolo.exception.ServiceNotAvailableException;
import org.liabolo.repository.Library;

/**
 *
 * @author Jurij Henne
 *
 * An implementation of a add metadata and edit metadata forms.
 */
public class MetadataForm extends DefaultForm implements ActionListener, KeyListener {

	/**
	 * Desired media type
	 * 
	 * @uml.property name="type"
	 */
	public String type;

    /** Available media types */
    public Object[] typesList;
    /** A combo box with names of available media types.*/
    public JComboBox mediaTypes;
    /** A list of input items for desired media type.
     * Contains all available information about a type, labeling and tooltip for every item in the list.*/
    public ArrayList items;
    /** Array of input fields, can contain text fields, text area or combo boxes. */
    public JComponent[] inputFields;
    /** Contains labels for input fields. */
    public JLabel[] labels;
    /** Used to save user input. */
    public MetaData metaData;
    /** Contains available location objects. */
    public Object[] locationList;
    /** Contains names of available locations. See also Object [] locationList.*/
    public JComboBox locations;
    /** Contains available location objects. */
    public Object[] branchList;
    /** Contains descriptions of available branch objects. See also Object [] branchList.*/
    public JComboBox branches;
    /** A collection of available individual lists. */
    public Collection myLists;

    //private Object[] myConnectedLibraries;
    //private JComboBox connections;

    /** If checked, the added metadata set will also be added to selected individual lists. */
    public JCheckBox addToList;

    public JTextField date_year;

    /** Indicates if metadata being edited */
    private boolean isEdit = false;
    /** Indicates if metadata being referred. */
    public boolean isReferred = false;

	/**
	 * 
	 * @uml.property name="referrence"
	 */
	private String referrence;

	/**
	 * Indicates if metadata was assigned to individual lists.
	 * 
	 * @uml.property name="addedToList"
	 */
	private boolean addedToList = false;


    //For edit purposes only
    /** Row of result table(see BrowseMetadataResults.java), that is being edited */
    private int editedRow;
    /** Intance of the search result form, that called edit form*/
    private BrowseMetadataResults sender;

    /**
     * Creates a new add-referrence form
     *
     */
    public MetadataForm() {
        super(Gui.lang.getString("add_referrence"), "images/spacer.gif");
        initLists();
        initConnections();

    }

    /**
     * Creates a new "add metadata" form with specified index and given referrence
     * @param index specifies a unique 'position' of  "add metadata" form in form holder-array
     * @param referrence Signature of referred meta data set
     * @param formLocation Location of the form on application desktop
     */
    public MetadataForm(int index, String referrence, Point formLocation) {

        super(index, Gui.lang.getString("add_new_md"), "images/book.png");
        this.type = "book";
        this.referrence = referrence;
        this.isReferred = true;
        //Collect available media types
        Object[] typesList = Gui.dispatcher.getAllMediaTypes(Gui.myLib).toArray();
        if (typesList.length > 0) {
            this.mediaTypes = FormElement.getComboBox(typesList, 0, Font.PLAIN, "av_mediatypes");
            this.mediaTypes.setActionCommand("change_type");
            this.mediaTypes.addActionListener(this);
            this.mediaTypes.addKeyListener(this);

            this.type = this.mediaTypes.getSelectedItem().toString();

            this.initCombos();
            this.initInputFields();
            this.addFormFrame(formLocation);
        } else {
            askToAddType("no_types_av");
        }

    }

    /**
     * Creates a new "add metadata" form with specified index
     * @param index specifies a unique 'position' of  "add metadata" form in form holder-array
     * @param formLocation Location of the form on application desktop
     */
    public MetadataForm(int index, Point formLocation) {

        super(index, Gui.lang.getString("add_new_md"), "images/book.png");
        this.type = "book"; //TODO: erkl�ren
        //Collect available media types
        //Object [] typesList = Gui.dispatcher.getAllMediaTypes(Gui.myLib).toArray();
        typesList = Gui.dispatcher.getAllMediaTypes(Gui.myLib).toArray();

        if (typesList.length > 0) {
            String[] tempList = new String[typesList.length];
            for (int i = 0; i < typesList.length; i++) {
                tempList[i] = Gui.lang.getString((String) typesList[i]);
            }
            this.mediaTypes = FormElement.getComboBox(tempList, 0, Font.PLAIN, "av_mediatypes");

            this.mediaTypes.setActionCommand("change_type");
            this.mediaTypes.addActionListener(this);
            this.mediaTypes.addKeyListener(this);

            this.type = this.typesList[mediaTypes.getSelectedIndex()].toString();

            this.initCombos();
            this.initInputFields();
            this.addFormFrame(formLocation);
        } else {
            askToAddType("no_types_av");
        }
    }

    /**
     *  Creates a new "edit metadata" form with specified index
     * @param index specifies a unique 'position' of  "add metadata" form in form holder-array
     * @param sender  Intance of the search result form, that called edit form
     * @param editedRow Row of result table(see BrowseMetadataResults.java), that is being edited
     * @param metaData Metadata object, that is being edited
     * @param formLocation Location of the form on application desktop
     */
    public MetadataForm(int index, BrowseMetadataResults sender, int editedRow, MetaData metaData, Point formLocation) {

        super(index, Gui.lang.getString("edit_meta"), "images/edit.png");
        //this.setPreferredSize(new Dimension(350,500));
        this.sender = sender;
        this.editedRow = editedRow;
        this.metaData = metaData;
        this.myLists = new HashSet();

        this.type = metaData.getDc_type();

        //System.out.println("TYPE:" + this.type);
        this.isEdit = true;
        //this.mediaTypes = null;

        this.initCombos();
        this.setInputFields();
        this.addFormFrame(null);
    }

	/**
	 * 
	 * @uml.property name="type"
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 
	 * @uml.property name="addedToList"
	 */
	public void setAddedToList(boolean value) {
		addedToList = value;
	}

	/**
	 * 
	 * @uml.property name="addedToList"
	 */
	public boolean isAddedToList() {
		return addedToList;
	}


    /**
     *  Initializes all combo boxes and input fields
     */
    public void initCombos() {
        //Generate Inputfields for selected Type
        // sollte ganz weg this.type = this.mediaTypes.getSelectedItem().toString();

        items = Gui.dispatcher.getMediaTypeItems(Gui.myLib, this.type);// Form elements for desired Media-type
        if (items == null) {
            try {
                items = TextItem.getGenericMediaTypeItems();
            } catch (ServiceNotAvailableException error) {
                Gui.statusBar.setReceiverMessage("No generic media type defined!(" + error + ")");
            }
        }

        //Collect available locations
        if (locations == null) {
            this.locationList = Gui.dispatcher.sortLocation(Gui.dispatcher.getAllLocations(Gui.myLib), Gui.myLib).toArray();
            Object[] locationsNames = new Object[locationList.length];
            for (int i = 0; i < locationList.length; i++) {
                locationsNames[i] = ((Location) locationList[i]).getName();
            }
            locations = new JComboBox(locationsNames);
            locations.insertItemAt(Gui.lang.getString("select_location"), 0);
            locations.setSelectedIndex(0);
            locations.addKeyListener(this);
        }


        if (branches == null && !this.isEdit) {
            //Collect available branches
            this.branchList = Gui.dispatcher.sortBranch(Gui.dispatcher.getAllBranches(Gui.myLib), Gui.myLib).toArray();
            Object[] branchDescription = new Object[branchList.length];
            for (int i = 0; i < branchList.length; i++) {
                branchDescription[i] = ((Branch) branchList[i]).getDescription();
            }
            branches = new JComboBox(branchDescription);
            branches.insertItemAt(Gui.lang.getString("select_branch"), 0);
            branches.setSelectedIndex(0);
            branches.addKeyListener(this);
            branches.setToolTipText(Gui.lang.getString("warning1"));
        }

        initLists();
        initConnections();


        labels = new JLabel[items.size()];
        inputFields = new JComponent[items.size()];

    }

    /** Inits check box for individual list assign purposes */
    public void initLists() {
        //Check for available individual lists and init the checkbox
        myLists = Gui.dispatcher.getAllIndividualLists();
        if (myLists.size() > 0)
            this.addToList = new JCheckBox(Gui.lang.getString("list_assign"));
        else {
            this.addToList = new JCheckBox(Gui.lang.getString("list_assign"));
            this.addToList.setEnabled(false);
        }
        this.addToList.setToolTipText(Gui.lang.getString("check_for_add_to_list"));

    }

    /**
     *  Setup  types of input fields for add metadata form.  For Example: input field for athor should be text field.
     *  The necessary information is provided by "ArrayList items". See also Field Summary.
     */
    protected void initInputFields() {
        date_year = new JTextField(5);

        for (int i = 0; i < items.size(); i++) {
            labels[i] = FormElement.getInfiniteExtLabel(((TextItem) items.get(i)).getLocalizedName(), Font.PLAIN, null);
            switch (((TextItem) items.get(i)).getType()) {
                case 0:
                    if (((TextItem) items.get(i)).getDCid() == TextItem.DC_RELATION && this.isReferred) {
                        JTextField field = new JTextField(this.referrence, 40);
                        field.setToolTipText(((TextItem) items.get(i)).getLocalizedDescription());
                        field.addKeyListener(this);
                        inputFields[i] = field;
                    }
/*if(((TextItem)items.get(i)).getDCid()==TextItem.DC_DATE)
                            {
                                JTextField field = new JTextField("", 5);
                                field.setToolTipText(((TextItem)items.get(i)).getDescription());
                                field.addKeyListener(this);
                                inputFields[i] =  field;
                            }
                            else
                            */
                    else if (((TextItem) items.get(i)).getDCid() == 7 && inputFields[i] != null)//&& !inputFields[i].equals("")
                    {

//Do nothing, do not reset language
                    } else {
                        JTextField field = new JTextField("", 40);
                        field.setToolTipText(((TextItem) items.get(i)).getLocalizedDescription());
                        field.addKeyListener(this);
                        inputFields[i] = field;
                    }

                    break;
                case 1:
                    JTextArea area = new JTextArea(30, 3);
                    area.setToolTipText(((TextItem) items.get(i)).getLocalizedDescription());
                    area.setFocusable(true);
                    //area.addKeyListener(this);
                    inputFields[i] = area;
                    break;

                case 2:
                    inputFields[i] = new JLabel("TODO");
                    locations.setToolTipText(((TextItem) items.get(i)).getLocalizedDescription());
                    //inputFields[i] =  locations;
                    break;
                case 3:
                    inputFields[i] = new JLabel("TODO");
                    //inputFields[i] =  locations;
                    break;

            }
        }

    }

    //Only for Edit
    /**
     *  Setup  types of input fields for edit metadata form and init them with data from edited metadata object
     *  See also method initInputFields().
     */
    protected void setInputFields() {
        date_year = new JTextField(5);
        for (int i = 0; i < items.size(); i++) {
            labels[i] = FormElement.getInfiniteExtLabel(((TextItem) items.get(i)).getLocalizedName(), Font.PLAIN, null);
            switch (((TextItem) items.get(i)).getDCid()) {
                case 0:
                    inputFields[i] = new JTextField(metaData.getDc_creator(), 40);
                    break;
                case 1:
                    inputFields[i] = new JTextField(metaData.getDc_title(), 40);
                    break;
                case 2:
                    inputFields[i] = new JTextField(metaData.getDc_publisher(), 40);
                    break;
                    //case 3: inputFields[i] = new JTextField(MetaData.convertDate(metaData.getDc_date()),5);break;
                case 3:
                    date_year.setText(MetaData.convertDate(metaData.getDc_date()));
                    break;
                case 4:
                    inputFields[i] = new JTextField(metaData.getDc_relation(), 40);
                    break;
                case 5:
                    inputFields[i] = new JTextField(metaData.getDc_source(), 40);
                    break;
                case 6:
                    locations.setSelectedItem(metaData.getDc_coverage());
                case 7:
                    inputFields[i] = new JTextField(metaData.getDc_language(), 40);
                    break;
                case 8:
                    inputFields[i] = new JTextArea(metaData.getDc_description(), 30, 3);
                    break;
                case 9:
                    inputFields[i] = new JTextField(metaData.getDc_subject(), 40);
                    break;
                case 10:
                    inputFields[i] = new JTextField(metaData.getDc_identifier(), 40);
                    break;
                case 11: /*metaData.setDc_type(act_field);*/
                    break;
                case 12:
                    inputFields[i] = new JTextField(metaData.getDc_format(), 40);
                    break;
                case 13:
                    inputFields[i] = new JTextField(metaData.getDc_contributors(), 40);
                    break;
                case 14:
                    inputFields[i] = new JTextField(metaData.getDc_rights(), 40);
                    break;
            }
        }

    }

    /**
     *  Returns the layouted form
     */
    public JPanel showFormContent() {
        JPanel root = getMDRootPanel(); //inherited
        int border = 5;
        double row_h = TableLayout.PREFERRED;
// Creating the Grid for TableLayout
        double panel_size[][] =
                {{border,
                  TableLayout.PREFERRED,
                  TableLayout.PREFERRED,
                  border}, // Columns
                 {border,
                  TableLayout.PREFERRED,
                  TableLayout.PREFERRED, // DBs
                  TableLayout.PREFERRED, // Checkbox
                  border}}; 		// Rows
        TableLayout tl = new TableLayout(panel_size);
        for (int i = 0; i < items.size(); i++)
            tl.insertRow(2, row_h);

//Space between Columns and Rows
        tl.setHGap(5);
        tl.setVGap(5);

        JPanel panel = new JPanel(tl);

        if (!this.isEdit) {
            panel.setBorder
                    (BorderFactory.createTitledBorder(
                            BorderFactory.createEtchedBorder(BevelBorder.LOWERED), Gui.lang.getString("add_new_mdset")));

            root.add(FormElement.getExtLabel("av_mediatypes", Font.PLAIN, null), "1,1,1,1 ,RIGHT,CENTER");
            root.add(mediaTypes, "3,1,3,1 ,LEFT,CENTER");
        } else
            panel.setBorder
                    (BorderFactory.createTitledBorder(
                            BorderFactory.createEtchedBorder(BevelBorder.LOWERED), Gui.lang.getString("edit_meta")));

        int count = 1;
        for (int i = 0; i < items.size(); i++) {
            panel.add(labels[i], "1," + count + ",1," + count + ",RIGHT,TOP");
            switch (((TextItem) items.get(i)).getType()) {
                case 0:
                    if (((TextItem) items.get(i)).getDCid() == TextItem.DC_RELATION && !this.isEdit) {
                        JPanel comboPanel = new JPanel(new BorderLayout());
                        comboPanel.add((JTextField) inputFields[i], BorderLayout.WEST);
                        JButton relation = FormElement.getButton(null, "images/add_referrence.png", "add_referrence", null, true);
                        relation.setActionCommand("setReference");
                        relation.addActionListener(this);
                        if (this.type.equals("book")) {
                            relation.setEnabled(false);
                        }
                        comboPanel.add(relation, BorderLayout.EAST);
                        /*if(this.isReferred)
                        {
                            ((JTextField)inputFields[i]).setEnabled(false);
                            relation.setEnabled(false);
                        }
                        */
                        panel.add(comboPanel, "2," + count + ",2," + count + ",FULL,CENTER");
                    } else
                        panel.add((JTextField) inputFields[i], "2," + count + ",2," + count + ",FULL,CENTER");
                    break;
                case 1:
                    ((JTextArea) inputFields[i]).setLineWrap(true);
                    JScrollPane sp = new JScrollPane((JTextArea) inputFields[i]);
                    sp.setPreferredSize(new Dimension(150, 50));
                    panel.add(sp, "2," + count + ",2," + count + ",FULL,CENTER");
                    break;
                case 2:
                    panel.add(this.locations, "2," + count + ",2," + count + ",FULL,CENTER");
                    break;
                case 3:
                    panel.add(this.date_year, "2," + count + ",2," + count + ",LEFT,CENTER");
                    break;
            }
            count++;
        }


        if (!this.isEdit) {
            JLabel branch_label = FormElement.getExtLabel("branch", Font.PLAIN, null);
            panel.add(branch_label, "1," + count + ",1," + count + ",RIGHT,CENTER");

            branches.addKeyListener(this);
            panel.add(branches, "2," + count + ",2," + count + ",FULL,CENTER");

            count++;

            JLabel av_conns = FormElement.getExtLabel("savein", Font.PLAIN, null);
            panel.add(av_conns, "1," + count + ",1," + count + ",RIGHT,CENTER");
            connectionList.addKeyListener(this);
            panel.add(connectionList, "2," + count + ",2," + count + ",FULL,CENTER");

            count++;

            //JLabel toList_label = FormElement.getExtLabel("list_assign", Font.PLAIN, null);
            //panel.add(toList_label, "1," + count + ",1," + count + ",RIGHT,CENTER");

            this.addToList.addKeyListener(this);
            panel.add(addToList, "2," + count + ",2," + count + ",FULL,CENTER");
        }

        root.add(panel, "1,2,3,2 ,CENTER,CENTER");

//Next-Button
        JButton ok_button = FormElement.getButton("ok", "images/ok.png", "save", null, true);
        ok_button.setActionCommand("ok");
        ok_button.addActionListener(this);
        ok_button.addKeyListener(this);
        root.add(ok_button, "1,3,1,3 ,FULL,CENTER");


//Cancel-Button
        JButton cancel_button = FormElement.getButton("cancel", "images/cancel.png", "close_wnd", null, true);
        cancel_button.setActionCommand("cancel");
        cancel_button.addActionListener(this);
        cancel_button.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_ESCAPE || event.getKeyCode() == KeyEvent.VK_ENTER)
                    actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "cancel"));
            }
        });
        root.add(cancel_button, "3,3,3,3 ,FULL,CENTER");

        return root;

    }

    /** Init the input fields with data of edited metadata set */
    protected void setMetaData() {
        for (int i = 0; i < items.size(); i++) {
            String act_field = "";
            switch (((TextItem) items.get(i)).getType()) {
                case 0:
                    act_field = ((JTextField) inputFields[i]).getText();
                    break;
                case 1:
                    act_field = ((JTextArea) inputFields[i]).getText();
                    break;

            }
            System.out.println("I"+i+":"+act_field);

            switch (((TextItem) items.get(i)).getDCid()) {
                case 0:
                    metaData.setDc_creator(act_field);
                    break;
                case 1:
                    metaData.setDc_title(act_field);
                    break;
                case 2:
                    metaData.setDc_publisher(act_field);
                    break;

                    case 3: String dateInput = date_year.getText();
                    		metaData.setDc_date(MetaData.convertDate(dateInput)); break;
                //case 3:
                   // metaData.setDc_date(MetaData.convertDate(act_field));
                    //break;

                case 4:
                    metaData.setDc_relation(act_field);
                    break;
                case 5:
                    metaData.setDc_source(act_field);
                    break;
                case 6:
                    metaData.setDc_coverage(locations.getSelectedItem().toString());
                    break;
                case 7:
                    metaData.setDc_language(act_field);
                    break;
                case 8:
                    metaData.setDc_description(act_field);
                    break;
                case 9:
                    metaData.setDc_subject(act_field);
                    break;
                case 10:
                    metaData.setDc_identifier(act_field);
                    break;
                case 11: /*metaData.setDc_type(act_field);*/
                    break;
                case 12:
                    metaData.setDc_format(act_field);
                    break;
                case 13:
                    metaData.setDc_contributors(act_field);
                    break;
                case 14:
                    metaData.setDc_rights(act_field);
                    break;
            }
        }

    }

    /**
     * Return false, if branch or location were not selected.
     * @return
     */
    protected boolean checkInput() {
        if (locations.getSelectedIndex() == 0)
            JOptionPane.showMessageDialog(
                    Gui.modalParent,
                    Gui.lang.getString("location_not_selected"),
                    Gui.lang.getString("warning"),
                    JOptionPane.CANCEL_OPTION);
        else if (branches.getSelectedIndex() == 0)
            JOptionPane.showMessageDialog(
                    Gui.modalParent,
                    Gui.lang.getString("branch_not_selected"),
                    Gui.lang.getString("warning"),
                    JOptionPane.CANCEL_OPTION);
        return true;
    }

    /** Saves new metadata set into database */
    protected void formSave() {
        boolean ok = checkInput();
        if (locations.getSelectedIndex() > 0 && branches.getSelectedIndex() > 0) {
            Library actLib = Gui.dispatcher.getConnectedLibraryByName(connectionList.getSelectedItem().toString());

            String branchToSave = ((Branch) branchList[branches.getSelectedIndex() - 1]).getAbbreviation(); // ;)


            //System.out.println(actLib.getDbURI());
            metaData = MetaData.createNewMetaData(branchToSave, actLib.getDbURI());
            if (metaData != null) {
                metaData.setDc_type(this.type);
                metaData.setLiabolo_branch(branchToSave);


                this.setMetaData();
                LibItem newItem = new LibItem();
                newItem.setMetaData(this.metaData);

                if (this.addToList.isSelected()) {
                    Collection itemsToAdd = new HashSet();
                    itemsToAdd.add(newItem);
                    myLists = Gui.dispatcher.getAllIndividualLists();
                    AddToList tolist = new AddToList(myLists, itemsToAdd, this);
                }

                // add to lists
                if ((this.addToList.isSelected() && isAddedToList()) || !this.addToList.isSelected()) {
                    if (Gui.dispatcher.addLibItem(newItem, actLib, false)) {
                        Point location = Gui.my_forms[index].getLocation();
                        //Gui.my_forms[index].dispose();
                        this.initInputFields();
                        this.isReferred = false;
                        this.referrence = "";
                        this.updateFormFrame(location);
                        setAddedToList(false);
                    }

                }

            }
        }

    }

    /** Saves edited metadata set  */
    private void editSave() {
        this.setMetaData();
        LibItem newItem = new LibItem();
        newItem.setMetaData(this.metaData);

//Get the right library for actual libitem
        Library editLib = null;
        if (metaData.isInWorkspace())
            editLib = Gui.myLib;
        else {
            String serverDbUri = "";
            StringTokenizer tok = new StringTokenizer(metaData.getLiabolo_signature(), "@");
            while (tok.hasMoreTokens())
                serverDbUri = tok.nextToken();
            editLib = Gui.dispatcher.getConnectedLibraryByDbURI("exist://" + serverDbUri);
        }
        if (Gui.dispatcher.editLibItem(newItem)) {
            Gui.my_forms[index].dispose();
            sender.updateEditedEntry(this.metaData, this.editedRow);
        }

    }

    /** Handles media type changes. See also JComboBox mediaTypes */
    private void changeFormType() {
        this.type = this.typesList[mediaTypes.getSelectedIndex()].toString();
        this.initCombos();
        this.initInputFields();
        Point location = Gui.my_forms[index].getLocation();
        this.updateFormFrame(location);
    }

	/**
	 * Put given relation string in the corresponding input field of the form.
	 * See also BrowseMetadataResuls.java (Add referrence)
	 * @param relation Signature string of metadata set, which should be referred
	 * 
	 * @uml.property name="referrence"
	 */
	public void setReferrence(String relation) {
		if (relation != null) {
			this.isReferred = true;
			for (int i = 0; i < items.size(); i++) {
				switch (((TextItem) items.get(i)).getDCid()) {
					case 4 :
						((JTextField) inputFields[i]).setText(relation);
						break;
				}
			}

			Point location = Gui.my_forms[index].getLocation();
			this.updateFormFrame(location);
		}
	}

    /** Invoked when an action occurs. */
    public void actionPerformed(ActionEvent e) {
        String actionCmd = e.getActionCommand();

        if (actionCmd.equals("cancel")) {
            closeForm();
        }

        if (actionCmd.equals("ok")) {
            if (!this.isEdit) {
                formSave();
                /** Update TreeForm*/
                if (Gui.automaticUpdate) {
                	WaitDialog.showDialog();
                	Location loc = (Location)locationList[locations.getSelectedIndex()];
                    TreeForm.updateTreeForm(4,(Branch) branchList[branches.getSelectedIndex() - 1],loc);
                    WaitDialog.closeDialog();
                    try 
					{
						this.setSelected(true);
					} catch (PropertyVetoException e1) 
					{
						e1.printStackTrace();
					}
                }
            } else {
                editSave();
           }
        }

        if (actionCmd.equals("setReference")) {
            ReferrenceDialog refDialog = new ReferrenceDialog(this, branchList, locationList);
        }


        if (actionCmd.equals("change_type")) {
            changeFormType();
        }


    }

    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
            actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "cancel"));
        if (event.getKeyCode() == KeyEvent.VK_ENTER)
            actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "ok"));
    }


    public void keyReleased(KeyEvent event) {
    }

    public void keyTyped(KeyEvent event) {
    }


}
