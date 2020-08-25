package com.ketki.kquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Start_test extends AppCompatActivity {

    TextView textView,timer;
    RadioGroup rg;
    RadioButton rb1,rb2,rb3,rb4;
    Button submitbutton,quitbutton;


    String Questions[] ={"1.  31:37::19:____",
            "2.  A's age is twice the sum of ages of B and C.If after 11 years the age of a is eual to sum of ages of B and C,find age of A",
            "3.  What annual installment will discharge a debt of '2070 due in 3 years at 15%p.a. simple interest?",
            "4.  The ratio of incomes of A and B is 5:4. The ratio of expenditures of A:B is 2:3. If A's saving is eual to B's expenditure, then find the ratio of their savings.",
            "5.  Which of these access specifiers can be used for an interface?",
            "6.  If the selling price of an article is '460 and profit on it is 15%, then find the cost price?",
            "7.  What is the return type of Constructors?",
            "8.  Which of the following package stores all the standard java classes?",
            "9.  Which of these method of class String is used to compare two String objects for their equality?",
            "10.  A train, 600 meters long, crosses a bridge, 2400 meters long ,in 3 minutes. Find the speed of train in kmph"};

    String answers[] ={"23","22 years","'600","3:1","public","400","None of the mentioned","java","equals()","60kmph"};

    String options[] ={"21","27","29","23",
            "24 years","20 years","18 years","22 years",
            "'124.70","'600","'742.70","'700",
            "1:3","3:1","2:1","3:4",
            "public","protected","private","All of the mentioned",
            "450","500","400","800",
            "int","float","void","None of the mentioned",
            "lang","java","util","java.packages",
            "equals()","Equals()","isequal()","Isequal()",
            "50 kmph","45kmph","54kmph","60kmph"};


    public static int score=0,correct=0,wrong=0,attempts=0;
    public int index=0,opt=0;


    public void setQuitbutton()
    {
        if(rg.getCheckedRadioButtonId()!=-1)
        {

            RadioButton ans=(RadioButton)findViewById(rg.getCheckedRadioButtonId());
            String answer=ans.getText().toString();
            attempts++;
            if(answer.equals(answers[index]))
            {
                correct++;
                score++;
            }
            else
                wrong++;
        }

        Intent intent1=new Intent(Start_test.this ,Result.class);
        startActivity(intent1);
    }


    private int seconds=10*60;
    public void timer_()
    {
        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(seconds>0) {
                    handler.postDelayed(this,1000);
                    int min=seconds/60;
                    int sec=seconds%60;
                    String time=Integer.toString(min)+":"+Integer.toString(sec);
                    timer.setText(time);
                    seconds--;
                }
                else {
                    setQuitbutton();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"You cannot go back",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        timer=(TextView)findViewById(R.id.timer_textview);
        timer_();

        Start_test.attempts=0;
        Start_test.correct=0;
        Start_test.wrong=0;
        Start_test.score=0;

        textView=(TextView)findViewById(R.id.text);

        rg=(RadioGroup)findViewById(R.id.radiogroup);
        rb1=(RadioButton)findViewById(R.id.radio1);
        rb2=(RadioButton)findViewById(R.id.radio2);
        rb3=(RadioButton)findViewById(R.id.radio3);
        rb4=(RadioButton)findViewById(R.id.radio4);

        submitbutton=(Button)findViewById(R.id.button);
        quitbutton=(Button)findViewById(R.id.button2);

        textView.setText(Questions[index]);
        rb1.setText(options[opt]);
        rb2.setText(options[++opt]);
        rb3.setText(options[++opt]);
        rb4.setText(options[++opt]);

        submitbutton.setOnClickListener (new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                if(rg.getCheckedRadioButtonId()!=-1)
                {
                    RadioButton ans=(RadioButton)findViewById(rg.getCheckedRadioButtonId());
                    String answer=ans.getText().toString();
                    attempts++;
                    if(answer.equals(answers[index]))
                    {
                        correct++;
                        score++;
                    }
                    else
                        wrong++;
                }

                if(index+1<Questions.length)
                {
                    textView.setText(Questions[++index]);
                    rb1.setText(options[++opt]);
                    rb2.setText(options[++opt]);
                    rb3.setText(options[++opt]);
                    rb4.setText(options[++opt]);
                }
                else
                {
                    Intent intent1=new Intent(Start_test.this ,Result.class);
                    startActivity(intent1);
                }
                rg.clearCheck();
            }

        });

        quitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setQuitbutton();
            }
        });
    }
}
