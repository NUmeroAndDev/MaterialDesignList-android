package com.numero.materiallistdemo;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    private Context context;
    private ArrayList<Item> list;
    private HashMap<Integer, Boolean> selection;
    private OnItemClickListener onItemClickListener;

    ItemAdapter(Context context, ArrayList<Item> list){
        this.list = list;
        selection = new HashMap<>();
        this.context = context;
    }

    public void setSelection(int position, boolean checked) {
        selection.put(position, checked);
        notifyDataSetChanged();
    }

    public boolean isSelection(int position){
        return selection.get(position) != null;
    }

    public boolean isSelectedItem(){
        return selection.size() != 0;
    }

    public void removeSelection(int position) {
        selection.remove(position);
        notifyDataSetChanged();
    }

    public void clearSelection() {
        selection.clear();
        notifyDataSetChanged();
    }

    public void deleteSelection() {
        for (int i = list.size() - 1; i >= 0; i--) {
            if (selection.get(i) != null) {
                list.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        return new ItemViewHolder(view, list);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.image.setImageResource(R.mipmap.ic_launcher);
        holder.titleText.setText(list.get(position).title);
        holder.subTitleText.setText(list.get(position).subTitle);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onItemLongClick(position);
                return false;
            }
        });

        holder.itemLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.itemBackground));
        if (selection.get(position) != null) {
            holder.itemLayout.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_light));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
}
