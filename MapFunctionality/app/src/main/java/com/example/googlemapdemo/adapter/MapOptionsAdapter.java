package com.example.googlemapdemo.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.googlemapdemo.R;
import com.example.googlemapdemo.databinding.ItemMapOptionBinding;
import com.example.googlemapdemo.onItemClickListener.OnItemSelectedListener;
import com.example.googlemapdemo.viewholder.MapOptionViewHolder;

public class MapOptionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private String[] options;
    private Context context;
    private OnItemSelectedListener<String> onItemSelectedListener;

    public MapOptionsAdapter(String[]options,Context context)
    {
        this.context = context;
        this.options = options;
        this.onItemSelectedListener = (OnItemSelectedListener)context;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemMapOptionBinding itemMapOptionBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_map_option,viewGroup,false);
        return new MapOptionViewHolder(itemMapOptionBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MapOptionViewHolder mapOptionViewHolder = (MapOptionViewHolder)viewHolder;
        mapOptionViewHolder.onBind(options[i]);
        mapOptionViewHolder.getItemMapOptionBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemSelectedListener!=null)
                {
                    onItemSelectedListener.onItemSelected(i,options[i]);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.length;
    }
}
