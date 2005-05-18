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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * @author slotta
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author lexi
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class XslExport {


	private static String XSL_Document_PATH;
	

	private static DOMSource XML_DOMSource;
	private static DOMSource XSL_DOMSource;

	private static Document XML_Document;
	private static Document XSL_Document;

	private static void convertToXml(LibItem item) {
		MetaData meta;

		try {
			OutputStreamWriter fw =
				new OutputStreamWriter(
					new FileOutputStream("tempXMLFile.xml"),
					"UTF-8");
			fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			fw.write("<mediatype>");

			meta = item.getMetaData();

			fw.write("<title>" + meta.getDc_title() + "</title>");
			fw.write("<creator>" + meta.getDc_creator() + "</creator>");
			fw.write("<subject>" + meta.getDc_subject() + "</subject>");
			fw.write(
				"<description>" + meta.getDc_description() + "</description>");
			fw.write("<publisher>" + meta.getDc_publisher() + "</publisher>");
			fw.write(
				"<contributors>"
					+ meta.getDc_contributors()
					+ "</contributors>");
			fw.write("<date>" + meta.getDc_date() + "</date>");
			fw.write("<type>" + meta.getDc_type() + "</type>");
			fw.write("<format>" + meta.getDc_format() + "</format>");
			fw.write(
				"<identifier>" + meta.getDc_identifier() + "</identifier>");
			fw.write("<source>" + meta.getDc_source() + "</source>");
			fw.write("<language>" + meta.getDc_language() + "</language>");
			fw.write("<relation>" + meta.getDc_relation() + "</relation>");
			fw.write("<coverage>" + meta.getDc_coverage() + "</coverage>");
			fw.write("<rights>" + meta.getDc_rights() + "</rights>");
			fw.write("<branch>" + meta.getLiabolo_branch() + "</branch>");
			fw.write(
				"<signature>" + meta.getLiabolo_signature() + "</signature>");
			fw.write("</mediatype>");
			fw.close();

		} catch (FileNotFoundException e) {
			System.err.println(e);

		} catch (IOException e) {
			System.err.println(e);

		}

	}

	public static void export(Collection col, String filename, String format) {
		XSL_Document_PATH = Configurator.configMainDir+"/"+format+".xsl";
		
		File tempExportFile = new File("tempExportFile.txt");
		File tempXml = new File("tempXMLFile.xml");
		boolean first = true; // needed for writing before the first element
		boolean last = false; // and after the last element
		try {

			//		Get Document Builder Factory
			DocumentBuilderFactory factory =
				DocumentBuilderFactory.newInstance();

							 // Turn on validation, and turn off namespaces
			//				 factory.setValidating(true);
							 factory.setNamespaceAware(true);
			
			// Obtain a document builder object
			DocumentBuilder builder = factory.newDocumentBuilder();

			//		DOMParser parser = new DOMParser();

			Iterator it = col.iterator();

			// open destination file for writing

			FileWriter exportFile;
			PrintWriter writeExport;
			exportFile = new FileWriter(filename, true);
			writeExport = new PrintWriter(exportFile);

			LibItem item;

			// loop through items
			while (it.hasNext()) {
				item = (LibItem) it.next();
				if (!it.hasNext()) last = true;
				// Generate temp XML File for transformation
				convertToXml(item);

				// read XML file
				XML_Document = builder.parse(tempXml);
				//parser.parse(new InputSource(new FileInputStream("tempXMLFile.xml")));;

				//				Document XML_Document = parser.getDocument();
				XML_DOMSource = new DOMSource(XML_Document);

				// read XSL file

				XSL_Document = builder.parse(new File(XSL_Document_PATH));
				XSL_DOMSource = new DOMSource(XSL_Document);

				TransformerFactory tFactory = TransformerFactory.newInstance();
				Transformer transformer;
				transformer = tFactory.newTransformer(XSL_DOMSource);
				if (first) {
					transformer.setParameter("prolog", "true");
					first = false;
				}
				if (last) {
					transformer.setParameter("epilog", "true");
				}
				// Output format: csv
				// create xml file
				transformer.transform(
					XML_DOMSource,
					new StreamResult(tempExportFile));

				// add single File to export file
				// open file for reading

				FileReader file1 = new FileReader(tempExportFile);
				BufferedReader fileInput = new BufferedReader(file1);

				// Read temp file and append it to output file
				
				String text = fileInput.readLine();

								
				while ((text) != null) {
					writeExport.println(text);
					text = fileInput.readLine();
				}

			}
			// close export file
			exportFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tempExportFile.deleteOnExit(); // for unknown reason instant delete doesn't work!?
		tempXml.delete();
		
	}

}
