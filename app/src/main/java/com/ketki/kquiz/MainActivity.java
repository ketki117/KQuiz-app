package com.ketki.kquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    EditText Name,gmail,password;
    Button signupbtn;
    TextView tvsignup;
    FirebaseAuth mfireBaseAuth;
    String id;
    FirebaseDatabase rootNode;
    DatabaseReference mdatabaseref;
    int count=0;
    static int flag=0;

    @Override
    public void onBackPressed() {
        if(flag==0) {
            Toast.makeText(getApplicationContext(), "press again to exit!", Toast.LENGTH_SHORT).show();
            flag=1;
        }
        else
            finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mfireBaseAuth= FirebaseAuth.getInstance();
       if(mfireBaseAuth.getCurrentUser()!=null && mfireBaseAuth.getCurrentUser().isEmailVerified())
        {
            startActivity(new Intent(MainActivity.this,Enter_test.class));
        }

        setContentView(R.layout.activity_main);

      //  mfireBaseAuth= FirebaseAuth.getInstance();
        Name=(EditText)findViewById(R.id.PersonName);  //textview for name
        gmail=(EditText)findViewById(R.id.EmailAddress); //textview for gmail
        password=(EditText)findViewById(R.id.Password);  //textview for password
        signupbtn=(Button)findViewById(R.id.button3);   // sign up button
        tvsignup=(TextView)findViewById(R.id.textView); // textview for sign in

        //after signup button is clicked, check whether entered information is correct and authenticate the user corresponding the that info
        signupbtn.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "";

            @Override
            public void onClick(View v) {
                final String mailaddress= gmail.getText().toString();
                final String pwd=password.getText().toString();
                final String name=Name.getText().toString();

                if(mailaddress.isEmpty() &&  pwd.isEmpty() && name.isEmpty() )
                {
                    Toast.makeText(MainActivity.this,"Fiels are empty!",Toast.LENGTH_SHORT).show();
                }
                else if(mailaddress.isEmpty())
                {
                    gmail.setError("Please enter your email id!");
                    gmail.requestFocus();
                }
                else if(pwd.isEmpty())
                {
                    password.setError("Please enter your password!");
                    password.requestFocus();
                }
                else if(name.isEmpty())
                {
                    password.setError("Please enter your name!");
                    password.requestFocus();
                }
                else if(!(mailaddress.isEmpty() && pwd.isEmpty() && name.isEmpty() ))
                {

                    mfireBaseAuth.createUserWithEmailAndPassword(mailaddress,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            Toast.makeText(MainActivity.this,"Entered credentials are wrong or you might have already signed up.",Toast.LENGTH_SHORT).show();
                            else {
                                mfireBaseAuth.getCurrentUser().sendEmailVerification();

                                UserCredentials user = new UserCredentials(name, mailaddress, pwd);

                                rootNode = FirebaseDatabase.getInstance();
                                mdatabaseref = rootNode.getReference("users");

                                id = mfireBaseAuth.getCurrentUser().getUid();
                                mdatabaseref.child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                            Log.d(TAG, "Document was saved");
                                        else
                                            Log.w(TAG, "Document was not saved!");
                                    }
                                });

                                Toast.makeText(MainActivity.this, "Sign up successful, Please verify your email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, Login.class));
                            }
                        }
                    });
                }
                else
                    Toast.makeText(MainActivity.this, "Entered email id or password is wrong!", Toast.LENGTH_SHORT).show();
            }
        });

        //if user has already signed up, enter the login activity
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Login.class));
            }
        });

    }
}