package org.liabolo.test;

import org.liabolo.service.Dispatcher;
import org.liabolo.repository.Library;
import org.liabolo.common.*;
import org.liabolo.client.offline.Gui;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

import java.util.*;

/**
 * JUnit-Test for creating, removing and editing own mediatypes
 */
public class MediaTypeTest extends TestCase{

    private static Logger log = Logger.getLogger(MediaTypeTest.class);
    private static Dispatcher dispatcher;
    private static Library actLib;

    private static ResourceBundle resourceBundle;

    public MediaTypeTest(String s) {
        super(s);
    }

    public static Test suite() {
        dispatcher = new Dispatcher();
        actLib = dispatcher.connect(dispatcher.getConnection("local"));

        TestSuite suite = new TestSuite();

        suite.addTest(new MediaTypeTest("testAddMediaTypeTest"));
        suite.addTest(new MediaTypeTest("testRemoveMediaTypeTest"));
        suite.addTest(new MediaTypeTest("testgetMediaTypeItemsTest"));
        suite.addTest(new MediaTypeTest("testGetAllMediaTypesTest"));
        return suite;
    }

    protected void tearDown() {
    }

    protected void setUp() throws Exception {
    }

    public void testAddMediaTypeTest() throws Exception {
        log.info("########################################");
        log.info("bin in testAddMediaTypeTest");
        log.info("########################################");
		TextItem[] items = 
		{
			new TextItem(TextItem.DC_CREATOR, Gui.lang.getString("creator"), Gui.lang.getString("creator_desc"), TextItem.TEXTFIELD),
			new TextItem(TextItem.DC_TITLE,Gui.lang.getString("titel"), Gui.lang.getString("title_desc"), TextItem.TEXTFIELD),
			new TextItem(TextItem.DC_PUBLISHER,Gui.lang.getString("publisher"), Gui.lang.getString("publisher_desc"), TextItem.TEXTFIELD),
			new TextItem(TextItem.DC_DATE, Gui.lang.getString("date"), Gui.lang.getString("date_desc"), TextItem.TEXTFIELD),
			new TextItem(TextItem.DC_RELATION,Gui.lang.getString("relation"), Gui.lang.getString("relation_desc"), TextItem.TEXTFIELD),
			new TextItem(TextItem.DC_SOURCE, Gui.lang.getString("source"), Gui.lang.getString("source_desc"), TextItem.TEXTFIELD),
			new TextItem(TextItem.DC_COVERAGE,Gui.lang.getString("coverage"), Gui.lang.getString("coverage_desc"), TextItem.TEXTFIELD),
			new TextItem(TextItem.DC_LANGUAGE,Gui.lang.getString("language"), Gui.lang.getString("language_desc"), TextItem.TEXTFIELD),
			new TextItem(TextItem.DC_DESCRIPTION,Gui.lang.getString("description"), Gui.lang.getString("description_desc"), TextItem.TEXTFIELD),
			new TextItem(TextItem.DC_SUBJECT, Gui.lang.getString("subject"), Gui.lang.getString("subject_desc"), TextItem.TEXTFIELD),
			new TextItem(TextItem.DC_IDENTIFIER, Gui.lang.getString("identifier"), Gui.lang.getString("identifier_desc"), TextItem.TEXTFIELD),
			new TextItem(TextItem.DC_FORMAT, Gui.lang.getString("format"), Gui.lang.getString("format_desc"), TextItem.TEXTFIELD),                         
			new TextItem(TextItem.DC_CONTRIBUTORS, Gui.lang.getString("contributors"), Gui.lang.getString("contributors_desc"), TextItem.TEXTFIELD),
			// new TextItem(Gui.lang.getString("type"), Gui.lang.getString("type_desc"), TextItem.TEXTFIELD),
			new TextItem(TextItem.DC_RIGHTS, Gui.lang.getString("rights"), Gui.lang.getString("rights_desc"), TextItem.TEXTFIELD),
		};
        boolean added = dispatcher.addMediaType(actLib, "book", items);

        assertEquals(true,added);

    }

    public void testRemoveMediaTypeTest() throws Exception {
        log.info("########################################");
        log.info("bin in testRemoveMediaTypeTest");
        log.info("########################################");
        boolean removed = dispatcher.removeMediaType(actLib, "newM");
        assertEquals(true,removed);

    }

    public void testgetMediaTypeItemsTest() throws Exception {
        log.info("########################################");
        log.info("bin in testgetMediaTypeItemsTest");
        log.info("########################################");
        ArrayList items = dispatcher.getMediaTypeItems(actLib, "newList");
        log.debug("List size : "+items.size(),5);
        assertTrue(items.size()>0);

    }

    public void testGetAllMediaTypesTest() throws Exception {
        log.info("########################################");
        log.info("bin in testGetAllMediaTypesTest");
        log.info("########################################");
        List mediaTypes = dispatcher.getAllMediaTypes(actLib);
        log.debug("List size : "+mediaTypes.size(),5);
        for(int a=0; a<mediaTypes.size();a++)
            log.info("Mediatype :"+mediaTypes.get(a));

        assertTrue(true);

    }

}
