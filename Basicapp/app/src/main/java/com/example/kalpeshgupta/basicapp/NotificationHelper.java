package com.example.kalpeshgupta.basicapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import static android.graphics.Color.*;

/**
 * Created by Kalpesh Gupta on 30-01-2018.
 */

public class NotificationHelper extends ContextWrapper {
    private static final String my_channel_id = "com.example.kalpeshgupta.basicapp.multlip";
    private static final String my_channel_name = "Mult channel";
    private NotificationManager manager;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationHelper(Context base){
        super(base);
        createChannels();
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels() {
        NotificationChannel myChannel = new NotificationChannel(my_channel_id,my_channel_name,NotificationManager.IMPORTANCE_DEFAULT);
        myChannel.enableLights(true);
        myChannel.setLightColor(GREEN);
        myChannel.enableVibration(true);
        myChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager();


    }

    public NotificationManager getManager() {
        if(manager == null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;

    }

    //@Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getApplicationNotif(String title, String body) {
        return new Notification.Builder(NotificationHelper.this,my_channel_id)
                .setContentText(body)
                .setContentTitle(title);


    }
}
