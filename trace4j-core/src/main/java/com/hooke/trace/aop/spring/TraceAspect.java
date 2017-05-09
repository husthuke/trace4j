package com.hooke.trace.aop.spring;

import com.hooke.trace.TraceProcess;
import com.hooke.trace.aop.IAopTrace;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;

/**
 * 流程追踪切面，用于记录相关日志
 * @author huke <huke@tiantanhehe.com>
 * @version V1.0
 * @see:
 * @since:
 * @date 2017/2/12
 */
@Aspect
public class TraceAspect implements IAopTrace {

    @Override
    public Object trace(JoinPoint joinPoint) {
        return TraceProcess.trace(joinPoint);

    }


}
