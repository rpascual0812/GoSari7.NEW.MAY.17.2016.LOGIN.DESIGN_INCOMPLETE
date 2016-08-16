package com.android.iit.chrs.gosari;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by pogi on 8/9/2016.
 */
public class GCMPushReceiverService extends GcmListenerService {
    //This method will be called every message receive
    @Override
    public void onMessageReceived(String from, Bundle data){
        //Getting message from the bundle
        String message = data.getString("message");
        //Displaying a notification with the message
        sendNotification(message);
    }

    //This method is generating notification and displaying the notification
    private void sendNotification(String message){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this,requestCode,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder noBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.titlelogo)
                .setContentText("New Items Arrived")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSound(sound);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,noBuilder.build());
    }
}
