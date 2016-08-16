package com.android.iit.chrs.gosari;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.Notification.*;
import static android.app.PendingIntent.*;

public class MainActivity extends AppCompatActivity {

    Button btnStart;

    Animation slide_in,pop_in,pop_in2,scale,slide_out;

    ImageView iv_buildings,iv_gosariIcons;

    ConnectionDetector cd;

    //LinearLayout LinearLayout_main;

    private BroadcastReceiver broadcastReceiver;

    RelativeLayout relativeLayout;


  //  TransitionDrawable transitionDrawable;
AnimationDrawable anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
        cd=new ConnectionDetector(getApplicationContext());
        //LinearLayout_main=(LinearLayout)findViewById(R.id.LinearLayout_main);
        iv_buildings=(ImageView)findViewById(R.id.iv_buildings);
        iv_gosariIcons=(ImageView)findViewById(R.id.iv_gosariIcon);
        btnStart=(Button)findViewById(R.id.btnStart);


      //  changeBackground();

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

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
                    String token = intent.getStringExtra("token");
                }else if (intent.getAction().equals(GCMRegistrationIntentService.REGITRATION_ERROR)){
                    Toast.makeText(getApplicationContext(),"GCM registration error!",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Error occured!",Toast.LENGTH_LONG).show();
                }
            }
        };

        //Checking play service is available or not
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        //if play service is not available
        if (ConnectionResult.SUCCESS != resultCode){
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                Toast.makeText(getApplicationContext(),"Google Play Service is not installed/enabled in this device!",Toast.LENGTH_LONG).show();
                GooglePlayServicesUtil.showErrorNotification(resultCode,getApplicationContext());
            }else {
                //If play service is not supported
                //Displaying an error message
                Toast.makeText(getApplicationContext(),"This device is not supported by Google Play Service!",Toast.LENGTH_LONG).show();
            }
        }else {
            //If play service is available
            //Starting intent to register device
            Intent intent = new Intent(this,GCMRegistrationIntentService.class);
            startService(intent);
        }

        Calendar c = Calendar.getInstance();
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM--dd HH:mm:ss");
        if (hours >=6 && hours < 12){
            relativeLayout.setBackgroundResource(R.drawable.new_background_hdpi);
        }else if (hours >= 12 && hours < 17){
            relativeLayout.setBackgroundResource(R.drawable.background_noon_hdpi);
        }else if (hours >= 17 && hours <= 24){
            relativeLayout.setBackgroundResource(R.drawable.background_night_hdpi);
        }else {
            relativeLayout.setBackgroundResource(R.drawable.background_night_hdpi);
        }


    }


    public void ViewCategory(){
        Intent viewCategory = new Intent(getApplicationContext(),ViewCategory.class);
        startActivity(viewCategory);
        recreate();

    }



    public void Slide_in(){

        slide_in=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_slide_in_top);
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
        pop_in=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.abc_popup_enter);
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

             //   SlideBackground();


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

               // transitionDrawable.resetTransition();

            }
        });


    }



    public void Pop_in2(){
        pop_in2=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.abc_popup_exit);
        iv_gosariIcons.startAnimation(pop_in2);

    }



    public void Scale(){
        scale=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.abc_fade_in);
        iv_gosariIcons.startAnimation(scale);
        scale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ViewCategory();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void Slide_out(){
        slide_out=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.abc_slide_in_bottom);
        iv_buildings.startAnimation(slide_out);
        slide_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.e("MESSAGE: ","SHOW NEXT INTENT");

                ViewCategory();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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




 /*   public void changeBackground(){
        LinearLayout_main.animate().translationY(0).translationYBy(120).setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }*/

    //Registering receiver on resumed activity
    @Override
    protected void onResume(){
        super.onResume();
        Log.w("MainActivity","Resume");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter(GCMRegistrationIntentService.REGITRATION_ERROR));
    }

    //Unregistering activity on pause
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

}
