package ir.cheeez.smslistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import ir.cheeez.smslistener.Finder;

public class SmsReciever extends BroadcastReceiver {

    private final SmsManager smsManager = SmsManager.getDefault();
    private Finder smsChecker;

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
                        Log.d("sms Come: ","sender number "+ phoneNumber + " message Body : "+ messageBody);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //Toast.makeText(context,"hey i have a message",Toast.LENGTH_LONG).show();
    }
}
