package com.example.guest.campfire.service;

import android.util.Xml;

import com.example.guest.campfire.Constants;
import com.example.guest.campfire.models.Campsite;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

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

    public class CampsiteParser {

        public List parse(InputStream in) throws XmlPullParserException, IOException {

            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();
                return readFeed(parser);
            } finally {
                in.close();
            }
        }
    }

    private List readFeed(XmlPullParserException parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.st)
    }
}
