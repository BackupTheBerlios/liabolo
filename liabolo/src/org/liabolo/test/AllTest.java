package org.liabolo.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.liabolo.common.Configurator;
import org.liabolo.client.offline.Gui;

public class AllTest {

    public static Test suite() {
        TestSuite suite = new TestSuite();

        Configurator.config();


//        suite.addTest(UserTest.suite());
//        suite.addTest(BranchTest.suite());

//        suite.addTest(LibItemTest.suite());
//        suite.addTest(TimeServletTest.suite());
//        suite.addTest(IndividualListTest.suite());
//          suite.addTest(RegistryTest.suite());
//          suite.addTest(MediaTypeTest.suite());
        suite.addTest(ServerTest.suite());

        return suite;
    }
}
