package com.example.labuser.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Sensor temp;
    private TextView sensorInfo;
    private TextView txtTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensorInfo = (TextView) findViewById(R.id.txtInfo);
        txtTemp = (TextView) findViewById(R.id.txtTemp);

        String s = "Sensor Info\n";

        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        for(int i=0;i<deviceSensors.size();i++) {
            s+= deviceSensors.get(i).getName() + ":" + deviceSensors.get(i).getVendor() + "\n";
        }
        sensorInfo.setText(s);

        mSensor = null;

        if(mSensor == null){
            if(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            }
            else{
                Log.v("err","no accelerometer");
            }
        }
        temp = null;
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE) != null){
            temp = mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
        }
        if(temp == null) {
            txtTemp.setText("no temp sensor");
        }
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    @Override
    public final void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_TEMPERATURE){
            float t = event.values[0];

            txtTemp.setText("temp" + t + "C , " + (9.0 / 5.0 * t + 32) + "F");
        }
        else if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){



        }


    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, temp, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
