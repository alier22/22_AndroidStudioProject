package kr.co.company.sensortest1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    CompassView compassView;
    SensorManager sensorManager;
    Sensor accelerometer;
    Sensor magmetometer;

    float[] mGravity;
    float[] mGeomagnetic;
    float azimuth;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        compassView = new CompassView(this);
        setContentView(compassView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magmetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this,magmetometer,SensorManager.SENSOR_DELAY_UI);


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
            mGravity = sensorEvent.values;
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = sensorEvent.values;

        if (mGravity != null && mGeomagnetic != null){
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I,mGravity, mGeomagnetic);
            if (success){
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuth =  orientation[0];

            }
            compassView.invalidate();


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public class CompassView extends View{
        Paint paint = new Paint();

        public CompassView(Context context){
            super(context);
            paint.setColor(0xffff0000);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            paint.setAntiAlias(true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int width = getWidth();
            int height = getHeight();
            int centerX = width/2;
            int centerY = height/2;

            canvas.drawLine(centerX,0,centerX, height, paint);
            canvas.drawLine(0,centerY,width,centerY,paint);

            float degree = (float) 0.0;
            if (azimuth != 0){
                degree = azimuth * 360 / (2*3.1415f);
                canvas.rotate(degree,centerX,centerY);
            }
            paint.setColor(0xff0000ff);
            paint.setStrokeWidth(4);
            canvas.drawLine(centerX,centerY-100, centerX, centerY+100,paint);
            canvas.drawLine(centerX-100, centerY, centerX+100, centerY, paint);


            paint.setColor(0xffff0000);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);



        }
    }



}






