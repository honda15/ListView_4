package com.example.listview_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

public class ProxiActivity extends AppCompatActivity {

    private TextView textViewData;
    private ImageView imageViewPic;
    private SensorManager sensorMg;
    private Sensor sensor;
    private MySensor sensorListen;
    private StringBuffer data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxi);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle("Proximity sensor");
        textViewData = findViewById(R.id.textView_proxidata);
        textViewData.setText("");
        imageViewPic = findViewById(R.id.imageView_proxPic);

        sensorMg = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorMg.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        sensorListen = new MySensor();
        sensorMg.registerListener(sensorListen,sensor,SensorManager.SENSOR_DELAY_NORMAL);

    }

    private class MySensor implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            data = new StringBuffer();
            float value = event.values[0];
            Log.d("lee","proxi value="+value);
            textViewData.setText("Proximity value :" + value+"\n");
            if(value < 2){
               imageViewPic.setImageResource(R.drawable.p2);
               textViewData.append("Too close !!!");
            }else {
                imageViewPic.setImageResource(R.drawable.p1);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    @Override
    protected void onDestroy() {
        sensorMg.unregisterListener(sensorListen);
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