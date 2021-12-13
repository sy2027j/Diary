package com.example.diary;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerViewTimelineAdapter extends RecyclerView.Adapter<RecyclerViewTimelineAdapter.ItemViewHolder> {

    private ArrayList<Timeline> listData = new ArrayList<>();
    static String date="";
    private Context context;
    private OnItemClick mCallback;

    RecyclerViewTimelineAdapter(Context context, ArrayList<Timeline> listData, OnItemClick listener){
        this.listData=listData;
        this.context=context;
        this.mCallback=listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(Timeline data) {
        listData.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView year,month,day,title,content;
        LinearLayout linearLayout;
        private Timeline timeline;

        ItemViewHolder(View itemView) {
            super(itemView);

            year = itemView.findViewById(R.id.year);
            month = itemView.findViewById(R.id.month);
            day = itemView.findViewById(R.id.day);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            linearLayout = itemView.findViewById(R.id.linear);
        }

        void onBind(Timeline data) {
            this.timeline=data;

            year.setText(data.getYear());
            month.setText(data.getMonth());
            day.setText(data.getDay());
            title.setText(data.getTitle());
            content.setText(data.getContent());

            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            date=timeline.getYear()+timeline.getMonth()+timeline.getDay();
            mCallback.onClick(date);
            date="";
        }
    }
}