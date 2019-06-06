package com.finalart.smartattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

    }

    public void user_signup(View view)
    {
        final EditText email=findViewById(R.id.email);
        final EditText pass=findViewById(R.id.pass);
        final EditText name=findViewById(R.id.name);
        if(email.getText().toString().isEmpty() || pass.getText().toString().isEmpty() || name.getText().toString().isEmpty())
        {
            AlertDialog.Builder sign_up_err = new AlertDialog.Builder(MainActivity.this);
            sign_up_err.setTitle("Sign Up Error");
            sign_up_err.setMessage("Please Enter Valid Details");
            sign_up_err.show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),pass.getText().toString().trim())
                    .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                FirebaseUser user = mAuth.getCurrentUser();
                                String id=mAuth.getUid();
                                AlertDialog.Builder sign_up_success = new AlertDialog.Builder(MainActivity.this);
                                sign_up_success.setTitle("Sign Up Successful");
                                sign_up_success.setMessage("Please sign in with your email and password");
                                sign_up_success.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(name.getText().toString().trim())
                                                .build();

                                        user.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Intent login_screen = new Intent(getApplicationContext(),loginpanel.class);
                                                            startActivity(login_screen);
                                                            email.setText("");
                                                            name.setText("");
                                                            pass.setText("");
                                                        }
                                                    }
                                                });

                                    }
                                });
                                sign_up_success.show();



                            }
                            else {
                                Toast.makeText(MainActivity.this,"User already registered",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void user_loginpage(View view)
    {
        Intent login_screen = new Intent(getApplicationContext(),loginpanel.class);
        startActivity(login_screen);
    }
}
