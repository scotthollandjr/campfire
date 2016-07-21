package com.example.guest.campfire.ui;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ScrollView;
import android.widget.Spinner;


import com.example.guest.campfire.R;
import com.example.guest.campfire.models.Market;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.statesSpinner) Spinner mStatesSpinner;
    @Bind(R.id.submitButton) Button mSubmit;
    @Bind(R.id.zipEditText) EditText mZipEditText;
//    private Spinner stateSpinner;
    private Firebase mFirebaseRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userName;
    private RecyclerView mMessages;
    private LinearLayoutManager mManager;
    private ScrollView mScrollView;
    private FirebaseListAdapter<Market> mAdapter;
    private ArrayList<Market> marketList;
    private long children;
    private int childInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);

        final FirebaseRecyclerAdapter<Market, MarketHolder> mAdapter;
        mFirebaseRef = new Firebase("https://campfire-75336.firebaseio.com/");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userName = user.getDisplayName();
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                } else {

                }
            }
        };

        mSubmit.setOnClickListener(this);

        Spinner spinner = (Spinner) findViewById(R.id.statesSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.states_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Select a state");
        spinner.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        if(v == mSubmit) {
            //String state = mStatesSpinner.getSelectedItem().toString();
            String zip = mZipEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, MarketActivity.class);
            intent.putExtra("zip", zip);
            startActivity(intent);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        //mAdapter.unregisterDataSetObserver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
