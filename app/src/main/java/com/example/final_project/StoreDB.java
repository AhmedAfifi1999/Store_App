package com.example.final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class StoreDB extends SQLiteOpenHelper {
    public static final String DB_NAME = "store_db";
    public static final int DB_VERSION = 1;

    // product table
    public static final String PRODUCT_TB_NAME = "product";
    public static final String PRODUCT_id = "id";
    public static final String PRODUCT_TITLE = "title";
    public static final String PRODUCT_DESCRIPTION = "description";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_IMAGE_PATH = "image";
    public static final String PRODUCT_IS_CASH = "cash";

    public StoreDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //when Create DB  onetime Just
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PRODUCT_TB_NAME + " (" + PRODUCT_id + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + PRODUCT_TITLE + " Text , " + PRODUCT_DESCRIPTION + " text , " + PRODUCT_PRICE + " REAL , " + PRODUCT_IMAGE_PATH + " text ," + PRODUCT_IS_CASH + "  integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on update database
        //Drop table >>
        //db.execSQL("drop table if EXISTS product");
    }

    public boolean insertProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_TITLE, product.getTitle());
        values.put(PRODUCT_DESCRIPTION, product.getDescription());
        values.put(PRODUCT_PRICE, product.getPrice());
        values.put(PRODUCT_IMAGE_PATH, product.getImage());
        values.put(PRODUCT_IS_CASH, product.isCash());
        long result = db.insert(PRODUCT_TB_NAME, null, values);
        return result != -1;
    }

    public boolean updateProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_TITLE, product.getTitle());
        values.put(PRODUCT_DESCRIPTION, product.getDescription());
        values.put(PRODUCT_PRICE, product.getPrice());
        values.put(PRODUCT_IMAGE_PATH, product.getImage());
        values.put(PRODUCT_IS_CASH, product.isCash());
        String args[] = {String.valueOf(product.getId())};
        int result = db.update(PRODUCT_TB_NAME, values, "id=?", args);
        return result > 0;
    }

    public long getProductCount() {
        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, PRODUCT_TB_NAME);
    }

    public boolean deleteProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        String args[] = {String.valueOf(product.getId())};
        int result = db.delete(PRODUCT_TB_NAME, "id=?", args);

        return result > 0;
    }


    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + PRODUCT_TB_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(cursor.getColumnIndex(PRODUCT_id));//0
                String title = cursor.getString(cursor.getColumnIndex(PRODUCT_TITLE));//1
                String description = cursor.getString(cursor.getColumnIndex(PRODUCT_DESCRIPTION));//2
                double price = cursor.getDouble(cursor.getColumnIndex(PRODUCT_PRICE));//3
                String image = cursor.getString(cursor.getColumnIndex(PRODUCT_IMAGE_PATH));//4
                int cash = cursor.getInt(cursor.getColumnIndex(PRODUCT_IS_CASH));//5

                Product product = new Product(id, title, description, price, image, (cash == 1) ? true : false);
                products.add(product);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return products;
    }

}
