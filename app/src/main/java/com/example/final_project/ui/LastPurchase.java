package com.example.final_project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.final_project.R;
import com.example.final_project.adapters.PurchaseAdapter;
import com.example.final_project.database.PurchaseTable;
import com.example.final_project.model.Purchase;

import java.util.ArrayList;
import java.util.List;

public class LastPurchase extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_purchase);

        recyclerView = findViewById(R.id.last_purchase);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        PurchaseTable purchaseTable = new PurchaseTable(this);
        List<Purchase> Purchase = new ArrayList<>();
        List<Purchase> product3;

        product3 = purchaseTable.getAllPurchases();

        PurchaseAdapter PurchaseAdapter = new PurchaseAdapter(product3, this);

        recyclerView.setAdapter(PurchaseAdapter);


    }
}
