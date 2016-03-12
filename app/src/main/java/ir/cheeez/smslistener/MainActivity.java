package ir.cheeez.smslistener;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Intent mNotificationIntent;
    private PendingIntent mContentIntent;

    private Button setRegexBtn;
    private TextView regexStatusTextView;
    private EditText inputRegex;


    RemoteViews mContentView = new RemoteViews(
            Constants.PACKAGE_NAME,R.layout.notify_lay);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Connect and set Toolbar as ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Connect Regex Status Text view from XML to Java
        regexStatusTextView = (TextView) findViewById(R.id.regex_status);

        // Connect regex input edit Text from XML to Java
        inputRegex = (EditText) findViewById(R.id.input);

        // Connect set button from XML to Java
        setRegexBtn = (Button) findViewById(R.id.btn_set_regex);

        // Create return Intent from notification
        mNotificationIntent = new Intent(getApplicationContext()
                ,MainActivity.class);
        mContentIntent = PendingIntent.getActivity(getApplication(),0
                ,mNotificationIntent,Intent.FLAG_ACTIVITY_NEW_TASK);
        loadData();


        setRegexBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                // set Content Text in notification
                mContentView.setTextViewText(R.id.text, Constants.regexContentText);

                //Build Notification
                Notification.Builder notifyBuilder = new Notification.Builder(getApplicationContext())
                        .setTicker(Constants.regexSetTickerText)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setAutoCancel(true)
                        .setContentIntent(mContentIntent)
                        .setContentTitle(Constants.regexContentTitle)
                        .setContentText(Constants.regexContentText)
                        .setVibrate(Constants.vibrationPattern);

                SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME,0).edit();
                editor.putString(Constants.REGEX_KEY_PREF,inputRegex.getText().toString());
                editor.apply();
                loadData();

                // pass notification to notificationManager and notify :)
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(Constants.regexSetNotifyID,
                        notifyBuilder.build());
            }
        });

    }

    private void loadData() {
        SharedPreferences data = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, 0);
        String regex = data.getString(Constants.REGEX_KEY_PREF, Constants.DEFAULT_REGEX);
        regexStatusTextView.setText(Constants.TITLE_DOC.replace("REGEXTYPE", regex));
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


}
