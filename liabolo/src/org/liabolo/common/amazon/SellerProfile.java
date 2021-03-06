/**
 * SellerProfile.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.liabolo.common.amazon;

public class SellerProfile  implements java.io.Serializable {
    private org.liabolo.common.amazon.SellerProfileDetails[] sellerProfileDetails;

    public SellerProfile() {
    }

    public org.liabolo.common.amazon.SellerProfileDetails[] getSellerProfileDetails() {
        return sellerProfileDetails;
    }

    public void setSellerProfileDetails(org.liabolo.common.amazon.SellerProfileDetails[] sellerProfileDetails) {
        this.sellerProfileDetails = sellerProfileDetails;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SellerProfile)) return false;
        SellerProfile other = (SellerProfile) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sellerProfileDetails==null && other.getSellerProfileDetails()==null) || 
             (this.sellerProfileDetails!=null &&
              java.util.Arrays.equals(this.sellerProfileDetails, other.getSellerProfileDetails())));
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
        if (getSellerProfileDetails() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSellerProfileDetails());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSellerProfileDetails(), i);
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
        new org.apache.axis.description.TypeDesc(SellerProfile.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.amazon.com", "SellerProfile"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sellerProfileDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SellerProfileDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://soap.amazon.com", "SellerProfileDetails"));
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
