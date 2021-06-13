package com.example.final_project.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.R;
import com.example.final_project.imageOperation.SaveImage;
import com.hbb20.CountryCodePicker;

import java.io.FileNotFoundException;
import java.io.InputStream;
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
    private EditText fullName, userName, mail, password, rePassword, emailAddress, address, phoneNumber;
    private Button btPickDate, save;
    private CheckBox isAdministrator;
    CircleImageView signup_img;

    CountryCodePicker ccp;

    SharedPreferences sp;
    SharedPreferences.Editor edit;
    String selected_country_code;
    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        declare();
        btPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker mDatePickerDialogFragment;
                mDatePickerDialogFragment = new DatePicker();
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

                getNumber();
// check is empty or null  >>
                edit.putLong(USER_ID, System.currentTimeMillis());
                edit.putString(USER_NAME, username);
                edit.putString(PASSWORD, pass);
                edit.putString(FULL_NAME, full_name);
                edit.apply();

                SaveImage saveImage = new SaveImage(Sign_up.this);
               String path = saveImage.saveImageInStorage(image, "Users_Image", username + "_" + SystemClock.currentThreadTimeMillis());
                Log.d("PATH", "path : "+ path);

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
        // emailAddress = findViewById(R.id.Address);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.phoneNumber);

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
        ccp = findViewById(R.id.ccp);

    }

    public void chooseImage(View view) {

        getPermission();

    }

    public void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            if (data != null) {
                imageUri = data.getData();
                //   signup_img.setImageURI(imageUri);
                try {
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    image = BitmapFactory.decodeStream(is, null, options);
                    signup_img.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE,
                                WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE);
            } else {
                openGallery();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                requestCode == REQUEST_CODE) {
            openGallery();
        }
    }




    public void getNumber() {

        String fullNumber = ccp.getFullNumber() + phoneNumber.getText().toString();
        Toast.makeText(this, "Full Number is " + fullNumber, Toast.LENGTH_SHORT).show();

    }


}
