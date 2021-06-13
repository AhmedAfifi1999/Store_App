package com.example.final_project.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.final_project.R;
import com.example.final_project.imageOperation.SaveImage;
import com.example.final_project.model.Product;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> data;
    private onClickItem item;
    private Context context;

    public ProductAdapter(List<Product> data, onClickItem item, Context context) {
        this.data = data;
        this.item = item;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        String cashSting = context.getString(R.string.cash);
        String installmentString = context.getString(R.string.installment);
        SaveImage image = new SaveImage();
        Bitmap imageBitMap;

        //--
        Product product = data.get(position);

        if (product.getImage() != null || !product.getImage().isEmpty()) {
            imageBitMap = image.loadImageFromStorage(product.getImage());
            //holder.productImage.setImageURI(Uri.parse(product.getImage()));
            holder.productImage.setImageBitmap(imageBitMap);
        }
        holder.productTitle.setText(product.getTitle());
        holder.productPrice.setText(product.getPrice() + "");
        holder.productIsCash.setText(product.isCash() ? cashSting : installmentString);
       // Glide.with(context).load("https://realfood.tesco.com/media/images/Burger-31LGH-a296a356-020c-4969-86e8-d8c26139f83f-0-1400x919.jpg").into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private CardView productItem;
        private CircleImageView productImage;
        private TextView productTitle, productPrice, productIsCash;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productItem = itemView.findViewById(R.id.product_item);
            productImage = itemView.findViewById(R.id.product_img);
            productTitle = itemView.findViewById(R.id.product_title);
            productPrice = itemView.findViewById(R.id.purchase_product_price);
            productIsCash = itemView.findViewById(R.id.product_cash);
            productItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.onClickItemSelected(data.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface onClickItem {
        void onClickItemSelected(Product product);
    }
}
