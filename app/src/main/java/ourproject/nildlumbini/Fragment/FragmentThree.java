package ourproject.nildlumbini.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ourproject.nildlumbini.R;

/**
 * Created by smadhu on 9/22/2017.
 */

public class FragmentThree extends Fragment {
    String option = "Friends";
    public FragmentThree() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        final RecyclerView myRecyle = (RecyclerView)view.findViewById(R.id.recycleView_fragment2);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getActivity(),1);
        myRecyle.setLayoutManager(layoutManager);
        myRecyle.setItemAnimator(new DefaultItemAnimator());

        GetDataForFragments g =  new GetDataForFragments(getActivity(), option, myRecyle,"p");
        g.loadData();
        g.setData();
        return  view;
    }

}
