package ourproject.nildlumbini;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    Button userProfileAddButton;
    RecyclerView userProfileRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        userProfileAddButton= (Button) findViewById(R.id.user_profile_add_button);
        userProfileRecycler= (RecyclerView) findViewById(R.id.user_profile_recycler);

     RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(UserProfileActivity.this,LinearLayoutManager.VERTICAL,false);
        userProfileRecycler.setLayoutManager(layoutManager);
        userProfileRecycler.setItemAnimator(new DefaultItemAnimator());



        userProfileAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfileActivity.this,DiaLog_Add.class));
            }
        });


    }
}
