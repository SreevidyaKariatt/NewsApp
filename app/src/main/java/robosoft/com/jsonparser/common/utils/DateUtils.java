package robosoft.com.jsonparser.common.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import robosoft.com.jsonparser.common.constants.AppConstants;

/**
 * Created by sree on 1/2/16.
 */

//TODO make the method static
public class DateUtils {


    private static final String TAG = "News";
    private static final SimpleDateFormat dateformat = new SimpleDateFormat(AppConstants.stringConstants.DATE_FORMAT);
    public static String parseDate(String data)
    {

        try {
            //TODO make variable static,make the parameter as constant
            return dateformat.format(dateformat.parse(data));
        } catch (ParseException e) {
            Log.i(TAG,"Date parse exception");
        }
        return null;
    }
}
