package com.ketki.kquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Enter_test extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_test);

        if(getIntent().getBooleanExtra("Exit",false))
        {
            finish();
            return;
        }

        Button startbutton = (Button) findViewById(R.id.btn1);
        Button instnbutton= (Button)findViewById(R.id.btn3);

        //clicking on start button starts Start_test activity
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Start_test.class);
                    startActivity(intent);
            }
        });

        //clicking on instruction button starts instruction activity
        instnbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Instructions.class);
                startActivity(intent);
            }
        });
    }
}