/**
 * AmazonSearchPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.liabolo.common.amazon;

public interface AmazonSearchPort extends java.rmi.Remote {
    public org.liabolo.common.amazon.ProductInfo keywordSearchRequest(org.liabolo.common.amazon.KeywordRequest keywordSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ProductInfo textStreamSearchRequest(org.liabolo.common.amazon.TextStreamRequest textStreamSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ProductInfo powerSearchRequest(org.liabolo.common.amazon.PowerRequest powerSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ProductInfo browseNodeSearchRequest(org.liabolo.common.amazon.BrowseNodeRequest browseNodeSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ProductInfo asinSearchRequest(org.liabolo.common.amazon.AsinRequest asinSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ProductLine[] blendedSearchRequest(org.liabolo.common.amazon.BlendedRequest blendedSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ProductInfo upcSearchRequest(org.liabolo.common.amazon.UpcRequest upcSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ProductInfo skuSearchRequest(org.liabolo.common.amazon.SkuRequest skuSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ProductInfo authorSearchRequest(org.liabolo.common.amazon.AuthorRequest authorSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ProductInfo artistSearchRequest(org.liabolo.common.amazon.ArtistRequest artistSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ProductInfo actorSearchRequest(org.liabolo.common.amazon.ActorRequest actorSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ProductInfo manufacturerSearchRequest(org.liabolo.common.amazon.ManufacturerRequest manufacturerSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ProductInfo directorSearchRequest(org.liabolo.common.amazon.DirectorRequest directorSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ProductInfo listManiaSearchRequest(org.liabolo.common.amazon.ListManiaRequest listManiaSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ProductInfo wishlistSearchRequest(org.liabolo.common.amazon.WishlistRequest wishlistSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ListingProductDetails exchangeSearchRequest(org.liabolo.common.amazon.ExchangeRequest exchangeSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.MarketplaceSearch marketplaceSearchRequest(org.liabolo.common.amazon.MarketplaceRequest marketplaceSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.SellerProfile sellerProfileSearchRequest(org.liabolo.common.amazon.SellerProfileRequest sellerProfileSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.SellerSearch sellerSearchRequest(org.liabolo.common.amazon.SellerRequest sellerSearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ProductInfo similaritySearchRequest(org.liabolo.common.amazon.SimilarityRequest similaritySearchRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ShoppingCart getShoppingCartRequest(org.liabolo.common.amazon.GetShoppingCartRequest getShoppingCartRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ShoppingCart clearShoppingCartRequest(org.liabolo.common.amazon.ClearShoppingCartRequest clearShoppingCartRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ShoppingCart addShoppingCartItemsRequest(org.liabolo.common.amazon.AddShoppingCartItemsRequest addShoppingCartItemsRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ShoppingCart removeShoppingCartItemsRequest(org.liabolo.common.amazon.RemoveShoppingCartItemsRequest removeShoppingCartItemsRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.ShoppingCart modifyShoppingCartItemsRequest(org.liabolo.common.amazon.ModifyShoppingCartItemsRequest modifyShoppingCartItemsRequest) throws java.rmi.RemoteException;
    public org.liabolo.common.amazon.GetTransactionDetailsResponse getTransactionDetailsRequest(org.liabolo.common.amazon.GetTransactionDetailsRequest getTransactionDetailsRequest) throws java.rmi.RemoteException;
}
