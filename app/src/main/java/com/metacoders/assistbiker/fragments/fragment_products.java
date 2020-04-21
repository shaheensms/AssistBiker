package com.metacoders.assistbiker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.adapter.ProductsAdapter;
import com.metacoders.assistbiker.models.ProductsModel;
import com.metacoders.assistbiker.requests.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class fragment_products extends Fragment {

    View view;
    private RecyclerView productRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView.Adapter adapter;
    private List<ProductsModel> productsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_products, container, false);
        // Inflate the layout for this fragment
        loadProducts();

        productRecyclerView = view.findViewById(R.id.products_recyclerview);
        productRecyclerView.setHasFixedSize(true);

        return view;
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
                    productRecyclerView.setLayoutManager(gridLayoutManager);
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


}
