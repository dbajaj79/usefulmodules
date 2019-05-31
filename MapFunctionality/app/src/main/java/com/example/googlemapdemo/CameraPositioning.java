package com.example.googlemapdemo;

import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.googlemapdemo.databinding.ActivityCameraandviewBinding;
import com.example.googlemapdemo.databinding.ActivityCamerpositioningBinding;
import com.example.googlemapdemo.onItemClickListener.OnEnterClickListener;
import com.example.googlemapdemo.util.CustomAlertDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CameraPositioning extends AppCompatActivity implements OnMapReadyCallback {

    ActivityCamerpositioningBinding activityCamerpositioningBinding;

    GoogleMap mGoogle;

    private LatLng sydney;
    private LatLng delhi;
    private LatLng mumbai;
    private CameraPosition cameraPositioning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sydney = new LatLng(-34,151);
        delhi = new LatLng(28.644800,77.216721);
        mumbai = new LatLng(19.076090,72.877426);
        activityCamerpositioningBinding = DataBindingUtil.setContentView(this, R.layout.activity_camerpositioning);
        setCameraPosition();
        setupMapFragment();
    }
    private void setCameraPosition()
    {
        cameraPositioning = new CameraPosition(mumbai,4f,4f,2f);
    }
    private void addMarkers()
    {
        mGoogle.addMarker(new MarkerOptions().position(delhi).title("Delhi"));
        mGoogle.addMarker(new MarkerOptions().position(mumbai).title("Mumbai"));
        mGoogle.addMarker(new MarkerOptions().position(sydney).title("Sydney"));
    }
    private void setupMapFragment() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_cameraposition);
        supportMapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogle = googleMap;
        setClickListener();
        addMarkers();
    }

    private void setClickListener() {
        OnClickListenerHelper onClickListenerHelper = new OnClickListenerHelper();
        activityCamerpositioningBinding.btnLatlngWithZoom.setOnClickListener(onClickListenerHelper);
        activityCamerpositioningBinding.btnnewlatlng.setOnClickListener(onClickListenerHelper);
        activityCamerpositioningBinding.btnNewPosition.setOnClickListener(onClickListenerHelper);

    }


    class OnClickListenerHelper implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            CustomAlertDialog.setOnEnterClickListener(new OnEnterClickListenerHelper());
            switch (v.getId()) {
                case R.id.btn_latlng_with_zoom:
                    mGoogle.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,4f));
                    break;
                case R.id.btnnewlatlng:
                    mGoogle.animateCamera(CameraUpdateFactory.newLatLng(delhi));
                    break;
                case R.id.btn_new_position:
                    mGoogle.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPositioning));
                    break;
                            }

        }
    }

    class OnEnterClickListenerHelper implements OnEnterClickListener {

        @Override
        public void onEnterClick(String tag, String value) {
            Toast.makeText(CameraPositioning.this, tag + value, Toast.LENGTH_LONG).show();
            switch (tag) {
                case AppConstants.PREF_MAX:
                    mGoogle.setMaxZoomPreference(Float.parseFloat(value));
                    break;
                case AppConstants.PREF_MIN:
                    mGoogle.setMinZoomPreference(Float.parseFloat(value));
                    break;
                case AppConstants.ZOOM_BY:
                    mGoogle.moveCamera(CameraUpdateFactory.zoomBy(Float.parseFloat(value)));
                    break;
                case AppConstants.ZOOM_BY_POINT:

                    Float zoomValue = Float.parseFloat(value);
                    Point point = new Point(10, 20);
                    mGoogle.moveCamera(CameraUpdateFactory.zoomBy(zoomValue, point));
                    break;
                case AppConstants.ZOOM_TO:
                    mGoogle.moveCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(value)));
                    break;
            }
        }
    }

}
