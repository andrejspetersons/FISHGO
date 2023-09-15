package com.example.fishgomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {
    private EditText email_field;
    private EditText password_field;
    private Button loginbtn;
    private Button registrationbtn;
    private DBHandler db = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbtn = findViewById(R.id.login_button);
        email_field = findViewById(R.id.email_field);
        password_field = findViewById(R.id.password_field);
        registrationbtn = findViewById(R.id.registracija);
        db=new DBHandler(getApplicationContext());


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = email_field.getText().toString();
                String password = password_field.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {

                    Toast.makeText(MainActivity.this, "Ievadiet datus", Toast.LENGTH_SHORT).show();
                    return;

                }
                else if(db.UserExist(email,password)){

                    String id="";
                    String name="";
                    String surname="";

                    Cursor cursor=db.getEmailInfo(email);

                    while(cursor.moveToNext()||cursor.getCount()==0){

                        if(cursor.getCount()>0){

                            id=cursor.getString(0);
                            name=cursor.getString(1);
                            surname=cursor.getString(2);

                        }

                    }


                    Intent intent=new Intent(MainActivity.this,MainMenu.class);

                    intent.putExtra("ID_KEY",id);
                    intent.putExtra("NAME_KEY",name);
                    intent.putExtra("SURNAME_KEY",surname);
                    intent.putExtra("EMAIL_KEY",email);


                    startActivity(intent);


                }
                else{

                    Toast.makeText(MainActivity.this,"Create account first",Toast.LENGTH_SHORT).show();

                }

            }

        });


        registrationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_registration = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent_registration);
            }
        });

    }

}


