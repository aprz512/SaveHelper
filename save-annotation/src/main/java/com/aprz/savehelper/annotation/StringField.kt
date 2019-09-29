package com.aprz.savehelper.annotation

/**
 * @author by liyunlei
 *
 *
 * write on 2019/9/27
 *
 *
 * Class desc:
 */
@Target(AnnotationTarget.FIELD)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class StringField(val key: String = "", val defaultValue: String = "")
