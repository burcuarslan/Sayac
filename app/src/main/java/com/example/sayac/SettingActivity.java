package com.example.sayac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SettingActivity extends AppCompatActivity {

    EditText up_limit,low_limit;
    Button up_plus,up_minus,low_plus,low_minus;
    CheckBox up_vib,low_vib,up_sound,low_sound;

    SetupClass setupClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Context context = getApplicationContext();
        setupClass=SetupClass.getInstance(context);

        up_limit=(EditText)findViewById(R.id.up_limit);
        low_limit=(EditText)findViewById(R.id.low_limit);

        up_plus=(Button)findViewById(R.id.up_plus);
        up_minus=(Button)findViewById(R.id.up_minus);
        low_plus=(Button)findViewById(R.id.low_plus);
        low_minus=(Button)findViewById(R.id.low_minus);

        up_vib=(CheckBox) findViewById(R.id.up_vib);
        low_vib=(CheckBox) findViewById(R.id.low_vib);
        up_sound=(CheckBox) findViewById(R.id.up_sound);
        low_sound=(CheckBox) findViewById(R.id.low_sound);


        up_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up_plus(true,up_limit);
            }
        });

        up_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up_plus(false,up_limit);
            }
        });

        low_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up_plus(true,low_limit);
            }
        });

        low_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up_plus(false,low_limit);
            }
        });

    }

    private void up_plus(boolean op,EditText editText){
        int number=Integer.parseInt(editText.getText().toString());
       if (op) {
           number++;
       }
       else{
           number--;
       }
        editText.setText(String.valueOf(number));
    }

    @Override
    public void onResume(){
        super.onResume();
        setupClass.loadValues();
       getPreferences();
    }


    public void getPreferences(){
        up_limit.setText(String.valueOf(setupClass.up_limit));
        low_limit.setText(String.valueOf(setupClass.low_limit));
        up_vib.setChecked(setupClass.up_vib);
        low_vib.setChecked(setupClass.low_vib);
        up_sound.setChecked(setupClass.up_sound);
        low_sound.setChecked(setupClass.low_sound);

    }

    @Override
    public void onPause(){
        super.onPause();
        setupClass.up_limit=Integer.parseInt(up_limit.getText().toString());
        setupClass.low_limit=Integer.parseInt(low_limit.getText().toString());
        setupClass.up_vib=up_vib.isChecked();
        setupClass.low_vib=low_vib.isChecked();
        setupClass.up_sound=up_sound.isChecked();
        setupClass.low_sound=low_sound.isChecked();

        setupClass.saveValues();
    }
}