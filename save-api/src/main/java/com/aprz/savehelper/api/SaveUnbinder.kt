package com.aprz.savehelper.api

import android.os.Bundle
import androidx.annotation.UiThread

/**
 * @author by liyunlei
 *
 * write on 2019/9/28
 *
 * Class desc:
 */
interface SaveUnbinder {

    @UiThread
    fun save(outState: Bundle)

    @UiThread
    fun unbind()

}