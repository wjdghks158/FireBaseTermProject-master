package org.techtown.firebasetermproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import org.techtown.firebasetermproject.calender.EventDecorator;
import org.techtown.firebasetermproject.fragment.ThirdFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.techtown.firebasetermproject.fragment.ThirdFragment.aa;

public class DetailActivity extends AppCompatActivity {

    TextView title;
    TextView detail;
    TextView Hourview;
    TextView Minview;
    Button btn_plus;
    Button btn_minus;
    Date selectDay;
    String tag;
    private ThirdFragment tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent i = getIntent();
        String date = i.getStringExtra("date");
        tag = i.getStringExtra("tag");

        selectDay = new Date();

        title = (TextView)findViewById(R.id.title_date);
        detail = (TextView)findViewById(R.id.detail_info);
        Hourview = (TextView)findViewById(R.id.hour);
        Minview = (TextView)findViewById(R.id.min);
        title.setText(date);
        btn_plus = (Button)findViewById(R.id.btn_plus);
        btn_minus = (Button)findViewById(R.id.btn_minus);




        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThirdFragment tf = ((ThirdFragment)getSupportFragmentManager().findFragmentByTag(tag));


                CalendarDay dayPlus = aa;

                btn_plus.setText(dayPlus.toString());
                BusProvider.getInstance().post(new PushEvent(dayPlus, true));


                //getHourMin(dayPlus);

            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CalendarDay dayPlus = aa;

                btn_plus.setText(dayPlus.toString());

                BusProvider.getInstance().post(new PushEvent(dayPlus, false));


                //getHourMin(dayPlus);


            }
        });


    }

    public void getHourMin() {
        SimpleDateFormat formatter
                = new SimpleDateFormat("yyyyMMdd-HH-mm-ss-SSS", Locale.KOREA);

        int Hours = selectDay.getHours();
        int Min = selectDay.getMinutes()+1;
        String sHours = Integer.toString(Hours);
        String sMin = Integer.toString(Min);
        Hourview.setText(sHours);
        Minview.setText(sMin);

        new AlarmHATT(getApplicationContext()).Alarm(Hours, Min);
    }
}
