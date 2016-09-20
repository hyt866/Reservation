package com.example.tony.iphone7reservation;

/**
 * Created by tony on 9/19/16.
 */

import android.util.Log;

import java.util.Map;

public class IPhone {

    private static final String TAG = "MyActivity";

    private String model;
    private String store;
    private String color;
    private String size;

    public IPhone(String model,String store,Map<String,String> colorMap,Map<String,String> sizeMap) {
        this.model = model;
        this.store = store;
        this.color = Color(colorMap,model);
        this.size = Size(sizeMap,model);
    }

    private String Color(Map<String,String> colorMap, String model) {
        return colorMap.get(model);
    }

    private String Size(Map<String,String> sizeMap, String model) {
        return sizeMap.get(model);
    }

    public void show() {
        Log.d(TAG, this.model);
        Log.d(TAG, this.store);
        Log.d(TAG, this.color);
        Log.d(TAG, this.size);
    }

    public String getStore() {
        return this.store;
    }

    public String getColor() {
        return this.color;
    }

    public String getSize() {
        return this.size;
    }
}