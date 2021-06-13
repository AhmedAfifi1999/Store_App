package com.example.final_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.final_project.R;
import com.example.final_project.database.PurchaseTable;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void addItem(View view) {
        Intent addProductActivity = new Intent(getBaseContext(), AddProduct.class);
        startActivity(addProductActivity);

    }

    public void ChangePassword(View view) {
        Intent changePasswordActivity = new Intent(getBaseContext(), ChangePassword.class);
        startActivity(changePasswordActivity);


    }

    public void ShowAllPurchase(View view) {
        Intent LastPurchase = new Intent(getBaseContext(), LastPurchase.class);
        startActivity(LastPurchase);


    }

    public void ClearAllPurchase(View view) {
        PurchaseTable db= new PurchaseTable(this);
        db.deleteAllPurchase();
        Toast.makeText(this, "Delete Is Ok", Toast.LENGTH_SHORT).show();
    }
}
