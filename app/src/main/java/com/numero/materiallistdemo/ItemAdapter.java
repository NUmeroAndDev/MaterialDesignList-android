package com.numero.materiallistdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    private ArrayList<Item> list;
    private Context context;

    ItemAdapter(ArrayList<Item> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        return new ItemViewHolder(view, list, context);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int i) {
        holder.image.setImageResource(R.mipmap.ic_launcher);
        holder.titleText.setText(list.get(i).title);
        holder.subTitleText.setText(list.get(i).subTitle);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(i, context);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClick(i, context);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    abstract public void onItemClick(int position, Context context);

    abstract public void onItemLongClick(int position, Context context);
}
