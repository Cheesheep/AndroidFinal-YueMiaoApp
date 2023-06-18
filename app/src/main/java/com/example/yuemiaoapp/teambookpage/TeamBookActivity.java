package com.example.yuemiaoapp.teambookpage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yuemiaoapp.R;
import com.example.yuemiaoapp.teambookpage.camera.CreateQRBitmp;
import com.example.yuemiaoapp.teambookpage.dialog.ImageOptDialog;
import com.example.yuemiaoapp.teambookpage.utils.BitmapUtil;
import com.example.yuemiaoapp.teambookpage.utils.ImageUtil;

/**
 * func: 团体预约界面（扫描二维码加入）
* */
public class TeamBookActivity extends AppCompatActivity {
    private int SCAN_REQUEST_CODE=200;
    private int SELECT_IMAGE_REQUEST_CODE=201;
    protected final int PERMS_REQUEST_CODE = 202;

    private EditText etInput;
    private Bitmap qrCodeBitmap;
    private ImageView ivQrImage;
    private LinearLayout teamRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_book);
        setTitle("Join Team");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);//设置左上角是否可以点击
            actionBar.setDisplayHomeAsUpEnabled(true);//添加返回的图标
        }

        //6.0版本或以上需请求权限
        String[] permissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            requestPermissions(permissions,PERMS_REQUEST_CODE);
        }

        etInput=findViewById(R.id.et_input);
        ivQrImage=findViewById(R.id.iv_qr_image);
        teamRecord = findViewById(R.id.team_record);
        findViewById(R.id.btn_scanning).setOnClickListener(onClickListener);
        findViewById(R.id.generate_qr_code).setOnClickListener(onClickListener);
        teamRecord.setOnClickListener(view -> {
            showToast("功能尚未开发");
        });
        ivQrImage.setOnLongClickListener(v -> {
            longPress();
            return false;
        });
    }

    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_scanning://扫描
                    Intent intent = new Intent(TeamBookActivity.this,ScanActivity.class);
                    startActivityForResult(intent,SCAN_REQUEST_CODE);
                    break;
                case R.id.generate_qr_code://生成二维码
                    String contentString = etInput.getText().toString().trim();
                    if(TextUtils.isEmpty(contentString)){
                        showToast("请输入二维码内容");
                        return ;
                    }
                    Log.i("ansen","输入的内容:"+contentString);
                    Bitmap portrait = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
                    //两个方法，一个不传大小，使用默认
                    qrCodeBitmap = CreateQRBitmp.createQRCodeBitmap(contentString, portrait);
                    ivQrImage.setImageBitmap(qrCodeBitmap);
                    break;
            }
        }
    };

    private void longPress(){
        if(qrCodeBitmap==null){
            showToast("请先生成二维码图片");
            return ;
        }
        ImageOptDialog imageOptDialog=new ImageOptDialog(this);
        imageOptDialog.setCallback(new ImageOptDialog.ImageOptCallback() {
            //识别二维码
            @Override
            public void onIdentifyQrClick() {
                View view = getWindow().getDecorView().getRootView();//找到当前页面的根布局
                view.setDrawingCacheEnabled(true);//禁用绘图缓存
                view.buildDrawingCache();

                Bitmap temBitmap = view.getDrawingCache();
                String result= BitmapUtil.parseQRcode(temBitmap);
                showToast("长按识别二维码结果:"+result);

                //禁用DrawingCahce否则会影响性能 ,而且不禁止会导致每次截图到保存的是缓存的位图
                view.setDrawingCacheEnabled(false);//识别完成之后开启绘图缓存
            }

            //保存图片到本地
            @Override
            public void onSaveImageClick() {
                View view = getWindow().getDecorView().getRootView();//找到当前页面的根布局
                view.setDrawingCacheEnabled(true);//禁用绘图缓存
                view.buildDrawingCache();

                Bitmap temBitmap = view.getDrawingCache();
                ImageUtil.savePicToLocal(temBitmap,TeamBookActivity.this);

                //禁用DrawingCahce否则会影响性能 ,而且不禁止会导致每次截图到保存的是缓存的位图
                view.setDrawingCacheEnabled(false);//识别完成之后开启绘图缓存

                showToast("保存图片到本地成功");
            }
        });
        imageOptDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode==SELECT_IMAGE_REQUEST_CODE){//从图库选择图片
            String[] proj = {MediaStore.Images.Media.DATA};
            // 获取选中图片的路径
            Cursor cursor = this.getContentResolver().query(intent.getData(),proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                String photoPath = cursor.getString(columnIndex);
                String result= BitmapUtil.parseQRcode(photoPath);
                if (!TextUtils.isEmpty(result)) {
                    showToast("从图库选择的图片识别结果:"+result);
                } else {
                    showToast("从图库选择的图片不是二维码图片");
                }
            }
            cursor.close();
        }else if (requestCode == SCAN_REQUEST_CODE && resultCode == RESULT_OK) {
            String input = intent.getStringExtra(ScanActivity.INTENT_EXTRA_RESULT);
            showToast("扫描结果:"+input);
            etInput.setText(input);//将扫描结果放入
        }
    }

    private void showToast(String str){
        Toast.makeText(TeamBookActivity.this,str,Toast.LENGTH_LONG).show();
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {//设置返回按钮事件
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static void actionStart(Context context){
        Intent intent = new Intent(context,TeamBookActivity.class);
        context.startActivity(intent);
    }
}