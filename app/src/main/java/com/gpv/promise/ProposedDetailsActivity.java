package com.gpv.promise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.identity.intents.AddressConstants;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProposedDetailsActivity extends AppCompatActivity {
    @Bind(R.id.image_proposed_details) public ImageView image;
    @Bind(R.id.button_proposed_details) public AppCompatButton button;
    @Bind(R.id.desc_para_proposed_details) public TextView description;
    @Bind(R.id.details_para_proposed_details) public TextView details;
    @Bind(R.id.links_para_proposed_links) public TextView links;

    private String projectId;

    public static String PROJECT_ID = "PROJECT_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proposed_details_items);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                projectId = extras.getString(PROJECT_ID);
                populateViews();
            }
            else
                finish();
        }
    }

    void populateViews() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Project");
        query.getInBackground(projectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                ParseObject project = object;
                if (e == null) {
                    try {
                        if (project != null && project.getParseFile("image") != null) {
                            Picasso.with(ProposedDetailsActivity.this)
                                    .load(project.getParseFile("image").getFile())
                                    .into(image);
                        }
                    } catch (ParseException ex) {
                    }
                    getSupportActionBar().setTitle(project.getString("projectName"));
                    description.setText(project.getString("projectDescription"));
                    String detailsText = "";
                    detailsText += "Date of publication of proposal: " + project.getDate("proposedDate") + "<br/>"
                            + "Expected date of completion" + project.getDate("expectedCompletedDate") + "<br/>"
                            + "Agencies involved: " + project.getString("agency");

                    details.setText(Html.fromHtml(detailsText));
                    links.setText(project.getString("reference"));
                }

            }
        });

    }
}
