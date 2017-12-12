package org.techtown.firebasetermproject.fragment;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.techtown.firebasetermproject.MainActivity;
import org.techtown.firebasetermproject.R;
import org.techtown.firebasetermproject.dto.ClassDTO;
import org.techtown.firebasetermproject.dto.TaskDTO;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class SecondFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    //String userID = "b@b.com";
    FirebaseDatabase database;
    private List<TaskDTO> taskDTOS = new ArrayList<>();
    private List<ClassDTO> classDTOS = new ArrayList<>();

    private TextView monday[] = new TextView[16];
    private TextView tuesday[] = new TextView[16];
    private TextView wenday[] = new TextView[16];
    private TextView thursday[] = new TextView[16];
    private TextView friday[] = new TextView[16];
    //wenday5
    @Override
    public void onActivityCreated(Bundle b) {
        //15
        super.onActivityCreated(b);
        Log.d("박정환","onActivityCreated");
        Log.d("박정환","박정환"+monday[3].getText().toString());
        setting(monday,tuesday,wenday,thursday,friday,getContext());





    }




    public SecondFragment(int page) {

        for(int i=0; i<14; i++) {
        }
        Log.d("박정환","두번째 프레그먼트");
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        //PageFragment fragment1 = new PageFragment(page ,name_Str, location_Str, state, PhoneNum);
        this.setArguments(args);
    }
/*
    public void addSchedule(ClassDTO classDTO) {
        classDTO.

    }
*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);


        Log.d("박정환","두번째 프레그먼트 oncreate");
        //Log.d("박정환",  MainActivity.userID);
        database = FirebaseDatabase.getInstance();
        database.getReference().child("task").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taskDTOS.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TaskDTO taskDTO = snapshot.getValue(TaskDTO.class);
                    taskDTOS.add(taskDTO);
                    Log.d("박정환","task");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // 클래스 조회 하는 거래 3번 돈다
        database.getReference().child("class").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("박정환", "class");
                classDTOS.clear();
                ClassDTO classDTO;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("박정환", "class1");
                    classDTO = snapshot.getValue(ClassDTO.class);
                    classDTOS.add(classDTO);
//                    Log.d("박정환",MainActivity.userID);
                }
                for( int i=0; i<taskDTOS.size(); i++) {
                    Log.d("박정환", "class2");
                    if(MainActivity.userID.equals(taskDTOS.get(i).userID)) {
                        Log.d("박정환", "class3");
                        for(int j=0; j<classDTOS.size(); j++){
                            Log.d("박정환", "class4");
                            if(taskDTOS.get(i).subject_class.equals(classDTOS.get(j).subject_class)) {
                                settingDay(classDTOS.get(j));
                                Log.d("박정환", "class5");
                                Log.d("박정환",classDTOS.get(j).day);
                                //   sett
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d("박정환","onCreate끝");
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("박정환","onCreateView");
        View view = null;
        if (mPage == 1) {
            view = inflater.inflate(R.layout.fragment_second, container, false);//fragment_page
        }

        monday[0] = (TextView) view.findViewById(R.id.monday1);
        monday[1] = (TextView) view.findViewById(R.id.monday2);
        monday[2] = (TextView) view.findViewById(R.id.monday3);
        monday[3] = (TextView) view.findViewById(R.id.monday4);
        monday[4] = (TextView) view.findViewById(R.id.monday5);
        monday[5] = (TextView) view.findViewById(R.id.monday6);
        monday[6] = (TextView) view.findViewById(R.id.monday7);
        monday[7] = (TextView) view.findViewById(R.id.monday8);
        monday[8] = (TextView) view.findViewById(R.id.monday9);
        monday[9] = (TextView) view.findViewById(R.id.monday10);
        monday[10] = (TextView) view.findViewById(R.id.monday11);
        monday[11] = (TextView) view.findViewById(R.id.monday12);
        monday[12] = (TextView) view.findViewById(R.id.monday13);
        monday[13] = (TextView) view.findViewById(R.id.monday14);
        monday[14] = (TextView) view.findViewById(R.id.monday15);

        tuesday[0] = (TextView) view.findViewById(R.id.tuesday1);
        tuesday[1] = (TextView) view.findViewById(R.id.tuesday2);
        tuesday[2] = (TextView) view.findViewById(R.id.tuesday3);
        tuesday[3] = (TextView) view.findViewById(R.id.tuesday4);
        tuesday[4] = (TextView) view.findViewById(R.id.tuesday5);
        tuesday[5] = (TextView) view.findViewById(R.id.tuesday6);
        tuesday[6] = (TextView) view.findViewById(R.id.tuesday7);
        tuesday[7] = (TextView) view.findViewById(R.id.tuesday8);
        tuesday[8] = (TextView) view.findViewById(R.id.tuesday9);
        tuesday[9] = (TextView) view.findViewById(R.id.tuesday10);
        tuesday[10] = (TextView) view.findViewById(R.id.tuesday11);
        tuesday[11] = (TextView) view.findViewById(R.id.tuesday12);
        tuesday[12] = (TextView) view.findViewById(R.id.tuesday13);
        tuesday[13] = (TextView) view.findViewById(R.id.tuesday14);
        tuesday[14] = (TextView) view.findViewById(R.id.tuesday15);


        wenday[0] = (TextView) view.findViewById(R.id.wenday1);
        wenday[1] = (TextView) view.findViewById(R.id.wenday2);
        wenday[2] = (TextView) view.findViewById(R.id.wenday3);
        wenday[3] = (TextView) view.findViewById(R.id.wenday4);
        wenday[4] = (TextView) view.findViewById(R.id.wenday5);
        wenday[5] = (TextView) view.findViewById(R.id.wenday6);
        wenday[6] = (TextView) view.findViewById(R.id.wenday7);
        wenday[7] = (TextView) view.findViewById(R.id.wenday8);
        wenday[8] = (TextView) view.findViewById(R.id.wenday9);
        wenday[9] = (TextView) view.findViewById(R.id.wenday10);
        wenday[10] = (TextView) view.findViewById(R.id.wenday11);
        wenday[11] = (TextView) view.findViewById(R.id.wenday12);
        wenday[12] = (TextView) view.findViewById(R.id.wenday13);
        wenday[13] = (TextView) view.findViewById(R.id.wenday14);
        wenday[14] = (TextView) view.findViewById(R.id.wenday15);

        thursday[0] = (TextView) view.findViewById(R.id.thursday1);
        thursday[1] = (TextView) view.findViewById(R.id.thursday2);
        thursday[2] = (TextView) view.findViewById(R.id.thursday3);
        thursday[3] = (TextView) view.findViewById(R.id.thursday4);
        thursday[4] = (TextView) view.findViewById(R.id.thursday5);
        thursday[5] = (TextView) view.findViewById(R.id.thursday6);
        thursday[6] = (TextView) view.findViewById(R.id.thursday7);
        thursday[7] = (TextView) view.findViewById(R.id.thursday8);
        thursday[8] = (TextView) view.findViewById(R.id.thursday9);
        thursday[9] = (TextView) view.findViewById(R.id.thursday10);
        thursday[10] = (TextView) view.findViewById(R.id.thursday11);
        thursday[11] = (TextView) view.findViewById(R.id.thursday12);
        thursday[12] = (TextView) view.findViewById(R.id.thursday13);
        thursday[13] = (TextView) view.findViewById(R.id.thursday14);
        thursday[14] = (TextView) view.findViewById(R.id.thursday15);

        friday[0] = (TextView) view.findViewById(R.id.friday1);
        friday[1] = (TextView) view.findViewById(R.id.friday2);
        friday[2] = (TextView) view.findViewById(R.id.friday3);
        friday[3] = (TextView) view.findViewById(R.id.friday4);
        friday[4] = (TextView) view.findViewById(R.id.friday5);
        friday[5] = (TextView) view.findViewById(R.id.friday6);
        friday[6] = (TextView) view.findViewById(R.id.friday7);
        friday[7] = (TextView) view.findViewById(R.id.friday8);
        friday[8] = (TextView) view.findViewById(R.id.friday9);
        friday[9] = (TextView) view.findViewById(R.id.friday10);
        friday[10] = (TextView) view.findViewById(R.id.friday11);
        friday[11] = (TextView) view.findViewById(R.id.friday12);
        friday[12] = (TextView) view.findViewById(R.id.friday13);
        friday[13] = (TextView) view.findViewById(R.id.friday14);
        friday[14] = (TextView) view.findViewById(R.id.friday15);

        Log.d("박정환","여기까지 가나?");

        //classDTOS.get(0).
        Log.d("박정환","여기까지 가나2?");
        return view;
    }

    public void setting(TextView[] monday, TextView[] tuesday, TextView[] wenday, TextView[] thursday, TextView[] friday, Context context) {
        for(int i = 0; i<13; i++) {
            if(!monday[i].getText().toString().equals("")) {
                Log.d("박정환", "asd"+monday[i].toString());
                monday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPink));
            }
            if(!tuesday[i].getText().toString().equals("")) {
                tuesday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPink));
            }
            if(!wenday[i].getText().toString().equals("")) {
                wenday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPink));
            }
            if(!thursday[i].getText().toString().equals("")) {
                thursday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPink));
            }
            if(!friday[i].getText().toString().equals("")) {
                friday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPink));
            }
        }
    }

    public void settingDay(ClassDTO classDTO) {
        if(classDTO.day.equals("monday")) {
            for( int i=classDTO.period; i< classDTO.period+classDTO.class_Time; i++) {
                monday[i].setText("수업");
                monday[i].setBackgroundColor(getContext().getResources().getColor(R.color.colorPink));
            }
        }
        else if (classDTO.day.equals("tuesday")){
            for( int i=classDTO.period; i< classDTO.period+classDTO.class_Time; i++) {
                tuesday[i].setText("수업");
            }
        }
        else if (classDTO.day.equals("wenday")){
            for( int i=classDTO.period; i< classDTO.period+classDTO.class_Time; i++) {
                wenday[i].setText("수업");
            }
        }
        else if ( classDTO.day.equals("thursday")){
            for( int i=classDTO.period; i< classDTO.period+classDTO.class_Time; i++) {
                thursday[i].setText("수업");
            }
        }else if ( classDTO.day.equals("friday")){
            for( int i=classDTO.period; i< classDTO.period+classDTO.class_Time; i++) {
                friday[i].setText("수업");
            }
        }
    }


}