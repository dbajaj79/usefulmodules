package com.example.googlemapdemo.cameraandview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.googlemapdemo.R;
import com.example.googlemapdemo.databinding.ActivityScrollingBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CameraScrolling extends AppCompatActivity implements OnMapReadyCallback {

    ActivityScrollingBinding activityScrollingBinding;
    GoogleMap mGoogle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityScrollingBinding = DataBindingUtil.setContentView(this, R.layout.activity_scrolling);
        setTitle("Camera Scrolling");
        setupMapFragment();
    }

    private void addMarker() {
        mGoogle.addMarker(new MarkerOptions().position(new LatLng(-34, 151)).title("Sydney"));
    }

    private void setupMapFragment() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_scrolling);
        supportMapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogle = googleMap;
        addMarker();
        setClickListener();
    }

    private void setClickListener() {
        OnClickListenerHelper onClickListenerHelper = new OnClickListenerHelper();
        activityScrollingBinding.btnUp.setOnClickListener(onClickListenerHelper);
        activityScrollingBinding.btnDown.setOnClickListener(onClickListenerHelper);
        activityScrollingBinding.btnLeft.setOnClickListener(onClickListenerHelper);
        activityScrollingBinding.btnRight.setOnClickListener(onClickListenerHelper);
    }


    class OnClickListenerHelper implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_down:
                    mGoogle.animateCamera(CameraUpdateFactory.scrollBy(0f, -100f));

                    break;
                case R.id.btn_up:
                    mGoogle.animateCamera(CameraUpdateFactory.scrollBy(0f, 100f));

                    break;
                case R.id.btn_right:
                    mGoogle.animateCamera(CameraUpdateFactory.scrollBy(-100f, 0f));

                    break;
                case R.id.btn_left:
                    mGoogle.animateCamera(CameraUpdateFactory.scrollBy(100f, 0f));

                    break;
            }
        }

    }
}


