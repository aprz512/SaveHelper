package com.aprz.savehelper.api

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.CheckResult
import androidx.annotation.Nullable
import androidx.annotation.UiThread
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.*

/**
 * @author by liyunlei
 *
 * write on 2019/9/28
 *
 * Class desc:
 */

const val TAG = "SaveHelper"

val BINDINGS: MutableMap<Class<*>, Constructor<out SaveUnbinder>> = LinkedHashMap()

val EMPTY_UNBINDER = object : SaveUnbinder {

    override fun save(outState: Bundle) {
    }

    override fun unbind() {
    }

}

@UiThread
fun get(target: Activity, intent: Intent?, savedInstanceState: Bundle?): SaveUnbinder {
    return createBinding(target, intent, savedInstanceState)
}

fun createBinding(target: Activity, intent: Intent?, savedInstanceState: Bundle?): SaveUnbinder {
    val targetClass = target.javaClass
    Log.d(TAG, "Looking up binding for " + targetClass.name)
    val constructor = findBindingConstructorForClass(targetClass) ?: return EMPTY_UNBINDER

    //noinspection TryWithIdenticalCatches Resolves to API 19+ only type.
    try {
        return constructor.newInstance(target, intent, savedInstanceState)
    } catch (e: IllegalAccessException) {
        throw RuntimeException("Unable to invoke $constructor", e)
    } catch (e: InstantiationException) {
        throw RuntimeException("Unable to invoke $constructor", e)
    } catch (e: InvocationTargetException) {
        val cause = e.cause
        if (cause is RuntimeException) {
            throw cause
        }
        if (cause is Error) {
            throw cause
        }
        throw RuntimeException("Unable to create binding instance.", cause)
    }

}

@Nullable
@CheckResult
@UiThread
fun findBindingConstructorForClass(cls: Class<*>): Constructor<out SaveUnbinder>? {

    var bindingCtor: Constructor<out SaveUnbinder>? = BINDINGS[cls]
    if (bindingCtor != null) {
        Log.d(TAG, "HIT: Cached in binding map.")
        return bindingCtor
    }

    val clsName = cls.name
    if (clsName.startsWith("android.") || clsName.startsWith("java.")) {
        Log.d(TAG, "MISS: Reached framework class. Abandoning search.")
        return null
    }

    try {
        val bindingClass = cls.classLoader?.loadClass(clsName + "_FieldSaving")
        bindingCtor =
            bindingClass?.getConstructor(
                cls,
                Intent::class.java,
                Bundle::class.java
            ) as Constructor<out SaveUnbinder>
        Log.d(TAG, "HIT: Loaded binding class and constructor.")
    } catch (e: ClassNotFoundException) {
        Log.d(TAG, "Not found. Trying superclass " + cls.superclass?.name)
        cls.superclass?.apply {
            bindingCtor = findBindingConstructorForClass(this)
        }
    } catch (e: NoSuchMethodException) {
        throw RuntimeException("Unable to find binding constructor for $clsName", e)
    }

    bindingCtor?.apply {
        BINDINGS[cls] = this
    }

    return bindingCtor
}

