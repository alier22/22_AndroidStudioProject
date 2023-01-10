package com.example.activityresulttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SubActivity extends AppCompatActivity {

    EditText edit;
    Button buttonOK;
    Button buttonCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        edit = (EditText)findViewById(R.id.edit);
        buttonOK = (Button)findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("INPUT_TEXT",edit.getText().toString());
                setResult(RESULT_OK, intent);
                finish();

            }
        });
        buttonCancel = (Button)findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    public void onClickCancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onClickOK(View view) {
        Intent intent = new Intent();
        intent.putExtra("INPUT_TEXT",edit.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}