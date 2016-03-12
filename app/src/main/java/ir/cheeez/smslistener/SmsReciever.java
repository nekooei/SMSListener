package ir.cheeez.smslistener;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.RemoteViews;


public class SmsReciever extends BroadcastReceiver {

    private final SmsManager smsManager = SmsManager.getDefault();
    private Finder smsChecker;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle extras = intent.getExtras();

        try{
            if (extras != null){
                Object[] pdusObj = (Object[]) extras.get("pdus");
                for (int i = 0 ; i < pdusObj.length ; i++){
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);

                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String messageBody = currentMessage.getMessageBody();

                    smsChecker = new Finder(context,messageBody);
                    boolean matched = smsChecker.matchPattern();
                    if(matched){

                        Intent notifyIntent = new Intent(context,MainActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(context,1
                                ,notifyIntent,Intent.FLAG_ACTIVITY_NEW_TASK);

                        RemoteViews notifyView = new RemoteViews(Constants.PACKAGE_NAME,R.layout.notify_lay);

                        Notification.Builder builder = new Notification.Builder(context)
                                .setTicker(Constants.smsTickerText)
                                .setSmallIcon(android.R.drawable.stat_sys_warning)
                                .setAutoCancel(true)
                                .setContentIntent(contentIntent)
                                .setContentTitle(Constants.smsContentTitle)
                                .setContentText(Constants.smsContentText)
                                .setVibrate(Constants.vibrationPattern);
                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(Constants.recievedSMSMatchedNotifyID,builder.build());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //Toast.makeText(context,"hey i have a message",Toast.LENGTH_LONG).show();
    }
}
