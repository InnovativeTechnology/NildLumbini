package ourproject.nildlumbini;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;



public class DiaLog_Add extends AppCompatActivity {
    EditText title, article;
    Button add;
    Spinner option;

    TextView close;



    String title1="",option1="",article1="",imgUrl1="";

    StorageReference sReference;
    private ImageButton mselectImage;

    private Uri mImage = null;
    private static final int GALARY_FIELD = 1;
    String[] message;

    DatabaseReference firebaseDatabase;


    String Timestamp;

    Uri downloadUri;

    Bundle bundle;
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
                (DiaLog_Add.this, android.R.layout.simple_dropdown_item_1line, opt);
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
                Picasso.with(DiaLog_Add.this).load(message[3]).resize(200, Display.DEFAULT_DISPLAY).into(mselectImage);
            }catch (Exception e) {
                e.printStackTrace();
            }

        }


        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("UserFile");
        sReference = FirebaseStorage.getInstance().getReference();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)      {
                //  String[] bodyParts={retrieve1.title,retrieve1.option,retrieve1.article,retrieve1.imgUrl};

                final ProgressDialog progressDialog= new ProgressDialog(DiaLog_Add.this);

                if (valid()) {
                    progressDialog.setTitle("uploading..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    if(mImage!=null) {
                        if(bundle!=null) {
                            String uids = message[4];
                            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                            database.child("UserFile").child(uids).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    UploadNewFile(progressDialog);
                                }
                            });
                        }
                        else
                            UploadNewFile(progressDialog);


                    } else {
                        addChild(firebaseDatabase.push(), progressDialog, "");
                    }
                } else {
                    Toast.makeText(DiaLog_Add.this, "Please Input Valid Information..", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mselectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALARY_FIELD);
            }
        });


    }

    private void UploadNewFile(final ProgressDialog progressDialog) {
        if(bundle==null) {
            if (mImage != null) {

                StorageReference filePath = sReference.child("userimg").child(mImage.getLastPathSegment());
                filePath.putFile(mImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        downloadUri = taskSnapshot.getDownloadUrl();
                        DatabaseReference newPost = firebaseDatabase.push();
                        //  DatabaseReference newPost1 = firebaseDatabase1;
                        addChild(newPost, progressDialog, downloadUri.toString());
//                        Map<String, String> map = new HashMap<String, String>();
//                        map.put("name", FirebaseAuth.getInstance().getCurrentUser().getEmail());
//                        map.put("option", option.getSelectedItem().toString());
//                        map.put("title", title.getText().toString());
//                        map.put("article", article.getText().toString());
//                        map.put("imgUrl", downloadUri.toString());
//                        map.put("Date", Timestamp);
//                        //  newPost1.push().setValue(map);
///*
//                            startActivity(new Intent(DiaLog_Add.this, MainActivity.class));
//                            finish();*/
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DiaLog_Add.this, "Connection Problem, Try again", Toast.LENGTH_SHORT).show();

                    }
                });
            } else {
                addChild(firebaseDatabase.push(), progressDialog, "");

            }
        }else {
            addChild(firebaseDatabase.push(), progressDialog, mImage.toString());
        }
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


    private boolean valid() {
        Boolean t = true;
        String s = title.getText().toString();
        if (s.equals(""))
            t = false;
        else if (article.getText().toString().equals(""))
            t = false;
        return t;
    }


    private void addChild(DatabaseReference newPost, ProgressDialog progressDialog, String s) {
        newPost.child("name").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        newPost.child("option").setValue(option.getSelectedItem().toString());
        newPost.child("title").setValue(title.getText().toString());
        newPost.child("article").setValue(article.getText().toString());
        newPost.child("imgUrl").setValue(s);
        newPost.child("Date").setValue(Timestamp);
        progressDialog.dismiss();

        //MainActivity.activity.onStart();

        Toast.makeText(getApplicationContext(),"Successfully update!!!",Toast.LENGTH_SHORT).show();
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALARY_FIELD && resultCode == RESULT_OK) {
//          /*  Uri setUri  = data.getData();
//            CropImage.activity(setUri)
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setAspectRatio(1, 1)
//                    .start(this);
//
//        }
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
//            CropImage.ActivityResult resultImage = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImage = data.getData();
                mselectImage.setImageURI(mImage);
            }

        }


    }
    @Override
    public void onResume() {
        super.onResume();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiaLog_Add.this,UserProfileActivity.class));
                finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();

    }
}