package com.example.diary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CalendarFragment extends Fragment {

    MaterialCalendarView calendarView;
    TextView textView;
    static CalendarDay selectedDay = null;
    static boolean Selected;
    String DATE,month, day,dot,sdate;
    int year,smonth,sday,y,d,m;
    Context context;
    DetailFragment detailFragment = new DetailFragment();
    DetailViewFragment detailViewFragment=new DetailViewFragment();

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v=inflater.inflate(R.layout.activity_calendar, container, false);
        calendarView=(MaterialCalendarView)v.findViewById(R.id.mycalendar);
        textView=(TextView)v.findViewById(R.id.textview);
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String getTime = sdf.format(date);
        context=container.getContext();

        if(getArguments()!=null){
            dot=getArguments().getString("dot");
            sdate=getArguments().getString("date");
            Drawing();
            Log.e("getArguments",dot);
        }

        Drawing();

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                if(selectedDay == date){
                    selected = false;
                    Selected = selected;
                }
                else{
                    selected = true;
                    Selected = selected;
                }

                selectedDay = date;
                DATE = selectedDay.toString();
                String[] parsedDATA = DATE.split("[{]");
                parsedDATA = parsedDATA[1].split("[}]");
                parsedDATA = parsedDATA[0].split("-");

                smonth = Integer.parseInt(parsedDATA[1])+1;

                if(smonth<10){
                    month="0"+smonth;
                }else{
                    month=String.valueOf(smonth);
                }
                if(Integer.parseInt(parsedDATA[2])<10){
                    day="0"+parsedDATA[2];
                }else{
                    day=parsedDATA[2];
                }

                String selectedDate= parsedDATA[0]+month+day;

                if(Integer.parseInt(selectedDate) > Integer.parseInt(getTime)){ //미래의 날짜를 선택했을 때
                    AlertDialog.Builder builder =new AlertDialog.Builder(context);
                    builder.setTitle("Diary").setMessage("미래는 기록할 수 없습니다.");
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
                }else{ //아닐때~~
                    Log.e("date",selectedDate);
                    Bundle bundle = new Bundle();
                    bundle.putString("date", selectedDate);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                    try{
                        Diary diary=((MainActivity)getActivity()).db.diaryDao().loadAllByDate(selectedDate);
                        if(diary==null){  //날짜가 없으면 작성창으로
                            detailFragment.setArguments(bundle);
                            transaction.replace(R.id.container, detailFragment).addToBackStack(null).commit();
                        }else{  //있으면 조회창으로
                            detailViewFragment.setArguments(bundle);
                            transaction.replace(R.id.container, detailViewFragment).addToBackStack(null).commit();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        return v;
    }

    public void Drawing(){ //데이터베이스에 있는 날짜 점 찍깅,,
        List<Diary> datelist =((MainActivity)getActivity()).db.diaryDao().datelist();
        for(int i=0; i < datelist.size(); i++){
            String dbdate=datelist.get(i).date;
            y =Integer.parseInt(dbdate.substring(0,4));
            m =Integer.parseInt(dbdate.substring(4,6));
            d =Integer.parseInt(dbdate.substring(6,8));
            calendarView.addDecorators(new EventDecorator(Color.RED, Collections.singleton(CalendarDay.from(y,m-1,d))));
        }
    }
}
