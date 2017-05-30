package com.example.betulyaman.chirp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.betulyaman.chirp.R.id;
import com.example.betulyaman.chirp.R.layout;
import com.example.betulyaman.chirp.containers.Category;
import com.example.betulyaman.chirp.handlers.CategorizationHandler;

import java.util.ArrayList;

public class TabFragment extends Fragment {

    ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate tab_layout and setup Views.
        View view = inflater.inflate(layout.layout_tab, null);
        tabLayout = (TabLayout) view.findViewById(id.layout_tab);
        viewPager = (ViewPager) view.findViewById(id.pager_view);

        // Set an Adapter for the View Pager
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        //for(String category : new DatabaseHandler(getActivity().getApplicationContext()).getCategoryNames()){
        //    viewPagerAdapter.addFragment(new TweetFragment(), category);
        //}

        viewPager.setAdapter(viewPagerAdapter);

        // Now, this is a workaround. The setupWithViewPager doesn't works without the runnable. Maybe a Support Library Bug.
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        populateFragments();

        return view;
    }

    public void populateFragments() {
        ArrayList<Category> categories = CategorizationHandler.start(getActivity().getApplicationContext());

        for (Category category : categories) {
            viewPagerAdapter.getTitleFragment(category.getName()).setListToView(category.getTweets());
        }
    }
}