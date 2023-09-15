package com.example.fishgomobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

       ImageView map_view=findViewById(R.id.map_view);
       ImageView loot_registration=findViewById(R.id.registration_view);
       ImageView user_infoview=findViewById(R.id.user_info);


       map_view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Toast.makeText(getApplicationContext(),"Funkcija pagaidam nav pieejama",Toast.LENGTH_SHORT).show();

           }
       });

        loot_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loot=new Intent(MainMenu.this,Loma_register_activity.class);
                startActivity(loot);
            }
        });

        user_infoview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=getIntent().getStringExtra("ID_KEY");
                String name=getIntent().getStringExtra("NAME_KEY");
                String surname=getIntent().getStringExtra("SURNAME_KEY");
                String email=getIntent().getStringExtra("EMAIL_KEY");

                Intent intent=new Intent(MainMenu.this,User_Profile.class);

                intent.putExtra("USER_ID",id);
                intent.putExtra("USER_NAME",name);
                intent.putExtra("USER_SURNAME",surname);
                intent.putExtra("USER_EMAIL",email);


                startActivity(intent);

            }
        });

    }

}