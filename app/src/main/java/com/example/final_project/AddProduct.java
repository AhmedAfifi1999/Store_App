package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity {
    private static final String TAG = "AddProduct";
    private StoreDB db;
     private EditText ProductTitle, ProductPrice, ProductDescription;
     private RadioGroup rg;
     private RadioButton Cash, Installment;
     private Button Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.product_add);
        db = new StoreDB(this);
           ProductTitle = findViewById(R.id.product_add_name);
        ProductDescription = findViewById(R.id.product_add_description);
        ProductPrice = findViewById(R.id.product_add_price);
        rg = findViewById(R.id.product_add_group);
        Cash = findViewById(R.id.product_add_cash);
        Installment = findViewById(R.id.product_add_installment);
        Save = findViewById(R.id.product_add_save);
        //TODO radiobutton get error when i change it
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product_title = ProductTitle.getText().toString();
                double product_price = Double.parseDouble(ProductPrice.getText().toString().equals("") ? ProductPrice.getText().toString() : "0.0");
                String product_description = ProductDescription.getText().toString();
                boolean isCash = Cash.isChecked();
                //TODO check if he insert using TextUtil
                Product product = new Product(product_title, product_description, product_price, isCash);
                boolean isSuccess = db.insertProduct(product);
                Toast.makeText(AddProduct.this, "this insert is " + isSuccess, Toast.LENGTH_SHORT).show();
                long count = db.getProductCount();
                Toast.makeText(AddProduct.this, "number of products is : " + count, Toast.LENGTH_SHORT).show();
                Intent Main = new Intent(getBaseContext(), MainActivity.class);
                startActivity(Main);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }
}
