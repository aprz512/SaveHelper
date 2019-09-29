package com.aprz.savehelper.save.handler;

import android.content.Intent;
import android.os.Bundle;


import com.aprz.savehelper.save.SavedFieldHandler;
import com.aprz.savehelper.save.anno.LongSavedField;

import java.lang.reflect.Field;

/**
 * @author by liyunlei
 * <p>
 * write on 2019/9/27
 * <p>
 * Class desc:
 */
public class LongHandler implements SavedFieldHandler<LongSavedField> {
    @Override
    public void injectField(LongSavedField annotation, Object target, Field field, Intent intent, Bundle savedInstanceState) {
        long defaultValue = annotation.defaultValue();
        String key = annotation.key();
        try {
            field.set(target, SavedFieldHandler.getSavedLong(intent, savedInstanceState, key, defaultValue));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveField(Bundle outState, Object target, Field key, LongSavedField annotation) {
        try {
            outState.putLong(annotation.key(), (Long) key.get(target));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
