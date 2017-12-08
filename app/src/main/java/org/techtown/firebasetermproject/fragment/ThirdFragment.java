package org.techtown.firebasetermproject.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import android.graphics.Color;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.techtown.firebasetermproject.BusProvider;
import org.techtown.firebasetermproject.DetailActivity;
import org.techtown.firebasetermproject.PushEvent;
import org.techtown.firebasetermproject.R;
import org.techtown.firebasetermproject.calender.EventDecorator;
import org.techtown.firebasetermproject.calender.OnDayDecorator;
import org.techtown.firebasetermproject.calender.SaturdayDecorator;
import org.techtown.firebasetermproject.calender.SundayDecorator;

@SuppressLint("ValidFragment")
public class ThirdFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private long btnPressTime = 0;
    public MaterialCalendarView calendar;
    View view = null;
    Vector vec;
    int firstDay;
    int totDays;
    int iYear;
    int iMonth;
    int doubleCount = 0;
    String tag;
    TextView selectDate;
    TextView selectDated;
    FrameLayout detail_frag;

    static public CalendarDay aa;

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


    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mPage == 2) {
            view = inflater.inflate(R.layout.fragment_third, container, false);//fragment_page
        }


        calendar = (MaterialCalendarView)view.findViewById(R.id.calendarView);

        dates = new Collection<CalendarDay>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<CalendarDay> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] a) {
                return null;
            }

            @Override
            public boolean add(CalendarDay calendarDay) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends CalendarDay> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }
        };

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

        calendar.addDecorator(new EventDecorator(Color.BLUE, specialDay));

        int s = specialDay.getYear();
        int ss =  specialDay.getMonth();
        int sss = specialDay.getDay();

        String s1 = Integer.toString(s);
        String s2 = Integer.toString(ss);
        String s3 = Integer.toString(sss);

        String newDate = s1 + s2+ s3;


        //selectDate = (TextView)view.findViewById(R.id.dated);
        //selectDated = (TextView)view.findViewById(R.id.datedd);
        //selectDate.setText(newDate);


        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                CalendarDay firstDate = date;
                Context context = getActivity();

                Log.d("d", "zzzzzzz");


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
//                        Intent detail = new Intent(context, DetailActivity.class);
//                        detail.putExtra("date", specialDay.toString());
//                        detail.putExtra("tag", ThirdFragment.this.getTag());
//                        startActivity(detail);

                        DetailFragment df = new DetailFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
                        transaction.add(R.id.detail_view, df);
                        transaction.addToBackStack(null);

// Commit the transaction
                        transaction.commit();
                    }
                    else if(firstDate.toString().equals(date.toString())){
                        Intent detail = new Intent(context, DetailActivity.class);
                        detail.putExtra("date", date.toString());
                        detail.putExtra("tag", tag);
                        startActivity(detail);

//                        DetailFragment df = new DetailFragment();
//
//                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                        transaction.add(R.id.detail_view, df);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
                    }
                    aa= date;

                }

            }
        });

        return view;

    }

    @Subscribe
    public void FinishLoad(PushEvent mPushEvent) {

        CalendarDay day = mPushEvent.getList();
        boolean OnOff = mPushEvent.getOnoff();

        //selectDated.setText(day.toString());

        if(OnOff) {
            calendar.addDecorator(new EventDecorator(Color.RED, day));
        }
        else{
            calendar.removeDecorator(new DayViewDecorator() {
                @Override
                public boolean shouldDecorate(CalendarDay day) {
                    return false;
                }

                @Override
                public void decorate(DayViewFacade view) {
                    view.addSpan(new DotSpan(0,0));
                }

            });
        }

        calendar.invalidateDecorators();

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
            CalendarDay day = CalendarDay.from(calendar);
            dates.add(day);
            calendar.add(Calendar.DATE, 5);
        }

        return dates;
    }







    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }



}
