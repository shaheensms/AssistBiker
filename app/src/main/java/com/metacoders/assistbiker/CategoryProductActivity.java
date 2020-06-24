package com.metacoders.assistbiker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.assistbiker.Activities.ProductDetailActivity;
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

public class CategoryProductActivity extends AppCompatActivity {

    private TextView mCategoryTV;
    private RecyclerView catProductRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private ProductsAdapter adapter;
    private ProductsAdapter.ItemClickListenter itemClickListenter;
    private List<ProductsModel> productsList = new ArrayList<>();
    private String catID, catName;
    private CategoryResponseModel singleCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);

        mCategoryTV = (TextView) findViewById(R.id.category_text);
        catProductRecyclerView = (RecyclerView) findViewById(R.id.cat_product_recyclerview);

        Intent i = getIntent();
        singleCategory = (CategoryResponseModel) i.getSerializableExtra("Category");
        mCategoryTV.setText("Category > " + singleCategory.getP_cat_title());

        if (singleCategory.getP_cat_id().equals("0")) {
            loadProducts();
        } else {
            loadCatProducts();
        }

        itemClickListenter = new ProductsAdapter.ItemClickListenter() {
            @Override
            public void onItemClick(View view, final int pos) {

                Intent i = new Intent(CategoryProductActivity.this, ProductDetailActivity.class);
                ProductsModel singleProduct = new ProductsModel();
                singleProduct = productsList.get(pos);
                i.putExtra("PRODUCT", singleProduct);
                //  Toasty.warning(context , singleProduct.getProduct_title() , Toasty.LENGTH_SHORT).show();
                startActivity(i);
            }
        };

    }

    private void loadCatProducts() {
        Call<List<ProductsModel>> call = ServiceGenerator
                .AllApi()
                .getCategoryProducts(singleCategory.getP_cat_id());

        call.enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {
                if (response.isSuccessful() & response.body() != null) {
                    productsList = response.body();
                    adapter = new ProductsAdapter(CategoryProductActivity.this, productsList, itemClickListenter);
                    gridLayoutManager = new GridLayoutManager(CategoryProductActivity.this, 2);
                    catProductRecyclerView.setLayoutManager(gridLayoutManager);
                    catProductRecyclerView.setAdapter(adapter);

                    catProductRecyclerView.setLayoutManager(gridLayoutManager);

                    catProductRecyclerView.getViewTreeObserver().addOnPreDrawListener(

                            new ViewTreeObserver.OnPreDrawListener() {
                                @Override
                                public boolean onPreDraw() {

                                    catProductRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                                    for (int i = 0; i < catProductRecyclerView.getChildCount(); i++) {
                                        View v = catProductRecyclerView.getChildAt(i);
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

    private void loadProducts() {

        Call<List<ProductsModel>> call = ServiceGenerator
                .AllApi()
                .getProducts();

        call.enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {
                if (response.isSuccessful() & response.body() != null) {
                    productsList = response.body();
                    adapter = new ProductsAdapter(CategoryProductActivity.this, productsList, itemClickListenter);
                    gridLayoutManager = new GridLayoutManager(CategoryProductActivity.this, 2);
                    catProductRecyclerView.setLayoutManager(gridLayoutManager);
                    catProductRecyclerView.setAdapter(adapter);

                    catProductRecyclerView.setLayoutManager(gridLayoutManager);

                    catProductRecyclerView.getViewTreeObserver().addOnPreDrawListener(

                            new ViewTreeObserver.OnPreDrawListener() {
                                @Override
                                public boolean onPreDraw() {

                                    catProductRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                                    for (int i = 0; i < catProductRecyclerView.getChildCount(); i++) {
                                        View v = catProductRecyclerView.getChildAt(i);
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