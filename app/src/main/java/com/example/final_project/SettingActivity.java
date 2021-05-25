package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void addItem(View view) {
        Intent addProductActivity  = new Intent(getBaseContext(),AddProduct.class);
        startActivity(addProductActivity);

    }

    public void ChangePassword(View view) {
        Intent changePasswordActivity  = new Intent(getBaseContext(),ChangePassword.class);
        startActivity(changePasswordActivity);


    }
}
