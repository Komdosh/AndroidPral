package com.komdosh.pral.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import com.komdosh.pral.Display.WordDisplay;

import java.util.concurrent.TimeUnit;

/**
 * Created by Komdosh on 24.09.2016.
 */

public class myService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendNotif();
        return super.onStartCommand(intent, flags, startId);
    }

    void sendNotif() {
        Intent i = new Intent(this, WordDisplay.class);
        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(WordDisplay.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(i);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentText("Hello WORLD!");
        mBuilder.setContentTitle("Title!");

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(412, mBuilder.build());
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }
}
