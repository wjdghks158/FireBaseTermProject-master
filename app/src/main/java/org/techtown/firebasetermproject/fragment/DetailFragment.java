package org.techtown.firebasetermproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.techtown.firebasetermproject.BusProvider;
import org.techtown.firebasetermproject.PushEvent;
import org.techtown.firebasetermproject.calender.EventDecorator;

import org.techtown.firebasetermproject.AlarmHATT;
import org.techtown.firebasetermproject.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.techtown.firebasetermproject.fragment.ThirdFragment.aa;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view = null;
    View view2 = null;
    TextView title;
    TextView detail;
    TextView Hourview;
    TextView Minview;
    Button btn_plus;
    Button btn_minus;
    Date selectDay;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail, container, false);//fragment_page
        selectDay = new Date();
        title = (TextView)view.findViewById(R.id.title_date);
        detail = (TextView)view.findViewById(R.id.detail_info);
        Hourview = (TextView)view.findViewById(R.id.hour);
        Minview = (TextView)view.findViewById(R.id.min);
        //title.setText();
        btn_plus = (Button)view.findViewById(R.id.btn_plus);
        btn_minus = (Button)view.findViewById(R.id.btn_minus);




        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CalendarDay dayPlus = aa;

                btn_plus.setText(dayPlus.toString());
                BusProvider.getInstance().post(new PushEvent(dayPlus, true));


                getHourMin(dayPlus);

//                ThirdFragment fragment = (ThirdFragment) getFragmentManager().findFragmentById(R.id.tabItem3);
//                Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
//                bundle.putString("userId", dayPlus.toString()); // key , value
//                tf.setArguments(bundle);


            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CalendarDay dayPlus = aa;

                btn_plus.setText(dayPlus.toString());

                BusProvider.getInstance().post(new PushEvent(dayPlus, false));


                getHourMin(dayPlus);

//                ThirdFragment fragment = (ThirdFragment) getFragmentManager().findFragmentById(R.id.tabItem3);
//                Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
//                bundle.putString("userId", dayPlus.toString()); // key , value
//                tf.setArguments(bundle);


            }
        });



        return view;
    }

    public CalendarDay getHourMin(CalendarDay dayPlus) {
        SimpleDateFormat formatter
                = new SimpleDateFormat("yyyyMMdd-HH-mm-ss-SSS", Locale.KOREA);

        int Hours = selectDay.getHours();
        int Min = selectDay.getMinutes()+1;
        String sHours = Integer.toString(Hours);
        String sMin = Integer.toString(Min);
        Hourview.setText(sHours);
        Minview.setText(sMin);

        new AlarmHATT(getContext()).Alarm(Hours, Min);

        return dayPlus;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroy(){



        super.onDestroy();
    }
}
