package com.example.final_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.final_project.R;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {
    private Button register, login;
    private CheckBox remember_me;
    public static final String IS_REMEMBERD = "IS_REMEMBERD";
    private EditText loginUserName, loginPassword;
    private String user_name, full_name, password;
    private boolean isRemember;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    private TextInputLayout usernameLayout, passwordLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        Toast.makeText(this, "isRemember :" + isRemember, Toast.LENGTH_SHORT).show();
        declare();
        isChecked();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getBaseContext(), Sign_up.class);
                startActivity(register);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginUsertxt = loginUserName.getText().toString();
                String loginPasswordtxt = loginPassword.getText().toString();

                boolean check = checkValidation(loginUsertxt, loginPasswordtxt);
                if (check) {
                    if (user_name.equals(loginUsertxt) && loginPasswordtxt.equals(password)) {
                        if (remember_me.isChecked()) {

                            edit.putBoolean(IS_REMEMBERD, true);
                            edit.apply();
                        }
                        Intent Main = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(Main);
                        finish();
                    } else {
                        Toast.makeText(Login.this, " error Wrong UserName or password", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    private void declare() {
        login = findViewById(R.id.login2);
        register = findViewById(R.id.register);
        loginUserName = findViewById(R.id.loginUserName);
        loginPassword = findViewById(R.id.loginPassword);
        remember_me = findViewById(R.id.remember_me);
        usernameLayout = findViewById(R.id.login_username_layout);
        passwordLayout = findViewById(R.id.login_password_layout);
        sp = getSharedPreferences("user_info", MODE_PRIVATE);
        edit = sp.edit();
        user_name = sp.getString(Sign_up.USER_NAME, "0");
        full_name = sp.getString(Sign_up.FULL_NAME, "0");
        password = sp.getString(Sign_up.PASSWORD, "0");
        isRemember = sp.getBoolean(IS_REMEMBERD, false);
        if (!user_name.equals("0") & !full_name.equals("0") & !password.equals("0")) {
            loginUserName.setText(user_name);
            loginPassword.setText(password);
        }
    }

    private void isChecked() {
        Toast.makeText(Login.this, "remember is :" + isRemember, Toast.LENGTH_SHORT).show();

        if (isRemember) {
            Intent MainActivity = new Intent(getBaseContext(), MainActivity.class);
            startActivity(MainActivity);
            finish();
        }
    }

    private boolean checkValidation(String userName, String password) {

        boolean usernameF = false, passwordf = false;
        if (!TextUtils.isEmpty(userName)) {
            usernameLayout.setErrorEnabled(false);
            usernameF = true;

        } else {
            usernameLayout.setError("Input required");

            usernameLayout.setErrorEnabled(true);

        }
        if (!TextUtils.isEmpty(password)) {
            passwordLayout.setErrorEnabled(false);
            passwordf = true;
        } else {
            passwordLayout.setError("Input required");

            passwordLayout.setErrorEnabled(true);


        }

        return usernameF && passwordf;
    }
}

