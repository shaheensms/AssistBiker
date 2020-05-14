package com.metacoders.assistbiker.adapter;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class CartRecylerViewAdapter extends RecyclerView.Adapter <CartRecylerViewAdapter.ViewHolder> {

    private List<CartDbModel> cartList = new ArrayList<>() ;
    private  CartActivity cartActivity ;

    Utilities utilities = new Utilities() ;

    private CartRecylerViewAdapter.ClickListener clickListener;

    public CartRecylerViewAdapter(CartActivity mcontext , List<CartDbModel> cartList)  {
        this.cartActivity = mcontext;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart, parent, false);
        CartRecylerViewAdapter.ViewHolder viewHolder = new CartRecylerViewAdapter.ViewHolder(view);
        return viewHolder;
    }
    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final CartDatabase database = Room.databaseBuilder(cartActivity, CartDatabase.class, CartDatabase.DB_NAME).build();
        final CartDbModel cartItem = cartList.get(position);
        holder.txtName.setText(cartItem.title);
        holder.textPrice.setText("৳ "+ cartItem.price);
        holder.numberButton.setNumber(cartItem.quantity+ "");
        Glide.with(cartActivity)
                .load(Constants.IMAGE_URL +cartList.get(position).product_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.productImage) ;


        holder.delteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // delete  the item
                CartDbModel singleITem = cartList.get(position) ;

                new AsyncTask<CartDbModel, Void, Integer>() {
                    @Override
                    protected Integer doInBackground(CartDbModel... params) {
                        return database.dao().deleteCartItem(params[0]);
                    }

                    @Override
                    protected void onPostExecute(Integer number) {
                        super.onPostExecute(number);

                        cartActivity.TotalTextView.setText(utilities.calculateTotal(cartList)+ " BDT");
                        Toasty.success(cartActivity , "Item Removed !!  ", Toasty.LENGTH_SHORT).show();

                    }
                }.execute(singleITem);
                cartList.remove(position) ;
                notifyItemChanged(position);
                notifyDataSetChanged();


            }
        });

        holder.numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                // update the value .....
              CartDbModel UpdateItem =  new CartDbModel() ;

                UpdateItem = cartList.get(position) ;

                UpdateItem.quantity = newValue ;

               // UpdateItem.title = cartList.get(position).title ;
             //   holder.textPrice.setText(cartList.get(position).price * newValue + ""); // setting the item price in the row
                //UpdateItem.product_id = cartList.get(position).product_id ;
                //UpdateItem.cart_id = cartList.get(position).cart_id ;



                new AsyncTask<CartDbModel, Void, Integer>() {
                    @Override
                    protected Integer doInBackground(CartDbModel... params) {
                        return database.dao().updateCart(params[0]);
                    }

                    @Override
                    protected void onPostExecute(Integer number) {
                        super.onPostExecute(number);
                        // TODO here i sending it

                        cartActivity.TotalTextView.setText(utilities.calculateTotal(cartList)+ " BDT");

                    }
                }.execute(UpdateItem);



            }
        });






    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public TextView textPrice;
        public CardView cardView;
        public ElegantNumberButton numberButton ;
        public  ImageButton delteBtn  ;
        public  ImageView productImage;



        public ViewHolder(View view) {
            super(view);

            delteBtn = view.findViewById(R.id.cart_delete) ;
            txtName = view.findViewById(R.id.cart_name);
            textPrice = view.findViewById(R.id.cart_price);
            numberButton = view.findViewById(R.id.number_button) ;
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
