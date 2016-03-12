package ir.cheeez.smslistener;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    //Notification ID for future updates
    private static int myNotificationId = 1;

    private final CharSequence regexSetTickerText = "Regex has been set successfully" ;
    private final CharSequence regexContentTitle = "Regex";
    private final CharSequence regexContentText = "Regex set";

    private long[] vibration = {0,200,100,200};

    private Intent mNotificationIntent;
    private PendingIntent mContentIntent;

    private Button setRegexBtn;

    RemoteViews mContentView = new RemoteViews(
            "ir.cheeez.smslistener.",R.layout.notify_lay
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connect and set Toolbar as ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Connect set button from XML to Java
        setRegexBtn = (Button) findViewById(R.id.btn_set_regex);

        // Create return Intent from notification
        mNotificationIntent = new Intent(getApplicationContext()
                ,MainActivity.class);
        mContentIntent = PendingIntent.getActivity(getApplication(),0
                ,mNotificationIntent,Intent.FLAG_ACTIVITY_NEW_TASK);


        setRegexBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                // set Content Text in notification
                mContentView.setTextViewText(R.id.text,regexContentText);

                //Build Notification
                Notification.Builder notifyBuilder = new Notification.Builder(getApplicationContext())
                        .setTicker(regexSetTickerText)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setAutoCancel(true)
                        .setContentIntent(mContentIntent)
                        .setContentTitle(regexContentTitle)
                        .setContentText(regexContentText)
                        .setVibrate(vibration);

                // pass notification to notificationManager and notify :)
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(myNotificationId,
                        notifyBuilder.build());
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class thread extends AsyncTask<Integer,Integer,Bitmap>{


        @Override
        protected Bitmap doInBackground(Integer... params) {
            return null;
        }
    }
}
