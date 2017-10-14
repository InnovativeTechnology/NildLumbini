package ourproject.nildlumbini;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by suman on 8/16/2017.
 */

public class NetworkConnection {
    Context c;
    public NetworkConnection(Context context){
         c=context;
     }
    public boolean isNetworkConnection(){
//        ProgressDialog dialog = new ProgressDialog(c);
//        dialog.setMessage("Plz wait...");
//        dialog.show();
        ConnectivityManager manager = (ConnectivityManager) c.getSystemService(c.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        //dialog.dismiss();
        return isAvailable;
     }
}
