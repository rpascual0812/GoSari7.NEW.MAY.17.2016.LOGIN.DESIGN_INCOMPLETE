package com.android.iit.chrs.gosari;


    import android.app.Activity;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.graphics.drawable.ColorDrawable;
    import android.net.Uri;
    import android.support.design.widget.FloatingActionButton;
    import android.support.v4.app.NavUtils;
    import android.support.v7.app.ActionBarActivity;
    import android.support.v7.app.AlertDialog;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.support.v7.widget.Toolbar;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.ListView;
    import android.widget.TextView;
    import android.widget.Toast;

    import org.json.JSONArray;

    import java.util.ArrayList;

    public class ViewFood extends AppCompatActivity {

        public static ArrayList<ItemCategory> foodList;

        static ItemAdapter adapter;

        public static JSONArray catergories = null;

        public static String message;

        ImageButton imgbtnViewCart;

        DbHelper db;

        static Activity activity;

        int showCartItemCount,showCartItemPrice;

        TextView ShowCartItems;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
             getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setContentView(R.layout.activity_view_food);

            ShowCartItems=(TextView)findViewById(R.id.tvFood_ShowTotalCount);

            activity=this;

            db=new DbHelper(this);

            ShowCountItems();

            imgbtnViewCart=(ImageButton)findViewById(R.id.imgbtnViewCart);

            AsyncTaskItem.UrlChoice = 1;

            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

            foodList = new ArrayList<ItemCategory>();

            new AsyncTaskItem(this).execute();

            ListView listview = (ListView) findViewById(R.id.list);

            adapter = new ItemAdapter(getApplicationContext(), R.layout.row_food, foodList);

            assert listview != null;
            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                    int test = Integer.parseInt(foodList.get(position).getPk());
                    int choice = test;
                    ViewFoodItems.title=foodList.get(position).getCategory();

                    switch (choice) {
                        case 1:
                            AsyncTaskFoodItem.url = "http://gosari.ph/api/items/list.php?categories_pk=1&archived=false";
                            break;
                        case 2:
                            AsyncTaskFoodItem.url = "http://gosari.ph/api/items/list.php?categories_pk=2&archived=false";
                            break;
                        case 3:
                            AsyncTaskFoodItem.url = "http://gosari.ph/api/items/list.php?categories_pk=3&archived=false";
                            break;
                        case 4:
                            AsyncTaskFoodItem.url = "http://gosari.ph/api/items/list.php?categories_pk=4&archived=false";
                            break;
                        case 5:
                            AsyncTaskFoodItem.url = "http://gosari.ph/api/items/list.php?categories_pk=5&archived=false";
                            break;
                    }
                    Toast.makeText(getApplicationContext(), String.valueOf(test), Toast.LENGTH_SHORT).show();
                    Intent ShowFoodItem = new Intent(getApplicationContext(), ViewFoodItems.class);
                    startActivity(ShowFoodItem);


                }
            });

            imgbtnViewCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewCart();

                }
            });





        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                // Respond to the action bar's Up/Home button
                case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }


        public void ViewCart(){

            Intent viewCart = new Intent(getApplicationContext(),ViewCart.class);
            startActivity(viewCart);

        }


        public void ShowCountItems(){
           // showCartItemCount=db.getItemCount();
            showCartItemCount=db.getTotalitemCount();
            showCartItemPrice=db.getTotalPrice();

            Log.e("TOTAL PRICE AND COUNT: ",String.valueOf(showCartItemCount)+" "+String.valueOf(showCartItemPrice));

            ShowCartItems.setText("Cart "+showCartItemCount+" item(s) - "+'\u20B1'+" "+showCartItemPrice);
        }


        public static void restart(){
            activity.recreate();
        }


    }

