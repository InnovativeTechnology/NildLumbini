package ourproject.nildlumbini;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    Button userProfileAddButton;
    RecyclerView userProfileRecycler;
    FirebaseAuth mauth;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbarFi);
        setSupportActionBar(toolbar);
        getSupportActionBar();
        mauth= FirebaseAuth.getInstance();
        userProfileAddButton = (Button) findViewById(R.id.user_profile_add_button);
        userProfileRecycler = (RecyclerView) findViewById(R.id.user_profile_recycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(UserProfileActivity.this, LinearLayoutManager.VERTICAL, false);
        userProfileRecycler.setLayoutManager(layoutManager);
        userProfileRecycler.setItemAnimator(new DefaultItemAnimator());

        userProfileAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           startActivity(new Intent(UserProfileActivity.this, DiaLog_Add.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pro,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();
        if (id == R.id.user_logout) {
            AlertDialogManage();
        }
        return true;
    }

    private void AlertDialogManage() {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(UserProfileActivity.this);
        alertbox.setTitle("Are you sure want to logout??");
        alertbox.setCancelable(false);
        alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mauth.signOut();
                startActivity(new Intent(UserProfileActivity.this,MainActivity.class));
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
}