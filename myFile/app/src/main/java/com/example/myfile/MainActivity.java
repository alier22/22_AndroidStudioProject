package com.example.myfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    EditText edit1;
    EditText edit2;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit1= (EditText)findViewById(R.id.edit1);
        edit2= (EditText)findViewById(R.id.edit2);
        text= (TextView)findViewById(R.id.text);

    }

    //Button을 클릭했을 때 자동으로 호출되는 callback method....
    public void mOnClick(View v){
        switch(v.getId()){
            case R.id.btn_save: //Internal Storage에 file 저장하기
                String data= edit1.getText().toString(); //EditText에서 Text1 얻어오기
                edit1.setText("");
                String data2= edit2.getText().toString(); //EditText에서 Text2 얻어오기
                edit1.setText("");
                try {
                    //FileOutputStream 객체생성, 파일명 "data.txt", 새로운 텍스트 추가하기 모드
                    FileOutputStream fos=openFileOutput("teo.txt", Context.MODE_APPEND);
                    fos.write(edit1.getText().toString().getBytes());
                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            case R.id.btn_load: //file 에서 읽어오기FileOutputStream fos=openFileOutput("data.txt", Context.MODE_APPEND);
                StringBuffer buffer= new StringBuffer();
                try {

                    //FileInputStream 객체생성, 파일명 "data.txt"
                    FileInputStream fis=openFileInput("teo.txt");
                    BufferedReader reader= new BufferedReader(new InputStreamReader(fis));
                    String str=reader.readLine();//한 줄씩 읽어오기
                    while(str!=null){
                        buffer.append(str+"\n");
                        str=reader.readLine();
                    }
                    text.setText(buffer.toString());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
        }
    }
}
