package com.example.myschool.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myschool.MainActivity;
import com.example.myschool.R;
import com.example.myschool.function.RightGridMenuAdapter;

public class MainRightFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_right, container, false);
        initRecyclerView();
        return view;
    }
    private void initRecyclerView(){
        //设置网格布局的recyclerview的菜单
        RecyclerView recyclerView = view.findViewById(R.id.right_recycler_view);
        //通过这个设置一行多列的布局，瀑布式
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        RightGridMenuAdapter adapter = new RightGridMenuAdapter((Activity) getActivity());
        recyclerView.setAdapter(adapter);
    }
}