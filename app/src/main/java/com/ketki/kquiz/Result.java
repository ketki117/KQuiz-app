package com.ketki.kquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Result extends AppCompatActivity {

     FirebaseAuth mfirebaseauth=FirebaseAuth.getInstance();
    DatabaseReference mdatabaseref;
    FirebaseDatabase rootNode;
    TextView t;

    static int flag=0;
    //set the value for all txt views
    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        t=(TextView)findViewById(R.id.textView7);
        fetchname(t);

        TextView t2=(TextView)findViewById(R.id.textView8);
        t2.setText(Integer.toString(Start_test.attempts));
        Start_test.attempts=0;

        TextView t3=(TextView)findViewById(R.id.textView9);
        t3.setText(Integer.toString(Start_test.correct));
        Start_test.correct=0;

        TextView t4=(TextView)findViewById(R.id.textView10);
        t4.setText(Integer.toString(Start_test.wrong));
        Start_test.wrong=0;

        TextView t5=(TextView)findViewById(R.id.textView11);
        t5.setText(Integer.toString(Start_test.score)+"/10");
        Start_test.score=0;

        Button b1=(Button)findViewById(R.id.button);
        Button b2=(Button)findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getApplicationContext(),Start_test.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(flag==0)
                {
                    flag=1;
                    Toast.makeText(getApplicationContext(),"press again to exit!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i = new Intent(getApplicationContext(), Enter_test.class);
                    i.setFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("Exit", true);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    //fetch usename from database
    public void fetchname(View view)
    {

        rootNode=FirebaseDatabase.getInstance();
        mdatabaseref=rootNode.getReference("users").child(mfirebaseauth.getCurrentUser().getUid());

        mdatabaseref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                t.setText(dataSnapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //logic for logging out
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
     switch(item.getItemId())
     {
         case R.id.logoutbutton:
         {
           FirebaseAuth.getInstance().signOut();
           mfirebaseauth=null;
           Intent in= new Intent(Result.this, MainActivity.class);
           startActivity(in);
         }
     }
     return true;
    }
}