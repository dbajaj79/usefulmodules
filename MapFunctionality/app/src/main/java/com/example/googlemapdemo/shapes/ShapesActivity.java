package com.example.googlemapdemo.shapes;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.googlemapdemo.R;
import com.example.googlemapdemo.adapter.MapOptionsAdapter;
import com.example.googlemapdemo.databinding.ActivityMainBinding;
import com.example.googlemapdemo.onItemClickListener.OnItemSelectedListener;

public class ShapesActivity extends AppCompatActivity implements OnItemSelectedListener<String> {

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setRecyclerView();
    }
    private void setRecyclerView() {
        String[] options = new String[]{"Circle","Polyline"};
        MapOptionsAdapter mapOptionsAdapter = new MapOptionsAdapter(options, this);
        activityMainBinding.setAdapter(mapOptionsAdapter);

    }

    @Override
    public void onItemSelected(int position, String value) {
        Intent intent =null;
        switch (position)
        {
            case 0:
                intent = new Intent(ShapesActivity.this,CircleDemoActivity.class);
                break;
            case 1:
                intent = new Intent(ShapesActivity.this,PolylinesActivity.class);
                break;
            case 2:

                break;
        }
        if(intent!=null)
        {
            startActivity(intent);
        }
    }
}
