package com.android.iit.chrs.gosari;

/**
 * Created by greg on 5/10/16.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "CartDB";


    public static final String TABLE_CART = "carts";

    // Books Table Columns names
    public static final String KEY_ID = "id";
    public static final String ITEM_PK = "item_pk";
    public static final String CATEGORY_PK = "category_pk";
    public static final String ITEM_CART = "item_cart";
    public static final String ITEM_DESC = "item_description";
    public static final String ITEM_PRICE = "item_price";
    public static final String ITEM_COUNT = "item_count";
    public static final String ITEM_DELIVERY_TIME = "item_deliverytime";
    public static final String ITEM_DELIVERY_DATE = "item_date";

    private static final String[] COLUMNS = {KEY_ID, ITEM_PK, CATEGORY_PK, ITEM_CART, ITEM_DESC, ITEM_PRICE, ITEM_COUNT, ITEM_DELIVERY_TIME};


    public DbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate (SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_CART_TABLE = "CREATE TABLE carts ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "item_pk TEXT, " +
                "category_pk TEXT," +
                "item_cart TEXT," +
                "item_description TEXT," +
                "item_price INTEGER," +
                "item_count INTEGER," +
                "item_deliverytime TEXT," +
                "item_date TEXT)";
        // create books table
        db.execSQL(CREATE_CART_TABLE);

        String CREATE_CHKOUT_TABLE="CREATE TABLE chkout ("+
                "chkout_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "chkout_item_pk TEXT,"+
                "chkout_categ_pk TEXT,"+
                "chkout_item TEXT,"+
                "chkout_price INTEGER,"+
                "chkout_count INTEGER,"+
                "chkout_date TEXT)";
        db.execSQL(CREATE_CHKOUT_TABLE);

        String CREATE_ACCOUNT_TABLE="CREATE TABLE account ("+
                "account_mobile TEXT,"+
                "account_password TEXT)";

        db.execSQL(CREATE_ACCOUNT_TABLE);
    }

    private SQLiteDatabase db;

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS carts");
        db.execSQL("DROP TABLE IF EXISTS chkout");
        // create fresh books table
        onCreate(db);
    }

    public void addItem (ItemCart itemCart) {
        //for logging
        Log.d("addBook", toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_PK, itemCart.getCartpk());
        contentValues.put(CATEGORY_PK, itemCart.getCartpk_Categories());
        contentValues.put(ITEM_CART, itemCart.getCartItems());
        contentValues.put(ITEM_DESC, itemCart.getCartDescription());
        contentValues.put(ITEM_PRICE, itemCart.getCartPrice());
        contentValues.put(ITEM_COUNT, itemCart.getCartCount());
        contentValues.put(ITEM_DELIVERY_TIME, itemCart.getCartDeliveryTime());
        contentValues.put(ITEM_DELIVERY_DATE,itemCart.getCartDate());
        // 3. insert
        db.insert(TABLE_CART, // table
                null, //nullColumnHack
                contentValues); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public ArrayList<ItemCart> getAllItem () {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ItemCart> listItems = new ArrayList<ItemCart>();

        Cursor cursor = db.rawQuery("SELECT * from " + TABLE_CART,
                new String[]{});

        if (cursor.moveToFirst()) {
            do {
                ItemCart itemCart = new ItemCart();


                itemCart.id = cursor.getInt(cursor.getColumnIndex(KEY_ID));

                itemCart.Cartpk = cursor.getString(cursor.getColumnIndex(ITEM_PK));

                itemCart.Cartpk_Categories = cursor.getString(cursor.getColumnIndex(CATEGORY_PK));

                itemCart.CartItems = cursor.getString(cursor.getColumnIndex(ITEM_CART));

                itemCart.CartDescription = cursor.getString(cursor.getColumnIndex(ITEM_DESC));

                itemCart.CartPrice = cursor.getInt(cursor.getColumnIndex(ITEM_PRICE));

                itemCart.CartCount = cursor.getInt(cursor.getColumnIndex(ITEM_COUNT));

                itemCart.CartDeliveryTime = cursor.getString(cursor.getColumnIndex(ITEM_DELIVERY_TIME));

                itemCart.CartDate=cursor.getString(cursor.getColumnIndex(ITEM_DELIVERY_DATE));


                listItems.add(itemCart);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return listItems;
    }

    public int getItemCount () {

        SQLiteDatabase db=this.getReadableDatabase();

        String getItemCount="SELECT COUNT (item_cart) FROM "+TABLE_CART;
        Cursor c=db.rawQuery(getItemCount,null);
        c.moveToFirst();
        int count =c.getInt(0);
        c.close();
        return count;
    }

    public int getTotalPrice() {
        SQLiteDatabase db=this.getReadableDatabase();
        String getTotalPrice="SELECT SUM (item_price) FROM "+TABLE_CART;
        Cursor c=db.rawQuery(getTotalPrice,null);
        if(c.moveToFirst()){
            return c.getInt(0);
        }
        c.close();
        return  0;
    }

    public boolean verifyItem(String item){
        db=this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_CART+" WHERE item_cart = ? ";
        Cursor c=db.rawQuery(query,new String[]{item});

        boolean verifyItem=false;
        if(c.moveToFirst()){
            verifyItem=true;
            while (c.moveToNext());

        }

        c.close();
        db.close();
        return  verifyItem;
    }

    public void updateDateTime(String date){
        db=this.getWritableDatabase();
        String query=("UPDATE carts SET item_deliverytime = '"+date+"'");
        db.execSQL(query);
        db.close();

    }

    public void deletItem(String item){

        db=this.getWritableDatabase();

        db.execSQL("DELETE FROM " +  TABLE_CART + " WHERE item_cart='"+item+"'");

        db.close();
    }

    public void updateItem(String item,int newPrice,int newCount) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_PRICE, newPrice);
        contentValues.put(ITEM_COUNT, newCount);
        db.update(TABLE_CART, contentValues, "item_cart=?", new String[]{item});
        db.close();

    }

    public void removeAllItem(){
        db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_CART);
        db.close();
    }

    public void InserToChkout(){
        db=this.getWritableDatabase();
        db.execSQL("INSERT INTO chkout (chkout_item_pk,chkout_categ_pk,chkout_item,chkout_price,chkout_count,chkout_date) " +
                "SELECT item_pk,category_pk,item_cart,item_price,item_count,item_date FROM "+TABLE_CART);
        db.close();
    }

    public ArrayList<ItemCheckout>getAllItemChkout(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ItemCheckout> listItems = new ArrayList<ItemCheckout>();

        Cursor cursor = db.rawQuery("SELECT * from chkout",
                new String[]{});

        if (cursor.moveToFirst()) {
            do {
                ItemCheckout itemCheckout = new ItemCheckout();

                itemCheckout.chkout_id = cursor.getInt(cursor.getColumnIndex("chkout_id"));

                itemCheckout.chkout_itempk = cursor.getString(cursor.getColumnIndex("chkout_item_pk"));

                itemCheckout.chkout_categpk = cursor.getString(cursor.getColumnIndex("chkout_categ_pk"));

                itemCheckout.chkout_item = cursor.getString(cursor.getColumnIndex("chkout_item"));

                itemCheckout.chkout_price = cursor.getInt(cursor.getColumnIndex( "chkout_price"));

                itemCheckout.chkout_count = cursor.getInt(cursor.getColumnIndex("chkout_count"));

                itemCheckout.chkout_time = cursor.getString(cursor.getColumnIndex("chkout_date"));


                listItems.add(itemCheckout);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return listItems;

    }

    public boolean checkfirst(){
        db=this.getReadableDatabase();
        boolean empty;
        Cursor c=db.rawQuery("SELECT COUNT(*)FROM account",null);
        c.moveToFirst();
        int count=c.getInt(0);
        if(count>0){
            empty=true;
        }else
            empty=false;
        return  empty;
    }

    public void addAccount(Account account){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("account_mobile",account.getLogin_mobile());
        values.put("account_password",account.getLogin_pass());
        db.insert("account",null,values);
        db.close();

    }

    public ArrayList<Account> getLoginAccount() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM account", null);

        ArrayList<Account> accountArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Account account = new Account();
                account.login_mobile = cursor.getString(cursor.getColumnIndex("account_mobile"));
                account.login_pass = cursor.getString(cursor.getColumnIndex("account_password"));
                accountArrayList.add(account);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return accountArrayList;

    }

    public void clearchkouthistory(){
        db=this.getWritableDatabase();
        db.execSQL("DELETE FROM chkout");
        db.close();
    }

    public void LogOut(){
        db=this.getWritableDatabase();
        db.execSQL("DELETE FROM account");
        db.close();
    }
}

