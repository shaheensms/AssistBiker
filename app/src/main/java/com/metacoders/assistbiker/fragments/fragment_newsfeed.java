package com.metacoders.assistbiker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.adapter.NewsFeedAdapter;
import com.metacoders.assistbiker.adapter.NewsTrendAdapter;
import com.metacoders.assistbiker.models.NewsFeedModel;
import com.metacoders.assistbiker.requests.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class fragment_newsfeed extends Fragment {
    View view;
    private RecyclerView newsRecyclerView, trendRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    private List<NewsFeedModel> newsfeedList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newfeed, container, false);
        // Inflate the layout for this fragment
        loadNews();

        newsRecyclerView = view.findViewById(R.id.news_feed_recyclerview);
        trendRecyclerView = view.findViewById(R.id.trending_news_feed_recyclerview);
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


        return view;
    }

    private void loadNews() {

        Call<List<NewsFeedModel>> call = ServiceGenerator
                .AllApi()
                .getNewsFeed();

        call.enqueue(new Callback<List<NewsFeedModel>>() {
            @Override
            public void onResponse(Call<List<NewsFeedModel>> call, Response<List<NewsFeedModel>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    if (response.isSuccessful() && response.body() != null) {
                        newsfeedList = response.body();
                        adapter = new NewsFeedAdapter(getActivity(), newsfeedList);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        newsRecyclerView.setLayoutManager(linearLayoutManager);
                        newsRecyclerView.setAdapter(adapter);

                        adapter = new NewsTrendAdapter(getActivity(), newsfeedList);
                        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        trendRecyclerView.setLayoutManager(linearLayoutManager);
                        trendRecyclerView.setNestedScrollingEnabled(true);
                        trendRecyclerView.setAdapter(adapter);


                        Log.d(TAG, "onResponse: feeds are" + newsfeedList.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NewsFeedModel>> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.toString());
            }
        });


    }

}
