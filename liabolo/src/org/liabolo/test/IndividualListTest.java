package org.liabolo.test;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.liabolo.common.Logger;
import org.liabolo.common.Authenticity;
import org.liabolo.common.IndividualList;
import org.liabolo.common.LibItem;
import org.liabolo.service.Dispatcher;
import org.liabolo.repository.Library;

import java.util.Collection;
import java.util.Iterator;

public class IndividualListTest extends TestCase {

    private static Logger log = Logger.getLogger(BranchTest.class);
    private static Dispatcher dispatcher;
    private static Library actLib;

    public IndividualListTest(String s) {
        super(s);
    }

    public static Test suite() {
        dispatcher = new Dispatcher();
        actLib = dispatcher.connect(dispatcher.getConnection("local"));

        TestSuite suite = new TestSuite();
        suite.addTest(new IndividualListTest("testStoreIndividualListTest"));
        suite.addTest(new IndividualListTest("testGetIndividualListTest"));
        suite.addTest(new IndividualListTest("testGetAllItemFromIndividualListTest"));
        suite.addTest(new IndividualListTest("testGetAllIndividualListsTest"));
        suite.addTest(new IndividualListTest("testRemoveIndividualListTest"));

        return suite;
    }

    protected void tearDown() {
    }

    protected void setUp() throws Exception {
    }

    public void testStoreIndividualListTest() throws Exception {
        log.info("########################################");
        log.info("bin in testStoreIndividualList");
        log.info("########################################");

        IndividualList newList = new IndividualList("testList", "Eine Testliste");
        newList.setDescription("Das ist eine Testliste..");
        newList.addItem("234234234","Beschreibung");
        newList.addItem("234234256","Beschreibung");
        newList.addItem("23423445645",null);
        newList.addItem("2342342456","Beschreibung");
        newList.addItem("23423445634","Beschreibung");

        boolean added = dispatcher.addIndividualList(newList);
        assertEquals(true,added);

    }

    public void testRemoveIndividualListTest() throws Exception {
        log.info("########################################");
        log.info("bin in testRemoveRemoveIndividualList");
        log.info("########################################");
        boolean removed = dispatcher.removeIndividualList("testList");
        assertEquals(true,removed);

    }

    public void testGetAllItemFromIndividualListTest() throws Exception {
        log.info("########################################");
        log.info("bin in testGetAllItemFromIndividualListTest");
        log.info("########################################");
        Collection allListItems = dispatcher.getAllItemsFromIndividualList("testList");
        Iterator iter = allListItems.iterator();
        while(iter.hasNext())
            log.debug("IndividualList Item :"+((LibItem)iter.next()).getMetaData().getDc_title(),5);
        log.debug("List size : "+allListItems.size(),5);
        assertTrue(allListItems.size()>0);

    }

    public void testGetAllIndividualListsTest() throws Exception {
        log.info("########################################");
        log.info("bin in testGetAllIndividualListsTest");
        log.info("########################################");
        Collection allLists = dispatcher.getAllIndividualLists();
        Iterator iter = allLists.iterator();
        while(iter.hasNext())
            log.debug("IndividualList :"+((IndividualList)iter.next()).getListName(),5);

        assertEquals(1,allLists.size());

    }

    public void testGetIndividualListTest() throws Exception {
        log.info("########################################");
        log.info("bin in testGetIndividualListTest");
        log.info("########################################");
        IndividualList list = dispatcher.getIndividualList("testList");
        assertNotNull(list);

    }

}
