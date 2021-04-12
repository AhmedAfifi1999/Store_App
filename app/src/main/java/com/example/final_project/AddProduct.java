package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddProduct extends AppCompatActivity {

    private StoreDB db;
    private EditText ProductName, ProductPrice, ProductDescription;
    private RadioGroup g1;
    private RadioButton Cash, Installment;
    private Button Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        db = new StoreDB(this);
        ProductName = findViewById(R.id.product_name);
        ProductPrice = findViewById(R.id.product_price);
        g1 = findViewById(R.id.radioGroup2);
        Cash = findViewById(R.id.cash);
        Installment = findViewById(R.id.installment);
        Save = findViewById(R.id.save_product);


    }
}
