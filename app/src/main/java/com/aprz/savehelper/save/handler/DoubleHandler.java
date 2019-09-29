package com.aprz.savehelper.save.handler;

import android.content.Intent;
import android.os.Bundle;


import com.aprz.savehelper.save.SavedFieldHandler;
import com.aprz.savehelper.save.anno.DoubleSavedField;

import java.lang.reflect.Field;

/**
 * @author by liyunlei
 * <p>
 * write on 2019/9/27
 * <p>
 * Class desc:
 */
public class DoubleHandler implements SavedFieldHandler<DoubleSavedField> {
    @Override
    public void injectField(DoubleSavedField annotation, Object target, Field field, Intent intent, Bundle savedInstanceState) {
        double defaultValue = annotation.defaultValue();
        String key = annotation.key();
        try {
            field.set(target, SavedFieldHandler.getSavedDouble(intent, savedInstanceState, key, defaultValue));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveField(Bundle outState, Object target, Field key, DoubleSavedField annotation) {
        try {
            outState.putDouble(annotation.key(), (Double) key.get(target));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
