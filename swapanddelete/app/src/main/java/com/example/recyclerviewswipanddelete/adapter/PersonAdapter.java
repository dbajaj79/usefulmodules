package com.example.recyclerviewswipanddelete.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recyclerviewswipanddelete.R;
import com.example.recyclerviewswipanddelete.databinding.ItemPersonBinding;
import com.example.recyclerviewswipanddelete.model.Person;
import com.example.recyclerviewswipanddelete.viewholder.PersonViewHolder;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<Person> personList;
    Context context;

    public PersonAdapter(Context context,List<Person>personList)
    {
        this.personList = personList;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemPersonBinding itemPersonBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_person,viewGroup,false);
        return new PersonViewHolder(itemPersonBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PersonViewHolder personViewHolder = (PersonViewHolder)viewHolder;
        Person person = personList.get(i);
        personViewHolder.onBind(person);

    }

    @Override
    public int getItemCount() {
        return personList.size();
    }
}
