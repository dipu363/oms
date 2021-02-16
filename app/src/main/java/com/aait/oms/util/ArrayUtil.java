package com.aait.oms.util;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArrayUtil {

    public static ArrayList<Object> convert(JSONArray jArr)
    {
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            for (int i=0, l=jArr.length(); i<l; i++){
                list.add(jArr.get(i));
            }
        } catch (JSONException e) {}

        return list;
    }

    public static JSONArray convert(Collection<Object> list)
    {
        return new JSONArray(list);
    }

    public static <T> List<T> getList(JSONArray jsonArray, Object ob) throws Exception {


        List<T> list = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
//            String json = new Gson().toJson(jsonArray.get(i));
//            Object model = objectMapperReadValue(json, ob);


            list.add((T)jsonArray.get(i));
        }

        return list;

    }


}
