package com.ketki.kquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText gmail, password;
    Button signinbtn;
    FirebaseAuth mfireBaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mfireBaseAuth = FirebaseAuth.getInstance();
        gmail = (EditText) findViewById(R.id.EmailAddress);
        password = (EditText) findViewById(R.id.Password);
        signinbtn = (Button) findViewById(R.id.button3);

        //logic for signing in
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mailaddress = gmail.getText().toString();
                String pwd = password.getText().toString();

                if (mailaddress.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(Login.this, "Fiels are empty!", Toast.LENGTH_SHORT).show();
                } else if (mailaddress.isEmpty()) {
                    gmail.setError("Please enter your email id!");
                    gmail.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter your password!");
                    password.requestFocus();
                } else if (!(mailaddress.isEmpty() && pwd.isEmpty())) {
                    mfireBaseAuth.signInWithEmailAndPassword(mailaddress, pwd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                if(mfireBaseAuth.getCurrentUser()!=null && !mfireBaseAuth.getCurrentUser().isEmailVerified())
                                    Toast.makeText(Login.this, "You might not have verified your email or signed up yet", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(Login.this, Enter_test.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(Login.this, "Entered email id or password is wrong!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else
                    Toast.makeText(Login.this, "Entered email id or password is wrong!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}