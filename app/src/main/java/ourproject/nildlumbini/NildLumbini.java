package ourproject.nildlumbini;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ramesh on 10/8/2017.
 */

public class NildLumbini extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
