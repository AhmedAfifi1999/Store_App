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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.R;
import com.example.final_project.imageOperation.SaveImage;
import com.google.android.material.textfield.TextInputLayout;
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
    public static final String IS_ADMIN = "is_admin";

    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_CODE = 100;


    Uri imageUri;
    OutputStream outputStream;

    private TextView tvDate;
    private EditText fullName, userName, email, password, rePassword, address, phoneNumber;
    private Button btPickDate, save;
    private CheckBox isAdministrator;
    CircleImageView signup_img;
    TextInputLayout layoutPassword;
    TextInputLayout layoutRePassword;
    TextInputLayout layoutFullName;
    TextInputLayout layoutUserName;
    TextInputLayout layoutEmail;


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
        email = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.rePassword);
        // emailAddress = findViewById(R.id.Address);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.phoneNumber);
        ///--
        layoutPassword = findViewById(R.id.signUp_password_layout);
        layoutRePassword = findViewById(R.id.signUp_rePassword_layout);
        layoutFullName = findViewById(R.id.signUp_fullName_layout);
        layoutUserName = findViewById(R.id.signUp_UserName_layout);
        layoutEmail = findViewById(R.id.signUp_email_layout);
        //TextView
        tvDate = findViewById(R.id.tvDate);
        //Button
        btPickDate = findViewById(R.id.btPickDate);
        save = findViewById(R.id.save);
        isAdministrator = findViewById(R.id.checkbox_meat);
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

    public boolean checkPassword() {

        String pass = password.getText().toString();
        String rePass = rePassword.getText().toString();
        if (!TextUtils.isEmpty(pass)) {
            layoutPassword.setErrorEnabled(false);


        } else {
            layoutPassword.setError("Input required");
            layoutPassword.setErrorEnabled(true);
        }

        if (!TextUtils.isEmpty(rePass)) {
            layoutRePassword.setErrorEnabled(false);


        } else {

            layoutRePassword.setError("Input required");
            layoutRePassword.setErrorEnabled(true);
        }

        if (!TextUtils.isEmpty(rePass) & !TextUtils.isEmpty(pass)) {
            if (pass.equals(rePass)) {

                return true;
            } else {

                layoutRePassword.setError("Pass not Equal");
                layoutRePassword.setErrorEnabled(true);
                layoutPassword.setError("Pass not Equal");
                layoutPassword.setErrorEnabled(true);

            }
        }

        return false;
    }


    public void save_SignUp(View view) {
        String emailAddress = email.getText().toString();
        String username = userName.getText().toString();
        String pass = password.getText().toString();
        String full_name = fullName.getText().toString();
        boolean isAdmin = isAdministrator.isChecked();

        boolean userAndFullNameAndEmail = checkStringValues(full_name, username, emailAddress);
        boolean a = checkPassword();
        if (a && userAndFullNameAndEmail) {
            getNumber();
// check is empty or null  >>
            edit.putLong(USER_ID, System.currentTimeMillis());
            edit.putString(USER_NAME, username);
            edit.putString(PASSWORD, pass);
            edit.putBoolean(IS_ADMIN, isAdmin);
            edit.putString(FULL_NAME, full_name);
            edit.apply();

            SaveImage saveImage = new SaveImage(Sign_up.this);
            String path = saveImage.saveImageInStorage(image, "Users_Image", username + "_" + SystemClock.currentThreadTimeMillis());
            Log.d("PATH", "path : " + path);

            // move to  Login Activity
            Intent login = new Intent(getBaseContext(), Login.class);
            startActivity(login);
            finish();
        } else {

            Toast.makeText(Sign_up.this, "Error", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean checkStringValues(String fullName, String userName, String email) {

        boolean fullNameF = false, userNameF = false, emailF = false;

        if (!TextUtils.isEmpty(fullName)) {
            fullNameF = true;
            layoutFullName.setErrorEnabled(false);

        } else {
            layoutFullName.setError("Input required");
            layoutFullName.setErrorEnabled(true);
        }
        if (!TextUtils.isEmpty(userName)) {
            userNameF = true;
            layoutUserName.setErrorEnabled(false);

        } else {
            layoutUserName.setError("Input required");
            layoutUserName.setErrorEnabled(true);
        }
        if (!TextUtils.isEmpty(email)) {
            layoutEmail.setErrorEnabled(false);

            emailF = true;
        } else {
            layoutEmail.setError("Input required");
            layoutEmail.setErrorEnabled(true);
        }


        return (emailF & userNameF & fullNameF);
    }
}
