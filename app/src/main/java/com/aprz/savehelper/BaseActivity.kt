package com.aprz.savehelper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.aprz.savehelper.save.HandlerFactory
import java.lang.reflect.Field
import java.util.*
import kotlin.collections.set

/**
 * @author by liyunlei
 *
 * write on 2019/9/28
 *
 * Class desc:
 */


abstract class BaseActivity : AppCompatActivity() {

    companion object {
        const val TAG = "BaseActivity"
    }

    private val mSavedFieldMap = HashMap<Field, Annotation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent, savedInstanceState)
    }

    private fun handleIntent(intent: Intent, savedInstanceState: Bundle?) {
        val s = System.nanoTime()

        val declaredFields = this.javaClass.declaredFields
        for (declaredField in declaredFields) {
            declaredField.isAccessible = true
            val annotations = declaredField.annotations
            for (annotation in annotations) {
                val handler = HandlerFactory.get(annotation)
                if (handler != null) {
                    handler.injectField(annotation, this, declaredField, intent, savedInstanceState)
                    mSavedFieldMap[declaredField] = annotation
                }
            }
        }

        Log.e(TAG, "获取耗时" + ((System.nanoTime() - s) / 1000000.0).toString() + "毫秒")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val s = System.nanoTime()

        val entrySet = mSavedFieldMap.entries
        for (entry in entrySet) {
            val key = entry.key
            val value = entry.value
            HandlerFactory.get(value)?.saveField(outState, this, key, value)
        }

        Log.e(TAG, "保存耗时" + ((System.nanoTime() - s) / 1000000.0).toString() + "毫秒")
    }

    private fun injectField(
        annotations: Array<Annotation>,
        field: Field,
        intent: Intent,
        savedInstanceState: Bundle?
    ) {

    }

}