package com.example.guest.campfire.ui;

import android.content.Intent;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.guest.campfire.R;
import com.example.guest.campfire.models.Market;
import com.example.guest.campfire.service.ApiService;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MarketActivity extends AppCompatActivity {
    public static ArrayList<Market> mMarkets = new ArrayList<>();
    public static ArrayList<String> ids = new ArrayList<>();
    @Bind(R.id.stateTextView) TextView mStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String zip = intent.getStringExtra("zip");
        mStateTextView.setText(zip);
    }

    public void getMarkets(String zip) {
        final ApiService apiService = new ApiService();

        apiService.findMarkets(zip, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                ids = apiService.getIds(response); //<------list of ids!-------

                final ApiService apiService = new ApiService();

                for (String id : ids) {
                    apiService.getMarketDetails(id, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Market market = apiService.processResults(response);
                            Log.d("CUBONE", "onreponse: " + market.getAddress());
                            mMarkets.add(market);
                        }

                    });
                }

                MarketActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        //                        String[] sites = new String[mCampsites.size()];
                        //                        for (int i = 0; i < sites.length; i++) {
                        //                            sites[i] = mCampsites.get(i).getName();
                    }
                });
            }
        });
    }

    public ArrayList<LatLng> getLocationFromAddress(ArrayList<Market> markets) {
        ArrayList latLongs = new ArrayList();

        Geocoder coder = new Geocoder(MarketActivity.this, Locale.getDefault());

        List<Address> address;
        LatLng coordinates = null;

        for (Market market : markets) {
            String street = market.getAddress();
            try {
                address = coder.getFromLocationName(street, 1);

                if (address == null) {
                    return null;
                }
                Address location = address.get(0);
                location.getLatitude();
                location.getLongitude();

                coordinates = new LatLng(location.getLatitude(), location.getLongitude());

                latLongs.add(coordinates);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return latLongs;
    }

}


