package com.example.yuemiaoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsArticleContentActivity extends BaseActivity {

    private String articleUrl = null;
    private WebView webView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_article_content);
        //设置标题栏内容
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            setTitle(getIntent().getStringExtra("title"));
            actionBar.setHomeButtonEnabled(true);//设置左上角是否可以点击
            actionBar.setDisplayHomeAsUpEnabled(true);//添加返回的图标
        }
        //设置文章内容

        articleUrl = getIntent().getStringExtra("url");
        //设置webView内容
        webView = findViewById(R.id.news_webView);
        webView.getSettings().setJavaScriptEnabled(true);
        //返回false可以解决重定向问题
        webView.setWebViewClient(new WebViewClient(){
            //重写这个方法解决重定向的问题
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        webView.loadUrl(articleUrl);
    }

    //重写顶部菜单栏构造方法
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.article_content_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //重写顶部菜单栏点击设置
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //设置返回按钮事件
                finish();
                return true;
            case R.id.article_open_browser:
                //设置浏览器打开事件
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(articleUrl));//跳转到网页
                startActivity(intent);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public static void actionStart(Context context, String title, String url){
        Intent intent = new Intent(context,NewsArticleContentActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }
}