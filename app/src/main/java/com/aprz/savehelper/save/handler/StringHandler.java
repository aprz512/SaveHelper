package com.aprz.savehelper.save.handler;

import android.content.Intent;
import android.os.Bundle;


import com.aprz.savehelper.save.SavedFieldHandler;
import com.aprz.savehelper.save.anno.StringSavedField;

import java.lang.reflect.Field;

/**
 * @author by liyunlei
 * <p>
 * write on 2019/9/27
 * <p>
 * Class desc:
 */
public class StringHandler implements SavedFieldHandler<StringSavedField> {
    @Override
    public void injectField(StringSavedField annotation, Object target, Field field, Intent intent, Bundle savedInstanceState) {
        String defaultValue = annotation.defaultValue();
        String key = annotation.key();
        try {
            field.set(target, SavedFieldHandler.getSavedString(intent, savedInstanceState, key, defaultValue));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveField(Bundle outState, Object target, Field key, StringSavedField annotation) {
        try {
            outState.putString(annotation.key(), (String) key.get(target));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
