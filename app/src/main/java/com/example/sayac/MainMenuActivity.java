package com.example.sayac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity implements SensorEventListener {

    private TextView value;
    Button minus, plus, settings;
    int up_limit, low_limit, current_value;
    boolean up_vib, low_vib, up_sound, low_sound;
    Vibrator vibrator;
    MediaPlayer mediaPlayer;
    SensorManager sensorManager;
    Sensor sensorShake;

    SetupClass setupClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        value = (TextView) findViewById(R.id.value);
        minus = (Button) findViewById(R.id.minus);
        plus = (Button) findViewById(R.id.plus);
        settings = (Button) findViewById(R.id.setting);

        Context context = getApplicationContext();
        setupClass = SetupClass.getInstance(context);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.circles);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorShake = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensorShake, SensorManager.SENSOR_DELAY_NORMAL);

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueChange(-1,mediaPlayer);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueChange(1,mediaPlayer);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void minus() {
        int number = Integer.parseInt(value.getText().toString());
        number--;
        value.setText(String.valueOf(number));
    }

    private void plus() {
        int number = Integer.parseInt(value.getText().toString());
        number++;
        value.setText(String.valueOf(number));
    }

    private void valueChange(int step,MediaPlayer mediaPlayer) {
        int number = current_value;
        if (step > 0) {
            number = Integer.parseInt(value.getText().toString());

            if (number < up_limit) {
                number += step;
            }
            else{
                checkVib(up_vib);
                checkSound(up_sound,mediaPlayer);
            }



        } else if (step < 0) {
            number = Integer.parseInt(value.getText().toString());
            if (number > low_limit) {
                number += step;
            }
            else{
                checkVib(low_vib);
                checkSound(low_sound,mediaPlayer);
            }
        }
        value.setText(String.valueOf(number));
    }

    private void checkVib(boolean vib){
        if (vib) {
            vibrator.vibrate(100);
        }

    }
    private void checkSound(boolean sound,MediaPlayer mediaPlayer){
        if (sound) {
            mediaPlayer.start();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupClass.loadValues();
        getPreferences();

    }

    private void getPreferences() {
        up_limit = setupClass.up_limit;
        low_limit = setupClass.low_limit;
        up_vib = setupClass.up_vib;
        low_vib = setupClass.low_vib;
        up_sound = setupClass.up_sound;
        low_sound = setupClass.low_sound;

    }

    @Override
    protected void onPause() {
        super.onPause();
        setupClass.saveValues();
        setupClass.setCurrentValue(Integer.parseInt(value.getText().toString()));
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    valueChange(5,  mediaPlayer);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    valueChange(-5, mediaPlayer);
                }
                return true;

        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float x=sensorEvent.values[0];
        float y=sensorEvent.values[1];
        float z=sensorEvent.values[2];

        float acceleration=(float)Math.sqrt(x*x+y*y+z*z)-(SensorManager.GRAVITY_EARTH);
        if(acceleration>2){
            clear();
        }

    }


    private void clear(){
        value.setText("0");
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}