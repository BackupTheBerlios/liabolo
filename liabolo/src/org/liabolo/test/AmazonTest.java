package org.liabolo.test;

//import org.systinet.wasp.webservice.Registry;
//import org.systinet.wasp.webservice.LookupException;
import org.liabolo.common.amazon.*;
import org.liabolo.common.MetaData;
import org.liabolo.common.Logger;
import org.liabolo.common.LibItem;
import org.liabolo.common.Signature;
import org.liabolo.service.Dispatcher;
import org.liabolo.repository.Library;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

import javax.xml.parsers.ParserConfigurationException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class AmazonTest extends TestCase{

    private static String key = "YOUR KEY HERE";
    private static String wsdl = "http://soap.amazon.com/schemas3/AmazonWebServices.wsdl";
    private static String service = "http://soap.amazon.com/onca/soap3";
    private static int pageLength = 10;

    private static Logger log = Logger.getLogger(BranchTest.class);

    private static Dispatcher dispatcher;




    public AmazonTest(String s) {

        super(s);

    }



    public static Test suite() {
        dispatcher = new Dispatcher();
        TestSuite suite = new TestSuite();
        suite.addTest(new AmazonTest("testSearch"));

        return suite;

    }



    protected void tearDown() {

    }



    protected void setUp() throws Exception {

    }


    public void testSearch() throws ParserConfigurationException {
        Collection result1 = dispatcher.searchAmazon((short)0,"easy");
        Iterator resulIterator1 = result1.iterator();
        while(resulIterator1.hasNext()){
            log.info(((LibItem)resulIterator1.next()).getMetaData().getDomContent().toString());
        }

        Collection result2 = dispatcher.searchAmazon((short)1,"Steve");
        Iterator resulIterator2 = result2.iterator();
        while(resulIterator2.hasNext()){
            log.info(((LibItem)resulIterator2.next()).getMetaData().getDomContent().toString());
        }
    }
}