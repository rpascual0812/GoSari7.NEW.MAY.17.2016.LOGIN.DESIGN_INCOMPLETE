package com.android.iit.chrs.gosari;

/**
 * Created by greg on 5/10/16.
 */
public class ItemCart {


    int id;
    String Cartpk;
    String Cartpk_Categories;
    String CartItems;
    String CartDescription;
    int CartPrice;
    int CartCount;
    String CartDeliveryTime;
    String CartDate;

    public ItemCart(){}

    public ItemCart(int id,String Cartpk,String CartItems,String CartDescription,int CartPrice,int CartCount,String CartDeliveryTime,String CartDate){

        this.id=id;
        this.Cartpk=Cartpk;
        this.CartItems=CartItems;
        this.CartDescription=CartDescription;
        this.CartPrice=CartPrice;
        this.CartCount=CartCount;
        this.CartDeliveryTime=CartDeliveryTime;
        this.CartDate=CartDate;
    }

    public ItemCart(String Cartpk_Categories,String Cartpk,String CartItems,String CartDescription,int CartPrice,int CartCount,String CartDeliveryTime,String CartDate){

        super();
        this.Cartpk=Cartpk;
        this.Cartpk_Categories=Cartpk_Categories;
        this.CartItems=CartItems;
        this.CartDescription=CartDescription;
        this.CartPrice=CartPrice;
        this.CartCount=CartCount;
        this.CartDeliveryTime=CartDeliveryTime;
        this.CartDate=CartDate;
    }

    public String getCartpk() {
        return Cartpk;
    }

    public void setCartpk(String cartpk) {
        Cartpk = cartpk;
    }

    public String getCartItems() {
        return CartItems;
    }

    public void setCartItems(String cartItems) {
        CartItems = cartItems;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCartDescription() {
        return CartDescription;
    }

    public void setCartDescription(String cartDescription) {
        CartDescription = cartDescription;
    }

    public int getCartPrice() {
        return CartPrice;
    }

    public void setCartPrice(int cartPrice) {
        CartPrice = cartPrice;
    }

    public int getCartCount() {
        return CartCount;
    }

    public void setCartCount(int cartCount) {
        CartCount = cartCount;
    }

    public String getCartDeliveryTime() {
        return CartDeliveryTime;
    }

    public void setCartDeliveryTime(String cartDeliveryTime) {
        CartDeliveryTime = cartDeliveryTime;
    }

    public String getCartpk_Categories() {
        return Cartpk_Categories;
    }

    public void setCartpk_Categories(String cartpk_Categories) {
        Cartpk_Categories = cartpk_Categories;
    }

    public String getCartDate () {
        return CartDate;
    }

    public void setCartDate (String cartDate) {
        CartDate = cartDate;
    }


}