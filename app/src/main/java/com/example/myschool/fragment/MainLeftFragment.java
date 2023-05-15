package com.example.myschool.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myschool.MainActivity;
import com.example.myschool.NewsArticleContentActivity;
import com.example.myschool.R;
import com.example.myschool.function.ForceOffLineReceiver;
import com.example.myschool.function.NavigationUtil;
import com.example.myschool.function.NewsFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * 主页面左侧的碎片
 * 也是手机模式下的主页面所有内容
 */

public class MainLeftFragment extends Fragment {
    DrawerLayout drawerLayout;
    Toolbar myToolbar;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //获取碎片视图
        view = inflater.inflate(R.layout.fragment_main_left, container, false);
        //设置首页工具栏内容以及样式
        //这里如果在onStart设置会报错Drawer为空
        // 因为这个时候可能Fragment已经构建好了
        initToolBarView();
        //初始化上方的网格菜单
        initGridMenu();
        //将页面绑定viewPager，进行设置
        initViewPager();
        //初始化layout的设置，例如图标，定位
        initTabLayoutView();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //由于在建立Navigation的时候需要发送Fragment
        //而fragment的view在onCreateView当中是未构建好的，用不了
        NavigationUtil navigationUtil = new NavigationUtil(this,myToolbar,drawerLayout);
        navigationUtil.initMainNavigation();
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

    private void initToolBarView() {
        myToolbar = view.findViewById(R.id.my_toolbar);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        //将图标菜单文件添加到toolbar当中
        myToolbar.inflateMenu(R.menu.toolbar_menu);
        myToolbar.setTitle("SZU");
        //ToolBar的菜单的点击事件
        myToolbar.setOnMenuItemClickListener(item -> { //toolbar菜单点击事件
            switch (item.getItemId()){
                case R.id.logout_button:{
                    ForceOffLineReceiver.sendLogoutBroadCast((MainActivity) getActivity());
                    break;
                }
                default:
                    Toast.makeText(getActivity(), "菜单栏功能尚未开发", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        });
        //设置侧滑栏图标
        myToolbar.setNavigationIcon(R.drawable.personal);
        //打开侧滑栏的监听事件
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
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