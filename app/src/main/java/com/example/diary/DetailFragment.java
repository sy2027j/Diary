package com.example.diary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    TextView editText;
    private RecyclerView mRecyclerView;
    private ArrayList<Item> mList;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    RecyclerViewAdapter adapter;
    EditText editTitle;
    EditText editContent;
    private Context context;
    String date;
    int result=0;
    String title, content;
    String DATE;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v=inflater.inflate(R.layout.activity_detail, container, false);

        editText=(TextView) v.findViewById(R.id.editdate); //선택한 날짜 나타내기
        mRecyclerView=(RecyclerView) v.findViewById(R.id.semotion); //이모티콘 리사이클러뷰
        editTitle=(EditText)v.findViewById(R.id.edittitle); //타이틀 작성
        editContent=(EditText)v.findViewById(R.id.editcontent);
        Button btn =(Button)v.findViewById(R.id.okbtn); //저장 버튼
        Button cancelBtn =(Button)v.findViewById(R.id.cancelbtn); //취소 버튼
        mList = new ArrayList<>();
        context=container.getContext();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter=new RecyclerViewAdapter(getContext());

        adapter.addItem(new Item("\uD83E\uDD70"));
        adapter.addItem(new Item("☺"));
        adapter.addItem(new Item("\uD83D\uDE10"));
        adapter.addItem(new Item("\uD83D\uDE14"));
        adapter.addItem(new Item("\uD83D\uDE21"));
        adapter.addItem(new Item("\uD83E\uDD2F"));
        adapter.addItem(new Item("\uD83D\uDE2D"));
        mRecyclerView.setAdapter(adapter);

        if(getArguments()!=null){
            Log.e("getArguments","date");
            date=getArguments().getString("date");
            String dates=date.substring(0,4)+" / "+date.substring(4,6)+" / "+date.substring(6,8);
            editText.setText(dates);
        }else{
            Log.e("error","no date");
        }

        try{
            Diary diary=((MainActivity)getActivity()).db.diaryDao().loadAllByDate(date);
            if(diary != null && !diary.equals("")){
                adapter.selectedPosition = Integer.parseInt(diary.mood);
                editTitle.setText(diary.title);
                editContent.setText(diary.content);
                result=1;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mood= adapter.getSeletedImg();
                title=editTitle.getText().toString();
                content=editContent.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("date", date);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                DetailViewFragment detailViewFragment = new DetailViewFragment();
                CalendarFragment calendarFragment = new CalendarFragment();

                detailViewFragment.setArguments(bundle);

                AlertDialog.Builder builder =new AlertDialog.Builder(context);
                if(mood==""){
                    Toast.makeText(context, "Today's Mood 를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    if(title.length() !=0){
                        if(content.length() != 0){
                            if(result==1){ //수정
                                ((MainActivity)getActivity()).db.diaryDao().diaryUpdate(title,content,mood,date);
                                builder.setTitle("Diary").setMessage("수정되었습니다.");
                                AlertDialog alertDialog=builder.create();
                                alertDialog.show();
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(1000);

                                            alertDialog.cancel();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                                transaction.replace(R.id.container, detailViewFragment).addToBackStack(null).commit();
                                Log.e("update","ok");
                            }else{ //삽입
                                ((MainActivity)getActivity()).db.diaryDao().insert(new Diary(date,title,content,mood));
                                builder.setTitle("Diary").setMessage("하루가 기록되었습니다.");
                                AlertDialog alertDialog=builder.create();
                                alertDialog.show();
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(1000);

                                            alertDialog.cancel();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                                bundle.putString("dot", "red");
                                calendarFragment.setArguments(bundle);
                                transaction.replace(R.id.container, calendarFragment).addToBackStack(null).commit();
                                Log.e("insert","ok");
                            }
                        }else{
                            Toast.makeText(context, "Content을 작성해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(context, "Title을 작성해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).onBackPressed();
            }
        });

        return v;
    }
}
