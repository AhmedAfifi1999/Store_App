package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class Sign_up extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private  TextView tvDate;
    private Button btPickDate , save;

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getBaseContext(),Login.class);
                startActivity(login);
                finish();

            }
        });


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
        tvDate = findViewById(R.id.tvDate);
        btPickDate = findViewById(R.id.btPickDate);
        save = findViewById(R.id.save);
        tvDate = findViewById(R.id.tvDate);
        btPickDate = findViewById(R.id.btPickDate);
        sp =getSharedPreferences("user_info",MODE_PRIVATE);
        edit =sp.edit();

    }
}
