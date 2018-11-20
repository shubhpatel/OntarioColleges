package com.centennial.colleges.ontario.ontariocolleges;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Colleges colleges;
    int position, branchPosition;
    Context mContext;
    Boolean isBranch, isAll;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mContext = this;

        isAll = getIntent().getBooleanExtra("all", false);

        if (!isAll) {

            position = getIntent().getIntExtra("position", 0);

            isBranch = getIntent().getBooleanExtra("isBranch", false);

            if (isBranch) {
                branchPosition = getIntent().getIntExtra("branchPosition", 0);
            }
        }


        Gson gson = new GsonBuilder().create();

        colleges = gson.fromJson(loadJSONFromAsset(), Colleges.class);


        Log.e("count", "size " + colleges.collegeList.get(position).branches.size());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.e("MapsActivity.java", "Error while reading file : " + ex.getMessage());
            return null;
        }
        return json;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(mContext);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(mContext);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(mContext);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

//        Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(55.585901, -105.750596);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (isAll) {

            LatLng tmp = new LatLng(55.585901, -105.750596);

            for (Colleges.College college : colleges.collegeList) {
                for (Colleges.College.Branch branch : college.branches) {
                    tmp = new LatLng(branch.lat, branch.lng);
                    mMap.addMarker(new MarkerOptions().position(tmp).title(college.getName() + " College\n" + branch.getBranchname() + " Campus").snippet(branch.getAddress()));
                }

            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(tmp));

        } else {

            Colleges.College college = colleges.collegeList.get(position);

            if (isBranch) {
                Colleges.College.Branch branch = college.branches.get(branchPosition);
                mMap.addMarker(new MarkerOptions().position(new LatLng(branch.lat, branch.lng)).title(college.getName() + " College\n" + branch.getBranchname() + " Campus").snippet(branch.getAddress()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(branch.lat, branch.lng)));
            } else {
                LatLng tmp = new LatLng(55.585901, -105.750596);
                for (Colleges.College.Branch branch : college.branches) {
                    tmp = new LatLng(branch.lat, branch.lng);
                    mMap.addMarker(new MarkerOptions().position(tmp).title(college.getName() + " College\n" + branch.getBranchname() + " Campus").snippet(branch.getAddress()));
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLng(tmp));

            }

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
