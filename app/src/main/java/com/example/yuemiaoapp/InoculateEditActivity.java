package com.example.yuemiaoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yuemiaoapp.schobject.InoculateInfo;

public class InoculateEditActivity extends BaseActivity {
    EditText nameEdit;
    EditText idTypeEdit;
    EditText idEdit;
    EditText birthEdit;
    EditText sexEdit;
    EditText phoneEdit;
    EditText locEdit;
    EditText detailLocEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inoculate_edit);
        setTitle("Edit Inoculate Info");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);//设置返回图标可
            actionBar.setHomeButtonEnabled(true);//设置返回图标是否可以点击
        }
        initUI();
    }
    private void initUI(){
        nameEdit = findViewById(R.id.ino_edit_name);
        idTypeEdit = findViewById(R.id.ino_edit_id_type);
        idEdit = findViewById(R.id.ino_edit_identity);
        birthEdit = findViewById(R.id.ino_edit_birth);
        sexEdit = findViewById(R.id.ino_edit_sex);
        phoneEdit = findViewById(R.id.ino_edit_phone);
        locEdit = findViewById(R.id.ino_edit_loc);
        detailLocEdit = findViewById(R.id.ino_edit_detail_loc);
        //若是编辑信息则放入原本的信息，进行编辑修改
        String type = getIntent().getStringExtra("type");
        InoculateInfo card = InoculateListAdapter.card_inoculate_info;
        if(type.equals("update")){
            nameEdit.setText(card.getName());
            idTypeEdit.setText(card.getId_type());
            idEdit.setText(card.getIdentity());
            birthEdit.setText(card.getBirth());
            sexEdit.setText(card.getSex());
            phoneEdit.setText(card.getPhone());
            locEdit.setText(card.getLocation());
            detailLocEdit.setText(card.getLoc_detail());
        }
        //按键确定新增数据
        Button button = findViewById(R.id.ino_add_sure_button);
        button.setOnClickListener(view -> {
            InoculateInfo info = new InoculateInfo();
            info.setName(nameEdit.getText().toString());
            info.setId_type(idTypeEdit.getText().toString());
            info.setIdentity(idEdit.getText().toString());
            info.setBirth(birthEdit.getText().toString());
            info.setSex(sexEdit.getText().toString());
            info.setPhone(phoneEdit.getText().toString());
            info.setLocation(locEdit.getText().toString());
            info.setLoc_detail(detailLocEdit.getText().toString());
            //不要忘记加上当前的用户ID
            info.setUser_id(LoginActivity.userInfo.getId());
            if(type.equals("update")){
                info.update(card.getId());
                Toast.makeText(this, "更新成功！", Toast.LENGTH_SHORT).show();
            }else {
                info.save();//新增一条数据
                Toast.makeText(this, "增加成功！", Toast.LENGTH_SHORT).show();
            }
            finish();//结束当前活动
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
    public static void actionStart(Context context,String type){
        Intent intent = new Intent(context,InoculateEditActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
}