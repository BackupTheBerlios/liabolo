/*
 * Created on 25.05.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.liabolo.client.offline.common;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author Jurij Henne
 * 
 */

public class ImportFileFilter extends FileFilter 
{ 
	/** File extension of import file to be shown in "open file" dialog */	 
	 private String fileEnding; 
  
  	/** Creates a import file filter with desired file extension */
	 public ImportFileFilter(String fileEnding) 
	 { 
		  this.fileEnding = fileEnding; 
	 } 
  
  	/** Implementation of inherited method accept() */
	public boolean accept(File file) 
	{ 
		String fileName = file.getName().toLowerCase(); 
		if (fileName.endsWith(fileEnding) || file.isDirectory()) 
		{ 
			return true; 
		} 
		else 
			return false; 
	} 
	
	/** Implementation of inherited method getDescription() */
	public String getDescription() 
	{ 
		if (fileEnding.equals("xls")) 
		{ 
			  return "Excel Sheet"; 
		
		} 
		return null; 
	} 
} 