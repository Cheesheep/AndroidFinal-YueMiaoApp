package com.example.myschool.function;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myschool.LoginActivity;
import com.example.myschool.MainActivity;
import com.example.myschool.R;
import com.example.myschool.UserInfoPageActivity;
import com.example.myschool.fragment.MainLeftFragment;
import com.google.android.material.navigation.NavigationView;



public class NavigationUtil {
    MainActivity activity;
    //View fragmentView;
    Toolbar myToolbar;
    DrawerLayout drawerLayout;
    NavigationView mNavigationView;
    //导航栏的头部部分视图，因为该视图在另一个单独的文件
    View headerView;
    public NavigationUtil(MainActivity activity,Toolbar myToolbar,DrawerLayout drawerLayout){
        this.activity = activity;
        this.myToolbar = myToolbar;
        this.drawerLayout = drawerLayout;
        mNavigationView = activity.findViewById(R.id.nav_view);
        headerView = mNavigationView.getHeaderView(0);
    }
    public void initMainNavigation(){
       // headerView.setBackgroundResource(R.drawable.color_card03);
        //初始化用户信息
        initUserInfo();
        //初始化点击事件
        initNavClick();

    }
    private void initNavClick(){
        //侧滑栏里面的菜单的监听事件
        //侧滑栏中菜单的点击事件
        mNavigationView.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.logout_button:{
                    //发送注销账户的广播
                    ForceOffLineReceiver.sendLogoutBroadCast(activity);
                    break;
                }
                case R.id.nav_item_user://和下一个共用
                {
                    UserInfoPageActivity.actionStart(activity);
                    break;
                }
                default:{
                    Toast.makeText(activity, "功能尚未开发，敬请期待", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            //关闭抽屉即关闭侧换此时已经跳转到其他界面，自然要关闭抽屉
            drawerLayout.closeDrawer(Gravity.LEFT);
            return true;
        });
    }
    //对应每个用户的头像存储位置，该信息存储在userinfo的ImgUrl当中
    int[] imgArr = {R.drawable.admin,R.drawable.dog,R.drawable.mokelo};
    private void initUserInfo(){
        //获取可编辑的用户信息
        //注意！：这里不能直接用Activity来findViewById是
        // 因为下面的这些控件不在activity_main当中，而在left_fragment的nav_header当中
        ImageView imageView = headerView.findViewById(R.id.icon_image);
        TextView nickName = headerView.findViewById(R.id.nav_nickname);
        TextView grade = headerView.findViewById(R.id.nav_grade);
        TextView major = headerView.findViewById(R.id.nav_major);
        //获取来自LoginActivity传过来的数据
        nickName.setText(LoginActivity.userInfo.getNickname());
        grade.setText(LoginActivity.userInfo.getGrade());
        major.setText(LoginActivity.userInfo.getMajor());
        int imgUrl = Integer.parseInt(LoginActivity.userInfo.getImgUrl());
        imageView.setImageResource(imgArr[imgUrl]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfoPageActivity.actionStart(activity);
            }
        });
    }
}
