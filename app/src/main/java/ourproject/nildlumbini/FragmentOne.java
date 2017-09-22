package ourproject.nildlumbini;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by smadhu on 9/22/2017.
 */

public class FragmentOne extends Fragment {
    RecyclerView myRecyle;
    public FragmentOne() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_one, container, false);
        myRecyle= view.findViewById(R.id.r1);
        return view;


    }

}
