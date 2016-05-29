package com.android.iit.chrs.gosari;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


/**
 * Created by greg on 4/23/16.
 */
public class ItemAdapter extends ArrayAdapter<ItemCategory>{
    ArrayList<ItemCategory>ItemList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;


    public ItemAdapter(Context context, int resource, ArrayList<ItemCategory> objects) {
        super(context, resource, objects);
        vi=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        ItemList=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v=convertView;

        if(v==null){
            holder=new ViewHolder();
            v=vi.inflate(Resource,null);
            holder.tvImages=(ImageView)v.findViewById(R.id.img_thumbnail);
            holder.tvCategory=(TextView)v.findViewById(R.id.tvCategory);
            v.setTag(holder);

        }
        else {
            holder=(ViewHolder)v.getTag();
        }
       // holder.tvImages.setImageResource(R.drawable.moving_loading);
        String url=ItemList.get(position).getImage();
        Log.e("WEBSITE: ","http://www.gosari.ph/"+url);
        Picasso.with(getContext()).load("http://www.gosari.ph/"+url).placeholder(R.drawable.moving_loading).into(holder.tvImages);
        holder.tvCategory.setText(ItemList.get(position).getCategory());
        return v;
    }

    static class ViewHolder{

        public TextView tvCategory;
        public ImageView tvImages;

    }

    }
