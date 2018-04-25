package com.example.honor.myhealthlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Honor on 2018/4/17.
 */

public class CalculateBMRActivity  extends AppCompatActivity{
    EditText mEdtName, mEdtHeight, mEdtWeight, mEdtBirthday;
    Button mBtnCalculate, mBtnBack;
    TextView mTextResult;
    RadioGroup mRdgMale, mRdgFemale;
    private RadioGroup mRdgGroupSex;

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
        mRdgGroupSex = (RadioGroup) findViewById(R.id.RdgGroupSex);
        mEdtName.setText(Preference.getString(this, "name", ""));
        mEdtWeight.setText(Preference.getString(this, "weight", ""));
        mEdtHeight.setText(Preference.getString(this, "height", ""));
        switch (Preference.getString(this, "sex", "")) {
            case "先生":
                mRdgGroupSex.check(R.id.RdgMale);
                break;
            case "小姐":
                mRdgGroupSex.check(R.id.RdgFemale);
                break;
            default:
                break;
        }

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateBmr();
            }
        });
    }

    public void calculateBmr() {
        int id;
        id = mRdgGroupSex.getCheckedRadioButtonId();
        String name = mEdtName.getText().toString();
        String height = mEdtHeight.getText().toString();
        String weight = mEdtWeight.getText().toString();
        String birthday = mEdtBirthday.getText().toString();
        String sex;
        String age;

        if (name.isEmpty() || height.isEmpty() || weight.isEmpty() || birthday.isEmpty()) {
            Toast.makeText(this, "請勿留空", Toast.LENGTH_SHORT).show();
        } else {
            boolean hasSex = true;
            switch (id) {
                case R.id.RdgMale:
                    sex = "先生";
                    float bmr = Float.valueOf(weight)*13.7 + Float.valueOf(height)*5 - Integer.valueOf(age)*6.8 + 66 ;
                    break;
                case R.id.RdgFemale:
                    sex = "小姐";
                    float bmr = Float.valueOf(weight)*9.6 + Float.valueOf(height)*1.8 - Integer.valueOf(age)*4.7 + 655 ;
                    break;
                default:
                    hasSex = false;
                    sex = "";
                    break;
            }
            if (!hasSex) {
                Toast.makeText(this, "請選擇性別", Toast.LENGTH_SHORT).show();
                return;
            }
            mTextResult.setText(String.format("*%s%s您好\n您的 BMR = %.1f 大卡"
                    , name, sex, bmr ));
            Preference.setString(this, "name", name);
            Preference.setString(this, "sex", sex);
            Preference.setString(this, "weight", weight);
            Preference.setString(this, "height", height);
        }
    }
}
