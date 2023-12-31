package com.example.yuemiaoapp.function;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * 将assets中的db文件拷贝到databases中
 */
public class DatabaseUtil {

    @SuppressLint("SdCardPath")
    public static void packDataBase(Context context){
        // com.example.myschool 是程序的包名，请根据自己的程序调整
        // /data/data/com.example.myschool/databases目录是准备放 SQLite 数据库的地方，也是 Android 程序默认的数据库存储目录
        // 数据库名为 SchoolDemo.db
        String DB_PATH = "/data/data/" + context.getPackageName() +"/databases/";
        String DB_NAME = "SchoolDemo.db";

        // 检查 SQLite 数据库文件是否存在
        try {
            // 得到 assets 目录下我们实现准备好的 SQLite 数据库作为输入流
            InputStream is = context.getAssets().open(DB_NAME);
            // 输出流,在指定路径下生成db文件
            OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
            // 文件写入
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            // 关闭文件流
            os.flush();
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


