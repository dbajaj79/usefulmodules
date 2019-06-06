package com.example.recyclerviewswipanddelete.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.recyclerviewswipanddelete.databinding.ItemPersonBinding;
import com.example.recyclerviewswipanddelete.model.Person;

public class PersonViewHolder extends RecyclerView.ViewHolder {
    private ItemPersonBinding itemPersonBinding;

    public PersonViewHolder(@NonNull ItemPersonBinding itemPersonBinding) {
        super(itemPersonBinding.getRoot());
        this.itemPersonBinding = itemPersonBinding;
    }

    public void onBind(Person person)
    {
        itemPersonBinding.setPerson(person);

    }
}
