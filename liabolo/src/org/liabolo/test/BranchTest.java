package org.liabolo.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.liabolo.service.Dispatcher;
import org.liabolo.repository.Library;
import org.liabolo.common.Authenticity;
import org.liabolo.common.Logger;
import org.liabolo.common.Branch;

import java.util.Collection;
import java.util.Iterator;

public class BranchTest extends TestCase {

    private static Logger log = Logger.getLogger(BranchTest.class);
    private static Dispatcher dispatcher;
    private static Library localLib;

    public BranchTest(String s) {
        super(s);
    }

    public static Test suite() {
        dispatcher = new Dispatcher();
        localLib = dispatcher.connect(dispatcher.getConnection("local"));

        TestSuite suite = new TestSuite();

//        suite.addTest(new BranchTest("testGetAllBranches"));
        suite.addTest(new BranchTest("testCreateNewBranch"));
//        suite.addTest(new BranchTest("testEditBranch"));
        suite.addTest(new BranchTest("testRemoveBranch"));

        return suite;
    }

    protected void tearDown() {
    }

    protected void setUp() throws Exception {
    }

    public void testGetAllBranches() throws Exception {
        log.info("########################################");
        log.info("bin in testGetAllBranches");
        log.info("########################################");
        Collection allBranches = dispatcher.getAllBranches(localLib);
        Iterator allBranchesIter = allBranches.iterator();
        while(allBranchesIter.hasNext())
            log.info("Branch  abbr:'"+((Branch)allBranchesIter.next()).getAbbreviation()+"'");
        if(allBranches.size()>0)
            assertTrue(true);
        else
            assertTrue(false);

    }

    public void testCreateNewBranch() throws Exception {
        log.info("########################################");
        log.info("bin in testCreateNewBranch");
        log.info("########################################");
        boolean added = dispatcher.addBranch(new Branch("myBranch", "my Description"), localLib);
        assertEquals(true,added);

    }

    public void testEditBranch() throws Exception {
        log.info("########################################");
        log.info("bin in testEditBranch");
        log.info("########################################");
//        dispatcher.addBranch("geo", localLib);
    }

    public void testRemoveBranch() throws Exception {
        log.info("########################################");
        log.info("bin in testRemoveBranch");
        log.info("########################################");
        boolean removed = dispatcher.removeBranch(new Branch("myBranch", "my Description"));
        assertEquals(true,removed);

    }

}
