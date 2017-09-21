package ourproject.nildlumbini;

<<<<<<< HEAD
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
=======
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.R.attr.button;
import static android.R.attr.password;
>>>>>>> 502e3d35eac84a072e1157e5b027d83919ea6d05

public class MainActivity extends AppCompatActivity {
    EditText email,pass;
    Button login;
    private FirebaseAuth firebaseAuth;

    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
        toolbar= (Toolbar) findViewById(R.id.toolbarFirst);
        navigationView= (NavigationView) findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);

        getSupportActionBar().setTitle("");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                if(item.getItemId()==R.id.user_profile)
                {
                    Intent i = new Intent(MainActivity.this, UserProfileActivity.class);
                    startActivity(i);
                }

                return true;
            }
        });

=======
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.password);
        firebaseAuth=FirebaseAuth.getInstance();
        login=(Button)findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             final ProgressDialog p= ProgressDialog.show(MainActivity.this,"Wait for a minute","Waiting",true);
                (firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                              p.dismiss();

                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getApplicationContext(),"login sucessful",Toast.LENGTH_SHORT).show();
                                }else
                                {
                                    Toast.makeText(getApplicationContext(),"login  is not  sucessful",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
>>>>>>> 502e3d35eac84a072e1157e5b027d83919ea6d05
    }
}
