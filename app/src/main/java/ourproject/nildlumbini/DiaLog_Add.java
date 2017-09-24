package ourproject.nildlumbini;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class DiaLog_Add extends AppCompatActivity {
    EditText title,article;
    Button add;
    Spinner option;

    StorageReference sReference;
    private ImageButton mselectImage;

    private Uri mImage = null;
    private static final int GALARY_FIELD = 1;


    DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        option= (Spinner) findViewById(R.id.option);
        final String[] opt = this.getResources().getStringArray(R.array.option);
        mselectImage = (ImageButton)findViewById(R.id.selectImage);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (DiaLog_Add.this, android.R.layout.simple_dropdown_item_1line,opt);
        option.setAdapter(adapter);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("UserFile");

        sReference = FirebaseStorage.getInstance().getReference();

try {
    title = (EditText) findViewById(R.id.title1);
}
catch (Exception e)
{
    e.printStackTrace();
}
        article= (EditText) findViewById(R.id.article);
        add= (Button) findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)      {
                final ProgressDialog progressDialog= new ProgressDialog(DiaLog_Add.this);
                progressDialog.setTitle("uploading..");
                progressDialog.setCancelable(false);
                progressDialog.show();
                StorageReference filePath = sReference.child("userimg").child(mImage.getLastPathSegment());
                filePath.putFile(mImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                   String  Timestamp = ExtractDateTime.getDate();
                    DatabaseReference newPost = firebaseDatabase.push();

                    newPost.child("name").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    newPost.child("option").setValue(option.getSelectedItem().toString());
                    newPost.child("title").setValue(title.getText().toString());
                    newPost.child("article").setValue(article.getText().toString());
                    newPost.child("imgUrl").setValue(downloadUri.toString());
                    newPost.child("Date").setValue(Timestamp);
                    progressDialog.dismiss();
                    startActivity(new Intent(DiaLog_Add.this,MainActivity.class));
                    finish();
                }
            });}
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALARY_FIELD && resultCode == RESULT_OK){
          /*  Uri setUri  = data.getData();
            CropImage.activity(setUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult resultImage = CropImage.getActivityResult(data);
*/            if(resultCode == RESULT_OK){
                mImage = data.getData();
                mselectImage.setImageURI(mImage);
            }

        }
    }
}
