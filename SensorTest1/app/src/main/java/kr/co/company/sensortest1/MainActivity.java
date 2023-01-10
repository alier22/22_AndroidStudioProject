package kr.co.company.sensortest1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import kr.co.company.sensortest1.R;

public class MainActivity extends AppCompatActivity
        implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensorProximity;
    private Sensor sensorLight;

    private TextView textSensorLight;
    private TextView textSensorProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textSensorLight = (TextView) findViewById(R.id.light);
        textSensorProximity = (TextView) findViewById(R.id.proximity);

        // Get an instance of the sensor manager.
        sensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);

        sensorProximity = sensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (sensorLight == null) { textSensorLight.setText("조도 센서가 없음"); }
        if (sensorProximity == null) {            textSensorProximity.setText("근접 센서가 없음");        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sensorProximity != null) {
            sensorManager.registerListener(this, sensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorLight != null) {
            sensorManager.registerListener(this, sensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];

        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                textSensorLight.setText("조도 센서="+currentValue);
                break;
            case Sensor.TYPE_PROXIMITY:
                textSensorProximity.setText("근접 센서="+currentValue);
                break;
            default:
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {    }
}