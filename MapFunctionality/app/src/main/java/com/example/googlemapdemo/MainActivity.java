package com.example.googlemapdemo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.googlemapdemo.adapter.MapOptionsAdapter;
import com.example.googlemapdemo.databinding.ActivityMainBinding;
import com.example.googlemapdemo.onItemClickListener.OnItemSelectedListener;


public class MainActivity extends AppCompatActivity implements OnItemSelectedListener<String> {


    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setRecyclerView();

    }

    private void setRecyclerView() {
        String[] options = new String[]{"Simple Map","Map UI And Gestures","Map Padding",
                "Camera and View"};
        MapOptionsAdapter mapOptionsAdapter = new MapOptionsAdapter(options, this);
        activityMainBinding.setAdapter(mapOptionsAdapter);

    }


    @Override
    public void onItemSelected(int position, String value) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(MainActivity.this, SimpleMap.class);
                break;
            case 1:
                intent = new Intent(MainActivity.this,MapUIandGestures.class);
                break;
            case 2:
                intent = new Intent(MainActivity.this,GoogleMapPadding.class);
                break;
            case 3:
                intent = new Intent(MainActivity.this,CameraAndViewOptions.class);
                break;

        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
