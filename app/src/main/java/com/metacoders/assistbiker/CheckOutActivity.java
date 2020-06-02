package com.metacoders.assistbiker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.metacoders.assistbiker.adapter.CartRecylerViewAdapter_For_Fragment;
import com.metacoders.assistbiker.adapter.checkOutCartAdapter;
import com.metacoders.assistbiker.database.CartDatabase;
import com.metacoders.assistbiker.fragments.fragment_cart;
import com.metacoders.assistbiker.models.CartDbModel;

import java.util.ArrayList;
import java.util.List;

public class CheckOutActivity extends AppCompatActivity {

    CartDatabase myDatabase ;
    RecyclerView orderList ;
   checkOutCartAdapter cartAdapter  ;
    CartRecylerViewAdapter_For_Fragment.ViewHolder viewHolder  ;
    public static TextView TotalTextView;
    double toatalAmount = 0.0 ;
    Context context ;
    ArrayList<CartDbModel> cartList = new ArrayList<>();
    Activity activity ;
    CardView cartContainer  ;
    LinearLayout emptyLayout  ;
    LinearLayoutManager linearLayoutManager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

         orderList = findViewById(R.id.listCartCheckOut) ;
         linearLayoutManager = new LinearLayoutManager(this );
         orderList.setLayoutManager(linearLayoutManager);

        loadAllCartItem();


    }
    @SuppressLint("StaticFieldLeak")
    private void loadAllCartItem() {

        myDatabase = Room.databaseBuilder(CheckOutActivity.this, CartDatabase.class, CartDatabase.DB_NAME)
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
                    orderList.setVisibility(View.VISIBLE);
                  //  emptyLayout.setVisibility(View.GONE);
                 //   cartContainer.setVisibility(View.VISIBLE);
                    cartAdapter = new checkOutCartAdapter( CheckOutActivity.this ,todoList);
                    orderList.setAdapter(cartAdapter);


//                    TotalTextView.setText(calculateTotal(todoList) + " BDT");
                    toatalAmount = 0.0  ;

                }
                else
                {

                    // show  empty layout
                    orderList.setVisibility(View.GONE); // because  recycler view not updating .....
                 //   emptyLayout.setVisibility(View.VISIBLE) ;
                  //  cartContainer.setVisibility(View.GONE);




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








}
