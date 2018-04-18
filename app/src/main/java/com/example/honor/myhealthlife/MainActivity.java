package com.example.honor.myhealthlife;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button mBtnBmi, mBtnBmr;
    Handler handler = new Handler();
    TextView mTxtTime;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss aa", Locale.TAIWAN);
            mTxtTime.setText(dateFormat.format(date));
            handler.postDelayed(runnable,500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnBmi = (Button) findViewById(R.id.btnBMI);
        mBtnBmr = (Button) findViewById(R.id.btnBMR);
        mTxtTime = findViewById(R.id.textViewTime);
        mBtnBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalculateBMIActivity.class);
                startActivity(intent);
            }
        });

        mBtnBmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CalculateBMRActivity.class));
            }
        });
        handler.post(runnable);

    }
}
