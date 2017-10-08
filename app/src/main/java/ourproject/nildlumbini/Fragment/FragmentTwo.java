package ourproject.nildlumbini.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ourproject.nildlumbini.MyList;
import ourproject.nildlumbini.R;

/**
 * Created by smadhu on 9/22/2017.
 */

public class FragmentTwo extends Fragment {
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
        View view= inflater.inflate(R.layout.fragment_two, container, false);
        Toast.makeText(getActivity(),MyList.arrayList()+"",Toast.LENGTH_LONG).show();
        return  view;
    }

}
