package com.example.listview_4;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewSensor;
    private SensorManager sensorMg;
    private List<Sensor> sensorData;
    private List<String> listData;
    private ArrayAdapter<String> adapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewSensor = findViewById(R.id.listView_id);
        sensorMg = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorData = sensorMg.getSensorList(Sensor.TYPE_ALL);

        listViewSensor.setTextFilterEnabled(true);      //啟用按鍵過濾功能

        listData = new ArrayList<String>();
        for(Sensor sensor: sensorData){
            listData.add(sensor.getType()+" : " + sensor.getName() + " - " + sensor.getPower() +
                    "mA\n" + sensor.getVendor());
        }

        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_expandable_list_item_1,
                listData);
        listViewSensor.setAdapter(adapter);

        setTitle("Sensor number :" + listData.size());

        listViewSensor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Sensor sensor = sensorData.get(position);
               switch (sensor.getType()){
                   case Sensor.TYPE_PROXIMITY:  //距離
                       sensor = sensorMg.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                       if(sensor==null){
                           Toast.makeText(MainActivity.this, "No proximity sensor",
                                   Toast.LENGTH_SHORT).show();
                       }else {
                           intent = new Intent(MainActivity.this, ProxiActivity.class);
                           startActivity(intent);
                       }
                       break;
                   case  Sensor.TYPE_LIGHT:    //光線
                       intent = new Intent(MainActivity.this, LightActivity.class);
                       startActivity(intent);
                       break;

                   case  Sensor.TYPE_ACCELEROMETER:   //加速度
                       intent = new Intent(MainActivity.this, AccActivity.class);
                       startActivity(intent);
                       break;

//                   case  Sensor.TYPE_AMBIENT_TEMPERATURE:   //溫度
//     //                  intent = new Intent(MainActivity.this, TemperatureActivity.class);
//                       startActivity(intent);
//                       break;
               }
            }
        });

    }
}