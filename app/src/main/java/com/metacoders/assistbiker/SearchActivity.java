package com.metacoders.assistbiker;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.assistbiker.adapter.ProductsAdapter;
import com.metacoders.assistbiker.models.ProductsModel;
import com.metacoders.assistbiker.requests.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SearchActivity extends AppCompatActivity {

    ProductsAdapter.ItemClickListenter itemClickListenter;
    private EditText mSearchEd;
    private RecyclerView productRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private ProductsAdapter adapter;
    private List<ProductsModel> productsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchEd = findViewById(R.id.search_ed);
        mSearchEd.requestFocus();
        productRecyclerView = findViewById(R.id.products_recyclerview);
        productRecyclerView.setHasFixedSize(true);


        // Inflate the layout for this fragment
        loadProducts();

        mSearchEd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (!TextUtils.isEmpty(mSearchEd.getText().toString())) {
                        loadSearch(mSearchEd.getText().toString());
                    } else {
                        loadProducts();
                    }

                    return true;
                }
                return false;
            }
        });

    }

    private void loadSearch(String query) {
        Call<List<ProductsModel>> call = ServiceGenerator
                .AllApi()
                .searchProduct(query);

        call.enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {
                if (response.isSuccessful() & response.body() != null) {
                    productsList = response.body();
                    adapter = new ProductsAdapter(SearchActivity.this, productsList, itemClickListenter);
                    gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                    productRecyclerView.setLayoutManager(gridLayoutManager);
                    productRecyclerView.setAdapter(adapter);

                    productRecyclerView.setLayoutManager(gridLayoutManager);

                    productRecyclerView.getViewTreeObserver().addOnPreDrawListener(

                            new ViewTreeObserver.OnPreDrawListener() {
                                @Override
                                public boolean onPreDraw() {

                                    productRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                                    for (int i = 0; i < productRecyclerView.getChildCount(); i++) {
                                        View v = productRecyclerView.getChildAt(i);
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

                } else {
                    Log.d(TAG, "onResponse: ERROR ");
                }

            }

            @Override
            public void onFailure(Call<List<ProductsModel>> call, Throwable t) {

            }
        });
    }

    private void loadProducts() {

        Call<List<ProductsModel>> call = ServiceGenerator
                .AllApi()
                .getProducts();

        call.enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {
                if (response.isSuccessful() & response.body() != null) {
                    productsList = response.body();
                    adapter = new ProductsAdapter(SearchActivity.this, productsList, itemClickListenter);
                    gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                    productRecyclerView.setLayoutManager(gridLayoutManager);
                    productRecyclerView.setAdapter(adapter);

                    productRecyclerView.setLayoutManager(gridLayoutManager);

                    productRecyclerView.getViewTreeObserver().addOnPreDrawListener(

                            new ViewTreeObserver.OnPreDrawListener() {
                                @Override
                                public boolean onPreDraw() {

                                    productRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                                    for (int i = 0; i < productRecyclerView.getChildCount(); i++) {
                                        View v = productRecyclerView.getChildAt(i);
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

//                    Log.d(TAG, "onResponse: Products are" + productsList.toString());
                } else {
                    Log.d(TAG, "onResponse: ERROR ");
                }
            }

            @Override
            public void onFailure(Call<List<ProductsModel>> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.toString());
            }
        });
    }
}