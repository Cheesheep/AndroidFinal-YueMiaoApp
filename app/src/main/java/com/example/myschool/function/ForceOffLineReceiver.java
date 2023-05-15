package com.example.myschool.function;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AlertDialog;

import com.example.myschool.LoginActivity;
import com.example.myschool.MainActivity;

//用于广播下线
public class ForceOffLineReceiver extends BroadcastReceiver {
    //该监听器包括了网络监听何强制下线监听
    @Override
    public void onReceive(Context context, Intent intent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);//构建对话框
        builder.setTitle("警告");
        String action = intent.getAction();
        if (action.equals("com.example.myschool.FORCE_OFFLINE")) {
            // Handle the FORCE_OFFLINE broadcast
            builder.setMessage("你已经被强制下线，请重新登录！");
            builder.setCancelable(false);//讲对话框设置为不可取消
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCollector.finishAll();//校徽所有活动
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent); //重新启动LoginActivity
                }
            });
        }
        else if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            // Handle the CONNECTIVITY_CHANGE broadcast
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable())
                return;
            else
                builder.setMessage("网络发生异常，请检查网络！");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
        }
        builder.show();//令该对话框可视化
    }

    //确认是否注销的对话框和退出确认的广播发送
    public static void sendLogoutBroadCast(MainActivity activity){
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder (activity);//通过AlertDialog.Builder创建出一个AlertDialog的实例

        dialog.setTitle("注销");//设置对话框的标题
        dialog.setMessage("确定退出校园登录吗");//设置对话框的内容
        dialog.setCancelable(false);//设置对话框是否可以取消
        dialog.setPositiveButton("确认", new DialogInterface. OnClickListener() {//确定按钮的点击事件

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //发送注销账户的广播
                Intent intent = new Intent("com.example.myschool.FORCE_OFFLINE");
                intent.setPackage(activity.getPackageName());
                activity.sendBroadcast(intent);
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
