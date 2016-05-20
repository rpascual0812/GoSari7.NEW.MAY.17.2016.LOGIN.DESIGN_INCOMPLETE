package com.android.iit.chrs.gosari;

/**
 * Created by greg on 5/5/16.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

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
        holder.tvImages.setImageResource(R.drawable.loading_image);
        new DownloadImageTask(holder.tvImages).execute(ItemList.get(position).getImage());
        holder.tvCategory.setText(ItemList.get(position).getCategory());
        return v;
    }

    static class ViewHolder{

        public TextView tvCategory;
        public ImageView tvImages;

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];

            try {
                InputStream in = new java.net.URL("http://www.gosari.ph/"+urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                Log.e("WEBSITE:",in.toString());
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }


        protected void onPostExecute(Bitmap bitmap) {

            bmImage.setImageBitmap(bitmap);

        }


    }



    }
