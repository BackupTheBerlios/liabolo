/**
 * SignatureSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.liabolo.common;

public class SignatureSoapBindingSkeleton implements org.liabolo.common.SignatureSender, org.apache.axis.wsdl.Skeleton {
    private org.liabolo.common.SignatureSender impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getSignature", _params, new javax.xml.namespace.QName("", "signatueString"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:common.liabolo.org", "getSignature"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getSignature") == null) {
            _myOperations.put("getSignature", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getSignature")).add(_oper);
    }

    public SignatureSoapBindingSkeleton() {
        this.impl = new org.liabolo.common.SignatureSoapBindingImpl();
    }

    public SignatureSoapBindingSkeleton(org.liabolo.common.SignatureSender impl) {
        this.impl = impl;
    }
    public java.lang.String getSignature() throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getSignature();
        return ret;
    }

}
