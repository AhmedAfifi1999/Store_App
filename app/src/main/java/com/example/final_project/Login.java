package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity   {
    //Button
    Button login ;
    Button register ;
    //Edit Text
    EditText loginUserName, loginPassword;

    SharedPreferences sp ;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register=findViewById(R.id.register);
        loginUserName =findViewById(R.id.loginUserName);
        loginPassword =findViewById(R.id.loginPassword);

        sp =getSharedPreferences("user_info",MODE_PRIVATE);
        edit =sp.edit();
        String user_name =sp.getString(new Sign_up().USER_NAME,"0");
        String full_name =sp.getString(new Sign_up().FULL_NAME,"0");
        String PASSWORD =sp.getString(new Sign_up().PASSWORD,"0");

        if(user_name.equals("0") ||full_name.equals("0")||PASSWORD.equals("0") ){
            //do no thing
        }else{
            loginUserName.setText(user_name);
            loginPassword.setText(PASSWORD);
        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getBaseContext(),Sign_up.class);
                startActivity(register);
                finish();
            }
        });
    }
}
