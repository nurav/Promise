package com.gpv.promise;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class HomeFragment1 extends Fragment {
    public RecyclerView mCurrentProjectsListView;
    public RecyclerView mProposedProjectListView;

    private CurrentProjectAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    public HomeFragment1() {
        // Required empty public constructor
    }

    public static HomeFragment1 newInstance() {
        HomeFragment1 fragment = new HomeFragment1();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home1, container, false);
        mCurrentProjectsListView = (RecyclerView) view.findViewById(R.id.recycler_current_home);
        mProposedProjectListView = (RecyclerView) view.findViewById(R.id.recycler_proposed_home);

        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new CurrentProjectAdapter(getContext(), new ArrayList<ParseObject>());
        mCurrentProjectsListView.setLayoutManager(mLayoutManager);
        mCurrentProjectsListView.setAdapter(mAdapter);

        return inflater.inflate(R.layout.fragment_home1, container, false);
    }

}

class CurrentProjectAdapter extends RecyclerView.Adapter<com.gpv.promise.CurrentProjectAdapter.ViewHolder> {
    private List<ParseObject> projects;
    private Context mContext;

    CurrentProjectAdapter(Context context, List<ParseObject> objects) {
        super();
        projects = new ArrayList<>();
        projects.addAll(objects);
        ParseObject object = new ParseObject("Project");
        object.put("projectName", "Hello");
        object.put("projectDescription", "Desc");
        projects.add(object);

        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView currentProjectImageView;
        TextView currentProjectTitleView;
        TextView currentProjectDescriptionView;

        ViewHolder(View v) {
            super(v);
            currentProjectDescriptionView = (TextView) v.findViewById(R.id.textview2_current_title);
            currentProjectTitleView = (TextView) v.findViewById(R.id.textview1_current_title);
            currentProjectImageView = (ImageView) v.findViewById(R.id.imageview_proposed_title);
        }
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    @Override
    public com.gpv.promise.CurrentProjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_vertical_currentproject, parent, false);
        ButterKnife.bind(this, parent);
        ViewHolder vh = new ViewHolder(v);
        return  vh;
    }

    @Override
    public void onBindViewHolder(com.gpv.promise.CurrentProjectAdapter.ViewHolder holder, int position) {
        holder.currentProjectDescriptionView.setText(projects.get(position).getString("projectDescription"));
        try {
            Picasso.with(mContext)
                    .load(projects.get(position)
                            .getParseFile("image")
                            .getFile())
                    .into(holder.currentProjectImageView);
        } catch (ParseException e) {

        }
        holder.currentProjectTitleView.setText(projects.get(position).getString("projectName"));
    }
}