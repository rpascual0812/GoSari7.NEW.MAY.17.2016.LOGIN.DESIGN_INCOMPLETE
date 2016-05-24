package com.android.iit.chrs.gosari;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button btnStart;

    Animation slide_in,pop_in,pop_in2;

    ImageView iv_buildings,iv_gosariIcons;

    ConnectionDetector cd;



  //  TransitionDrawable transitionDrawable;
AnimationDrawable anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cd=new ConnectionDetector(getApplicationContext());



        iv_buildings=(ImageView)findViewById(R.id.iv_buildings);
        iv_gosariIcons=(ImageView)findViewById(R.id.iv_gosariIcon);
        btnStart=(Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cd.isConnectingToInternet()) {
                    ViewCategory();
                }

                else {
                    ShowAlertNoInternet();
                }
            }
        });
        Slide_in();
       /* Pop_in2();
        Pop_in();*/




    }


    public void ViewCategory(){
        Intent viewCategory = new Intent(getApplicationContext(),ViewCategory.class);
        startActivity(viewCategory);

    }


    public void Slide_in(){
        slide_in=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in);
        iv_buildings.startAnimation(slide_in);

        slide_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Pop_in2();
                btnStart.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                    Pop_in();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }



    public void Pop_in(){
        btnStart.setVisibility(View.VISIBLE);
        pop_in=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.pop);
       btnStart.startAnimation(pop_in);

      btnStart.setBackgroundResource(R.drawable.change_background_btn);

        anim = (AnimationDrawable)
                btnStart.getBackground();

        //transitionDrawable=(TransitionDrawable)btnStart.getBackground();
       // transitionDrawable.startTransition(3000);


        pop_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
               // transitionDrawable.resetTransition();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
               // transitionDrawable.resetTransition();
               anim.start();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

               // transitionDrawable.resetTransition();

            }
        });


    }



    public void Pop_in2(){
        pop_in2=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.pop);
        iv_gosariIcons.startAnimation(pop_in2);

    }


    public void ShowAlertNoInternet(){
        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
        alertdialogbuilder.setTitle("Alert!");
        alertdialogbuilder.setMessage("Please check if your connected to the internet?");
        alertdialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                minimizeApp();

            }
        });
        AlertDialog alertDialog=alertdialogbuilder.create();
        alertDialog.show();

    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    public void minimizeApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }



}
