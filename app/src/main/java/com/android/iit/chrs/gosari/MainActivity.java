package com.android.iit.chrs.gosari;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart=(Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewCategory();
            }
        });


    }

    public void ViewCategory(){
        Intent viewCategory = new Intent(getApplicationContext(),ViewCategory.class);
        startActivity(viewCategory);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
