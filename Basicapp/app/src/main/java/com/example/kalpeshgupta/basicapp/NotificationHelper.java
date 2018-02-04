package com.example.kalpeshgupta.basicapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

/**
 * Created by Kalpesh Gupta on 03-02-2018.
 */

public class NotificationHelper extends ContextWrapper {
    private NotificationManager manager;
    public static final String Primary_Channel = "defaullt";

    public NotificationHelper(Context ctx)
    {
        super(ctx);

        NotificationChannel chan1 = new NotificationChannel(Primary_Channel,
                "Primary Channel",NotificationManager.IMPORTANCE_HIGH);
        chan1.setLightColor(Color.GREEN);
        chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(chan1);
    }

    public Notification.Builder getNotification1(String title,String body)
    {
        return new Notification.Builder(getApplicationContext(),Primary_Channel)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(getSmallIcon())
                .setAutoCancel(true);

    }

    public void Notify(int id,Notification.Builder notification)
    {
        getManager().notify(id,notification.build());
    }

    private NotificationManager getManager() {
        if(manager == null)
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    private int getSmallIcon() {
        return android.R.drawable.stat_notify_sync;
    }
}
