package ourproject.nildlumbini;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
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
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
    private static final int GALARY_FIELD = 111;
    private static final String SHARED_PREF_NAME = "";
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

     Uri mImage = null;
    static MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;

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
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, GALARY_FIELD);
                }
            });

            String s = getProfile();
            if(s !="" || !s.equals(null)) {
                byte[] img = Base64.decode(s.getBytes(), 1);
                image.setImageBitmap(BitmapFactory.decodeByteArray(img, 0, img.length));
            }else {

            }
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALARY_FIELD && resultCode == RESULT_OK) {
            if (resultCode == RESULT_OK) {
                mImage = data.getData();
                //image.setImageURI(mImage);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImage);
                    //use the bitmap as you like
                    image.setImageBitmap(bitmap);
                    storeProfile(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public boolean storeProfile(Bitmap s) {  //InputStream s
        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        if(s!=null){
            Bitmap myBit= s;//BitmapFactory.decodeStream(s);
            ByteArrayOutputStream baos= new ByteArrayOutputStream();
            myBit.compress(Bitmap.CompressFormat.PNG,100,baos);
            byte[] b=baos.toByteArray();
            String encoded= Base64.encodeToString(b,Base64.DEFAULT);
            editor.putString("PROFILE_IMG",encoded);
        }
        else {
            editor.putString("PROFILE_IMG","");
        }
        editor.apply();

        return true;
    }
    public  String getProfile()
    {
        String s = "";
        try {
             SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
             s = sharedPreferences.getString("PROFILE_IMG", "");
        }catch (Exception e){
            s ="";
        }
        return s;
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

    public void bindData() {
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

                           //keys = keys + "\n " + note.getKey();
                           String UserIds = note.getKey();
                           String na = note.child("name").getValue().toString();
                           String name =hell(na);
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
                           new MyList(doclist);
                           myRecyle.setAdapter(new Item_Adap(doclist, MainActivity.this));
                           progressDialog.dismiss();

                       }
                   }
                }
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
        String ss="- "+s;
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




