package com.metacoders.assistbiker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.adapter.OldCartRecylerViewAdapter;
import com.metacoders.assistbiker.models.CartModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {


    RecyclerView old_list;
    LinearLayoutManager manager;
    OldCartRecylerViewAdapter oldCartRecylerViewAdapter;
    CartModel student;

    List<CartModel> students = new ArrayList<>();
    private TextView mOrderId, mOrderDate, mOrderStatus, mOrderCount, mOrderPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        mOrderId = (TextView) findViewById(R.id.order_id);
        mOrderDate = (TextView) findViewById(R.id.date);
        mOrderStatus = (TextView) findViewById(R.id.status);
        mOrderCount = (TextView) findViewById(R.id.count);
        mOrderPrice = (TextView) findViewById(R.id.price);


        old_list = findViewById(R.id.list_order);
        manager = new LinearLayoutManager(getApplicationContext());

        old_list.setLayoutManager(manager);


        Intent o = getIntent();
        String list = o.getStringExtra("list");
        int orderId = o.getIntExtra("order_id", 0);
        String orderDate = o.getStringExtra("order_date");
        String orderStatus = o.getStringExtra("order_status");
        float orderPrice = o.getFloatExtra("order_price", 0);


        //boolean isSingleProduct = i.getBooleanExtra("isSingle", false);
        //  int  id = i.getIntExtra("productID" , 0) ;

        try {
            //   Gson g = new Gson();
            //   CartModel p = g.fromJson(item.getOrder_list(), CartModel.class) ;

            //     List<CartModel> list = Arrays.asList(new GsonBuilder().create().fromJson(item.getOrder_list(), CartModel.class));
            //     Read more: https://www.java67.com/2016/10/3-ways-to-convert-string-to-json-object-in-java.html#ixzz6PWi7uYqm

            JSONArray jsonArray = new JSONArray(list);
            for (int i = 0; i < jsonArray.length(); i++) {
                student = new Gson().fromJson(jsonArray.get(i).toString(), CartModel.class);
                students.add(student);
            }

        } catch (Exception e) {
            Log.d("TAG", "onBindViewHolder: " + e.getMessage());
        }


        Log.d("TAG", list + " \n" + students.size());

        // list is not empty
        if (students != null && !students.isEmpty()) {

            mOrderId.setText("Order Id : app_" + orderId);
            mOrderDate.setText("Ordered On : " + orderDate);
            mOrderStatus.setText(orderStatus);
            mOrderCount.setText("" + students.size());
            mOrderPrice.setText(orderPrice + "");

            oldCartRecylerViewAdapter = new OldCartRecylerViewAdapter(getApplicationContext(), students);
            old_list.setAdapter(oldCartRecylerViewAdapter);

        }

    }


}

