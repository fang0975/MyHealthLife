package com.example.honor.myhealthlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Honor on 2018/4/17.
 */

public class CalculateBMRActivity  extends AppCompatActivity{
    EditText mEdtName, mEdtHeight, mEdtWeight, mEdtBirthday;
    Button mBtnCalculate, mBtnBack;
    TextView mTextResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_bmr);
        mEdtName = (EditText) findViewById(R.id.edtName);
        mEdtHeight = (EditText) findViewById(R.id.edtHeight);
        mEdtWeight = (EditText) findViewById(R.id.edtWeight);
        mEdtBirthday = (EditText) findViewById(R.id.edtBirthday);
        mBtnCalculate = (Button) findViewById(R.id.btnCalculate);
        mBtnBack = (Button) findViewById(R.id.btnBack);
        mTextResult =(TextView)findViewById(R.id.textResult);

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalculateBMRActivity.this,MainActivity.class));
            }
        });

        mBtnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateBmr(view);
            }
        });

    }

    public void calculateBmr(View view) {
        String name = mEdtName.getText().toString();
        String Height = mEdtHeight.getText().toString();
        String Weight = mEdtWeight.getText().toString();
        String Birthday = mEdtBirthday.getText().toString();


    }
}
