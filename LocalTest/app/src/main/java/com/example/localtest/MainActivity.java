package com.example.localtest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST = 0 ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Log.i("permission","granted");
            }
            else{
                Log.i("permission","Not granted");
            }
        }
    }

    public void onClickStart(View view){
        Intent intent = new Intent(getBaseContext(),MyService.class);
        startService(intent);
    }

    public void onClickStop(View view){
        Intent intent = new Intent(getBaseContext(),MyService.class);
        stopService(intent);
    }



}