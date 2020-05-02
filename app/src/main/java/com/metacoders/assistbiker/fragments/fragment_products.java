package com.metacoders.assistbiker.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.assistbiker.Activities.ProductDetailActivity;
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
    private ProductsAdapter adapter;
    ProductsAdapter.ItemClickListenter itemClickListenter ;
    private List<ProductsModel> productsList = new ArrayList<>();
    GridLayoutManager linearLayoutManagefr ;
    Context context ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_products, container, false);
        context = view.getContext() ;

        // Inflate the layout for this fragment
        loadProducts();

        productRecyclerView = view.findViewById(R.id.products_recyclerview);
        productRecyclerView.setHasFixedSize(true);




         itemClickListenter = new ProductsAdapter.ItemClickListenter() {
             @Override
             public void onItemClick(View view,final int pos) {

                 Intent i = new Intent(context , ProductDetailActivity.class);
                 ProductsModel singleProduct = new ProductsModel() ;
                 singleProduct = productsList.get(pos) ;
                i.putExtra("PRODUCT",  singleProduct) ;
               //  Toasty.warning(context , singleProduct.getProduct_title() , Toasty.LENGTH_SHORT).show();
                 startActivity(i);



             }
         } ;


        return view;
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
                    adapter = new ProductsAdapter(getActivity(), productsList , itemClickListenter);
                    gridLayoutManager = new GridLayoutManager(getContext(), 2);
                    productRecyclerView.setLayoutManager(gridLayoutManager);
                    productRecyclerView.setAdapter(adapter);
                    productRecyclerView.setLayoutManager(gridLayoutManager);



                    productRecyclerView.getViewTreeObserver().addOnPreDrawListener(

                            new ViewTreeObserver.OnPreDrawListener() {
                                @Override
                                public boolean onPreDraw() {

                                    productRecyclerView.getViewTreeObserver().removeOnPreDrawListener( this);
                                    for( int i = 0 ; i<productRecyclerView.getChildCount() ; i++)
                                    {
                                        View v = productRecyclerView.getChildAt(i) ;
                                        v.setAlpha(0.0f);
                                        v.animate()
                                                .alpha(1.0f)
                                                .setDuration(300)
                                                .setStartDelay(i*50)
                                                .start();


                                    }


                                    return true;
                                }
                            }

                    );


//                    Log.d(TAG, "onResponse: Products are" + productsList.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ProductsModel>> call, Throwable t) {
//                Log.d(TAG, "onResponse: " + t.toString());


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
