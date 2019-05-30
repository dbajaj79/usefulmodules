package com.example.googlemapdemo.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.googlemapdemo.databinding.ItemMapOptionBinding;

public class MapOptionViewHolder extends RecyclerView.ViewHolder {

    private ItemMapOptionBinding itemMapOptionBinding;

    public MapOptionViewHolder(@NonNull ItemMapOptionBinding itemMapOptionBinding) {
        super(itemMapOptionBinding.getRoot());
        this.itemMapOptionBinding = itemMapOptionBinding;
    }

    public void onBind(String title)
    {
        itemMapOptionBinding.textTitle.setText(title);
    }

    public ItemMapOptionBinding getItemMapOptionBinding() {
        return itemMapOptionBinding;
    }
}
