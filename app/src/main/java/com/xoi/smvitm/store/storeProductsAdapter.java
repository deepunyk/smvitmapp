package com.xoi.smvitm.store;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.xoi.smvitm.R;

import java.util.List;

public class storeProductsAdapter extends RecyclerView.Adapter<storeProductsAdapter.ProductViewHolder> {

    private List<Product> list;
    private Context context;
    private OnItemClickListener mListener;

    public storeProductsAdapter(List<Product> list, Context context) {
        this.list = list;
        this.context = context;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.store_item, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        Product product =list.get(position);

        holder.textViewTitle.setText(product.getTitle());
        holder.textViewDate.setText(product.getDate());
        holder.textViewCat.setText(product.getCat());
        holder.textViewPrice.setText("₹ "+product.getPrice());
        Glide.with(context)
                .load(product.getImage())
                .into(holder.imageView);

      /*  final String des = product.getDes();
        //final String mobile = product.getMobile();
        final String owner = product.getOwner();
        final String name = product.getName();
        final String sem = product.getSem();
        final String section = product.getSection();
        final String email = product.getEmail();
        final String branchid = product.getBranchid();*/


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        Intent intent = new Intent(context,storeProductDetails.class);
        /*intent.putExtra("des", des);
        //intent.putExtra("mobile",mobile);
        intent.putExtra("owner",owner);
        intent.putExtra("name",name);
        intent.putExtra("sem",sem);
        intent.putExtra("section",section);
        intent.putExtra("email",email);
        intent.putExtra("branchid",branchid);*/
        context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitle, textViewDate, textViewCat, textViewPrice,textViewDes,textViewUSN,textViewName,textViewSem,textViewSection,textViewEmail,textViewBranchid;
        public ImageView imageView;
        public RelativeLayout parentLayout;

        public ProductViewHolder(View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parent_layout);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewCat = itemView.findViewById(R.id.textViewCat);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
            /*textViewDes = itemView.findViewById(R.id.textViewDes);
            textViewUSN = itemView.findViewById(R.id.ownerUSN);
            textViewName = itemView.findViewById(R.id.ownerName);
            textViewSem = itemView.findViewById(R.id.ownerSem);
            textViewSection = itemView.findViewById(R.id.ownerSection);
            textViewEmail = itemView.findViewById(R.id.ownerEmail);
            textViewBranchid = itemView.findViewById(R.id.ownerBranch);*/

        }
    }
}