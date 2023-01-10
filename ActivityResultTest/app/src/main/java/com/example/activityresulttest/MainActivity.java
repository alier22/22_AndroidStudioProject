package com.example.activityresulttest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text;

    static final int Get_STRING = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView)findViewById(R.id.text2);
    }



    public void onClick(View view){
        Intent intent = new Intent(MainActivity.this, SubActivity.class);
        startActivityForResult(intent, Get_STRING);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        text.setText(data.getStringExtra("INPUT_TEXT"));

        if(resultCode == RESULT_OK){
            text.setText(data.getStringExtra("INPUT_TEXT"));
        }

    }


}