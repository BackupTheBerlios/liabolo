
/*
 * Created on 15.01.2004 by Thorsten Schlörmann
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


public class Signature {


	String url= "";
	String signatureString = "";
	String branch;


	public Signature(String branch, String signature, String url){
		this.signatureString = signature;
		this.branch = branch;
		url = url.replaceFirst("^.*://", "");

		if(url == null || url.equals(""))
			this.url = Configurator.getProperty("localURLAlias", "localhost", "general");
		else
			this.url = url;

	}

	public String getFullyQualifiedSignature() {
		return this.branch + "_" + this.signatureString + "@" +this.url;

	}

	public String getLocalSignature() {
		return this.branch + "_" + this.signatureString;

	}
	
	public String getSignatureNumber(){
		return this.signatureString;
	}

	public String getUrl(){
		return url;
	}
}
