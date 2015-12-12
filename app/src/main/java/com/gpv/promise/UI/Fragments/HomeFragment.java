package com.gpv.promise.UI.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gpv.promise.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment {
    @Bind(R.id.recycler_current_home)
    public RecyclerView mCurrentProjectsListView;

    @Bind(R.id.recycler_proposed_home)
    public RecyclerView mProposedProjectListView;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    class CurrentProjectAdapter extends RecyclerView.Adapter<CurrentProjectAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.imageview_current_title)
            ImageView currentProjectImageView;
            @Bind(R.id.textview1_current_title)
            TextView currentProjectTitleView;
            @Bind(R.id.textview2_current_title)
            TextView currentProjectDescriptionView;

            ViewHolder(View v) {
                super(v);
                ButterKnife.bind(this, v);
            }
        }
        public int getItemCount() {
            return 0;
        }

        @Override
        public CurrentProjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(CurrentProjectAdapter.ViewHolder holder, int position) {

        }
    }

}