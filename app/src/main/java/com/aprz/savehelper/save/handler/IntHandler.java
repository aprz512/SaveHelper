package com.aprz.savehelper.save.handler;

import android.content.Intent;
import android.os.Bundle;


import com.aprz.savehelper.save.SavedFieldHandler;
import com.aprz.savehelper.save.anno.IntSavedField;

import java.lang.reflect.Field;

/**
 * @author by liyunlei
 * <p>
 * write on 2019/9/27
 * <p>
 * Class desc:
 */
public class IntHandler implements SavedFieldHandler<IntSavedField> {

    @Override
    public void injectField(IntSavedField annotation, Object target, Field field, Intent intent, Bundle savedInstanceState) {
        int defaultValue = annotation.defaultValue();
        String key = annotation.key();
        try {
            field.set(target, SavedFieldHandler.getSavedInt(intent, savedInstanceState, key, defaultValue));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveField(Bundle outState, Object target, Field key, IntSavedField annotation) {
        try {
            outState.putInt(annotation.key(), (Integer) key.get(target));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
