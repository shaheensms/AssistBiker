package com.metacoders.assistbiker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.adapter.CartRecylerViewAdapter;
import com.metacoders.assistbiker.database.CartDatabase;
import com.metacoders.assistbiker.models.CartDbModel;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    CartDatabase myDatabase ;
    RecyclerView cartRecylerview ;
    CartRecylerViewAdapter cartAdapter  ;
    CartRecylerViewAdapter.ViewHolder viewHolder  ;
   public TextView TotalTextView;
    double toatalAmount = 0.0 ;
    CardView cartContainer  ;
    MaterialButton checkOutBtn ;

    LinearLayout emptyLayout  ;
    ArrayList<CartDbModel> cartList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        myDatabase = Room.databaseBuilder(getApplicationContext(), CartDatabase.class, CartDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();


        cartRecylerview = findViewById(R.id.cartList ) ;
        TotalTextView = findViewById(R.id.totalView) ;
        emptyLayout = findViewById(R.id.emptyView) ;
        cartContainer = findViewById(R.id.cartContainer) ;
        checkOutBtn = findViewById(R.id.checkOutBtn);
        emptyLayout.setVisibility(View.GONE);

        cartRecylerview.setLayoutManager(new LinearLayoutManager(this));



        loadAllCartItem();

        checkOutBtn.setOnClickListener(v -> {

            Intent i = new Intent(getApplicationContext() , CheckOutActivity.class);
            startActivity(i);

        });


    }

    @SuppressLint("StaticFieldLeak")
    private void loadAllCartItem() {
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


                    cartAdapter = new CartRecylerViewAdapter(CartActivity.this, todoList);
                    cartRecylerview.setAdapter(cartAdapter);


                    TotalTextView.setText(calculateTotal(todoList) + " BDT");

                }
                else
                {
                    // show  empty layout

                    emptyLayout.setVisibility(View.VISIBLE) ;
                    cartContainer.setVisibility(View.GONE);

                }
               // cartAdapter.updateTodoList(todoList);

            }
        }.execute();
    }


    private  double  calculateTotal(List<CartDbModel> todoList)
    {
        for(CartDbModel item : todoList)
        {
            toatalAmount  = toatalAmount + (item.price * item.quantity) ;
        }


        return  toatalAmount ;

    }

    public  void setTotalTextView(String price)
    {   TotalTextView = findViewById(R.id.totalView) ;
         TotalTextView.setText(price);

    }


}
