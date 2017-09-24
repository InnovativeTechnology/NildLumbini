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
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import ourproject.nildlumbini.Fragment.FragmentFive;
import ourproject.nildlumbini.Fragment.FragmentFour;
import ourproject.nildlumbini.Fragment.FragmentOne;
import ourproject.nildlumbini.Fragment.FragmentSix;
import ourproject.nildlumbini.Fragment.FragmentThree;
import ourproject.nildlumbini.Fragment.FragmentTwo;


public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    DrawerLayout drawerLayout;
    FragmentPagerAdapter mPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUtils();
        initUI();
        initNavigationView();
        bindData();

    }

    private void initUtils() {
        firebaseAuth=FirebaseAuth.getInstance();
    }

    private void initUI(){
        initToolbar();
        initDrawerLayout();
        initNavigationView();
        tabLayout= (TabLayout) findViewById(R.id.tabs);
        initPager();

    }

    private void initPager() {
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

    }

    private void initDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarFirst);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    private void initNavigationView() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        if (navigationView != null) {

            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {

                            navigationMenuChanged(menuItem);
                            return true;
                        }
                    });
        }
    }

    private void navigationMenuChanged(MenuItem menuItem) {
         openFragment(menuItem.getItemId());
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();

    }
    public void openFragment(int menuId) {

        switch (menuId) {
            case R.id.user_profile:
            case R.id.user_profile1: {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }

                break;
           case R.id.logout1:
           {
               Toast.makeText(MainActivity.this,"Sign out perform",Toast.LENGTH_SHORT).show();
           }
               break;

            default:
                break;
        }

        }
    private void bindData() {


        bindMenu();


    }

    // This function will change the menu based on the user is logged in or not.
    public void bindMenu() {
        if (firebaseAuth.getCurrentUser()!=null) {
            navigationView.getMenu().setGroupVisible(R.id.group_after_login, true);
            navigationView.getMenu().setGroupVisible(R.id.group_before_login, false);
        } else {
            navigationView.getMenu().setGroupVisible(R.id.group_before_login, true);
            navigationView.getMenu().setGroupVisible(R.id.group_after_login, false);
        }
    }
    }




