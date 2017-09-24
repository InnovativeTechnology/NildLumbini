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
import ourproject.nildlumbini.R;
import ourproject.nildlumbini.RetrieveData;

/**
 * Created by smadhu on 9/22/2017.
 */

public class FragmentOne extends Fragment {
    RecyclerView myRecyle;
    ArrayList<RetrieveData> arrayList;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public FragmentOne() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_one, container, false);
        myRecyle= view.findViewById(R.id.r1);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getActivity(),1);
        myRecyle.setLayoutManager(layoutManager);
        myRecyle.setItemAnimator(new DefaultItemAnimator());


        database.child("UserFile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean t = false;
                //if(dataSnapshot.hasChild())
                final ArrayList<RetrieveData> doclist= new ArrayList<>();
                for(DataSnapshot note :dataSnapshot.getChildren())
                {
                    if(note.getChildrenCount()>0) {
                        String name= note.child("name").getValue().toString();
                        String option= note.child("option").getValue().toString();
                        String title= note.child("title").getValue().toString();
                        String article= note.child("article").getValue().toString();
                        String imgUrl= note.child("imgUrl").getValue().toString();
                        String Date= note.child("Date").getValue().toString();
                        doclist.add(new RetrieveData(name,option,title,article,imgUrl,Date));
                        t = true;
                    }
                }
                if(t == true) {
                    myRecyle.setAdapter(new Item_Adap(doclist, getActivity()));
                }
                Log.d("TAG","SIZE"+doclist.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG",databaseError.toString());
            }
        });
        return view;
    }

}
