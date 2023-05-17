package com.example.myschool.function;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myschool.fragment.NotificationFragment;


/**
 *
 * 用于TabLayout可以点击显示不同的新闻列表页面
 */
public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"最新", "推荐", "科研"};

    public NewsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //选择不同的菜单返回不同的Fragment页面
        if (position == 1) {
            return new NotificationFragment("top");
        } else if (position == 2) {
            return new NotificationFragment("sci");
        }
        return new NotificationFragment("latest");
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}

