package com.example.lux.fallandstressmonitor;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FcmMessagingService extends FirebaseMessagingService {
    public final int NOTIFICATION_ID = 0;

    @SuppressLint("ResourceAsColor")
    @Override

    public void onMessageReceived(RemoteMessage remoteMessage){

        if(remoteMessage.getData().size()>0) {
            final String title, message, img_url;

            title = remoteMessage.getData().get("title");
            message = remoteMessage.getData().get("message");
            img_url = remoteMessage.getData().get("img_url");

            Intent intent = new Intent(this, caregiverArea.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            String CHANNEL_ID = "personal notifications";
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

            builder.setContentTitle(title);
            //builder.setContentText(message);
            builder.setSubText(message);
            builder.setContentIntent(pendingIntent);
            builder.setSound(sounduri);
            builder.setSmallIcon(R.drawable.ic_notification); //la piccola icona dell'applicazione
            builder.setColor(R.color.colorAccent);
            builder.setAutoCancel(true);
            ImageRequest imageRequest = new ImageRequest(img_url, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(response));
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


                    if (notificationManager != null) {
                        notificationManager.notify(NOTIFICATION_ID,builder.build());
                    }

                }
            }, 0, 0, null, Bitmap.Config.RGB_565 ,new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }

            });

            MySingleton.getmInstances(this).addToRequestque(imageRequest);

        }
    }

}