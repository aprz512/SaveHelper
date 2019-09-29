package com.aprz.savehelper.save;

import android.util.SparseArray;


import com.aprz.savehelper.save.anno.DoubleSavedField;
import com.aprz.savehelper.save.anno.FloatSavedField;
import com.aprz.savehelper.save.anno.IntSavedField;
import com.aprz.savehelper.save.anno.LongSavedField;
import com.aprz.savehelper.save.anno.StringSavedField;
import com.aprz.savehelper.save.handler.DoubleHandler;
import com.aprz.savehelper.save.handler.FloatHandler;
import com.aprz.savehelper.save.handler.IntHandler;
import com.aprz.savehelper.save.handler.LongHandler;
import com.aprz.savehelper.save.handler.StringHandler;

import java.lang.annotation.Annotation;

/**
 * @author by liyunlei
 * <p>
 * write on 2019/9/27
 * <p>
 * Class desc:
 */
public class HandlerFactory {

    private static SparseArray<SavedFieldHandler> mArray = new SparseArray<>();

    static {
        mArray.put(0, new IntHandler());
        mArray.put(1, new LongHandler());
        mArray.put(2, new FloatHandler());
        mArray.put(3, new DoubleHandler());
        mArray.put(4, new StringHandler());
    }

    public static SavedFieldHandler get(Annotation annotation) {

        if (annotation instanceof IntSavedField) {
            return mArray.get(0);
        } else if (annotation instanceof LongSavedField) {
            return mArray.get(1);
        } else if (annotation instanceof FloatSavedField) {
            return mArray.get(2);
        } else if (annotation instanceof DoubleSavedField) {
            return mArray.get(3);
        } else if (annotation instanceof StringSavedField) {
            return mArray.get(4);
        }

        return null;
    }

}
