package com.metacoders.assistbiker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

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
    ArrayList<CartDbModel> cartList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        myDatabase = Room.databaseBuilder(getApplicationContext(), CartDatabase.class, CartDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();

        cartRecylerview = findViewById(R.id.cartList ) ;
        cartRecylerview.setLayoutManager(new LinearLayoutManager(this));



        loadAllCartItem();



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


                cartAdapter = new CartRecylerViewAdapter(getApplicationContext() , todoList);
                cartRecylerview.setAdapter(cartAdapter);
               // cartAdapter.updateTodoList(todoList);

            }
        }.execute();
    }

}
