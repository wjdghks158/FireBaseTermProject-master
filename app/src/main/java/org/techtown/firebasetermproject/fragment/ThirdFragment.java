package org.techtown.firebasetermproject.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.techtown.firebasetermproject.R;
import org.techtown.firebasetermproject.calender.OnDayDecorator;
import org.techtown.firebasetermproject.calender.SaturdayDecorator;
import org.techtown.firebasetermproject.calender.SundayDecorator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

@SuppressLint("ValidFragment")
public class ThirdFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    View view = null;
    Vector vec;
    int firstDay;
    int totDays;
    int iYear;
    int iMonth;
    Calendar calendar;
    TextView selectDate;
    TextView selectDated;

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

    }


    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mPage == 2) {
            view = inflater.inflate(R.layout.fragment_third, container, false);//fragment_page
        }


        final MaterialCalendarView calendar = (MaterialCalendarView)view.findViewById(R.id.calendarView);

        calendar.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(com.prolificinteractive.materialcalendarview.CalendarDay.from(2017, 0, 1))
                .setMaximumDate(com.prolificinteractive.materialcalendarview.CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendar.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(), new OnDayDecorator());

        calendar.setSelectionColor(Color.RED);


        final com.prolificinteractive.materialcalendarview.CalendarDay specialDay = com.prolificinteractive.materialcalendarview.CalendarDay.from(2017, 12, 25);

        int s = specialDay.getYear();
        int ss =  specialDay.getMonth();
        int sss = specialDay.getDay();

        String s1 = Integer.toString(s);
        String s2 = Integer.toString(ss);
        String s3 = Integer.toString(sss);

        String newDate = s1 + s2+ s3;


        selectDate = (TextView)view.findViewById(R.id.dated);
        selectDated = (TextView)view.findViewById(R.id.datedd);
        selectDate.setText(newDate);



        Log.d("d", specialDay.toString() + "Ddd");


        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull com.prolificinteractive.materialcalendarview.CalendarDay date, boolean selected) {

                Context context = getActivity();
                int a = 1;

                selectDate.setText(specialDay.toString());
                selectDated.setText(date.toString());

                if(date.toString().equals(specialDay.toString())){
                    Toast.makeText(context, "oho!",
                            Toast.LENGTH_SHORT).show();
                    Log.d("PH", "honghbbbbong");
                }
                if(date.toString().compareTo(specialDay.toString()) == 0) {
                    Toast.makeText(context, "party!",
                            Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(context, "null",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }

    protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        ArrayList<CalendarDay> dates = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            com.prolificinteractive.materialcalendarview.CalendarDay day = com.prolificinteractive.materialcalendarview.CalendarDay.from(calendar);
            dates.add(day);
            calendar.add(Calendar.DATE, 5);
        }

        return dates;
    }

    public class EventDecorator implements DayViewDecorator {

        private final int color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(com.prolificinteractive.materialcalendarview.CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(5, color));
        }
    }





    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }




}
