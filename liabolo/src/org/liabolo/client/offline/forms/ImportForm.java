/*
 * Created on 28.05.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.liabolo.client.offline.forms;

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.liabolo.client.offline.Gui;
import org.liabolo.client.offline.common.FormElement;
import org.liabolo.client.offline.common.ImportFileFilter;
import org.liabolo.common.Branch;
import org.liabolo.common.ExcelSheet;
import org.liabolo.exception.ImportFailureException;
import org.liabolo.repository.Library;

public class ImportForm extends DefaultForm implements ActionListener 
{	
	/** Instance of file chooser shown in "open import file" dialog*/
	private JFileChooser fc;
	/** Imported data object */
	private ExcelSheet excelHeader;
	/** Rows of imported excel file */
	private ArrayList excelRows;
	/** Instance of mapping table */
	private JTable table;
	/** Available media types */
	private Object [] typesList;
	/** Available media types as Combobox */
	private JComboBox mediaTypes;
	/** Available branch objects  */
	private Object [] branchList;
	/** Descriptions of branches as combo box */
	private JComboBox branches;
	/** Conflict map */
	public  Boolean [] conflicts;
	/** Selected row of imported data. Is used to browse inside the import data. */
	private int selectedExcelRow = 0;


	/**
	 * Creates  a new import form frame 
	 * @param index specifies a unique 'position' of  "add metadata" form in form holder-array
	 */
	public ImportForm(int index) 
	{	
		super(index, Gui.lang.getString("import"),"images/import.png");
		
		typesList = Gui.dispatcher.getAllMediaTypes(Gui.myLib).toArray(); 
		String [] tempList = new String[typesList.length];
		for(int i=0; i<typesList.length; i++)
		{
			tempList[i] = Gui.lang.getString((String)typesList[i]);
		}
		this.mediaTypes = FormElement.getComboBox(tempList,0,Font.PLAIN,"av_mediatypes");
		/*
		Object [] typesList = Gui.dispatcher.getAllMediaTypes(Gui.myLib).toArray();
		this.mediaTypes = new JComboBox(typesList);
		this.mediaTypes.setToolTipText(Gui.lang.getString("av_mediatypes")); 
		*/
		this.branchList = Gui.dispatcher.sortBranch(Gui.dispatcher.getAllBranches(Gui.myLib),Gui.myLib).toArray();
		Object [] branchDescription = new Object[branchList.length];
		for(int i=0;i<branchList.length;i++)
		{
			branchDescription[i] = ((Branch)branchList[i]).getDescription();
		}
		this.branches = new JComboBox(branchDescription);	
		this.initConnections();
		this.addFormFrame(null);
	}

	/** Implementation of inherited method showFormContent() */
	public  JPanel showFormContent()
	{
		JPanel root = new JPanel();
		fc = new JFileChooser();
		ImportFileFilter ff = new ImportFileFilter("xls");
		ff.accept(new File("xls"));
		ff.getDescription();
		fc.addChoosableFileFilter(ff);
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.addActionListener(this);
		root.add(fc);
		return root;
	}
 
	 /**
	  * Clears the frame and puts the  mapping form inside it
	  * @param import_title title to be shown inside the panel titled border
	  */
	public void printMappingPanel(String import_title) 
	{		
		JPanel excelPanel = getExcelMappingPanel(import_title);

		Gui.my_forms[index].getContentPane().removeAll();
		Gui.my_forms[index].getContentPane().add(excelPanel);
		Gui.my_forms[index].pack();
		
		try 
		{
			Gui.my_forms[index].setSelected(true);
		}
		catch (java.beans.PropertyVetoException e) 
		{
			log.debug(e);
		}
    
	}

/**
 * Returns layouted  mapping form
 * @param import_title title to be shown inside the panel titled border
 * @return 
 */
	private JPanel getExcelMappingPanel(String import_title)
	{
		JPanel root = getRootPanel(); //inherited
		int  border = 2;
		 // Creating the Grid for TableLayout
		 double size[][] =
				 {{border,
				 	TableLayout.PREFERRED,
				 		TableLayout.PREFERRED,
				 			TableLayout.PREFERRED, 
				 				border}, // Columns
				  {border, 
					TableLayout.PREFERRED, 
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
						 BorderFactory.createEtchedBorder(BevelBorder.LOWERED), import_title));

		 JLabel message = FormElement.getSystemFormLabel("add_tip", null);
		 panel.add(message, "1,1,3,1,CENTER,CENTER");
		 
		table = getExcelMappingTable();
		
		//Updates the conflict indication, not really nice but it works
		table.setValueAt(new Boolean(true),0,1);
		
		JScrollPane sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(250, 200));
		panel.add(sp, "1,2,3,2,FULL,FULL");
		
		//Prev-Button
		JButton prev_button = FormElement.getInfiniteButton("<<", null, "previous_item", null, true);
		prev_button.setActionCommand("prev");
		prev_button.addActionListener(this);
		panel.add(prev_button, "1,3,1,3,RIGHT,CENTER");
			
		JLabel label = FormElement.getSystemFormLabel("browse_entries",null);
		panel.add(label, "2,3,2,3,CENTER,CENTER");
		
		//Next-Button
		JButton next_button = FormElement.getInfiniteButton(">>",null, "next_item", null, true);
		next_button.setActionCommand("next"); 
		next_button.addActionListener(this);
		panel.add(next_button, "3,3,3,3,LEFT,CENTER");	
			
		 double cb_size[][] =
				 {{TableLayout.FILL, 10,TableLayout.PREFERRED}, // Columns
				  {TableLayout.PREFERRED, 5, TableLayout.PREFERRED, 5, TableLayout.PREFERRED}}; // Rows

		JPanel cb_pane = new JPanel(new TableLayout(cb_size));
		
		JLabel cb_label = FormElement.getSystemFormLabel("saveas",null);
		cb_pane.add(cb_label, "0,0,0,0,RIGHT,CENTER");	
		
		cb_pane.add(this.mediaTypes, "2,0,2,0,FULL,CENTER");			
		
		JLabel cb_label2 = FormElement.getSystemFormLabel("branch",null);
		cb_pane.add(cb_label2, "0,2,0,2,RIGHT,CENTER");	
		
		branches.addItem(Gui.lang.getString("select_branch"));
		branches.setSelectedItem(Gui.lang.getString("select_branch"));
		cb_pane.add(branches, "2,2,2,2,FULL,CENTER");	
		
			
		JLabel av_conns = FormElement.getSystemFormLabel("savein", null);
		cb_pane.add(av_conns, "0,4,0,4,RIGHT,CENTER");	
		cb_pane.add(connectionList, "2,4,2,4,FULL,CENTER");
		
		panel.add(cb_pane, "1,4,3,4,CENTER,CENTER");	

		
		 root.add(panel, "1,1,3,1");

		 //OK-Button
		 JButton ok_button = FormElement.getButton("ok", "images/ok.png", "import_items", null, false);
		 ok_button.setActionCommand("ok");
		 ok_button.addActionListener(this);
		 root.add(ok_button, "1,2,1,2,FULL,CENTER");

		 //Cancel-Button
		 JButton cancel_button = FormElement.getButton("cancel", "images/cancel.png", "close_wnd", null, false);
		 cancel_button.setActionCommand("cancel");
		 cancel_button.addActionListener(this);
		 root.add(cancel_button, "3,2,3,2 ,FULL,CENTER");

		 return root;

	}
	
	/**
	 * Returns the mapping table for import form. The table defines a custom table model to handle conflicts
	 * @return mapping table
	 */
	private JTable getExcelMappingTable()
	{
		JTable table = new JTable(new AbstractTableModel()
		{
			String[] columnNames = Gui.lang.getStringArray("import_column_array");
			private final String[] target = Gui.lang.getStringArray("book_array");		
			private Object[][] data = getData();
			
			private Object[][] getData()
			{	
				int targetCounter = 0;
				ArrayList excelRow = excelHeader.getEntry(0);
				Object[][] data = new Object[excelRow.size()][3];
				for(int i=0;i<excelRow.size();i++)
				{
					if(i==target.length)
						targetCounter = 0;				
					data[i][0]=excelRow.get(i);
					data[i][1]=new Boolean(true);
					data[i][2]=target[targetCounter];
					//data[i][3]=new String("  ok");
					targetCounter++;
					log.debug("i:"+i+"/Counter:"+targetCounter+"/Targetlänge:"+target.length+"/ExcelColumns"+excelRow.size(),3);
				}
				return data;
			}
											

															
			public int getColumnCount() {
				return columnNames.length;
				
			}
			
			public int getRowCount() {
				return data.length;
			}

			public String getColumnName(int col) {
				return columnNames[col];
			}
			
			public Class getColumnClass(int c) {
				return getValueAt(0, c).getClass();
			}

			public Object getValueAt(int row, int col) {
				//log.debug("Col:"+col+"/Row:"+row,3);
				return data[row][col];
			}	
			
			public boolean isCellEditable(int row, int col)
			{        
				if (col ==1 || col==2) 
					return true;
				else 
					return false;
			}
			public void setValueAt(Object value, int row, int col) 
			{

				data[row][col] = value;
				fireTableCellUpdated(row, col);

				int j=0;
				int g=0;
				
				ArrayList excelRow = excelHeader.getEntry(0);
				
				for(int i=0;i<excelRow.size();i++)
				{
					for(int k=i+1;k<excelRow.size();k++)
					{
						if(data[i][2]==data[k][2] && (data[i][1].toString().equals("true") && data[k][1].toString().equals("true") ))
						{
							conflicts[i] = new Boolean(true);
							conflicts[k] = new Boolean(true);
									
							j=k;
							g=1;
	
							fireTableCellUpdated(i, 2);
							fireTableCellUpdated(k, 2);
						
						}
					
						else 
						{
							if(k!=j)	
							{								
								conflicts[k] = new Boolean(false);
								fireTableCellUpdated(k, 2);	
							}

						}
						if(g==0)
						{	
							conflicts[0] = new Boolean(false);
							fireTableCellUpdated(0, 2);	
						}
					}			
				}
			}			
		}
		);

		String[] columnNames = Gui.lang.getStringArray("import_column_array");
		table.setColumnModel(getImportTableColumnModel(columnNames));   
		//table.setDefaultRenderer(Object.class, new MyRenderer());
		JScrollPane scrollPane = new JScrollPane(table);
		initTargetColumn(table, table.getColumnModel().getColumn(2));	
	
		return table;	
	}

	/**
	 * Creates and returns the custom table column model for the mapping table(see above)
	 * @param columnNames names of columns to display in table header
	 * @return
	 */
	private TableColumnModel getImportTableColumnModel(String[] columnNames)
	{
		DefaultTableColumnModel model = new DefaultTableColumnModel();
		
		TableColumn source = new TableColumn(0,100);
		source.setHeaderValue(columnNames[0]);
		model.addColumn(source);
		TableColumn map = new TableColumn(1,20);
		map.setHeaderValue(columnNames[1]);
		model.addColumn(map);
		TableColumn target = new TableColumn(2,100);
		model.addColumn(target);
		target.setHeaderValue(columnNames[2]);

		return model;
	}		
	
	/**
	 * Initializes a custom cell editor for desired column
	 * @param table table containing the column
	 * @param targetColumn column to attach the cell renderer
	 */
	private void initTargetColumn(JTable table, TableColumn targetColumn) 
	{
		String[] target = Gui.lang.getStringArray("book_array");
		JComboBox editorChoice = new JComboBox(target);
		editorChoice.setFocusable(false);
	
		targetColumn.setCellEditor(new DefaultCellEditor(editorChoice));
		//Set up tool tips for the sport cells.
		DefaultTableCellRenderer renderer =
				//new DefaultTableCellRenderer();
				new MyRenderer();
		renderer.setToolTipText(Gui.lang.getString("import_tt2"));
		targetColumn.setCellRenderer(renderer);
	
	}
	
	/**
	 * Handles "ok" action. The method examines possible conflicts, 
	 * picks  out the maping settings and leads the data to the appropriate methods of the middle ware
	 */
	private void saveImportData()
	{
		boolean isCollision = false;
			for(int i=0; i<conflicts.length;i++)
			{
				if(conflicts[i].toString().equals("true"))
					isCollision=true;
			}
			if(branches.getSelectedItem().toString().equals(Gui.lang.getString("select_branch")))
			{
				JOptionPane.showMessageDialog(
				Gui.modalParent,
						Gui.lang.getString("branch_not_selected"),
							Gui.lang.getString("warning"),
								JOptionPane.CANCEL_OPTION);
					
			}	
			else if(isCollision)
			{
				JOptionPane.showMessageDialog(
					Gui.modalParent,
						Gui.lang.getString("is_collision"),
							Gui.lang.getString("warning"),
								JOptionPane.CANCEL_OPTION);				
			}
			else
			{					
				String[] target = Gui.lang.getStringArray("book_array");	
				ArrayList excelRow = excelHeader.getEntry(0);
				for(byte i=0; i<excelRow.size();i++)
				{
					if(table.getValueAt(i,1).toString().equals("true"))
					{
						//TODO: anstatt von target die Combos auslesn,weil auch mehr als 12 einträge seien können
						//
						if(table.getValueAt(i,2).toString().equals(target[0]))
							excelHeader.columns.titleCol = i;
						
						else if(table.getValueAt(i,2).toString().equals(target[1]))
							excelHeader.columns.creatorCol = i;
	
						else if(table.getValueAt(i,2).toString().equals(target[2]))
							excelHeader.columns.subjectCol = i;
	
						else if(table.getValueAt(i,2).toString().equals(target[3]))
							excelHeader.columns.descriptionCol = i;
		
						else if(table.getValueAt(i,2).toString().equals(target[4]))
							excelHeader.columns.publisherCol = i;
		
						else if(table.getValueAt(i,2).toString().equals(target[5]))
							excelHeader.columns.contributorsCol = i;
						
						else if(table.getValueAt(i,2).toString().equals(target[6]))
							excelHeader.columns.dateCol = i;
						
						else if(table.getValueAt(i,2).toString().equals(target[7]))
							excelHeader.columns.formatCol = i;
						
						else if(table.getValueAt(i,2).toString().equals(target[8]))
							excelHeader.columns.sourceCol = i;
						
						else if(table.getValueAt(i,2).toString().equals(target[9]))
							excelHeader.columns.languageCol = i;
					
						else if(table.getValueAt(i,2).toString().equals(target[10]))
							excelHeader.columns.relationCol = i;
						
						else if(table.getValueAt(i,2).toString().equals(target[11]))
							excelHeader.columns.coverageCol = i;
						
						else if(table.getValueAt(i,2).toString().equals(target[12]))
							excelHeader.columns.rightsCol = i;
					}
				}
				String branchToImport = ((Branch)branchList[branches.getSelectedIndex()]).getAbbreviation(); // ;)

//				  Library easyTestLib = Gui.dispatcher.getConnectedLibraryByName("liaboloServer1");
				Library actLib = Gui.dispatcher.getConnectedLibraryByName(connectionList.getSelectedItem().toString());
				String mediaType = (String)typesList[mediaTypes.getSelectedIndex()];
				ArrayList errors = excelHeader.setValues(excelHeader.columns, branchToImport, mediaType ,Gui.dispatcher,actLib);
				Gui.my_forms[index].dispose();
				if(errors.size()>0)
				{
					JPanel errorPane = new JPanel(new GridLayout(errors.size(),1));
					for(int i=0;i<errors.size();i++)
					{
						errorPane.add(new JLabel(errors.get(i).toString()));
					}

							JOptionPane.showMessageDialog(
								Gui.modalParent,
									errorPane,
										Gui.lang.getString("warning"),
											JOptionPane.CANCEL_OPTION);								
				}
				else
				{
					showConfirmMessage();
				}
				//Gui.statusBar.setReceiverMessage(excelHeader.rowList.size()+" "+Gui.lang.getString("items_added"));
				
				/*log.debug("titel:"+excelHeader.columns.titleCol,3);
				log.debug("creator:"+excelHeader.columns.creatorCol,3);
				log.debug("publischer:"+excelHeader.columns.publisherCol,3);
				log.debug("date:"+excelHeader.columns.dateCol,3);
				*/
			} //else		
	}

	/** Invoked when an action occurs.*/
	public void actionPerformed(ActionEvent e) 
	{
		String actionCmd = e.getActionCommand();
		
		if(actionCmd == JFileChooser.CANCEL_SELECTION)
			Gui.my_forms[index].dispose();
		else  
		if(actionCmd == JFileChooser.APPROVE_SELECTION)
		{
			try 
			{
				excelHeader = new ExcelSheet(fc.getSelectedFile().getPath());
				ArrayList excelRow = excelHeader.getEntry(0);
				conflicts = new Boolean[excelRow.size()];
				//TODO: titel EXT-abhängig übergeben
				printMappingPanel(Gui.lang.getString("add_excel"));
			}
			 catch (ImportFailureException i)
			{
				log.debug(i);
				JOptionPane.showMessageDialog(
					Gui.modalParent,
						Gui.lang.getString("file_error"),
							Gui.lang.getString("warning"),
								JOptionPane.CANCEL_OPTION);
			}
			
			
		}
		
		if(actionCmd == "next")
		{
			if((selectedExcelRow+1)<excelHeader.size())
			{
				this.selectedExcelRow+=1;
				ArrayList excelRow = excelHeader.getEntry(this.selectedExcelRow);	
				if(excelRow!=null)
					for(int i=0; i<excelRow.size();i++)
					{
						table.setValueAt(excelRow.get(i),i,0);
					}
			}
		}
		if(actionCmd == "prev")
		{
			if(selectedExcelRow>0)
			{
				this.selectedExcelRow-=1;
				ArrayList excelRow = excelHeader.getEntry(this.selectedExcelRow);	
				if(excelRow!=null)
					for(int i=0; i<excelRow.size();i++)
					{
						table.setValueAt(excelRow.get(i),i,0);

					}
			}
		}
		
		if(actionCmd == "ok")
		{
			saveImportData();
		}
		
		if (actionCmd.equals("cancel")) 
		{
			closeForm();
		}

	}
	
	class MyRenderer extends DefaultTableCellRenderer 
	{  
		public Component getTableCellRendererComponent(JTable table, Object value,
									boolean isSelected, boolean hasFocus,                                  
									int row, int col) 
		{    
			JLabel jl = (JLabel)super.getTableCellRendererComponent(table, value, isSelected,
							 hasFocus, row, col);    
			jl.setOpaque(true);    
			if(Boolean.TRUE.equals(conflicts[row])) jl.setBackground(new Color(200,20,20,100)); 
			else  jl.setBackground(new Color(255,255,255,0));     
		 return jl;  
		}}

}
