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


public class User {
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * User with only read permissions on all Collections but system
     */
    public static final String GUEST_USER = "guest";
    /**
     * User with read and write permissions on all Collections but system
     */
    public static final String DEFAULT_USER = "liabolo";
    /**
     * User with read and write permissions on all Collections also system
     */
    public static final String DBA_USER = "dba";

    private String name;
    private String passwd;
    private String group;
    private String home;

    public User() {
        log.debug("All attributes get their default values", 5);
        this.name = "";
        this.passwd = "";
        this.group = "";
        this.home = "";
    }

    public User(String name, String passwd, String group, String home) {
        log.debug("Value name =" + name, 5);
        log.debug("Value passwd =" + passwd, 5);
        log.debug("Value group =" + group, 5);
        log.debug("Value home =" + home, 5);
        this.name = name;
        this.passwd = passwd;
        this.group = group;
        this.home = home;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }
}
