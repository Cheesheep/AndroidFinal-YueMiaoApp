package com.example.myschool.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.myschool.MainActivity;
import com.example.myschool.NewsArticleContentActivity;
import com.example.myschool.R;
import com.example.myschool.schobject.Notification;

import org.litepal.LitePal;

import java.util.List;


public class NotificationFragment extends Fragment {
    private ListView lv;
    private List<Notification> mList;
    private View globalView;
    private MainActivity mainActivity;
    private String label; //tab显示文章的标签
    public NotificationFragment(String label){
        this.label = label;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 获取view，并且连接到主活动上面
        globalView = inflater.inflate(R.layout.fragment_notification, container, false);
        mainActivity =(MainActivity) getActivity();
        initUI();
        initData();
        initAdapter();
        return globalView;
    }

    private void initAdapter() {
        lv.setAdapter(new NotificationFragment.NewsAdapter());
    }

    private void initData() {
        //根据label标签条件查询处相应的文章
        mList = LitePal.where("label = ?",label).find(Notification.class);
    }

    private void initUI() {
        lv = (ListView) globalView.findViewById(R.id.newsList);
        //设置点击事件监听
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notification notification = mList.get(position);
                //跳转到显示文章内容的活动
                NewsArticleContentActivity.actionStart(mainActivity,notification.getTitle(),notification.getUrl());
            }
        });
    }

    private class NewsAdapter extends BaseAdapter {
        //适配器处理新闻列表
        @Override
        public int getCount() {
            return mList.size();
        }
        @Override
        public Notification getItem(int position) {
            return mList.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NotificationFragment.ViewHolder holder;
            if (convertView == null) {//获取卡片的具体内容
                holder = new NotificationFragment.ViewHolder();
                convertView = View.inflate(mainActivity.getApplicationContext(), R.layout.listview_item, null);
                holder.title = (TextView) convertView.findViewById(R.id.news_title);
                holder.type = (TextView) convertView.findViewById(R.id.news_type);
                holder.date = (TextView) convertView.findViewById(R.id.news_date);
                holder.faculty = (TextView) convertView.findViewById(R.id.news_faculty);

                convertView.setTag(holder);
            } else {
                holder = (NotificationFragment.ViewHolder) convertView.getTag();
            }
            Notification item = getItem(position);
            holder.title.setText(item.getTitle());
            holder.type.setText('[' + item.getType() + ']');
            holder.date.setText(item.getDate());
            holder.faculty.setText(item.getFaculty());
            return convertView;
        }
    }
    private static class ViewHolder {
        TextView title;
        TextView type;
        TextView date;
        TextView faculty;

    }


}