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

import org.exist.backup.Backup;
import org.exist.backup.Restore;
import org.exist.security.Permission;
import org.exist.util.SyntaxException;
import org.exist.xmldb.DatabaseInstanceManager;
import org.exist.xmldb.UserManagementService;
import org.exist.xmldb.DatabaseStatus;
import org.liabolo.common.*;
import org.liabolo.exception.MergingException;
import org.liabolo.exception.NoConnectionException;
import org.liabolo.exception.ServiceNotAvailableException;
import org.liabolo.exception.XMLException;
import org.w3c.dom.*;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.*;
import java.util.Collection;


public abstract class XMLDB implements Library {

    private Logger log = Logger.getLogger(this.getClass());
    private String name = "";
    private String dbURI = "";
    private String driver = "";
    private String username = "";
    private String password = "";
    private String rootCollection = "";

    private Database database;


    public XMLDB(String name, String dbURI, String driver) {
        this.name = name;
        log.debug("Name =" + name, 2);


        this.dbURI = dbURI;
        log.debug("DbURI =" + dbURI, 2);

        this.driver = driver;

        rootCollection = Configurator.getProperty("defaultRootCollection");
        log.debug("RootCollection is DefaultRootCollection '" + rootCollection + "'!", 3);
    }


    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return username;
    }

    public String getDbURI() {
        return dbURI;
    }

    public Database getDatabase() {
        return database;
    }

    public String getRootCollection() {
        return rootCollection;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }

    public Logger getLog() {
        return log;
    }

    /**
     * Login process to desired databse with username and password. </br> The root-collection will be opened
     * @param username
     * @param password
     */
    public boolean connect(String username, String password) {
        this.username = username;
        log.debug("Username =" + username, 2);
        this.password = password;
        log.debug("Password =" + password, 3);

        try {
            Class c = Class.forName(driver);
            database = (Database) c.newInstance();
            database.setProperty("create-database", "true");
            DatabaseManager.registerDatabase(database);
            log.debug("Database registered", 5);

            database.getCollection(dbURI + "/" + rootCollection, username, password);

            log.debug("Authentication passed!", 4);
            return true;
        } catch (XMLDBException e) {
            log.debug(e);
            return false;
        } catch (ClassNotFoundException e) {
            log.debug(e);
            return false;
        } catch (InstantiationException e) {
            log.debug(e);
            return false;
        } catch (IllegalAccessException e) {
            log.debug(e);
            return false;
        }
    }

    /**
     * Disconnect from databse
     * @return status of connection
     */
    public boolean shutdown() throws ServiceNotAvailableException {
        org.xmldb.api.base.Collection root = null;
        try {
            root = database.getCollection(dbURI + "/" + rootCollection, "admin", password);
            DatabaseInstanceManager manager = (DatabaseInstanceManager) root.getService("DatabaseInstanceManager", "1.0");
            manager.shutdown();
        } catch (XMLDBException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        }
        return true;
    }

    /**
     * Search on all opened collections with an XPATH expressure.</BR>
     * As first sample '//' the Collection contains all library items from the opened collections</BR>
     * As second example '/mediatype[@id='XPATH for beginnners ']' the Collection contains all library items mediatype as root of the doument with the attribute id='XPATH for beginners'
     * @param xpath XPATH expression
     * @return resultset
     * @see java.util.Collection
     * @throws NoConnectionException
     */
    public Collection search(String xpath) throws NoConnectionException {
        Collection result = new HashSet();
        log.debug("Search for xpath pattern = " + xpath, 5);
        try {
            org.xmldb.api.base.Collection actCol = database.getCollection(dbURI + "/" + rootCollection, username, password);
            XPathQueryService service = (XPathQueryService) actCol.getService("XPathQueryService", "1.0");
            service.setProperty("indent", "yes");
            ResourceSet resultSet = service.query(xpath);
            ResourceIterator resultIter = resultSet.getIterator();
            log.debug("Count results for Collection " + actCol.getName() + " = " + resultSet.getSize(), 7);
            while (resultIter.hasMoreResources()) {
                Resource res = resultIter.nextResource();
                if (res.getResourceType().equals("XMLResource")) {
                    log.debug("Searchresult is a XML resource.", 7);
                    XMLResource xmlResource = (XMLResource) res;
                    log.debug(res.getContent().toString(), 5);
                    Node node = xmlResource.getContentAsDOM();
                    result.add(MetaData.nodeToLibItem(node));
                } else
                    log.debug("Searchresult is no XML resource.", 7);
            }
        } catch (XMLDBException e) {
            log.debug(e);
            throw new NoConnectionException(e.getMessage());
        }
        return result;
    }

    /**
     * Searches for pattern on an element specified by the the table-key</br>
     * The keys are static types declared in org.liabolo.common.MetaData
     * @see org.liabolo.common.MetaData
     * @param types defined in MetaData
     * @param pattern search-pattern
     * @return  Collection with results
     */
    public Collection search(short[] types, String pattern, boolean workspace) throws ServiceNotAvailableException {
        log.debug("Search for '" + pattern + "' from " + this.dbURI + "..", 5);
        for (int a = 0; a < types.length; a++)
            log.debug(".. on types type[" + a + "]=" + types[a], 7);

        pattern = pattern.toLowerCase();

        if (pattern.startsWith("*"))
            pattern = pattern.substring(1, pattern.length());
        if (pattern.endsWith("*"))
            pattern = pattern.substring(0, pattern.length() - 1);


        Hashtable result = new Hashtable();
        try {
            org.xmldb.api.base.Collection actCol;

            if (!workspace) {
                log.debug("Search will NOT be processed on workspace of library '" + getDbURI() + "'", 7);
                actCol = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir + "/branchlist", username, password);
            } else {
                log.debug("Search will be processed on workspace of library '" + getDbURI() + "'", 7);
                actCol = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalWorkspace + "/branchlist", username, password);
            }

            for (int a = 0; a < types.length; a++) {
                short type = types[a];
                log.debug("Actual short type is'" + type + "'", 6);

                XPathQueryService service = (XPathQueryService) actCol.getService("XPathQueryService", "1.0");
                service.setProperty("indent", "yes");
                try {
                    switch (type) {
                        case MetaData.DC_IDENTIFIER:
                            {
                                log.debug("Search for pattern = " + pattern + " as identifier ..", 5);

                                ResourceSet resultSet = service.query("/mediatype/identifier[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                        case MetaData.DC_TITLE:
                            {
                                log.debug("Search for pattern = " + pattern + " as title ..", 5);

                                log.debug("Search for exact pattern:'/mediatype/title[contains(lower-case(text()),'" + pattern + "')]/parent::*'", 8);
                                ResourceSet resultSet = service.query("/mediatype/title[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                        case MetaData.DC_CREATOR:
                            {
                                log.debug("Search for pattern = " + pattern + " as creator ..", 5);

                                ResourceSet resultSet = service.query("/mediatype/creator[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                        case MetaData.DC_SUBJECT:
                            {
                                log.debug("Search for pattern = " + pattern + " as subject ..", 5);

                                ResourceSet resultSet = service.query("/mediatype/subject[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                        case MetaData.DC_DESCRIPTION:
                            {
                                log.debug("Search for pattern = " + pattern + " as description ..", 5);

                                ResourceSet resultSet = service.query("/mediatype/description[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                        case MetaData.DC_PUBLISHER:
                            {
                                log.debug("Search for pattern = " + pattern + " as publisher ..", 5);

                                ResourceSet resultSet = service.query("/mediatype/publisher[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                        case MetaData.DC_CONTRIBUTORS:
                            {
                                log.debug("Search for pattern = " + pattern + " as contributors ..", 5);

                                ResourceSet resultSet = service.query("/mediatype/contributors[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                        case MetaData.DC_DATE:
                            {
                                log.debug("Search for pattern = " + pattern + " as date ..", 5);

                                ResourceSet resultSet = service.query("/mediatype/date[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                        case MetaData.DC_TYPE:
                            {
                                log.debug("Search for pattern = " + pattern + " as type ..", 5);

                                ResourceSet resultSet = service.query("/mediatype/type[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                        case MetaData.DC_FORMAT:
                            {
                                log.debug("Search for pattern = " + pattern + " as format ..", 5);

                                ResourceSet resultSet = service.query("/mediatype/format[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                        case MetaData.DC_SOURCE:
                            {
                                log.debug("Search for pattern = " + pattern + " as source ..", 5);

                                ResourceSet resultSet = service.query("/mediatype/source[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                        case MetaData.DC_LANGUAGE:
                            {
                                log.debug("Search for pattern = " + pattern + " as language ..", 5);

                                ResourceSet resultSet = service.query("/mediatype/language[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                        case MetaData.DC_RELATION:
                            {
                                log.debug("Search for pattern = " + pattern + " as relation ..", 5);

                                ResourceSet resultSet = service.query("/mediatype/relation[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                        case MetaData.DC_COVERAGE:
                            {
                                log.debug("Search for pattern = " + pattern + " as coverage ..", 5);

                                ResourceSet resultSet = service.query("/mediatype/coverage[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                        case MetaData.DC_RIGHTS:
                            {
                                log.debug("Search for pattern = " + pattern + " as rights ..", 5);

                                ResourceSet resultSet = service.query("/mediatype/rights[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                        case MetaData.LIABOLO_BRANCH:
                            {
                                log.debug("Search for pattern = " + pattern + " as branch ..", 5);

                                ResourceSet resultSet = service.query("/mediatype/branch[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                        case MetaData.LIABOLO_SIGNATURE:
                            {
                                log.debug("Search for pattern = " + pattern + " as signature ..", 5);

                                ResourceSet resultSet = service.query("/mediatype/signature[contains(lower-case(text()),'" + pattern + "')]/parent::*");
                                result = processResultSet(result, resultSet);
                                break;
                            }
                    }
                } catch (IllegalArgumentException e) {
                    log.debug(e.toString(), 9);
                    //e.printStackTrace();
                } catch (Exception e) {
                    log.debug(e.toString(), 9);
                }
            }
        } catch (XMLDBException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        } catch (Exception e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        }

        log.debug("Number of results is '" + result.values().size() + "' !", 8);

        return result.values();
    }

    private Hashtable processResultSet(Hashtable result, ResourceSet partResult) throws XMLDBException {

        ResourceIterator resultIter = partResult.getIterator();
        log.debug("Count results for Collection  = " + partResult.getSize(), 7);
        while (resultIter.hasMoreResources()) {
            Resource res = resultIter.nextResource();
            if (res.getResourceType().equals("XMLResource")) {
                log.debug("Searchresult is a XML resource.", 7);
                XMLResource xmlResource = (XMLResource) res;
                log.debug(res.getContent().toString(), 7);
                Node node = xmlResource.getContentAsDOM();
                LibItem item = MetaData.nodeToLibItem(node);
                if (!result.containsKey(res.getId()))
                    result.put(item.getMetaData().getLiabolo_signature(), item);
                else
                    log.debug("Searchresult is already stored in result-collection", 7);
            } else
                log.debug("Searchresult is no XML resource.", 7);
        }
        return result;
    }


    /**
     * Update a changed libitem from offline working
     * @param item
     * @see org.liabolo.common.LibItem
     * @return Result of updating process
     */
    public boolean addLibItem(LibItem item, boolean workspace, boolean overwrite) throws ServiceNotAvailableException, XMLException {
        MetaData metaData = item.getMetaData();
        try {
            if (!workspace)
                log.debug("Collection for storing libitem :" + dbURI + "/" + rootCollection + "/" + Library.globalHomeDir + "/branchlist/" + metaData.getLiabolo_branch(), 8);
            else
                log.debug("Collection for storing libitem :" + dbURI + "/" + rootCollection + "/" + Library.globalWorkspace + "/branchlist/" + metaData.getLiabolo_branch(), 8);

            org.xmldb.api.base.Collection col;
            if (!workspace)
                col = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir + "/branchlist/" + metaData.getLiabolo_branch(), username, password);
            else
                col = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalWorkspace + "/branchlist/" + metaData.getLiabolo_branch(), username, password);

            if (col == null) {
                log.debug("LibItem could not added to branch '" + metaData.getLiabolo_branch() + "' because branch does not exist", 3);
            } else {
                StringTokenizer tok = new StringTokenizer(metaData.getLiabolo_signature(), "@");
                if (tok.hasMoreTokens()) {
                    String localSignature = tok.nextToken();
                    log.debug("LocalSignature :" + localSignature, 5);

                    //check, if resource already exists
                    Resource existingLibItemResource = col.getResource(localSignature);
                    if (existingLibItemResource == null) {//OK

                        //because now a new libitem is stored to the db the actualDbTime is set to the value of the localDbTime
                        if (!workspace)
                            metaData.setLastKnownDbTime(metaData.getLocalUpdateTime());

                        XMLResource document = (XMLResource) col.createResource(localSignature, "XMLResource");
                        document.setContentAsDOM(metaData.getDomContent());

                        col.storeResource(document);
                        return true;
                    } else {//Exist
                        if (overwrite) {//should be overwritten
                            existingLibItemResource.setContent(item.getMetaData().getDomContent());
                            col.storeResource(existingLibItemResource);

/*
                            log.debug("LibItem '" + localSignature + "' on lib '" + dbURI + "' should be overwritten!", 5);
                            col.removeResource(existingLibItemResource);
                            log.debug("Old LibItem sucessfully removed!", 7);
                            //Now there the item is removed, therefor nothing should be overwritten
                            //because now a new libitem is stored to the db the actualDbTime is set to the value of the localDbTime
                            if (!workspace) {
                                log.debug("LibItem is not in workspace!", 7);
                                metaData.setLastKnownDbTime(metaData.getLocalUpdateTime());
                            }

                            XMLResource newDocument = (XMLResource) col.createResource(localSignature, "XMLResource");
                            log.debug("Preparing new libitem for storing!", 7);

                            stupidMethod(Long.parseLong(Configurator.getProperty("dbDelay", "10500", "general")));

                            if(newDocument!=null)
                                System.out.println("Id of newDocuemnt:"+newDocument.getDocumentId());
                            if(metaData.getDomContent()!=null)
                                System.out.println("Node name:"+metaData.getDomContent().getNodeName());



                            newDocument.setContentAsDOM(metaData.getDomContent());
                            log.debug("New document created!", 7);
                            col.storeResource(newDocument);
                            log.debug("New LibItem sucessfully added!", 7);
*/                            return true;
                        } else {//should not be overwritten, failed
                            log.debug("LibItem '" + localSignature + "' already exists and should not be overwritten!", 5);
                            return false;
                        }
                    }
                } else//no signature found, failed
                    log.debug("Local Signature could not found", 7);
                return false;
            }

        } catch (Exception e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        }
        return false;
    }

    /**
     *
     * @param item
     * @return null, if LibItem is commited, otherwise return the pair of local and global libitems (MergingData)
     * @throws MergingException
     */
    public MergingData editLibItem(LibItem item, boolean overwrite) throws MergingException {
        MetaData metaData = item.getMetaData();
        try {
            if (item.getMetaData().isInWorkspace())
                log.debug("Edited LibItem is '" + item.getMetaData().getLiabolo_signature() + "' stored in workspace", 8);
            else
                log.debug("Edited LibItem is '" + item.getMetaData().getLiabolo_signature() + "' stored in local database", 8);
            org.xmldb.api.base.Collection col;
            if (!item.getMetaData().isInWorkspace())
                col = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir + "/branchlist/" + metaData.getLiabolo_branch(), username, password);
            else
                col = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalWorkspace + "/branchlist/" + metaData.getLiabolo_branch(), username, password);
            if (col == null) {
                log.debug("LibItem could not edited because branch does not exist!", 3);
            }

            Node dbRootNode = null;
            long dbActualTime;
            long lastKnownDbTime;
            StringTokenizer tok = new StringTokenizer(metaData.getLiabolo_signature(), "@");
            if (tok.hasMoreTokens()) {
                String localSignature = tok.nextToken();
                log.debug("LocalSignature :" + localSignature, 5);
                XMLResource ressource = (XMLResource) col.getResource(localSignature);
                dbRootNode = ressource.getContentAsDOM();
            }

            if (dbRootNode != null) {
                //really dbActualTime from db
                Element existingRootElement = (Element) dbRootNode;
                String actualDbTimeString = existingRootElement.getAttribute("actualDbTime");
                log.debug("actualDbTime =" + actualDbTimeString, 8);
                dbActualTime = Long.parseLong(actualDbTimeString);
                // the old dbTime from the checkout date
                log.debug("lastKnownDbTime =" + metaData.getLastKnownDbTime(), 8);
                lastKnownDbTime = Long.parseLong(metaData.getLastKnownDbTime());

                //since the last checkout no changes from another person are made
                if (dbActualTime == lastKnownDbTime) {
                    log.debug("Metadata times local and global are equal. No external changes has been made!", 3);
                    //OVERWRITE

                    if (addLibItem(item, item.getMetaData().isInWorkspace(), true))
                        return null;
                    else
                        return new MergingData(item, MetaData.nodeToLibItem(dbRootNode));
                } else {//since the last checkout there are changes made!! No commit can be made..
                    if (overwrite) {
                        log.info("Global Changes are made during last checkout. LibItem '" + metaData.getLiabolo_signature() + "' should be overwritten!");
                        if (addLibItem(item, item.getMetaData().isInWorkspace(), true))
                            return null;
                        else
                            return new MergingData(item, MetaData.nodeToLibItem(dbRootNode));
                    } else {
                        log.info("Global Changes are made during last checkout. LibItem '" + metaData.getLiabolo_signature() + "' could not be commited!");
                        return new MergingData(item, MetaData.nodeToLibItem(dbRootNode));
                    }
                }
            } else {
                throw new MergingException("Error occured while commiting libItem '" + metaData.getLiabolo_signature() + "'!");
            }
        } catch (XMLDBException e) {
            log.debug(e);
            throw new MergingException("Error occured while while commiting libItem '" + metaData.getLiabolo_signature() + "'!");
        } catch (NumberFormatException e) {
            log.debug(e);
            throw new MergingException("Error occured while while commiting libItem '" + metaData.getLiabolo_signature() + "'!");
        } catch (ServiceNotAvailableException e) {
            log.debug(e);
            throw new MergingException("Error occured while while commiting libItem '" + metaData.getLiabolo_signature() + "'!");
        } catch (XMLException e) {
            log.debug(e);
            throw new MergingException("Error occured while while commiting libItem '" + metaData.getLiabolo_signature() + "'!");
        }
    }


    /**
     * Removing a libitem from the database
     * @param libItem
     * @return success or failure
     */
    public boolean removeLibItem(LibItem libItem) throws ServiceNotAvailableException {
        MetaData metaData = libItem.getMetaData();
        try {
            org.xmldb.api.base.Collection col;
            if (!libItem.getMetaData().isInWorkspace())
                col = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir + "/branchlist/" + metaData.getLiabolo_branch(), username, password);
            else
                col = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalWorkspace + "/branchlist/" + metaData.getLiabolo_branch(), username, password);

            StringTokenizer tok = new StringTokenizer(metaData.getLiabolo_signature(), "@");
            if (tok.hasMoreTokens()) {
                String localSignature = tok.nextToken();
                log.debug("LocalSignature :" + localSignature, 5);
                XMLResource resource = (XMLResource) col.getResource(localSignature);
                col.removeResource(resource);
            } else
                return false;
        } catch (XMLDBException e) {
            throw new ServiceNotAvailableException(e.getMessage());
        }
        return true;
    }

    public LibItem getLibItem(String signature) throws ServiceNotAvailableException {
        short[] types = {MetaData.LIABOLO_SIGNATURE};

        Collection libitemCol = search(types, signature, false);

        if (name.equals(Configurator.getProperty("localName")))
            libitemCol.addAll(search(types, signature, true));

        if (libitemCol.size() > 0) {
            LibItem actItem = (LibItem) libitemCol.iterator().next();
            if (actItem != null)
                log.debug("LibItem '" + actItem.getMetaData().getLiabolo_signature() + "' found", 6);
            else
                log.debug("LibItem found but returned as null", 6);
            return actItem;
        } else {
            log.debug("No LibItem found!", 6);
            return null;
        }
    }

    public int countAllLibItemsFromType(String type, String pattern){
        try {
            org.xmldb.api.base.Collection actCol = database.getCollection(dbURI + "/" + rootCollection, username, password);
            XPathQueryService service = (XPathQueryService) actCol.getService("XPathQueryService", "1.0");
            service.setProperty("indent", "yes");
            //ResourceSet resultSet = service.query("count(/mediatype/"+type+"[contains(lower-case(text()),'"+pattern+"')]/self::*)");
            ResourceSet resultSet = service.query("count(/mediatype/"+type+"[contains(text(),'"+pattern+"')]/self::*)");
            ResourceIterator resultIter = resultSet.getIterator();
            if(resultIter.hasMoreResources()){
                Resource res = resultIter.nextResource();
                return Integer.parseInt(res.getContent().toString());
            }else
            {
            	return 0;
            }
        }catch(Exception e){
            log.debug(e);
            return 0;
        }
    }

    /**
     * Returns all Libitems from the specified branch in the user home-dir
     * @param branch
     * @return Collection of LibItems
     */
    public Collection getAllLibitemsFromBranch(String branch, boolean workspace) throws ServiceNotAvailableException {
        HashSet allLibItems = new HashSet();
        try {

            org.xmldb.api.base.Collection branchCol;
            if (!workspace)
                branchCol = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir + "/branchlist/" + branch, username, password);
            else
                branchCol = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalWorkspace + "/branchlist/" + branch, username, password);


            log.debug("Branch to list:" + branch, 5);
            if (branchCol != null) {
                String[] resourceNames = branchCol.listResources();
                for (int a = 0; a < resourceNames.length; a++) {
                    Resource actResource = branchCol.getResource(resourceNames[a]);
                    if (actResource.getResourceType().equals("XMLResource")) {
                        log.debug("LibItem " + actResource.getId() + " is a XML resource.", 7);
                        XMLResource xmlResource = (XMLResource) actResource;
                        log.debug(actResource.getContent().toString(), 7);
                        Node node = xmlResource.getContentAsDOM();
                        allLibItems.add(MetaData.nodeToLibItem(node));
                    } else
                        log.debug("LibItem " + actResource.getId() + " is no XML resource.", 4);
                }
            }
        } catch (XMLDBException e) {
            throw new ServiceNotAvailableException(e.getMessage());
        }
        return allLibItems;
    }

    /**
     * Gets all available branches from the user-dir.</br>
     * @return Collection of Branches
     */
    public Collection getAllBranches() throws ServiceNotAvailableException {
        List result = new ArrayList();
        Comparator comp = new Comparator() {
            public int compare(Object a, Object b) {
                String abbr_branch_a = ((Branch) a).getAbbreviation();
                String abbr_branch_b = ((Branch) b).getAbbreviation();
                if (abbr_branch_a != null && abbr_branch_b != null) {
                    char char_a = abbr_branch_a.toLowerCase().charAt(0);
                    char char_b = abbr_branch_b.toLowerCase().charAt(0);
                    if (char_a < char_b)
                        return -1;
                    if (char_a > char_b)
                        return 1;
                    if (char_a == char_b)
                        return 0;
                } else {
                    return -1;
                }

                return 1;
            }
        };

        try {
            org.xmldb.api.base.Collection mainHomeCollection = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir, username, password);

            XMLResource resource = (XMLResource) mainHomeCollection.getResource("branches");
            if (resource != null) {
                Node contentNode = resource.getContentAsDOM();
                NodeList children = contentNode.getChildNodes();
                for (int a = 0; a < children.getLength(); a++) {
                    Node actChildNode = children.item(a);
                    if (actChildNode.getNodeType() == Node.ELEMENT_NODE) {
                        result.add(new Branch(((Element) actChildNode).getAttribute("abbr"), ((Element) actChildNode).getAttribute("desc")));
                    }
                }
            }
        } catch (XMLDBException e) {
            log.debug(e);
        }

        Collections.sort(result, comp);

        return result;
    }

    /**
     * Adds a new branch to the users home-dir
     * @param branch
     * @return added gives information about success or failure
     */
    public boolean addBranch(Branch branch) throws ServiceNotAvailableException {
        boolean doAdd = true;
        try {
            org.xmldb.api.base.Collection mainHomeCollection = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir, username, password);

            if (mainHomeCollection.getResource("branches") == null) {
                XMLResource resource = (XMLResource) mainHomeCollection.createResource("branches", "XMLResource");

                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                Document doc = builder.newDocument();

                Element locationsElement = doc.createElement("branches");
                //locationsElement.setAttribute("library",dbURI);
                locationsElement = (Element) doc.appendChild(locationsElement);

                Element newLocationElement = doc.createElement("branch");
                newLocationElement.setAttribute("abbr", branch.getAbbreviation());
                newLocationElement.setAttribute("desc", branch.getDescription());

                locationsElement.appendChild(newLocationElement);

                resource.setContentAsDOM(locationsElement);

                mainHomeCollection.storeResource(resource);

                log.info("Branchlist initiated.");
            } else {
                XMLResource resource = (XMLResource) mainHomeCollection.createResource("branches", "XMLResource");
                Collection allBranches = getAllBranches();

                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                Document doc = builder.newDocument();

                Element branchesElement = doc.createElement("branches");
                //branchesElement.setAttribute("library",dbURI);
                branchesElement = (Element) doc.appendChild(branchesElement);

                //add old Locations an check if there is double one
                Iterator allBranchesIter = allBranches.iterator();
                while (allBranchesIter.hasNext()) {
                    Branch actBranch = (Branch) allBranchesIter.next();
                    log.debug("Branch in Collection :" + actBranch.getAbbreviation(), 5);
                    if (!actBranch.getAbbreviation().equals(branch.getAbbreviation())) {
                        Element newBranchElement = doc.createElement("branch");
                        newBranchElement.setAttribute("abbr", actBranch.getAbbreviation());
                        newBranchElement.setAttribute("desc", actBranch.getDescription());

                        branchesElement.appendChild(newBranchElement);
                    } else {
                        //found the identical existing Location, not add double
                        doAdd = false;
                        break;
                    }
                }
                //the new Branch
                Element newBranchElement = doc.createElement("branch");
                newBranchElement.setAttribute("abbr", branch.getAbbreviation().toLowerCase());
                newBranchElement.setAttribute("desc", branch.getDescription());

                branchesElement.appendChild(newBranchElement);

                if (doAdd) {

                    createBranchCollection(branch.getAbbreviation());
                    //add the branch-Collection
                    org.xmldb.api.base.Collection colBranch = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir + "/branchlist", username, password);
                    CollectionManagementService mgtService = (CollectionManagementService)
                            colBranch.getService("CollectionManagementService", "1.0");
                    mgtService.createCollection(branch.getAbbreviation());

                    //add the documetn with branches
                    mainHomeCollection.removeResource(resource);
                    resource.setContentAsDOM(branchesElement);

                    mainHomeCollection.storeResource(resource);
                    log.info("Branch '" + branch.getAbbreviation() + "' succesfully stored!");
                    return true;
                } else {
                    log.info("Branch '" + branch.getAbbreviation() + "' already exists!");
                    return false;
                }

            }

        } catch (XMLDBException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        } catch (ParserConfigurationException e) {
            log.debug(e);
        } catch (SyntaxException e) {
            log.debug(e);
        }
        return false;
    }

    /**
     * Removes a user branch from the users home-dir
     * First the branch entry in the branch list will be removed, and then the branch with all data its including
     * @param branch
     * @return removed gives information about success or failure
     */
    public boolean removeBranch(Branch branch) throws ServiceNotAvailableException {
        boolean doRemove = false;
        try {
            org.xmldb.api.base.Collection mainHomeCollection = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir, username, password);
            if (mainHomeCollection.getResource("branches") != null) {
                XMLResource resource = (XMLResource) mainHomeCollection.createResource("branches", "XMLResource");
                Collection allBranches = getAllBranches();

                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                Document doc = builder.newDocument();

                Element branchesElement = doc.createElement("branches");
                //branchesElement.setAttribute("library",dbURI);
                branchesElement = (Element) doc.appendChild(branchesElement);

                Iterator allBranchesIter = allBranches.iterator();
                while (allBranchesIter.hasNext()) {
                    Branch actBranch = (Branch) allBranchesIter.next();
                    if (!actBranch.getAbbreviation().equals(branch.getAbbreviation())) {
                        Element newBranchElement = doc.createElement("branch");
                        newBranchElement.setAttribute("abbr", actBranch.getAbbreviation());
                        newBranchElement.setAttribute("desc", actBranch.getDescription());

                        branchesElement.appendChild(newBranchElement);
                    } else {
                        //found match and now remove ist
                        doRemove = true;
                    }
                }

                if (doRemove) {
                    //remove the document with branches
                    mainHomeCollection.removeResource(resource);
                    resource.setContentAsDOM(branchesElement);

                    mainHomeCollection.storeResource(resource);
                    //remove the branch-Collection
                    org.xmldb.api.base.Collection colBranch = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir, username, password);
                    CollectionManagementService mgtService = (CollectionManagementService)
                            colBranch.getService("CollectionManagementService", "1.0");
                    mgtService.removeCollection("/" + Library.globalHomeDir + "/branchlist" + "/" + branch);

                    log.info("Branch '" + branch.getAbbreviation() + "' succesfully removed!");
                    return true;
                } else {
                    log.info("Branch '" + branch.getAbbreviation() + "' does not exist!");
                    return false;
                }
            }

        } catch (XMLDBException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return false;

    }

    /**
     * Removes all LibItems from the given branch in workspace or user-dir
     * @param branch
     * @return whether all Libitems could be removed or not all
     * @throws ServiceNotAvailableException
     */
    public boolean removeAllLibItemsFromBranch(Branch branch, boolean workspace) throws ServiceNotAvailableException {
        boolean allRemoved = true;
        try {
            org.xmldb.api.base.Collection branchCollection;
            if (!workspace)
                branchCollection = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir + "/branchlist/" + branch.getAbbreviation(), username, password);
            else
                branchCollection = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalWorkspace + "/branchlist/" + branch.getAbbreviation(), username, password);

            if (branchCollection != null) {
                String[] allLibItemNames = branchCollection.listResources();
                log.debug("Found '" + allLibItemNames.length + "' LibItems for Branch '" + branch.getAbbreviation() + "'", 7);
                for (int a = 0; a < allLibItemNames.length; a++) {
                    Resource actResource = branchCollection.getResource(allLibItemNames[a]);
                    if (actResource != null) {
                        try {
                            branchCollection.removeResource(actResource);
                            log.debug("LibItem '" + actResource.getId() + "' is removed!", 5);
                        } catch (XMLDBException ex) {
                            log.debug("LibItem '" + actResource.getId() + "' could not be removed!", 5);
                            allRemoved = false;
                        }
                    }
                }
            } else {
                log.error("Could not found desired branch '" + branch.getAbbreviation() + "'!");
            }
        } catch (XMLDBException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        }
        return allRemoved;
    }

    public boolean storeIndividualList(IndividualList newList) throws ServiceNotAvailableException {
        try {
            org.xmldb.api.base.Collection individualListCollection = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir + "/individuallist", username, password);
            if (individualListCollection == null)
                createCollection(Library.globalHomeDir + "/individuallist", username, User.DEFAULT_USER, Configurator.getProperty("homePermission"));

            // create a new individual list named 'name'
            if (individualListCollection.getResource(newList.getListName()) == null) {
                XMLResource document = (XMLResource) individualListCollection.createResource(newList.getListName(), "XMLResource");
                document.setContentAsDOM(newList.getDomContent());
                individualListCollection.storeResource(document);

                log.info("Individual list  '" + newList.getListName() + "' successfully created.");
                return true;
            } else {
                log.info("Individual list  '" + newList.getListName() + "' exists already.");
                return false;
            }
        } catch (XMLDBException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        } catch (ParserConfigurationException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        } catch (SyntaxException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        }
    }

    /**
     * Removes an existing individual list from database
     * @param listName
     * @return removed gives information about success or failure
     * @throws ServiceNotAvailableException
     */
    public boolean removeIndividualList(String listName) throws ServiceNotAvailableException {
        try {
            org.xmldb.api.base.Collection col = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir + "/individuallist", username, password);
            XMLResource resource = (XMLResource) col.getResource(listName);
            col.removeResource(resource);
        } catch (XMLDBException e) {
            throw new ServiceNotAvailableException(e.getMessage());
        }
        return true;
    }

    /**
     * Retrieves all stored individual lists from the users home dir
     * @return COllection with IndividualList-Objects
     * @throws ServiceNotAvailableException
     */
    public Collection getAllIndividualLists() throws ServiceNotAvailableException {
        HashSet allIndividualLists = new HashSet();
        try {
            org.xmldb.api.base.Collection indCol = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir + "/individuallist", username, password);
            IndividualList actList;
            String[] resourceNames = indCol.listResources();
            for (int a = 0; a < resourceNames.length; a++) {
                Resource actResource = indCol.getResource(resourceNames[a]);
                if (actResource.getResourceType().equals("XMLResource")) {
                    log.debug("IndividualList " + actResource.getId() + " is a XML resource.", 7);
                    XMLResource xmlResource = (XMLResource) actResource;
                    log.debug(actResource.getContent().toString(), 7);
                    Node node = xmlResource.getContentAsDOM();
                    actList = IndividualList.nodeToIndividualList(node);
                    if (actList != null)
                        allIndividualLists.add(actList);
                } else
                    log.debug("IndividualList " + actResource.getId() + " is no XML resource.", 4);
            }
        } catch (XMLDBException e) {
            throw new ServiceNotAvailableException(e.getMessage());
        }
        return allIndividualLists;
    }

    /**
     * Retrieves the desired individual list from the users home dir
     * @param name of the list
     * @return IndividualList or null
     * @throws ServiceNotAvailableException
     */
    public IndividualList getIndividualList(String name) throws ServiceNotAvailableException {
        try {
            org.xmldb.api.base.Collection indCol = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir + "/individuallist", username, password);
            Resource resource = indCol.getResource(name);
            if (resource.getResourceType().equals("XMLResource")) {
                log.debug("IndividualList " + resource.getId() + " is a XML resource.", 7);
                XMLResource xmlResource = (XMLResource) resource;
                log.debug(resource.getContent().toString(), 7);
                Node node = xmlResource.getContentAsDOM();
                IndividualList actList = IndividualList.nodeToIndividualList(node);
                return actList;
            } else {
                log.debug("IndividualList " + resource.getId() + " is no XML resource.", 4);
                return null;
            }
        } catch (XMLDBException e) {
            throw new ServiceNotAvailableException(e.getMessage());
        }
    }

    /**
     * Adds a new location to an existing library
     * @param location
     * @return added gives information about success or failure
     * @throws ServiceNotAvailableException
     */
    public boolean addLocation(Location location) throws ServiceNotAvailableException {
        boolean doAdd = true;
        try {
            org.xmldb.api.base.Collection mainHomeCollection = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir, username, password);

            if (mainHomeCollection.getResource("locations") == null) {
                XMLResource resource = (XMLResource) mainHomeCollection.createResource("locations", "XMLResource");

                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                Document doc = builder.newDocument();

                Element locationsElement = doc.createElement("locations");

                locationsElement = (Element) doc.appendChild(locationsElement);

                Element newLocationElement = doc.createElement("location");
                newLocationElement.setAttribute("name", location.getName());
                newLocationElement.setAttribute("desc", location.getDescription());

                locationsElement.appendChild(newLocationElement);

                resource.setContentAsDOM(locationsElement);

                mainHomeCollection.storeResource(resource);

                log.info("Locationlist initiated.");
            } else {
                XMLResource resource = (XMLResource) mainHomeCollection.createResource("locations", "XMLResource");
                Collection allLocations = getAllLocations();

                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                Document doc = builder.newDocument();

                Element locationsElement = doc.createElement("locations");
                //locationsElement.setAttribute("library",dbURI);
                locationsElement = (Element) doc.appendChild(locationsElement);

                //add old Locations an check if there is double one
                Iterator allLocationsIter = allLocations.iterator();
                while (allLocationsIter.hasNext()) {
                    Location actLocation = (Location) allLocationsIter.next();
                    log.debug("Location in Collection :" + actLocation.getName(), 5);
                    if (!actLocation.getName().equals(location.getName())) {
                        Element newLocationElement = doc.createElement("location");
                        newLocationElement.setAttribute("name", actLocation.getName());
                        newLocationElement.setAttribute("desc", actLocation.getDescription());

                        locationsElement.appendChild(newLocationElement);
                    } else {
                        //found the identical existing Location, not add double
                        doAdd = false;
                        break;
                    }
                }
                //the new Location
                Element newLocationElement = doc.createElement("location");
                newLocationElement.setAttribute("name", location.getName());
                newLocationElement.setAttribute("desc", location.getDescription());

                locationsElement.appendChild(newLocationElement);

                if (doAdd) {
                    mainHomeCollection.removeResource(resource);
                    resource.setContentAsDOM(locationsElement);

                    mainHomeCollection.storeResource(resource);
                    log.info("Location '" + location.getName() + "' succesfully stored!");
                    return true;
                } else {
                    log.info("Location '" + location.getName() + "' already exists!");
                    return false;
                }

            }

        } catch (XMLDBException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        } catch (ParserConfigurationException e) {
            log.debug(e);
        }

        return false;
    }

    public boolean removeLocation(Location location) throws ServiceNotAvailableException {
        boolean doRemove = false;
        try {
            org.xmldb.api.base.Collection mainHomeCollection = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir, username, password);
            if (mainHomeCollection.getResource("locations") != null) {
                XMLResource resource = (XMLResource) mainHomeCollection.createResource("locations", "XMLResource");
                Collection allLocations = getAllLocations();

                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                Document doc = builder.newDocument();

                Element locationsElement = doc.createElement("locations");
                //locationsElement.setAttribute("library",dbURI);
                locationsElement = (Element) doc.appendChild(locationsElement);

                Iterator allLocationsIter = allLocations.iterator();
                while (allLocationsIter.hasNext()) {
                    Location actLocation = (Location) allLocationsIter.next();
                    if (!actLocation.getName().equals(location.getName())) {
                        Element newLocationElement = doc.createElement("location");
                        newLocationElement.setAttribute("name", actLocation.getName());
                        newLocationElement.setAttribute("desc", actLocation.getDescription());

                        locationsElement.appendChild(newLocationElement);
                    } else {
                        //found match and now remove ist
                        doRemove = true;
                    }
                }

                if (doRemove) {
                    mainHomeCollection.removeResource(resource);
                    resource.setContentAsDOM(locationsElement);

                    mainHomeCollection.storeResource(resource);
                    log.info("Location '" + location.getName() + "' succesfully removed!");
                    return true;
                } else {
                    log.info("Location '" + location.getName() + "' does not exist!");
                    return false;
                }
            }

        } catch (XMLDBException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
        return false;
    }

    public Collection getAllLocations() throws ServiceNotAvailableException {
        Collection result = new HashSet();

        try {
            org.xmldb.api.base.Collection mainHomeCollection = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir, username, password);

            XMLResource resource = (XMLResource) mainHomeCollection.getResource("locations");
            if (resource != null) {
                Node contentNode = resource.getContentAsDOM();
                NodeList children = contentNode.getChildNodes();
                for (int a = 0; a < children.getLength(); a++) {
                    Node actChildNode = children.item(a);
                    if (actChildNode.getNodeType() == Node.ELEMENT_NODE) {
                        result.add(new Location(((Element) actChildNode).getAttribute("name"), ((Element) actChildNode).getAttribute("desc")));
                    }
                }
            }
        } catch (XMLDBException e) {
            log.debug(e);
        }

        return result;
    }

    /**
     * Search for all occurences of given location in all LibItems of the repository
     * @param loc Location
     * @return Collection of LibItems
     * @throws ServiceNotAvailableException
     */
    public Collection getAllLibItemsFromLocation(Location loc) throws ServiceNotAvailableException {
        short[] types = {MetaData.DC_COVERAGE};

        Collection libitemCol = search(types, loc.getName(), false);

        if (name.equals(Configurator.getProperty("localName")))
        	libitemCol.addAll(search(types, loc.getName(), true));


        return libitemCol;
    }

    public abstract boolean addUser(User user) throws ServiceNotAvailableException;

    public abstract boolean removeUser(User user) throws ServiceNotAvailableException;

    public abstract boolean editUser(User user) throws ServiceNotAvailableException;

    public abstract Collection getAllUsers() throws ServiceNotAvailableException;

    public abstract Collection getGroups() throws ServiceNotAvailableException;


    public org.xmldb.api.base.Collection createCollection(String name, String owner, String group, String permissions) throws XMLDBException, SyntaxException {
        CollectionManagementService colMgmtService;
        UserManagementService userMgmtService;
        org.xmldb.api.base.Collection rootCol;
        org.xmldb.api.base.Collection newCol;

        rootCol = database.getCollection(dbURI + "/" + rootCollection, username, password);
        colMgmtService = (CollectionManagementService)
                rootCol.getService("CollectionManagementService", "1.0");
        newCol = colMgmtService.createCollection(name);

        log.info("Collection " + newCol.getName() + " created.");
        userMgmtService =
                (UserManagementService) rootCol.getService("UserManagementService",
                        "1.0");
        Permission p = new Permission();
        p.setOwner(owner);
        p.setGroup(group);
        p.setPermissions(permissions);
        userMgmtService.setPermissions(newCol, p);

        return newCol;
    }

    private org.xmldb.api.base.Collection createBranchCollection(String branch) throws XMLDBException, SyntaxException {
        CollectionManagementService colMgmtService;
        UserManagementService userMgmtService;
        org.xmldb.api.base.Collection rootCol;
        org.xmldb.api.base.Collection newCol;

        rootCol = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir + "/branchlist", username, password);
        colMgmtService = (CollectionManagementService)
                rootCol.getService("CollectionManagementService", "1.0");
        newCol = colMgmtService.createCollection(branch);

        log.info("Collection " + newCol.getName() + " created.");
        userMgmtService =
                (UserManagementService) rootCol.getService("UserManagementService",
                        "1.0");
        Permission p = new Permission();
        p.setOwner(username);
        p.setGroup(User.DEFAULT_USER);
        p.setPermissions(Configurator.getProperty("branchPermission"));
        userMgmtService.setPermissions(newCol, p);

        return newCol;
    }


    public boolean backup(String backupDir, String rootCollection) throws ServiceNotAvailableException {
        boolean backuped = false;
        log.debug("Backup-dir " + backupDir,5);
        Backup backup = new Backup(username, password, backupDir, rootCollection);
        try {
            backup.backup(false, null);
            backuped = true;
        } catch (Exception e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        }

        return backuped;
    }

    public boolean restore(File content, String rootCollection) throws ServiceNotAvailableException {
        boolean restored = false;
        try {
            Restore restore = new Restore(username, password, content, rootCollection);
            restore.restore(true, null);
            restored = true;
        } catch (Exception e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        }

        return restored;
    }

    public ArrayList sort(final short type, Collection col) {


        Iterator it = col.iterator();
        ArrayList list = new ArrayList();

        while (it.hasNext()) {
            list.add(it.next());
        }
        class MetadataComparator implements Comparator {
            int i;

            public int compare(Object a, Object b) {
                MetaData md1 = ((LibItem) a).getMetaData();
                MetaData md2 = ((LibItem) b).getMetaData();
                switch (type) {
                    case 0:
                        i = (md1.getDc_identifier().compareTo(md2.getDc_identifier()));
                        break;
                    case 1:
                        i = ((md1.getDc_title().toLowerCase()).compareTo(md2.getDc_title().toLowerCase()));
                        break;
                    case 2:
                        i = ((md1.getDc_creator().toLowerCase()).compareTo((md2.getDc_creator().toLowerCase())));
                        break;
                    case 3:
                        i = ((md1.getDc_subject().toLowerCase()).compareTo(md2.getDc_subject().toLowerCase()));
                        break;
                    case 4:
                        i = ((md1.getDc_description().toLowerCase()).compareTo(md2.getDc_description().toLowerCase()));
                        break;
                    case 5:
                        i = ((md1.getDc_publisher().toLowerCase()).compareTo((md2.getDc_publisher().toLowerCase())));
                        break;
                    case 6:
                        i = ((md1.getDc_contributors().toLowerCase()).compareTo(md2.getDc_contributors().toLowerCase()));
                        break;
                    case 7:
                        i = (md1.getDc_date().compareTo(md2.getDc_date()));
                        break;
                    case 8:
                        i = ((md1.getDc_type().toLowerCase()).compareTo(md2.getDc_type().toLowerCase()));
                        break;
                    case 9:
                        i = ((md1.getDc_format().toLowerCase()).compareTo(md2.getDc_format().toLowerCase()));
                        break;
                    case 10:
                        i = ((md1.getDc_source().toLowerCase()).compareTo(md2.getDc_source().toLowerCase()));
                        break;
                    case 11:
                        i = ((md1.getDc_language().toLowerCase()).compareTo(md2.getDc_language().toLowerCase()));
                        break;
                    case 12:
                        i = ((md1.getDc_relation().toLowerCase()).compareTo(md2.getDc_relation().toLowerCase()));
                        break;
                    case 13:
                        i = ((md1.getDc_coverage().toLowerCase()).compareTo(md2.getDc_coverage().toLowerCase()));
                        break;
                    case 14:
                        i = ((md1.getDc_rights().toLowerCase()).compareTo(md2.getDc_rights().toLowerCase()));
                        break;
                    case 15:
                        i = ((md1.getLiabolo_branch().toLowerCase()).compareTo(md2.getLiabolo_branch().toLowerCase()));
                        break;


                    default :
                        i = (md1.getDc_title().compareTo((md2.getDc_title())));
                }
                return i;


            }
        }


        Collections.sort(list, new MetadataComparator());
        return list;
    }

    public ArrayList sortSignature(Collection col) {

        Iterator it = col.iterator();
        ArrayList list = new ArrayList();

        while (it.hasNext()) {
            list.add(it.next());
        }


        class SignaturePrefixComparator implements Comparator {
            int i;

            public int compare(Object a, Object b) {
                MetaData md1 = ((LibItem) a).getMetaData();
                MetaData md2 = ((LibItem) b).getMetaData();
                String s1 = md1.getLiabolo_signature();
                String s2 = md2.getLiabolo_signature();

                // now sort by the signature part before the @
                String prefix1 = s1.substring(0, s1.indexOf("@"));
                String prefix2 = s2.substring(0, s2.indexOf("@"));
                i = prefix1.compareTo(prefix2);
                return i;
            }

        }
        Collections.sort(list, new SignaturePrefixComparator());

        class SignatureSuffixComparator implements Comparator {
            int i;

            public int compare(Object a, Object b) {
                MetaData md1 = ((LibItem) a).getMetaData();
                MetaData md2 = ((LibItem) b).getMetaData();
                String s1 = md1.getLiabolo_signature();
                String s2 = md2.getLiabolo_signature();

                // first sort by the hostpart after the @
                String suffix1 = s1.substring(s1.indexOf("@") + 1);
                String suffix2 = s2.substring(s2.indexOf("@") + 1);
                i = suffix1.compareTo(suffix2);
                return i;
            }

        }
        Collections.sort(list, new SignatureSuffixComparator());

        return list;
    }

    public ArrayList sortBranch(Collection col) {

        Iterator it = col.iterator();
        ArrayList list = new ArrayList();

        while (it.hasNext()) {
            list.add(it.next());
        }

        class BranchComparator implements Comparator {
            int i;

            public int compare(Object a, Object b) {
                Branch b1 = (Branch) a;
                Branch b2 = (Branch) b;

                i = (b1.getDescription()).toLowerCase().compareTo((b2.getDescription().toLowerCase()));

                return i;
            }


        }

        Collections.sort(list, new BranchComparator());

        return list;
    }


    public ArrayList sortLocation(Collection col) {

        Iterator it = col.iterator();
        ArrayList list = new ArrayList();

        while (it.hasNext()) {
            list.add(it.next());
        }

        class LocationComparator implements Comparator {
            int i;

            public int compare(Object a, Object b) {
                Location l1 = (Location) a;
                Location l2 = (Location) b;

                i = (l1.getName()).toLowerCase().compareTo((l2.getName().toLowerCase()));

                return i;
            }


        }

        Collections.sort(list, new LocationComparator());

        return list;
    }

    public ArrayList sortIndividualList(Collection col) {

        Iterator it = col.iterator();
        ArrayList list = new ArrayList();

        while (it.hasNext()) {
            list.add(it.next());
        }

        class IndividualListComparator implements Comparator {
            int i;

            public int compare(Object a, Object b) {
                IndividualList l1 = (IndividualList) a;
                IndividualList l2 = (IndividualList) b;

                i = (l1.getListName()).toLowerCase().compareTo((l2.getListName().toLowerCase()));

                return i;
            }


        }
        Collections.sort(list, new IndividualListComparator());

        return list;
    }


    public ArrayList sortConnections(Collection col) {

        Iterator it = col.iterator();
        ArrayList list = new ArrayList();

        while (it.hasNext()) {
            list.add(it.next());
        }

        class ConnectionsComparator implements Comparator {
            int i;

            public int compare(Object a, Object b) {
                Connection c1 = (Connection) a;
                Connection c2 = (Connection) b;

                i = (c1.getName()).toLowerCase().compareTo((c2.getName().toLowerCase()));

                return i;
            }


        }

        Collections.sort(list, new ConnectionsComparator());

        return list;
    }

    public ArrayList getMediaTypeItems(String mediaType) throws ServiceNotAvailableException {
        ArrayList items = new ArrayList();

        try {
            org.xmldb.api.base.Collection mainHomeCol = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir, username, password);
            Resource resource = mainHomeCol.getResource(mediaType);
            if (resource != null && resource.getResourceType().equals("XMLResource")) {
                log.debug("MediaType " + resource.getId() + " is a XML resource.", 7);
                XMLResource xmlResource = (XMLResource) resource;
                log.debug(resource.getContent().toString(), 7);
                Node node = xmlResource.getContentAsDOM();
                NodeList children = node.getChildNodes();

                for (int a = 0; a < children.getLength(); a++) {
                    if (children.item(a) != null && children.item(a).getNodeType() == Node.ELEMENT_NODE) {
                        Element actChildElem = (Element) children.item(a);
                        items.add(new TextItem(new Short(actChildElem.getAttribute("DCid")).shortValue(), actChildElem.getAttribute("name"), actChildElem.getAttribute("desc"), new Short(actChildElem.getAttribute("type")).shortValue()));
                    }
                }
            }

            return items;
        } catch (XMLDBException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        }
    }

    public List getAllMediaTypes() throws ServiceNotAvailableException {
        List mediaTypes = new ArrayList();
        try {
            org.xmldb.api.base.Collection mainHomeCol = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir, username, password);
            String[] allResources = mainHomeCol.listResources();
            mediaTypes.remove("branches");
            mediaTypes.remove("locations");
            for (int a = 0; a < allResources.length; a++)
                if (!allResources[a].equals("locations") && !allResources[a].equals("branches"))
                    mediaTypes.add(allResources[a]);

            return mediaTypes;
        } catch (XMLDBException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.getMessage());
        }
    }

    public boolean removeMediaType(String mediaType) throws ServiceNotAvailableException {
        try {
            org.xmldb.api.base.Collection col = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir, username, password);
            XMLResource resource = (XMLResource) col.getResource(mediaType);
            col.removeResource(resource);
            return true;
        } catch (XMLDBException e) {
            throw new ServiceNotAvailableException(e.getMessage());
        }
    }

    public boolean addMediaType(String mediaType, TextItem[] items) throws ServiceNotAvailableException {
        boolean added = false;
        try {
            org.xmldb.api.base.Collection mainHomeCollection = database.getCollection(dbURI + "/" + rootCollection + "/" + Library.globalHomeDir, username, password);
            if (mainHomeCollection.getResource(mediaType) == null) {
                XMLResource resource = (XMLResource) mainHomeCollection.createResource(mediaType, "XMLResource");

                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                Document doc = builder.newDocument();

                Element mediaTypeElement = doc.createElement("mediaType");
                //mediaTypeElement.setAttribute("id", mediaType);
                mediaTypeElement = (Element) doc.appendChild(mediaTypeElement);

                for (int a = 0; a < items.length; a++) {
                    Element newTextItemElement = doc.createElement("metadata");
                    newTextItemElement.setAttribute("name", items[a].getKeyName());
                    newTextItemElement.setAttribute("desc", items[a].getKeyDescription());
                    newTextItemElement.setAttribute("DCid", new Short(items[a].getDCid()).toString());
                    newTextItemElement.setAttribute("type", new Short(items[a].getType()).toString());
                    mediaTypeElement.appendChild(newTextItemElement);

                }

                resource.setContentAsDOM(mediaTypeElement);

                mainHomeCollection.storeResource(resource);

                return true;

            }
        } catch (XMLDBException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.toString());
        } catch (ParserConfigurationException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.toString());
        } catch (DOMException e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.toString());
        } catch (Exception e) {
            log.debug(e);
            throw new ServiceNotAvailableException(e.toString());
        }


        return added;
    }

    /*
    private void stupidMethod(long waitingTime){
        log.debug("Waiting for "+waitingTime+" milliseconds ",9);
        long current = System.currentTimeMillis();
        while(current + waitingTime > System.currentTimeMillis()){
            //a very dirty method, but there is no time left to debug :-)
        }
    }
    */
}
