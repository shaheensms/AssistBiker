package com.metacoders.assistbiker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.Utils.Constants;
import com.metacoders.assistbiker.models.ProductsModel;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private Context ctx;
    private List<ProductsModel> productList;
    private ItemClickListenter itemClickListenter ;

    public ProductsAdapter(Context ctx, List<ProductsModel> productList , ItemClickListenter itemClickListenter ) {
        this.ctx = ctx;
        this.productList = productList;
        this.itemClickListenter = itemClickListenter ;
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_product, parent, false);

        return new ViewHolder(view , itemClickListenter);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder holder, int position) {

        ProductsModel product = productList.get(position);

        holder.pTitle.setText(product.getProduct_title());
        holder.pPrice.setText("৳ " + product.getProduct_price().toString());
        holder.pCategory.setText(product.getProduct_keywords());



        Glide.with(ctx)
                .load(Constants.IMAGE_URL + product.getProduct_img1())
                .thumbnail(0.25f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(/*sizeMultiplier=*/ 0.25f)
                .error(R.drawable.placeholder)
                .into(holder.pImage);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

         ImageView pImage;
         TextView pTitle;
         TextView pPrice;
         TextView pCategory;
         CardView cardView ;


         ItemClickListenter itemClickListenter ;


        private ViewHolder(@NonNull View itemView , ItemClickListenter  itemClickListenter  ) {
            super(itemView);


            pImage = itemView.findViewById(R.id.product_imageview);
            pTitle = itemView.findViewById(R.id.product_title_tv);
            cardView= itemView.findViewById(R.id.cardView) ;
            pPrice = itemView.findViewById(R.id.product_price_tv);
            pCategory = itemView.findViewById(R.id.product_category_tv);
            this.itemClickListenter = itemClickListenter;
            cardView.setOnClickListener(this);
            this.itemClickListenter = itemClickListenter;
        }

        @Override
        public void onClick(View v) {
            itemClickListenter.onItemClick( v   ,  getAdapterPosition());
        }
    }

    public  interface  ItemClickListenter{
        void onItemClick(View view , int pos ) ;
    }

}
