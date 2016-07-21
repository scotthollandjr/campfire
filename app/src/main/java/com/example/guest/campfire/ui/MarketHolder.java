package com.example.guest.campfire.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.guest.campfire.R;


public class MarketHolder extends RecyclerView.ViewHolder {
    View mView;

    public MarketHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setName(String name) {
        TextView field = (TextView) mView.findViewById(R.id.message_text);
        field.setText(name);
    }

    public void setComment(String comment) {
        TextView field = (TextView) mView.findViewById(R.id.name_text);
        field.setText(comment);
    }


}
