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
package org.liabolo.service;


import org.liabolo.common.*;
import org.liabolo.client.offline.Gui;

import org.liabolo.exception.*;
import org.liabolo.repository.Library;
import org.liabolo.repository.XMLDB;
import org.liabolo.repository.Exist;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;

import org.xmldb.api.base.XMLDBException;

public class Dispatcher {

    private static Logger log = Logger.getLogger(Dispatcher.class);

    // All connected libraries with their authentication
//    private List connectedLibs;
    private Hashtable connectedLibs;
    private Library local;

    public Dispatcher() {
//        connectedLibs = new ArrayList();
        connectedLibs = new Hashtable();
    }


/*####################################################################################
#
# MISC FUNCTIONS
#
####################################################################################*/
    //TODO More than 2 Libraries should are possible
    public Collection getAllAvailableLibraries() {
        Collection allLibs = new HashSet();
        local = new Exist(Configurator.getProperty("localName", "", "connections"), Configurator.getProperty("localDbURI", "", "connections"), Configurator.getProperty("localDriver", "", "connections"));
        allLibs.add(local);

        String connectionCount = Configurator.getProperty("connectionCount", "", "connections");
        if (connectionCount != null && !connectionCount.equals("")) {
            int connectionCountInt = Integer.parseInt(connectionCount);
            for (int a = 1; a <= connectionCountInt; a++) {
                log.debug("Available library : " + Configurator.getProperty("connectionDbURI" + connectionCountInt, "", "connections"), 5);
                allLibs.add(
                        new Exist(Configurator.getProperty("connectionName" + connectionCountInt, "", "connections"),
                                Configurator.getProperty("connectionDbURI" + connectionCountInt, "", "connections"),
                                Configurator.getProperty("connectionDriver" + connectionCountInt, "", "connections")));
            }
        }

        return allLibs;
    }

    public boolean addConnection(Connection newConnection) {
        String connectionCount = Configurator.getProperty("connectionCount", "", "connections");
        if (connectionCount != null && !connectionCount.equals("") && newConnection.getName() != null && !newConnection.equals("")) {
            int connectionCountInt = Integer.parseInt(connectionCount);
            connectionCountInt++;

            Configurator.setProperty("connectionName" + connectionCountInt, newConnection.getName(), "connections");
            Configurator.setProperty("connectionDriver" + connectionCountInt, newConnection.getDriver(), "connections");
            Configurator.setProperty("connectionDbURI" + connectionCountInt, newConnection.getDbURI(), "connections");
            Configurator.setProperty("connectionUsername" + connectionCountInt, newConnection.getUsername(), "connections");
            Configurator.setProperty("connectionPassword" + connectionCountInt, newConnection.getPassword(), "connections");
			Configurator.setProperty("connectionActive" + connectionCountInt, String.valueOf(newConnection.isActive()) ,"connections");

            Configurator.setProperty("connectionCount", Integer.toString(connectionCountInt), "connections");
            if (Configurator.store()) {
                log.info("Connection '" + newConnection.getName() + "' successfully added!");
                return true;
            }
        }

        log.info("Adding of Connection '" + newConnection.getName() + "' failed!");
        return false;

    }

    public boolean editConnection(Connection connection) {
        boolean edited = false;
        log.pause();
        if (removeConnection(connection.getName()))
            edited = addConnection(connection);
        log.resume();

        if (edited)
            log.info("Connection '" + connection.getName() + "' successfully edited!");
        else
            log.info("Editing of connection '" + connection.getName() + "' failed!");

        return edited;
    }

    public boolean removeConnection(String connectionName) {
        int connectionCountInt = Configurator.getIntProperty("connectionCount", 0, "connections");
        for (int a = 1; a <= connectionCountInt; a++) {
            if (connectionName.equals(Configurator.getProperty("connectionName" + a, "", "connections"))) {
                Configurator.removeProperty("connectionName" + a, "connections");
                Configurator.removeProperty("connectionDriver" + a, "connections");
                Configurator.removeProperty("connectionDbURI" + a, "connections");
                Configurator.removeProperty("connectionUsername" + a, "connections");
                Configurator.removeProperty("connectionPassword" + a, "connections");
				Configurator.removeProperty("connectionActive" + a, "connections");

                int newConCount = Configurator.getIntProperty("connectionCount", 0, "connections");
                newConCount--;
                Configurator.setProperty("connectionCount", Integer.toString(newConCount), "connections");
                if (Configurator.store()) {
                    log.info("Connection '" + connectionName + "' successfully removed!");
                    return true;
                }
            }
        }

        log.info("Removing of connection '" + connectionName + "' failed!");
        return false;
    }

    public boolean removeConnections(Collection connectionNames) {
        int connectionCountInt;
        String connectionName;
        connectionCountInt = Configurator.getIntProperty("connectionCount", 0, "connections");

        Iterator conNamesIter = connectionNames.iterator();
        while(conNamesIter.hasNext()){
            connectionName = (String)conNamesIter.next();
            for (int a = 1; a <= connectionCountInt; a++) {
                if (connectionName.equals(Configurator.getProperty("connectionName" + a, "", "connections"))) {
                    Configurator.removeProperty("connectionName" + a, "connections");
                    Configurator.removeProperty("connectionDriver" + a, "connections");
                    Configurator.removeProperty("connectionDbURI" + a, "connections");
                    Configurator.removeProperty("connectionUsername" + a, "connections");
                    Configurator.removeProperty("connectionPassword" + a, "connections");
                    Configurator.removeProperty("connectionActive" + a, "connections");

                    int newConCount = Configurator.getIntProperty("connectionCount", 0, "connections");
                    newConCount--;
                    Configurator.setProperty("connectionCount", Integer.toString(newConCount), "connections");
                    log.debug("Connection '"+connectionName+"' is prepared for removing",6);
                }
            }
        }

        if (Configurator.store()) {
            log.info("Connections successfully removed!");
            return true;
        }

        log.info("Removing of connections failed!");
        return false;
    }

    public Connection getConnection(String connectionName) {
        //local lib settings
        if (connectionName.equals(Configurator.getProperty("localName", "", "connections"))) {
            return new Connection(Configurator.getProperty("localName", "", "connections"), Configurator.getProperty("localDbURI", "", "connections"), Configurator.getProperty("localUsername", "", "connections"), Configurator.getProperty("localPassword", "", "connections"), Configurator.getProperty("localDriver", "", "connections"),Boolean.getBoolean(Configurator.getProperty("localActive", "true", "connections")));
        }
        //all other connections
        String connectionCount = Configurator.getProperty("connectionCount", "", "connections");
        if (connectionCount != null && !connectionCount.equals("")) {
            int connectionCountInt = Integer.parseInt(connectionCount);
            for (int a = 1; a <= connectionCountInt; a++) {
                if (connectionName.equals(Configurator.getProperty("connectionName" + a, "", "connections"))) {
                    return new Connection(Configurator.getProperty("connectionName" + a, "", "connections"),
                            Configurator.getProperty("connectionDbURI" + a, "", "connections"),
                            Configurator.getProperty("connectionUsername" + a, "", "connections"),
                            Configurator.getProperty("connectionPassword" + a, "", "connections"),
                            Configurator.getProperty("connectionDriver" + a, "", "connections"),
							new Boolean(Configurator.getProperty("connectionActive" + a, "true", "connections")).booleanValue());
                }
            }
        }

        log.error("Could not found desired connection " + connectionName);
        return new Connection();
    }


    public Collection getAllConnectedLibraries() {
        return connectedLibs.values();
    }

    public Collection getAllGlobalConnectedLibraries() {
//        ArrayList allGlobalLibs = new ArrayList();
        Hashtable myConnections = (Hashtable) connectedLibs.clone();
        myConnections.remove(local.getDbURI());
/*        for(int a=0;a<connectedLibs.size();a++){
            if(!((Library)connectedLibs.get(a)).getName().equals(Configurator.getProperty("localName", "", "connections")))
                allGlobalLibs.add(connectedLibs.get(a));
        }
        return allGlobalLibs;
*/      return myConnections.values();
    }

    /**
     * Returns all connection but local
     * @return Collection connections
     */
    public Collection getAllConnections() {
        Collection allConnections = new HashSet();

        String connectionCount = Configurator.getProperty("connectionCount", "", "connections");
        if (connectionCount != null && !connectionCount.equals("")) {
            int connectionCountInt = Integer.parseInt(connectionCount);
            for (int a = 1; a <= connectionCountInt; a++) {
            	//TODO System.out.println("vor:"+Configurator.getProperty("connectionActive" + a, "", "connections"));
            	// TODO System.out.println("nach:"+new Boolean(Configurator.getProperty("connectionActive" + a, "", "connections")).booleanValue());
                boolean activStatus = new Boolean(Configurator.getProperty("connectionActive" + a, "", "connections")).booleanValue();
                allConnections.add(
                        new Connection(Configurator.getProperty("connectionName" + a, "", "connections"),
                                Configurator.getProperty("connectionDbURI" + a, "", "connections"),
                                Configurator.getProperty("connectionUsername" + a, "", "connections"),
                                Configurator.getProperty("connectionPassword" + a, "", "connections"),
                                Configurator.getProperty("connectionDriver" + a, "", "connections"),
								new Boolean(Configurator.getProperty("connectionActive" + a, "true", "connections")).booleanValue()));
            }
        }
        return allConnections;
    }

    /**
     * Gets the connected library by name which refers to the given connection
     * @param connectionName desired connection
     * @return the connected library or null, if no connection to this library exists
     */
    public Library getConnectedLibraryByName(String connectionName) {
        Library lib = null;
        ;
        Collection allConnectedLibs = connectedLibs.values();
        Iterator allConnectedLibsIter = allConnectedLibs.iterator();
        while (allConnectedLibsIter.hasNext()) {
            lib = (Library) allConnectedLibsIter.next();
            if (lib.getName().equals(connectionName))
                return lib;
        }
        log.error("Not connected to '" + connectionName + "'");
        return null;
    }

    /**
     * Gets the connected library by dbUri which refers to the given connection
     * @param dbUri desired connection
     * @return the connected library or null, if no connection to this library exists
     */
    public Library getConnectedLibraryByDbURI(String dbUri) {
        Library lib = (Library) connectedLibs.get(dbUri);

        return lib;
    }

    public Library connect(Connection con) {
        Library lib = null;
        boolean connected = false;

        log.debug("Connection name:" + con.getName(), 8);
        log.debug("Connection dburi:" + con.getDbURI(), 8);
        log.debug("Connection username:" + con.getUsername(), 8);
        log.debug("Connection password:" + con.getPassword(), 8);
        log.debug("Connection driver:" + con.getDriver(), 8);


        try {
            if (con.getDriver().equals("org.exist.xmldb.DatabaseImpl")) {
                log.debug("Connection satisfies eXist-driver", 6);
                lib = new Exist(con.getName(), con.getDbURI(), con.getDriver());
                connected = lib.connect(con.getUsername(), con.getPassword());
                if (connected) {
                    connectedLibs.put(lib.getDbURI(), lib);
                    if (lib.getDbURI().equals(Configurator.getProperty("localDbURI", "", "connections")))
                        local = lib;
                } else {
                    log.debug("Connection does not satisfy eXist-driver", 6);
                    log.info("Could not connect to Library " + con.getDbURI());
                }
            }
        } catch (NoConnectionException e) {
            log.error("Connection to Library '" + con.getDbURI() + "' could not be established!");
        } catch (AuthenticationException e) {
            log.error("Authentication for library '" + con.getDbURI() + "' failed!");
        }

        return lib;

    }


    public void connect(Collection allConnections) {
        Iterator conIter = allConnections.iterator();
        while (conIter.hasNext()) {
            connect(((Connection) conIter.next()));
        }
    }

    public boolean disconnectFromAllConnectedLibraries() {
/*        for (int a = connectedLibs.size() - 1; a >= 0; a--) {
            if (!((Library) connectedLibs.get(a)).getName().equals(Configurator.getProperty("localName", "localhost", "connections"))) {
                log.info("Disconnect from :" + ((Library) connectedLibs.get(a)).getName() + "!");
                connectedLibs.remove(a);
            }
        }
*/      connectedLibs.clear();
        connectedLibs.put(local.getDbURI(), local);

        return true;
    }

    public boolean disconnect(Library library) {
        connectedLibs.remove(library.getDbURI());
        try {
            if (library.getDbURI().equals(local.getDbURI()))
                local.shutdown();
        } catch (ServiceNotAvailableException e) {
            log.error("Could not disconect from '" + library.getDbURI() + "'");
        }
        return true;
    }

    public static String getServerTimeInMillis() {
        return Long.toString(Calendar.getInstance().getTimeInMillis());
    }

/*####################################################################################
#
# Synchronising functions
#
####################################################################################*/

    /**
     * Checks out all libitms with given signature from given library</br>
     * All libitems will be stored in the local workspace
     * @param libitems
     * @param overwrite
     */

    public void checkoutUnsortedLibItems(Collection libitems, boolean overwrite) {

        Collection result = new HashSet();
        //get all items by signature
        String actSignature = "";
        Iterator libitemsIter = libitems.iterator();
        while (libitemsIter.hasNext()) {
            actSignature = ((LibItem) libitemsIter.next()).getMetaData().getLiabolo_signature().toString();

            if(!actSignature.endsWith(Configurator.getProperty("localURLAlias"))){
                LibItem getLibItemResult = getLibItem(actSignature);
                if (getLibItemResult != null) {
                    result.add(getLibItemResult);
                    log.debug("Item by signature '" + actSignature + "' found on lib '" + actSignature + "'", 5);
                } else
                    log.debug("Item by signature '" + actSignature + "' could not be found on lib '" + actSignature + "'", 5);
            }else{
                log.info("Local LibItems could not checked out!");
            }
        }

        //store all found items in workspace
        LibItem actLibItem;
        Iterator resultIter = result.iterator();
        while (resultIter.hasNext()) {
            actLibItem = (LibItem) resultIter.next();
            if (addLibItemToWorkspace(actLibItem, overwrite))
                log.debug("Item with signature '" + actSignature + "' stored in local workspace", 5);
            else
                log.debug("Item with signature '" + actSignature + "' could not stored in local workspace", 5);
        }
    }

    /**
     * Checks out all libitms with given branch from given library</br>
     * All libitems will be stored in the local workspace
     * @param lib
     * @param branches
     */
    public void checkoutByBranch(Library lib, Collection branches) {
        Collection result = new HashSet();
        Iterator branchesIter = branches.iterator();
        try {
            while (branchesIter.hasNext()) {
                Branch actBranch = (Branch) branchesIter.next();
                result = lib.getAllLibitemsFromBranch(actBranch.getAbbreviation(), false);
            }
        } catch (ServiceNotAvailableException e) {
            log.debug(e);
            log.error("Could not get all Libitems from branch on Library '" + lib.getDbURI() + "'!");
        }

        //store all found items in pum-workspace
        LibItem actLibItem;
        Iterator resultIter = result.iterator();
        while (resultIter.hasNext()) {
            actLibItem = (LibItem) resultIter.next();
            if (addLibItemToWorkspace(actLibItem, true))
                log.debug("Item with signature '" + actLibItem.getMetaData().getLiabolo_signature() + "' stored in local workspace", 5);
            else
                log.debug("Item with signature '" + actLibItem.getMetaData().getLiabolo_signature() + "' could not stored in local workspace", 5);
        }

    }

    /**
     * Search for all libitems of the workspace, which are edited
     * @return Collection of
     */
    public Collection getLibItemsEditedFromWorkspace() {
        Collection result;
        try {
            result = local.search("/mediatype[number(@actualDbTime)<number(@localDbTime)]/self::*");
            return result;
        } catch (NoConnectionException e) {
            log.debug(e);
            log.error("Could not get all edited Libitems from workspace!");
            return new HashSet();
        }
    }

    /**
     * Tries to update or add all edited libitems</br>
     * Not mergable libitems wll be returned, so the user could changes metadata entries and try it again
     * @param editedLibItems
     * @return Collections libitems which could not be merged
     */

    public Collection commitChangesForEditedLibItems(Collection editedLibItems) {
        Collection notCommitedLibItems = new HashSet();
        Iterator editedLibItemsIter = editedLibItems.iterator();
        //for every changed or added libitem, check changes now
        while (editedLibItemsIter.hasNext()) {
            LibItem actItem = (LibItem) editedLibItemsIter.next();
            MetaData actMetaData = actItem.getMetaData();

            //because in real global db it shold not stored in workspace!
            actMetaData.isInWorkspace(false);

            //get the Library-Instance of the global library
            String serverDbUri = actMetaData.getLiabolo_signature();
            StringTokenizer tok = new StringTokenizer(serverDbUri, "@");
            while (tok.hasMoreTokens())
                serverDbUri = tok.nextToken();
            Library actItemGlobalLibrary = null;
            Iterator connectedLibsIter = connectedLibs.values().iterator();
            while (connectedLibsIter.hasNext()) {
                Library myLib = (Library) connectedLibsIter.next();
                if ((myLib).getDbURI().endsWith(serverDbUri))
                    actItemGlobalLibrary = myLib;
            }
            try {
                //get the actual item from the db
                if (actItemGlobalLibrary != null) {
                    MergingData data = actItemGlobalLibrary.editLibItem(actItem, false);
                    if (data != null) {
                        notCommitedLibItems.add(data);
                    } else {//Everything OK
                        log.debug("Libitem '" + actItem.getMetaData().getLiabolo_signature() + "' successfully commited", 6);
                        log.debug("Updating LibItem '" + actItem.getMetaData().getLiabolo_signature() + "' from local workspace", 6);
                        //Sets the actualDbTime to the localDbTime
                        actItem.getMetaData().setLastKnownDbTime(actItem.getMetaData().getLocalUpdateTime());
                        //and write it back
                        log.pause();
                        addLibItemToWorkspace(actItem, true);
                        log.resume();
                    }
                } else {
                    log.info("Not connected to '" + serverDbUri + "'. LibItem '" + actMetaData.getLiabolo_signature() + "' could not be commited!");
                }
            } catch (MergingException e) {
                log.error(e.getMessage());
                notCommitedLibItems.add(new MergingData(actItem, null));
            }

        }
        return notCommitedLibItems;
    }

    public void resolveConflict_looseChanges(LibItem item) {
        HashSet set = new HashSet();
        set.add(item);

        checkoutUnsortedLibItems(set, true);
    }

    public boolean resolveConflict_storeChanges(LibItem item) {
        MetaData meta = item.getMetaData();
        meta.setLastKnownDbTime(Dispatcher.getServerTimeInMillis());
        item.setMetaData(meta);

        HashSet set = new HashSet();
        set.add(item);
        Collection result = commitChangesForEditedLibItems(set);
        //only if a MergingData-Object comes as result
        if (result.size() == 1 && result.iterator().next() != null)
            return false;
        else//if null, everything is OK
            return true;
    }

    /**
     * Search for changes on of workspace-items on the global libs</br>
     * @param overwrite indicates, whether overwrite local changes or not
     */
    public void updateWorkspace(boolean overwrite) {
        short[] types = {MetaData.LIABOLO_SIGNATURE};
        String pattern = "*";
        Library actLib;
        LibItem actItem;
        Collection updateLibItems;
        Collection allWorkSpaceItems = new HashSet();
        String fullSignature;
        String libDbUriString;
        StringTokenizer tok;

        allWorkSpaceItems = addWorkspaceSearchResults(types, pattern, allWorkSpaceItems);
        log.debug("Number of workspace item is '" + allWorkSpaceItems.size() + "' !", 5);
        Object[] allWorkSpaceItemsArray = allWorkSpaceItems.toArray();

        log.debug("Connection size is '" + connectedLibs.size() + "'", 7);

        actLib = local;

        log.debug("Check workspace entries from " + actLib.getDbURI(), 6);
        updateLibItems = new HashSet();
        for (int b = 0; b < allWorkSpaceItemsArray.length; b++) {
            actItem = (LibItem) allWorkSpaceItemsArray[b];
            fullSignature = actItem.getMetaData().getLiabolo_signature();

            //get the libDbURI from the libitem
            libDbUriString = "";
            tok = new StringTokenizer(fullSignature, "@");
            while (tok.hasMoreTokens())
                libDbUriString = tok.nextToken();
            log.debug("Tokenizer got Dburi :" + libDbUriString, 7);

            if (connectedLibs.containsKey("exist://" + libDbUriString)) {//adds the libitem to the collection for later checkout/update
                updateLibItems.add(actItem);
                log.debug("LibItem '" + actItem.getMetaData().getLiabolo_signature() + "' is prepared form updating process", 5);
            } else {
                log.debug("cutted dbUri from signature does not match library '" + actLib.getDbURI() + "'", 9);
            }
        }

        log.debug("Checking out '" + updateLibItems.size() + "' items ..", 5);
        checkoutUnsortedLibItems(updateLibItems, overwrite);

    }

    /**
     * Removes all temporally stored libitems from the local workspace, expect libitem, which are referneced by individuallists
     * @return success or failure
     */
    public boolean clearWorkspace() {
        boolean cleared = false;
        try {
            Collection allBranches = getAllBranches(local);
            log.debug("Found '" + allBranches.size() + "' Branches!", 7);
            Iterator allBranchesIter = allBranches.iterator();
            while (allBranchesIter.hasNext()) {
                cleared = local.removeAllLibItemsFromBranch(((Branch) allBranchesIter.next()), true);
            }
        } catch (ServiceNotAvailableException e) {
            log.error("Could not clear the workspace!");
        }

        if (cleared)
            log.info("Workspace succesfully cleared!");
        else
            log.info("Could not clear the workspace!");

        return cleared;
    }

    /**
     * Lists all items from local workspace
     * @return
     */
    public Collection listWorkspace() {
        Collection alllWorkSpaceItems;
        short[] types = {17};
        try {
            alllWorkSpaceItems = local.search(types, "*", true);
        } catch (ServiceNotAvailableException e) {
            log.error("Could not list workspace!");
            return new HashSet();
        }

        return alllWorkSpaceItems;
    }

/*####################################################################################
#
# DATA MANIPULATION AND DEFINITION FUNCTIONS
#
####################################################################################*/

    /***********************************************
     *
     * MediaType
     *
     ************************************************/

    /**
     * Get all Metadata-entries(labels) for the given mediatype
     * @param lib Library
     * @param mediaType user defined media type
     * @return Array of TextItems which represent the mediatype
     * @see org.liabolo.common.TextItem
     */
    public ArrayList getMediaTypeItems(Library lib, String mediaType) {
        ArrayList items;
        try {
            items = lib.getMediaTypeItems(mediaType);
            return items;
        } catch (ServiceNotAvailableException e) {
            log.error("Retrieving metadata for mediatype '" + mediaType + "' failed!");
        }

        return new ArrayList();
    }

    /**
     * Get all Metadata-entries(labels) for the given mediatype
     * @param lib Library
     * @return List all available mediatypes or an empty list
     */

    public List getAllMediaTypes(Library lib) {
        try {
            return lib.getAllMediaTypes();
        } catch (ServiceNotAvailableException e) {
            log.error("Retrieving all mediatypes on library'" + lib.getDbURI() + "' failed!");
        }

        return new ArrayList();
    }

    /**
     * Get all Metadata-entries(labels) for the given mediatype
     * @param lib Library
     * @param name the new name for the mediatype
     * @param items Array of TextItems which represent the new mediatype
     * @see org.liabolo.common.TextItem
     * @return added gives information about success or failure
     */

    public boolean addMediaType(Library lib, String name, TextItem[] items) {
        boolean added = false;
        try {
            added = lib.addMediaType(name, items);
            log.info("MediaType '" + name + "' succesfully added!");
            return added;
        } catch (ServiceNotAvailableException e) {
            log.error("Adding metadata for mediatype '" + name + "' failed!");
        }

        return added;
    }

    /**
     * Get all Metadata-entries(labels) for the given mediatype
     * @param lib Library
     * @param name Name of mediatype to be removed
     * @return removed gives information about success or failure
     */

    public boolean removeMediaType(Library lib, String name) {
        boolean removed = false;
        try {
            removed = lib.removeMediaType(name);
            log.info("MediaType '" + name + "' succesfully removed!");
            return removed;
        } catch (ServiceNotAvailableException e) {
            log.error("Removing metadata for mediatype '" + name + "' failed!");
        }

        return removed;
    }

    /***********************************************
     *
     * Search
     *
     ************************************************/

    /**
     * Search with a XPATH-Expression on the local db
     * @param xpath expression for search engine, e.g. //*[id='12312312']
     * @return Collection of LibItems
     */
    public Collection search(String xpath) {
        Collection result = new HashSet();
        boolean addAll = false;

        Library actLib = null;

        Collection actLibSearchResults;
        try {
            log.debug("Number of connected libs is " + connectedLibs.size(), 6);
            Iterator connectedLibsIter = connectedLibs.values().iterator();
            while (connectedLibsIter.hasNext()) {
                //for (int a = 0; a < connectedLibs.size(); a++) {
                actLib = (Library) connectedLibsIter.next();
                actLibSearchResults = actLib.search(xpath);
                log.debug("Search results for xpath expression '" + xpath + "'..", 5);
                addAll = result.addAll(actLibSearchResults);
                if (!addAll)
                    log.debug("Not all Search results from '" + actLib.getDbURI() + "' could added!", 1);
                else
                    log.debug(actLibSearchResults.size() + " search results from '" + actLib.getDbURI() + "' added!", 4);
            }

            log.info(result.size() + " results found for xpath-search '" + xpath + "'!");
        } catch (NoConnectionException e) {
            log.error("Error occured while searching on '" + actLib.getDbURI() + "'!");
        }

        return result;
    }

    /**
     * Search on all connected libraries for desired pattern
     * @param types Array of shorts which represent the types in which the search should be processed
     * @see org.liabolo.common.MetaData for shorts
     * @param pattern
     * @return Collection of LibItems
     */
    public Collection search(short[] types, String pattern) {
        Collection result = new HashSet();
        boolean addAll = false;

        Library actLib = null;

        Collection actLibSearchResults;
        try {
            log.debug("Number of connected libs is " + connectedLibs.size(), 6);
            Iterator connectedLibsIter = connectedLibs.values().iterator();
            while (connectedLibsIter.hasNext()) {
                actLib = (Library) connectedLibsIter.next();
                //only for local workspace
                if (actLib.getDbURI().equals(local.getDbURI())){
                    actLibSearchResults = actLib.search(types, pattern, true);
                    log.debug("Search results for pattern '" + pattern + "' from workspace on lib:" + actLib.getDbURI() + " = " + actLibSearchResults.size(), 5);
                    addAll = result.addAll(actLibSearchResults);
                    if (!addAll)
                        log.debug("Not all Search results from '" + actLib.getDbURI() + "' could added!", 1);
                    else
                        log.debug(actLibSearchResults.size() + " search results from '" + actLib.getDbURI() + "' added!", 4);
                }
                 //for all databases
                actLibSearchResults = actLib.search(types, pattern, false);
                log.debug("Search results for pattern '" + pattern + "' from lib:" + actLib.getDbURI() + " = " + actLibSearchResults.size(), 5);
                addAll = result.addAll(actLibSearchResults);
                if (!addAll)
                    log.debug("Not all Search results from '" + actLib.getDbURI() + "' could added!", 1);
                else
                    log.debug(actLibSearchResults.size() + " search results from '" + actLib.getDbURI() + "' added!", 4);
            }

            result = addWorkspaceSearchResults(types, pattern, result);

            log.info(result.size() + Gui.lang.getString("results_found") + pattern + "'!");
        } catch (ServiceNotAvailableException e) {
            log.error("Error occured while searching on '" + actLib.getDbURI() + "'!");
        }

        return result;
    }

    /**
     * Search for given pattern in local workspace and adds the results to the given collection
     * @param types
     * @param pattern
     * @param alreadySearchResult
     * @return
     */
    private Collection addWorkspaceSearchResults(short[] types, String pattern, Collection alreadySearchResult) {
        try {
            Collection allBranchItemsForLib = local.search(types, pattern, true);

            Iterator allBranchItemsForLibIter = allBranchItemsForLib.iterator();
            while (allBranchItemsForLibIter.hasNext()) {  //for all workspace results
                LibItem actWorkspaceLibItem = ((LibItem) allBranchItemsForLibIter.next());
                Object[] allLibItemsArray = alreadySearchResult.toArray();
                boolean contained = false;
                for (int a = 0; a < allLibItemsArray.length; a++) {
                    if (((LibItem) allLibItemsArray[a]).getMetaData().getLiabolo_signature().equals(actWorkspaceLibItem.getMetaData().getLiabolo_signature())) {
                        contained = true;
                        log.debug("LibItem with signature '" + actWorkspaceLibItem.getMetaData().getLiabolo_signature() + "' is in workspace, but would not be returned, because the real one is availbale!", 5);
                        break;
                    }
                }
                if (!contained)
                    alreadySearchResult.add(actWorkspaceLibItem);
            }
        } catch (ServiceNotAvailableException e) {
            log.error("Error occured while searching on local workspace!");
        }

        return alreadySearchResult;
    }

    /**
     * Search on a specific connected repository for desired pattern with a desired type
     * @param library Library
     * @param types Array of shorts which represent the types in which the search should be processed
     * @see org.liabolo.common.MetaData for shorts
     * @param pattern search pattern
     * @return Collection of LibItems
     */
    public Collection search(Library library, short[] types, String pattern) {
        Collection result = new HashSet();
        try {
            result = library.search(types, pattern, false);
            if (library.getDbURI().equals(local.getDbURI())) {
                result.addAll(library.search(types, pattern, true));
            }
            log.info(result.size() + Gui.lang.getString("results_found") + pattern + "'!");

        } catch (ServiceNotAvailableException e) {
            log.error("Connection to " + library.getDbURI() + " refused ..");
        }
        return result;
    }

    /***********************************************
     *
     * LibItem
     *
     ************************************************/

    /**
     * Add a new LibItem to the local library
     * @param item the new LibItem
     * @param lib Library
     * @return added gives information about success or failure
     */
    public boolean addLibItem(LibItem item, Library lib, boolean overwrite) {
        boolean added = false;
        try {
            if (connectedLibs.values().contains(lib)) {
                added = lib.addLibItem(item, false, overwrite);
                log.info("LibItem " + item.getMetaData().getLiabolo_signature() + " sucessfully added to Library '" + lib.getDbURI() + "'!");
            } else {
                log.error("Not connected to library " + lib.getDbURI());
            }
        } catch (Exception e) {
            log.error("LibItem " + item.getMetaData().getLiabolo_signature() + " could not be added to Library '" + lib.getDbURI() + "'!");
        }
        return added;
    }

    /**
     * Add a new LibItem to the workspace of the local library
     * @param item the new LibItem
     * @param overwrite
     * @return added gives information about success or failure
     */

    private boolean addLibItemToWorkspace(LibItem item, boolean overwrite) {
        boolean added = false;
        try {
            //from now on this item is stored in the workspace
            item.getMetaData().isInWorkspace(true);
            if (local.addLibItem(item, true, overwrite)) {
                log.info("LibItem " + item.getMetaData().getLiabolo_signature() + " sucessfully added to workspace of Library '" + local.getDbURI() + "'!");
                added = true;
            } else
                log.error("LibItem " + item.getMetaData().getLiabolo_signature() + " could not be added to workspace of Library '" + local.getDbURI() + "'!");
        } catch (Exception e) {
            log.error("LibItem " + item.getMetaData().getLiabolo_signature() + " could not be added to workspace of Library '" + local.getDbURI() + "'!");
        }
        return added;
    }


    /**
     * Remove an existing LibItem
     * @param item LibItem to be removed
     * @return removed gives information about success or failure
     */
    public boolean removeLibItem(LibItem item) {
        boolean removed = false;
        Library lib = null;
        try {
            //get the lib
            String serverDbUri = "";
            StringTokenizer tok = new StringTokenizer(item.getMetaData().getLiabolo_signature(), "@");
            while (tok.hasMoreTokens())
                serverDbUri = tok.nextToken();

            if(serverDbUri.equals("localhost") || item.getMetaData().isInWorkspace()){
                lib = local;
            }else{
                lib = Gui.dispatcher.getConnectedLibraryByDbURI("exist://"+serverDbUri);
            }

            if(lib!=null){//OK
                removed = lib.removeLibItem(item);

                if(removed){
                    //check, if removed item was stored in an individual list, then remove it from list
                    String signature = item.getMetaData().getLiabolo_signature();
                    Collection allLists = getAllIndividualLists();
                    Iterator allListsIter = allLists.iterator();
                    while(allListsIter.hasNext()){
                        IndividualList actList = (IndividualList) allListsIter.next();
                        Hashtable allListItems = actList.getItems();

                        if(allListItems.containsKey(signature)){
                            actList.removeItem(signature);
                            if(!local.storeIndividualList(actList)){
                                removed = false; //because the list reference could not be removed
                                log.error("Libitem reference "+signature+" could not be removed from Individuallist "+actList.getListName()+".");
                            }else
                                log.debug("Libitem "+signature+" is removed from Individuallist "+actList.getListName()+".",5);

                        }
                    }
                }else{
                    log.error("LibItem could not be removed!");
                }

            }else{//signature is not valid or not connected
                log.info("No library found or invalid signature..");
                log.debug("Signature is :"+item.getMetaData().getLiabolo_signature(),6);
                log.debug("ServerDbURI is :"+serverDbUri,6);
                return false;
            }

            log.info("LibItem " + item.getMetaData().getLiabolo_signature() + " sucessfully removed!");
        } catch (ServiceNotAvailableException e) {
            log.error("LibItem could not be removed!");
        }
        return removed;
    }

    /**
     * Edit an existing LibItem
     * @param item LibItem to be updated
     * @return updated gives information about success or failure
     */
    public boolean editLibItem(LibItem item) {
        boolean updated = false;
        Library lib;
        try {
            //get the lib
            String serverDbUri = "";
            StringTokenizer tok = new StringTokenizer(item.getMetaData().getLiabolo_signature(), "@");
            while (tok.hasMoreTokens())
                serverDbUri = tok.nextToken();

            if(serverDbUri.equals("localhost") || item.getMetaData().isInWorkspace()){
                lib = local;
            }else{
                lib = Gui.dispatcher.getConnectedLibraryByDbURI("exist://"+serverDbUri);
            }

            if(lib!=null){//signature is not valid or not connected
                if (lib.editLibItem(item, false) == null) {//OK
                    log.info("LibItem '" + item.getMetaData().getLiabolo_signature() + "' sucessfully in '" + lib.getDbURI() + "' stored!");
                    return true;
                } else
                    return false;
            }else{
                log.info("No library found or invalid signature..");
                log.debug("Signature is :"+item.getMetaData().getLiabolo_signature(),6);
                log.debug("ServerDbURI is :"+serverDbUri,6);
                return false;
            }
        } catch (MergingException e) {
            log.error("LibItem '" + item.getMetaData().getLiabolo_signature() + "' could not updated successfully!");

        }

        return updated;
    }

    /**
     * Get a LibItem by known signature
     * @param signature Identifier for LibItem
     * @return LibItem (if exist) or null
     */
    public LibItem getLibItem(String signature) {
        log.debug("Get libitem '" + signature + "' from library '" + signature + "'..", 7);
        try {
            //get the lib
            String serverDbUri = "";
            StringTokenizer tok = new StringTokenizer(signature, "@");
            while (tok.hasMoreTokens())
                serverDbUri = tok.nextToken();

            Library lib;
            if(serverDbUri.equals(Configurator.getProperty("localURLAlias")))
                lib = local;
            else
                lib = getConnectedLibraryByDbURI("exist://" + serverDbUri);

            if(lib!=null)
                return lib.getLibItem(signature);
            else
                return null;
        } catch (ServiceNotAvailableException e) {
            log.debug(e);
            return null;
        }
    }

    public int countAllLibItemsFromBranch(String branch){
        return local.countAllLibItemsFromType("branch", branch);
    }

    /**
     * Search for all items stored in the given branch
     * @param branch Branch
     * @return Collection of LibItems
     */
    public Collection getAllLibItemsFromBranch(String branch) {
        Collection allLibItems = new HashSet();

        try {
            Iterator connectedLibsIter = connectedLibs.values().iterator();
            while (connectedLibsIter.hasNext()) {
//            for (int a = 0; a < connectedLibs.size(); a++) {
                allLibItems.addAll(((Library) connectedLibsIter.next()).getAllLibitemsFromBranch(branch, false));
            }

            // at last check the workspace
            // but display only workspace results which are not already contained in the result(original if possible)
            Collection allBranchItemsForLib = local.getAllLibitemsFromBranch(branch, true);

            Iterator allBranchItemsForLibIter = allBranchItemsForLib.iterator();
            while (allBranchItemsForLibIter.hasNext()) {  //for all workspace results
                LibItem actWorkspaceLibItem = ((LibItem) allBranchItemsForLibIter.next());
                Object[] allLibItemsArray = allLibItems.toArray();
                boolean contained = false;
                for (int a = 0; a < allLibItemsArray.length; a++) {
                    if (((LibItem) allLibItemsArray[a]).getMetaData().getLiabolo_signature().equals(actWorkspaceLibItem.getMetaData().getLiabolo_signature())) {
                        contained = true;
                        log.debug("LibItem with signature '" + actWorkspaceLibItem.getMetaData().getLiabolo_signature() + "' is in workspace, but would not be returned, because the real one is availbale!", 5);
                        break;
                    }
                }
                if (!contained)
                    allLibItems.add(actWorkspaceLibItem);
            }

        } catch (ServiceNotAvailableException e) {
            log.error("Could not get desired items for branch!");
        }

        return allLibItems;
    }

    /***********************************************
     *
     * Branch
     *
     ************************************************/

    /**
     * Retrieves all branches from a given Library
     * @param lib Library
     * @return Collection of branches
     */
    public Collection getAllBranches(Library lib) {
        Collection allBranches = new HashSet();
        try {
            allBranches = lib.getAllBranches();
        } catch (ServiceNotAvailableException e) {
            log.error("Could not get any available branches");
        }

        return allBranches;
    }

    /**
     * Adds a new branch to given library
     * @param branch new branch
     * @return added gives information about success or failure
     */
    public boolean addBranch(Branch branch, Library lib) {
        boolean added = false;
        try {
            added = lib.addBranch(branch);
        } catch (ServiceNotAvailableException e) {
            log.error("Could not add branch '" + branch.getAbbreviation() + "' to branchlist!");
        }

        return added;
    }

    /**
     * Edits the given branch</br>
     * Abbreviation could not be changed. Only the description is changeable.
     * @param branch
     * @return
     */
    public boolean editBranch(Branch branch) {
        if (removeBranch(branch))
            return addBranch(branch, local);
        else
            return false;
    }

    /**
     * Removes an existing branch
     * @param branch Branch to remove
     * @return removed gives information about success or failure
     */
    public boolean removeBranch(Branch branch) {
        boolean removed = false;
        try {
            removed = local.removeBranch(branch);
        } catch (ServiceNotAvailableException e) {
            log.error("Could not remove branch '" + branch.getAbbreviation() + "' from branchlist!");
        }

        return removed;
    }

    /***********************************************
     *
     * IndividualList
     *
     ************************************************/

    /**
     * Stores an individual list of items</br>
     * an Szenario is: indlist named 'master thesis', which represent all LibItems which are relevant for this work
     * @param indList IndividualList
     * @return added gives information about success or failure
     */
    public boolean addIndividualList(IndividualList indList) {
        boolean added = false;
        try {
            added = local.storeIndividualList(indList);
            if (added)
                log.info("Storing individualList '" + indList.getListName() + "' successfull!");
        } catch (ServiceNotAvailableException e) {
            log.error("Could not store individualList '" + indList.getListName() + "' !");
        }

        return added;
    }

    /**
     * Removes an existing individual list
     * @param listName Name of the list
     * @return removed gives information about success or failure
     */
    public boolean removeIndividualList(String listName) {
        boolean removed = false;
        try {
            removed = local.removeIndividualList(listName);
            if (removed)
                log.info("Removing individualList '" + listName + "' successfull!");
        } catch (ServiceNotAvailableException e) {
            log.error("Could not remove individualList '" + listName + "' !");
        }

        return removed;
    }

    /**
     * Processes changes on a given individual list</br>
     * References must not be valid over time</br>
     * Deletion of a libitem would not take changes in individual lists
     * @param list individual list
     * @return updated gives information about success or failure
     */
    public boolean editIndividualList(IndividualList list) {
        try {
            if (local.removeIndividualList(list.getListName()))
                return local.storeIndividualList(list);
        } catch (ServiceNotAvailableException e) {
            log.debug(e);
        }

        return false;
    }

    /**
     * Retrieves all stored individual lists
     * @return Collection of stored lists in the local library
     */
    public Collection getAllIndividualLists() {
        Collection allLists = new HashSet();
        try {
            allLists = local.getAllIndividualLists();
        } catch (ServiceNotAvailableException e) {
            log.error("Error occured while getting all individual lists' !");
        }
        return allLists;
    }

    /**
     * Gets a individual list spezified by its name
     * @param name Name of individual list
     * @return IndividualList or null
     */
    public IndividualList getIndividualList(String name) {
        try {
            IndividualList list = local.getIndividualList(name);
            return list;
        } catch (ServiceNotAvailableException e) {
            log.error("Error occured while getting the individual list '" + name + "' !");
            return null;
        }
    }

    /**
     * Retrieves all referenced LibItems from a given IndividualList name
     * @param listname Name of Individual list
     * @return Collection of LibItems referenced by the list
     */
    public Collection getAllItemsFromIndividualList(String listname) {
        Collection allItems = new HashSet();
        IndividualList list = getIndividualList(listname);
        Hashtable items = list.getItems();
        Enumeration itemsEnumeration = items.keys();
        while (itemsEnumeration.hasMoreElements()) {
            String actItemName = (String) itemsEnumeration.nextElement();
            LibItem actLibItem = getLibItem(actItemName);
            if (actLibItem != null)
                allItems.add(actLibItem);
        }

        return allItems;
    }

    /***********************************************
     *
     * Location
     *
     ************************************************/

    /**
     * Adds a new Location to the library</br>
     * A location is a temporary place, where people can find the libitem
     * @param loc Location, e.g. room number in building
     * @return added gives information about success or failure
     */
    public boolean addNewLocation(Location loc) {
        boolean added = false;
        try {
            added = local.addLocation(loc);
        } catch (ServiceNotAvailableException e) {
            log.debug(e);
        }

        return added;
    }

    /**
     * Change information about a location, mostly the description
     * @param loc Location
     * @return edited gives information about success or failure
     */
    public boolean editLocation(Location loc) {
        log.pause();
        try {
            if (local.removeLocation(loc))
                return local.addLocation(loc);
        } catch (ServiceNotAvailableException e) {
            log.error("Editing of Location '" + loc.getName() + "' failed!");
        } finally {
            log.resume();
        }
        return false;
    }

    /**
     * Removes a location
     * @param loc Location
     * @return removed gives information about success or failure
     */
    public boolean removeLocation(Location loc) {
        boolean removed = false;
        try {
            removed = local.removeLocation(loc);
        } catch (ServiceNotAvailableException e) {
            log.debug(e);
        }

        return removed;
    }

    public int countAllLibItemsFromLocation(String location){
        return local.countAllLibItemsFromType("coverage", location);
    }

    /**
     * Retrieves all locations from a given library
     * @param lib Library
     * @return Collection of Locations
     */
    public Collection getAllLocations(Library lib) {
        Collection allLocations = new HashSet();
        try {
            allLocations = lib.getAllLocations();
            log.debug(allLocations.size() + " locations found on lib '" + lib.getDbURI() + "'!",5);
        } catch (ServiceNotAvailableException e) {
            log.error("Error while retrieving all locations from db '" + lib.getDbURI() + "'!");
        }

        return allLocations;
    }


    /**
     * Search for all occurences of given location in all LibItems of the repository
     * @param loc Location
     * @return Collection of LibItems
     */
    public Collection getAllLibItemsFromLocation(Location loc) {
        Collection result;
        try {
            result = local.getAllLibItemsFromLocation(loc);
        } catch (ServiceNotAvailableException e) {
            log.error("Error occured while searching for all LibItems placed in the include given location!");
            result = new HashSet();
        }

        return result;
    }

/*####################################################################################
#
# BACKUP FUNCTIONS
#
####################################################################################*/
    /**
     * Makes a backup of the actual data on the given library
     * @param backupDir Place for storing the backup data
     * @param rootCollection axis from which the backup should be processed recursivly</br>
     * e.g. xmldb:exist:///db or xmldb:exist:///db/homes/user/branchlist/inf
     * @return backuped gives information about success or failure
     */
    public boolean backup(String backupDir, String rootCollection) {
        boolean backuped = false;
        try {
            local.backup(backupDir, rootCollection);
            backuped = true;
            log.info("Backup of " + rootCollection + " in " + backupDir + " created!");
        } catch (ServiceNotAvailableException e) {
            log.error("Backup of " + rootCollection + " in " + backupDir + " failed!");
        }

        return backuped;
    }

    /**
     * Restores an earlier made backup
     * @param filePath Place where the backup data were stored
     * @param rootCollection axis from which the backup should be processed recursivly</br>
     * e.g. xmldb:exist:///db or xmldb:exist:///db/homes/user/branchlist/inf
     * @return restored gives information about success or failure
     */
    public boolean restore(String filePath, String rootCollection) {
        boolean restored = false;
        try {
            local.restore(new File(filePath), rootCollection);
            restored = true;
            log.info("Restore of " + filePath + " created!");
        } catch (ServiceNotAvailableException e) {
            log.error("Restore of " + filePath + " failed!");
        }

        return restored;
    }

/*####################################################################################
#
# USERMANAGEMENT FUNCTIONS
#
####################################################################################*/
    /**
     * Adds a new user to a lib
     * @param user the new User
     * @param lib Library
     * @return added gives information about success or failure
     */
    public boolean addUser(User user, Library lib) {
        boolean userAdded = false;

        try {
            userAdded = lib.addUser(user);
        } catch (ServiceNotAvailableException e) {
            log.error("Error occuring while adding user '" + user.getName() + "'!");
        }

        return userAdded;
    }

    /**
     * Removes an existing user from the usermanagement of the library
     * @param user User to remove
     * @param lib Library
     * @return removed gives information about success or failure
     */
    public boolean removeUser(User user, Library lib) {
        boolean userRemoved = false;

        try {
            userRemoved = lib.removeUser(user);
            if (userRemoved)
                log.info("User '" + user.getName() + "' successfully removed");
            else
                log.info("User '" + user.getName() + "' could not be successfully removed");
        } catch (ServiceNotAvailableException e) {
            log.error("Error occuring while removing user '" + user.getName() + "'!");
        }

        return userRemoved;
    }

    /**
     * Make changes to a users profile
     * @param user
     * @param lib
     * @return edited gives information about success or failure
     */
    public boolean editUser(User user, Library lib) {
        boolean userEdited = false;

        try {
            userEdited = lib.editUser(user);
            if (userEdited)
                log.info("User '" + user.getName() + "' successfully updated");
            else
                log.info("User '" + user.getName() + "' could not be successfully updated");
        } catch (ServiceNotAvailableException e) {
            log.error("Error occuring while editing user '" + user.getName() + "'!");
        }

        return userEdited;
    }

    /**
     * Retrieves all user profiles
     * @param lib Library
     * @return Collection of User
     */
    public Collection getAllUsers(Library lib) {
        Collection allUsers = new HashSet();

        try {
            allUsers = lib.getAllUsers();
        } catch (ServiceNotAvailableException e) {
            log.error("Error occuring while retrieving all users from Library '" + lib.getDbURI() + "'!");
        }

        return allUsers;
    }

    public void registerLibrary() {
    }

    /**
     * Initiates the language files for a given locale
     * @param locale the given locale
     * @return RessourceBundle which contains language data
     */
    public ResourceBundle initLanguage(Locale locale) {

        String name = "org.liabolo.languages.Language";
        ResourceBundle lang = ResourceBundle.getBundle(name, locale);
        return lang;

    }

    /** gets a Collection of Metadata Items and returns a sorted ArrayList.
     *
     * @param s gives the metadata attribute which should be sorted; see MetaData.java for details.
     * (e.g. '1' stands for 'title')
     *
     * @param col The Collection of metadata to be sorted
     * @param lib The actual Library
     * @return a sorted ArrayList
     */
    public ArrayList sort(short s, Collection col, Library lib) {
        ArrayList list = new ArrayList();
        list = lib.sort(s, col);
        return list;
    }

    public ArrayList sortSignature(Collection col, Library lib) {
        ArrayList list = new ArrayList();
        list = lib.sortSignature(col);
        return list;
    }

    public ArrayList sortBranch(Collection col, Library lib) {
        ArrayList list = new ArrayList();
        list = lib.sortBranch(col);
        return list;
    }


    public ArrayList sortLocation(Collection col, Library lib) {
        ArrayList list = new ArrayList();
        list = lib.sortLocation(col);
        return list;
    }

    public ArrayList sortIndividualList(Collection col, Library lib) {
        ArrayList list = new ArrayList();
        list = lib.sortIndividualList(col);
        return list;
    }

    public ArrayList sortConnections(Collection col, Library lib) {
        ArrayList list = new ArrayList();
        list = lib.sortConnections(col);
        return list;
    }

    /** exports all items from a given individual list to a text file
     *
     * @param listNames - a collection of all names of lists to be exported
     * @param fileName - name of the text file to be written
     * @return a boolean indicating that file was written successfully or not
     */
    public boolean exportText(Collection listNames, String fileName) {
        short writeCounter = 0; // count successfull write operations
        short i = 0; // count iterations of while loop
        Iterator it = listNames.iterator();
        while (it.hasNext()) {
            i++;
            String name = it.next().toString();
            Collection allItems = getAllItemsFromIndividualList(name);
            boolean written = TextExport.writeToFile(allItems, name, fileName);
            if (written) writeCounter++;
        }
        if (writeCounter == i) {
            return true;
        } // check, if file was written in every while-iteration
        else {
            return false;
        }
    }

    public void export(Collection listnames, String filename, String format) {


		File f = new File(filename);
		if (f.exists()) f.delete();
    	Iterator it = listnames.iterator();
    	while (it.hasNext()) {
    		String name = it.next().toString();
    		Collection allItems = getAllItemsFromIndividualList(name);

 

    		XslExport.export(allItems,filename,format);
    	}
    }

}
