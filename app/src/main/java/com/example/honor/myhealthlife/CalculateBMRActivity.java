package com.example.honor.myhealthlife;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by Honor on 2018/4/17.
 */

public class CalculateBMRActivity extends AppCompatActivity implements OnInitListener {
    EditText mEdtName, mEdtHeight, mEdtWeight, mEdtBirthday;
    Button mBtnCalculate, mBtnBack;
    TextView mTextResult;
    RadioGroup mRdgMale, mRdgFemale;
    private RadioGroup mRdgGroupSex;
    int year, month, day, age = 0;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(YEAR);
        month = calendar.get(MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setContentView(R.layout.activity_calculate_bmr);
        mEdtName = (EditText) findViewById(R.id.edtName);
        mEdtHeight = (EditText) findViewById(R.id.edtHeight);
        mEdtWeight = (EditText) findViewById(R.id.edtWeight);
        mEdtBirthday = (EditText) findViewById(R.id.edtBirthday);
        mBtnCalculate = (Button) findViewById(R.id.btnCalculate);
        mBtnBack = (Button) findViewById(R.id.btnBack);
        mTextResult = (TextView) findViewById(R.id.textResult);
        mRdgGroupSex = (RadioGroup) findViewById(R.id.RdgGroupSex);
        mEdtName.setText(Preference.getString(this, "name", ""));
        mEdtWeight.setText(Preference.getString(this, "weight", ""));
        mEdtHeight.setText(Preference.getString(this, "height", ""));
        tts = new TextToSpeech(this,this);
        getSupportActionBar().setTitle("計算BMR-基礎代謝率");
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

        mEdtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CalculateBMRActivity.this, AlertDialog.THEME_HOLO_LIGHT, dateSetListener, year, month, day)
                        .show();
            }
        });

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

        if (name.isEmpty() || height.isEmpty() || weight.isEmpty() || birthday.isEmpty()) {
            Toast.makeText(this, "請勿留空", Toast.LENGTH_SHORT).show();
        } else {
            boolean hasSex = true;
            double bmr;
            switch (id) {
                case R.id.RdgMale:
                    sex = "先生";
                    bmr = Float.valueOf(weight) * 13.7 + Float.valueOf(height) * 5 - Integer.valueOf(age) * 6.8 + 66;
                    break;
                case R.id.RdgFemale:
                    sex = "小姐";
                    bmr = Float.valueOf(weight) * 9.6 + Float.valueOf(height) * 1.8 - Integer.valueOf(age) * 4.7 + 655;
                    break;
                default:
                    hasSex = false;
                    sex = "";
                    bmr = 0f;
                    break;
            }
            if (!hasSex) {
                Toast.makeText(this, "請選擇性別", Toast.LENGTH_SHORT).show();
                return;
            }
            mTextResult.setText(String.format("*%s%s您好\n您的 BMR = %.1f 大卡"
                    , name, sex, bmr));
            Preference.setString(this, "name", name);
            Preference.setString(this, "sex", sex);
            Preference.setString(this, "weight", weight);
            Preference.setString(this, "height", height);
            tts.speak(mTextResult.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            @SuppressLint("DefaultLocale") String msg = String.format("%d / %d / %d", year, monthOfYear + 1, dayOfMonth);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            age = getOld(calendar.getTime());
            // Toast.makeText(CalculateBMRActivity.this, msg, Toast.LENGTH_SHORT).show();
            mEdtBirthday.setText(String.format("%d / %d / %d ( %d歲 )",year,month,day,age));
        }
    };

    private int getOld(Date first) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(new Date());
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) || a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE)) {
            diff--;
        }
        return diff;
    }

    private int getOldByDate(Date first) {
        long time = new Date().getTime() - first.getTime();
        Calendar a = Calendar.getInstance();
        a.setTimeInMillis(time);
        return a.get(YEAR) - 1970;
    }

    private Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }
    public void onInit(int status) {
        // TODO Auto-generated method stub
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.TAIWAN);    //設定語言為英文
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                tts.setPitch(1);    //語調(1為正常語調；0.5比正常語調低一倍；2比正常語調高一倍)
                tts.setSpeechRate(1);    //速度(1為正常速度；0.5比正常速度慢一倍；2比正常速度快一倍)
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    @Override
    public void onDestroy() {
        // shutdown tts
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
