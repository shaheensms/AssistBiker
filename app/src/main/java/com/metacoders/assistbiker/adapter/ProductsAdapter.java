package com.metacoders.assistbiker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.models.ProductsModel;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private Context ctx;
    private List<ProductsModel> productList;

    public ProductsAdapter(Context ctx, List<ProductsModel> productList) {
        this.ctx = ctx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_product, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder holder, int position) {

        ProductsModel product = productList.get(position);

        holder.pTitle.setText(product.getProduct_title());
        holder.pPrice.setText("$" + product.getProduct_price().toString());
        holder.pCategory.setText(product.getProduct_keywords());

        Glide.with(ctx)
                .load(product.getProduct_img1())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .into(holder.pImage);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView pImage;
        public TextView pTitle;
        public TextView pPrice;
        public TextView pCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            pImage = itemView.findViewById(R.id.product_imageview);
            pTitle = itemView.findViewById(R.id.product_title_tv);
            pPrice = itemView.findViewById(R.id.product_price_tv);
            pCategory = itemView.findViewById(R.id.product_category_tv);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
