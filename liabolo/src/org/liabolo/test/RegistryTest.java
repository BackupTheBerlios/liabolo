package org.liabolo.test;

//import org.liabolo.common.Logger;
import org.liabolo.service.Dispatcher;
import org.liabolo.registry.*;
import org.liabolo.common.Logger;
import org.liabolo.common.Configurator;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;

/**
 * Junit-Test for adding, removing and edting new libary entries on the global registry via Web-Services
 */
public class RegistryTest extends TestCase {

    private static Logger logger = Logger.getLogger(RegistryTest.class);

    public RegistryTest(String s) {
        super(s);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
//        suite.addTest(new RegistryTest("testGetAllAvailableLibraries"));
        suite.addTest(new RegistryTest("testAddLibrary"));

        return suite;
    }

    public void testGetAllAvailableLibraries() throws MalformedURLException, ServiceException, RemoteException {
        RegistryService service = new RegistryServiceLocator();
        Registry registry = service.getRegistry(new URL(Configurator.getProperty("registryURL")));
        System.out.println(registry.toString());
        StringHolder holder1 = new StringHolder();
        StringHolder holder2 = new StringHolder();
        StringHolder holder3 = new StringHolder();
//        registry.getAllAvailableLibraries(holder1,holder2,holder3);
//        System.out.println(holder1.value +";"+ holder2.value +";"+ holder3.value);
/*
        Dispatcher dispatcher = new Dispatcher();
        String serverTimeinMillis = dispatcher.getServerTimeInMillis(Configurator.getProperty("timeServerURL"));
        logger.info("Server Time in millis :"+serverTimeinMillis);
        assertNotNull(serverTimeinMillis);
*/
    }

    public void testAddLibrary() throws MalformedURLException, ServiceException, RemoteException {
        RegistryService service = new RegistryServiceLocator();
        //Registry registry = service.getRegistry(new URL(Configurator.getProperty("registryURL")));
        Registry registry = service.getRegistry();
//        org.liabolo.Library result = registry.getAllLibraries2("bla");
//        System.out.println(result.getDbURI());
//        System.out.println(result.getUsername());
//        System.out.println(result.getPassword());
//        boolean success = registry.addLibrary("newDBURI");
//        if(success)
//            System.out.println("New Library to registry added!");
    }

}
