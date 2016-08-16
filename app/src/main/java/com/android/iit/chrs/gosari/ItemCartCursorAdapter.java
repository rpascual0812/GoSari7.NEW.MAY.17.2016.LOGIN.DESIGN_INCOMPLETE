package com.android.iit.chrs.gosari;

/**
 * Created by greg on 5/10/16.
 */
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by greg on 4/26/16.
 */

public class ItemCartCursorAdapter extends ArrayAdapter<ItemCart> {

    Context context;

    ArrayList<ItemCart>items=new ArrayList<ItemCart>();

    static String itemRemove;

    DbHelper db;


    public ItemCartCursorAdapter (Context context,ArrayList<ItemCart> items) {
        super(context, R.layout.row_cart_items,items);
        this.context=context;
        this.items=items;
        db=new DbHelper(context);

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public ItemCart getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {

       LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.row_cart_items,parent,false);
        TextView cart_item=(TextView)view.findViewById(R.id.tvCart_Items);
        TextView cart_description=(TextView)view.findViewById(R.id.tvCart_Description_Items);
        TextView cart_price=(TextView)view.findViewById(R.id.tvCart_Price_Items);
        TextView cart_count=(TextView)view.findViewById(R.id.tvCart_Count);
        TextView cart_deliverytime=(TextView)view.findViewById(R.id.tvCart_DeliveryTime);
        Button  btnRemove=(Button)view.findViewById(R.id.btn_remove);
        btnRemove.setFocusable(false);
        btnRemove.setClickable(true);

        cart_item.setText(items.get(position).CartItems);
        cart_description.setText("Description: " +items.get(position).CartDescription);
        cart_price.setText("Price: " +String.valueOf(items.get(position).CartPrice));
        cart_deliverytime.setText("Delivery Time: " +items.get(position).CartDeliveryTime);
        cart_count.setText("Count: "+String.valueOf(items.get(position).CartCount));
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   // db.deletItem(itemRemove);
                Log.e("MESSAGE: ","Remove button is working");
                String item=items.get(position).getCartItems();
                Log.e("MESSAGE:","DELETING "+item);
                db.deletItem(item);
                items.remove(position);
                notifyDataSetChanged();
                //ViewCart.restart();

            }
        });

        return view;

    }

    static class ShowCart {
        TextView cart_item;
        TextView cart_description;
        TextView cart_price;
        TextView cart_count;
        TextView cart_deliverytime;
        Button btnRemove;
    }


}