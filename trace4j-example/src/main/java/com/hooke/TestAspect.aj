package com.hooke;


import com.hooke.trace.TraceProcess;

/**
 *
 * @see:
 * @since:
 * @author huke <huke@tiantanhehe.com>
 * @version V1.0
 * @date 2017/2/12 
 */
public aspect TestAspect {

    pointcut tracePointcut(): execution(* com.hooke.biz..*(..));

    Object around(): tracePointcut(){
        return TraceProcess.trace(thisJoinPoint);
    }

}
