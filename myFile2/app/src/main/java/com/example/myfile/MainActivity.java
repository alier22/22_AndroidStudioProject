package com.example.myfile;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
public class MainActivity extends AppCompatActivity {
    String FILENAME = "myFile.txt";
    EditText edit;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)==false){
            Toast.makeText(this, "외부 스토리지 실패", Toast.LENGTH_SHORT).show();
        }
        edit = (EditText) findViewById(R.id.EditText01);
        String path =
                String.valueOf(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS));
        text = (TextView) findViewById(R.id.TextView01);
        text.setText(path);
        Button readButton = (Button) findViewById(R.id.buttonRead);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new
                        File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), FILENAME);
                try {
                    InputStream is;
                    is = new FileInputStream(file);
                    byte[] buffer = new byte[is.available()];
                    is.read(buffer);
                    text.setText(new String(buffer));
                    is.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        Button writeButton = (Button) findViewById(R.id.buttonWrite);
        writeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                File file = new
                        File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), FILENAME);
                try {
                    OutputStream os = new FileOutputStream(file);
                    os.write(edit.getText().toString().getBytes());
                    os.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}