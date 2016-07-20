package com.example.guest.campfire.ui;

import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.guest.campfire.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CampSitesActivity extends AppCompatActivity {
    @Bind(R.id.stateTextView) TextView mStateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_sites);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String state = intent.getStringExtra("state");
        mStateTextView.setText(state);
    }
}
