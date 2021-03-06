/**
 * GetTransactionDetailsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.liabolo.common.amazon;

public class GetTransactionDetailsResponse  implements java.io.Serializable {
    private org.liabolo.common.amazon.ShortSummary[] shortSummaries;

    public GetTransactionDetailsResponse() {
    }

    public org.liabolo.common.amazon.ShortSummary[] getShortSummaries() {
        return shortSummaries;
    }

    public void setShortSummaries(org.liabolo.common.amazon.ShortSummary[] shortSummaries) {
        this.shortSummaries = shortSummaries;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetTransactionDetailsResponse)) return false;
        GetTransactionDetailsResponse other = (GetTransactionDetailsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.shortSummaries==null && other.getShortSummaries()==null) || 
             (this.shortSummaries!=null &&
              java.util.Arrays.equals(this.shortSummaries, other.getShortSummaries())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getShortSummaries() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getShortSummaries());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getShortSummaries(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetTransactionDetailsResponse.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.amazon.com", "GetTransactionDetailsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shortSummaries");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ShortSummaries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.amazon.com", "ShortSummary"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
