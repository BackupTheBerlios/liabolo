/**
 * SignatureService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.liabolo.common;

public interface SignatureService extends javax.xml.rpc.Service {
    public java.lang.String getSignatureSenderAddress();

    public org.liabolo.common.SignatureSender getSignatureSender() throws javax.xml.rpc.ServiceException;

    public org.liabolo.common.SignatureSender getSignatureSender(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
