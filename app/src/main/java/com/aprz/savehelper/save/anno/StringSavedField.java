package com.aprz.savehelper.save.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author by liyunlei
 * <p>
 * write on 2019/9/27
 * <p>
 * Class desc:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StringSavedField {
    String key() default "";

    String defaultValue() default "";
}
