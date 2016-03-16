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
import android.telephony.SmsMessage;


public class SmsReciever extends BroadcastReceiver {

    private Finder smsChecker;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        final Bundle extras = intent.getExtras();
        try{
            try {
                Object[] pdusObj = (Object[]) extras.get("pdus");
                for (Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                    //String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String messageBody = currentMessage.getMessageBody();

                    smsChecker = new Finder(context, messageBody);
                    boolean matched = smsChecker.matchPattern();
                    if (matched) {
                        doAction();
                    }
                }
            }catch (NullPointerException ex){
                ex.printStackTrace();
            }

            if (extras != null){
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //Toast.makeText(context,"hey i have a message",Toast.LENGTH_LONG).show();
    }

    // You can rewrite your action in this method
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void doAction() {
        Intent notifyIntent = new Intent(context,MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,1
                ,notifyIntent,Intent.FLAG_ACTIVITY_NEW_TASK);

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
