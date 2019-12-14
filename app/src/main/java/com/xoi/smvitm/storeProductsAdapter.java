package com.xoi.smvitm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class storeProductsAdapter extends RecyclerView.Adapter<storeProductsAdapter.ProductViewHolder> {

    private List<Product> list;
    private Context context;

    public storeProductsAdapter(List<Product> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.store_item, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product =list.get(position);

        holder.textViewTitle.setText(product.getTitle());
        holder.textViewDate.setText(product.getDate());
        holder.textViewCat.setText(product.getCat());
        holder.textViewPrice.setText("₹ "+product.getPrice());
        Glide.with(context)
                .load(product.getImage())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewDate, textViewCat, textViewPrice;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewCat = itemView.findViewById(R.id.textViewCat);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}