package com.example.myschool;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myschool.function.ForceOffLineReceiver;
import com.example.myschool.function.NavigationUtil;
import com.example.myschool.function.NewsFragmentPagerAdapter;
import com.example.myschool.schobject.UserInfo;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends BaseActivity {
    DrawerLayout drawerLayout;
    Toolbar myToolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();//初始化视图

    }


    private void initViews() {
        //设置首页工具栏内容以及样式
        initToolBarView();
        //设置侧边栏界面
        NavigationUtil navigationUtil = new NavigationUtil(this,myToolbar,drawerLayout);
        navigationUtil.initMainNavigation();
        //将页面绑定viewPager，进行设置
        initViewPager();
        //初始化layout的设置，例如图标，定位
        initTabLayoutView();
    }
    private ViewPager mViewPager;
    private void initViewPager() {
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        NewsFragmentPagerAdapter myFragmentPagerAdapter = new NewsFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);
    }
    private void initTabLayoutView() {
        //将TabLayout与ViewPager绑定在一起
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initToolBarView() {
        myToolbar = findViewById(R.id.my_toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        //将图标菜单文件添加到toolbar当中
        myToolbar.inflateMenu(R.menu.toolbar_menu);
        myToolbar.setTitle("SZU");
        //ToolBar的菜单的点击事件
        myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) { //toolbar菜单点击事件
                switch (item.getItemId()){
                    case R.id.logout_button:{
                        ForceOffLineReceiver.sendLogoutBroadCast(MainActivity.this);
                        break;
                    }
                    default:
                        Toast.makeText(MainActivity.this, "菜单栏功能尚未开发", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
    public static void actionStart(Context context, UserInfo userInfo){
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra("nickname",userInfo.getNickname());
        intent.putExtra("grade",userInfo.getGrade());
        intent.putExtra("major",userInfo.getMajor());
        intent.putExtra("url",userInfo.getImgUrl());
        context.startActivity(intent);
    }
}
