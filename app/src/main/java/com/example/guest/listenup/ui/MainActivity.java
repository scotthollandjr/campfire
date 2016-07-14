package com.example.guest.listenup.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import com.example.guest.listenup.R;

import butterknife.Bind;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.commentEditText) EditText mCommentEditText;
    @Bind(R.id.commentListView) ListView mCommentListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
