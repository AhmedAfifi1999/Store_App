package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductAdapter.onClickItem {
    private RecyclerView recyclerView;
    private StoreDB db;
    SharedPreferences sp;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("user_info", MODE_PRIVATE);
        edit = sp.edit();

        db = new StoreDB(this);
        recyclerView = findViewById(R.id.rv_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        List<Product> product = new ArrayList<>();
        List<Product> product3;

        product3 = db.getAllProducts();
    /*    product.add(new Product(
                1,
                "test1",
                "test1 test1 test1",
                50.5,
                "",
                true
        ));*/

        ProductAdapter productAdapter = new ProductAdapter(product3, this, this);
        recyclerView.setAdapter(productAdapter);

    }

    @Override
    public void onClickItemSelected(Product product) {

        Toast.makeText(this, product.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_menu, menu);
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
}
