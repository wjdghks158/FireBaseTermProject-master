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
    String userID;
    FirebaseDatabase database;
    private List<TaskDTO> taskDTOS = new ArrayList<>();
    private List<ClassDTO> classDTOS = new ArrayList<>();

    private String monday[] = new String[13];
    private String tuesday[] = new String[13];
    private String wednesday[] = new String[13];
    private String thursday[] = new String[13];
    private String friday[] = new String[13];




    public SecondFragment(int page) {

        for(int i=0; i<13; i++) {
            monday[i] ="";
            tuesday[i] = "";
            wednesday[i] = "";
            thursday[i] = "";
            friday[i] = "";
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
    public void setting(TextView[] monday, TextView[] tuesday, TextView[] wednesday, TextView[] thursday, TextView[] friday, Context context) {
        for(int i = 0; i<13; i++) {
            if(!this.monday[i].equals("")) {
                monday[i].setText(this.monday[i]);
                monday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
            if(!this.tuesday[i].equals("")) {
                tuesday[i].setText(this.tuesday[i]);
                tuesday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }

            if(!this.wednesday[i].equals("")) {
                wednesday[i].setText(this.wednesday[i]);
                wednesday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }

            if(!this.thursday[i].equals("")) {
                thursday[i].setText(this.thursday[i]);
                thursday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }

            if(!this.friday[i].equals("")) {
                friday[i].setText(this.friday[i]);
                friday[i].setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
       Log.d("박정환","두번째 프레그먼트 oncreate");
        Log.d("박정환",  MainActivity.userID);
        database = FirebaseDatabase.getInstance();
        database.getReference().child("task").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taskDTOS.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TaskDTO taskDTO = snapshot.getValue(TaskDTO.class);
                    taskDTOS.add(taskDTO);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        database.getReference().child("class").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("박정환", "write9");
                classDTOS.clear();
                ClassDTO classDTO;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    classDTO = snapshot.getValue(ClassDTO.class);
                    for(int j=0; j<taskDTOS.size(); j++) {
                       Log.d("박정환","내가 수강하는 과목 : " +taskDTOS.get(j).subject_class);
                      if(classDTO.subject_class.equals(taskDTOS.get(j).subject_class.toString())){
                          classDTOS.add(classDTO);
                      }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (mPage == 1) {
            view = inflater.inflate(R.layout.fragment_second, container, false);//fragment_page
        }

        //classDTOS.get(0).

        return view;
    }
}
