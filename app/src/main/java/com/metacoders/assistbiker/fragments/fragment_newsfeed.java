package com.metacoders.assistbiker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.adapter.NewsFeedAdapter;
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
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    private List<NewsFeedModel> newsfeedList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newfeed, container, false);
        // Inflate the layout for this fragment
        loadNews();

        mRecyclerView = view.findViewById(R.id.news_feed_recyclerview);
        mRecyclerView.setHasFixedSize(true);


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
                        mRecyclerView.setLayoutManager(linearLayoutManager);
                        mRecyclerView.setAdapter(adapter);

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