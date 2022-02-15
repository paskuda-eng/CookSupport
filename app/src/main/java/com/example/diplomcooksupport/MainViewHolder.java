package com.example.diplomcooksupport;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MainViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView meal;
    public TextView description;

    public MainViewHolder(View itemView) {

        super(itemView);

        meal = itemView.findViewById(R.id.meal_name);
        image = itemView.findViewById(R.id.card_view_images);
        description = itemView.findViewById(R.id.meal_description);

    }
}
