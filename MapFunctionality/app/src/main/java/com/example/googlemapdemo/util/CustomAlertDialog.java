package com.example.googlemapdemo.util;

import android.app.AlertDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.googlemapdemo.R;
import com.example.googlemapdemo.databinding.InputAlertdilogBinding;
import com.example.googlemapdemo.onItemClickListener.OnEnterClickListener;

public class CustomAlertDialog {

    static AlertDialog alertDialog;
    static OnEnterClickListener onEnterClickListener;
    static InputAlertdilogBinding alertdilogBinding;
    static String mytag;
    static String value;

    public static OnEnterClickListener getOnEnterClickListener() {
        return onEnterClickListener;
    }

    public static void setOnEnterClickListener(OnEnterClickListener onEnterClickListener) {
        CustomAlertDialog.onEnterClickListener = onEnterClickListener;
    }

    public static void showCustomDailog(Context context, String title, String description, String tag) {
         alertdilogBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.input_alertdilog, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(alertdilogBinding.getRoot());
        mytag = tag;
        alertdilogBinding.textViewTitle.setText(title);
        alertdilogBinding.textViewMessage.setText(description);
        alertdilogBinding.btnCancel.setOnClickListener(new OnClickHelper());
        alertdilogBinding.btnEnter.setOnClickListener(new OnClickHelper());
        alertDialog = builder.create();
        alertDialog.show();
    }
    public static void showCustomDailogWithArea(Context context, String title, String description, String tag) {
        alertdilogBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.input_alertdilog_witharea, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(alertdilogBinding.getRoot());
        mytag = tag;
        alertdilogBinding.textViewTitle.setText(title);
        alertdilogBinding.textViewMessage.setText(description);
        alertdilogBinding.btnCancel.setOnClickListener(new OnClickHelper());
        alertdilogBinding.btnEnter.setOnClickListener(new OnClickHelper());
        alertDialog = builder.create();
        alertDialog.show();
    }

    static class OnClickHelper implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cancel:
                    alertDialog.dismiss();
                    break;
                case R.id.btn_enter:
                    if(alertdilogBinding.editTextValue.getText().toString().isEmpty())
                    {
                        Toast.makeText(v.getContext(), "Please Enter Value", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        onEnterClickListener.onEnterClick(mytag,alertdilogBinding.editTextValue.getText().toString());
                        alertDialog.dismiss();
                    }
                    break;
            }
        }
    }
}
