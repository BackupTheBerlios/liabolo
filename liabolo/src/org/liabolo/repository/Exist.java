/*
 * Created on 15.01.2004 by Easy (Stefan Willer)
 *
 * Copyright (c) Projektgruppe P30 Uni Oldenburg Germany
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; see the file COPYING.  If not, write to
 * the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 *
 */

package org.liabolo.repository;

import org.liabolo.common.User;
import org.liabolo.common.Configurator;
import org.liabolo.common.Logger;
import org.liabolo.exception.ServiceNotAvailableException;
import org.exist.xmldb.UserManagementService;
import org.exist.util.SyntaxException;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;


public class Exist extends XMLDB{

    private Logger log = Logger.getLogger(this.getClass());

    public Exist(String name, String dbURI, String driver){
        super(name, dbURI, driver);
    }

    /**
     * Adds a new user to the database user management
     * @param user
     * @return added gives information about success or failure
     * @throws org.liabolo.exception.ServiceNotAvailableException
     */
    public boolean addUser(User user) throws ServiceNotAvailableException {
        UserManagementService userMgmtService;
        org.xmldb.api.base.Collection rootCol;
        try {
            //create a new db-user
            org.exist.security.User existUser = new org.exist.security.User(user.getName(), user.getPasswd(), user.getGroup());
            // gets the CollectionMgmtService on rootCollection-collection
            rootCol = super.getDatabase().getCollection(super.getDbURI() + "/" + super.getRootCollection(), super.getUsername(), super.getPassword());
            //adds the new user to the db
            userMgmtService =
                    (UserManagementService) rootCol.getService("UserManagementService",
                            "1.0");
            userMgmtService.addUser(existUser);

            //get the homes-collection, only for main-user
            if (!user.getGroup().equals("dba") && !user.getGroup().equals("guest")) {
                org.xmldb.api.base.Collection homesCollection = super.getDatabase().getCollection(super.getDbURI() + "/" + super.getRootCollection() + "/" + Library.globalHomeDir, super.getUsername(), super.getPassword());
                // if the homes-collection does not exist, create it
                if (homesCollection == null) {
                    super.createCollection(Library.globalHomeDir, super.getUsername(), User.DEFAULT_USER, Configurator.getProperty("homePermission"));
                }
                // create the home-collection for new user
                super.createCollection(Library.globalHomeDir + "/" + user.getName(), existUser.getName(), User.DEFAULT_USER, Configurator.getProperty("homePermission"));
            }

            log.info("User '" + user.getName() + "' successfully with new home-Collection created.");
        } catch (XMLDBException e) {
            log.debug(e);
            //if an error has happened, a before created home-collection has to be removed
            try {
                org.xmldb.api.base.Collection colHome = super.getDatabase().getCollection(super.getDbURI() + "/" + super.getRootCollection() + "/" + Library.globalHomeDir, super.getUsername(), super.getPassword());
                if (colHome != null && colHome.getChildCollection(user.getName()) != null) {
                    CollectionManagementService colMgtService = (CollectionManagementService)
                            colHome.getService("CollectionManagementService", "1.0");
                    colMgtService.removeCollection(user.getName());
                }
            } catch (XMLDBException ex) {
            }
            throw new ServiceNotAvailableException(e.getMessage());
        } catch (SyntaxException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        }
        return true;
    }

    /**
     * Removes an existing user
     * @param user
     * @return removed gives information about success or failure
     * @throws org.liabolo.exception.ServiceNotAvailableException
     */
    public boolean removeUser(User user) throws ServiceNotAvailableException {
        try {
            org.exist.security.User existUser = new org.exist.security.User(user.getName(), user.getPasswd(), user.getGroup());
            org.xmldb.api.base.Collection col = super.getDatabase().getCollection(super.getDbURI() + "/" + super.getRootCollection(), super.getUsername(), super.getPassword());
            UserManagementService service =
                    (UserManagementService) col.getService("UserManagementService",
                            "1.0");
            //removes the user
            service.removeUser(existUser);
            //removes the user home-dir
            removeUserHome(user.getName());
        } catch (XMLDBException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        }
        return true;
    }

    /**
     * Removes the specified user home-dir with all included data
     * @param homeDir
     * @return
     */
    private boolean removeUserHome(String homeDir) throws XMLDBException {
        org.xmldb.api.base.Collection colBranch = super.getDatabase().getCollection(super.getDbURI() + "/" + super.getRootCollection() + "/" + Library.globalHomeDir, super.getUsername(), super.getPassword());
        CollectionManagementService mgtService = (CollectionManagementService)
                colBranch.getService("CollectionManagementService", "1.0");
        mgtService.removeCollection(homeDir);
        log.info("User Home " + homeDir + " successfully removed.");

        return true;
    }

    public boolean editUser(User user) throws ServiceNotAvailableException {
        try {
            org.exist.security.User existUser = new org.exist.security.User(user.getName(), user.getPasswd(), user.getGroup());
            org.xmldb.api.base.Collection col = super.getDatabase().getCollection(super.getDbURI() + "/" + super.getRootCollection(), super.getUsername(), super.getPassword());
            UserManagementService service =
                    (UserManagementService) col.getService("UserManagementService",
                            "1.0");
            service.updateUser(existUser);
        } catch (XMLDBException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        }
        return true;
    }

    public Collection getAllUsers() throws ServiceNotAvailableException {
        Collection allUsers = new HashSet();
        try {
            org.xmldb.api.base.Collection col = super.getDatabase().getCollection(super.getDbURI() + "/" + super.getRootCollection(), super.getUsername(), super.getPassword());
            UserManagementService service =
                    (UserManagementService) col.getService("UserManagementService",
                            "1.0");

            org.exist.security.User[] existUsers = service.getUsers();

            for (int a = 0; a < existUsers.length; a++){
                allUsers.add(new User(existUsers[a].getName(), existUsers[a].getPassword(), existUsers[a].getPrimaryGroup(), existUsers[a].getHome()));
            }
            log.debug("Found " + existUsers.length + " users", 5);
        } catch (XMLDBException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        }
        return allUsers;
    }

     /**
     * Gets all groups from the logged in user
     * @return Collection groups
     * @throws ServiceNotAvailableException
     */
    public Collection getGroups() throws ServiceNotAvailableException {
        Collection groups = new HashSet();
        org.xmldb.api.base.Collection col = null;
        try {
            col = super.getDatabase().getCollection(super.getDbURI() + "/" + super.getRootCollection(), super.getUsername(), super.getPassword());
            UserManagementService service =
                    (UserManagementService) col.getService("UserManagementService",
                            "1.0");
            org.exist.security.User user = service.getUser(super.getUsername());

            String actGroup = "";
            Iterator groupIterator = user.getGroups();
            while (groupIterator.hasNext()) {
                actGroup = groupIterator.next().toString();
                groups.add(actGroup);
            }
        } catch (XMLDBException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        }

        return groups;
    }
}
