package org.liabolo.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.liabolo.service.Dispatcher;
import org.liabolo.repository.Library;
import org.liabolo.common.*;
import org.liabolo.exception.AuthenticationException;
import org.liabolo.exception.NoConnectionException;
import org.liabolo.exception.UnknownException;
import org.liabolo.exception.ServiceNotAvailableException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class LibItemTest extends TestCase {

    private static Logger log = Logger.getLogger(LibItemTest.class);

    private static Dispatcher dispatcher;
    private static List connectedLibs;
    private static Library localLib;
    private static LibItem newItem;

    public LibItemTest(String s) {
        super(s);
    }

    public static Test suite() {
        dispatcher = new Dispatcher();
        localLib = dispatcher.connect(dispatcher.getConnection("local"));

        TestSuite suite = new TestSuite();

        suite.addTest(new LibItemTest("testCreateNewLibItem"));  //tut
        suite.addTest(new LibItemTest("testGetAllLibItemsForBranch"));  //tut
        suite.addTest(new LibItemTest("testSearchLibItem1"));  //tut
//          suite.addTest(new LibItemTest("testSearchLibItem2"));  //tut
        suite.addTest(new LibItemTest("testEditLibItem"));  //tut
        suite.addTest(new LibItemTest("testRemoveLibItem"));  //tut
//        suite.addTest(new LibItemTest("testCreateBackup"));  //tut
//        suite.addTest(new LibItemTest("testRestoreBackup")); //tut
//          suite.addTest(new LibItemTest("testGetAllLocations")); //tut
//          suite.addTest(new LibItemTest("testCreateNewLocation")); //tut
//          suite.addTest(new LibItemTest("testRemoveLocation")); //tut

        return suite;
    }

    protected void tearDown() {
    }

    protected void setUp() throws Exception{
    }

    public void testCreateNewLibItem(){
        log.info("########################################");
        log.info("bin in testCreateNewLibItem");
        log.info("########################################");
        MetaData metaData = MetaData.createNewMetaData("inf", localLib.getDbURI());
        if (metaData != null) {
        metaData.setDc_creator("Stefan Willer");
        metaData.setDc_language("deutsch");
        metaData.setDc_description("Das ist ein Testeintrag2!");
        metaData.setLiabolo_branch("inf");
        metaData.setDc_title("Easys Testeintrag");

        newItem = new LibItem();
        newItem.setMetaData(metaData);

        assertEquals(true,dispatcher.addLibItem(newItem, localLib, false));
        }
    }

    public void testEditLibItem() throws Exception{
        log.info("########################################");
        log.info("bin in testEditLibItem");
        log.info("########################################");

        MetaData metaData = newItem.getMetaData();
        //metaData.setDc_description("Beschreibung hat sich geaendert!");
        metaData.setDc_subject("Neues Subject hinzugefügt!");
        newItem.setMetaData(metaData);
        assertEquals(true,dispatcher.editLibItem(newItem));
    }

    public void testRemoveLibItem(){
        log.info("########################################");
        log.info("bin in testRemoveLibItem");
        log.info("########################################");
        assertEquals(true,dispatcher.removeLibItem(newItem));
    }

    public void testGetAllLibItemsForBranch(){
        log.info("########################################");
        log.info("bin in testgetAllLibItemsForBranch");
        log.info("########################################");

        LibItem actLibItem = null;
        Collection allLibItems = dispatcher.getAllLibItemsFromBranch("inf");
        log.debug("LibItem count:"+allLibItems.size(),5);
        Iterator iterator = allLibItems.iterator();
        while(iterator.hasNext()){
            actLibItem = (LibItem) iterator.next();
            log.debug("LibItem :"+actLibItem.getMetaData().getDc_title(),5);
        }
        assertEquals(1,allLibItems.size());
    }

    public void testSearchLibItem1(){
        log.info("########################################");
        log.info("bin in testSearchLibItem1");
        log.info("########################################");
        short[] types = {1,2};
        LibItem actLibItem = null;

        log.debug("Searchresults from "+connectedLibs.size()+" libs:",4);
        Collection allLibItems = dispatcher.search(types,"EaS*");
        log.info("Search result count:"+allLibItems.size()+" for pattern 'Easys Testeintrag'");
        Iterator iterator = allLibItems.iterator();
        while(iterator.hasNext()){
            actLibItem = (LibItem) iterator.next();
            log.debug("LibItem:"+actLibItem.getMetaData().getDc_title()+"in branch "+actLibItem.getMetaData().getLiabolo_branch(),5);
        }

        assertEquals(2,allLibItems.size());
    }

    public void testSearchLibItem2(){
        log.info("########################################");
        log.info("bin in testSearchLibItem2");
        log.info("########################################");
        LibItem actLibItem = null;

        String xpath = "/mediatype/title[lower-case(text())='easy']/parent::*";
        //String xpath = "/mediatype/*";

        log.debug("Searchresults from "+connectedLibs.size()+" libs:",4);
        Collection allLibItems = dispatcher.search(xpath);
        log.info("Search result count:"+allLibItems.size()+" for pattern '"+xpath+"'");
        Iterator iterator = allLibItems.iterator();
        while(iterator.hasNext()){
            actLibItem = (LibItem) iterator.next();
            log.debug("LibItem:"+actLibItem.getMetaData().getDc_title()+"in branch "+actLibItem.getMetaData().getLiabolo_branch(),5);
        }

//        assertEquals(1,allLibItems.size());
    }

    public void testCreateBackup(){
        log.info("########################################");
        log.info("bin in testCreateBackup");
        log.info("########################################");
        boolean  backuped = dispatcher.backup("backup", "xmldb:exist:///db");

        assertEquals(true,backuped);
    }


    public void testRestoreBackup(){
        log.info("########################################");
        log.info("bin in testRestoreBackup");
        log.info("########################################");
        boolean  restored = dispatcher.restore("backup/db/__contents__.xml", "xmldb:exist:///db");

        assertEquals(true,restored);
    }

    public void testGetAllLocations(){
        log.info("########################################");
        log.info("bin in TestGetAllLocations");
        log.info("########################################");
        Collection allLocations = dispatcher.getAllLocations(localLib);
        Iterator iter = allLocations.iterator();
        while(iter.hasNext()){
            log.debug("Location :"+((Location)iter.next()).getName(),7);
        }
        assertEquals(true,true);
    }

    public void testCreateNewLocation(){
        log.info("########################################");
        log.info("bin in testCreateNewLocation");
        log.info("########################################");
        Location loc = new Location("newLocation2","newDescriptio2");

        boolean added = dispatcher.addNewLocation(loc);
        if(added)
            log.debug("Location added:"+loc.getName() ,7);

        assertEquals(true,added);
    }

    public void testRemoveLocation(){
        log.info("########################################");
        log.info("bin in testRemoveLocation");
        log.info("########################################");
        Location loc = new Location("newLocation2","newDescription2");

        boolean removed = dispatcher.removeLocation(loc);
        if(removed)
            log.debug("Location removed:"+loc.getName() ,7);

        assertEquals(true,removed);
    }

}
