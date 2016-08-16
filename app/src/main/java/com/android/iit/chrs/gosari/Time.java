package com.android.iit.chrs.gosari;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by pogi on 8/3/2016.
 */
public class Time extends AppCompatActivity {
    private TextView time;
    private Button Back;
    long timeinmilliseconds;
    long Totaltimeinmilliseconds;
    boolean blink;
    NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    Random random;
    int m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timecount);
        time = (TextView) findViewById(R.id.tv_counter);
        Totaltimeinmilliseconds = 1800 * 1000;
        timeinmilliseconds = 30 * 1000;
        setActionListeners();
    }
    //builds an activity to display timer on delivery with notification along with timer inside it
    private void setActionListeners(){
        m = getRandom();
        CountDownTimer countDownTimer = new CountDownTimer(Totaltimeinmilliseconds, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                if (blink){
                    time.setVisibility(View.VISIBLE);
                }else {
                    time.setVisibility(View.INVISIBLE);
                }
                blink = !blink;
                time.setText(String.format("%02d", seconds/60)+ ":" + String.format("%02d",seconds % 60));
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                builder = new NotificationCompat.Builder(Time.this);
                builder.setSmallIcon(R.drawable.titlelogo);
                builder.setContentTitle("Delivery");
                builder.setContentText(time.getText());
                notificationManager.notify(m, builder.build());
            }

            @Override
            public void onFinish() {
                time.setText("Items Delivered");
                time.setVisibility(View.VISIBLE);
                m = getRandom();
                Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                builder = new NotificationCompat.Builder(Time.this);
                builder.setSmallIcon(R.drawable.titlelogo);
                builder.setContentTitle("Delivery");
                builder.setContentText(time.getText());
                builder.setSound(soundUri);
                notificationManager.notify(m, builder.build());
            }
        }.start();
    }

    public int getRandom() {
        random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }
}
