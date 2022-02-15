package com.example.map;
 
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;

/**
 * @author : created by JTY
 * 邮箱 : 1474054587@qq.com
 * 描述 : 主页面
 * 功能 : 定位当前位置
 *       点击 btn_search 进入搜索页面
 *       点击 btn_navigation 开始导航
 */
public class MainActivity extends AppCompatActivity {

    private Permission permission;
    private MapView mMapView = null;
    private AMap aMap = null;
    private MyLocationStyle myLocationStyle;
    private AmapNaviParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //动态申请权限
        permission = new Permission(this);
        //隐私请求写死为同意（反正不会发布，就不请求了）
        AMapLocationClient.updatePrivacyShow(this,true,true);
        AMapLocationClient.updatePrivacyAgree(this,true);
        //初始化地图容器、地图组件
        mMapView = findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        //初始化定位蓝点
        myLocationStyle = new MyLocationStyle();
        //每5000毫秒重新定位
        myLocationStyle.interval(5000);
        //设置定位蓝点
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
        //放大地图
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }

    /**
     * 点击 btn_search 进入搜索页面
     * @param view
     */
    public void gotoSearch(View view){
        startActivity(new Intent(this,SearchActivity.class));
    }

    /**
     * 点击 btn_navigation 开始导航
     * @param view
     */
    public void navigation(View view){
        // 导航组件参数配置
        params = new AmapNaviParams(null,null, null, AmapNaviType.DRIVER, AmapPageType.ROUTE);
        // 启动导航组件
        AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
