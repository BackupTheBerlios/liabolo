/*
 * Created on 15.01.2004
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



