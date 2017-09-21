package ourproject.nildlumbini;

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

public class MainActivity extends AppCompatActivity {
    EditText email,pass;
    Button login;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }
}
