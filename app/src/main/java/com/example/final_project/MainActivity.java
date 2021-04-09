package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductAdapter.onClickItem {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        List<Product> product = new ArrayList<>();
        product.add(new Product(
                1,
                "test1",
                "test1 test1 test1",
                50.5,
                "",
                true
        ));
        product.add(new Product(
                1,
                "test2",
                "test1 test1 test1",
                50.5,
                "",
                true
        ));
        product.add(new Product(
                1,
                "test3",
                "test1 test1 test1",
                50.5,
                "",
                true
        ));
        product.add(new Product(
                1,
                "test4",
                "test1 test1 test1",
                50.5,
                "",
                true
        ));
        product.add(new Product(
                1,
                "test2",
                "test1 test1 test1",
                50.5,
                "",
                true
        ));
        product.add(new Product(
                1,
                "test3",
                "test1 test1 test1",
                50.5,
                "",
                true
        ));
        product.add(new Product(
                1,
                "test4",
                "test1 test1 test1",
                50.5,
                "",
                true
        ));
        ProductAdapter productAdapter = new ProductAdapter(product, this, this);
        recyclerView.setAdapter(productAdapter);

    }

    @Override
    public void onClickItemSelected(Product product) {
        Toast.makeText(this, product.getProductTitle(), Toast.LENGTH_SHORT).show();
    }
}
