package ir.cheeez.smslistener;

/**
 * Created by milad on 3/12/16.
 */
public class Constants {

    public static final String PACKAGE_NAME = "ir.cheeez.smslistener";

    public static String SHARED_PREFERENCES_NAME = "regex";

    public static String DEFAULT_REGEX = "^[A-Z]{1,3} [a-z]+ [0-9]+$";

    public static String REGEX_KEY_PREF = "regex";

    public static final CharSequence regexSetTickerText = "Regex has been set successfully" ;
    public static final CharSequence regexContentTitle = "Regex";
    public static final CharSequence regexContentText = "Regex set";
    public static long[] vibrationPattern = {0,200,100,200};

    public static final CharSequence smsTickerText = "SMS Found with entered Regular Expression";
    public static final CharSequence smsContentTitle = "SMS Found";
    public static final CharSequence smsContentText = "";

    public static final String TITLE_DOC = "REGEX:\nREGEXTYPE";

    //Notification ID for future updates
    public static int regexSetNotifyID = 1;

    public static int recievedSMSMatchedNotifyID = 2;

}
