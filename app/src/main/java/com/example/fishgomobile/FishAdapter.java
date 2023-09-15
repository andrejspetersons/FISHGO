package com.example.fishgomobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class FishAdapter extends ArrayAdapter<Fish> {

    private LayoutInflater layoutInflater;
    private int layout;
    private List<Fish>history;


    public FishAdapter(@NonNull Context context, int resource, @NonNull List<Fish> history) {
        super(context, resource,history);
        this.history=history;
        this.layout=resource;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public View getView(int pos, View containerView, ViewGroup parent){

        View view=layoutInflater.inflate(this.layout,parent,false);

        ImageView fishview=view.findViewById(R.id.fish_imageView);
        TextView fishtype=view.findViewById(R.id.textview_fish_name);
        TextView fishingdevice=view.findViewById(R.id.makšķerešanas_ierice);

        Fish fish_history=history.get(pos);

        fishview.setImageResource(fish_history.getRes());
        fishtype.setText(fish_history.getName());
        fishingdevice.setText(fish_history.getIerice());

        return view;
    }
}
