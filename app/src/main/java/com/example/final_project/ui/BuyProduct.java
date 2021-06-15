package com.example.final_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.final_project.R;
import com.example.final_project.database.PurchaseTable;
import com.example.final_project.imageOperation.SaveImage;
import com.example.final_project.model.Purchase;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BuyProduct extends AppCompatActivity {


    TextView productPrice, totalProductPrice, descriptionProduct, title;
    ElegantNumberButton counter;
    ImageView image;
    Button buyProductBtn;
    double Price;
    int num, Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_product);
        productPrice = findViewById(R.id.buyProduct_Price);
        totalProductPrice = findViewById(R.id.buyProduct_totalPrice);
        descriptionProduct = findViewById(R.id.buyProduct_details);
        image = findViewById(R.id.buyProduct_ImageOrder);
        buyProductBtn = findViewById(R.id.buyProduct_order_btn);
        title = findViewById(R.id.buyProduct_Title);
        counter = findViewById(R.id.buyProduct_quantity);

        Intent productIntent = getIntent();
        String Title = productIntent.getStringExtra("Title");
        String Image = productIntent.getStringExtra("Image");
        String Description = productIntent.getStringExtra("Description");
        Price = productIntent.getDoubleExtra("Price", -1);
        Id = productIntent.getIntExtra("Id", -1);
        Toast.makeText(this, "Price : " + Price, Toast.LENGTH_SHORT).show();
        title.setText(Title);
        productPrice.setText(Price + "$");
        totalProductPrice.setText(Price + "$");
        descriptionProduct.setText(Description);
        if (Image != null || !Image.isEmpty()) {
            SaveImage imageLoad = new SaveImage(BuyProduct.this);
            Bitmap imageBitMap;
            imageBitMap = imageLoad.loadImageFromStorage(Image);
            image.setImageBitmap(imageBitMap);
        }

        counter.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = Integer.parseInt(counter.getNumber());
                totalProductPrice.setText(Price * num + "$");
            }
        });

    }

    public void SendRequestOrder(View view) {

        new MaterialAlertDialogBuilder(BuyProduct.this)
                .setTitle("Buy Product")
                .setMessage("Are You Sure You want to Buy this Product ?")
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        acceptOrder();
                        Intent MainIntent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(MainIntent);
                        finish();

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();


    }

    public void acceptOrder() {

        if (num >= 1) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd ");
            String currentDate = sdf.format(new Date());
            Toast.makeText(this, "currentDate is :" + currentDate, Toast.LENGTH_SHORT).show();


            PurchaseTable purchaseTable = new PurchaseTable(this);
            Purchase purchase = new Purchase(Id, currentDate, num);
            boolean isSave = purchaseTable.insertPurchase(purchase);
            Log.d("Save", "IsSave: " + isSave);


        } else {
            Toast.makeText(this, "You Can't buy 0 product", Toast.LENGTH_SHORT).show();
        }
    }
}
