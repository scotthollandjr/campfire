package com.example.guest.campfire.service;

import com.example.guest.campfire.Constants;
import com.example.guest.campfire.models.Campsite;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;


public class ApiService {
    private static final String URL = "http://api.amp.active.com/camping/campgrounds/?pstate=";
    private static final String URL_API = "&api_key=";

    public ArrayList<Campsite> mCampsites = new ArrayList<>();

    public static void findCampsites(String state, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        String url = URL + state + URL_API + Constants.API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Campsite> processResults(Response response) {
        ArrayList<Campsite> campsites = new ArrayList<>();

        return campsites;
    }
}
