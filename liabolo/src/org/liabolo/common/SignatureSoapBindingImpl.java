/**
 * SignatureSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.liabolo.common;

public class SignatureSoapBindingImpl implements org.liabolo.common.SignatureSender{
    public java.lang.String getSignature() throws java.rmi.RemoteException {
    	
    	System.setProperty("liabolo.config","/home/p30/server/Liabolo/config");
    	Configurator.config();
    	Signature signature = SignatureGenerator.setSignature("","");
    	
        return signature.getSignatureNumber();
 
    }

}
