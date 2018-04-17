package com.example.honor.myhealthlife;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button mBtnBmi, mBtnBmr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnBmi = (Button) findViewById(R.id.btnBMI);
        mBtnBmr = (Button) findViewById(R.id.btnBMR);
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

    }
}
