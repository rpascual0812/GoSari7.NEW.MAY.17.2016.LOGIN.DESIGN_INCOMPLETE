package com.android.iit.chrs.gosari;

/**
 * Created by greg on 5/10/16.
 */
public class ItemCheckout {

    public int chkout_id;
    public String chkout_itempk;
    public String chkout_categpk;
    public String chkout_item;
    public int chkout_price;
    public int chkout_count;
    public String chkout_time;
    public String chkout_datereceived;

    public ItemCheckout(){

    }

    public ItemCheckout (int chkout_id, String chkout_itempk, String chkout_categpk, String chkout_item, int chkout_price, int chkout_count, String chkout_time,String chkout_datereceived) {
        this.chkout_id = chkout_id;
        this.chkout_itempk = chkout_itempk;
        this.chkout_categpk = chkout_categpk;
        this.chkout_item = chkout_item;
        this.chkout_price = chkout_price;
        this.chkout_count = chkout_count;
        this.chkout_time = chkout_time;
        this.chkout_datereceived=chkout_datereceived;
    }

    public ItemCheckout (String chkout_itempk, String chkout_categpk, String chkout_item, int chkout_price, int chkout_count, String chkout_time,String chkout_datereceived) {
        super();
        this.chkout_itempk = chkout_itempk;
        this.chkout_categpk = chkout_categpk;
        this.chkout_item = chkout_item;
        this.chkout_price = chkout_price;
        this.chkout_count = chkout_count;
        this.chkout_time = chkout_time;
        this.chkout_datereceived=chkout_datereceived;
    }

    public int getChkout_id () {
        return chkout_id;
    }

    public void setChkout_id (int chkout_id) {
        this.chkout_id = chkout_id;
    }

    public String getChkout_itempk () {
        return chkout_itempk;
    }

    public void setChkout_itempk (String chkout_itempk) {
        this.chkout_itempk = chkout_itempk;
    }

    public String getChkout_categpk () {
        return chkout_categpk;
    }

    public void setChkout_categpk (String chkout_categpk) {
        this.chkout_categpk = chkout_categpk;
    }

    public String getChkout_item () {
        return chkout_item;
    }

    public void setChkout_item (String chkout_item) {
        this.chkout_item = chkout_item;
    }

    public int getChkout_price () {
        return chkout_price;
    }

    public void setChkout_price (int chkout_price) {
        this.chkout_price = chkout_price;
    }

    public int getChkout_count () {
        return chkout_count;
    }

    public void setChkout_count (int chkout_count) {
        this.chkout_count = chkout_count;
    }

    public String getChkout_time () {
        return chkout_time;
    }

    public void setChkout_time (String chkout_time) {
        this.chkout_time = chkout_time;
    }

    public String getChkout_datereceived() {
        return chkout_datereceived;
    }

    public void setChkout_datereceived(String chkout_datereceived) {
        this.chkout_datereceived = chkout_datereceived;
    }
}