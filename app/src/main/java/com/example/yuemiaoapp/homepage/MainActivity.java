package com.example.yuemiaoapp.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.yuemiaoapp.common.BaseActivity;
import com.example.yuemiaoapp.common.NewsArticleContentActivity;
import com.example.yuemiaoapp.R;
import com.example.yuemiaoapp.function.BottomBarUtil;
import com.example.yuemiaoapp.common.ForceOffLineReceiver;

/**
 * 主页面，单页模式放有左右碎片
 * 双页模式下首先加载左边碎片
 */

public class MainActivity extends BaseActivity {
    DrawerLayout drawerLayout;
    Toolbar myToolbar;

    //主活动的功能比较少，因为一部分都放在Fragment碎片当中
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置顶部菜单栏的按钮，监听事件等
        initToolBarView();
        NavigationUtil navigationUtil = new NavigationUtil(this,myToolbar,drawerLayout);
        navigationUtil.initMainNavigation();
        BottomBarUtil.initBottomBar(MainActivity.this);
    }

    private void initToolBarView() {
        myToolbar = findViewById(R.id.my_toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        //将图标菜单文件添加到toolbar当中
        myToolbar.inflateMenu(R.menu.toolbar_menu);
        myToolbar.setTitle("YueMiao");
        //ToolBar的菜单的点击事件
        myToolbar.setOnMenuItemClickListener(item -> { //toolbar菜单点击事件
            switch (item.getItemId()){
                case R.id.szu_home:{
                    NewsArticleContentActivity.actionStart(MainActivity.this,"Home","https://www1.szu.edu.cn/");
                    break;
                }
                case R.id.logout_button:{
                    ForceOffLineReceiver.sendLogoutBroadCast(MainActivity.this);
                    break;
                }
                default:
                    Toast.makeText(MainActivity.this, "菜单栏功能尚未开发", Toast.LENGTH_SHORT).show();
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

    //该方法用于接收来自Login登录时候传入的用户数据
    public static void actionStart(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }
}
