/**
 * AmazonSearchBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.liabolo.common.amazon;

public class AmazonSearchBindingStub extends org.apache.axis.client.Stub implements org.liabolo.common.amazon.AmazonSearchPort {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[26];
        org.apache.axis.description.OperationDesc oper;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("KeywordSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "KeywordSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "KeywordRequest"), org.liabolo.common.amazon.KeywordRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ProductInfo"));
        oper.setReturnClass(org.liabolo.common.amazon.ProductInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("TextStreamSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "TextStreamSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "TextStreamRequest"), org.liabolo.common.amazon.TextStreamRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ProductInfo"));
        oper.setReturnClass(org.liabolo.common.amazon.ProductInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PowerSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "PowerSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "PowerRequest"), org.liabolo.common.amazon.PowerRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ProductInfo"));
        oper.setReturnClass(org.liabolo.common.amazon.ProductInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("BrowseNodeSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "BrowseNodeSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "BrowseNodeRequest"), org.liabolo.common.amazon.BrowseNodeRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ProductInfo"));
        oper.setReturnClass(org.liabolo.common.amazon.ProductInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("AsinSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "AsinSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "AsinRequest"), org.liabolo.common.amazon.AsinRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ProductInfo"));
        oper.setReturnClass(org.liabolo.common.amazon.ProductInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("BlendedSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "BlendedSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "BlendedRequest"), org.liabolo.common.amazon.BlendedRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ProductLineArray"));
        oper.setReturnClass(org.liabolo.common.amazon.ProductLine[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("UpcSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "UpcSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "UpcRequest"), org.liabolo.common.amazon.UpcRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ProductInfo"));
        oper.setReturnClass(org.liabolo.common.amazon.ProductInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SkuSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "SkuSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "SkuRequest"), org.liabolo.common.amazon.SkuRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ProductInfo"));
        oper.setReturnClass(org.liabolo.common.amazon.ProductInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("AuthorSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "AuthorSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "AuthorRequest"), org.liabolo.common.amazon.AuthorRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ProductInfo"));
        oper.setReturnClass(org.liabolo.common.amazon.ProductInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ArtistSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "ArtistSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "ArtistRequest"), org.liabolo.common.amazon.ArtistRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ProductInfo"));
        oper.setReturnClass(org.liabolo.common.amazon.ProductInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[9] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ActorSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "ActorSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "ActorRequest"), org.liabolo.common.amazon.ActorRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ProductInfo"));
        oper.setReturnClass(org.liabolo.common.amazon.ProductInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ManufacturerSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "ManufacturerSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "ManufacturerRequest"), org.liabolo.common.amazon.ManufacturerRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ProductInfo"));
        oper.setReturnClass(org.liabolo.common.amazon.ProductInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("DirectorSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "DirectorSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "DirectorRequest"), org.liabolo.common.amazon.DirectorRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ProductInfo"));
        oper.setReturnClass(org.liabolo.common.amazon.ProductInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ExchangeSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "ExchangeSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "ExchangeRequest"), org.liabolo.common.amazon.ExchangeRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ListingProductDetails"));
        oper.setReturnClass(org.liabolo.common.amazon.ListingProductDetails.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ListManiaSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "ListManiaSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "ListManiaRequest"), org.liabolo.common.amazon.ListManiaRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ProductInfo"));
        oper.setReturnClass(org.liabolo.common.amazon.ProductInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("WishlistSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "WishlistSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "WishlistRequest"), org.liabolo.common.amazon.WishlistRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ProductInfo"));
        oper.setReturnClass(org.liabolo.common.amazon.ProductInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SellerProfileSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "SellerProfileSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "SellerProfileRequest"), org.liabolo.common.amazon.SellerProfileRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "SellerProfile"));
        oper.setReturnClass(org.liabolo.common.amazon.SellerProfile.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SellerSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "SellerSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "SellerRequest"), org.liabolo.common.amazon.SellerRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "SellerSearch"));
        oper.setReturnClass(org.liabolo.common.amazon.SellerSearch.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("MarketplaceSearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "MarketplaceSearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "MarketplaceRequest"), org.liabolo.common.amazon.MarketplaceRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "MarketplaceSearch"));
        oper.setReturnClass(org.liabolo.common.amazon.MarketplaceSearch.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SimilaritySearchRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "SimilaritySearchRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "SimilarityRequest"), org.liabolo.common.amazon.SimilarityRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ProductInfo"));
        oper.setReturnClass(org.liabolo.common.amazon.ProductInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[19] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetShoppingCartRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "GetShoppingCartRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "GetShoppingCartRequest"), org.liabolo.common.amazon.GetShoppingCartRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ShoppingCart"));
        oper.setReturnClass(org.liabolo.common.amazon.ShoppingCart.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "ShoppingCart"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ClearShoppingCartRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "ClearShoppingCartRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "ClearShoppingCartRequest"), org.liabolo.common.amazon.ClearShoppingCartRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ShoppingCart"));
        oper.setReturnClass(org.liabolo.common.amazon.ShoppingCart.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "ShoppingCart"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("AddShoppingCartItemsRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "AddShoppingCartItemsRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "AddShoppingCartItemsRequest"), org.liabolo.common.amazon.AddShoppingCartItemsRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ShoppingCart"));
        oper.setReturnClass(org.liabolo.common.amazon.ShoppingCart.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "ShoppingCart"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("RemoveShoppingCartItemsRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "RemoveShoppingCartItemsRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "RemoveShoppingCartItemsRequest"), org.liabolo.common.amazon.RemoveShoppingCartItemsRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ShoppingCart"));
        oper.setReturnClass(org.liabolo.common.amazon.ShoppingCart.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "ShoppingCart"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ModifyShoppingCartItemsRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "ModifyShoppingCartItemsRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "ModifyShoppingCartItemsRequest"), org.liabolo.common.amazon.ModifyShoppingCartItemsRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "ShoppingCart"));
        oper.setReturnClass(org.liabolo.common.amazon.ShoppingCart.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "ShoppingCart"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetTransactionDetailsRequest");
        oper.addParameter(new javax.xml.namespace.QName("", "GetTransactionDetailsRequest"), new javax.xml.namespace.QName("http://soap.amazon.com", "GetTransactionDetailsRequest"), org.liabolo.common.amazon.GetTransactionDetailsRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.amazon.com", "GetTransactionDetailsResponse"));
        oper.setReturnClass(org.liabolo.common.amazon.GetTransactionDetailsResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "GetTransactionDetailsResponse"));
        oper.setStyle(org.apache.axis.enum.Style.RPC);
        oper.setUse(org.apache.axis.enum.Use.ENCODED);
        _operations[25] = oper;

    }

    public AmazonSearchBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public AmazonSearchBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public AmazonSearchBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "FeedbackArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.Feedback[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "RemoveShoppingCartItemsRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.RemoveShoppingCartItemsRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "FeaturedProductsArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.FeaturedProduct[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ItemQuantity");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ItemQuantity.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "AuthorArray");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "TrackArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.Track[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "Package");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon._package.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "TextStreamRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.TextStreamRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ThirdPartyProductDetails");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ThirdPartyProductDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ManufacturerRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ManufacturerRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "CustomerReview");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.CustomerReview.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "SimilarProductsArray");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ClearShoppingCartRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ClearShoppingCartRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "PlatformArray");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ItemIdArray");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "KeyPhrase");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.KeyPhrase.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "SellerProfileDetailsArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.SellerProfileDetails[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "SellerProfile");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.SellerProfile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ThirdPartyProductDetailsArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ThirdPartyProductDetails[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ItemArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.Item[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ShoppingCart");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ShoppingCart.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "MarketplaceSearch");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.MarketplaceSearch.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "BrowseNode");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.BrowseNode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "PackageArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon._package[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "Details");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.Details.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ProductLineArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ProductLine[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ListManiaRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ListManiaRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "DetailsArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.Details[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "Variation");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.Variation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ShortSummaryArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ShortSummary[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "AddShoppingCartItemsRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.AddShoppingCartItemsRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ProductLine");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ProductLine.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "BrowseNodeArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.BrowseNode[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "WishlistRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.WishlistRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ArtistRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ArtistRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "SellerSearchDetails");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.SellerSearchDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "MarketplaceSearchDetailsArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.MarketplaceSearchDetails[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "BrowseNodeRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.BrowseNodeRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ListingProductDetails");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ListingProductDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "AuthorRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.AuthorRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ArtistArray");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "SellerProfileDetails");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.SellerProfileDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "Track");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.Track.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "FeaturedProduct");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.FeaturedProduct.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "SellerRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.SellerRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "GetTransactionDetailsRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.GetTransactionDetailsRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "AccessoryArray");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ListingProductDetailsArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ListingProductDetails[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "SimilarityRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.SimilarityRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ProductInfo");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ProductInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "AsinRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.AsinRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "OrderItem");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.OrderItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "CustomerReviewArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.CustomerReview[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "OrderItemArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.OrderItem[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ThirdPartyProductInfo");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ThirdPartyProductInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ModifyShoppingCartItemsRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ModifyShoppingCartItemsRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "AddItemArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.AddItem[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ExchangeRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ExchangeRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "KeyPhraseArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.KeyPhrase[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "PowerRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.PowerRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "SellerSearch");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.SellerSearch.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "SellerFeedback");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.SellerFeedback.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "GetTransactionDetailsResponse");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.GetTransactionDetailsResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ItemQuantityArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ItemQuantity[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "SellerProfileRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.SellerProfileRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "GetShoppingCartRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.GetShoppingCartRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ListArray");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "AddItem");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.AddItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "Item");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.Item.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "DirectorArray");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "SellerSearchDetailsArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.SellerSearchDetails[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "DirectorRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.DirectorRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "Feedback");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.Feedback.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "MarketplaceRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.MarketplaceRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ActorRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ActorRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "Reviews");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.Reviews.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "StarringArray");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ShortSummary");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ShortSummary.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "Price");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.Price.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "MarketplaceSearchDetails");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.MarketplaceSearchDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "FeaturesArray");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "VariationArray");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.Variation[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "OrderIdArray");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(arraysf);
            cachedDeserFactories.add(arraydf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "BlendedRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.BlendedRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "ListingProductInfo");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.ListingProductInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "KeywordRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.KeywordRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "UpcRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.UpcRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.amazon.com", "SkuRequest");
            cachedSerQNames.add(qName);
            cls = org.liabolo.common.amazon.SkuRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    private org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call =
                    (org.apache.axis.client.Call) super.service.createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                        java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                        _call.registerTypeMapping(cls, qName, sf, df, false);
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", t);
        }
    }

    public org.liabolo.common.amazon.ProductInfo keywordSearchRequest(org.liabolo.common.amazon.KeywordRequest keywordSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "KeywordSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {keywordSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ProductInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ProductInfo) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ProductInfo.class);
            }
        }
    }

    public org.liabolo.common.amazon.ProductInfo textStreamSearchRequest(org.liabolo.common.amazon.TextStreamRequest textStreamSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "TextStreamSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {textStreamSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ProductInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ProductInfo) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ProductInfo.class);
            }
        }
    }

    public org.liabolo.common.amazon.ProductInfo powerSearchRequest(org.liabolo.common.amazon.PowerRequest powerSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "PowerSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {powerSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ProductInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ProductInfo) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ProductInfo.class);
            }
        }
    }

    public org.liabolo.common.amazon.ProductInfo browseNodeSearchRequest(org.liabolo.common.amazon.BrowseNodeRequest browseNodeSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "BrowseNodeSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {browseNodeSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ProductInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ProductInfo) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ProductInfo.class);
            }
        }
    }

    public org.liabolo.common.amazon.ProductInfo asinSearchRequest(org.liabolo.common.amazon.AsinRequest asinSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "AsinSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {asinSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ProductInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ProductInfo) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ProductInfo.class);
            }
        }
    }

    public org.liabolo.common.amazon.ProductLine[] blendedSearchRequest(org.liabolo.common.amazon.BlendedRequest blendedSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "BlendedSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {blendedSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ProductLine[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ProductLine[]) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ProductLine[].class);
            }
        }
    }

    public org.liabolo.common.amazon.ProductInfo upcSearchRequest(org.liabolo.common.amazon.UpcRequest upcSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "UpcSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {upcSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ProductInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ProductInfo) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ProductInfo.class);
            }
        }
    }

    public org.liabolo.common.amazon.ProductInfo skuSearchRequest(org.liabolo.common.amazon.SkuRequest skuSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "SkuSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {skuSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ProductInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ProductInfo) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ProductInfo.class);
            }
        }
    }

    public org.liabolo.common.amazon.ProductInfo authorSearchRequest(org.liabolo.common.amazon.AuthorRequest authorSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "AuthorSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {authorSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ProductInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ProductInfo) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ProductInfo.class);
            }
        }
    }

    public org.liabolo.common.amazon.ProductInfo artistSearchRequest(org.liabolo.common.amazon.ArtistRequest artistSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "ArtistSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {artistSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ProductInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ProductInfo) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ProductInfo.class);
            }
        }
    }

    public org.liabolo.common.amazon.ProductInfo actorSearchRequest(org.liabolo.common.amazon.ActorRequest actorSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "ActorSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {actorSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ProductInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ProductInfo) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ProductInfo.class);
            }
        }
    }

    public org.liabolo.common.amazon.ProductInfo manufacturerSearchRequest(org.liabolo.common.amazon.ManufacturerRequest manufacturerSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "ManufacturerSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {manufacturerSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ProductInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ProductInfo) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ProductInfo.class);
            }
        }
    }

    public org.liabolo.common.amazon.ProductInfo directorSearchRequest(org.liabolo.common.amazon.DirectorRequest directorSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "DirectorSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {directorSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ProductInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ProductInfo) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ProductInfo.class);
            }
        }
    }

    public org.liabolo.common.amazon.ListingProductDetails exchangeSearchRequest(org.liabolo.common.amazon.ExchangeRequest exchangeSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "ExchangeSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {exchangeSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ListingProductDetails) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ListingProductDetails) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ListingProductDetails.class);
            }
        }
    }

    public org.liabolo.common.amazon.ProductInfo listManiaSearchRequest(org.liabolo.common.amazon.ListManiaRequest listManiaSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "ListManiaSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {listManiaSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ProductInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ProductInfo) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ProductInfo.class);
            }
        }
    }

    public org.liabolo.common.amazon.ProductInfo wishlistSearchRequest(org.liabolo.common.amazon.WishlistRequest wishlistSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "WishlistSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {wishlistSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ProductInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ProductInfo) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ProductInfo.class);
            }
        }
    }

    public org.liabolo.common.amazon.SellerProfile sellerProfileSearchRequest(org.liabolo.common.amazon.SellerProfileRequest sellerProfileSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "SellerProfileSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sellerProfileSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.SellerProfile) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.SellerProfile) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.SellerProfile.class);
            }
        }
    }

    public org.liabolo.common.amazon.SellerSearch sellerSearchRequest(org.liabolo.common.amazon.SellerRequest sellerSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "SellerSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sellerSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.SellerSearch) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.SellerSearch) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.SellerSearch.class);
            }
        }
    }

    public org.liabolo.common.amazon.MarketplaceSearch marketplaceSearchRequest(org.liabolo.common.amazon.MarketplaceRequest marketplaceSearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "MarketplaceSearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {marketplaceSearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.MarketplaceSearch) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.MarketplaceSearch) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.MarketplaceSearch.class);
            }
        }
    }

    public org.liabolo.common.amazon.ProductInfo similaritySearchRequest(org.liabolo.common.amazon.SimilarityRequest similaritySearchRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "SimilaritySearchRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {similaritySearchRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ProductInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ProductInfo) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ProductInfo.class);
            }
        }
    }

    public org.liabolo.common.amazon.ShoppingCart getShoppingCartRequest(org.liabolo.common.amazon.GetShoppingCartRequest getShoppingCartRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "GetShoppingCartRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {getShoppingCartRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ShoppingCart) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ShoppingCart) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ShoppingCart.class);
            }
        }
    }

    public org.liabolo.common.amazon.ShoppingCart clearShoppingCartRequest(org.liabolo.common.amazon.ClearShoppingCartRequest clearShoppingCartRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[21]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "ClearShoppingCartRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {clearShoppingCartRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ShoppingCart) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ShoppingCart) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ShoppingCart.class);
            }
        }
    }

    public org.liabolo.common.amazon.ShoppingCart addShoppingCartItemsRequest(org.liabolo.common.amazon.AddShoppingCartItemsRequest addShoppingCartItemsRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[22]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "AddShoppingCartItemsRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {addShoppingCartItemsRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ShoppingCart) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ShoppingCart) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ShoppingCart.class);
            }
        }
    }

    public org.liabolo.common.amazon.ShoppingCart removeShoppingCartItemsRequest(org.liabolo.common.amazon.RemoveShoppingCartItemsRequest removeShoppingCartItemsRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[23]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "RemoveShoppingCartItemsRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {removeShoppingCartItemsRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ShoppingCart) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ShoppingCart) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ShoppingCart.class);
            }
        }
    }

    public org.liabolo.common.amazon.ShoppingCart modifyShoppingCartItemsRequest(org.liabolo.common.amazon.ModifyShoppingCartItemsRequest modifyShoppingCartItemsRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[24]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "ModifyShoppingCartItemsRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {modifyShoppingCartItemsRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.ShoppingCart) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.ShoppingCart) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.ShoppingCart.class);
            }
        }
    }

    public org.liabolo.common.amazon.GetTransactionDetailsResponse getTransactionDetailsRequest(org.liabolo.common.amazon.GetTransactionDetailsRequest getTransactionDetailsRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[25]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://soap.amazon.com");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.amazon.com", "GetTransactionDetailsRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {getTransactionDetailsRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.liabolo.common.amazon.GetTransactionDetailsResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.liabolo.common.amazon.GetTransactionDetailsResponse) org.apache.axis.utils.JavaUtils.convert(_resp, org.liabolo.common.amazon.GetTransactionDetailsResponse.class);
            }
        }
    }

}
