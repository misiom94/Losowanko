package com.example.misio.losowanko;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.HashMap;

public class ActivityWynik extends AppCompatActivity {
    private static final String TAG = "ActivityWynik";
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    VideoView videoView;
    TableLayout tabelaWyniki;
    TableRow rowWyniki;
    Button buttonPowrot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wynik);

        Intent intent = getIntent();
        HashMap<String,String> wynik = (HashMap<String,String>)intent.getSerializableExtra("map");
        buttonPowrot = (Button)findViewById(R.id.buttonWroc);
        tabelaWyniki = (TableLayout)findViewById(R.id.tableWynik);
        videoView = (VideoView)findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://"+ getPackageName()+"/"+ R.raw.losowanie);
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                ((ViewManager)videoView.getParent()).removeView(videoView);
            }
        });
        fillTable(wynik,tabelaWyniki);
        initializeShakeDetection();


    }
    public void fillTable(HashMap<String,String> wynik, TableLayout tabelaWyniki){
        for(String key : wynik.keySet()){
            String value = wynik.get(key);
            TableRow rowWynik = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
            layoutParams.bottomMargin = 10;
            rowWynik.setLayoutParams(layoutParams);
            TextView viewWynik = new TextView(this);
            viewWynik.setText(key+"\n"+value+"\n\n");
            setViewParams(viewWynik);
            tabelaWyniki.addView(rowWynik);
            tabelaWyniki.addView(viewWynik);
        }
    }

    public void setViewParams(TextView textView){

        textView.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
        textView.setTextSize(20);
        textView.setBackgroundResource(R.drawable.row_border);
    }

    public void initializeShakeDetection(){
        mSensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                finish();
            }
        });
    }

}
