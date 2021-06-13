package com.example.final_project.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.final_project.R;
import com.example.final_project.adapters.ProductAdapter;
import com.example.final_project.database.StoreDB;
import com.example.final_project.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductAdapter.onClickItem {
    private RecyclerView recyclerView;
    private StoreDB db;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    private FloatingActionButton floatingActionButton;
    List<Product> product3;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton = findViewById(R.id.addNewItem_btn);
        sp = getSharedPreferences("user_info", MODE_PRIVATE);
        edit = sp.edit();

        db = new StoreDB(this);

        recyclerView = findViewById(R.id.rv_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        //  List<Product> product = new ArrayList<>();
        product3 = db.getAllProducts();

        setData(product3);


    }

    public void setData(List<Product> product ){
        productAdapter = new ProductAdapter(product, this, this);
        recyclerView.setAdapter(productAdapter);
    }
    @Override
    public void onClickItemSelected(Product product) {

        Toast.makeText(this, "Price : " + product.getPrice(), Toast.LENGTH_SHORT).show();
        Log.d("price", "Price: " + product.getPrice());
        Intent BuyProduct_Activity = new Intent(getBaseContext(), BuyProduct.class);
        BuyProduct_Activity.putExtra("Title", product.getTitle());
        BuyProduct_Activity.putExtra("Image", product.getImage());
        BuyProduct_Activity.putExtra("Description", product.getDescription());
        BuyProduct_Activity.putExtra("Price", product.getPrice());
        BuyProduct_Activity.putExtra("Id", product.getId());
        startActivity(BuyProduct_Activity);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.mainActivity_menu_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                product3 = db.getSearchedProducts(query);
                setData(product3);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                product3 = db.getAllProducts();
                setData(product3);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.setting:

                Intent settingActivity = new Intent(getBaseContext(), SettingActivity.class);
                startActivity(settingActivity);

                Toast.makeText(this, "go to setting Activity", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.logout:

                edit.putBoolean(Login.IS_REMEMBERD, false);
                edit.apply();

                Intent loginActivity = new Intent(getBaseContext(), Login.class);
                startActivity(loginActivity);
                finish();
                Toast.makeText(this, "go to login Activity", Toast.LENGTH_SHORT).show();

                return true;
        }
        return false;


    }

    public void addNewProduct(View view) {

        Intent addProductActivity = new Intent(getBaseContext(), AddProduct.class);
        startActivity(addProductActivity);


    }
}
