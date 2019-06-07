package com.example.captureimage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.captureimage.databinding.ActivityMainBinding;
import com.example.captureimage.util.AppConstants;

public class MainActivity extends AppCompatActivity implements CameraUtils.Callback{

    ActivityMainBinding activityMainBinding;
    private String capturedUri = null;
    private String mNewPicPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setOnClickListener();
    }

    private void setOnClickListener()
    {
        activityMainBinding.btnCamera.setOnClickListener(new OnClickHelper());
        activityMainBinding.btnGallery.setOnClickListener(new OnClickHelper());
    }

    private static final String TAG = "MainActivity";

    @Override
    public String getCapturedUri() {
        return capturedUri;
    }

    @Override
    public void setCapturedUri(String uri) {
        this.capturedUri = uri;

    }

    @Override
    public void onImageCaptured(Pair<String, String> originalCompressedPair) {
        Bitmap bitmap =null;
        if (!TextUtils.isEmpty(originalCompressedPair.second)) {
            mNewPicPath = originalCompressedPair.second;
            bitmap = BitmapFactory.decodeFile(mNewPicPath);

        }
        else if(!TextUtils.isEmpty(originalCompressedPair.first))
        {
            mNewPicPath = originalCompressedPair.first;
            bitmap = BitmapFactory.decodeFile(mNewPicPath);
        }
        if(bitmap!=null)
        {
            activityMainBinding.imageView.setImageBitmap(bitmap);
        }

    }

    class OnClickHelper implements View.OnClickListener
    {

        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.btn_camera:
                    CameraUtils.takeCameraPhoto(MainActivity.this);
                    break;
                case R.id.btn_gallery:
                    CameraUtils.choosePhoto(MainActivity.this);
                    break;
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //FareHeader attachPrescriptionFragment = (FareHeader) getSupportFragmentManager().findFragmentByTag(FareHeader.class.getSimpleName());
        if (requestCode == AppConstants.REQUEST_CODE_CAMERA_AND_STORAGE_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CameraUtils.takeCameraPhoto(this);
            }
        }
         else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

            CameraUtils.onRequestPermissionsResult(MainActivity.this, requestCode, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CameraUtils.ACTION_TAKE_PHOTO:
            case CameraUtils.ACTION_CHOOSE_FILE:
                if (resultCode == RESULT_OK) {
                    CameraUtils.onCaptureRxResult(MainActivity.this, requestCode, data);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

}
