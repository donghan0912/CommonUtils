package com.hpu.common.adapter;

import android.content.res.Resources;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/5/19
 */

public abstract class BaseItem<T> implements Item {
    public T mData;
    private int mLayoutRes;

    public BaseItem(){
        this(null, 0);
    }

    public BaseItem(@NonNull T t) {
        this(t, 0);
    }

    public BaseItem(@LayoutRes int resource) {
        this(null, resource);
    }

    public BaseItem(@Nullable T t, @LayoutRes int resource) {
        this.mData = t;
        this.mLayoutRes = resource;
    }

    public void setData(T t) {
        this.mData = t;
    }

    public T getData() {
        return mData;
    }

    @Override
    public int getLayoutResource() {
        return mLayoutRes;
    }

    @Override
    public int getItemViewType() {
        return getLayoutResource();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resourceId = getLayoutResource();
        if (resourceId <= 0) {
            throw new Resources.NotFoundException("Resource ID \"" + resourceId + "\" is not valid");
        }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
        if (itemView == null) {
            return null;
        }
        return new BaseViewHolder(itemView);
    }

}
