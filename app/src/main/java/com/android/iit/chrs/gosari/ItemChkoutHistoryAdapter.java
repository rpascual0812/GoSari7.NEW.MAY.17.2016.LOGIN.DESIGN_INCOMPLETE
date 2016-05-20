package com.android.iit.chrs.gosari;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by greg on 5/1/16.
 */
public class ItemChkoutHistoryAdapter extends ArrayAdapter<ItemCheckout>{
    ArrayList<ItemCheckout>itemCheckouts;
    Context context;
    ViewHolder holder;

    public ItemChkoutHistoryAdapter (Context context,ArrayList<ItemCheckout> itemCheckouts) {
        super(context,R.layout.row_chkout_history,itemCheckouts);
        this.itemCheckouts = itemCheckouts;
        this.context=context;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {

        holder=new ViewHolder();
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.row_chkout_history,parent,false);

        holder.chkout_item=(TextView)view.findViewById(R.id.tv_ChkOut_Items);
        holder.chkout_price=(TextView)view.findViewById(R.id.tv_ChkOut_Price_Items);
        holder.chkout_count=(TextView)view.findViewById(R.id.tv_ChkOut_Count);
        holder.chkout_date=(TextView)view.findViewById(R.id.tv_ChkOut_Date);



        holder.chkout_item.setText(itemCheckouts.get(position).chkout_item);
        holder.chkout_price.setText("Price: "+String.valueOf(itemCheckouts.get(position).chkout_price));
        holder.chkout_count.setText("Count: "+String.valueOf(itemCheckouts.get(position).chkout_count));
        holder.chkout_date.setText("Date of Purchased: "+itemCheckouts.get(position).chkout_time);
        return view;

    }

    class ViewHolder{
        TextView chkout_item;
        TextView chkout_price;
        TextView chkout_count;
        TextView chkout_date;

    }
}