/*
 * Created on 15.01.2004 by Thorsten Schlörmann
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
 
package org.liabolo.common;

import java.io.*;
import java.util.*;


public class TextExport {

	public static boolean writeToFile(Collection list, String listName, String fileName) {
		LibItem item;
		MetaData meta;
		Iterator it = list.iterator();
		try {
			FileWriter fw = new FileWriter(fileName, false);
			fw.write("\n");
			fw.write("*** Individuelle Liste: " + listName +" ***\n");
			fw.write("\n\n");
		
		
			while (it.hasNext()) {
				item = (LibItem) it.next();
				meta = item.getMetaData();
				fw.write("======================================================\n");
				fw.write("Titel       : " + meta.getDc_title()+ "\n");
				fw.write("Autor       : " + meta.getDc_creator() + "\n");
				fw.write("Verlag      : " + meta.getDc_publisher()+ "\n");
				fw.write("Datum       : " + MetaData.convertDate(meta.getDc_date())+ "\n");
				fw.write("Beziehung   : " + meta.getDc_relation()+ "\n");
				fw.write("Quelle      : " + meta.getDc_source() + "\n");
				fw.write("Standort    : " + meta.getDc_coverage() + "\n");
				fw.write("Sprache     : " + meta.getDc_language() + "\n");
				fw.write("Schlagwörter: " + meta.getDc_subject() + "\n");
				fw.write("ISBN-Nummer : " + meta.getDc_identifier() + "\n");
				fw.write("Auflage     : " + meta.getDc_format() + "\n");
				fw.write("Mitwirkende : " + meta.getDc_contributors() + "\n");
				fw.write("Rechte      : " + meta.getDc_rights() + "\n");
				fw.write("Beschreibung: " + meta.getDc_description() + "\n");
				
			}
				fw.write("======================================================\n");
				fw.close();
				return true;
		} catch (FileNotFoundException e) {
				System.err.println(e);
				return false;
		} catch (IOException e) {
				System.err.println(e);
				return false;
		}
		
	}

}
