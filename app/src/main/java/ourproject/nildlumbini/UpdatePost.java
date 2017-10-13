package ourproject.nildlumbini;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by suman on 10/13/2017.
 */

public class UpdatePost extends AppCompatActivity {

    EditText title, article;
    Button add;
    Spinner option;

    TextView close;



    String title1="",option1="",article1="",imgUrl1="";

    StorageReference sReference;
    private ImageButton mselectImage;

    private Uri mImage = null;
    String img = "";
    private static final int GALARY_FIELD = 1;
    String[] message;

    DatabaseReference firebaseDatabase;


    String Timestamp;
    Bundle bundle;
    String uids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);


        option= (Spinner) findViewById(R.id.option);

        try {
            title = (EditText) findViewById(R.id.title1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        article= (EditText) findViewById(R.id.article);
        add= (Button) findViewById(R.id.addButton);

        final String[] opt = this.getResources().getStringArray(R.array.option);
        mselectImage = (ImageButton)findViewById(R.id.selectImage);
        close = (TextView)findViewById(R.id.closePopup);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (UpdatePost.this, android.R.layout.simple_dropdown_item_1line, opt);
        option.setAdapter(adapter);

        Timestamp= ExtractDateTime.getDate();
        bundle= getIntent().getExtras();

        if(bundle!=null) {
            //  String[] bodyParts={retrieve1.title,retrieve1.option,retrieve1.article,retrieve1.imgUrl};
            message= bundle.getStringArray("message");
            option.setSelection(getIndex(option, message[1]));
            mImage = Uri.parse(message[3].toString());
            article.setText(message[2]);
            title.setText(message[0]);
            try {
                Picasso.with(UpdatePost.this).load(message[3]).resize(200, Display.DEFAULT_DISPLAY).into(mselectImage);
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

        if(new NetworkConnection(getApplicationContext()).isNetworkConnection()) {
            firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("UserFile");
            sReference = FirebaseStorage.getInstance().getReference();
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uids = message[4];
                    if (mImage != null) {
                        if (bundle != null) {
                            img = message[3];
                            //atFirstDeleteNode();
                            updateNode(img);
                        }
                    } else {
                        img = "";
                        //atFirstDeleteNode();
                        updateNode(img);
                    }
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),"There is no network connection.",Toast.LENGTH_SHORT).show();
        }

    }

    public void atFirstDeleteNode(){
        if(new NetworkConnection(UpdatePost.this).isNetworkConnection()) {
            final ProgressDialog dialog = new ProgressDialog(UpdatePost.this);
            dialog.setMessage("Deleting....");
            dialog.show();

            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            database.child("UserFile").child(uids).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(UpdatePost.this,uids, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                    //TODO restart User Profile activity

                }
            });
        }else {
            Toast.makeText(UpdatePost.this, "There is no network connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateNode(String img){
        final ProgressDialog progressDialog= new ProgressDialog(UpdatePost.this);

        progressDialog.setTitle("uploading..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        map.put("option", option.getSelectedItem().toString());
        map.put("title", title.getText().toString());
        map.put("article", article.getText().toString());
        map.put("imgUrl",img);
        map.put("Date", Timestamp);

        FirebaseDatabase.getInstance().getReference().child("UserFile").child(uids).updateChildren(map);
        progressDialog.dismiss();
        finish();
    }

    private int getIndex(Spinner option, String s) {
        int index = 0;

        for (int i=0;i<option.getCount();i++){
            if (option.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                index = i;
                break;
            }
        }
        return index;
    }
}
