package org.techtown.firebasetermproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class DetailActivity extends AppCompatActivity {

    TextView title;
    TextView detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent i = getIntent();
        String date = i.getStringExtra("date");

        title = (TextView)findViewById(R.id.title_date);
        detail = (TextView)findViewById(R.id.detail_info);

        title.setText(date);
    }
}

