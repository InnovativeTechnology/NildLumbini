package ourproject.nildlumbini;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText email, pass;
    Button login;
    FirebaseAuth mauth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.btnLogin);

       try {
            mauth = FirebaseAuth.getInstance();
            mUser = FirebaseAuth.getInstance().getCurrentUser();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
       if(mUser!=null)
        {
            Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
            startActivity(intent);
            finish();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();

            }
        });
    }
    private void logIn() {

        boolean isValid = true;

        if (email.getText().toString().trim().length() < 6 || !StaticClassUtils.isEmailFormatValid(email.getText().toString())) {
            email.setError("Enter valid user name");
            isValid = false;
        }
        if (pass.getText().toString().trim().length() < 6) {
            pass.setError("Minimum 6 characters required");
            isValid = false;
        }

        if (isValid) {
            final ProgressDialog p = ProgressDialog.show(LoginActivity.this, "Wait for a minute", "Waiting", true);
            if(new NetworkConnection(getApplicationContext()).isNetworkConnection()) {
                mauth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                        p.dismiss();

                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "login  is not  sucessful", Toast.LENGTH_SHORT).show();
                        }

                        }
                    });
            }else {
                Toast.makeText(getApplicationContext(), "There is no network connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
