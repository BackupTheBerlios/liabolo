/*
 * Created on 26.06.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.liabolo.client.offline.dialogs;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Collection;
import java.util.HashSet;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.forms.MetadataForm;
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
 * An implementation of a add metadata-referrence forms. See also MetadataForm .java
 */
public class AddReferrence extends MetadataForm {

	/** Instance of Add-Metadata forms, that called(creates) this form */
	private MetadataForm sender;
	/** Dialog, that shows this form */
	private JDialog refDialog;

/**
 *  Creates an new Add Referrence dialog
 * @param sender Instance of Add-Metadata forms, that called(creates) this form. Sender should not be null
 * @param branchList Array of available brahches
 * @param locationList Array of available locations
 */
	public AddReferrence(MetadataForm sender, Object[] branchList, Object[] locationList)
	{
		this.sender = sender;
		this.branchList = branchList;
		this.locationList = locationList;

		Object [] typeLabels = new String []{Gui.lang.getString("book")}; //TODO buch muss als keyword
	
		this.mediaTypes = FormElement.getComboBox(typeLabels,0, Font.PLAIN,"av_mediatypes");
		this.mediaTypes.setEnabled(false);

		this.type = "book"; //TODO erklären

		Object [] branchDescription = new Object[branchList.length];
		for(int i=0;i<branchList.length;i++)
		{
			branchDescription[i] = ((Branch)branchList[i]).getDescription();
		}
		branches = new JComboBox(branchDescription);
		branches.insertItemAt(Gui.lang.getString("select_branch"),0);
		branches.setSelectedIndex(0);
		branches.setToolTipText(Gui.lang.getString("warning1"));

		Object [] locationsNames = new Object[locationList.length];
		for(int i=0;i<locationList.length;i++)
		{
			locationsNames[i]=((Location)locationList[i]).getName();
		}
		locations = new JComboBox(locationsNames);
		locations.insertItemAt(Gui.lang.getString("select_location"),0);
		locations.setSelectedIndex(0);
		locations.addKeyListener(this);


		items = Gui.dispatcher.getMediaTypeItems(Gui.myLib, this.type);// Form elements for desired Media-type
		if(items==null)
		{
			//System.out.println("ist null");
			try
			  {
				items = TextItem.getGenericMediaTypeItems();
			  }
				catch(ServiceNotAvailableException error){
					Gui.statusBar.setReceiverMessage("No generic media type defined!("+error+")");
				}
		}
		labels = new JLabel[items.size()];

		inputFields = new JComponent[items.size()];
		//date_year = new JTextField(5);

		this.initInputFields();
		this.addFormDialog();

	}

	/** Creates and shows the dialog, that includes the "add referrence" form */
	private void addFormDialog()
	{
		refDialog = new JDialog(Gui.modalParent,true);
		refDialog.setTitle(this.title);
		refDialog.getContentPane().add(showFormContent());
		refDialog.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension labelSize = refDialog.getPreferredSize();

		refDialog.setLocation(screenSize.width/2 - (labelSize.width/2),
							screenSize.height/2 - (labelSize.height/2));

		refDialog.setVisible(true);
	}
	/** Processes save-action */
	protected void formSave()
	{
		boolean ok = checkInput();
		if(locations.getSelectedIndex()>0 && branches.getSelectedIndex()>0)
		{
			Library actLib = Gui.dispatcher.getConnectedLibraryByName(connectionList.getSelectedItem().toString());

			String branchToSave = ((Branch)branchList[branches.getSelectedIndex()-1]).getAbbreviation(); // ;)
			metaData = MetaData.createNewMetaData(branchToSave, actLib.getDbURI());
            if(metaData!=null){
			metaData.setDc_type(type);
			metaData.setLiabolo_branch(branchToSave);


			this.setMetaData();
			LibItem newItem = new LibItem();
			newItem.setMetaData(this.metaData);

			if(this.addToList.isSelected())
			{
				Collection itemsToAdd = new HashSet();
				itemsToAdd.add(newItem);
				AddToList tolist = new AddToList(myLists,itemsToAdd, this);
			}
			if((this.addToList.isSelected() && isAddedToList()) || !this.addToList.isSelected())
			{
				Gui.dispatcher.addLibItem(newItem, Gui.myLib, false);
				refDialog.dispose();
				//System.out.println(this.metaData.getUniol_signature());
				sender.setReferrence(this.metaData.getLiabolo_signature());
			}
            }
		}
	}
	
	/** Processes cancel-action */
	public void closeForm()
	{
		refDialog.dispose();
		this.setReferrence(null);
	}


}
