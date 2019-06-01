package com.example.googlemapdemo.shapes;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.googlemapdemo.R;
import com.example.googlemapdemo.polylineanimator.MapAnimator;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Cap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class PolylinesActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;

    private LatLng sydney;
    private LatLng delhi;
    private LatLng mumbai;
    private LatLng jammu;
    private LatLng chandigarh;
    private LatLng jaipur;
    private LatLng amritsar;
    private List<LatLng> animationList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polyline);
        setMapFragment();
        setPolyLineList();



    }
    private void setMapFragment()
    {
        SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_polyline);
        supportMapFragment.getMapAsync(this);
        setPlaces();
    }

    private void setPolyLineList()
    {
        animationList = new ArrayList<>();
        animationList.add(delhi);
        animationList.add(sydney);
        animationList.add(jaipur);
        animationList.add(jammu);
        animationList.add(chandigarh);
        animationList.add(mumbai);
        animationList.add(amritsar);
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
    private void setPlaces() {
        sydney = new LatLng(-34, 151);
        jammu = new LatLng(32.732998, 74.864273);
        delhi = new LatLng(28.644800, 77.216721);
        mumbai = new LatLng(19.076090, 72.877426);
        jaipur = new LatLng(26.922070, 75.778885);
        chandigarh = new LatLng(30.741482, 76.768066);
        amritsar = new LatLng(31.633980, 74.872261);

    }

    private void animatePolyline()
    {

        MapAnimator.getInstance().setPrimaryLineColor(R.color.colorAccent);
        MapAnimator.getInstance().setPrimaryLineColor(R.color.colorPrimary);
        MapAnimator.getInstance().animateRoute(mGoogleMap,animationList);

    }



    private void drawPolyLine()
    {
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(sydney);
        polylineOptions.add(jammu);
        polylineOptions.add(jaipur);
        polylineOptions.add(delhi);
        polylineOptions.add(chandigarh);
        polylineOptions.add(amritsar);
        polylineOptions.add(mumbai);
        polylineOptions.width(20f);
        mGoogleMap.addPolyline(polylineOptions);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap= googleMap;
        mGoogleMap.addMarker(new MarkerOptions().position(amritsar));
        setMarkers();
        animatePolyline();
       // drawPolyLine();
    }
}
