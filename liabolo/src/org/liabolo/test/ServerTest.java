package org.liabolo.test;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.liabolo.service.Dispatcher;
import org.liabolo.repository.Library;
import org.liabolo.common.*;
import org.liabolo.client.offline.Gui;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.HashSet;

/**
 * Junit-Test for all server communication.</br>
 */
public class ServerTest extends TestCase {

    private static Logger log = Logger.getLogger(ServerTest.class);

    private static Dispatcher dispatcher;
    private static Library localLib;
    private static Library globalLib;

    public ServerTest(String s) {
        super(s);
    }

    public static Test suite() {
        Configurator.config();
        dispatcher = new Dispatcher();

        localLib = dispatcher.connect(dispatcher.getConnection("local"));

        globalLib = dispatcher.connect(dispatcher.getConnection("liaboloServer"));

        Gui.lang = dispatcher.initLanguage(Locale.GERMAN);


        TestSuite suite = new TestSuite();
//        suite.addTest(new ServerTest("search"));
//        suite.addTest(new ServerTest("addItem"));
//        suite.addTest(new ServerTest("clearWorkspace"));
//        suite.addTest(new ServerTest("checkout"));
        suite.addTest(new ServerTest("updateWorkspace"));
//        suite.addTest(new ServerTest("checkin"));

        return suite;
    }

    public void search() throws Exception {
        log.info("########################################");
        log.info("bin in search");
        log.info("########################################");
        short[] types = {MetaData.DC_TITLE};
        //search on all connected libs for pattern
        Collection allResults = dispatcher.search(types, "title");
        Iterator allResultsIter = allResults.iterator();
        while (allResultsIter.hasNext()) {
            log.info("Result :" + ((LibItem) allResultsIter.next()).getMetaData().getDc_title());
        }
    }

    /**
     * Adds an existing item from the local db into the global db and removes after adding the item from the local db
     * @throws Exception
     */
    public void addItem() throws Exception {
        log.info("########################################");
        log.info("bin in addItem");
        log.info("########################################");

        // this is the item, which is already stored in local db
        LibItem item = new LibItem();
        MetaData meta = MetaData.createNewMetaData("inf", localLib.getDbURI());
        if (meta != null) {
            meta.setDc_title("title");
            String signature = meta.getLiabolo_signature();
            item.setMetaData(meta);

            // adds a new LibItem to the local db
            if (dispatcher.addLibItem(item, localLib, false)) {
                LibItem storedLibItem = dispatcher.getLibItem(signature);

                //now add this item to the global db and removes this from the local one
//TODO change signature on global lib
                if (dispatcher.addLibItem(storedLibItem, globalLib, false))
                    assertTrue(dispatcher.removeLibItem(storedLibItem));
            }
        }
    }

    public void checkout() throws Exception {
        log.info("########################################");
        log.info("bin in checkout");
        log.info("########################################");

        // checks out a set of items specified by their signature in workspace
        Branch existingBranch = new Branch("inf", "Beschreibung");
        Collection branches = new HashSet();
        branches.add(existingBranch);

        dispatcher.checkoutByBranch(globalLib, branches);
        assertTrue(true);
    }

    public void checkin() throws Exception {
        log.info("########################################");
        log.info("bin in checkin");
        log.info("########################################");

        Collection changedLibItems = dispatcher.getLibItemsEditedFromWorkspace();
        if (changedLibItems != null) {
            Iterator resultIter = changedLibItems.iterator();
            while (resultIter.hasNext())
                System.out.println(((LibItem) resultIter.next()).getMetaData().getLiabolo_signature());
        }


        Collection notCommmitLibItems = dispatcher.commitChangesForEditedLibItems(changedLibItems);
        Iterator notCommitedLibItemsIterator = notCommmitLibItems.iterator();
        while (notCommitedLibItemsIterator.hasNext())
            System.out.println("LibItem '" + ((LibItem) notCommitedLibItemsIterator.next()).getMetaData().getLiabolo_signature() + "' could not be commited !");

        assertTrue(true);
    }

    public void clearWorkspace() {
        log.info("########################################");
        log.info("bin in clearWorkspace");
        log.info("########################################");
        assertTrue(dispatcher.clearWorkspace());
    }

    public void updateWorkspace() {
        log.info("########################################");
        log.info("bin in updateWorkspace");
        log.info("########################################");
        dispatcher.updateWorkspace(true);

        assertTrue(true);
    }


}
