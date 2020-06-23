package com.metacoders.assistbiker.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.metacoders.assistbiker.Activities.CheckOutActivity;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.Utils.Constants;
import com.metacoders.assistbiker.Utils.Utilities;
import com.metacoders.assistbiker.database.CartDatabase;
import com.metacoders.assistbiker.models.CartDbModel;

import java.util.ArrayList;
import java.util.List;

public  class checkOutCartAdapter extends RecyclerView.Adapter <checkOutCartAdapter.ViewHolder> {

    private List<CartDbModel> cartList = new ArrayList<>() ;
    private CheckOutActivity cartActivity ;

    Utilities utilities = new Utilities() ;

    private CartRecylerViewAdapter.ClickListener clickListener;

    public checkOutCartAdapter(CheckOutActivity mcontext , List<CartDbModel> cartList)  {
        this.cartActivity = mcontext;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public checkOutCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_checkout, parent, false);
        checkOutCartAdapter.ViewHolder viewHolder = new checkOutCartAdapter.ViewHolder(view);
        return viewHolder;
    }
    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(@NonNull final checkOutCartAdapter.ViewHolder holder, final int position) {

        final CartDatabase database = Room.databaseBuilder(cartActivity, CartDatabase.class, CartDatabase.DB_NAME).build();
        final CartDbModel cartItem = cartList.get(position);
        holder.txtName.setText(cartItem.title);
        holder.textPrice.setText("৳ "+ cartItem.price);
        holder.quantity.setText( "x "+ cartItem.quantity);
        Glide.with(cartActivity)
                .load(Constants.IMAGE_URL +cartList.get(position).product_image)
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
        public TextView textPrice , quantity;
        public ImageView productImage;



        public ViewHolder(View view) {
            super(view);


            txtName = view.findViewById(R.id.cart_name);
            textPrice = view.findViewById(R.id.cart_price);
            productImage = view.findViewById(R.id.image);
            quantity = view.findViewById(R.id.cart_Quantity) ;

        }
    }

    public interface ClickListener {
        void launchIntent(int id);
    }


}
