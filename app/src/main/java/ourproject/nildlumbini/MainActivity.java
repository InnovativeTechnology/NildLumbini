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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ourproject.nildlumbini.Fragment.FragmentFive;
import ourproject.nildlumbini.Fragment.FragmentFour;
import ourproject.nildlumbini.Fragment.FragmentOne;
import ourproject.nildlumbini.Fragment.FragmentSix;
import ourproject.nildlumbini.Fragment.FragmentThree;
import ourproject.nildlumbini.Fragment.FragmentTwo;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FragmentPagerAdapter mPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    RecyclerView myRecyle;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

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
        myRecyle= (RecyclerView) findViewById(R.id.r1);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(MainActivity.this,1);
        myRecyle.setLayoutManager(layoutManager);
        myRecyle.setItemAnimator(new DefaultItemAnimator());

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
            case R.id.check:
                startActivity(new Intent(MainActivity.this,MainActivityCheck.class));
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
        database.child("UserFile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean t = false;
                //if(dataSnapshot.hasChild())
                final ArrayList<RetrieveData> doclist= new ArrayList<>();
                for(DataSnapshot note :dataSnapshot.getChildren())
                {
                   try {
                       if (note.getChildrenCount() > 0) {
                           String name = note.child("name").getValue().toString();
                           String option = note.child("option").getValue().toString();
                           String title = note.child("title").getValue().toString();
                           String article = note.child("article").getValue().toString();
                           String imgUrl = note.child("imgUrl").getValue().toString();
                           String Date = note.child("Date").getValue().toString();
                           doclist.add(new RetrieveData(name, option, title, article, imgUrl, Date));
                           t = true;
                       }
                   }catch (Exception e)
                   {
                       if(t == true) {
                           myRecyle.setAdapter(new Item_Adap(doclist, MainActivity.this));
                       }
                   }
                }
                if(t == true) {
                    myRecyle.setAdapter(new Item_Adap(doclist, MainActivity.this));
                }
                Log.d("TAG","SIZE"+doclist.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG",databaseError.toString());
            }
        });


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




