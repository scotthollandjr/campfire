package com.example.guest.campfire.service;

import android.util.Xml;

import com.example.guest.campfire.Constants;
import com.example.guest.campfire.models.Campsite;

import java.io.IOException;
import java.io.StringReader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;


public class ApiService {
    private static final String URL = "https://api.transitandtrails.org//api/v1/campgrounds.json?key=";
    //private static final String URL_API = "&api_key=";

    public ArrayList<Campsite> mCampsites = new ArrayList<>();

    public static void findCampsites(String state, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        String url = URL + Constants.API_KEY;

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