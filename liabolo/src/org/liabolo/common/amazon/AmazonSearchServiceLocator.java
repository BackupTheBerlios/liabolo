/**
 * AmazonSearchServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.liabolo.common.amazon;

public class AmazonSearchServiceLocator extends org.apache.axis.client.Service implements org.liabolo.common.amazon.AmazonSearchService {

    // Use to get a proxy class for AmazonSearchPort
    private final java.lang.String AmazonSearchPort_address = "http://soap.amazon.com/onca/soap3";

    public java.lang.String getAmazonSearchPortAddress() {
        return AmazonSearchPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AmazonSearchPortWSDDServiceName = "AmazonSearchPort";

    public java.lang.String getAmazonSearchPortWSDDServiceName() {
        return AmazonSearchPortWSDDServiceName;
    }

    public void setAmazonSearchPortWSDDServiceName(java.lang.String name) {
        AmazonSearchPortWSDDServiceName = name;
    }

    public org.liabolo.common.amazon.AmazonSearchPort getAmazonSearchPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AmazonSearchPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAmazonSearchPort(endpoint);
    }

    public org.liabolo.common.amazon.AmazonSearchPort getAmazonSearchPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.liabolo.common.amazon.AmazonSearchBindingStub _stub = new org.liabolo.common.amazon.AmazonSearchBindingStub(portAddress, this);
            _stub.setPortName(getAmazonSearchPortWSDDServiceName());
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
            if (org.liabolo.common.amazon.AmazonSearchPort.class.isAssignableFrom(serviceEndpointInterface)) {
                org.liabolo.common.amazon.AmazonSearchBindingStub _stub = new org.liabolo.common.amazon.AmazonSearchBindingStub(new java.net.URL(AmazonSearchPort_address), this);
                _stub.setPortName(getAmazonSearchPortWSDDServiceName());
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
        if ("AmazonSearchPort".equals(inputPortName)) {
            return getAmazonSearchPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://soap.amazon.com", "AmazonSearchService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("AmazonSearchPort"));
        }
        return ports.iterator();
    }

}
