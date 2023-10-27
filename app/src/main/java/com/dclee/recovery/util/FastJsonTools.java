package com.dclee.recovery.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dclee.recovery.base.Response;

public class FastJsonTools {

    /**
     * 对单个javabean的解析
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T get(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return t;
    }


    public static <T> T get(String jsonString, TypeReference<T> typeReference) {
        T t;
        try {
            return JSON.parseObject(jsonString, typeReference);
        } catch (Exception ex) {
            ex.printStackTrace();
            return (T) JSON.parseObject(jsonString, new TypeReference<Response<Object>>() {

            });
        }
    }

    public static <T> List<T> getList(String jsonStriung, Class<T> cls) {

        ArrayList<T> list = new ArrayList<T>();
        try {
            list = (ArrayList<T>) JSONArray.parseArray(jsonStriung, cls);
        } catch (Exception e) {
            // TODO: handle exception
            Log.i("exception:", e.toString());
        }
        return list;
    }

    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = JSON.parseObject(jsonString, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    public static <T> Response<T> getResponse(String result, Class<T> cls) {
        Response response = new Response();
        JSONObject jsonObject = JSON.parseObject(result);
        if (result.contains("message"))
            response.setMessage(jsonObject.getString("message"));
        response.setStatus(jsonObject.getString("status"));
        String data = jsonObject.getString("data");
        response.setData(get(data, cls));
//        Response<T> response = JSON.parseObject(result, new TypeReference<Response<T>>() {
//        });
        return response;
    }

    public static <T> Response<List<T>> getResponseList(String result, Class<T> cls) {
        Response response = new Response();
        JSONObject jsonObject = JSON.parseObject(result);
        response.setMessage(jsonObject.getString("message"));
        response.setStatus(jsonObject.getString("status"));
        String data = jsonObject.getString("data");
        response.setData(getList(data, cls));
        return response;
    }
}