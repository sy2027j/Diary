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

public class DetailViewFragment extends Fragment {
    TextView textmood, editdate;
    TextView content, title;
    Button updatebtn, deletebtn; //수정창으로 이동하는 버튼
    String date;
    Context context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.activity_detailview, container, false);

        textmood = (TextView) v.findViewById(R.id.textmood);
        editdate =(TextView) v.findViewById(R.id.editdate);
        title=(TextView) v.findViewById(R.id.title);
        content =(TextView)v.findViewById(R.id.content);
        updatebtn =(Button)v.findViewById(R.id.updatebtn);
        deletebtn=(Button) v.findViewById(R.id.deletebtn);
        context=container.getContext();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        CalendarFragment calendarFragment = new CalendarFragment();
        AlertDialog.Builder builder =new AlertDialog.Builder(context);
        builder.setTitle("Diary").setMessage("기록을 삭제합니다.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((MainActivity)getActivity()).db.diaryDao().diaryDelete(date);
                transaction.replace(R.id.container, calendarFragment).addToBackStack(null).commit();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog=builder.create();

        if(getArguments()!=null){
            date=getArguments().getString("date");
            String dates=date.substring(0,4)+" / "+date.substring(4,6)+" / "+date.substring(6,8);
            editdate.setText(dates);
            try{
                Diary diary=((MainActivity)getActivity()).db.diaryDao().loadAllByDate(date);
                switch (diary.mood){
                    case "0": textmood.setText("\uD83E\uDD70"); break;
                    case "1": textmood.setText("☺"); break;
                    case "2": textmood.setText("\uD83D\uDE10"); break;
                    case "3": textmood.setText("\uD83D\uDE14"); break;
                    case "4": textmood.setText("\uD83D\uDE21"); break;
                    case "5": textmood.setText("\uD83E\uDD2F"); break;
                    case "6": textmood.setText("\uD83D\uDE2D"); break;
                }
                title.setText(diary.title);
                content.setText(diary.content);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        updatebtn.setOnClickListener(new View.OnClickListener() { //수정창으로
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("date", date);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                DetailFragment detailFragment = new DetailFragment();
                detailFragment.setArguments(bundle);
                transaction.replace(R.id.container, detailFragment).addToBackStack(null).commit();
            }
        });

        //삭제
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });
        return v;
    }
}
