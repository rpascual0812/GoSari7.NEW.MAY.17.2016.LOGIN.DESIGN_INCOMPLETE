package com.android.iit.chrs.gosari;

/**
 * Created by greg on 5/5/16.
 */
import android.app.ProgressDialog;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AsyncTaskItem extends AsyncTask<String, String, JSONObject> {

    public static int UrlChoice;

    String url;

    private ProgressDialog dialog;

    JSONArray catergories;


    public AsyncTaskItem(ViewFood activity) {
        dialog = new ProgressDialog(activity);
    }


    @Override
    protected void onPreExecute() {
        dialog.setTitle("GoSari");
        dialog.setMessage("Loading items, please wait.");
        dialog.show();
    }


    @Override
    protected JSONObject doInBackground(String... args) {
        JSONParser jParser = new JSONParser();

        switch (UrlChoice) {
            case 1:
                url = "http://gosari.ph/api/categories/list.php?archived=false";
                break;
            case 2:
                url = "http://192.168.1.16/shop/FUNCTIONS/categories/list.php";
                break;

        }

        JSONObject json = jParser.getJSONFromUrl(url);
        return json;
    }


    @Override
    protected void onPostExecute(JSONObject json) {

                dialog.dismiss();
                ViewFood.adapter.notifyDataSetChanged();


        try {

            catergories = json.getJSONArray("result");
            for (int i = 0; i < catergories.length(); i++) {
                JSONObject c = catergories.getJSONObject(i);
                ItemCategory foods = new ItemCategory();
                foods.setPk(c.getString("pk"));
                foods.setCategory(c.getString("category"));
                foods.setImage(c.getString("image"));
                foods.setArchived(c.getString("archived"));
                ViewFood.foodList.add(foods);
            }


        }
        catch (JSONException e) {
        }
        catch (NullPointerException e){

        }


    }





}