package org.techtown.firebasetermproject;

import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Intent;
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
    TimePicker tt;
    TimePickerDialog ttt;
    Button btn_plus;
    Button btn_minus;
    String tag;
    CalendarDay dday;
    RadioButton red;
    RadioButton blue;
    RadioButton yellow;
    RadioGroup Gcolor;
    Calendar currentTime;
    int Hours;
    int Min;
    int color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i = getIntent();

        tag = i.getStringExtra("date");

        currentTime = Calendar.getInstance();

        title = (TextView)findViewById(R.id.title_date);
        detail = (TextView)findViewById(R.id.detail_info);

        tt = (TimePicker)findViewById(R.id.select_time);
        ttt = new TimePickerDialog(getApplicationContext(), this, currentTime.get(Calendar.HOUR), currentTime.get(Calendar.MINUTE), false);
        Gcolor = (RadioGroup)findViewById(R.id.select_color);


        Hourview = (TextView)findViewById(R.id.hour);
        Minview = (TextView)findViewById(R.id.min);
        title.setText(dayday.toString());
        btn_plus = (Button)findViewById(R.id.btn_plus);
        btn_minus = (Button)findViewById(R.id.btn_minus);

        btn_plus.setEnabled(false);


        Gcolor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio1){
                    color = Color.BLUE;
                }
                else if(checkedId == R.id.radio2){
                    color = Color.YELLOW;
                }
                else if(checkedId == R.id.radio3){
                    color = Color.RED;
                }
                else
                    color = Color.GREEN;

                btn_plus.setEnabled(true);
            }
        });



        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CalendarDay dayPlus = dayday;

                btn_plus.setText(dayPlus.toString());
                Log.d("PH", dayPlus.toString());
                BusProvider.getInstance().post(new PushEvent(dayPlus, true, color));


                ttt.show();

                getHourMin();

            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CalendarDay dayPlus = dayday;

                btn_minus.setText(dayPlus.toString());

                BusProvider.getInstance().post(new PushEvent(dayPlus, false, color));



            }
        });


    }


    public void getHourMin() {
        SimpleDateFormat formatter
                = new SimpleDateFormat("yyyyMMdd-HH-mm-ss-SSS", Locale.KOREA);


        tt.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Hours = hourOfDay;
                Min = minute;
            }

        });

        if(color == Color.RED){
            Min += 1;
        }
        else if(color == Color.YELLOW){
            Min += 3;
        }
        else if(color == Color.BLUE){
            Min += 5;
        }

        String sHours = Integer.toString(Hours);
        String sMin = Integer.toString(Min);
        Hourview.setText(sHours);
        Minview.setText(sMin);

        new AlarmHATT(getApplicationContext()).Alarm(Hours, Min);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}
