package com.example.guest.listenup.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guest.listenup.R;
import com.example.guest.listenup.models.Comment;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.commentEditText) EditText mCommentEditText;
    @Bind(R.id.sendButton) Button mSend;
    @Bind(android.R.id.list) ListView mList;
    private Firebase mFirebaseRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userName;
    private RecyclerView mMessages;
    private LinearLayoutManager mManager;
    private FirebaseListAdapter<Comment> mAdapter;

//    private ChatListAdapter mChatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);

        FirebaseRecyclerAdapter<Comment, CommentHolder> mAdapter;
        mFirebaseRef = new Firebase("https://listenup-51c14.firebaseio.com/");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        mMessages = (RecyclerView) findViewById(R.id.messagesList);

        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(false);

        mMessages.setHasFixedSize(false);
        mMessages.setLayoutManager(mManager);


        mMessages.setHasFixedSize(true);
        mMessages.setLayoutManager(new LinearLayoutManager(this));
        Query lastFifty = ref.limitToLast(50);
        mAdapter = new FirebaseRecyclerAdapter<Comment, CommentHolder>(Comment.class, R.layout.message, CommentHolder.class, lastFifty) {
            @Override
            protected void populateViewHolder(CommentHolder viewHolder, Comment model, int position) {
                viewHolder.setName(model.getUser());
                viewHolder.setComment(model.getContent());
            }
        };
        mMessages.setAdapter(mAdapter);

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

        mSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mSend) {
            String message = mCommentEditText.getText().toString();
            Comment comment = new Comment(message, userName);
            mFirebaseRef.push().setValue(comment);

            mCommentEditText.setText("");
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
