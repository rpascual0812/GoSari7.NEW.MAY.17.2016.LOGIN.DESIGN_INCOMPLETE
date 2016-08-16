package com.android.iit.chrs.gosari;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.SyncStateContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import static android.app.Notification.Builder;

public class ViewCart extends AppCompatActivity {

    ItemCartCursorAdapter customAdapter;


    ArrayList<ItemCart> itemCarts;

    ArrayList<ItemCart> newData;


    Button chkout;

    TextView tvTotalPrice, tvNoofItems;

    int totalprice, totalitems, itemcount;

    int count = 1, getcount;

    String item;

    DbHelper db;

    ListView listView;

    String date;

    int hour, minutes;

    Dialog ShowDialogTimeDate;

    ArrayList<ItemCart>newDateTimeChkout;

    static Activity activity;

    float itemprice,newprice = 0, updateprice = 0;

    ArrayList<Account>accountArrayList;

    String mobile;

    String deliverydate;

    ArrayList<ItemCheckout>checkoutArrayList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        checkoutArrayList=new ArrayList<ItemCheckout>();
        db = new DbHelper(this);


        accountArrayList=new ArrayList<>();

        accountArrayList=db.getLoginAccount();

        for(Account a:accountArrayList){
            mobile=a.getLogin_pass();
            Log.e("MOBILE: ",mobile);

        }

       // Cursor c=db.getMinDeliveryTime();

       // Log.e("DELIVERY TIME: ",c.toString());


        tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        tvNoofItems = (TextView) findViewById(R.id.tvTotalItems);


        totalprice = db.getTotalPrice();

        //totalitems = db.getItemCount();

        totalitems=db.getTotalitemCount();

        tvTotalPrice.setText("Total Price:"+'\u20B1'+" "+ String.valueOf(totalprice));

        tvNoofItems.setText("No. of items: " + String.valueOf(totalitems));

        chkout = (Button) findViewById(R.id.btnCheckout);

        listView = (ListView) findViewById(R.id.list_cart);


        itemCarts = db.getAllItem();

        newData = new ArrayList<ItemCart>();

        for (ItemCart items : itemCarts) {

            newData.add(items);
        }

        customAdapter = new ItemCartCursorAdapter(this, newData);

        listView.setAdapter(customAdapter);


        chkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int getItemCount=db.getItemCount();
                if(getItemCount<1){
                    Log.e("MESSAGE: ","NO ITEMS IN CART");
                    AlertNoItems();

                }
                else {
                    ShowDialogChkOut();
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                item = itemCarts.get(position).getCartItems();
                itemprice = itemCarts.get(position).getCartPrice();
                itemcount = itemCarts.get(position).getCartCount();


               showDialog();
                showUpdateDialog();
            }
        });


    }


    public void showDialog() {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewCart.this);


        alertDialogBuilder.setTitle("Cart Items");

        alertDialogBuilder.setMessage("Item: " + item + "\n" + "Count: " + itemcount + "\n" + "Price: " + itemprice);


        alertDialogBuilder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "UPDATE!!", Toast.LENGTH_SHORT).show();

                showUpdateDialog();

            }
        });

        alertDialogBuilder.setNegativeButton("REMOVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                Log.e("DELETING", "DELETED");
                db.deletItem(item);
                recreate();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }


    public void showUpdateDialog() {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewCart.this);

        LayoutInflater layoutInflater = LayoutInflater.from(ViewCart.this);
        View v = View.inflate(this, R.layout.edit_text_2, null);

        alertDialogBuilder.setView(v);

        final EditText etCart = (EditText) v.findViewById(R.id.etCart2);
        final Button btnAdd = (Button) v.findViewById(R.id.btnAdd2);
        final Button btnSub = (Button) v.findViewById(R.id.btnSub2);

        etCart.setText(String.valueOf(itemcount));


        alertDialogBuilder.setTitle("ENTER NEW NUMBER OF COUNTS?");
        alertDialogBuilder.setMessage("Item: " + item);

        InputMethodManager mgr = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(etCart, 0);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strcount = etCart.getText().toString();
                getcount = Integer.parseInt(strcount);
                getcount++;
                if (getcount > 999) {
                    getcount = 1;
                }
                etCart.setText(String.valueOf(getcount));

            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strcount = etCart.getText().toString();
                getcount = Integer.parseInt(strcount);
                getcount--;
                if (getcount <= 0) {
                    getcount = 1;
                }
                etCart.setText(String.valueOf(getcount));
            }
        });

        alertDialogBuilder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String strcount = etCart.getText().toString();

                if(strcount.equals("")){

                    Toast.makeText(getApplicationContext(),"Please enter a valid number.", Toast.LENGTH_SHORT).show();
                }

                else {
                    getcount = Integer.parseInt(strcount);
                    if(getcount<1){

                        Toast.makeText(getApplicationContext(),"Please enter a valid number.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        updateprice = itemprice / itemcount;
                        newprice = getcount * updateprice;

                        Log.e("UPDATE: ", "ITEM HAS BEEN UPDATED");
                        db.updateItem(item, newprice, getcount);
                        recreate();
                    }
                }

            }
        });

        alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }


    public void ShowDialogChkOut() {

        final AlertDialog.Builder alertdialogbuiler = new AlertDialog.Builder(ViewCart.this);

        alertdialogbuiler.setTitle("DO YOU WANT TO CHECKOUT?");

        alertdialogbuiler.setMessage("# OF ITEMS: " + String.valueOf(totalitems) + "\n" +

                "TOTAL PRICE:"+'\u20B1'+" "+String.valueOf(totalprice));

        alertdialogbuiler.setPositiveButton("SET ANOTHER DATE FOR DELIVERY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ShowDialogTimeDate();
            }
        });


        alertdialogbuiler.setNeutralButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });


        alertdialogbuiler.setNegativeButton("DELIVER NOW", new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(),Time.class);
                startActivity(intent);
                Log.e("INSERT: ", "ADDING TO CHKOUT TABLE");
                HttpGetItems(itemCarts);
                //db.updateDateTime();
                for(ItemCart cart:itemCarts){
                 deliverydate=cart.getCartDeliveryTime();
                   Log.e("DELIVERYTIME: ", deliverydate);
                }
                db.InserToChkout();
                db.removeAllItem();
                Log.e("DELETING: ", "ALL ITEMS HAS BEEN DELETED");
                AlerDialogSuccessChkOut();
                recreate();

                //Toast.makeText(getApplicationContext(), "Successfully chekcout items", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialoag = alertdialogbuiler.create();
        alertDialoag.show();

    }


    public Dialog ShowDialogTimeDate() {

                        ShowDialogTimeDate=new Dialog(this);

        ShowDialogTimeDate.setContentView(R.layout.date_time_picker);

        ShowDialogTimeDate.setTitle("Set Date and Time");


        Button btnCancel = (Button) ShowDialogTimeDate.findViewById(R.id.btn_cancel);
        Button btnSet = (Button) ShowDialogTimeDate.findViewById(R.id.btn_setdate);

        final DatePicker dp = (DatePicker) ShowDialogTimeDate.findViewById(R.id.datePicker);
        final NumberPicker numberPicker = (NumberPicker) ShowDialogTimeDate.findViewById(R.id.numberPicker1);
        final NumberPicker numberPicker1 = (NumberPicker) ShowDialogTimeDate.findViewById(R.id.numberPicker2);

        numberPicker.setMaxValue(19);
        numberPicker.setMinValue(7);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });


        numberPicker1.setMaxValue(59);
        numberPicker1.setMinValue(0);
        numberPicker1.setWrapSelectorWheel(true);
        numberPicker1.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        dp.setCalendarViewShown(false);

        final Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, +1);
        long minDate = calendar.getTimeInMillis();
        dp.setMinDate(minDate);
        calendar.add(calendar.DATE, +2);
        long max = calendar.getTimeInMillis();
        dp.setMaxDate(max);

        ShowDialogTimeDate.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                ShowDialogTimeDate.dismiss();
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub


                int m = dp.getMonth() + 1;
                int d = dp.getDayOfMonth();
                int y = dp.getYear();
                hour = numberPicker.getValue();
                minutes = numberPicker1.getValue();
                String test = String.format("%02d", hour);

                String test1 = String.format("%02d", minutes);

                date = y + "/" + m + "/" + d + " " + test + ":" + test1 + ":00 ";

                ConfirmDialogTimeDate(date);

            }
        });

            return null;
    }


    public void ConfirmDialogTimeDate(final String DateDelivery) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Receive your item on this Date?");

        alertDialogBuilder.setMessage("Date and Time of Delivery: " + date);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                newDateTimeChkout=new ArrayList<ItemCart>();
                db.updateDateTime(DateDelivery);
                newDateTimeChkout=db.getAllItem();
                HttpGetItems(newDateTimeChkout);
                db.InserToChkout();
                checkoutArrayList=db.getAllItemChkout();
                for(ItemCheckout checkout:checkoutArrayList){
                    deliverydate+=checkout.getChkout_datereceived();
                    Log.e("DELIVERYTIME: ",deliverydate);
                }
                db.removeAllItem();
              /*  Toast.makeText(getApplicationContext(), "Successfully chekcout items", Toast.LENGTH_SHORT).show();
                finish();*/
                AlerDialogSuccessChkOut();

            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void HttpGetItems(ArrayList<ItemCart> itemList) {


        try {
            JSONObject object=new JSONObject();
            JSONArray jsonArray=new JSONArray();
            for(ItemCart itemCart:itemList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("price", itemCart.getCartPrice());
                jsonObject.put("items_pk", itemCart.getCartpk_Categories());
                jsonObject.put("item", itemCart.getCartItems());
                jsonObject.put("delivery_time", itemCart.getCartDeliveryTime());
                jsonObject.put("count", itemCart.getCartCount());
                jsonArray.put(jsonObject);
            }

            HttpClient httpclient = new DefaultHttpClient();
            String output=jsonArray.toString();
          //  HttpGet httpGet= new HttpGet("http://gosari.ph/api/items/checkout.php?mobile_number=09950899977&new_date=%222016-05-10%22&order="+ URLEncoder.encode(jsonArray.toString()));
           // Log.e("MESSAGE","http://gosari.ph/api/items/checkout.php?mobile_number=09950899977&new_date=%222016-05-10%22&order="+ URLEncoder.encode(jsonArray.toString()));
            HttpGet httpGet= new HttpGet("http://gosari.ph/api/items/checkout.php?mobile_number="+ mobile +"&new_date=%222016-05-10%22&order="+ URLEncoder.encode(jsonArray.toString()));
            Log.e("MESSAGE","http://gosari.ph/api/items/checkout.php?mobile_number="+ mobile +"&new_date=%222016-05-10%22&order="+ URLEncoder.encode(jsonArray.toString()));
            object.put("order",jsonArray);
            String message=object.toString();
            Log.e("OUTPUT:",message);
            // Post the data:
            httpGet.setHeader("json",jsonArray.toString());
            httpGet.getParams().setParameter("jsonpost",object);

            // Execute HTTP Post Request
            System.out.print(object);
            HttpResponse response = httpclient.execute(httpGet);

            // for JSON:
            if(response != null) {
                InputStream is = response.getEntity().getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            }

        }
        catch (JSONException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }


    public static void restart(){
        activity.recreate();
    }


    public void AlertNoItems(){
        AlertDialog.Builder alerDialogBuilder=new AlertDialog.Builder(this);
        alerDialogBuilder.setTitle("Warning!");
        alerDialogBuilder.setMessage("No item(s) in your cart.");
        alerDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog= alerDialogBuilder.create();
        alertDialog.show();

    }


    public void AlerDialogSuccessChkOut(){
        AlertDialog.Builder alertdialogBuilder= new AlertDialog.Builder(this);
        alertdialogBuilder.setTitle("Success");
        alertdialogBuilder.setMessage("You have successfully check out your item(s).");
        alertdialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alertDialog=alertdialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed()
    {
        Intent viewFoodCategory=new Intent(getApplicationContext(),ViewCategory.class);
        startActivity(viewFoodCategory);
        this.finish();
    }


}
