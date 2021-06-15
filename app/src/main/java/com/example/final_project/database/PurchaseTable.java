package com.example.final_project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.final_project.model.Product;
import com.example.final_project.model.Purchase;

import java.util.ArrayList;

public class PurchaseTable extends StoreDB {


    // product table
    public static final String PURCHASE_TB_NAME = "purchase";
    public static final String purchase_ID = "id";
    public static final String PRODUCT_ID = "product_id";
    public static final String PURCHASE_PRODUCT_NUMBER = "quantity";
    public static final String PURCHASE_PRODUCT_DATE = "date";
    public static final String PURCHASE_PRODUCT_NAME = "name";

    public PurchaseTable(Context context) {
        super(context);
    }


    public static void createPurchaseTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PURCHASE_TB_NAME + " (" + purchase_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + PURCHASE_PRODUCT_DATE + " Text , " + PRODUCT_ID + " integer NOT NULL , " + PURCHASE_PRODUCT_NUMBER + "  integer)");

      /*
          db.execSQL("CREATE TABLE IF NOT EXISTS " + PURCHASE_TB_NAME + " (" + purchase_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + PURCHASE_PRODUCT_DATE + " Text , " + PURCHASE_PRODUCT_NAME + " Text , " +PRODUCT_ID + " integer NOT NULL , " + PURCHASE_PRODUCT_NUMBER + "  integer)");

         */
    }

    public static void updatePurchaseTable(SQLiteDatabase db) {

        db.execSQL("drop table if EXISTS " + PURCHASE_TB_NAME);

    }

    public boolean deletePurchase(Purchase purchase) {
        SQLiteDatabase db = getWritableDatabase();
        String args[] = {String.valueOf(purchase.getId())};
        int result = db.delete(PURCHASE_TB_NAME, "id=?", args);

        return result > 0;
    }

    public long getPurchaseCount() {
        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, PURCHASE_TB_NAME);
    }

    public boolean insertPurchase(Purchase purchase) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_ID, purchase.getProduct_id());
        values.put(PURCHASE_PRODUCT_NUMBER, purchase.getQuantity());
        values.put(PURCHASE_PRODUCT_DATE, purchase.getDate());
        long result = db.insert(PURCHASE_TB_NAME, null, values);
        return result != -1;
    }

    public boolean insertPurchaseWithName(Purchase purchase) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_ID, purchase.getProduct_id());
        values.put(PURCHASE_PRODUCT_NUMBER, purchase.getQuantity());
        values.put(PURCHASE_PRODUCT_NAME, purchase.getName());
        values.put(PURCHASE_PRODUCT_DATE, purchase.getDate());
        long result = db.insert(PURCHASE_TB_NAME, null, values);
        return result != -1;
    }

    public ArrayList<Purchase> getAllPurchases() {
        ArrayList<Purchase> purchasesArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + PURCHASE_TB_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(cursor.getColumnIndex(purchase_ID));//0
                int product_id = cursor.getInt(cursor.getColumnIndex(PRODUCT_ID));//0
                int quantity = cursor.getInt(cursor.getColumnIndex(PURCHASE_PRODUCT_NUMBER));//1
                String date = cursor.getString(cursor.getColumnIndex(PURCHASE_PRODUCT_DATE));//2

                Purchase purchases = new Purchase(id, product_id, date, quantity);
                purchasesArrayList.add(purchases);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return purchasesArrayList;

    }

    public Product getProduct(Purchase purchase) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Product> products = new ArrayList<>();
        String args[] = {String.valueOf(purchase.getProduct_id())};
        //purchase.getProduct_id()
        Product product = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + StoreDB.PRODUCT_TB_NAME + " pr ," + PURCHASE_TB_NAME +
                " pu  where pr." + StoreDB.PRODUCT_id + " = pu." + purchase_ID + " and  pu." + PurchaseTable.PRODUCT_ID + " = ? ", args);


        int i = 0;
        if (cursor != null && cursor.moveToNext()) {

            i++;
            int id = cursor.getInt(cursor.getColumnIndex(PRODUCT_id));//0
            String title = cursor.getString(cursor.getColumnIndex(PRODUCT_TITLE));//1
            Log.d("TAG", "PRODUCT_TITLE: " + title + i);
            String description = cursor.getString(cursor.getColumnIndex(PRODUCT_DESCRIPTION));//2
            double price = cursor.getDouble(cursor.getColumnIndex(PRODUCT_PRICE));//3
            String image = cursor.getString(cursor.getColumnIndex(PRODUCT_IMAGE_PATH));//4
            int cash = cursor.getInt(cursor.getColumnIndex(PRODUCT_IS_CASH));//5

            product = new Product(id, title, description, price, image, (cash == 1) ? true : false);
            //        products.add(product);
        }
        cursor.close();

//


        return product;
    }

    public void deleteAllPurchase() {
        SQLiteDatabase db = getWritableDatabase();
        //db.execSQL("drop table "+ PURCHASE_TB_NAME);
        db.execSQL("delete from "+ PURCHASE_TB_NAME);

         db.close();

    }
}
