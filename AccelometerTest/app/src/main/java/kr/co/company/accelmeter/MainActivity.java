package kr.co.company.accelmeter;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {
    SensorManager smanager = null;
    TextView textView1 = null;
    Sensor sensorAccel;

    SensorEventListener listener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            textView1.setText(" X축: " + values[0] + "\n Y축: " + values[1] + "\n Z축: " + values[2]);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smanager = (SensorManager) getSystemService(SENSOR_SERVICE);
        textView1 = (TextView) findViewById(R.id.textView2);

        sensorAccel = smanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensorAccel == null)
            Toast.makeText(getBaseContext(), "가속도계가 없음", Toast.LENGTH_LONG).show();
        else
            smanager.registerListener(listener, (Sensor) sensorAccel, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        smanager.unregisterListener(listener);
    }
}