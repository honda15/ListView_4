package com.example.listview_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AccActivity extends AppCompatActivity {

    private TextView textViewData;
    private ImageView imageViewTop,imageViewLeft,imageViewRight,imageViewBotton;
    private SensorManager sensorMg;
    private Sensor sensor;
    private MyListen accListen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc);
        //actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("ACCELEROMETER sensor");

        initView();

    }


    private void initView() {
        textViewData = findViewById(R.id.textView_acc_data);
        textViewData.setText("");
        imageViewTop = findViewById(R.id.imageView_top);
        imageViewLeft = findViewById(R.id.imageView_left);
        imageViewRight = findViewById(R.id.imageView_right);
        imageViewBotton = findViewById(R.id.imageView_botton);
        imageViewTop.setVisibility(View.INVISIBLE);
        imageViewBotton.setVisibility(View.INVISIBLE);
        imageViewLeft.setVisibility(View.INVISIBLE);
        imageViewRight.setVisibility(View.INVISIBLE);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);   //讓手機畫面垂直

        sensorMg = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorMg.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accListen = new MyListen();
        sensorMg.registerListener(accListen,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    private class MyListen implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            StringBuffer data = new StringBuffer();
            float xValue = event.values[0];
            float yValue = event.values[1];
            float zValue = event.values[2];
            data.append("x value" + xValue+"\n");
            data.append("y value" + yValue+"\n");
            data.append("z value" + zValue);

            textViewData.setText(data);

            if(xValue < -4.0){
                imageViewTop.setVisibility(View.INVISIBLE);
                imageViewBotton.setVisibility(View.INVISIBLE);
                imageViewLeft.setVisibility(View.VISIBLE);
                imageViewRight.setVisibility(View.INVISIBLE);
            }else if(xValue > 4.0){
                imageViewTop.setVisibility(View.INVISIBLE);
                imageViewBotton.setVisibility(View.VISIBLE);
                imageViewLeft.setVisibility(View.INVISIBLE);
                imageViewRight.setVisibility(View.INVISIBLE);
            }else {
                if(zValue > 8){
                    imageViewTop.setVisibility(View.VISIBLE);
                    imageViewBotton.setVisibility(View.INVISIBLE);
                    imageViewLeft.setVisibility(View.INVISIBLE);
                    imageViewRight.setVisibility(View.INVISIBLE);
                }else if(zValue < 1) {
                    imageViewTop.setVisibility(View.INVISIBLE);
                    imageViewBotton.setVisibility(View.INVISIBLE);
                    imageViewLeft.setVisibility(View.INVISIBLE);
                    imageViewRight.setVisibility(View.VISIBLE);
                }else {
                    imageViewTop.setVisibility(View.INVISIBLE);
                    imageViewBotton.setVisibility(View.INVISIBLE);
                    imageViewLeft.setVisibility(View.INVISIBLE);
                    imageViewRight.setVisibility(View.INVISIBLE);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    @Override
    protected void onDestroy() {
        sensorMg.unregisterListener(accListen);
        super.onDestroy();
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


}