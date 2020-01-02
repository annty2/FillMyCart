package com.example.fillmycart;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

public class NotificationHelper extends ContextWrapper {

    private static final String EDMT_CHANNEL_ID = "com.example.fillmycart.EDMTDEV";
    private static final String EDMT_CHANNEL_NAME ="EDMTDEV Channel";
    private NotificationManager manager;

    public NotificationHelper (Context base){
        super(base);
        createChannels();
    }

    private void createChannels() {
        //IMPORTANCE_DEFAULT = show everywhere, make noise' but don't visually intrude
        //IMPORTANCE_HIGH= show everywhere, make noise and peeks
        //IMPORTANCE_LOW = show everywhere, but isn't intrusive
        //IMPORTANCE_MIN = only show in shade' below the fold
        //IMPORTANCE_NONE = a notification with no importance, don't show in the shade

        NotificationChannel edmtChannel = new NotificationChannel(EDMT_CHANNEL_ID, EDMT_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        edmtChannel.enableLights(true);
        edmtChannel.enableVibration(false);
        edmtChannel.setLightColor(Color.GREEN);
        edmtChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        
        getManager().createNotificationChannel(edmtChannel);
    }

    public NotificationManager getManager() {
        if(manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public  Notification.Builder getEDMTChannelNotification (String title, String body){
        return new Notification.Builder(getApplicationContext(), EDMT_CHANNEL_ID)
                .setContentText(body)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true);
    }

}
