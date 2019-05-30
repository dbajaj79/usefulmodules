package com.example.googlemapdemo.onItemClickListener;

public interface OnItemSelectedListener<T extends Object> {
    void onItemSelected(int position,T value);
}
