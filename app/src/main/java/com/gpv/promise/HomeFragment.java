package com.gpv.promise;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    @Bind(R.id.recycler_proposed_home) public RecyclerView proposedList;
    @Bind(R.id.recycler_current_home) public RecyclerView currentList;

    private ProposedAdapter mProposedAdapter;
    private RecyclerView.LayoutManager mProposedLayoutManager;
    private CurrentAdapter mCurrentAdapter;
    private RecyclerView.LayoutManager mCurrentLayoutManager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);
        // Inflate the layout for this fragment
        mProposedAdapter = new ProposedAdapter();
        mProposedLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        proposedList.setAdapter(mProposedAdapter);
        proposedList.setLayoutManager(mProposedLayoutManager);

        mCurrentAdapter = new CurrentAdapter();
        mCurrentLayoutManager = new LinearLayoutManager(getContext());
        currentList.setAdapter(mCurrentAdapter);
        currentList.setLayoutManager(mCurrentLayoutManager);
        return v;
    }

    public class ProposedAdapter extends RecyclerView.Adapter<ProposedAdapter.ViewHolder> {
        private List<ParseObject> projects;

        public class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.layout_proposed)
            public View onClick;
            @Bind(R.id.textview_name_proposed_title)
            public TextView name;
            @Bind(R.id.textview_city_proposed_title)
            public TextView city;
            @Bind(R.id.imageview_proposed_title)
            public ImageView projectImage;

            public ViewHolder(View v) {
                super(v);
                ButterKnife.bind(this, v);
            }
        }

        public ProposedAdapter() {
            super();
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Project");
            query.whereContains("currentStatus", "Proposed");
            projects = new ArrayList<>();
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    projects.addAll(objects);
                    ProposedAdapter.this.notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.projects.size();
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final ParseObject project = projects.get(position);
            holder.city.setText(project.getString("city"));
            holder.name.setText(project.getString("projectName"));
            holder.onClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ProposedDetailsActivity.class);
                    intent.putExtra(ProposedDetailsActivity.PROJECT_ID, project.getObjectId());
                    startActivity(intent);
                }
            });

            try {
                ParseFile imageFile = project.getParseFile("image");
                Picasso.with(getContext()).load(imageFile.getFile()).into(holder.projectImage);
            } catch (ParseException e) {
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_horizontal_proposedproject, parent, false);
            ButterKnife.bind(this, parent);
            ViewHolder vh = new ViewHolder(v);
            return  vh;
        }
    }

    public class CurrentAdapter extends RecyclerView.Adapter<CurrentAdapter.ViewHolder> {
        private List<ParseObject> projects;

        public class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.card_view_current_project)
            public View onClick;
            @Bind(R.id.imageview_current_title)
            public ImageView image;
            @Bind(R.id.textview2_sub_title)
            public TextView description;
            @Bind(R.id.textview1_current_title)
            public TextView title;

            public ViewHolder(View v) {
                super(v);
                ButterKnife.bind(this, v);
            }
        }

        public CurrentAdapter() {
            super();
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Project");
            String currentStatuses[] = {"In Progress", "Stalled"};
            query.whereContainedIn("currentStatus", new ArrayList<String>(Arrays.asList(currentStatuses)));
            projects = new ArrayList<>();
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    projects.addAll(objects);
                    CurrentAdapter.this.notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return projects.size() > 3 ? 3 : projects.size();
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final ParseObject project = projects.get(position);
            holder.description.setText(project.getString("projectDescription"));
            holder.title.setText(project.getString("projectName"));
            holder.onClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ProposedDetailsActivity.class);
                    intent.putExtra(ProposedDetailsActivity.PROJECT_ID, project.getObjectId());
                    startActivity(intent);
                }
            });
            try {
                ParseFile imageFile = project.getParseFile("image");
                if (imageFile != null)
                    Picasso.with(getContext()).load(imageFile.getFile()).into(holder.image);
            } catch (ParseException e) {
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_vertical_currentproject, parent, false);
            ButterKnife.bind(this, parent);
            ViewHolder vh = new ViewHolder(v);
            return  vh;
        }
    }

}
