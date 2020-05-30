package com.metacoders.assistbiker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.metacoders.assistbiker.Activities.NewsDetailsActivity;
import com.metacoders.assistbiker.Activities.ProductDetailActivity;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.models.NewsFeedModel;

import java.util.List;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {
    private Context ctx;
    private List<NewsFeedModel> newsfeedList;
    private ItemClickListenter itemClickListenter;

    public NewsFeedAdapter(Context ctx, List<NewsFeedModel> newsfeedList, ItemClickListenter itemClickListenter) {
        this.ctx = ctx;
        this.newsfeedList = newsfeedList;
        this.itemClickListenter = itemClickListenter;
    }

    @NonNull
    @Override
    public NewsFeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news_feed, parent, false);

        return new ViewHolder(view, itemClickListenter);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedAdapter.ViewHolder holder, int position) {

        NewsFeedModel newsFeed = newsfeedList.get(position);

        holder.title.setText(newsFeed.getTitle());
        holder.price.setText("$" + newsFeed.getProduct_price().toString());
        holder.description.setText(newsFeed.getDescription());

        Glide.with(ctx)
                .load(newsFeed.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(/*sizeMultiplier=*/ 0.25f)
                .placeholder(R.drawable.placeholder)
                .into(holder.image);

        if(newsFeed.getIs_product().contains("true"))
        {
            holder.price.setVisibility(View.VISIBLE);
        }
        else  holder.price.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return newsfeedList.size();
    }

    public interface ItemClickListenter {
        void onItemClick(View view, int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public ImageView image;
        public TextView description;
        public TextView price;

        ItemClickListenter itemClickListenter;

        public ViewHolder(@NonNull View itemView, ItemClickListenter itemClickListenter) {
            super(itemView);

            itemView.setOnClickListener(this);

            title = itemView.findViewById(R.id.news_title_tv);
            image = itemView.findViewById(R.id.news_imageview);
            description = itemView.findViewById(R.id.news_description_tv);
            price = itemView.findViewById(R.id.news_price_tv);
            this.itemClickListenter = itemClickListenter;
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
//            itemClickListenter.onItemClick( v, position);

            if (newsfeedList.get(position).getIs_product().equals("false")) {
                Intent intent = new Intent(ctx, NewsDetailsActivity.class);
                intent.putExtra("image", newsfeedList.get(position).getImage());
                intent.putExtra("title", newsfeedList.get(position).getTitle());
                intent.putExtra("desc" , newsfeedList.get(position).getDescription()) ;

                ctx.startActivity(intent);
            }
            else {
                Intent intent = new Intent(ctx, ProductDetailActivity.class);
                intent.putExtra("isSingle" , true) ;
                intent.putExtra("productID" ,newsfeedList.get(position).getProduct_id()) ;
                ctx.startActivity(intent);
            }
        }
    }
}
