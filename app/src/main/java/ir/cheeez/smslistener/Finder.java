package ir.cheeez.smslistener;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ir.cheeez.smslistener.Constants;

/*
 * Created by milad on 3/12/16.
 */
public class Finder {

    private String REGEX;
    private String SMSBody;
    private Context context;

    Finder(Context context,String Sms){

        // save Context
        this.context = context;

        // get regular expression from shared preferences if we don't have this information set default from ir.cheeez.smslistener.Constants class
        SharedPreferences regexToolKit = context.getSharedPreferences(Constants.SHARED_PREFRENCES_NAME, 0);
        REGEX = regexToolKit.getString(Constants.REGEX_KEY_PREF,Constants.DEFUALT_REGEX);

        // save Sms body
        SMSBody = Sms;
    }

    boolean matchPattern(){
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(SMSBody);
        if(matcher.find()){
            return true;
        }else {
            return false;
        }
    }


}
