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
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.models.ProductsModel;

import java.util.List;

public class NewsTrendAdapter extends RecyclerView.Adapter<NewsTrendAdapter.ViewHolder> {
    private Context ctx;
    private List<ProductsModel> newsfeedList;

    public NewsTrendAdapter(Context ctx, List<ProductsModel> newsfeedList) {
        this.ctx = ctx;
        this.newsfeedList = newsfeedList;
    }

    @NonNull
    @Override
    public NewsTrendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news_trending, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsTrendAdapter.ViewHolder holder, int position) {

        ProductsModel newsFeed = newsfeedList.get(position);

        holder.title.setText(newsFeed.getProduct_title());
        holder.price.setText("$" + newsFeed.getProduct_price().toString());
//        holder.description.setText(newsFeed.getDescription());

        Glide.with(ctx)
                .load(newsFeed.getProduct_img1())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return newsfeedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public ImageView image;
//        public TextView description;
        public TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            title = itemView.findViewById(R.id.news_title_tv);
            image = itemView.findViewById(R.id.news_imageview);
//            description = itemView.findViewById(R.id.news_description_tv);
            price = itemView.findViewById(R.id.news_price_tv);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
