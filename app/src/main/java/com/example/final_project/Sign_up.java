package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Magnifier;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Sign_up extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String USER_NAME = "user_name";
    public static final String PASSWORD = "password";
    public static final String FULL_NAME = "full_name";
    public static final String USER_ID = "user_id";
    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_CODE = 100;


    Uri imageUri;
    OutputStream outputStream;

    private TextView tvDate;
    private EditText fullName, userName, mail, password, rePassword, mobileNumber, address;
    private Button btPickDate, save;
    private CheckBox isAdministrator;
    CircleImageView signup_img;

    SharedPreferences sp;
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
                String username = userName.getText().toString();
                String pass = password.getText().toString();
                String full_name = fullName.getText().toString();

                storeImage();

// check is empty or null  >>
                edit.putLong(USER_ID, System.currentTimeMillis());
                edit.putString(USER_NAME, username);
                edit.putString(PASSWORD, pass);
                edit.putString(FULL_NAME, full_name);
                edit.apply();

                // move to  Login Activity
                Intent login = new Intent(getBaseContext(), Login.class);
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
        mCalender.set(Calendar.YEAR, year);
        mCalender.set(Calendar.MONTH, month);
        mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        // Get the date in form of string
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalender.getTime());
        // Set the textview to the selectedDate String
        tvDate.setText(selectedDate);
    }


    private void declare() {
        //Edit text
        //fullName ,userName ,mail ,password ,rePassword,mobileNumber,address;
        fullName = findViewById(R.id.fullName);
        userName = findViewById(R.id.userName);
        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.rePassword);
        mobileNumber = findViewById(R.id.mobileNumber);
        address = findViewById(R.id.address);
        //TextView
        tvDate = findViewById(R.id.tvDate);
        //Button
        btPickDate = findViewById(R.id.btPickDate);
        save = findViewById(R.id.save);
        //SharedPreference
        sp = getSharedPreferences("user_info", MODE_PRIVATE);
        edit = sp.edit();
        // image
        signup_img = findViewById(R.id.signUp_Image);

    }

    public void chooseImage(View view) {
        Toast.makeText(this, "iam here", Toast.LENGTH_SHORT).show();

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            signup_img.setImageURI(imageUri);


        }
    }


    public void storeImage() {
        if (ContextCompat.checkSelfPermission(Sign_up.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

            saveImage();

        } else {

            askPermission();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 & grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImage();

            } else {

                Toast.makeText(this, "please provide the REQUIRED permission ", Toast.LENGTH_SHORT).show();
            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(Sign_up.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

    }

    private void saveImage() {
        File dir = new File(Environment.getDataDirectory(), "saveImage/");
        Log.d("Image", "saveImage: "+dir);;
        Toast.makeText(this, "start saveImage ", Toast.LENGTH_SHORT).show();

        if (!dir.exists()) {
            dir.mkdir();
            Toast.makeText(this, "make dir ", Toast.LENGTH_SHORT).show();


        } else {

            BitmapDrawable bitmapDrawable = (BitmapDrawable) signup_img.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            File file = new File(dir, System.currentTimeMillis() + ".jpg"); //change to user id > or same >>
            try {
                outputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
           // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Toast.makeText(this, "success Save", Toast.LENGTH_SHORT).show();
            try {
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ;

        }


    }

}
