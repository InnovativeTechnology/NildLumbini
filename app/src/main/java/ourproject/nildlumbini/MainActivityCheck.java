package ourproject.nildlumbini;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import ourproject.nildlumbini.Fragment.FragmentFive;
import ourproject.nildlumbini.Fragment.FragmentFour;
import ourproject.nildlumbini.Fragment.FragmentThree;
import ourproject.nildlumbini.Fragment.FragmentTwo;

public class MainActivityCheck extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentPagerAdapter mPagerAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_check);
        tabLayout= (TabLayout) findViewById(R.id.tabs);

        initPager();
    }

    private void initPager() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter= new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[]  fragments= new Fragment[]{new FragmentTwo(),new FragmentThree(),new FragmentFour(),new FragmentFive()};
            private  final String title[]= new String[]{"two","three","four","five"};
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return title[position];
            }
        };
        viewPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }


}
