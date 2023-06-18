package com.example.yuemiaoapp.function;


import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.utils.DistanceUtil;

import java.text.DecimalFormat;

/**
 * date:2023/6/17
 * author:fs956(admin)
 * funcation: 用于初始化一些百度地图SDK的设置选项
 */
public class BaiduMapUtils {
    /**
     * 根据经纬度计算距离
     *
     * @param longitude1 start
     * @param latitude1  start
     * @param longitude2 end
     * @param latitude2  end
     * @return
     */
    public static String getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        String distance = "";
        LatLng latLng1 = new LatLng(latitude1, longitude1);
        LatLng latLng2 = new LatLng(latitude2, longitude2);
        double dis = DistanceUtil.getDistance(latLng1, latLng2);
        if (dis < 1000) {
            return new DecimalFormat("#").format(dis) + "米";
        }
        if (dis > 1000) {
            return getDoubleString(dis / 1000) + "公里";
        }
        return distance;
    }
    public static double getDistanceNumerical(double longitude1, double latitude1,
                                     double longitude2, double latitude2){
        LatLng latLng1 = new LatLng(latitude1, longitude1);
        LatLng latLng2 = new LatLng(latitude2, longitude2);
        double dis = DistanceUtil.getDistance(latLng1, latLng2);
        return dis;
    }

    /*
     * 如果是小数，保留两位，非小数，保留整数
     * @param number
     */
    public static String getDoubleString(double number) {
        String numberStr;
        if (((int) number * 1000) == (int) (number * 1000)) {
            //如果是一个整数
            numberStr = String.valueOf((int) number);
        } else {
            DecimalFormat df = new DecimalFormat("######0.0");
            numberStr = df.format(number);
        }
        return numberStr;
    }

    /**
     * 自动关闭软键盘
     *
     * @param activity
     */
    public static void closeKeybord(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 百度定位
     *
     * @param context
     * @return
     */
    public static LocationClient getLocationClient(Context context) {
//定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        LocationClient locationClient = new LocationClient(context);
//声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
//注册监听函数
//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("bd09ll");
//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(1000);
//可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
//可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
//可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
//可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode();
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.setLocOption(locationOption);
        return locationClient;
    }

    static GeoCoder geoCoder = GeoCoder.newInstance();

    /**
     * 将经度纬度反向译为文字地址
     *
     * @param lon      经度
     * @param lat      纬度
     * @param listener OnGetGeoCoderResultListener监听器，对接收到的结果进行处理
     */
    public static void reverseGeoParse(double lon, double lat,
                                       OnGetGeoCoderResultListener listener) {
        geoCoder.setOnGetGeoCodeResultListener(listener);
        LatLng pt1 = new LatLng(lat, lon);
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(pt1));
    }
}

