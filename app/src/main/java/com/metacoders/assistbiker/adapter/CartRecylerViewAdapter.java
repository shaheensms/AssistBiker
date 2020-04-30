package com.metacoders.assistbiker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.models.CartDbModel;

import java.util.ArrayList;
import java.util.List;

public class CartRecylerViewAdapter extends RecyclerView.Adapter <CartRecylerViewAdapter.ViewHolder> {

    private List<CartDbModel> cartList = new ArrayList<>() ;
    Context context ;

    private CartRecylerViewAdapter.ClickListener clickListener;

    public CartRecylerViewAdapter(Context mcontext , List<CartDbModel> cartList)  {
        this.context = mcontext;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_row, parent, false);
        CartRecylerViewAdapter.ViewHolder viewHolder = new CartRecylerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartDbModel cartItem = cartList.get(position);
        holder.txtName.setText(cartItem.title);
        holder.textPrice.setText(cartItem.price + "");
        holder.numberButton.setNumber(cartItem.quantity+ "");
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public TextView textPrice;
        public LinearLayout cardView;
        public ElegantNumberButton numberButton ;


        public ViewHolder(View view) {
            super(view);


            txtName = view.findViewById(R.id.itemName_cart);
            textPrice = view.findViewById(R.id.itemPrice_cart);
            numberButton = view.findViewById(R.id.Quantity_btn) ;
            cardView = view.findViewById(R.id.container);
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
