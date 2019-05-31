package com.example.googlemapdemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.googlemapdemo.databinding.ActivityCameraandviewBinding;
import com.example.googlemapdemo.onItemClickListener.OnEnterClickListener;
import com.example.googlemapdemo.util.CustomAlertDialog;
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
        cameraandviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_cameraandview);
        setupMapFragment();
    }

    private void setupMapFragment() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_cameraandview);
        supportMapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogle = googleMap;
        cameraandviewBinding.btnZoomout.setOnClickListener(new OnClickLisenerHelper());
        cameraandviewBinding.btnZoonin.setOnClickListener(new OnClickLisenerHelper());
        cameraandviewBinding.btnMaxPreference.setOnClickListener(new OnClickLisenerHelper());
        cameraandviewBinding.btnMinPreference.setOnClickListener(new OnClickLisenerHelper());
    }

    class OnClickLisenerHelper implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            CustomAlertDialog.setOnEnterClickListener(new OnEnterClickListenerHelper());
            switch (v.getId()) {
                case R.id.btn_zoomout:
                    mGoogle.moveCamera(CameraUpdateFactory.zoomOut());
                    break;
                case R.id.btn_zoonin:
                    mGoogle.moveCamera(CameraUpdateFactory.zoomIn());
                    break;
                case R.id.btn_max_preference:
                    CustomAlertDialog.showCustomDailog(CameraAndView.this, "Set Min Zoom Preference", "User can't increase Zoom Level from Given Number", AppConstants.PREF_MAX);
                    break;
                case R.id.btn_min_preference:
                    CustomAlertDialog.showCustomDailog(CameraAndView.this, "Set Min Zoom Preference", "User can't change Map Zoom Level Below", AppConstants.PREF_MIN);
                    break;
            }

        }
    }
    class OnEnterClickListenerHelper implements OnEnterClickListener
    {

        @Override
        public void onEnterClick(String tag, String value) {
            Toast.makeText(CameraAndView.this,tag+value,Toast.LENGTH_LONG).show();
            switch (tag)
            {
                case AppConstants.PREF_MAX:
                    mGoogle.setMaxZoomPreference(Float.parseFloat(value));
                    break;
                case AppConstants.PREF_MIN:
                    mGoogle.setMinZoomPreference(Float.parseFloat(value));
                    break;
            }
        }
    }

}
