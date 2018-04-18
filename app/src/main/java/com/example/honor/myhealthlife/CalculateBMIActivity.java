package com.example.honor.myhealthlife;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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

public class CalculateBMIActivity extends AppCompatActivity {
    EditText mEdtName, mEdtHeight, mEdtWeight, mEdtWaistsize;
    Button mBtnCalculate, mBtnBack;
    SharedPreferences userInfo;
    TextView mTextResult;
    RadioGroup mRdgMale, mRdgFemale;
    private RadioGroup mRdgGroupSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_bmi);
        mEdtName = (EditText) findViewById(R.id.edtName);
        mEdtHeight = (EditText) findViewById(R.id.edtHeight);
        mEdtWeight = (EditText) findViewById(R.id.edtWeight);
        mEdtWaistsize = (EditText) findViewById(R.id.edtWaistsize);
        mBtnCalculate = (Button) findViewById(R.id.btnCalculate);
        mBtnBack = (Button) findViewById(R.id.btnBack);
        mTextResult = (TextView) findViewById(R.id.textResult);
        mRdgGroupSex = (RadioGroup) findViewById(R.id.RdgGroupSex);
        mEdtName.setText(Preference.getString(this, "name", ""));
        mEdtWeight.setText(Preference.getString(this, "weight", ""));
        mEdtHeight.setText(Preference.getString(this, "height", ""));
        mEdtWaistsize.setText(Preference.getString(this, "waistSize", ""));
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
                calculateBmi();
            }
        });

    }

    @SuppressLint("DefaultLocale")
    public void calculateBmi() {
        int id;
        String bmiHint;
        id = mRdgGroupSex.getCheckedRadioButtonId();
        String name = mEdtName.getText().toString();
        String weight = mEdtWeight.getText().toString();
        String height = mEdtHeight.getText().toString();
        String waistSize = mEdtWaistsize.getText().toString();
        String sex;
        String waistSizeHint;
        if (name.isEmpty() || height.isEmpty() || weight.isEmpty() || waistSize.isEmpty()) {
            Toast.makeText(this, "請勿留空", Toast.LENGTH_SHORT).show();
        } else {
            float bmi = Float.valueOf(weight) / (Float.valueOf(height) / 100f) / (Float.valueOf(height) / 100f);
            if (bmi < 18.5)
                bmiHint = "體重過輕";
            else if (bmi < 24)
                bmiHint = "健康體位";
            else if (bmi < 27)
                bmiHint = "體重過重";
            else if (bmi < 30)
                bmiHint = "輕度肥胖";
            else if (bmi < 35)
                bmiHint = "中度肥胖";
            else
                bmiHint = "重度肥胖";
            boolean hasSex = true;
            switch (id) {
                case R.id.RdgMale:
                    sex = "先生";
                    if (Float.valueOf(waistSize) >= 90)
                        waistSizeHint = "超過標準";
                    else
                        waistSizeHint = "標準";
                    break;
                case R.id.RdgFemale:
                    sex = "小姐";
                    if (Float.valueOf(waistSize) >= 80)
                        waistSizeHint = "超過標準";
                    else
                        waistSizeHint = "標準";
                    break;
                default:
                    hasSex = false;
                    sex = "";
                    waistSizeHint = "";
                    break;
            }
            if (!hasSex) {
                Toast.makeText(this, "請選擇性別", Toast.LENGTH_SHORT).show();
                return;
            }
            mTextResult.setText(String.format("*%s%s您好\n您的 BMI = %.2f(%s)\n腰圍 %.1f 公分(%s)"
                    , name, sex, bmi, bmiHint, Float.valueOf(waistSize), waistSizeHint));
            Preference.setString(this, "name", name);
            Preference.setString(this, "sex", sex);
            Preference.setString(this, "weight", weight);
            Preference.setString(this, "height", height);
            Preference.setString(this, "waistSize", waistSize);
        }
    }


}
