package ourproject.nildlumbini;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ramesh on 8/16/2017.
 */

public class ExtractDateTime {

    public static String getDate()
    {
        //20170816_184324
        return  new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    }
    public static String getDateForm()
    {
        //20170816_184324
       String s=  new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        return s;
    }


}
