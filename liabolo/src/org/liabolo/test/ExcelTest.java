/*
 * Created on May 13, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.liabolo.test;

import org.liabolo.service.Dispatcher;
import org.liabolo.repository.Library;
import org.liabolo.common.Authenticity;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import org.liabolo.common.ExcelSheet;
import org.liabolo.exception.ImportFailureException;

/**
 * @author lexi
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ExcelTest {

	 private static Dispatcher dispatcher;
	 private static List connectedLibs;
	 private static Library localLib;
	 private static ExcelSheet sheet;
	 private static ArrayList entries;

	public static void main(String[] args) {
        dispatcher = new Dispatcher();
        localLib = dispatcher.connect(dispatcher.getConnection("local"));


		//dispatcher.importExcel(localLib, "meineTabelle.xls");
        try {
            sheet = new ExcelSheet("meineTabelle.xls");
        } catch (ImportFailureException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }


        System.out.println("Groesse : " + sheet.size());

		ArrayList entries = sheet.getEntry(0);
		for (int i = 0; i < entries.size(); i++) {
			System.out.println(entries.get(i).toString());
		}

		System.out.println("Autorspalte : " + sheet.columns.creatorCol);
		sheet.columns.titleCol = 0;
		sheet.setValues(sheet.columns, "nav","book", dispatcher, localLib);


/*		for (int i = 0; i < entries.size(); i++) {
			System.out.print("Eintrag "+ i + ": ");
			ArrayList row = new ArrayList();
			row = (ArrayList) entries.get(i);
			for (int j =0 ; j < row.size() ; j++) {
				System.out.print(row.get(j).toString()+ ", ");

			}
			System.out.println();
		}
*/
	}
}

