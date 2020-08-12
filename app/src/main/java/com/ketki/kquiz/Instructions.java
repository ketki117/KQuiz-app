package com.ketki.kquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Instructions extends AppCompatActivity {

    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),Enter_test.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        TextView t=(TextView)findViewById(R.id.textView12);

        String s= "*The quiz tests your ability to think logically about written information, speed and subject knowledge."+"\n"+"\n"+ "In this test, you will be given questions that include some written information. Your task is to read the information provided in each question and find the correct answer."+"\n"+ "\n"+"* Once you have answered a question, you will NOT be able to go back to change your previous answer. Do not use the Back button on your browser as this may interfere with your test." + "\n"+ "\n" +"* There is no negative marking"+"\n"+ "\n"+ "*Work as quickly as you can while maintaining accuracy.";
        t.setText(s);


    }
}