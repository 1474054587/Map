package com.example.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Tip;

/**
 * @author : created by JTY
 * 邮箱 : 1474054587@qq.com
 * 描述 : 定位页面
 * 功能 : 定位搜索页面传入位置
 *       点击 btn_navigation 开始导航
 *       点击 btn_location 镜头回到定位坐标
 *       定位当前位置
 */
public class POIActivity extends AppCompatActivity {

    protected static Tip tip;

    private MapView mMapView;
    private AMap aMap;
    private LatLng latLng;
    private MyLocationStyle myLocationStyle;
    private Poi end;
    private AmapNaviParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi);
        //标题改为定位名称
        setTitle(tip.getName());
        //初始化地图容器、组件
        mMapView = findViewById(R.id.map_poi);
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        //获取定位坐标
        latLng = new LatLng(tip.getPoint().getLatitude(), tip.getPoint().getLongitude());
        //镜头移动到定位坐标
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18f));
        //初始化定位蓝点
        myLocationStyle = new MyLocationStyle();
        //定位蓝点设置为：连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        //每1000毫秒重新定位
        myLocationStyle.interval(1000);
        //设置定位蓝点
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
    }

    /**
     * 点击 btn_location 镜头回到定位坐标
     * @param view
     */
    public void location(View view){
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18f));
    }

    /**
     * 点击 btn_navigation 开始导航
     * @param view
     */
    public void navigation(View view){
        end = new Poi(tip.getName(),latLng,tip.getPoiID());
        // 导航组件参数配置
        params = new AmapNaviParams(null,null, end, AmapNaviType.DRIVER, AmapPageType.ROUTE);
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