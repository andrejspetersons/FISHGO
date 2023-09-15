package com.example.fishgomobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.tensorflow.lite.support.metadata.schema.Content;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefConf {

    private static final String LIST_KEY = "HISTORY_LIST";

    public static void writePreflist(Context context, ArrayList<Fish>fishlist){
        Gson gson=new Gson();
        String jsonString=gson.toJson(fishlist);

        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=pref.edit();
        editor.putString(LIST_KEY,jsonString);
        editor.apply();
    }

    public static ArrayList<Fish> readListfromPref(Context context){
        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString=pref.getString(LIST_KEY,"");

        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<Fish>>(){}.getType();
        ArrayList<Fish>fishlist=gson.fromJson(jsonString,type);

        return fishlist;

    }

}
