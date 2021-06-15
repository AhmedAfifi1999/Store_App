package com.example.final_project.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.final_project.R;
import com.example.final_project.database.StoreDB;
import com.example.final_project.imageOperation.SaveImage;
import com.example.final_project.model.Product;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AddProduct extends AppCompatActivity {
    private static final String TAG = "AddProduct";
    private StoreDB db;
    private EditText ProductTitle, ProductPrice, ProductDescription;
    private RadioGroup rg;
    private RadioButton Cash, Installment;
    private Button Save;
    private CircleImageView productImage;
    Bitmap image;
    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_CODE = 100;
    Uri imageUri;
    OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.product_add);
        db = new StoreDB(this);
        ProductTitle = findViewById(R.id.product_add_name);
        ProductDescription = findViewById(R.id.product_add_description);
        ProductPrice = findViewById(R.id.productAdd_price);
        rg = findViewById(R.id.product_add_group);
        Cash = findViewById(R.id.product_add_cash);
        Installment = findViewById(R.id.product_add_installment);
        Save = findViewById(R.id.product_add_save);
        productImage = findViewById(R.id.product_add_img);
        //TODO radiobutton get error when i change it [SOLVED]
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product_title = ProductTitle.getText().toString();
                double pPriceTemp = Double.parseDouble(ProductPrice.getText().toString());
                double product_price = ProductPrice.getText().toString().equals("") ? 0.0 : pPriceTemp;
                Toast.makeText(AddProduct.this, "pPriceTemp  : " + pPriceTemp, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: price :" + product_price);
                Toast.makeText(AddProduct.this, "product_price  : " + product_price, Toast.LENGTH_SHORT).show();
                //---
                SaveImage saveImage = new SaveImage(AddProduct.this);
                String path = "";
                path = saveImage.saveImageInStorage(image, "Products_Image", product_title + "_" + SystemClock.currentThreadTimeMillis());
                ///----
                String product_description = ProductDescription.getText().toString();
                boolean isCash = Cash.isChecked();
                //TODO check if he insert using TextUtil
                Product product = new Product(product_title, product_description, product_price, path, isCash);
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
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && data != null) {
            imageUri = data.getData();
            try {
                InputStream is = getContentResolver().openInputStream(imageUri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                image = BitmapFactory.decodeStream(is, null, options);
                productImage.setImageBitmap(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
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


}
