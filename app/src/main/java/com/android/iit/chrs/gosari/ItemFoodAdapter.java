package com.android.iit.chrs.gosari;

/**
 * Created by greg on 5/6/16.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by greg on 4/25/16.
 */
public class ItemFoodAdapter extends ArrayAdapter<ItemFood> implements Filterable {
    ArrayList<ItemFood> FoodList;
    ArrayList<ItemFood> orig;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;


    public ItemFoodAdapter(Context context, int resource, ArrayList<ItemFood> objects) {

        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        FoodList = objects;
        orig=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {

            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.tvitems = (TextView) v.findViewById(R.id.tvItem);
            holder.tvprice = (TextView) v.findViewById(R.id.tvPrice_Items);
            holder.tvdelivery_time = (TextView) v.findViewById(R.id.tvDeliveryTime_Items);
            // holder.tvdescription=(TextView)v.findViewById(R.id.tvDescription_Items);
            v.setTag(holder);
        } else {

            holder = (ViewHolder) v.getTag();
        }
        holder.tvitems.setText("Items: " + FoodList.get(position).getItems());

        holder.tvprice.setText("Price: " + FoodList.get(position).getPrice());
        //  holder.tvdescription.setText("DESCRIPTION: "+FoodList.get(position).getDescription());
        holder.tvdelivery_time.setText("Delivery Time: " + FoodList.get(position).getDeliverytime());

        return v;
    }

    static class ViewHolder {
        public TextView tvitems;
        public TextView tvprice;
        public TextView tvdelivery_time;
        // public TextView tvdescription;

    }


    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<ItemFood> results = new ArrayList<ItemFood>();
                if (orig == null)
                    orig = FoodList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final ItemFood g : orig) {
                            if (g.getItems().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                FoodList = (ArrayList<ItemFood>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return FoodList.size();
    }

    @Override
    public ItemFood getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }






}


