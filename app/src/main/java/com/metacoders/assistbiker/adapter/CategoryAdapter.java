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
import com.metacoders.assistbiker.models.CategoryResponseModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context ctx;
    private List<CategoryResponseModel> categoryList;
    private ItemClickListener itemClickListener;

    public CategoryAdapter(Context ctx, List<CategoryResponseModel> categoryList, ItemClickListener itemClickListener) {
        this.ctx = ctx;
        this.categoryList = categoryList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category, parent, false);

        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CategoryResponseModel category = categoryList.get(position);

        holder.cName.setText(category.getP_cat_title());

        Glide.with(ctx)
                .load(Constants.IMAGE_URL + category.getP_cat_image())
                .thumbnail(0.25f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.placeholder)
                .into(holder.cImage);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView cImage;
        TextView cName;
        CardView cardView;
        ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            cImage = itemView.findViewById(R.id.category_image);
            cName = itemView.findViewById(R.id.category_text);
            cardView = itemView.findViewById(R.id.category_card);

            this.itemClickListener = itemClickListener;
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
