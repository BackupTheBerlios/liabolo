/**
 * Variation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.liabolo.common.amazon;

public class Variation  implements java.io.Serializable {
    private java.lang.String asin;
    private java.lang.String clothingSize;
    private java.lang.String clothingColor;
    private java.lang.String price;
    private java.lang.String salePrice;
    private java.lang.String availability;
    private java.lang.String multiMerchant;
    private java.lang.String merchantSku;

    public Variation() {
    }

    public java.lang.String getAsin() {
        return asin;
    }

    public void setAsin(java.lang.String asin) {
        this.asin = asin;
    }

    public java.lang.String getClothingSize() {
        return clothingSize;
    }

    public void setClothingSize(java.lang.String clothingSize) {
        this.clothingSize = clothingSize;
    }

    public java.lang.String getClothingColor() {
        return clothingColor;
    }

    public void setClothingColor(java.lang.String clothingColor) {
        this.clothingColor = clothingColor;
    }

    public java.lang.String getPrice() {
        return price;
    }

    public void setPrice(java.lang.String price) {
        this.price = price;
    }

    public java.lang.String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(java.lang.String salePrice) {
        this.salePrice = salePrice;
    }

    public java.lang.String getAvailability() {
        return availability;
    }

    public void setAvailability(java.lang.String availability) {
        this.availability = availability;
    }

    public java.lang.String getMultiMerchant() {
        return multiMerchant;
    }

    public void setMultiMerchant(java.lang.String multiMerchant) {
        this.multiMerchant = multiMerchant;
    }

    public java.lang.String getMerchantSku() {
        return merchantSku;
    }

    public void setMerchantSku(java.lang.String merchantSku) {
        this.merchantSku = merchantSku;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Variation)) return false;
        Variation other = (Variation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.asin==null && other.getAsin()==null) || 
             (this.asin!=null &&
              this.asin.equals(other.getAsin()))) &&
            ((this.clothingSize==null && other.getClothingSize()==null) || 
             (this.clothingSize!=null &&
              this.clothingSize.equals(other.getClothingSize()))) &&
            ((this.clothingColor==null && other.getClothingColor()==null) || 
             (this.clothingColor!=null &&
              this.clothingColor.equals(other.getClothingColor()))) &&
            ((this.price==null && other.getPrice()==null) || 
             (this.price!=null &&
              this.price.equals(other.getPrice()))) &&
            ((this.salePrice==null && other.getSalePrice()==null) || 
             (this.salePrice!=null &&
              this.salePrice.equals(other.getSalePrice()))) &&
            ((this.availability==null && other.getAvailability()==null) || 
             (this.availability!=null &&
              this.availability.equals(other.getAvailability()))) &&
            ((this.multiMerchant==null && other.getMultiMerchant()==null) || 
             (this.multiMerchant!=null &&
              this.multiMerchant.equals(other.getMultiMerchant()))) &&
            ((this.merchantSku==null && other.getMerchantSku()==null) || 
             (this.merchantSku!=null &&
              this.merchantSku.equals(other.getMerchantSku())));
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
        if (getAsin() != null) {
            _hashCode += getAsin().hashCode();
        }
        if (getClothingSize() != null) {
            _hashCode += getClothingSize().hashCode();
        }
        if (getClothingColor() != null) {
            _hashCode += getClothingColor().hashCode();
        }
        if (getPrice() != null) {
            _hashCode += getPrice().hashCode();
        }
        if (getSalePrice() != null) {
            _hashCode += getSalePrice().hashCode();
        }
        if (getAvailability() != null) {
            _hashCode += getAvailability().hashCode();
        }
        if (getMultiMerchant() != null) {
            _hashCode += getMultiMerchant().hashCode();
        }
        if (getMerchantSku() != null) {
            _hashCode += getMerchantSku().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Variation.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.amazon.com", "Variation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Asin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clothingSize");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ClothingSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clothingColor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ClothingColor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("price");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Price"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salePrice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SalePrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("availability");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Availability"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("multiMerchant");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MultiMerchant"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("merchantSku");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MerchantSku"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
