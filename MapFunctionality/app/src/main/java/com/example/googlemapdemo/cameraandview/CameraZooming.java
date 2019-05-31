package com.example.googlemapdemo.cameraandview;

import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.googlemapdemo.AppConstant;
import com.example.googlemapdemo.R;
import com.example.googlemapdemo.databinding.ActivityCameraandviewBinding;
import com.example.googlemapdemo.onItemClickListener.OnEnterClickListener;
import com.example.googlemapdemo.util.CustomAlertDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class CameraZooming extends AppCompatActivity implements OnMapReadyCallback {

    ActivityCameraandviewBinding cameraandviewBinding;

    GoogleMap mGoogle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Camera Zooming");
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
        setClickListener();
    }

    private void setClickListener() {
        OnClickListenerHelper onClickListenerHelper = new OnClickListenerHelper();
        cameraandviewBinding.btnZoomout.setOnClickListener(onClickListenerHelper);
        cameraandviewBinding.btnZoonin.setOnClickListener(onClickListenerHelper);
        cameraandviewBinding.btnMaxPreference.setOnClickListener(onClickListenerHelper);
        cameraandviewBinding.btnMinPreference.setOnClickListener(onClickListenerHelper);
        cameraandviewBinding.btnZoomBy.setOnClickListener(onClickListenerHelper);
        cameraandviewBinding.btnZoomwithPoint.setOnClickListener(onClickListenerHelper);
        cameraandviewBinding.btnZoomTo.setOnClickListener(onClickListenerHelper);
        cameraandviewBinding.btnRestzoom.setOnClickListener(onClickListenerHelper);
    }


    class OnClickListenerHelper implements View.OnClickListener {


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
                    CustomAlertDialog.showCustomDailog(CameraZooming.this, "Set Max Zoom Preference", "User can't increase Zoom Level from Given Number", AppConstant.PREF_MAX);
                    break;

                case R.id.btn_min_preference:
                    CustomAlertDialog.showCustomDailog(CameraZooming.this, "Set Min Zoom Preference", "User can't change Map Zoom Level Below", AppConstant.PREF_MIN);
                    break;
                case R.id.btn_zoom_by:
                    CustomAlertDialog.showCustomDailog(CameraZooming.this, "Map is Zoom By this Number", "But make Sure Zoom Range Between Max and Min Zoom Preference", AppConstant.ZOOM_TO);
                    break;
                case R.id.btn_zoomwithPoint:
                    CustomAlertDialog.showCustomDailog(CameraZooming.this, "Map is Zoom By this Number", "But make Sure Zoom Range Between Max and Min Zoom Preference", AppConstant.ZOOM_BY_POINT);
                    break;
                case R.id.btn_zoom_to:
                    CustomAlertDialog.showCustomDailog(CameraZooming.this, "Map is Zoom By this Number", "But make Sure Zoom Range Between Max and Min Zoom Preference", AppConstant.ZOOM_BY);
                    break;
                case R.id.btn_restzoom:
                    mGoogle.resetMinMaxZoomPreference();
                    break;
            }

        }
    }

    class OnEnterClickListenerHelper implements OnEnterClickListener {

        @Override
        public void onEnterClick(String tag, String value) {
            Toast.makeText(CameraZooming.this, tag + value, Toast.LENGTH_LONG).show();
            switch (tag) {
                case AppConstant.PREF_MAX:
                    mGoogle.setMaxZoomPreference(Float.parseFloat(value));
                    break;
                case AppConstant.PREF_MIN:
                    mGoogle.setMinZoomPreference(Float.parseFloat(value));
                    break;
                case AppConstant.ZOOM_BY:
                    mGoogle.moveCamera(CameraUpdateFactory.zoomBy(Float.parseFloat(value)));
                    break;
                case AppConstant.ZOOM_BY_POINT:

                    Float zoomValue = Float.parseFloat(value);
                    Point point = new Point(10, 20);
                    mGoogle.moveCamera(CameraUpdateFactory.zoomBy(zoomValue, point));
                    break;
                case AppConstant.ZOOM_TO:
                    mGoogle.moveCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(value)));
                    break;
            }
        }
    }

}
