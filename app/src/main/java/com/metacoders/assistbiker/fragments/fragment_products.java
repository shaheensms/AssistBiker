package com.metacoders.assistbiker.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.adapter.ProductsAdapter;
import com.metacoders.assistbiker.models.ProductsModel;
import com.metacoders.assistbiker.requests.ServiceGenerator;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class fragment_products extends Fragment {

    private Animator spruceAnimator;
    View view;
    private RecyclerView productRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView.Adapter adapter;
    private List<ProductsModel> productsList = new ArrayList<>();
    GridLayoutManager linearLayoutManagefr ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_products, container, false);
        // Inflate the layout for this fragment
        loadProducts();

        productRecyclerView = view.findViewById(R.id.products_recyclerview);
        productRecyclerView.setHasFixedSize(true);

         linearLayoutManagefr = new GridLayoutManager(getContext(), 2) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                initSpruce();
            }
        };

        return view;
    }

    private void initSpruce() {
        spruceAnimator = new Spruce.SpruceBuilder(productRecyclerView)
                .sortWith(new DefaultSort(100))
                .animateWith(DefaultAnimations.shrinkAnimator(productRecyclerView, 800),
                        ObjectAnimator.ofFloat(productRecyclerView, "translationX", -productRecyclerView.getWidth(), 0f).setDuration(800))
                .start();
    }

    private void loadProducts() {
        Call<List<ProductsModel>> call = ServiceGenerator
                .AllApi()
                .getProducts();

        call.enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {
                if (response.isSuccessful()) {
                    productsList = response.body();
                    adapter = new ProductsAdapter(getActivity(), productsList);
                    gridLayoutManager = new GridLayoutManager(getContext(), 2);
                    productRecyclerView.setLayoutManager(linearLayoutManagefr);
                    productRecyclerView.setAdapter(adapter);

                    Log.d(TAG, "onResponse: Products are" + productsList.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ProductsModel>> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (spruceAnimator != null) {
            spruceAnimator.start();
        }

    }
}
