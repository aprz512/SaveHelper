package com.aprz.savehelper

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.aprz.savehelper.api.SaveUnbinder
import com.aprz.savehelper.api.get
import java.lang.reflect.Field
import java.util.*

/**
 * @author by liyunlei
 *
 * write on 2019/9/28
 *
 * Class desc:
 */


abstract class BaseActivity2 : AppCompatActivity() {

    companion object {
        const val TAG = "BaseActivity2"
    }

    private lateinit var saveUnbinder: SaveUnbinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val s = System.nanoTime()
        saveUnbinder = get(this, intent, savedInstanceState)
        Log.e(TAG, "获取花费了 ${(System.nanoTime() - s) / 1000000.0} 毫秒")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val s = System.nanoTime()
        saveUnbinder.save(outState)
        Log.e(TAG, "保存花费了 ${(System.nanoTime() - s) / 1000000.0} 毫秒")
    }

    override fun onDestroy() {
        super.onDestroy()
        saveUnbinder.unbind()
    }


}