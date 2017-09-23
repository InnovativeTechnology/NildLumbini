package ourproject.nildlumbini;


import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import ourproject.nildlumbini.fragments.FragmentFive;
import ourproject.nildlumbini.fragments.FragmentFour;
import ourproject.nildlumbini.fragments.FragmentOne;
import ourproject.nildlumbini.fragments.FragmentSix;
import ourproject.nildlumbini.fragments.FragmentThree;
import ourproject.nildlumbini.fragments.FragmentTwo;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FragmentPagerAdapter mPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbarFirst);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        tabLayout= (TabLayout) findViewById(R.id.tabs);

        getSupportActionBar().setTitle("");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        viewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter= new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[]  fragments= new Fragment[]{new FragmentOne(),new FragmentTwo(),new FragmentThree(),new FragmentFour(),new FragmentFive(),new FragmentSix()};
            private  final String title[]= new String[]{"one","two","three","four","five","six"};
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
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                if (item.getItemId() == R.id.user_profile) {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }

                return true;
            }
        });
    }

    }
