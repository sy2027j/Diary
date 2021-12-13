package com.example.diary;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<Item> items = new ArrayList<Item>();
    private int oldPosition = -1;
    public int selectedPosition = -1;

    public RecyclerViewAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Item getItem(int position) {
        return items.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        final ViewHolder holder1 = (ViewHolder) holder;
        holder.setItem(item);

        if (selectedPosition == position)
            holder.emotion.setBackgroundColor(Color.GRAY);
        else
            holder.emotion.setBackgroundColor(Color.TRANSPARENT);

        holder.emotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPosition = selectedPosition;
               // selectedPosition = position;
                notifyItemChanged(oldPosition);

                selectedPosition = holder1.getAdapterPosition();
                notifyItemChanged(selectedPosition);
            }
        });
    }
    public void addItem(Item item){
        items.add(item);
    }

    public String getSeletedImg() {
        String mood = "";
        if (selectedPosition != -1) {
            mood = String.valueOf(selectedPosition);
        }else{
            mood="";
        }
        return mood;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView emotion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            emotion = itemView.findViewById(R.id.mood_item);
        }

        public void setItem(Item item){
            emotion.setText(item.imgfile);
        }
    }

}