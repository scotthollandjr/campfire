package com.example.guest.campfire.service;

import android.util.Log;
import android.util.Xml;

import com.example.guest.campfire.Constants;
import com.example.guest.campfire.models.Market;

import java.io.IOException;
import java.io.StringReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    private static final String ZIP_URL = "http://search.ams.usda.gov/farmersmarkets/v1/data.svc/zipSearch?zip=";
    private static final String ID_URL = "http://search.ams.usda.gov/farmersmarkets/v1/data.svc/mktDetail?id=";

    public ArrayList<Object> mObjex = new ArrayList<>();

    public static void findMarkets(String zip, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        String url = ZIP_URL + zip;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }



    public ArrayList<String> getIds(Response response) {
        ArrayList<String> marketIds = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject marketsJSON = new JSONObject(jsonData);
                JSONArray itemsJSON = marketsJSON.getJSONArray("results");

                for (int i = 0; i < itemsJSON.length(); i++) {
                    JSONObject indivJSON = itemsJSON.getJSONObject(i);
                    String id = indivJSON.getString("id");

                    marketIds.add(id);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return marketIds;
    }

    public void getMarketDetails(String id, Callback callback) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .build();

            String url = ID_URL + id;

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(callback);
    }

    public Market processResults(Response response) {
        Market m = new Market("sup","sup","sup","sup");
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject rawJSON = new JSONObject(jsonData);
                JSONObject itemJSON = rawJSON.getJSONObject("marketdetails");
                String address = itemJSON.getString("Address");
                String link = itemJSON.getString("GoogleLink");
                String products = itemJSON.getString("Products");
                String schedule = itemJSON.getString("Schedule");

                m.setAddress(address);
                m.setLink(link);
                m.setProducts(products);
                m.setSchedule(schedule);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return m;
    }
}

// "http://search.ams.usda.gov/farmersmarkets/v1/data.svc/mktDetail?id=" + id

// "http://search.ams.usda.gov/farmersmarkets/v1/data.svc/locSearch?lat=" + lat + "&lng=" + lng