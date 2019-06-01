package com.example.googlemapdemo.cameraandview;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.googlemapdemo.AppConstant;
import com.example.googlemapdemo.R;
import com.example.googlemapdemo.databinding.ActivityBoundriesBinding;
import com.example.googlemapdemo.onItemClickListener.OnEnterClickListener;
import com.example.googlemapdemo.util.CustomAlertDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class BoundariesActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityBoundriesBinding activityBoundriesBinding;
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
        setTitle("BoundariesActivity");
        activityBoundriesBinding = DataBindingUtil.setContentView(this, R.layout.activity_boundries);
        setMapFragment();
        setCompontents();
        setListener();
    }

    private void setCompontents() {
        setPlaces();
        setListener();
    }

    private void setListener() {
        activityBoundriesBinding.btnCentering.setOnClickListener(new OnClickListenerHelper());
        activityBoundriesBinding.btnLatlngbounds.setOnClickListener(new OnClickListenerHelper());
        activityBoundriesBinding.btnMapviewport.setOnClickListener(new OnClickListenerHelper());
        activityBoundriesBinding.btnheightWidth.setOnClickListener(new OnClickListenerHelper());
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

    private void setLatLngBound(int padding) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(mumbai);
        builder.include(delhi);
        builder.include(sydney);
        builder.include(jammu);
        builder.include(mumbai);
        builder.include(amritsar);
        builder.include(jaipur);
        builder.include(chandigarh);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), padding));

    }
    private void setCenteringBound(int padding) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(mumbai);
        builder.include(delhi);
        builder.include(sydney);
        builder.include(jammu);
        builder.include(mumbai);
        builder.include(amritsar);
        builder.include(jaipur);
        builder.include(chandigarh);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(builder.build().getCenter(),padding));

    }
    private void setheigtWidth(int padding) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(mumbai);
        builder.include(delhi);
        builder.include(sydney);
        builder.include(jammu);
        builder.include(mumbai);
        builder.include(amritsar);
        builder.include(jaipur);
        builder.include(chandigarh);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),500,500,padding));

    }

    private void setMapBounds() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(mumbai);
        builder.include(delhi);
        builder.include(sydney);
        builder.include(jammu);
        builder.include(mumbai);
        builder.include(amritsar);
        builder.include(jaipur);
        builder.include(chandigarh);
        mGoogleMap.setLatLngBoundsForCameraTarget(builder.build());

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        setMarkers();
    }

    private void setMapFragment() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_boundaries);
        supportMapFragment.getMapAsync(this);
    }

    class OnClickListenerHelper implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            CustomAlertDialog.setOnEnterClickListener(new OnEnterClickListenerHelper());
            switch (v.getId()) {
                case R.id.btn_latlngbounds:
                    CustomAlertDialog.showCustomDailog(BoundariesActivity.this,"Padding for LatLng Bounds","Please provide Padding muliply by 100 to see effect and less than 700", AppConstant.LAT_LNG_PADDING);
                    break;
                case R.id.btn_centering:
                    CustomAlertDialog.showCustomDailog(BoundariesActivity.this,"Padding for Centering","Please provide Zoom less then 4 to see",AppConstant.CENTERING);
                    break;
                case R.id.btn_mapviewport:
                    setMapBounds();
                    break;
                case R.id.btnheight_width:
                    CustomAlertDialog.showCustomDailog(BoundariesActivity.this,"Bounds with Height and Width","Height is 500 widht 500",AppConstant.BOUND_HEIGHT_WIDTH);
                    break;

            }
        }
    }

    class OnEnterClickListenerHelper implements OnEnterClickListener
    {
        @Override
        public void onEnterClick(String tag, String value) {
            switch (tag)
            {
                case AppConstant.LAT_LNG_PADDING:
                    setLatLngBound(Integer.parseInt(value));
                    break;
                case AppConstant.CENTERING:
                    setCenteringBound(Integer.parseInt(value));
                    break;
                case AppConstant.MAP_AREA:

                    break;
                case AppConstant.BOUND_HEIGHT_WIDTH:
                    setheigtWidth(Integer.parseInt(value));
                    break;
            }
        }
    }
}
