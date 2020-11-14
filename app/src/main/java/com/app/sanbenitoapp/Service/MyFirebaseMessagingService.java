package com.app.sanbenitoapp.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.app.sanbenitoapp.Activity.ChatActivity;
import com.app.sanbenitoapp.Activity.MainActivity;
import com.app.sanbenitoapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

//Clase dedicada al recibimiento de push notifications desde firebase
public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    NotificationManager notificationManager;
    int idNo;
    Intent intent;
    String idvalor="0";
    PendingIntent pIntent;
    NotificationCompat.Builder builder;

    public MyFirebaseMessagingService()
    {

    }

    //Tag para token
    final String TAG = "MyToken New";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        // TODO(developer): Handle FCM messages here.
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        //Verificar si el mensaje contiene datos
        Log.e("PUSH", "PUSH: "+ remoteMessage.getData());

        if(remoteMessage.getData().size() > 0)
        {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
            if(remoteMessage.getData().get("tipo").equals("1")){

                Log.e("TIPO 1","TIPO 1: "+ remoteMessage.getData());
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("tipo","0");
                notificacion(this, intent, remoteMessage.getData().get("mensaje"), remoteMessage.getData().get("titulo"),1);

            }
            else if(remoteMessage.getData().get("tipo").equals("2")){
                Log.e("TIPO 2","TIPO 2: "+ remoteMessage.getData());
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("tipo","2");
                notificacion(this, intent, remoteMessage.getData().get("mensaje"), remoteMessage.getData().get("titulo"),1);

            }

            else if(remoteMessage.getData().get("tipo").equals("3"))
            {
                Log.e("TIPO 3","TIPO 3: "+ remoteMessage.getData());
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("tipo","3");
                notificacion(this,intent,remoteMessage.getData().get("mensaje"),remoteMessage.getData().get("titulo"),1);
            }
            else if(remoteMessage.getData().get("tipo").equals("4"))
            {
                Log.e("TIPO 4","TIPO 4: "+ remoteMessage.getData());
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("tipo","4");
                notificacion(this,intent,remoteMessage.getData().get("mensaje"),remoteMessage.getData().get("titulo"),1);
            }


            else if(remoteMessage.getData().get("tipo").equals("7")){
                Log.e("Nuevo mensaje","Nuevo mensaje: "+ remoteMessage.getData());
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("tipo", "7");
                notificacion(this, intent, remoteMessage.getData().get("mensaje"), remoteMessage.getData().get("titulo"), 1);
            }
        }

    }



    public void notificacion(MyFirebaseMessagingService context, Intent intent, String mensaje, String titulo, int idno) {
        intent.putExtra("idFeed", idno);
        Log.i("valor2", "ok" + idno);
        idvalor = idno + "";

        String CHANNEL_ID = "my_channel_01";
        pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;

            notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "Smart 912", importance);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .build();
            //mChannel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.alarmabeep), audioAttributes);

            builder = new NotificationCompat.Builder(this, "Smart912_" + idvalor);


            builder.setContentTitle(titulo)  // required
                    .setSmallIcon(R.mipmap.ic_launcher) // required
                    .setChannelId(CHANNEL_ID)
                    .setContentText(mensaje)  // required
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(mensaje))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pIntent)
                    .setTicker(mensaje)
                    //.setSound(Uri.parse("android.resource://"
                    //        + context.getPackageName() + "/" + R.raw.alarmabeep))
                    .setVibrate(new long[]{200, 200, 200, 200, 200, 1000});

            notificationManager.createNotificationChannel(mChannel);


            notificationManager.notify(idno, builder.build());
        } else {

            Notification notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher).setTicker(mensaje)
                    .setWhen(System.currentTimeMillis()).setContentTitle(titulo)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(mensaje))
                    .setContentText(mensaje).setContentIntent(pIntent)
                    .getNotification();
            // Remove the notification on click
            if (idno == 0 || idno == 1 || idno == 3) {
                notification.defaults |= Notification.DEFAULT_SOUND;
                notification.defaults |= Notification.DEFAULT_VIBRATE;
            } else {
                // notification.sound = Uri.parse("android.resource://"
                //        + context.getPackageName() + "/" + R.raw.alarmabeep
                //);

                long[] vibrate = {200, 200, 200, 200, 200, 1000};
                notification.vibrate = vibrate;
            }

            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            //notification.defaults |= Notification.DEFAULT_SOUND;
            //notification.defaults |= Notification.DEFAULT_VIBRATE;

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(idno, notification);
        }
    }
}
