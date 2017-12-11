package org.techtown.firebasetermproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.techtown.firebasetermproject.dto.ClassDTO;
import org.techtown.firebasetermproject.dto.NotiDTO;
import org.techtown.firebasetermproject.dto.PostDTO;
import org.techtown.firebasetermproject.dto.TaskDTO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WriteActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    TextView subj;
    Button btn_push;
    Button btn_write;
    FirebaseDatabase database;
    FirebaseDatabase database2;
    DatabaseReference myRef;
    String userID;
    TextView tv;
    TextView date_tv;
    DatePickerDialog dialog;

    long time;
    SimpleDateFormat yyyy;
    SimpleDateFormat mm;
    SimpleDateFormat dd;


    String year;
    String month ;
    String day;

    String regId;

    private List<ClassDTO> classDTOS = new ArrayList<>();
    private ArrayList<String> subject_class_List = new ArrayList<>();




    String[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

         time = System.currentTimeMillis();

         yyyy = new SimpleDateFormat("yyyy");
         mm = new SimpleDateFormat("MM");
         dd = new SimpleDateFormat("dd");


         year = yyyy.format(new Date(time));
         month = mm.format(new Date(time));
         day = dd.format(new Date(time));



        tv = (TextView)findViewById(R.id.textView1);
        date_tv = (TextView)findViewById(R.id.date_tv);
        date_tv.setText(year + "년" + month + "월" + day +"일");
        Intent ii = getIntent();
        userID = ii.getStringExtra("USERID");

        btn_push = (Button) findViewById(R.id.push);
        btn_write = (Button) findViewById(R.id.write_button);
        subj = (TextView)findViewById(R.id.title);
        // Write a message to the database
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
items = new String[10];
        if(user == null) {
            finish();
            return ;
        }
        database2 = FirebaseDatabase.getInstance();
        database2.getReference().child("class").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                classDTOS.clear();
                int i =0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ClassDTO classDTO = snapshot.getValue(ClassDTO.class);
                    classDTOS.add(classDTO);

                    items[i] = classDTO.subject_class;
                    subject_class_List.add(classDTO.subject_class);
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Spinner s = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        Log.d("박정환", "write3");
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv.setText(items[position]);
                Log.d("박정환","onItemSelected");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("박정환","onNothingSelected");
                tv.setText("");

            }
        });



        btn_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ((EditText)findViewById(R.id.edit_title)).getText().toString();
                String body = ((EditText)findViewById(R.id.edit_body)).getText().toString();
                String subject_class = ((TextView)findViewById(R.id.textView1)).getText().toString();
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("post");
                String key = myRef.push().getKey();
                HashMap<String, Object> postValues =  new HashMap<>();
                postValues.put("userID", userID);
                postValues.put("class", subject_class);
                postValues.put("title", title);
                postValues.put("body", body);
                postValues.put("deadline", time);

                myRef.child(key).setValue(postValues).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("박정환","onComplete시작");
                        sendData();
                        Log.d("박정환","onComplete끝남");
                    }
                });

               // send(subject_class);

            }
        });

        getRegistrationId();

        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ((EditText)findViewById(R.id.edit_title)).getText().toString();
                String body = ((EditText)findViewById(R.id.edit_body)).getText().toString();
                String subject_class = ((TextView)findViewById(R.id.textView1)).getText().toString();
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("post");
                String key = myRef.push().getKey();

                PostDTO postDTO = new PostDTO();
                postDTO.userID = userID;
                postDTO.uID = regId;
                postDTO.body = body;
                postDTO.post_Title = title;
                postDTO.subject_class = subject_class;
                postDTO.deadline = time;
                myRef.child(key).setValue(postDTO);




            }
        });
         dialog = new DatePickerDialog(this, listener, Integer.valueOf(year).intValue(), Integer.valueOf(month).intValue()-1
                , Integer.valueOf(day).intValue());
date_tv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        dialog.show();
    }
});
        Log.d("박정환", "write10");
    }


    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear = monthOfYear+1;
            date_tv.setText(year + "년" + monthOfYear + "월" + dayOfMonth +"일");
            Toast.makeText(getApplicationContext(), year + "년" + monthOfYear + "월" + dayOfMonth +"일", Toast.LENGTH_SHORT).show();
        }
    };

    public void getRegistrationId() {
        regId = FirebaseInstanceId.getInstance().getToken();
        Log.d("박정환",regId);
    }





    public void sendData() {

        Gson gson = new Gson();

        NotiDTO notiDTO = new NotiDTO();
        notiDTO.to = "9tU7213llvTlaeKSBXYlVSTgO7h2";
        notiDTO.notification.text = "asd";
        notiDTO.notification.title = "aaaaa";
        notiDTO.data.text = "asd";
        notiDTO.data.title = "aaaaa";

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf8"),gson.toJson(notiDTO));

        Request request = new Request.Builder()
                .header("Content-Type","application/json")
                .addHeader("Authorization","key=AIzaSyDhiqIpUW6x8ARYTucsDoLgGVJoZILpFhc")
                .url("http://gcm-http.googleapis.com/gcm/send")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("박정환","onFailure");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("박정환","onResponse");
            }
        });

    }

}
