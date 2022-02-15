package com.example.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : created by JTY
 * 邮箱 : 1474054587@qq.com
 * 描述 : 搜索页面
 * 功能 : 输入内容自动提示
 *       点击条目进入定位
 */
public class SearchActivity extends AppCompatActivity implements TextWatcher, SearchAdapter.OnItemClickListener, Inputtips.InputtipsListener {

    private EditText editText;
    private Inputtips inputTips;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private InputtipsQuery inputtipsQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //初始化输入框、recyclerView
        editText = findViewById(R.id.edit_query);
        editText.addTextChangedListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(new ArrayList<>(),this);
        recyclerView.setAdapter(searchAdapter);
        //设置监听器
        searchAdapter.setItemClickListener(this);
    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        searchAdapter.getData(list);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //输入框内容变化时，自动开始搜索
        inputtipsQuery = new InputtipsQuery(String.valueOf(s),null);
        inputTips = new Inputtips(this,inputtipsQuery);
        inputTips.setInputtipsListener(this);
        inputTips.setQuery(inputtipsQuery);
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    /**
     * 监听器
     * 点击 recyclerView 条目，进入定位页面
     * @param tip 定位信息
     */
    @Override
    public void onItemClick(Tip tip) {
        //获取不到定位坐标，弹一个 Toast
        if (tip.getPoiID() == null) {
            Toast toast = Toast.makeText(this,"定位失败。",Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Intent intent = new Intent(this,POIActivity.class);
            //传入定位信息
            POIActivity.tip = tip;
            //进入定位页面
            startActivity(intent);
        }
    }
}