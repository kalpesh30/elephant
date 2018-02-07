package com.example.kalpeshgupta.basicapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

/**
 * Created by Kalpesh Gupta on 03-02-2018.
 */

public class NotificationHelper extends ContextWrapper {

            public static final String Primary_Channel = "defaullt";
            private NotificationManager manager;


            public NotificationHelper(Context ctx)
            {
                super(ctx);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel chan1 = new NotificationChannel(Primary_Channel,
                            "Primary Channel", NotificationManager.IMPORTANCE_DEFAULT);
                    chan1.setLightColor(Color.GREEN);
                    chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                    getManager().createNotificationChannel(chan1);
                }
            }

        public Notification.Builder getNotification1(String title, String body) {
            Notification.Builder builder;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                builder = new Notification.Builder(getApplicationContext(), Primary_Channel);
            }
            else
                builder = new Notification.Builder(getApplicationContext());
            builder.setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(getSmallIcon())
                    .setAutoCancel(true)
                    .setStyle(new Notification.BigTextStyle().bigText(body));
            return builder;

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
            return android.R.drawable.stat_notify_chat;
        }
}
