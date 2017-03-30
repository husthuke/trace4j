package com.hooke.trace.aop;

import org.aspectj.lang.JoinPoint;

/**
 * 已aop机制实现的跟踪接口
 * @author huke <huke@tiantanhehe.com>
 * @version V1.0
 * @see:
 * @since:
 * @date 2017/3/28
 */
public interface IAopTrace {

    Object trace(JoinPoint joinPoint);


}
