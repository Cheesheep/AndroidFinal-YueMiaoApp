package com.example.yuemiaoapp.clinicmappage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.yuemiaoapp.common.BaseActivity;
import com.example.yuemiaoapp.R;
import com.example.yuemiaoapp.function.BottomBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
public class ClinicMapActivity extends BaseActivity implements ClinicListAdapter.OnItemClickListener,
        OnRefreshLoadMoreListener, BaiduMap.OnMapClickListener {
    MapView mapview;
    EditText contentSeach;
    RecyclerView locationRecy;
    TextView footerHintText;
    SmartRefreshLayout refreshlayout;
    LinearLayout linTop;
    private BaiduMap baiduMap;
    private String mCity = "深圳市";

    private double mLatitude = 0;
    private double mLongitude = 0;
    private PoiInfo mLocation;
    private PoiSearch mPoiSearch;
    private ClinicListAdapter mClinicListAdapter;
    private int page = 0;
    private LocationClient mLocationClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);
        setContentView(R.layout.activity_clinic_map);
        setTitle("Baidu Map");
        //ButterKnife.bind(this);
        initUI();
        initData();
    }
    private void initUI(){
        mapview = findViewById(R.id.mapview);
        contentSeach = findViewById(R.id.content_seach);
        locationRecy = findViewById(R.id.location_recy);
        footerHintText = findViewById(R.id.footer_hint_text);
        refreshlayout = findViewById(R.id.refreshlayout);
        linTop = findViewById(R.id.lin_top);
        //按键初始化
        initButton();
        //初始化底部导航栏
        BottomBarUtil.initBottomBar(ClinicMapActivity.this);
    }

    private void initData() {
        checkPermission();//先检查权限
        refreshlayout.setEnableRefresh(false);
        refreshlayout.setEnableLoadMore(true);
        refreshlayout.setOnRefreshLoadMoreListener(this);
        mClinicListAdapter = new ClinicListAdapter(ClinicMapActivity.this);
        locationRecy.setLayoutManager(new LinearLayoutManager(ClinicMapActivity.this));
        locationRecy.setAdapter(mClinicListAdapter);
        mClinicListAdapter.setOnItemClickListener(this);
        baiduMap = mapview.getMap();
        baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15.0f));//缩放
        baiduMap.setOnMapClickListener(this);
        baiduMap.setMyLocationEnabled(true);//开启定位图层
        mapview.setLongClickable(true);
        //初始化搜索栏
        initEdit();
        //初始化poi搜索
        initpoiSearch();
        //初始化位置信息
        initLocation();

    }

    private void checkPermission() {
        List<String> permissions = new ArrayList<>();
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (permissions.size() != 0) {
            ActivityCompat.requestPermissions(this, permissions.toArray(new String[0]), 100);
        }
    }
    private void initEdit() {
        contentSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //回车触发搜索事件，提取editView的内容
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String input = contentSeach.getText().toString();
                    if (!TextUtils.isEmpty(input)) {
                        mClinicListAdapter.clear();
                        initSeach(input, 0);
                        BaiduMapUtils.closeKeybord(ClinicMapActivity.this);
                    }
                    return true;
                }
                return false;
            }
        });
    }
    private static final int PERMISSION_REQUEST_CODE = 100;
    private double native_latitude;//存放用户的地址
    private double native_longitude;
    private void initLocation() {
        //client的各种配置信息
        mLocationClient = BaiduMapUtils.getLocationClient(ClinicMapActivity.this);
        //添加监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        mLocationClient.start();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        BaiduMapUtils.reverseGeoParse(latLng.longitude, latLng.latitude, new OnGetGeoCoderResultListener() {
            //获取正向解析结果时执行函数
            @Override
            public void onGetGeoCodeResult(GeoCodeResult arg0) {
            }

            //获取反向解析结果时执行函数
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                } else {
                    if (!TextUtils.isEmpty(result.getAddress())) {
                        mClinicListAdapter.clear();
                        page = 0;
                        initSeach(result.getAddress(), 0);
                    }
                    //点击地图任意位置显示该地址位置
                    showMap(latLng.latitude, latLng.longitude);
                }
            }
        });
    }

    @Override
    public void onMapPoiClick(MapPoi mapPoi) {
        if (!TextUtils.isEmpty(mapPoi.getName())) {
            mClinicListAdapter.clear();
            page = 0;
            initSeach(mapPoi.getName(), 0);
        }
        showMap(mapPoi.getPosition().latitude, mapPoi.getPosition().longitude);
    }
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.d("测试","获取当前定位");
            if (!TextUtils.isEmpty(location.getCity())) {
                mCity = location.getCity();
            }
            native_latitude = location.getLatitude();
            native_longitude = location.getLongitude();
            if (!TextUtils.isEmpty(location.getStreet())) {
                mClinicListAdapter.clear();
                initSeach(location.getStreet(), 0);
            }
            showMap(native_latitude, native_longitude);
//            mLocationClient.stop();
        }
    }

    /**
     * 设置地图定位标识
     *
     * @param latitude 经度
     * @param longtitude  纬度
     */
    protected void showMap(double latitude, double longtitude) {
        LatLng lng = new LatLng(latitude, longtitude);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(lng)
                .icon(bitmap)// 设置 Marker 覆盖物的图标
                .zIndex(9)// 设置 marker 覆盖物的 zIndex
                .draggable(true);
        baiduMap.clear();//清除覆盖物
        baiduMap.addOverlay(markerOptions);//添加
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(lng, 18f);
        baiduMap.animateMapStatus(u);
    }

    @Override
    public void onItem(PoiInfo PoiInfo, int position) {
        mLocation = PoiInfo;
        mClinicListAdapter.setmPosition(position);
        showMap(PoiInfo.getLocation().latitude, PoiInfo.getLocation().longitude);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        initSeach(contentSeach.getText().toString().trim(), page);
        refreshLayout.finishLoadMore();

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    private void initSeach(String keyword, int page) {
        //搜索信息，这里有默认显示的城市
        mPoiSearch.searchInCity(new PoiCitySearchOption()
                .city(mCity) //必填
                .keyword(keyword) //必填
                .cityLimit(false)
                .pageCapacity(20)
                .scope(2)
                .pageNum(page));

    }

    private void initpoiSearch() {
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
//                PoiInfo: name = 五里店; uid = f1abb0295fe781a284154102; address = 轨道交通6号线;轨道交通9号线一期;轨道交通环线; province = 重庆市; city = 重庆市; area = 江北区; street_id = ; phoneNum = ; postCode = null; detail = 1; location = latitude: 29.591655, longitude: 106.572932; hasCaterDetails = false; isPano = false; tag = null; poiDetailInfo = PoiDetailInfo: name = null; location = null; address = null; province = null; city = null; area = null; telephone = null; uid = null; detail = 0; distance = 0; type = ; tag = 地铁站; naviLocation = null; detailUrl = ; price = 0.0; shopHours = ; overallRating = 0.0;
//                tasteRating = 0.0; serviceRating = 0.0; environmentRating = 0.0; facilityRating = 0.0; hygieneRating = 0.0; technologyRating = 0.0; imageNum = 0; grouponNum = 0; discountNum = 0; commentNum = 0; favoriteNum = 0; checkinNum = 0; The 0 poiChildrenInfo is: PoiChildrenInfo: uid = f2c4c1ce23c1330b2fb90884; name = 五里店地铁站-3口; showName = 3口; tag = 出入口; location = latitude: 29.59119, longitude: 106.570995; address = ; The 1 poiChildrenInfo is: PoiChildrenInfo: uid = c88f5172d5155541a31de954; name = 五里店地铁站-4口; showName = 4口; tag = 出入口; location = latitude: 29.591266, longitude: 106.572599; address = ; direction = null; distance = 0
                List<PoiInfo> allPoi = poiResult.getAllPoi();
                if (allPoi != null && allPoi.size() != 0) {
                    mLocation = allPoi.get(0);
                    //在搜索栏下用adapter适配来显示所有地址信息
                    mClinicListAdapter.addAll(native_latitude, native_longitude, allPoi);
                } else {
                    mClinicListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            }
        });

    }

    private void initButton(){
        TextView clickOk = findViewById(R.id.click_ok);
        Button shekang = findViewById(R.id.search_shekang);
        Button menzhen = findViewById(R.id.search_menzhen);
        Button yiyuan = findViewById(R.id.search_yiyuan);
        clickOk.setOnClickListener(clinicMapClickListener);
        shekang.setOnClickListener(clinicMapClickListener);
        menzhen.setOnClickListener(clinicMapClickListener);
        yiyuan.setOnClickListener(clinicMapClickListener);
    }
    View.OnClickListener clinicMapClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.search_shekang:
                    mClinicListAdapter.clear();
                    initSeach("社康", 0);
                    BaiduMapUtils.closeKeybord(ClinicMapActivity.this);
                    break;
                case R.id.search_menzhen:
                    mClinicListAdapter.clear();
                    initSeach("门诊", 0);
                    BaiduMapUtils.closeKeybord(ClinicMapActivity.this);
                case R.id.search_yiyuan:
                    mClinicListAdapter.clear();
                    initSeach("医院", 0);
                    BaiduMapUtils.closeKeybord(ClinicMapActivity.this);
                    break;
                case R.id.click_ok:
                    Toast.makeText(ClinicMapActivity.this, "回到当前位置", Toast.LENGTH_SHORT).show();
                    showMap(native_latitude, native_longitude);
                    break;
                default:break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapview.onPause();
    }


    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        mapview.onDestroy();
        super.onDestroy();
    }
    public static void actionStart(Context context){
        Intent intent = new Intent(context,ClinicMapActivity.class);
        context.startActivity(intent);
    }
}
