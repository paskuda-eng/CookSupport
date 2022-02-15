package com.example.diplomcooksupport;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView meal;
    public TextView description;

    public SearchViewHolder(View itemView) {

        super(itemView);

        meal = itemView.findViewById(R.id.meal_name);
        image = itemView.findViewById(R.id.card_view_images);
        description = itemView.findViewById(R.id.meal_description);
    }
}
