package com.example.googlemapdemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.googlemapdemo.databinding.ActivityCameraandviewBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class CameraAndView extends AppCompatActivity implements OnMapReadyCallback {

    ActivityCameraandviewBinding cameraandviewBinding;
    GoogleMap mGoogle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraandviewBinding = DataBindingUtil.setContentView(this,R.layout.activity_cameraandview);
        setupMapFragment();
    }
    private void setupMapFragment()
    {
        SupportMapFragment supportMapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_cameraandview);
        supportMapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogle = googleMap;
        cameraandviewBinding.btnZoomout.setOnClickListener(new OnClickLisenerHelper());
        cameraandviewBinding.btnZoonin.setOnClickListener(new OnClickLisenerHelper());
    }

    class OnClickLisenerHelper implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btn_zoomout:
                    mGoogle.moveCamera(CameraUpdateFactory.zoomOut());
                    break;
                case R.id.btn_zoonin:
                    mGoogle.moveCamera(CameraUpdateFactory.zoomIn());
                    break;
            }

        }
    }
}
