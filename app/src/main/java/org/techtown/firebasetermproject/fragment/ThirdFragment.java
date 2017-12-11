package org.techtown.firebasetermproject.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Color;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.techtown.firebasetermproject.BusProvider;
import org.techtown.firebasetermproject.DetailActivity;
import org.techtown.firebasetermproject.PushEvent;
import org.techtown.firebasetermproject.R;
import org.techtown.firebasetermproject.calender.EventDecorator;
import org.techtown.firebasetermproject.calender.MyDBCalendar;
import org.techtown.firebasetermproject.calender.OnDayDecorator;
import org.techtown.firebasetermproject.calender.SaturdayDecorator;
import org.techtown.firebasetermproject.calender.SundayDecorator;

@SuppressLint("ValidFragment")
public class ThirdFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private static final String APP_SAVE_NAME = "saveCalendar";
    private int mPage;
    private long btnPressTime = 0;
    public MaterialCalendarView calendar;

    View view = null;
    Vector vec;
    int firstDay;
    int totDays;
    int iYear;
    int iMonth;
    TextView selectDate;
    TextView selectDated;
    TextView listCalendar;
    Button setCalender;
    public static CalendarDay dayday;

    final static String TAG="SQLITEDBTEST";
    private MyDBCalendar helper;

    List<CalendarDay> listDay;
    static ArrayList<String> saveDay;
    static ArrayList<Integer> saveColor;


    Collection<CalendarDay> dates;

    @SuppressLint("ValidFragment")
    public ThirdFragment(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        //PageFragment fragment1 = new PageFragment(page ,name_Str, location_Str, state, PhoneNum);
        this.setArguments(args);





    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

        BusProvider.getInstance().register(this);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mPage == 2) {
            view = inflater.inflate(R.layout.fragment_third, container, false);//fragment_page


            saveDay = new ArrayList<>();
            saveColor = new ArrayList<>();
            helper = new MyDBCalendar(getContext());

        }


        calendar = (MaterialCalendarView)view.findViewById(R.id.calendarView);

        listCalendar = (TextView)view.findViewById(R.id.calendarList);



        calendar.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendar.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(), new OnDayDecorator());

        calendar.setSelectionColor(Color.RED);

        final CalendarDay specialDay = CalendarDay.from(2017, 11, 25); // -1필요



        //calendar.addDecorator(new EventDecorator(Color.BLUE, specialDay));


        //저장된 달력 등록
        String sql = "Select * FROM calendar";
        Cursor cursor = helper.getReadableDatabase().rawQuery(sql,null);
        StringBuffer buffer = new StringBuffer();
        String s[];
        while (cursor.moveToNext()) {

            //buffer.append(cursor.getString(1)+"\t");
            buffer.append(cursor.getString(0)+",");

            if(!saveDay.contains(cursor.getString(0))) {
                saveDay.add(cursor.getString(0) + "\t");
                saveColor.add(cursor.getInt(1));

            }

            if(!saveDay.isEmpty()) {
                for(int i=0; i<saveDay.size(); i++){
                    String a = saveDay.get(i);
                    int color = saveColor.get(i);

                    a = a.replaceAll("[^0-9]", "");
                    String b = a.substring(0, 4);
                    String c = a.substring(4, 6);
                    String d = a.substring(6);

                    int year = Integer.parseInt(b);
                    int month = Integer.parseInt(c);
                    int day = Integer.parseInt(d);

                    CalendarDay dday = CalendarDay.from(year, month, day);

                    calendar.addDecorator(new EventDecorator(color, dday));

                    //listCalendar.setText(color);
                }
            }
        }



        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                CalendarDay firstDate = date;
                Context context = getActivity();

                //selectDate.setText(specialDay.toString());
                //selectDated.setText(date.toString());

                if (System.currentTimeMillis() > btnPressTime + 300) {
                    btnPressTime = System.currentTimeMillis();
                    Toast.makeText(context, "한번 더 터치하면 실행됩니다.",
                            Toast.LENGTH_SHORT).show();
                    firstDate = date;
                    return;
                }
                if (System.currentTimeMillis() <= btnPressTime + 1000) {
                    if(date.toString().equals(specialDay.toString())){

                        Intent detail = new Intent(context, DetailActivity.class);

                        detail.putExtra("date", specialDay.toString());
                        startActivity(detail);



                    }
                    else if(firstDate.toString().equals(date.toString())){
                        Intent detail = new Intent(context, DetailActivity.class);
                        detail.putExtra("date", date.toString());
                        startActivity(detail);

                    }
                    dayday = date;
                }

            }
        });

        return view;

    }

    @Subscribe
    public void FinishLoad(PushEvent mPushEvent) {

        CalendarDay day = mPushEvent.getList();
        boolean OnOff = mPushEvent.getOnoff();
        int color = mPushEvent.getColor();
        Log.d("PH", "color %"+ color);
        if(OnOff) {

            calendar.addDecorator(new EventDecorator(color, day));

            try {
                String sql = String.format (
                        "INSERT INTO calendar (day, color)\n"+ "VALUES ('%s', '%d')"
                        ,
                        day.toString(), color);
                helper.getWritableDatabase().execSQL(sql);

                Log.d("PH", sql);
            } catch (android.database.SQLException e) {
                Log.e(TAG,"Error inserting into DB");
            }


//            saveSharedPreferences_Data(getContext(), "day", saveDay);
//            count++;

        }
        else{
            color = 0x00FF00000;
            try {
                String sql = String.format (
                        "DELETE FROM calendar "+ "WHERE day = '%s'"
                        ,
                        day.toString());
                helper.getWritableDatabase().execSQL(sql);
                saveDay.remove(day.toString());
                Log.d("PH", sql);
            }catch (android.database.SQLException e){

            }



            calendar.addDecorator(new EventDecorator(color, day));

        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commitAllowingStateLoss();
        //calendar.invalidateDecorators();

    }


    @Override
    public void onStart(){
        super.onStart();


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }



}
