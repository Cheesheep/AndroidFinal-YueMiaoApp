package com.example.myschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myschool.schobject.UserInfo;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class LoginActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //链接数据库
        Connector.getDatabase(); //创建数据库
        DatabaseUtil.packDataBase(this); //导入assets的数据库文件
        initUI();

    }
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText accountEdit;
    private EditText passwordEdit;
    private CheckBox rememberPass;
    private List<UserInfo> userInfoList;
    private void initUI(){
        //绑定控件
        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        Button login = (Button) findViewById(R.id.login);
        //通过sharedPreferences获取记住的内容
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password",false);
        if(isRemember){
            //将记住的内容直接设置上去
            accountEdit.setText(pref.getString("account",""));
            passwordEdit.setText(pref.getString("password",""));
            rememberPass.setChecked(true);
        }
        //登录按钮的点击事件
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                //如果账号是admin且密码是123456,就认为登录成功
                userInfoList = LitePal.findAll(UserInfo.class);//获取密码数据库信息
                for(UserInfo info : userInfoList){
                    if(info.getAdmin().equals(account.trim()) && info.getPassword().equals(password.trim())){
                        editor = pref.edit();
                        if(rememberPass.isChecked()){ //检查是否勾选了记住密码
                            //使用Editor来添加数据存储
                            editor.putBoolean("remember_password",true);
                            editor.putString("account",account);
                            editor.putString("password",password);
                        }
                        else{
                            editor.clear();
                        }
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                }
                Toast.makeText(LoginActivity.this,"账号或密码错误！",
                        Toast.LENGTH_SHORT).show();
//                if("admin".equals(account.trim()) && "123456".equals(password.trim())){
//                    editor = pref.edit();
//                    if(rememberPass.isChecked()){ //检查是否勾选了记住密码
//                        //使用Editor来添加数据存储
//                        editor.putBoolean("remember_password",true);
//                        editor.putString("account",account);
//                        editor.putString("password",password);
//                    }
//                    else{
//                        editor.clear();
//                    }
//                    editor.apply();
//                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//                else{
//                    Toast.makeText(LoginActivity.this,"account or password is invalid",
//                            Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}
