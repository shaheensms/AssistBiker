package com.metacoders.assistbiker.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.Utils.Utilities;
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
    Utilities utilities ;
    private ShimmerFrameLayout mShimmerViewContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_orders);


        recyclerView = findViewById(R.id.p_order_list);

        manager = new LinearLayoutManager(getApplicationContext());
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        recyclerView.setLayoutManager(manager);
        utilities = new Utilities();

        int user_id =  utilities.isUserSignedIn(getApplicationContext()) ;

        if(user_id != 0)
        {
            loadPastOrders(user_id);
            Log.d("TAG", "onCreate:  USER ID " + user_id);
        }
        else
        {
            Toasty.error(getApplicationContext() , "Please Sign in Again !!!"  , Toasty.LENGTH_SHORT).show();
        }


    }

    void loadPastOrders(int user_id) {


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

                    recyclerView.getViewTreeObserver().addOnPreDrawListener(

                            new ViewTreeObserver.OnPreDrawListener() {
                                @Override
                                public boolean onPreDraw() {

                                    recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                                    for (int i = 0; i < recyclerView.getChildCount(); i++) {
                                        View v = recyclerView.getChildAt(i);
                                        v.setAlpha(0.0f);
                                        v.animate()
                                                .alpha(1.0f)
                                                .setDuration(300)
                                                .setStartDelay(i * 50)
                                                .start();
                                    }
                                    return true;
                                }

                            }


                    );

                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);

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

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }


}