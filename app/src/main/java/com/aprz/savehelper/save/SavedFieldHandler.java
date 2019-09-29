package com.aprz.savehelper.save;

import android.content.Intent;
import android.os.Bundle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author by liyunlei
 * <p>
 * write on 2019/9/27
 * <p>
 * Class desc:
 */
public interface SavedFieldHandler<T extends Annotation> {

    void injectField(T annotation, Object target, Field field, Intent intent, Bundle savedInstanceState);

    void saveField(Bundle outState, Object target, Field key, T annotation);

    static long getSavedLong(Intent intent, Bundle savedInstanceState, String key) {
        return getSavedLong(intent, savedInstanceState, key, 0L);
    }

    static long getSavedLong(Intent intent, Bundle savedInstanceState, String key, long defaultValue) {
        if (savedInstanceState != null) {
            return savedInstanceState.getLong(key, defaultValue);
        } else if (intent != null) {
            return intent.getLongExtra(key, defaultValue);
        }
        return defaultValue;
    }

    static int getSavedInt(Intent intent, Bundle savedInstanceState, String key) {
        return getSavedInt(intent, savedInstanceState, key, 0);
    }

    static int getSavedInt(Intent intent, Bundle savedInstanceState, String key, int defaultValue) {
        if (savedInstanceState != null) {
            return savedInstanceState.getInt(key, defaultValue);
        } else if (intent != null) {
            return intent.getIntExtra(key, defaultValue);
        }
        return defaultValue;
    }

    static double getSavedDouble(Intent intent, Bundle savedInstanceState, String key) {
        return getSavedDouble(intent, savedInstanceState, key, 0D);
    }

    static double getSavedDouble(Intent intent, Bundle savedInstanceState, String key, double defaultValue) {
        if (savedInstanceState != null) {
            return savedInstanceState.getDouble(key, defaultValue);
        } else if (intent != null) {
            return intent.getDoubleExtra(key, defaultValue);
        }
        return defaultValue;
    }

    static float getSavedFloat(Intent intent, Bundle savedInstanceState, String key) {
        return getSavedFloat(intent, savedInstanceState, key, 0F);
    }

    static float getSavedFloat(Intent intent, Bundle savedInstanceState, String key, float defaultValue) {
        if (savedInstanceState != null) {
            return savedInstanceState.getFloat(key, defaultValue);
        } else if (intent != null) {
            return intent.getFloatExtra(key, defaultValue);
        }
        return defaultValue;
    }

    static String getSavedString(Intent intent, Bundle savedInstanceState, String key) {
        return getSavedString(intent, savedInstanceState, key, "");
    }

    static String getSavedString(Intent intent, Bundle savedInstanceState, String key, String defaultValue) {
        if (savedInstanceState != null) {
            return savedInstanceState.getString(key, defaultValue);
        } else if (intent != null) {
            String extra = intent.getStringExtra(key);
            if (extra == null) {
                return "";
            }
            return extra;
        }
        return defaultValue;
    }

}
