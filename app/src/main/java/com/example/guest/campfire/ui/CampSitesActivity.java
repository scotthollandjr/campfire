package com.example.guest.campfire.ui;

import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.guest.campfire.R;
import com.example.guest.campfire.models.Campsite;
import com.example.guest.campfire.service.ApiService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CampSitesActivity extends AppCompatActivity {
    public static ArrayList<Campsite> mCampsites = new ArrayList<>();
    @Bind(R.id.stateTextView) TextView mStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_sites);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String state = intent.getStringExtra("state");
        mStateTextView.setText(state);

        getCampsites(state);
    }

    public void getCampsites(String state) {
        final ApiService apiService = new ApiService();

        apiService.findCampsites(state, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mCampsites = apiService.processResults(response);

                CampSitesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.d("CampsitesAct", "response: " + response);

//                        String[] sites = new String[mCampsites.size()];
//                        for (int i = 0; i < sites.length; i++) {
//                            sites[i] = mCampsites.get(i).getName();
                        }
                    });
                }
            });
        }
    }
