package com.example.diary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TimelineFragment extends Fragment implements OnItemClick{


    private RecyclerView tlRecyclerView;
    private ArrayList<Timeline> mList;
    static RecyclerViewTimelineAdapter adapter;
    Context context;
    static String selectDate="";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.activity_timeline, container, false);

        tlRecyclerView=(RecyclerView) v.findViewById(R.id.recyclerViewTl);
        mList = new ArrayList<>();
        context=container.getContext();
        tlRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
       // adapter=new RecyclerViewTimelineAdapter();
        adapter=new RecyclerViewTimelineAdapter(context,mList,this);
        tlRecyclerView.setAdapter(adapter);

        try{
            List<Diary> diaryList = ((MainActivity)getActivity()).db.diaryDao().getAll();
            Log.e("diaryList", String.valueOf(diaryList.size()));
            if(diaryList.size()>0){
                for(int i=0; i< diaryList.size(); i++){
                    String date =diaryList.get(i).getDate();
                    String y =date.substring(0,4);
                    String m =date.substring(4,6);
                    String d =date.substring(6,8);
                    adapter.addItem(new Timeline(y,m,d,diaryList.get(i).getTitle(),diaryList.get(i).getContent()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        adapter.notifyDataSetChanged();

        return v;
    }

    @Override
    public void onClick(String value) {
        selectDate=value;
        if(selectDate!=""){
            Log.e("널",selectDate);
            DetailViewFragment detailViewFragment=new DetailViewFragment();
            Bundle bundle = new Bundle();
            bundle.putString("date", selectDate);
            detailViewFragment.setArguments(bundle);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, detailViewFragment).addToBackStack(null).commit();
        }
    }
}
