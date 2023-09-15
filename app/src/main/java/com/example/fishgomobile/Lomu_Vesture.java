package com.example.fishgomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Lomu_Vesture extends AppCompatActivity {

    private ImageView imageView;
    private TextView  empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lomu_vesture);
        Intent intent = getIntent();
        String name="";
        ListView listView = findViewById(R.id.list);
        ArrayList<Fish> fishArrayList = PrefConf.readListfromPref(this);
        int[] picture_array = {R.drawable.karpa, R.drawable.lasis, R.drawable.zutis, R.drawable.lidaka, R.drawable.sams};

        if(fishArrayList==null){
            fishArrayList=new ArrayList<>();
        }

        if (savedInstanceState == null) {
            empty = findViewById(R.id.empty_list);
        }




        FishAdapter fishAdapter = new FishAdapter(Lomu_Vesture.this, R.layout.layot_fragment_2, fishArrayList);


        if(intent.hasExtra("nosaukums")) {

            name=intent.getStringExtra("nosaukums");

            if (name.compareTo("Karpa") == 0) {
                fishArrayList.add(new Fish(name, picture_array[0], "Makšķere"));

            } else if (name.compareTo("Lasis") == 0) {
                fishArrayList.add(new Fish(name, picture_array[1], "Makšķere"));
            } else if (name.compareTo("Zutis") == 0) {
                fishArrayList.add(new Fish(name, picture_array[2], "Makšķere"));
            } else if (name.compareTo("Līdaka") == 0) {
                fishArrayList.add(new Fish(name, picture_array[3], "Makšķere"));
            } else if (name.compareTo("Sams") == 0) {
                fishArrayList.add(new Fish(name, picture_array[4], "Makšķere"));
            }
            PrefConf.writePreflist(getApplicationContext(),fishArrayList);
            listView.setAdapter(fishAdapter);
        }
        else{
            empty.setText("Loms nav reģistrēts");
        }



    }

}