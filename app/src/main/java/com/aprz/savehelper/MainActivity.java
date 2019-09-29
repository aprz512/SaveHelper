package com.aprz.savehelper;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.aprz.savehelper.annotation.DoubleField;
import com.aprz.savehelper.annotation.FloatField;
import com.aprz.savehelper.annotation.IntField;
import com.aprz.savehelper.annotation.LongField;
import com.aprz.savehelper.annotation.StringField;
import com.aprz.savehelper.save.anno.DoubleSavedField;
import com.aprz.savehelper.save.anno.FloatSavedField;
import com.aprz.savehelper.save.anno.IntSavedField;
import com.aprz.savehelper.save.anno.LongSavedField;
import com.aprz.savehelper.save.anno.StringSavedField;

/**
 * @author by liyunlei
 * <p>
 * write on 2019/9/29
 * <p>
 * Class desc:
 */
public class MainActivity extends BaseActivity2 {

    @IntField(key = "testI", defaultValue = 70)
//    @IntSavedField(key = "testI", defaultValue = 70)
    int testI;

    @LongField(key = "testL", defaultValue = 71L)
//    @LongSavedField(key = "testL", defaultValue = 71L)
    long testL;

    @FloatField(key = "testF", defaultValue = 7.2F)
//    @FloatSavedField(key = "testF", defaultValue = 7.2F)
    float testF;

    @DoubleField(key = "testD", defaultValue = 7.3D)
//    @DoubleSavedField(key = "testD", defaultValue = 7.3D)
    double testD;

    @StringField(key = "testS", defaultValue = "74")
//    @StringSavedField(key = "testS", defaultValue = "74")
    String testS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
