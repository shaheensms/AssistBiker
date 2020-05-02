package com.metacoders.assistbiker.adapter;

import android.content.Context;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.models.NewsFeedModel;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {
    private Context ctx;
    private List<NewsFeedModel> newsfeedList;

    public NewsFeedAdapter(Context ctx, List<NewsFeedModel> newsfeedList) {
        this.ctx = ctx;
        this.newsfeedList = newsfeedList;
    }

    @NonNull
    @Override
    public NewsFeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news_feed, parent, false);

        return new ViewHolder(view);
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

    }

    @Override
    public int getItemCount() {
        return newsfeedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public ImageView image;
        public TextView description;
        public TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            title = itemView.findViewById(R.id.news_title_tv);
            image = itemView.findViewById(R.id.news_imageview);
            description = itemView.findViewById(R.id.news_description_tv);
            price = itemView.findViewById(R.id.news_price_tv);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
