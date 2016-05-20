package com.android.iit.chrs.gosari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class  ViewChkoutHistory extends AppCompatActivity {

    ArrayList<ItemCheckout>itemCheckouts;
    ArrayList<ItemCheckout>newData;
    ItemChkoutHistoryAdapter adapter;

    DbHelper db;

    Button btnclearhistory;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chkout_history);

        db=new DbHelper(this);

        ListView listView=(ListView)findViewById(R.id.list_chkout);

        btnclearhistory=(Button)findViewById(R.id.btnClearHistory);

        btnclearhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                db.clearchkouthistory();
                recreate();
            }
        });

        itemCheckouts=db.getAllItemChkout();

        newData=new ArrayList<ItemCheckout>();

        for(ItemCheckout itemCheckout: itemCheckouts){

            newData.add(itemCheckout);
        }

        adapter=new ItemChkoutHistoryAdapter(this,newData);

        listView.setAdapter(adapter);

    }


}