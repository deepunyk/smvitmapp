package com.xoi.smvitm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class storeProductsAdapter extends RecyclerView.Adapter<storeProductsAdapter.ProductViewHolder> {


    private Context mCtx;
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> shortdesc = new ArrayList<String>();
    private ArrayList<String> rating = new ArrayList<String>();
    private ArrayList<String> price= new ArrayList<String>();
    private ArrayList<String> imageLink = new ArrayList<String>();

    public storeProductsAdapter(ArrayList<String> title,ArrayList<String> shortdesc,ArrayList<String> rating,ArrayList <String> price,ArrayList<String> imageLink,Context mCtx) {
        this.mCtx = mCtx;

        this.title = title;
        this.shortdesc = shortdesc;
        this.rating = rating;
        this.price = price;
        this.imageLink = imageLink;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.store_item, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        holder.textViewTitle.setText(title.get(position));
        holder.textViewShortDesc.setText(shortdesc.get(position));
        holder.textViewRating.setText(rating.get(position));
        holder.textViewPrice.setText("₹ "+price.get(position));
        Glide.with(mCtx).load(imageLink.get(position)).into(holder.imageView);

        if(title.get(position).equals("-")){
            holder.textViewTitle.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}