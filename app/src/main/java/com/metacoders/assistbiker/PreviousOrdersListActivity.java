package com.metacoders.assistbiker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.assistbiker.adapter.PastOrderListAdapter;
import com.metacoders.assistbiker.models.Sent_Response_cart;
import com.metacoders.assistbiker.requests.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousOrdersListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager manager;
    PastOrderListAdapter adapter;
    private List<Sent_Response_cart> past_order_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_orders);


        recyclerView = findViewById(R.id.p_order_list);

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);


        loadPastOrders();
    }

    void loadPastOrders() {
        Call<List<Sent_Response_cart>> call = ServiceGenerator
                .AllApi()
                .getPastOrders(1, 2); // here type = 1 means all orders of the customer id  = ID


        call.enqueue(new Callback<List<Sent_Response_cart>>() {
            @Override
            public void onResponse(Call<List<Sent_Response_cart>> call, Response<List<Sent_Response_cart>> response) {

                if (response.isSuccessful() && response.code() == 200) {
                    past_order_list = response.body();

                    adapter = new PastOrderListAdapter(past_order_list, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } else {
                    Toasty.error(getApplicationContext(), response.errorBody().toString() + " " + response.code(), Toasty.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Sent_Response_cart>> call, Throwable t) {

                Toasty.error(getApplicationContext(), t.getMessage() + " ", Toasty.LENGTH_SHORT).show();

            }
        });


    }
}