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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ourproject.nildlumbini.Item_Adap;
import ourproject.nildlumbini.MainActivity;
import ourproject.nildlumbini.MyList;
import ourproject.nildlumbini.R;
import ourproject.nildlumbini.RetrieveData;

/**
 * Created by smadhu on 9/22/2017.
 */

public class FragmentTwo extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        final RecyclerView myRecyle = (RecyclerView)view.findViewById(R.id.recycleView_fragment2);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getActivity(),1);
        myRecyle.setLayoutManager(layoutManager);
        myRecyle.setItemAnimator(new DefaultItemAnimator());

        GetDataForFragments g =  new GetDataForFragments(getActivity(), option, myRecyle);
        g.loadData();
        g.setData();

        return  view;

    }

}
