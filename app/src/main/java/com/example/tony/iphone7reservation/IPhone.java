package com.example.tony.iphone7reservation;

/**
 * Created by tony on 9/19/16.
 */

import java.util.Map;

public class IPhone {

    private String model;
    private String store;
    private String color;
    private int size;
    private boolean plus;

    public IPhone(String model,String store,Map<String,String> colorMap,Map<String,String> sizeMap,Map<String,Boolean> plusMap) {
        this.model = model;
        this.store = store;
        this.color = color(colorMap,model);
        this.size = size(sizeMap,model);
        this.plus = plus(plusMap,model);
    }

    private String color(Map<String,String> colorMap, String model) {
        return colorMap.get(model);
    }

    private int size(Map<String,String> sizeMap, String model) {
        return Integer.parseInt(sizeMap.get(model));
    }

    private Boolean plus(Map<String,Boolean> plusMap, String model) {
        return plusMap.get(model);
    }

    public String getStore() {
        return this.store;
    }

    public String getColor() {
        return this.color;
    }

    public int getSize() {
        return this.size;
    }

    public String getPlus() {
        return this.plus ? "plus" : "";
    }

    @Override
    public String toString() {
        return getColor() + " " + getSize() + " " + getPlus() + " " + getStore();
    }
}