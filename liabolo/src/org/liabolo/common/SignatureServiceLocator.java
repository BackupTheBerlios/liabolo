/**
 * SignatureServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.liabolo.common;

public class SignatureServiceLocator extends org.apache.axis.client.Service implements org.liabolo.common.SignatureService {

    // Use to get a proxy class for SignatureSender
    private final java.lang.String SignatureSender_address = "http://localhost:8091/exist/services/SignatureSender";

    public java.lang.String getSignatureSenderAddress() {
        return SignatureSender_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SignatureSenderWSDDServiceName = "SignatureSender";

    public java.lang.String getSignatureSenderWSDDServiceName() {
        return SignatureSenderWSDDServiceName;
    }

    public void setSignatureSenderWSDDServiceName(java.lang.String name) {
        SignatureSenderWSDDServiceName = name;
    }

    public org.liabolo.common.SignatureSender getSignatureSender() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SignatureSender_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSignatureSender(endpoint);
    }

    public org.liabolo.common.SignatureSender getSignatureSender(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.liabolo.common.SignatureSoapBindingStub _stub = new org.liabolo.common.SignatureSoapBindingStub(portAddress, this);
            _stub.setPortName(getSignatureSenderWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.liabolo.common.SignatureSender.class.isAssignableFrom(serviceEndpointInterface)) {
                org.liabolo.common.SignatureSoapBindingStub _stub = new org.liabolo.common.SignatureSoapBindingStub(new java.net.URL(SignatureSender_address), this);
                _stub.setPortName(getSignatureSenderWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("SignatureSender".equals(inputPortName)) {
            return getSignatureSender();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:common.liabolo.org", "SignatureService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("SignatureSender"));
        }
        return ports.iterator();
    }

}
