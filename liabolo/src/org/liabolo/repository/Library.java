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

import java.util.Collection;
import java.util.*;
import java.io.File;

import org.liabolo.common.*;
import org.liabolo.exception.AuthenticationException;
import org.liabolo.exception.MergingException;
import org.liabolo.exception.NoConnectionException;
import org.liabolo.exception.ServiceNotAvailableException;
import org.liabolo.exception.UnknownException;
import org.liabolo.exception.XMLException;
import org.xmldb.api.base.XMLDBException;

public interface Library {

    public static final String globalHomeDir = "liabolo";
    public static final String globalWorkspace = "workspace";


    public String getName();

    public String getUsername();

    public String getDbURI();

    public boolean connect(String username, String password) throws NoConnectionException, AuthenticationException;

    public boolean shutdown() throws ServiceNotAvailableException;

    public Collection search(String xpath) throws NoConnectionException;

    /**
     *
     * @param types shorts, which define on which metadata items the search should be processed
     * @see org.liabolo.common.MetaData
     * @param pattern pattern for searching(* are at the begin or ending allowed)
     * @return Collection found LibItems
     * @throws ServiceNotAvailableException
     */
    public Collection search(short[] types, String pattern, boolean workspace) throws ServiceNotAvailableException;

    /**
     * Search for all edited libitems which were modified since time specified by parameter 'since'
     * @param since long representing the time or null</br>
     * null defines, whether changes are made. This option makes only sense, if workspace items should checked.
     * @param place
     * @return Collection of LibItems
     * @throws ServiceNotAvailableException
     */
//    public Collection getChangedLibItems(Long since, String place) throws ServiceNotAvailableException;

   // public LibItem checkout(String signature);

    public boolean addLibItem(LibItem item, boolean workspace, boolean overwrite) throws ServiceNotAvailableException, XMLException;

    public MergingData editLibItem(LibItem item, boolean overwrite) throws MergingException;

    public boolean removeLibItem(LibItem libItem) throws ServiceNotAvailableException;

    public LibItem getLibItem(String id) throws ServiceNotAvailableException;

    public int countAllLibItemsFromType(String type, String pattern);

    public Collection getAllLibitemsFromBranch(String branch, boolean workspace) throws ServiceNotAvailableException;

    public Collection getAllBranches() throws ServiceNotAvailableException;

    public boolean addBranch(Branch branch) throws ServiceNotAvailableException;

    public boolean removeBranch(Branch branch) throws ServiceNotAvailableException;

    public boolean removeAllLibItemsFromBranch(Branch branch, boolean workspace) throws ServiceNotAvailableException;

    public boolean storeIndividualList(IndividualList newList) throws ServiceNotAvailableException;

    public boolean removeIndividualList(String listName) throws ServiceNotAvailableException;

    public Collection getAllIndividualLists() throws ServiceNotAvailableException;

    public IndividualList getIndividualList(String name) throws ServiceNotAvailableException;

    public boolean addLocation(Location location) throws ServiceNotAvailableException;

    public boolean removeLocation(Location location) throws ServiceNotAvailableException;

    public Collection getAllLibItemsFromLocation(Location loc) throws ServiceNotAvailableException;

    public Collection getAllLocations() throws ServiceNotAvailableException;

    public boolean addUser(User user) throws ServiceNotAvailableException;

    public boolean removeUser(User user) throws ServiceNotAvailableException;

    public boolean editUser(User user) throws ServiceNotAvailableException;

    public Collection getAllUsers() throws ServiceNotAvailableException;

    public Collection getGroups() throws ServiceNotAvailableException;

    public boolean backup(String backupDir, String rootCollection) throws ServiceNotAvailableException;

    public boolean restore(File content, String rootCollection) throws ServiceNotAvailableException;

    public ArrayList sort(short type, Collection col);
    
    public ArrayList sortSignature(Collection col);

    public ArrayList sortBranch(Collection col);

	public ArrayList sortLocation(Collection col);

	public ArrayList sortIndividualList(Collection col);

	public ArrayList sortConnections(Collection col);

    public ArrayList getMediaTypeItems(String mediaType) throws ServiceNotAvailableException;

    public List getAllMediaTypes() throws ServiceNotAvailableException;

    public boolean removeMediaType(String mediaType) throws ServiceNotAvailableException;

    public boolean addMediaType(String mediaType, TextItem[] items) throws ServiceNotAvailableException;


}