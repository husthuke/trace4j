package com.hooke.trace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huke <huke@tiantanhehe.com>
 * @version V1.0
 * @see:
 * @since:
 * @date 2017/2/12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface Trace {

    String processName() default "";

    boolean startStep() default false;
}
