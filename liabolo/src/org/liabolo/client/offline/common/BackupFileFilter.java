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
 * @author Maxim Bauer
 *
 * Filter for Restore/Backup form
 */
public class BackupFileFilter extends FileFilter 
{ 
	/** File ending string*/
	private String fileEnding; 
      
	/** 
	 * Creates new Filefilter
	 * @param fileEnding filters over the ending of the file
	 * */
	public BackupFileFilter(String fileEnding) 
	{ 
		this.fileEnding = fileEnding; 
	} 
      
	/** 
	 * Examined whether the file is accepted or not
	 * @param file File to be accepted
	 * @return true if the file is accepted
	 * */
	public boolean accept(File file) 
	{ 
		String fileName = file.getName().toLowerCase(); 
		if (fileName.endsWith(fileEnding) && fileName.startsWith("__contents__") || (file.isDirectory() )) 
			return true; 
		else return false; 
	}
	
	/** 
	 * @return The description of the filter supplies 
	 * */
	public String getDescription() { 
		 if (fileEnding.equals("xml")) 
		 { 
			  return "Liabolo backup file (__contents__.xml)"; 
		 } 
		 return null; 
	} 
} 



