package com.numero.materiallistdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemViewHolder extends RecyclerView.ViewHolder{

    TextView titleText, subTitleText;
    ImageView image;
    Context context;
    View itemView;
    ArrayList<Item> list;

    public ItemViewHolder(View itemView, ArrayList<Item> list, Context context) {
        super(itemView);

        this.list = list;
        this.context = context;
        this.itemView = itemView;

        titleText = (TextView) itemView.findViewById(R.id.title);
        subTitleText = (TextView) itemView.findViewById(R.id.sub_title);
        image = (ImageView) itemView.findViewById(R.id.image);
    }
}
