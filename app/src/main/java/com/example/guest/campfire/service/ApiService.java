package com.example.guest.campfire.service;

import android.util.Xml;

import com.example.guest.campfire.Constants;
import com.example.guest.campfire.models.Market;

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
    private static final String URL = "http://search.ams.usda.gov/farmersmarkets/v1/data.svc/zipSearch?zip=";
    //private static final String URL_API = "&api_key=";

    public ArrayList<Market> mMarkets = new ArrayList<>();

    public static void findMarkets(String zip, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        String url = URL + zip;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    
    

    public ArrayList<Market> processResults(Response response) {
        ArrayList<Market> markets = new ArrayList<>();
        return markets;
    }
}

// "http://search.ams.usda.gov/farmersmarkets/v1/data.svc/zipSearch?zip=" + zip

// "http://search.ams.usda.gov/farmersmarkets/v1/data.svc/locSearch?lat=" + lat + "&lng=" + lng