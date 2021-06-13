package com.example.final_project.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.imageOperation.SaveImage;
import com.example.final_project.model.Product;
import com.example.final_project.model.Purchase;
import com.example.final_project.database.PurchaseTable;
import com.example.final_project.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder> {

    private List<Purchase> data;
    private Context context;
    private onClickItem item;

    public PurchaseAdapter(List<Purchase> data, Context context, onClickItem item) {
        this.data = data;
        this.context = context;
        this.item = item;
    }

    public PurchaseAdapter(List<Purchase> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PurchaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.last_purchase_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseViewHolder holder, int position) {
//TODO it need to use db to get data where  product id >> just temp test
        String cashSting = context.getString(R.string.cash);
        String installmentString = context.getString(R.string.installment);
        SaveImage image = new SaveImage();
        Bitmap imageBitMap;

        //--
        Purchase purchase = data.get(position);
        Log.d("position", "onBindViewHolder: " + position);
        PurchaseTable purchaseTable = new PurchaseTable(context);

        Product temp = purchaseTable.getProduct(purchase);
        holder.productDate.setText(purchase.getDate());
        holder.productTitle.setText(temp.getTitle());
        holder.productPrice.setText(temp.getPrice() + "");
        holder.totalPrice.setText(temp.getPrice() * purchase.getQuantity() + "");
        holder.productQuantity.setText(purchase.getQuantity() + "");
        holder.productIsCash.setText(temp.isCash() ? cashSting : installmentString);
        if (temp.getImage() != null || !temp.getImage().isEmpty()) {
            imageBitMap = image.loadImageFromStorage(temp.getImage());
            //holder.productImage.setImageURI(Uri.parse(product.getImage()));
            holder.productImage.setImageBitmap(imageBitMap);
        }

        // holder.productID.setText(purchase.getProduct_id() + "");
        //   holder.productQuantity.setText(purchase.getQuantity()+"");
        // Glide.with(context).load("https://realfood.tesco.com/media/images/Burger-31LGH-a296a356-020c-4969-86e8-d8c26139f83f-0-1400x919.jpg").into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class PurchaseViewHolder extends RecyclerView.ViewHolder {
        private CardView productItem;
        private CircleImageView productImage;
        private TextView productDate, productPrice, totalPrice, productTitle, productQuantity, productIsCash;


        public PurchaseViewHolder(@NonNull View itemView) {
            super(itemView);
            productQuantity = itemView.findViewById(R.id.purchase_product_quantity);
            productTitle = itemView.findViewById(R.id.purchase_product_title);
            productImage = itemView.findViewById(R.id.purchase_product_img);
            totalPrice = itemView.findViewById(R.id.purchase_product_totalPrice);
            productDate = itemView.findViewById(R.id.purchase_date);
            productPrice = itemView.findViewById(R.id.purchase_product_price);
            productIsCash = itemView.findViewById(R.id.price_type);

        }
    }

    public interface onClickItem {
        void onClickItemSelected(Purchase purchase);
    }
}
