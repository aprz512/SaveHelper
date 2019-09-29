package com.aprz.savehelper.save.handler;

import android.content.Intent;
import android.os.Bundle;


import com.aprz.savehelper.save.SavedFieldHandler;
import com.aprz.savehelper.save.anno.FloatSavedField;

import java.lang.reflect.Field;

/**
 * @author by liyunlei
 * <p>
 * write on 2019/9/27
 * <p>
 * Class desc:
 */
public class FloatHandler implements SavedFieldHandler<FloatSavedField> {
    @Override
    public void injectField(FloatSavedField annotation, Object target, Field field, Intent intent, Bundle savedInstanceState) {
        float defaultValue = annotation.defaultValue();
        String key = annotation.key();
        try {
            field.set(target, SavedFieldHandler.getSavedFloat(intent, savedInstanceState, key, defaultValue));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveField(Bundle outState, Object target, Field key, FloatSavedField annotation) {
        try {
            outState.putFloat(annotation.key(), (Float) key.get(target));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
