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

package org.liabolo.common;

import org.liabolo.exception.ServiceNotAvailableException;

public class Connection {

    private String name = "";
	private String dbURI = "";
    private String username = "";
    private String password = "";
    private String driver = "org.exist.xmldb.DatabaseImpl";
	private boolean active = true;

    /**
     * Contructor for exist-db connections</br>
     * The driver is set to the default value 'org.exist.xmldb.DatabaseImpl'</br>
     * If another db should be used, take the default contructor or additionally call the method 'setDriver'
     * @param dbURI
     * @param username
     * @param password
     * @param driver
     */
    public Connection(String name, String dbURI, String username, String password, String driver, boolean active) {
        this.name = name;
        this.dbURI = dbURI;
        this.username = username;
        this.password = password;
        this.driver = driver;
        this.active = active;
    }

    public Connection(){}

    public String getDbURI() {
        return dbURI;
    }

    public void setDbURI(String dbURI) {
        this.dbURI = dbURI;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param b
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

}
