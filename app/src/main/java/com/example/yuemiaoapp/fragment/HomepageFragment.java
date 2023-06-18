package com.example.yuemiaoapp.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.yuemiaoapp.TeamBookActivity;
import com.example.yuemiaoapp.VaccineCardActivity;
import com.example.yuemiaoapp.NewsArticleContentActivity;
import com.example.yuemiaoapp.R;
import com.example.yuemiaoapp.function.NewsFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * 主页面左侧的碎片
 * 也是手机模式下的主页面所有内容
 */

public class HomepageFragment extends Fragment {

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
        view = inflater.inflate(R.layout.fragment_homepage, container, false);
        //设置首页工具栏内容以及样式
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
    }


    int[] menu_arr = {R.id.grid_bt1,R.id.grid_bt2,R.id.grid_bt3,R.id.grid_bt4,
            R.id.grid_bt5,R.id.grid_bt6,R.id.grid_bt7};
    private void initGridMenu(){

        FragmentActivity activity = getActivity();
        for (int i = 0; i < 7; i++) {
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
                        TeamBookActivity.actionStart(activity);
                        break;}
                    case R.id.grid_bt7: {
                        VaccineCardActivity.actionStart(activity);
                        break;}
                    default:
                        Toast.makeText(activity, "功能尚未开发", Toast.LENGTH_SHORT).show();
                        break;
                }
            });
        }
    }
}