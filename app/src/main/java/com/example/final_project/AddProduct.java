package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity {

    private StoreDB db;
    private EditText ProductTitle, ProductPrice, ProductDescription;
    private RadioGroup rg;
    private RadioButton Cash, Installment;
    private Button Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        db = new StoreDB(this);
        ProductTitle = findViewById(R.id.product_name);
        ProductDescription=findViewById(R.id.product_description);
        ProductPrice = findViewById(R.id.product_price);
        rg = findViewById(R.id.radioGroup2);
        Cash = findViewById(R.id.cash);
        Installment = findViewById(R.id.installment);
        Save = findViewById(R.id.save_product);



        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product_title = ProductTitle.getText().toString();
                double product_price = Double.parseDouble(ProductPrice.getText().toString());
                String product_description = ProductDescription.getText().toString();
                boolean isCash = Cash.isChecked();
                //check if he insert using TextUtil
                Product product = new Product(product_title, product_description, product_price, isCash);
                boolean isSuccess = db.insertProduct(product);
                Toast.makeText(AddProduct.this, "this insert is "+isSuccess, Toast.LENGTH_SHORT).show();

                long count = db.getProductCount();
                Toast.makeText(AddProduct.this, "number of products is : "+count, Toast.LENGTH_SHORT).show();


            }
        });

    }
}
