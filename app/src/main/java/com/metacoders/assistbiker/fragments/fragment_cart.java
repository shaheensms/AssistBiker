package com.metacoders.assistbiker.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.metacoders.assistbiker.Activities.CartActivity;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.adapter.CartRecylerViewAdapter;
import com.metacoders.assistbiker.adapter.CartRecylerViewAdapter_For_Fragment;
import com.metacoders.assistbiker.database.CartDatabase;
import com.metacoders.assistbiker.models.CartDbModel;

import java.util.ArrayList;
import java.util.List;

public class fragment_cart  extends Fragment {

    View view ;

    CartDatabase myDatabase ;
    RecyclerView cartRecylerview ;
    CartRecylerViewAdapter_For_Fragment cartAdapter  ;
    CartRecylerViewAdapter_For_Fragment.ViewHolder viewHolder  ;
    public static TextView TotalTextView;
    double toatalAmount = 0.0 ;
    Context context ;
    ArrayList<CartDbModel> cartList = new ArrayList<>();
    Activity activity ;
    CardView cartContainer  ;

    LinearLayout emptyLayout  ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        context = view.getContext() ;
         activity = (Activity) getActivity() ;



        cartRecylerview = view.findViewById(R.id.cartList ) ;
        TotalTextView = view.findViewById(R.id.totalView) ;
        emptyLayout = view.findViewById(R.id.emptyView) ;
        cartContainer = view.findViewById(R.id.cartContainer) ;
        emptyLayout.setVisibility(View.GONE);

        cartRecylerview.setLayoutManager(new LinearLayoutManager(context));



        loadAllCartItem();



        return  view  ;

    }

    @SuppressLint("StaticFieldLeak")
    private void loadAllCartItem() {

        myDatabase = Room.databaseBuilder(context, CartDatabase.class, CartDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
        new AsyncTask<String, Void, List<CartDbModel>>() {
            @Override
            protected List<CartDbModel> doInBackground(String... params) {
                return myDatabase.dao().fetchAllTodos();
            }

            @SuppressLint("SetTextI18n")
            @Override
            protected void onPostExecute(List<CartDbModel> todoList) {



                if(todoList != null && !todoList.isEmpty()) // i know its werid but thats r8 cheaking list is popluted
                {
                    // list is populated
                    cartRecylerview.setVisibility(View.VISIBLE);
                    emptyLayout.setVisibility(View.GONE);
                    cartContainer.setVisibility(View.VISIBLE);
                    cartAdapter = new CartRecylerViewAdapter_For_Fragment( todoList , context,fragment_cart.this);
                    cartRecylerview.setAdapter(cartAdapter);


                    TotalTextView.setText(calculateTotal(todoList) + " BDT");
                    toatalAmount = 0.0  ;

                }
                else
                {

                    // show  empty layout
                    cartRecylerview.setVisibility(View.GONE); // because  recycler view not updating .....
                    emptyLayout.setVisibility(View.VISIBLE) ;
                    cartContainer.setVisibility(View.GONE);




                }



            }
        }.execute();
    }


    private  double  calculateTotal(List<CartDbModel> todoList) {
        toatalAmount = 0.0  ;
        for(CartDbModel item : todoList)
        {
            toatalAmount  = toatalAmount + (item.price * item.quantity) ;
        }


        return  toatalAmount ;

    }

    @Override
    public void onResume() {
        super.onResume();
        loadAllCartItem();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadAllCartItem();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        loadAllCartItem();
    }
}
