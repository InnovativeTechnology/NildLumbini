package ourproject.nildlumbini.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import ourproject.nildlumbini.Item_Adap;
import ourproject.nildlumbini.R;
import ourproject.nildlumbini.RetrieveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


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

        arrayList= new ArrayList<>();
        RetrieveData r1= new RetrieveData("Name1","option1","title1","hahah","blah blah blah blah blah blah blah blah blah blah blah blah");
        RetrieveData r2= new RetrieveData("Name1","option1","title1","hahah","blah blah blah blah blah blah blah blah blah blah blah blah");
        RetrieveData r3= new RetrieveData("Name1","option1","title1","hahah","blah blah blah blah blah blah blah blah blah blah blah blah");
        RetrieveData r4= new RetrieveData("Name1","option1","title1","hahah","blah blah blah blah blah blah blah blah blah blah blah blah");
        RetrieveData r5= new RetrieveData("Name1","option1","title1","hahah","blah blah blah blah blah blah blah blah blah blah blah blah");
        arrayList.add(r1);
        arrayList.add(r2);
        arrayList.add(r3);
        arrayList.add(r4);
        arrayList.add(r5);
        myRecyle.setAdapter(new Item_Adap(arrayList,getActivity()));

        return view;
    }

}
