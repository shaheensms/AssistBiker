package com.metacoders.assistbiker.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.metacoders.assistbiker.Activities.SearchActivity;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.Utils.Constants;
import com.metacoders.assistbiker.adapter.NewsFeedAdapter;
import com.metacoders.assistbiker.adapter.NewsTrendAdapter;
import com.metacoders.assistbiker.api.api;
import com.metacoders.assistbiker.models.NewsFeedModel;
import com.metacoders.assistbiker.models.ProductsModel;
import com.metacoders.assistbiker.requests.ServiceGenerator;
import com.metacoders.assistbiker.test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class fragment_newsfeed extends Fragment {
    View view;
    private RecyclerView newsRecyclerView, trendRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    private List<NewsFeedModel> newsfeedList = new ArrayList<>();
    private List<ProductsModel> productsList = new ArrayList<>();
    NewsFeedAdapter.ItemClickListenter itemClickListenter;
    private ShimmerFrameLayout mShimmerViewContainer;
    TextView sellAllView ;

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newfeed, container, false);
        // Inflate the layout for this fragment
        // loadNews();

        newsRecyclerView = view.findViewById(R.id.news_feed_recyclerview);
        trendRecyclerView = view.findViewById(R.id.trending_news_feed_recyclerview);
        sellAllView = view.findViewById(R.id.see_all) ;
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        newsRecyclerView.setHasFixedSize(true);
        trendRecyclerView.setHasFixedSize(true);

        trendRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                int action = e.getAction();
                // Toast.makeText(getActivity(),"HERE",Toast.LENGTH_SHORT).show();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);

                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        //test()  ;

        sellAllView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent p = new Intent(getContext() , SearchActivity.class);
                startActivity(p);

            }
        });


        loadTrend();
        loadNews();

//        itemClickListenter = new NewsFeedAdapter.ItemClickListenter() {
//            @Override
//            public void onItemClick(View view, int pos) {
//                NewsFeedModel newsFeed = new NewsFeedModel();
//                newsFeed = newsfeedList.get(pos);
//
////                if (!newsFeed.getIs_product()) {
//                    Intent intent = new Intent(context, NewsDetailsActivity.class);
////                    intent.putExtra("News", newsFeed);
//                    startActivity(intent);
////                }
//
//            }
//        };

        return view;
    }

    private void test() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api api = retrofit.create(api.class);

        Observable<List<NewsFeedModel>> userObservable = api
                .getNewsFeeds()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<List<ProductsModel>> eventsObservable = api
                .getLatestProducts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<test> combined = Observable.zip(userObservable, eventsObservable, (newsFeedModels, productsModels) -> new test(newsFeedModels, productsModels));

        combined.subscribe(new Subject<test>() {
            @Override
            public boolean hasObservers() {
                return false;
            }

            @Override
            public boolean hasThrowable() {
                return false;
            }

            @Override
            public boolean hasComplete() {
                return false;
            }

            @Override
            public Throwable getThrowable() {
                return null;
            }

            @Override
            protected void subscribeActual(Observer<? super test> observer) {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(test test) {
                List<NewsFeedModel> newss = new ArrayList<>();
                List<ProductsModel> productsModels = new ArrayList<>();

                newss = test.news;
                productsModels = test.products;

                // Log.d("TEST" , productsModels.get(0).getProduct_title() + "" + newss.size()  ) ;
                adapter = new NewsFeedAdapter(getActivity(), newss, itemClickListenter);
                linearLayoutManager = new LinearLayoutManager(getContext());
                newsRecyclerView.setLayoutManager(linearLayoutManager);
                newsRecyclerView.setAdapter(adapter);

                adapter = new NewsTrendAdapter(getActivity(), productsModels);
                linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                trendRecyclerView.setLayoutManager(linearLayoutManager);
                trendRecyclerView.setNestedScrollingEnabled(true);
                trendRecyclerView.setAdapter(adapter);


            }

            @Override
            public void onError(Throwable e) {

                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onComplete() {

            }
        });


    }

    private void loadNews() {

        Call<List<NewsFeedModel>> call = ServiceGenerator
                .AllApi()
                .getNewsFeed();

        call.enqueue(new Callback<List<NewsFeedModel>>() {
            @Override
            public void onResponse(Call<List<NewsFeedModel>> call, Response<List<NewsFeedModel>> response) {

                if (response.code() == 200 && response.body() != null) {
                    newsfeedList = response.body();
                    adapter = new NewsFeedAdapter(getActivity(), newsfeedList, itemClickListenter);
                    linearLayoutManager = new LinearLayoutManager(getContext());
                    newsRecyclerView.setLayoutManager(linearLayoutManager);
                    newsRecyclerView.setAdapter(adapter);

//                        adapter = new NewsTrendAdapter(getActivity(), newsfeedList);
//                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//                        trendRecyclerView.setLayoutManager(linearLayoutManager);
//                        trendRecyclerView.setNestedScrollingEnabled(true);
//                        trendRecyclerView.setAdapter(adapter);

                    Log.d(TAG, "onResponse: feeds are" + newsfeedList.toString());
                }
            }

            @Override
            public void onFailure(Call<List<NewsFeedModel>> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.toString());
            }
        });


    }

    private void loadTrend() {
        Call<List<ProductsModel>> call = ServiceGenerator
                .AllApi()
                .getLatestProduct();

        call.enqueue(new Callback<List<ProductsModel>>() {
            @Override
            public void onResponse(Call<List<ProductsModel>> call, Response<List<ProductsModel>> response) {

                if (response.code() == 200 && response.body() != null) {
                    productsList = response.body();

                    adapter = new NewsTrendAdapter(getActivity(), productsList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    trendRecyclerView.setLayoutManager(linearLayoutManager);
                    trendRecyclerView.setNestedScrollingEnabled(true);
                    trendRecyclerView.setAdapter(adapter);

                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<ProductsModel>> call, Throwable t) {

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
