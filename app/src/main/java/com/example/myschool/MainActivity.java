package com.example.myschool;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.litepal.LitePal;

import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();//初始化视图
    }
    private void initViews() {
        //设置首页工具栏内容以及样式
        initToolBarView();
        //将页面绑定viewPager，进行设置
       // initViewPager();
        //初始化layout的设置，例如图标，定位
        // initTabLayoutView();
    }
    DrawerLayout drawerLayout;
    private void initToolBarView() {
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        //将图标菜单文件添加到toolbar当中
        myToolbar.inflateMenu(R.menu.toolbar_menu);
        myToolbar.setTitle("Hello News");
        //ToolBar的菜单的点击事件
        myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.logout_button:{
                        //发送注销账户的广播
                        Intent intent = new Intent("com.example.myschool.FORCE_OFFLINE");
                        intent.setPackage(getPackageName());
                        sendBroadcast(intent);
                        break;
                    }
                    default:
                        Toast.makeText(MainActivity.this, "菜单栏功能尚未开发", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        //配置侧滑栏，并且监听点击事件
        myToolbar.setNavigationIcon(R.drawable.personal);
        //打开侧滑栏的监听事件
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        //侧滑栏里面的菜单的监听事件
        NavigationView mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            //侧滑栏中菜单的点击事件
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.logout_button:{
                        //发送注销账户的广播
                        Intent intent = new Intent("com.example.myschool.FORCE_OFFLINE");
                        intent.setPackage(getPackageName());
                        sendBroadcast(intent);
                        break;
                    }
                    default:{
                        Toast.makeText(MainActivity.this, "功能尚未开发，敬请期待", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                //关闭抽屉即关闭侧换此时已经跳转到其他界面，自然要关闭抽屉
                drawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            }
        });
    }
}
