package com.finalart.smartattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginpanel extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpanel);
        mAuth = FirebaseAuth.getInstance();

    }

    public void user_login(View view)
    {
        final EditText email_ln=findViewById(R.id.email_ln);
        final EditText pass_ln=findViewById(R.id.pass_ln);
        TextView sigun_back = findViewById(R.id.sign_back);
        if(email_ln.getText().toString().isEmpty() || pass_ln.getText().toString().isEmpty())
        {  AlertDialog.Builder sign_in_err = new AlertDialog.Builder(loginpanel.this);
            sign_in_err.setTitle("Sign In Error");
            sign_in_err.setMessage("Please Enter Valid Details");
            sign_in_err.show();}
        else {
            mAuth.signInWithEmailAndPassword(email_ln.getText().toString().trim(), pass_ln.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent qr_atten = new Intent(getApplicationContext(), qr_attendance.class);
                                startActivity(qr_atten);
                            } else {
                                Toast.makeText(loginpanel.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    public void user_signback(View view)
    {
        Intent back_act = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(back_act);
    }
}
