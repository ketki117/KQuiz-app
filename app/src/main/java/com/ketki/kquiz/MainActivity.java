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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String username="name",m_id="mail_id",pass="PassWord";
    EditText Name,gmail,password;
    Button signupbtn;
    TextView tvsignup;
    FirebaseAuth mfireBaseAuth;

    private static DocumentReference mdocref=FirebaseFirestore.getInstance().document("user/details");

    static int flag=0;


    public static DocumentReference getMdocref()
    {
        return mdocref;
    }

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
        setContentView(R.layout.activity_main);

        mfireBaseAuth= FirebaseAuth.getInstance();
        Name=(EditText)findViewById(R.id.PersonName);
        gmail=(EditText)findViewById(R.id.EmailAddress);
        password=(EditText)findViewById(R.id.Password);
        signupbtn=(Button)findViewById(R.id.button3);
        tvsignup=(TextView)findViewById(R.id.textView);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "";

            @Override
            public void onClick(View v) {
                String mailaddress= gmail.getText().toString();
                String pwd=password.getText().toString();
                String name=Name.getText().toString();

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
                            else
                            {
                                String username=Name.getText().toString();
                                Map<String,Object> datatosave=new HashMap<>();
                                datatosave.put("name",username);
                                mdocref.set(datatosave).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });

                                Toast.makeText(MainActivity.this,"Sign up successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this,Enter_test.class));
                            }
                        }
                    });

                    Map<String,Object> datatosave=new HashMap<String,Object>();
                    datatosave.put(username,name);
                    datatosave.put(m_id,mailaddress);
                    datatosave.put(pass,pwd);

                    mdocref.set(datatosave).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                Log.d(TAG,"Document was saved");
                            else
                                Log.w(TAG,"Document was not saved!");
                        }
                    });

                }
                else
                    Toast.makeText(MainActivity.this, "Entered email id or password is wrong!", Toast.LENGTH_SHORT).show();
            }
        });

        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Login.class));
            }
        });

    }
}