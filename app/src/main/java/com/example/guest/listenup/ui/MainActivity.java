package com.example.guest.listenup.ui;


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

import android.widget.Button;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.ScrollView;


import com.example.guest.listenup.R;
import com.example.guest.listenup.models.Comment;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import java.util.ArrayList;

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
    private ScrollView mScrollView;
    private FirebaseListAdapter<Comment> mAdapter;
    private ArrayList<Comment> commentList;
    private long children;
    private int childInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);

        mScrollView = (ScrollView) findViewById(R.id.activity_chat_scroll_view);
        FirebaseRecyclerAdapter<Comment, CommentHolder> mAdapter;
        mFirebaseRef = new Firebase("https://listenup-51c14.firebaseio.com/");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        mMessages = (RecyclerView) findViewById(R.id.messagesList);



        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(false);

        mMessages.setHasFixedSize(false);
        mMessages.setLayoutManager(mManager);

//        mMessages.scrollToPosition(getChildren());

        mMessages.setHasFixedSize(true);
        mMessages.setLayoutManager(new LinearLayoutManager(this));
        mManager.setStackFromEnd(true);

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

//    private void scrollToBottom() {
//        mRecyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onItemRangeInserted(int positionStart, int itemCount) {
//                mManager.smoothScrollToPosition(mMessages, null, mRecyclerViewAdapter.getItemCount());
//            }
//        });
//    }

    @Override
    public void onClick(View v) {
        if (v == mSend) {
            getChildren();
            //int hello = getChildren();
            //mMessages.scrollToPosition(hello - 1);
            String message = mCommentEditText.getText().toString();
            Comment comment = new Comment(message, userName);
            mFirebaseRef.push().setValue(comment);

            mCommentEditText.setText("");
        }
    };

    public void getChildren() {
        mFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("stuff", dataSnapshot.getChildrenCount() +"");
                children = dataSnapshot.getChildrenCount();
                childInt = (int) (long) children;
                mMessages.scrollToPosition(childInt - 1);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });


        //mMessages.scrollToPosition(childInt);
    }


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
