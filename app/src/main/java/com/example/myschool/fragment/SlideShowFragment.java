package com.example.myschool.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myschool.R;

import java.util.ArrayList;
import java.util.Objects;

/**
* func ： 轮播图碎片实现
* */
public class SlideShowFragment extends Fragment {
    private View view;
    private ViewPager viewPager;  //轮播图模块
    // 图片资源id数组
    private final int[] mImg = new int[]{
                R.drawable.poster01,
                R.drawable.poster02,
                R.drawable.poster03,
                R.drawable.poster04,
                R.drawable.poster05
    };
    // 文本描述
    private final String[] mDec = new String[]{
                "听从指挥，抗击疫情",
                "有序测量体温，预防新冠",
                "共筑健康屏障",
                "及时接种疫苗",
                "抗击疫苗"
    };
    private final int[] mImg_id = new int[]{
        R.id.pager_img1,
                R.id.pager_img2,
                R.id.pager_img3,
                R.id.pager_img4,
                R.id.pager_img5
    };
    private ArrayList<ImageView> mImgList;
    private LinearLayout ll_dots_container;
    private TextView loop_dec;
    private int previousSelectedPosition = 0;
    boolean isRunning = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_slide_show, container, false);
        initLoopView();  //实现轮播图
        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    private void initLoopView() {
        viewPager = (ViewPager)view.findViewById(R.id.loopviewpager);
        ll_dots_container = (LinearLayout)view.findViewById(R.id.ll_dots_loop);
        loop_dec = (TextView)view.findViewById(R.id.loop_dec);


        // 初始化要展示的5个ImageView
        mImgList = new ArrayList<ImageView>();
        ImageView imageView;
        View dotView;
        LinearLayout.LayoutParams layoutParams;
        for(int i=0;i<mImg.length;i++){
            //初始化要显示的图片对象
            imageView = new ImageView(view.getContext());
            imageView.setBackgroundResource(mImg[i]);
            imageView.setId(mImg_id[i]);
            imageView.setOnClickListener(pagerOnClickListener);
            mImgList.add(imageView);
            //加引导点
            dotView = new View(view.getContext());
            dotView.setBackgroundResource(R.drawable.dot);
            //引导点大小
            layoutParams = new LinearLayout.LayoutParams(60,60);
            if(i!=0){
                layoutParams.leftMargin=10;
            }
            //设置默认所有都不可用
            dotView.setEnabled(false);
            ll_dots_container.addView(dotView,layoutParams);
        }

        ll_dots_container.getChildAt(0).setEnabled(true);
        loop_dec.setText(mDec[0]);
        previousSelectedPosition=0;
        //设置适配器
        viewPager.setAdapter(new LoopViewAdapter(mImgList));
        // 把ViewPager设置为默认选中Integer.MAX_VALUE / t2，从十几亿次开始轮播图片，达到无限循环目的;
        int m = (Integer.MAX_VALUE / 2) %mImgList.size();
        int currentPosition = Integer.MAX_VALUE / 2 - m;
        viewPager.setCurrentItem(currentPosition);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                int newPosition = i % mImgList.size();
                loop_dec.setText(mDec[newPosition]);
                ll_dots_container.getChildAt(previousSelectedPosition).setEnabled(false);
                ll_dots_container.getChildAt(newPosition).setEnabled(true);
                previousSelectedPosition = newPosition;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        // 开启轮询
        new Thread(){
            public void run(){
                while(isRunning){
                    try{
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //下一条
                    if(!isRunning)
                        break;
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                        }
                    });
                }
            }
        }.start();

    }
    View.OnClickListener pagerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.pager_img1:
                    Toast.makeText(view.getContext(), "图片1被点击", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pager_img2:
                    Toast.makeText(view.getContext(), "图片2被点击", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pager_img3:
                    break;
                case R.id.pager_img4:
                    break;
                case R.id.pager_img5:
                    break;
            }
        }
    };

    static class LoopViewAdapter extends PagerAdapter {

        private ArrayList<ImageView> imageViewList;

        public LoopViewAdapter(ArrayList<ImageView> mImgList){
            imageViewList = mImgList;
        }

        // 1. 返回要显示的条目内容, 创建条目
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            // container: 容器: ViewPager
            // position: 当前要显示条目的位置 0 -> 4
            //newPosition = position % 5
            int newPosition = position % imageViewList.size();
            ImageView img = imageViewList.get(newPosition);
            // a. 把View对象添加到container中
            container.addView(img);
            // b. 把View对象返回给框架, 适配器
            return img;
        }
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;   //返回一个无限大的值，可以 无限循环!
        }
        /**
         * 判断是否使用缓存, 如果返回的是true, 使用缓存. 不去调用instantiateItem方法创建一个新的对象
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o ;
        }
    }

}