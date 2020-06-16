package com.metacoders.assistbiker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.metacoders.assistbiker.Activities.CartActivity;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.Utils.Constants;
import com.metacoders.assistbiker.Utils.Utilities;
import com.metacoders.assistbiker.database.CartDatabase;
import com.metacoders.assistbiker.models.CartDbModel;
import com.metacoders.assistbiker.models.CartModel;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class OldCartRecylerViewAdapter extends RecyclerView.Adapter <OldCartRecylerViewAdapter.ViewHolder> {

    private List<CartModel> cartList = new ArrayList<>() ;
    private  Context cartActivity ;

    Utilities utilities = new Utilities() ;

    private OldCartRecylerViewAdapter.ClickListener clickListener;

    public OldCartRecylerViewAdapter(Context mcontext , List<CartModel> cartList)  {
        this.cartActivity = mcontext;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_checkout, parent, false);
        OldCartRecylerViewAdapter.ViewHolder viewHolder = new OldCartRecylerViewAdapter.ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

          final CartModel cartItem = cartList.get(position);
        holder.txtName.setText(cartItem.getTitle());
        holder.textPrice.setText("৳ "+ cartItem.getPrice());
        holder.numberButton.setText( "X" + cartItem.getQuantity());
        Glide.with(cartActivity)
                .load(Constants.IMAGE_URL +cartList.get(position).getProduct_image())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.productImage) ;





    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public TextView textPrice;
        public CardView cardView;
        public TextView numberButton ;
        public  ImageButton delteBtn  ;
        public  ImageView productImage;



        public ViewHolder(View view) {
            super(view);

            txtName = view.findViewById(R.id.cart_name);
            textPrice = view.findViewById(R.id.cart_price);
            numberButton = view.findViewById(R.id.cart_Quantity) ;
            cardView = view.findViewById(R.id.container);
            productImage = view.findViewById(R.id.image);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  //  clickListener.launchIntent(todoList.get(getAdapterPosition()).todo_id);
                }
            });
        }
    }

    public interface ClickListener {
        void launchIntent(int id);
    }


}
