package ourproject.nildlumbini.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ourproject.nildlumbini.Item_Adap;
import ourproject.nildlumbini.MainActivity;
import ourproject.nildlumbini.R;
import ourproject.nildlumbini.RetrieveData;

/**
 * Created by smadhu on 9/22/2017.
 */

public class FragmentTwo extends Fragment {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    String option = "Motivation";


    public FragmentTwo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_two, container, false);
        final RecyclerView myRecyle = (RecyclerView)v.findViewById(R.id.recycleView_fragment2);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getActivity(),1);
        myRecyle.setLayoutManager(layoutManager);
        myRecyle.setItemAnimator(new DefaultItemAnimator());

//        database.child("UserFile").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                boolean t = false;
//                //if(dataSnapshot.hasChild())
//                final ArrayList<RetrieveData> doclist= new ArrayList<>();
//                for(DataSnapshot note :dataSnapshot.getChildren())
//                {
//                    try {
//                        if (note.getChildrenCount() > 0) {
//                            String name = note.child("name").getValue().toString();
//                            String option = note.child("option").getValue().toString();
//                            String title = note.child("title").getValue().toString();
//                            String article = note.child("article").getValue().toString();
//                            String imgUrl = note.child("imgUrl").getValue().toString();
//                            String Date = note.child("Date").getValue().toString();
//                            doclist.add(new RetrieveData(name, option, title, article, imgUrl, Date));
//                            t = true;
//                        }
//                    }catch (Exception e)
//                    {
//                        if(t == true) {
//                            myRecyle.setAdapter(new Item_Adap(doclist, getContext()));
//                        }
//                    }
//                }
//                if(t == true) {
//                    myRecyle.setAdapter(new Item_Adap(doclist, getContext()));
//                }
//                Log.d("TAG","SIZE"+doclist.size());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("TAG",databaseError.toString());
//            }
//        });

        new GetDataForFragments(getActivity(), database, option, myRecyle).loadData();
        return v;
    }

}
