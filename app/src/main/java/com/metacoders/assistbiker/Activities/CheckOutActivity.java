package com.metacoders.assistbiker.Activities;

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

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.adapter.CartRecylerViewAdapter_For_Fragment;
import com.metacoders.assistbiker.adapter.checkOutCartAdapter;
import com.metacoders.assistbiker.api.api;
import com.metacoders.assistbiker.database.CartDatabase;
import com.metacoders.assistbiker.models.CartDbModel;
import com.metacoders.assistbiker.models.Response_register;
import com.metacoders.assistbiker.models.Sent_Response_cart;
import com.metacoders.assistbiker.requests.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {

    CartDatabase myDatabase ;
    RecyclerView orderList ;
    checkOutCartAdapter cartAdapter  ;
    CartRecylerViewAdapter_For_Fragment.ViewHolder viewHolder  ;
    public static TextView TotalTextView;
    double toatalAmount = 0.0 ;
    Context context ;
   List<CartDbModel> cartList = new ArrayList<>();
    Activity activity ;
    CardView cartContainer  ;
    TextView totalView  ;
    LinearLayout emptyLayout  ;
    LinearLayoutManager linearLayoutManager ;
    MaterialButton placeOrder ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

         orderList = findViewById(R.id.listCartCheckOut) ;
         totalView = findViewById(R.id.totalView) ;
         placeOrder = findViewById(R.id.place_order_btn);

         linearLayoutManager = new LinearLayoutManager(this );
         orderList.setLayoutManager(linearLayoutManager);

        loadAllCartItem();


        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendResponseToTheServer();
            }
        });


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


                    totalView.setText(calculateTotal(todoList) + " BDT");
                    toatalAmount = 0.0  ;
                    cartList = todoList ;


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


    public  void sendResponseToTheServer() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String orderList = gson.toJson(cartList); // all the order is in  This  String
        api api = ServiceGenerator.AllApi() ;
        Sent_Response_cart dataModel = new Sent_Response_cart(2 , 1200 ,"12/3/2015" , "pending" ,"null",
                "cartList" , orderList , "test","test","test","test" );

        Call<Response_register> response_Call = api.postCartList(dataModel) ;

        response_Call.enqueue(new Callback<Response_register>() {
            @Override
            public void onResponse(Call<Response_register> call, Response<Response_register> response) {

                if(response.code() ==200) {

                    // check the response

                    Response_register resp = response.body();

                    if(resp.getMsg().equals("successfull"))
                    {
                        String userID =  resp.getData() ;

                      //  dialog.dismiss();
                        Toasty.success(getApplicationContext() , "Cart ID - " + userID  , Toasty.LENGTH_LONG)
                                .show();
                        finish();

                    }
                    else {
                        Toasty.success(getApplicationContext() , "ERROR " , Toasty.LENGTH_LONG)
                                .show();
                    }



                }
                else {
                    // request again
                    Toasty.success(getApplicationContext() , "ERROR " , Toasty.LENGTH_LONG)
                            .show();

                }




            }

            @Override
            public void onFailure(Call<Response_register> call, Throwable t) {

                Toasty.success(getApplicationContext() , "ERROR :  " + t.getMessage() , Toasty.LENGTH_LONG)
                        .show();
            }
        });

    }





}
