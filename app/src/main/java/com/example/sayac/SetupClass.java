package com.example.sayac;

import android.content.Context;
import android.content.SharedPreferences;

public class SetupClass {
    int up_limit, low_limit, current_value;
    boolean up_vib, up_sound, low_vib, low_sound;

    private static final String UP_LIMIT = "UP_LIMIT";
    private static final String LOW_LIMIT = "LOW_LIMIT";
    private static final String CURRENT_VALUE = "CURRENT_VALUE";
    private static final String UP_VIB = "UP_VIB";
    private static final String UP_SOUND = "UP_SOUND";
    private static final String LOW_VIB = "LOW_VIB";
    private static final String LOW_SOUND = "LOW_SOUND";
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    static SetupClass setupClass = null;

    private SetupClass(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("setup", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SetupClass  getInstance(Context context){
       if(setupClass == null){
           setupClass = new SetupClass(context);
       }
       return setupClass;
    }


    public void setValue(int up_limit, int low_limit,boolean up_vib,boolean up_sound,boolean low_vib,boolean low_sound){
        this.up_limit = up_limit;
        this.low_limit = low_limit;

        this.up_vib= up_vib;
        this.up_sound = up_sound;
        this.low_vib = low_vib;
        this.low_sound = low_sound;

    }

    public void setCurrentValue(int current_value){
        this.current_value = current_value;
    }

    public void saveValues(){
        editor.putInt(UP_LIMIT, up_limit);
        editor.putInt(LOW_LIMIT, low_limit);
        editor.putInt(CURRENT_VALUE, current_value);
        editor.putBoolean(UP_VIB, up_vib);
        editor.putBoolean(UP_SOUND, up_sound);
        editor.putBoolean(LOW_VIB, low_vib);
        editor.putBoolean(LOW_SOUND, low_sound);
        editor.commit();
    }

    public void loadValues(){
        up_limit = sharedPreferences.getInt(UP_LIMIT, 0);
        low_limit = sharedPreferences.getInt(LOW_LIMIT, 0);
        current_value = sharedPreferences.getInt(CURRENT_VALUE, 0);
        up_vib=sharedPreferences.getBoolean(UP_VIB, true);
        up_sound=sharedPreferences.getBoolean(UP_SOUND, true);
        low_vib=sharedPreferences.getBoolean(LOW_VIB, true);
        low_sound=sharedPreferences.getBoolean(LOW_SOUND, true);

    }




}
