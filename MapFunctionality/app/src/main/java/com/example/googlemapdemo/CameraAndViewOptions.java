package com.example.googlemapdemo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.googlemapdemo.adapter.MapOptionsAdapter;
import com.example.googlemapdemo.databinding.ActivityMainBinding;
import com.example.googlemapdemo.onItemClickListener.OnItemSelectedListener;

public class CameraAndViewOptions extends AppCompatActivity implements OnItemSelectedListener<String>{

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setRecyclerView();
    }

    private void setRecyclerView()
    {
        String[] options = new String[]{"Zooming","Scrolling","Positioning"};
        MapOptionsAdapter mapOptionsAdapter = new MapOptionsAdapter(options,CameraAndViewOptions.this);
        activityMainBinding.setAdapter(mapOptionsAdapter);
    }

    @Override
    public void onItemSelected(int position, String value) {
        Intent intent =null;
        switch (position)
        {
            case 0:
                intent = new Intent(CameraAndViewOptions.this,CameraZooming.class);
                break;
            case 1:
                intent = new Intent(CameraAndViewOptions.this,CameraScrolling.class);
            case 2:
                intent = new Intent(CameraAndViewOptions.this,CameraPositioning.class);
                break;
        }
        if(intent!=null)
        {
            startActivity(intent);
        }

    }


}
