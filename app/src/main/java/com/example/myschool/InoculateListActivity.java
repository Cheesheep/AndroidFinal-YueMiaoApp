package com.example.myschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myschool.schobject.InoculateInfo;
import com.example.myschool.schobject.UserInfo;

import org.litepal.LitePal;

import java.util.List;

public class InoculateListActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inoculate_list);
        setTitle("Inoculate List");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);//设置返回图标可
            actionBar.setHomeButtonEnabled(true);//设置返回图标是否可以点击
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //加载RecycleView放在Resume中方便刷新数据
        initRecyclerView();
        findViewById(R.id.add_inoculate).setOnClickListener(view -> {
            //增加一条数据记录
            finish();
            InoculateEditActivity.actionStart(this,"add");
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//返回图标对应事件
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initRecyclerView(){
        //设置网格布局的recyclerview的菜单
        RecyclerView recyclerView = findViewById(R.id.right_recycler_view);
        //可以通过这个设置一行多列的布局，瀑布式
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        InoculateListAdapter adapter = new InoculateListAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context,InoculateListActivity.class);
        context.startActivity(intent);
    }
}
class InoculateListAdapter extends RecyclerView.Adapter<InoculateListAdapter.ViewHolder>{
    List<InoculateInfo> inoculateInfoList;
    UserInfo userInfo = LoginActivity.userInfo;
    View cardView;
    Activity activity;
    public InoculateListAdapter(Activity activity){
        this.activity = activity;
        //获取当前用户的受种者记录
        inoculateInfoList = LitePal.where("user_id = ?",String.valueOf(userInfo.getId()))
                .find(InoculateInfo.class);
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView cardText;
        TextView cardBirth;
        TextView cardIdType;
        TextView cardIdentity;
        TextView cardPhone;
        ImageView deleteButton;//用于删除记录的按钮
        TextView cardBindText;//用来显示是否绑定了当前用户
        public ViewHolder(View view){
            super(view);
            cardText =  view.findViewById(R.id.card_name);
            cardBirth =  view.findViewById(R.id.card_birth);
            cardIdType = view.findViewById(R.id.card_id_type);
            cardIdentity = view.findViewById(R.id.card_identify);
            cardPhone = view.findViewById(R.id.card_phone);
            deleteButton = view.findViewById(R.id.card_delete_inoculate);
            cardBindText = view.findViewById(R.id.card_bind_text);
        }
    }

    @Override
    public InoculateListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //将卡片的布局样式加载进来
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_inoculate,viewGroup,false);
        cardView = view;
        return new InoculateListAdapter.ViewHolder(view);
    }
    //将数据与每个单元绑定上
    public static InoculateInfo card_inoculate_info;
    @Override
    public void onBindViewHolder(InoculateListAdapter.ViewHolder viewHolder, int position) {
        InoculateInfo card = inoculateInfoList.get(position);
        //viewHolder.cardImage.setAlpha(0.5f);
        viewHolder.cardText.setText(card.getName());
        viewHolder.cardBirth.setText(card.getBirth());
        viewHolder.cardIdType.setText(card.getId_type());
        //对身份证号进行一个加密处理
        String identity = card.getIdentity();
        String id_encrypt;
        if(identity.length() < 4)
            id_encrypt = "null";
        else
            id_encrypt = identity.substring(0,5)+ "********" + identity.substring(identity.length() - 2);
        viewHolder.cardIdentity.setText(id_encrypt);
        String phone = card.getPhone();
        String phone_encrypt;
        if(phone.length() < 3)
            phone_encrypt = "null";
        else
            phone_encrypt = phone.substring(0,3) + "*****" + phone.substring(phone.length() - 3);
        viewHolder.cardPhone.setText(phone_encrypt);
        if(card.getId() == userInfo.getInoculate_id())
            viewHolder.cardBindText.setVisibility(View.VISIBLE);
        else viewHolder.cardBindText.setVisibility(View.GONE);
        //删除记录按钮监听事件
        viewHolder.deleteButton.setOnClickListener(view -> {
            deleteSure(card);
        });
        //点击整张卡片的监听事件
        cardView.setOnClickListener(view -> {
            //将选中的受种者信息传过去
            InoculateInfoPageActivity.actionStart(activity);
            card_inoculate_info = card;
        });
    }
    //获取数据列表的长度给viewPager，告诉recyclerVIew有多少个子项
    @Override
    public int getItemCount() {
        return inoculateInfoList.size();
    }
    private void deleteSure(InoculateInfo card){
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder (activity);//通过AlertDialog.Builder创建出一个AlertDialog的实例

        dialog.setTitle("警告");//设置对话框的标题
        dialog.setMessage("确定删除该记录吗");//设置对话框的内容
        dialog.setCancelable(true);//设置对话框是否可以取消
        dialog.setPositiveButton("确认", new DialogInterface. OnClickListener() {//确定按钮的点击事件

            @Override
            public void onClick(DialogInterface dialog, int which) {
                LitePal.delete(InoculateInfo.class,card.getId());
                activity.finish();
                InoculateListActivity.actionStart(activity);
                Toast.makeText(activity, "删除记录成功", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface. OnClickListener() {//取消按钮的点击事件
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();//显示对话框
    }
}
