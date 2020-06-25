package com.metacoders.assistbiker.fragments;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.metacoders.assistbiker.Activities.ProductDetailActivity;
import com.metacoders.assistbiker.Activities.SearchActivity;
import com.metacoders.assistbiker.CategoryProductActivity;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.adapter.CategoryAdapter;
import com.metacoders.assistbiker.adapter.ProductsAdapter;
import com.metacoders.assistbiker.models.CategoryResponseModel;
import com.metacoders.assistbiker.models.ProductsModel;
import com.metacoders.assistbiker.requests.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProductsFragment extends Fragment {

    private Animator spruceAnimator;
    View view;
    private EditText mSearchEd;
    private RecyclerView productRecyclerView, categoryRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private ProductsAdapter adapter;
    private CategoryAdapter cAdapter;
    ProductsAdapter.ItemClickListenter itemClickListenter;
    private CategoryAdapter.ItemClickListener catItemClickListener;
    private List<ProductsModel> productsList = new ArrayList<>();
    private List<CategoryResponseModel> categoryList = new ArrayList<>();
    GridLayoutManager linearLayoutManagefr;
    private ShimmerFrameLayout mShimmerViewContainer;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_products, container, false);
        context = view.getContext();

        // Inflate the layout for this fragment
        loadCategories();
        loadProducts();

        productRecyclerView = view.findViewById(R.id.products_recyclerview);
        categoryRecyclerView = view.findViewById(R.id.category_recyclerview);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        mSearchEd = view.findViewById(R.id.search_ed);
        productRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setHasFixedSize(true);

        catItemClickListener = new CategoryAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Intent i = new Intent(context, CategoryProductActivity.class);
                CategoryResponseModel singleCategory = new CategoryResponseModel();
                singleCategory = categoryList.get(pos);
                i.putExtra("Category", singleCategory);
                //  Toasty.warning(context , singleProduct.getProduct_title() , Toasty.LENGTH_SHORT).show();
                startActivity(i);
            }
        };

        itemClickListenter = new ProductsAdapter.ItemClickListenter() {
            @Override
            public void onItemClick(View view, final int pos) {

                Intent i = new Intent(context, ProductDetailActivity.class);
                ProductsModel singleProduct = new ProductsModel();
                singleProduct = productsList.get(pos);
                i.putExtra("PRODUCT", singleProduct);
                //  Toasty.warning(context , singleProduct.getProduct_title() , Toasty.LENGTH_SHORT).show();
                startActivity(i);
            }
        };

        mSearchEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadCategories() {

        Call<List<CategoryResponseModel>> call = ServiceGenerator
                .AllApi()
                .getCategory();

        call.enqueue(new Callback<List<CategoryResponseModel>>() {
            @Override
            public void onResponse(Call<List<CategoryResponseModel>> call, Response<List<CategoryResponseModel>> response) {
                categoryList = response.body();
                cAdapter = new CategoryAdapter(getActivity(), categoryList, catItemClickListener);
                gridLayoutManager = new GridLayoutManager(getContext(), 5);
                categoryRecyclerView.setLayoutManager(gridLayoutManager);
                categoryRecyclerView.setAdapter(cAdapter);
            }

            @Override
            public void onFailure(Call<List<CategoryResponseModel>> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.toString());
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
                    adapter = new ProductsAdapter(getActivity(), productsList, itemClickListenter);
                    gridLayoutManager = new GridLayoutManager(getContext(), 2);
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

                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
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
