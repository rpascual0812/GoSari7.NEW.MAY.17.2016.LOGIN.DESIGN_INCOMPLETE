package com.android.iit.chrs.gosari;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Created by pogi on 8/9/2016.
 */
public class GCMRegistrationIntentService extends IntentService {
    //Constants for success and error
    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    public static final String REGITRATION_ERROR = "RegistrationError";

    //Class constructor
    public GCMRegistrationIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Registering gcm to the device
        registerGCM();
    }

    private void registerGCM() {
        //Register complete intent initially null
        Intent registrationcomplete = null;
        //Register token is also null
        //we will get the token on successful registration
        String token = null;
        try {
            //Creating an instanceid
            InstanceID instanceID = InstanceID.getInstance (getApplicationContext());
            //Getting the token for the instance ID
            token = instanceID.getToken (getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE,null);
            Log.w("GCMRegIntentService","token:" + token);
            registrationcomplete = new Intent(REGISTRATION_SUCCESS);
            registrationcomplete.putExtra("token",token);
        }catch (Exception e){
            Log.w("GCMRegIntentService","Registration error");
            registrationcomplete = new Intent(REGITRATION_ERROR);
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationcomplete);
    }

}
