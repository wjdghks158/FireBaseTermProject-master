package org.techtown.firebasetermproject;

import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import org.techtown.firebasetermproject.calender.EventDecorator;
import org.techtown.firebasetermproject.calender.MyDBCalendar;
import org.techtown.firebasetermproject.fragment.ThirdFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.techtown.firebasetermproject.fragment.ThirdFragment.dayday;

public class DetailActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    TextView title;
    TextView detail;
    TextView Hourview;
    TextView Minview;
    TimePickerDialog ttt;
    Button btn_plus;
    Button btn_minus;
    String tag;
    RadioGroup Gcolor;
    Calendar currentTime;
    CalendarDay dayPlus;
    String ccnt;
    int Hours;
    int Min;
    int color;
    int cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i = getIntent();

        ccnt = i.getStringExtra("date");

        currentTime = Calendar.getInstance();


        title = (TextView)findViewById(R.id.title_date);
        detail = (TextView)findViewById(R.id.detail_info);


        ttt = new TimePickerDialog(this, 2, this, currentTime.get(Calendar.HOUR), currentTime.get(Calendar.MINUTE), false);

        Gcolor = (RadioGroup)findViewById(R.id.select_color);


        Hourview = (TextView)findViewById(R.id.hour);
        Minview = (TextView)findViewById(R.id.min);

        String a = dayday.toString();
        a = a.replaceAll("[^0-9]", "");
        String b = a.substring(0, 4);
        String c = a.substring(4, 6);
        String d = a.substring(6);

        title.setText(b + "년 " + c + "월 " + d + "일을 선택하셨습니다!");
        btn_plus = (Button)findViewById(R.id.btn_plus);
        btn_minus = (Button)findViewById(R.id.btn_minus);


        btn_plus.setEnabled(false);


        cnt = Integer.parseInt(ccnt);

        Gcolor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                btn_plus.setEnabled(true);
                currentTime = Calendar.getInstance();

                if(checkedId == R.id.radio1){
                    color = Color.BLUE;
                }
                else if(checkedId == R.id.radio2){
                    color = Color.YELLOW;
                }
                else if(checkedId == R.id.radio3){
                    color = Color.RED;
                }
                else if(checkedId == R.id.radio4){
                    color = Color.GREEN;
                    ttt.show();
                }
                else{
                    btn_plus.setEnabled(false);
                }

            }
        });



        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dayPlus = dayday;

                btn_plus.setText(dayPlus.toString());


                getHourMin();

                onBackPressed();
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dayPlus = dayday;

                btn_minus.setText(dayPlus.toString());

                BusProvider.getInstance().post(new PushEvent(dayPlus, false, color));


                onBackPressed();
            }
        });

    }


    public void getHourMin() {

        if(color == Color.RED){
            Hours = currentTime.get(Calendar.HOUR_OF_DAY);
            Min = currentTime.get(Calendar.MINUTE);
            Min += 1;
        }
        else if(color == Color.YELLOW){
            Hours = currentTime.get(Calendar.HOUR_OF_DAY);
            Min = currentTime.get(Calendar.MINUTE);
            Min += 3;
        }
        else if(color == Color.BLUE){
            Hours = currentTime.get(Calendar.HOUR_OF_DAY);
            Min = currentTime.get(Calendar.MINUTE);
            Min += 5;
        }
        else{

        }


        String sHours = Integer.toString(Hours);
        String sMin = Integer.toString(Min);
        Hourview.setText(sHours);
        Minview.setText(sMin);




        //1,3,5일을 1분 3분 5분으로 축약
        if(Min > Calendar.getInstance().get(Calendar.MINUTE)){
            BusProvider.getInstance().post(new PushEvent(dayPlus, true, color));
            Log.d("PH", "jj" + Min + "nn" + Calendar.getInstance().get(Calendar.MINUTE));
            new AlarmHATT(getApplicationContext()).Alarm(Hours, Min, cnt);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        btn_plus.setEnabled(true);

        Hours = hourOfDay;
        Min = minute;

        String sHours = Integer.toString(Hours);
        String sMin = Integer.toString(Min);
        Hourview.setText(sHours);
        Minview.setText(sMin);

    }
}
