package com.example.myschool.function;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

import com.example.myschool.MainActivity;
import com.example.myschool.PersonPageActivity;
import com.example.myschool.R;

/*
* 设置底部导航栏
* */
public class BottomBarUtil {

    static Activity currentActivity;

    public static void initBottomBar(Activity activity){
        currentActivity = activity;
        Button homePage = currentActivity.findViewById(R.id.home_page);
        Button clinicPage = currentActivity.findViewById(R.id.clinic_page);
        Button personPage = currentActivity.findViewById(R.id.person_page);
        Drawable[] drawables;
        //设置当前活动的bar的字体颜色
        if(currentActivity.getClass() == MainActivity.class){
            homePage.setTextColor(currentActivity.getResources().getColor(R.color.colorPrimary));
            //设置button上的图标颜色
            drawables = homePage.getCompoundDrawables();//获取所有的drawable
            drawables[1].setColorFilter(currentActivity.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            homePage.setCompoundDrawables(drawables[0],drawables[1],drawables[2],drawables[3]);
        }else if(currentActivity.getClass() == PersonPageActivity.class){
            personPage.setTextColor(currentActivity.getResources().getColor(R.color.colorPrimary));
            drawables = personPage.getCompoundDrawables();
            drawables[1].setColorFilter(currentActivity.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            personPage.setCompoundDrawables(drawables[0],drawables[1],drawables[2],drawables[3]);
        }else{
            clinicPage.setTextColor(currentActivity.getResources().getColor(R.color.colorPrimary));
        }
        //设置页面跳转功能
        homePage.setOnClickListener(onClickListener);
        personPage.setOnClickListener(onClickListener);
        clinicPage.setOnClickListener(onClickListener);
    }
    static View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.home_page:
                    // 在这里编写homePage的点击事件处理逻辑
                    if(! (view.getContext() instanceof MainActivity) )
                        MainActivity.actionStart(currentActivity);
                    break;
                case R.id.clinic_page:
                    // 在这里编写clinicPage的点击事件处理逻辑
                    break;
                case R.id.person_page:
                    // 在这里编写personPage的点击事件处理逻辑
                    if(! (view.getContext() instanceof PersonPageActivity) )
                        PersonPageActivity.actionStart(currentActivity);
                    break;
            }
        }
    };
}
