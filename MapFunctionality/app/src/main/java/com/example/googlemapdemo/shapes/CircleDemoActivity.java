package com.example.googlemapdemo.shapes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.googlemapdemo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CircleDemoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private LatLng sydney;
    private LatLng delhi;
    private LatLng mumbai;
    private LatLng jammu;
    private LatLng chandigarh;
    private LatLng jaipur;
    private LatLng amritsar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        setMapFragment();
        setPlaces();
    }

    private void setPlaces() {
        sydney = new LatLng(-34, 151);
        jammu = new LatLng(32.732998, 74.864273);
        delhi = new LatLng(28.644800, 77.216721);
        mumbai = new LatLng(19.076090, 72.877426);
        jaipur = new LatLng(26.922070, 75.778885);
        chandigarh = new LatLng(30.741482, 76.768066);
        amritsar = new LatLng(31.633980, 74.872261);

    }

    private void setMarkers() {
        mGoogleMap.addMarker(new MarkerOptions().position(amritsar));
        mGoogleMap.addMarker(new MarkerOptions().position(delhi));
        mGoogleMap.addMarker(new MarkerOptions().position(sydney));
        mGoogleMap.addMarker(new MarkerOptions().position(jammu));
        mGoogleMap.addMarker(new MarkerOptions().position(chandigarh));
        mGoogleMap.addMarker(new MarkerOptions().position(mumbai));
        mGoogleMap.addMarker(new MarkerOptions().position(jaipur));
    }


    private void setMapFragment()
    {
        SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_circle);
        supportMapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap= googleMap;
        setMarkers();
        drawCircle();

    }

    private void drawCircle()
    {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(amritsar);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(amritsar,14f));
        circleOptions.strokeWidth(20f);
        circleOptions.fillColor(Color.RED);
        circleOptions.strokeColor(Color.BLUE);
        circleOptions.radius(1000);
        mGoogleMap.addCircle(circleOptions);

    }
}
