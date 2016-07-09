package com.numero.materiallistdemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemViewHolder extends RecyclerView.ViewHolder{

    LinearLayout itemLayout;
    TextView titleText, subTitleText;
    ImageView image;
    View itemView;
    ArrayList<Item> list;

    public ItemViewHolder(View itemView, ArrayList<Item> list) {
        super(itemView);

        this.list = list;
        this.itemView = itemView;

        itemLayout = (LinearLayout) itemView.findViewById(R.id.item_layout);
        titleText = (TextView) itemView.findViewById(R.id.title);
        subTitleText = (TextView) itemView.findViewById(R.id.sub_title);
        image = (ImageView) itemView.findViewById(R.id.image);
    }
}
