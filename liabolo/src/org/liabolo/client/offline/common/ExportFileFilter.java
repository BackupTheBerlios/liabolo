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
