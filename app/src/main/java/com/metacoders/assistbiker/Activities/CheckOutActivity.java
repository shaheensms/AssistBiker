package com.metacoders.assistbiker.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.metacoders.assistbiker.Utils.Utilities;
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
    double toatalAmount = 0.0  ;
    float dueAmount = 0;
    Context context ;
   List<CartDbModel> cartList = new ArrayList<>();
    Activity activity ;
    CardView cartContainer  ;
    TextView totalView  , delivrly_charge_checkout_Tv , deliver_adress , payment_Type , contact_Number , totalProductPrice
            , totalInvoice;
    LinearLayout emptyLayout  ;
    LinearLayoutManager linearLayoutManager ;
    MaterialButton placeOrder ;
    Utilities utilities ;
    int user_Id = 0 ;


    String  dZone  , dAdress , pType;
    float zoneCharge ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        utilities = new Utilities() ;
        user_Id = utilities.getuserID(getApplicationContext()) ;

//        o.putExtra("deliveryZone" , zone) ;
//        o.putExtra("zoneCharge", price) ;
//        o.putExtra("deliverAddress",delivery_adress ) ;
//        o.putExtra("paymentType",paymentMethod ) ;
        Intent o = getIntent();
        dZone = o.getStringExtra("deliveryZone") ;
        dAdress = o.getStringExtra("deliverAddress") ;
        pType =o.getStringExtra("paymentType");
        zoneCharge = o.getFloatExtra("zoneCharge" , 0 );


         orderList = findViewById(R.id.listCartCheckOut) ;
         totalView = findViewById(R.id.totalView) ;
         payment_Type = findViewById(R.id.paymentTypeINvoice) ;
         placeOrder = findViewById(R.id.place_order_btn);
         totalProductPrice =findViewById(R.id.total_food_price_checkout);
         deliver_adress = findViewById(R.id.deliveryLocationInvoice);
         contact_Number = findViewById(R.id.mobileNumberViewINvoice);
         totalInvoice = findViewById(R.id.totalinvoice);
         contact_Number.setText(utilities.getSavedContacts(getApplicationContext()));
         deliver_adress.setText(dAdress);
         payment_Type.setText(pType);
         delivrly_charge_checkout_Tv = findViewById(R.id.delivrly_charge_checkout) ;

         linearLayoutManager = new LinearLayoutManager(this );
         orderList.setLayoutManager(linearLayoutManager);

         delivrly_charge_checkout_Tv.setText(zoneCharge+"");

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

                   double TOTAL =  calculateTotal(todoList) ;
                    totalView.setText( TOTAL+ zoneCharge + " BDT");
                    totalProductPrice.setText(TOTAL +"");
                    totalInvoice.setText(TOTAL+ zoneCharge+"");
                    dueAmount = (float) (TOTAL + zoneCharge);

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
        Sent_Response_cart dataModel = new Sent_Response_cart(user_Id , dueAmount ,utilities.getCurrentDate(), "pending" ,"null",
                "Products" , orderList , pType,dZone,dAdress,"test" );

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
