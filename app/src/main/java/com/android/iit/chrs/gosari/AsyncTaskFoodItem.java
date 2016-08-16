package com.android.iit.chrs.gosari;

/**
 * Created by greg on 5/6/16.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class AsyncTaskFoodItem extends AsyncTask<String, String, JSONObject> {


    public static String url="http://gosari.ph/api/items/list.php?categories_pk";

    private ProgressDialog dialog;

    JSONArray result;

    public AsyncTaskFoodItem(ViewFoodItems activity) {
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        dialog.setTitle("GoSari");
        dialog.setMessage("Loading items , please wait.");
        dialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... args) {


        JSONParser jParser = new JSONParser();
        JSONObject json;
        json= jParser.getJSONFromUrl(url);
        return  json;

    }

    @Override
    protected void onPostExecute(JSONObject json) {


        ViewFoodItems.adapter.notifyDataSetChanged();

        dialog.dismiss();

        try{
           // ViewFoodItems.message=json.getString("msg");
            result=json.getJSONArray("result");
            for(int i=0;i<result.length();i++) {
                JSONObject r=result.getJSONObject(i);
                ItemFood itemFood = new ItemFood();
                itemFood.setPk(r.getString("pk"));
                itemFood.setPk_categories(r.getString("categories_pk"));
                itemFood.setItems(r.getString("item"));
                itemFood.setPrice(r.getString("price"));
                itemFood.setDescription(r.getString("description"));
                itemFood.setDeliverytime(r.getString("delivery_time"));
                itemFood.setCount(r.getString("count"));
                itemFood.setArchived(r.getString("archived"));
                ViewFoodItems.ItemfoodList.add(itemFood);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

    }



}