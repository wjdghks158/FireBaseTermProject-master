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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.firebasetermproject.dto.ClassDTO;
import org.techtown.firebasetermproject.dto.PostDTO;
import org.techtown.firebasetermproject.dto.TaskDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    RequestQueue queue;

    private List<ClassDTO> classDTOS = new ArrayList<>();
    private ArrayList<String> subject_class_List = new ArrayList<>();




    String[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("박정환", "write");
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
        Log.d("박정환", "write2");

        Log.d("박정환", "write4");
        Log.d("asd","asd");
        Intent ii = getIntent();
        Log.d("박정환", "write5");
        userID = ii.getStringExtra("USERID");
        Log.d("박정환", "write6");
        Log.d("박정환", userID);
        btn_push = (Button) findViewById(R.id.push);
        btn_write = (Button) findViewById(R.id.write_button);
        subj = (TextView)findViewById(R.id.title);
        // Write a message to the database
        Log.d("박정환", "write7");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        items = new String[10];
        if(user == null) {
            finish();
            return ;
        }
        Log.d("박정환", "write8");
        database2 = FirebaseDatabase.getInstance();
        Log.d("박정환", "write100");
        DatabaseReference a = database2.getReference();
        Log.d("PH", database2.getReference().toString());

//        a.child("class").addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d("박정환", "write9");
//                classDTOS.clear();
//                int i =0;
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    ClassDTO classDTO = snapshot.getValue(ClassDTO.class);
//                    classDTOS.add(classDTO);
//
//                    items[i] = classDTO.subject_class;
//                    subject_class_List.add(classDTO.subject_class);
//                    i++;
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                throw databaseError.toException();
//
//            }
//        });

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




        // String key = myRef.push().getKey();
                /*
                HashMap<String, Object> postValues =  new HashMap<>();
                postValues.put("userID", userID);
                postValues.put("class", subject_class);
                postValues.put("title", title);
                postValues.put("body", body);
                postValues.put("deadline", time);
                */





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

                myRef.child(key).setValue(postValues);

                // send(subject_class);

            }
        });

        queue = Volley.newRequestQueue(getApplicationContext());
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
                /*
                HashMap<String, Object> postValues =  new HashMap<>();
                postValues.put("userID", userID);
                postValues.put("class", subject_class);
                postValues.put("title", title);
                postValues.put("body", body);
                postValues.put("deadline", time);
                */
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

    public void send(String input) {
        JSONObject requestData = new JSONObject();

        try{
            requestData.put("priority","high");
            JSONObject dataObj = new JSONObject();
            dataObj.put("contents", input);
            requestData.put("data",dataObj);

            JSONArray idArray = new JSONArray();
            idArray.put(0,regId);
            requestData.put("reistration_ids",idArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sendData(requestData, new SendResponseListener() {
            @Override
            public void onRequestCompleted() {

            }

            @Override
            public void onRequestStarted() {

            }

            @Override
            public void onRequestWithError(VolleyError error) {

            }
        });

    }


    public interface SendResponseListener {
        public void onRequestStarted();
        public void onRequestCompleted();
        public void onRequestWithError(VolleyError error);
    }


    public void sendData(JSONObject requestData, final SendResponseListener listener) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "https://fcm.googleapis.com/fcm/send",
                requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onRequestCompleted();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onRequestWithError(error);
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String,String>();
                headers.put("Authorization","key=AAAA_mr_EYY:APA91bGoPS2vaNX8XHx9MgThI7-dy0w-uGy1yaSZCaxsrX2re_WYhwtvILMqzkmPrhBX92aopkn6h-3Q8H-AtVxiLYGdmm5vsIkvE81kWDJJzjVCbnNfdmn-LTJGc2uV1Ocs32dYmcpi");


                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        request.setShouldCache(false);
        listener.onRequestStarted();
        queue.add(request);
    }

}