package org.techtown.firebasetermproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class WriteActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    TextView subj;
    Button btn;
    Button btn_read;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        Log.d("asd","asd");
        Intent ii = getIntent();
        userID = ii.getStringExtra("USERID");
        Log.d("박정환", userID);
        btn = (Button) findViewById(R.id.push);
        btn_read = (Button) findViewById(R.id.read);
        subj = (TextView)findViewById(R.id.subject);
        // Write a message to the database

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user == null) {
            finish();
            return ;
        }



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ((EditText)findViewById(R.id.edit_title)).getText().toString();
                String body = ((EditText)findViewById(R.id.edit_body)).getText().toString();
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("post");
                String key = myRef.push().getKey();
                HashMap<String, Object> postValues =  new HashMap<>();
                postValues.put("userID", userID);
                postValues.put("title", title);
                postValues.put("body", body);

                myRef.child(key).setValue(postValues);

            }
        });

        btn_read.setOnClickListener(new View.OnClickListener() {
            String title;
            String body;

            @Override
            public void onClick(View v) {
                myRef = database.getReference("post");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //title = dataSnapshot.getValue("");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
