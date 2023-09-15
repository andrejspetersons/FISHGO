package com.example.fishgomobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import java.util.Calendar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText user_name;
    private EditText user_surname;
    private EditText user_email;
    private EditText user_dob;
    private EditText user_password;
    private EditText user_confirmed_password;
    private Button submit_button;
    private DBHandler dbHandler;
    private DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        user_name=findViewById(R.id.user_name_reg);
        user_surname=findViewById(R.id.user_surname_reg);
        user_email=findViewById(R.id.user_email_reg);
        user_password=findViewById(R.id.user_password_reg);
        user_confirmed_password=findViewById(R.id.user_confirm_password_reg);
        user_dob=findViewById(R.id.user_dob_reg);
        submit_button=findViewById(R.id.submit);


        dbHandler=new DBHandler(getApplicationContext());



        user_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar=Calendar.getInstance();
                int current_Day=calendar.get(Calendar.DAY_OF_MONTH);
                int current_Month=calendar.get(Calendar.MONTH);
                int current_Year=calendar.get(Calendar.YEAR);

                datePickerDialog=new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        user_dob.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                    }
                },current_Year,current_Month,current_Day);

                datePickerDialog.show();
            }
        });



        submit_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name=user_name.getText().toString();
            String surname=user_surname.getText().toString();
            String email=user_email.getText().toString();
            String password=user_password.getText().toString();
            String confirmed_password=user_confirmed_password.getText().toString();


            if(password.compareTo(confirmed_password)!=0){
                Toast.makeText(RegistrationActivity.this, "Passwords dont match", Toast.LENGTH_SHORT).show();
            }
            /*else if(user_password.getText().toString().length()<=6){
                Toast.makeText(RegistrationActivity.this,"Password is too short",Toast.LENGTH_SHORT).show();
            }*/
            else if(user_password.getText().toString().length()>20){
                Toast.makeText(RegistrationActivity.this,"Password is too long",Toast.LENGTH_SHORT).show();
            }
            else if(name.length()==0||surname.length()==0||email.length()==0){
                Toast.makeText(RegistrationActivity.this,"Enter all data",Toast.LENGTH_SHORT).show();
            }
            else{
               //add new user to sqlite DB
                Intent intent=new Intent(RegistrationActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Toast.makeText(RegistrationActivity.this,"Submit successful",Toast.LENGTH_SHORT).show();
                dbHandler.addNewUser(name,surname,email,"null",password);

            }

        }


    });


    }
}