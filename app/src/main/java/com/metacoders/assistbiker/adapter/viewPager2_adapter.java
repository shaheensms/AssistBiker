package com.metacoders.assistbiker.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.metacoders.assistbiker.fragments.fragment_newsfeed;
import com.metacoders.assistbiker.fragments.fragment_products;

public class viewPager2_adapter extends FragmentStateAdapter {

    private String[] titles = new String[]{"Home", "Products", "Cart"};

    public viewPager2_adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new fragment_newsfeed();
            case 1:
                return new fragment_products();

        }

        return new fragment_newsfeed();
    }



    @Override
    public int getItemCount() {
        return titles.length;
    }
}
