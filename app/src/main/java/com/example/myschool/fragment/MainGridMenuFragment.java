package com.example.myschool.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myschool.MainActivity;
import com.example.myschool.NewsArticleContentActivity;
import com.example.myschool.R;

/*
* 该类管理主页面上方的网格菜单内容，比较简单，
* 主要功能就是做Button的响应事件
* */
public class MainGridMenuFragment extends Fragment implements View.OnClickListener{

    int[] menu_arr = {R.id.grid_bt1,R.id.grid_bt2,R.id.grid_bt3,R.id.grid_bt4,
            R.id.grid_bt5,R.id.grid_bt6,R.id.grid_bt7,R.id.grid_bt8};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_grid_menu,container,false);
        for (int i = 0; i < 8; i++) {
            view.findViewById(menu_arr[i]).setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        FragmentActivity activity = getActivity();
        switch (view.getId()){
            case R.id.grid_bt1: {
                NewsArticleContentActivity.actionStart(activity,"计软主页","https://csse.szu.edu.cn/");
                break;}
            case R.id.grid_bt2: {
                NewsArticleContentActivity.actionStart(activity,"学校简介","https://www.szu.edu.cn/xxgk/xxjj.htm");
                break;}
            case R.id.grid_bt3: {
                NewsArticleContentActivity.actionStart(activity,"本科选课","http://bkxk.szu.edu.cn/xsxkapp/sys/xsxkapp/*default/index.do");
                break;}
            case R.id.grid_bt4: {
                NewsArticleContentActivity.actionStart(activity,"校务信箱","https://www1.szu.edu.cn/mailbox/");
                break;}
            case R.id.grid_bt5: {
                NewsArticleContentActivity.actionStart(activity,"教师事务","https://www1.szu.edu.cn/view.asp?id=12");
                break;}
            case R.id.grid_bt6: {
                NewsArticleContentActivity.actionStart(activity,"学生事务","https://www1.szu.edu.cn/view.asp?id=13");
                break;}
            case R.id.grid_bt7: {
                NewsArticleContentActivity.actionStart(activity,"荔园生活","https://www1.szu.edu.cn/tv/");
                break;}
            case R.id.grid_bt8: {

                break;}
            default:break;
        }
    }
}