package com.example.fishgomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class User_Profile extends AppCompatActivity {

    private Button loma_vesture_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent intent=getIntent();
        TextView user_info=findViewById(R.id.profile_info);
        loma_vesture_button=findViewById(R.id.loma_vesture_btn);

        user_info.setText("ID:"+intent.getStringExtra("USER_ID")+
                "\nNAME:"+intent.getStringExtra("USER_NAME")+
                "\nSURNAME:"+intent.getStringExtra("USER_SURNAME")+
                "\nEMAIL:"+intent.getStringExtra("USER_EMAIL"));

        loma_vesture_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent_2=new Intent(User_Profile.this,Lomu_Vesture.class);
                startActivity(intent_2);
            }
        });

    }
}