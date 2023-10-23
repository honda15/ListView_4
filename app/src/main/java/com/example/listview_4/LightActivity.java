package com.example.listview_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class LightActivity extends AppCompatActivity {

    private TextView textViewData;
    private ImageView imageViewPic;
    private SensorManager sensorMg;
    private Sensor sensorlight;
    private SensorEventListener lightListen;
    private MyListenTemp tempListen;
    private Sensor sensorTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        //actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Light sensor");

        textViewData = findViewById(R.id.textView_lightdata);
        textViewData.setText("");
        imageViewPic = findViewById(R.id.imageView_lightPic);

        sensorMg = (SensorManager) getSystemService(SENSOR_SERVICE);
//溫度
        sensorTemp = sensorMg.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        tempListen = new MyListenTemp();
        sensorMg.registerListener(tempListen,sensorTemp,SensorManager.SENSOR_DELAY_NORMAL);  //第三個參數，偵測時間間隔

        sensorlight = sensorMg.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightListen = new MyListen();
        sensorMg.registerListener(lightListen,sensorlight,SensorManager.SENSOR_DELAY_NORMAL);  //第三個參數，偵測時間間隔
    }

    //actionbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyListen implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            StringBuffer data = new StringBuffer();
            float value = event.values[0];
            data.append("Lux value :" + value);
            textViewData.setText(data);
            if(value < 2000){
                imageViewPic.setImageResource(R.drawable.dark_light);
            }else {
                imageViewPic.setImageResource(R.drawable.imageslight);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    @Override
    protected void onDestroy() {
        sensorMg.unregisterListener(lightListen);
        sensorMg.unregisterListener(tempListen);
        super.onDestroy();
    }

    private class MyListenTemp implements  SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            StringBuffer data = new StringBuffer();
            float value = event.values[0];
            data.append("Temperature value :" + value);
            textViewData.setText(data);
//            if(value < 2000){
//                imageViewPic.setImageResource(R.drawable.dark_light);
//            }else {
//                imageViewPic.setImageResource(R.drawable.imageslight);
//            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}