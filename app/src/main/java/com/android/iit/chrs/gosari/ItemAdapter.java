package com.android.iit.chrs.gosari;

/**
 * Created by greg on 5/5/16.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by greg on 4/23/16.
 */
public class ItemAdapter extends ArrayAdapter<ItemCategory>{
    ArrayList<ItemCategory>ItemList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
    Bitmap mIcon11 = null;






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
        holder.tvImages.setImageResource(R.drawable.moving_loading);
        String url=ItemList.get(position).getImage();
        Log.e("WEBSITE: ","http://www.gosari.ph/"+url);
        Picasso.with(getContext()).load("http://www.gosari.ph/"+url).into(holder.tvImages);
        holder.tvCategory.setText(ItemList.get(position).getCategory());
        return v;
    }

    static class ViewHolder{

        public TextView tvCategory;
        public ImageView tvImages;

    }

    }
