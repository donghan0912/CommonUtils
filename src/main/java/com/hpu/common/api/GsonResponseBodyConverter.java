package com.hpu.common.api;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hpu.common.BuildConfig;
import com.hpu.common.utils.LogUtil;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2017/6/13
 */

class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private Type type;

    GsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T convert(@Nullable ResponseBody value) throws IOException {
        String json;
        if (value != null) {
            json = value.string();
            if (BuildConfig.IS_DEBUG) {
                LogUtil.d("接口返回数据: " + json);
            }
        } else {
            if (BuildConfig.IS_DEBUG) {
                LogUtil.d("接口返回数据: ResponseBody is null");
            }
            return null;
        }
        try {
            Gson gson = new Gson();
            CommonResponse<T> response = gson.fromJson(json, new TypeToken<CommonResponse<T>>() {}.getType());
            if (!"OK".equals(response.status)) {
                return gson.fromJson(gson.toJson(response.hourly), type);
            } else {
                throw new ResponseException(2, "no data found");
            }
//            return new Gson().fromJson(json, type);
        } finally {
            value.close();
        }
    }
}
