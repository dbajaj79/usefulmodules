package com.example.googlemapdemo.cameraandview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.googlemapdemo.R;
import com.example.googlemapdemo.databinding.ActivityBoundriesBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class Boundries extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityBoundriesBinding activityBoundriesBinding;
    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBoundriesBinding = DataBindingUtil.setContentView(this,R.layout.activity_boundries);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
    }

    private void setMapFragment()
    {
        SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_boundries);
        supportMapFragment.getMapAsync(this);
    }
}
