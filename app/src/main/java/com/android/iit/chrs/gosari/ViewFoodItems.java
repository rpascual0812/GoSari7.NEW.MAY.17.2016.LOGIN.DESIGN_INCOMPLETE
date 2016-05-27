package com.android.iit.chrs.gosari;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



public class ViewFoodItems extends AppCompatActivity {

    public static ArrayList<ItemFood> ItemfoodList;

    static ItemFoodAdapter adapter;

    public static JSONArray result;


    public static String message;

    int count = 1, newcount = 0;

    float totalprice = 0;

    String getCount;


    public String pk = null;
    public String pk_categories = null;
    public String items = null;
    public String description = null;
    public String price = null;
    public String delivery_time = null;

    DbHelper db;


    String date;

    GridView listitem;

    static String title = null;

    ImageButton imgbtnViewCart;

    SearchView sv;

    int showCartCount,showCartPrice;

    TextView ShowTotalCount;

    LinearLayout LinearLayoutSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food_items);
        ShowTotalCount=(TextView)findViewById(R.id.tvFoodItems_ShowTotalCount);
        sv=(SearchView) findViewById(R.id.searchView1);
        db = new DbHelper(this);
        this.setTitle(title);

        ItemfoodList = new ArrayList<ItemFood>();



        new AsyncTaskFoodItem(ViewFoodItems.this).execute();


        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

        listitem = (GridView) findViewById(R.id.list_food);

        LinearLayoutSearch=(LinearLayout)findViewById(R.id.LinearLayout_search);

        adapter = new ItemFoodAdapter(getApplicationContext(), R.layout.row_food_items, ItemfoodList);

        listitem.setAdapter(adapter);

        listitem.setTextFilterEnabled(true);

        listitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                pk = adapter.FoodList.get(position).getPk();
                pk_categories = adapter.FoodList.get(position).getPk_categories();
                items = adapter.FoodList.get(position).getItems();
                description = adapter.FoodList.get(position).getDescription();
                price = adapter.FoodList.get(position).getPrice();
                delivery_time = adapter.FoodList.get(position).getDeliverytime();

                ShowAlertDialog();
            }


        });



        imgbtnViewCart = (ImageButton) findViewById(R.id.imgbtnViewCart2);

        imgbtnViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowCartItem();
            }
        });
        sv.onActionViewExpanded();
        sv.requestFocus();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.notifyDataSetChanged();

                Log.e("ON QUERY SUBMIT:",query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

               adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();
                return true;




            }
        });

        ShowCartItemCount();


    }


    public void ShowAlertDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        LayoutInflater layoutInflater = LayoutInflater.from(ViewFoodItems.this);

        View promptView = View.inflate(this, R.layout.edit_text, null);


        alertDialog.setView(promptView);

        alertDialog.setTitle(items);


        final EditText etCart = (EditText) promptView.findViewById(R.id.etCart);
        final Button btnAdd = (Button) promptView.findViewById(R.id.btnAdd);
        final Button btnSub = (Button) promptView.findViewById(R.id.btnSub);


        InputMethodManager mgr = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);

        mgr.showSoftInput(etCart, 0);

        etCart.setText("1");


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;

                etCart.setText(String.valueOf(count));
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count < 0) {
                    count--;
                } else {
                    count = 1;
                }
                etCart.setText(String.valueOf(count));

            }
        });


        alertDialog.setMessage("Description: " + description + "\n" + "Price: " + price);

        alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                Toast.makeText(getApplicationContext(), "YOU CLICKED CANCEL", Toast.LENGTH_SHORT).show();

                dialogInterface.cancel();
            }
        });

        alertDialog.setNegativeButton("ADD TO CART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                getCount = etCart.getText().toString();

                boolean verify = db.verifyItem(items);

                Toast.makeText(getApplicationContext(), items, Toast.LENGTH_SHORT).show();

                if (verify == true) {
                    CartAlert();
                    count = 1;

                } else {



                    if (getCount.equals("")) {

                        Log.e("MESSAGE", "ENTER A VALID INPUT");
                        Toast.makeText(getApplicationContext(),"Please enter a valid number.", Toast.LENGTH_SHORT).show();

                    } else {

                        newcount = Integer.parseInt(getCount);

                        if (newcount < 1) {
                            Toast.makeText(getApplicationContext(),"Please enter a valid number.", Toast.LENGTH_SHORT).show();
                            Log.e("MESSAGE: ", "PLEASE Enter a valid Count");

                        } else {
                            InserData();
                            count = 1;
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }


                    }
                }

            }
        });

        alertDialog.show();

    }


    public void ShowCartItem() {

        Intent showCartItem = new Intent(getApplicationContext(), ViewCart.class);
        startActivity(showCartItem);
        ViewFood.restart();
        this.finish();
    }


    public void InserData() {

        DateFormat df = new SimpleDateFormat("EEE,MMM d, yyyy");
        date = df.format(Calendar.getInstance().getTime());
        totalprice = Integer.parseInt(price) * Integer.parseInt(getCount);
        Log.d("Inserting to Databse:", "Cart Items");
        db.addItem(new ItemCart(pk, pk_categories, items, description, totalprice, newcount, delivery_time, date));

    }


    public void CartAlert() {
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setTitle("Alert");
        alertdialogbuilder.setMessage(items + " is already in your cart. Please check your cart.");
        alertdialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = alertdialogbuilder.create();
        alertDialog.show();
    }


    public void ShowCartItemCount(){

        // showCartCount=db.getItemCount();

        showCartCount=db.getTotalitemCount();
        showCartPrice=db.getTotalPrice();
        Log.e("CART ITEMS:",String.valueOf(showCartCount)+" "+String.valueOf(showCartPrice));

        ShowTotalCount.setText("Cart "+showCartCount+" item(s) - "+'\u20B1'+" "+showCartPrice);
    }

    @Override
    public void onBackPressed()
    {
        ViewFood.restart();
        finish();
    }



}