package com.metacoders.assistbiker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.metacoders.assistbiker.R;

public class fragment_newsfeed extends Fragment {
    View view ;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_newfeed, container, false);
        // Inflate the layout for this fragment
        return view ;
    }
}
