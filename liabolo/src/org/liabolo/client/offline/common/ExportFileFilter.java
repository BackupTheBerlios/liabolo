/*
 * Created on 25.05.2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.liabolo.client.offline.common;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.liabolo.common.Configurator;

/**
 * @author Jurij Henne
 * For export purposes
 * 
 */

public class ExportFileFilter extends FileFilter 
{ 
	/** File extension of export file to be shown in "save file" dialog */	 
	 private String fileEnding; 
	/** Filter description */	 
	 private String description; 
  
  	/** Creates a import file filter with desired file extension */
	 public ExportFileFilter(String fileEnding, String description) 
	 { 
	 	this.fileEnding = fileEnding;
	 	this.description = description;
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
			return this.description;
	} 
} 
