package org.liabolo.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.liabolo.service.Dispatcher;
import org.liabolo.repository.Library;
import org.liabolo.common.Authenticity;
import org.liabolo.common.User;
import org.liabolo.common.Logger;
import org.liabolo.exception.NoConnectionException;
import org.liabolo.exception.AuthenticationException;

import java.util.Collection;
import java.util.Iterator;

public class UserTest extends TestCase {

    private static Logger log = Logger.getLogger(UserTest.class);
    private static Dispatcher dispatcher;
    private static Library actLib;
    private User newUser;

    public UserTest(String s) {
        super(s);
    }

    public static Test suite() {
        dispatcher = new Dispatcher();
        actLib = dispatcher.connect(dispatcher.getConnection("local"));

        TestSuite suite = new TestSuite();

//        suite.addTest(new UserTest("testCreateNewUser"));
//        suite.addTest(new UserTest("testEditUser"));
//        suite.addTest(new UserTest("testRemoveUser"));
        suite.addTest(new UserTest("testGetAllUsers"));

        return suite;
    }

    protected void tearDown() {
//        dispatcher.shutdown(actLib);
    }

    protected void setUp() {
    }

    public void testCreateNewUser() throws Exception{
        log.info("########################################");
        log.info("bin in testCreateNewUser");
        log.info("########################################");
        newUser = new User();
        newUser.setName("NewUser");
        newUser.setPasswd("NewUserPassword");
        newUser.setGroup("p30");
        boolean added = dispatcher.addUser(newUser, actLib);
        assertEquals(true,added);

    }

    public void testEditUser() throws Exception{
        log.info("########################################");
        log.info("bin in testEditUser");
        log.info("########################################");
        newUser = new User();
        newUser.setName("NewUser");
        newUser.setPasswd("NewUserPassword");
        newUser.setGroup("Wurst");
        boolean edited = dispatcher.editUser(newUser, actLib);
        assertEquals(true,edited);

    }

    public void testRemoveUser() throws Exception{
        log.info("########################################");
        log.info("bin in testRemoveUser");
        log.info("########################################");
        newUser = new User();
        newUser.setName("NewUser");
        boolean removed = dispatcher.removeUser(newUser, actLib);
        assertEquals(true,removed);
    }

    public void testGetAllUsers() throws Exception{
        log.info("########################################");
        log.info("bin in testGetAllUsers");
        log.info("########################################");
        Collection allUsers = dispatcher.getAllUsers(actLib);
        Iterator allUsersIterator = allUsers.iterator();
        System.out.println("Count allUSers :"+allUsers.size());
        while(allUsersIterator.hasNext())
            System.out.println(((User)allUsersIterator.next()).getName());

        assertTrue(allUsers.size()>0);
    }
}
