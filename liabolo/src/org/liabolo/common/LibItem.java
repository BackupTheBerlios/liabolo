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

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

public class LibItem {

    private Location loc;
    private File file;
    private MetaData meta;


    /**
     *
     */
    public LibItem() {
    }
/*
    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
*/

    public MetaData getMetaData() {
 //       if(meta==null)
 //           meta = MetaData.createNewMetaData();

        return meta;
    }

    public void setMetaData(MetaData meta) {
        this.meta = meta;
        //set branch to 'nav' (not available) if none is set

        if (meta.getLiabolo_branch().equals(" "))
            meta.setLiabolo_branch("nav");

        // set signature including branch

        if (meta.getLiabolo_signature().equals(" ")) {
System.out.println("SIGNATURE IST LEER. HIER MUSS WAS GEMACHT WREDEN!!!");

            Signature s = null;
            s = SignatureGenerator.setSignature(meta.getLiabolo_branch(), "LEER");


//TODO        if (s == null) // Fehlerbehandlung
        	meta.setLiabolo_signature(s);
        }
    }

}
