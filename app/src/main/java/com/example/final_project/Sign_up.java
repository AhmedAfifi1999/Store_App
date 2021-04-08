package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class Sign_up extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String USER_NAME= "user_name";
    public static  final String PASSWORD= "password";
    public static final String FULL_NAME= "full_name";

    private  TextView tvDate ;
    private EditText fullName ,userName ,mail ,password ,rePassword,mobileNumber,address;
    private Button btPickDate , save;
    private CheckBox isAdministrator;

    SharedPreferences sp ;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        declare();


        btPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.example.final_project.DatePicker mDatePickerDialogFragment;
                mDatePickerDialogFragment = new com.example.final_project.DatePicker();
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            }
        });
//Save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=   userName.getText().toString();
                String pass=   password.getText().toString();
                String full_name=   fullName.getText().toString();
// check is empty or null  >>
                edit.putString(USER_NAME,username);
                edit.putString(PASSWORD,pass);
                edit.putString(FULL_NAME,full_name);
                edit.apply();
                // move to  Login Activity
                Intent login = new Intent(getBaseContext(),Login.class);
                startActivity(login);
                finish();

            }
        });


    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getFULL_NAME() {
        return FULL_NAME;
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        // Create a Calender instance
        Calendar mCalender = Calendar.getInstance();
        // Set static variables of Calender instance
        mCalender.set(Calendar.YEAR,year);
        mCalender.set(Calendar.MONTH,month);
        mCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        // Get the date in form of string
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalender.getTime());
        // Set the textview to the selectedDate String
        tvDate.setText(selectedDate);
    }


    private void declare(){
        //Edit text
             //fullName ,userName ,mail ,password ,rePassword,mobileNumber,address;
        fullName =findViewById(R.id.fullName);
        userName =findViewById(R.id.userName);
        mail =findViewById(R.id.mail);
        password =findViewById(R.id.password);
        rePassword =findViewById(R.id.rePassword);
        mobileNumber =findViewById(R.id.mobileNumber);
        address =findViewById(R.id.address);
        //TextView
        tvDate = findViewById(R.id.tvDate);
        //Button
        btPickDate = findViewById(R.id.btPickDate);
        save = findViewById(R.id.save);
        //SharedPreference
        sp =getSharedPreferences("user_info",MODE_PRIVATE);
        edit =sp.edit();

    }
}
