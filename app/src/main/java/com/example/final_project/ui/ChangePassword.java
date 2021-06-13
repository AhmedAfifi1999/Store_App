package com.example.final_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.R;

public class ChangePassword extends AppCompatActivity {

    private TextView errorMsg;
    private EditText oldPassword, newPassword, reNewPassword;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    String realOldPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        oldPassword = findViewById(R.id.changePassword_oldPassword);
        newPassword = findViewById(R.id.changePassword_newPassword);
        reNewPassword = findViewById(R.id.changePassword_reNewPassword);
        errorMsg = findViewById(R.id.changePassword_errorMsg);

        sp = getSharedPreferences("user_info", MODE_PRIVATE);
        edit = sp.edit();


    }

    public void saveChangePassword(View view) {
        realOldPassword = sp.getString(Sign_up.PASSWORD, "0");

        if (oldPassword.getText().toString().equals(realOldPassword)) {

            if (newPassword.getText().toString().equals(reNewPassword.getText().toString())) {
                String newPass = newPassword.getText().toString();
                Toast.makeText(this, "newPass :" + newPass, Toast.LENGTH_SHORT).show();
                edit.putString(Sign_up.PASSWORD, newPass);
              String  password_get = sp.getString(Sign_up.PASSWORD, "0");

                Toast.makeText(this, "newPass :" + password_get, Toast.LENGTH_SHORT).show();

                edit.apply();
                Toast.makeText(this, "newPass :" + password_get, Toast.LENGTH_SHORT).show();

                errorMsg.setTextColor(Color.GREEN);
                errorMsg.setText("success change password");

            } else {
                errorMsg.setTextColor(Color.RED);
                errorMsg.setText("the new password is not same");
            }
        } else {
            errorMsg.setTextColor(Color.RED);
            errorMsg.setText("the old password is wrong");

        }
    }
}
