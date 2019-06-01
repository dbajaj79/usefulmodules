package com.example.googlemapdemo.cameraandview;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.googlemapdemo.R;
import com.example.googlemapdemo.adapter.MapOptionsAdapter;
import com.example.googlemapdemo.databinding.ActivityMainBinding;
import com.example.googlemapdemo.onItemClickListener.OnItemSelectedListener;

public class CameraAndViewOptionsActivity extends AppCompatActivity implements OnItemSelectedListener<String>{


    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Camera and View Options");
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setRecyclerView();
    }

    private void setRecyclerView()
    {
        String[] options = new String[]{"Zooming","Scrolling","Positioning","BoundariesActivity"};
        MapOptionsAdapter mapOptionsAdapter = new MapOptionsAdapter(options, CameraAndViewOptionsActivity.this);
        activityMainBinding.setAdapter(mapOptionsAdapter);
    }

    @Override
    public void onItemSelected(int position, String value) {
        Intent intent =null;
        switch (position)
        {
            case 0:
                intent = new Intent(CameraAndViewOptionsActivity.this, CameraZoomingActivity.class);
                break;
            case 1:
                intent = new Intent(CameraAndViewOptionsActivity.this, CameraScrollingActivity.class);
                break;
            case 2:
                intent = new Intent(CameraAndViewOptionsActivity.this, CameraPositioningActivity.class);
                break;
            case 3:
                intent = new Intent(CameraAndViewOptionsActivity.this, BoundariesActivity.class);
                break;
        }
        if(intent!=null)
        {
            startActivity(intent);
        }

    }


}
