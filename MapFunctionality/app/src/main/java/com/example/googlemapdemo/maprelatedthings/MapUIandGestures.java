package com.example.googlemapdemo.maprelatedthings;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.googlemapdemo.R;
import com.example.googlemapdemo.databinding.ActivityUiandgesturesBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapUIandGestures extends AppCompatActivity implements OnMapReadyCallback {
    ActivityUiandgesturesBinding activityUiandgesturesBinding;
    private GoogleMap mGoogleMap;

    @Override
    public void onCreate(Bundle saveInStance) {
        super.onCreate(saveInStance);
        setTitle("Map UI and Gestures");
        activityUiandgesturesBinding = DataBindingUtil.setContentView(this, R.layout.activity_uiandgestures);
        setMapFragment();
    }

    private void setMapFragment() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_uiandgestures);
        supportMapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        setMapUI();
        setListeners();


    }
    private void setMapUI()
    {
        LatLng latLng = new LatLng(-34,151);
        mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("SydnMaker"));
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.setBuildingsEnabled(true);
        mGoogleMap.getUiSettings().setIndoorLevelPickerEnabled(true);
    }
    private void setListeners()
    {
        OnClickHelper onClickHelper = new OnClickHelper();
        activityUiandgesturesBinding.disableRotate.setOnClickListener(onClickHelper);
        activityUiandgesturesBinding.disableTilt.setOnClickListener(onClickHelper);
        activityUiandgesturesBinding.disableZoom.setOnClickListener(onClickHelper);
        activityUiandgesturesBinding.disableSroll.setOnClickListener(onClickHelper);

        //Hide Show UI
        activityUiandgesturesBinding.hideTilt.setOnClickListener(onClickHelper);
        activityUiandgesturesBinding.hideToolbar.setOnClickListener(onClickHelper);
        activityUiandgesturesBinding.hideZoom.setOnClickListener(onClickHelper);
        //Map Type
        activityUiandgesturesBinding.typeHybrid.setOnClickListener(onClickHelper);
        activityUiandgesturesBinding.typeNormal.setOnClickListener(onClickHelper);
        activityUiandgesturesBinding.typeStelite.setOnClickListener(onClickHelper);
        activityUiandgesturesBinding.typeTrain.setOnClickListener(onClickHelper);
        activityUiandgesturesBinding.typenone.setOnClickListener(onClickHelper);
        activityUiandgesturesBinding.enableTrafic.setOnClickListener(onClickHelper);

    }
    class OnClickHelper implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.disable_rotate:
                    if(activityUiandgesturesBinding.disableRotate.getText().toString().equalsIgnoreCase("Disable Rotate"))
                    {
                        activityUiandgesturesBinding.disableRotate.setText("Enable Rotate");
                        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);
                    }
                    else
                    {
                        activityUiandgesturesBinding.disableRotate.setText("Disable Rotate");
                        mGoogleMap.getUiSettings().setRotateGesturesEnabled(true);
                    }
                    break;
                case R.id.disable_tilt:
                    if(activityUiandgesturesBinding.disableTilt.getText().toString().equalsIgnoreCase("Disable Tilt"))
                    {
                        activityUiandgesturesBinding.disableTilt.setText("Enable Tilt");
                        mGoogleMap.getUiSettings().setTiltGesturesEnabled(false);
                    }
                    else
                    {
                        activityUiandgesturesBinding.disableTilt.setText("Disable Tilt");
                        mGoogleMap.getUiSettings().setTiltGesturesEnabled(true);
                    }
                    break;
                case R.id.disable_sroll:
                    if(activityUiandgesturesBinding.disableSroll.getText().toString().equalsIgnoreCase("Disable Scroll"))
                    {
                        activityUiandgesturesBinding.disableSroll.setText("Enable Scroll");
                        mGoogleMap.getUiSettings().setScrollGesturesEnabled(false);
                    }
                    else
                    {
                        activityUiandgesturesBinding.disableSroll.setText("Disable Scroll");
                        mGoogleMap.getUiSettings().setScrollGesturesEnabled(true);
                    }
                    break;
                case R.id.disable_zoom:
                    if(activityUiandgesturesBinding.disableZoom.getText().toString().equalsIgnoreCase("Disable Zoom"))
                    {
                        activityUiandgesturesBinding.disableZoom.setText("Disable Zoom");
                        mGoogleMap.getUiSettings().setZoomGesturesEnabled(false);
                    }
                    else
                    {
                        activityUiandgesturesBinding.disableSroll.setText("Disable Scroll");
                        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
                    }
                    break;

                case R.id.hide_tilt:
                    if(activityUiandgesturesBinding.hideTilt.getText().toString().equalsIgnoreCase("Hide Tilt"))
                    {
                        activityUiandgesturesBinding.hideTilt.setText("Show Tilt");
                        mGoogleMap.getUiSettings().setCompassEnabled(false);
                    }
                    else
                    {
                        activityUiandgesturesBinding.hideTilt.setText("Hide Tilt");
                        mGoogleMap.getUiSettings().setCompassEnabled(true);
                    }
                    break;
                case R.id.hide_zoom:
                    if(activityUiandgesturesBinding.hideZoom.getText().toString().equalsIgnoreCase("Hide Zoom"))
                    {
                        activityUiandgesturesBinding.hideZoom.setText("Show Zoom");
                        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
                    }
                    else
                    {
                        activityUiandgesturesBinding.hideZoom.setText("Hide Zoom");
                        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
                    }
                case R.id.hide_toolbar:
                    if(activityUiandgesturesBinding.hideToolbar.getText().toString().equalsIgnoreCase("Hide ToolBar"))
                    {
                        activityUiandgesturesBinding.hideToolbar.setText("Show ToolBar");
                        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
                    }
                    else
                    {
                        activityUiandgesturesBinding.hideToolbar.setText("Hide ToolBar");
                        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
                    }

                    break;
                case R.id.type_hybrid:
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    break;
                case R.id.type_normal:
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    break;
                case R.id.type_train:
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    break;
                case R.id.type_stelite:
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    break;
                case R.id.typenone:
                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                    break;
                case R.id.enable_trafic:
                    if(activityUiandgesturesBinding.enableTrafic.getText().toString().equalsIgnoreCase("Enable Traffic"))
                    {
                        activityUiandgesturesBinding.enableTrafic.setText("Disable Traffic");
                        mGoogleMap.setTrafficEnabled(true);
                    }
                    else
                    {
                        activityUiandgesturesBinding.enableTrafic.setText("Enable Traffic");
                        mGoogleMap.setTrafficEnabled(true);
                    }

                    break;


            }
        }
    }
}
