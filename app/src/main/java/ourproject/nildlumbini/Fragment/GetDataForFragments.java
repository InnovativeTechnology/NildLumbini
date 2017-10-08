package ourproject.nildlumbini.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ourproject.nildlumbini.Item_Adap;
import ourproject.nildlumbini.MainActivity;
import ourproject.nildlumbini.RetrieveData;

/**
 * Created by suman on 10/8/2017.
 */

class GetDataForFragments {
    FragmentActivity activity;
    DatabaseReference database;
    String option;
    RecyclerView myRecyle;
    public GetDataForFragments(FragmentActivity activity, DatabaseReference database, String option, RecyclerView myRecyle){
        this.activity = activity;
        this.database = database;
        this.option = option;
        this.myRecyle = myRecyle;
    }

    public void loadData(){
        database.child("UserFile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean t = false;
                //if(dataSnapshot.hasChild())
                final ArrayList<RetrieveData> doclist = new ArrayList<>();
                for (DataSnapshot note : dataSnapshot.getChildren()) {
                    try {
                        if (note.getChildrenCount() > 0) {
                            String name = note.child("name").getValue().toString();
                            String option = note.child("option").getValue().toString();
                            String title = note.child("title").getValue().toString();
                            String article = note.child("article").getValue().toString();
                            String imgUrl = note.child("imgUrl").getValue().toString();
                            String Date = note.child("Date").getValue().toString();
                            doclist.add(new RetrieveData(name, option, title, article, imgUrl, Date));
                            t = true;
                        }
                    } catch (Exception e) {
                        if (t == true) {
                            myRecyle.setAdapter(new Item_Adap(doclist, activity));
                        }
                    }
                }
                if (t == true) {
                    myRecyle.setAdapter(new Item_Adap(doclist, activity));
                }
                Log.d("TAG", "SIZE" + doclist.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG", databaseError.toString());
            }
        });
    }
}
