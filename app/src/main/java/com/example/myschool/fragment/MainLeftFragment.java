package com.example.myschool.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.text.BoringLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.myschool.NewsArticleContentActivity;
import com.example.myschool.R;
import com.example.myschool.function.NewsFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * 主页面左侧的碎片
 * 也是手机模式下的主页面所有内容
 */

public class MainLeftFragment extends Fragment {

    View view;
    Boolean isTwoPane;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.right_frag) != null)
            isTwoPane = false;//判断是否处于双页模式（手机屏幕）
        else isTwoPane = true; //单页模式为Ipad
        if(!isTwoPane){//若此时是平板模式，不是双页
            //将左侧上方的菜单栏隐藏
            GridLayout gridLayout = view.findViewById(R.id.main_grid_menu);
            gridLayout.setVisibility(View.GONE);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //获取碎片视图
        view = inflater.inflate(R.layout.fragment_main_left, container, false);
        //设置首页工具栏内容以及样式
        //这里如果在onStart设置会报错Drawer为空
        // 因为这个时候可能Fragment已经构建好了
        //初始化上方的网格菜单
        initGridMenu();
        //将页面绑定viewPager，进行设置
        initViewPager();
        //初始化layout的设置，例如图标，定位
        initTabLayoutView();
        return view;
    }

    private ViewPager mViewPager;
    private void initViewPager() {
        mViewPager= (ViewPager) view.findViewById(R.id.viewPager);
        NewsFragmentPagerAdapter myFragmentPagerAdapter = new NewsFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);
    }
    private void initTabLayoutView() {
        //将TabLayout与ViewPager绑定在一起
        TabLayout mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        //设置更多文章的点击事件
        ImageView imageView = view.findViewById(R.id.more_page_bt);
        imageView.setOnClickListener(view -> {
            //点击跳转到公文通
            NewsArticleContentActivity.actionStart(getActivity(),"Notification","https://www1.szu.edu.cn/board/");
        });
    }


    int[] menu_arr = {R.id.grid_bt1,R.id.grid_bt2,R.id.grid_bt3,R.id.grid_bt4,
            R.id.grid_bt5,R.id.grid_bt6,R.id.grid_bt7,R.id.grid_bt8};
    private void initGridMenu(){

        FragmentActivity activity = getActivity();
        for (int i = 0; i < 8; i++) {
            view.findViewById(menu_arr[i]).setOnClickListener(click_view -> {
                switch (click_view.getId()){
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
            });
        }
    }
}