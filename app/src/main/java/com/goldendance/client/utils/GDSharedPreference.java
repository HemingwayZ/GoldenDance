package com.goldendance.client.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 本地存储
 * Created by hemingway on 2017/1/14.
 */

public class GDSharedPreference {
    private static final String GD_SETTING = "gd_setting";
    public static final String KEY_TOKEN = "user_token";
    //常数

    /**
     * 将值保存到SharedPreferences 参数为hashMap形式
     *
     * @param context
     * @param values  值可以是字符串 也可以是布尔值 例如： hm.put("ccc", "aa2a"); hm.put("ddd",
     *                true); hm.put("eeee", Long.valueOf(1234) );
     * @return
     */
    public static boolean storeValue(Context context, Map<String, Object> values) {
        boolean result = false;
        if (context == null || values == null) {
            return result;
        }
        SharedPreferences userPreference = context.getSharedPreferences(GD_SETTING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreference.edit();
        for (Object value : values.keySet()) {
            Object valueItem = values.get(value);
            if (valueItem == null) {
                continue;
            }
            if (valueItem instanceof String) {
                editor.putString((String) value, (String) valueItem);
            } else if (valueItem instanceof Boolean) {
                editor.putBoolean((String) value, (Boolean) valueItem);
            } else if (valueItem instanceof Long) {
                editor.putLong((String) value, (Long) valueItem);
            } else if (valueItem instanceof Float) {
                editor.putFloat((String) value, (Float) valueItem);
            } else if (valueItem instanceof Integer) {
                editor.putInt((String) value, (Integer) valueItem);
            } else {
                editor.putString((String) value, valueItem + "");
            }
        }
        return editor.commit();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(GD_SETTING,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(GD_SETTING,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(GD_SETTING,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(GD_SETTING,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            }
            editor.commit();
        }
    }

}
