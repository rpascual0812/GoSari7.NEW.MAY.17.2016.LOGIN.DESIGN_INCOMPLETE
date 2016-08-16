package com.android.iit.chrs.gosari;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by pogi on 8/9/2016.
 */
public class GCMTokenRefreshListener extends InstanceIDListenerService {
    //If the token is changed registering again
    @Override
    public void onTokenRefresh(){
        Intent intent = new Intent(this, GCMRegistrationIntentService.class);
        startService(intent);
    }
}
