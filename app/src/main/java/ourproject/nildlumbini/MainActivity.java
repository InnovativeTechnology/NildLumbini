package ourproject.nildlumbini;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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

import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    DrawerLayout drawerLayout;
    FragmentPagerAdapter mPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    RecyclerView myRecyle;
    SwipeRefreshLayout swip;
    SwipeRefreshLayout swipeRefreshLayout;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    final ArrayList<RetrieveData> doclist= new ArrayList<>();
    TextView name;
    CircleImageView image;
    Button editProfile;


    TextView email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database.keepSynced(true);
        initUtils();
        initUI();
        initNavigationView();
        bindData();
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swip);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                progressDialog.show();
                doclist.clear();
                bindData();
            }
        });
    }

    private void initUtils() {
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog= new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("refreshing..");


    }

    @Override
    protected void onStart() {
        super.onStart();

        View header= navigationView.getHeaderView(0);

        email = (TextView) header.findViewById(R.id.email);
        image = (CircleImageView)header.findViewById(R.id.imageViewProfile);
        editProfile = (Button)header.findViewById(R.id.buttonDrawer);

        editProfile.setVisibility(View.INVISIBLE);

        if(firebaseAuth.getCurrentUser() != null){
            email.setText(firebaseAuth.getCurrentUser().getEmail().toString());
            email.setTextColor(Color.BLACK);

            editProfile.setVisibility(View.VISIBLE);
            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Still on Contruction",Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(getApplicationContext(), "Plz login to set user profile",Toast.LENGTH_SHORT).show();
        }


    }

    private void initUI(){
        initToolbar();
        initDrawerLayout();
        initNavigationView();
        myRecyle= (RecyclerView) findViewById(R.id.r1);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(MainActivity.this,1);
        myRecyle.setLayoutManager(layoutManager);
        myRecyle.setItemAnimator(new DefaultItemAnimator());
        swip= (SwipeRefreshLayout) findViewById(R.id.swip);


    }

    private void initDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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

        View header= navigationView.getHeaderView(0);

        email = (TextView) header.findViewById(R.id.email);
        image = (CircleImageView)header.findViewById(R.id.imageViewProfile);
        editProfile = (Button)header.findViewById(R.id.buttonDrawer);

           editProfile.setVisibility(View.INVISIBLE);

        if(firebaseAuth.getCurrentUser() != null){
            email.setText(firebaseAuth.getCurrentUser().getEmail().toString());
            email.setTextColor(Color.BLACK);

            editProfile.setVisibility(View.VISIBLE);
            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Still on Contruction",Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(getApplicationContext(), "Plz login to set user profile",Toast.LENGTH_SHORT).show();
        }
    }

/*
    @Override
    protected void onRestart() {
        super.onRestart();
        if(firebaseAuth.getCurrentUser() != null){
           */
/* email.setText(firebaseAuth.getCurrentUser().getEmail().toString());
            email.setTextColor(Color.BLACK);
*//*

            editProfile.setVisibility(View.VISIBLE);
            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Still on Contruction",Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(getApplicationContext(), "Plz login to set user profile",Toast.LENGTH_SHORT).show();
        }
        bindMenu();
    }
*/

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
            case R.id.check1:
                startActivity(new Intent(MainActivity.this,MainActivityCheck.class));
                break;
           case R.id.logout1:
           {

               onLogout();
               //Toast.makeText(MainActivity.this,"Sign out perform",Toast.LENGTH_SHORT).show();
           }
               break;

            default:
                break;
        }

        }

    private void onLogout() {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(MainActivity.this);
        alertbox.setTitle("Are you sure want to logout??");
        alertbox.setCancelable(false);
        alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                finish();
            }
        });
        alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertbox.show();
    }

    private void bindData() {
        bindMenu();
        database.child("UserFile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean t = false;
                //if(dataSnapshot.hasChild())
                final ArrayList<RetrieveData> doclist= new ArrayList<>();
                String keys = "";
                for(DataSnapshot note :dataSnapshot.getChildren())
                {
                   try {
                       if (note.getChildrenCount() > 0) {

                           keys = keys +"\n "+ note.getKey();
                           String UserIds = note.getKey();
                           String na = note.child("name").getValue().toString();
                           String name =hell(na);

                           String option = note.child("option").getValue().toString();
                           String title = note.child("title").getValue().toString();
                           String article = note.child("article").getValue().toString();
                           String imgUrl = note.child("imgUrl").getValue().toString();
                           String Date = note.child("Date").getValue().toString();
                           doclist.add(new RetrieveData(UserIds, name, option, title, article, imgUrl, Date));
                           t = true;

                       }
                   }catch (Exception e)
                   {
                       if(t == true) {
                           new MyList(doclist);
                           myRecyle.setAdapter(new Item_Adap(doclist, MainActivity.this));
                           progressDialog.dismiss();

                       }
                   }
                }
                //Toast.makeText(getApplicationContext(),keys,Toast.LENGTH_LONG).show();
                if(t == true) {
                    new MyList(doclist);
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
    private String hell(String name) {
        String s=name.split("@")[0];
        String ss=s;
        return ss;

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




