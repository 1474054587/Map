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

public class SearchActivity extends AppCompatActivity implements TextWatcher, SearchAdapter.OnItemClickListener, Inputtips.InputtipsListener {

    private EditText editText;
    private Inputtips inputTips;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private InputtipsQuery inputtipsQuery;

    public Tip tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        editText = findViewById(R.id.edit_query);
        editText.addTextChangedListener(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(new ArrayList<>(),this);
        recyclerView.setAdapter(searchAdapter);
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
        inputtipsQuery = new InputtipsQuery(String.valueOf(s),null);
        //inputtipsQuery.setCityLimit(true);
        inputTips = new Inputtips(this,inputtipsQuery);
        inputTips.setInputtipsListener(this);
        inputTips.setQuery(inputtipsQuery);
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onItemClick(Tip tip) {
        if (tip == null) {
            Toast toast = Toast.makeText(this,"定位失败。",Toast.LENGTH_SHORT);
            toast.show();
        } else {
            this.tip = tip;
            Intent intent = new Intent(this,POIActivity.class);
            POIActivity.tip = tip;
            startActivity(intent);
        }
    }
}