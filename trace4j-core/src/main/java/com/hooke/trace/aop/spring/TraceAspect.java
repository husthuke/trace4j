package com.hooke.trace.aop.spring;

import com.google.gson.Gson;
import com.hooke.trace.aop.IAopTrace;
import com.hooke.trace.Trace;
import com.hooke.trace.manager.TraceInheritParamManager;
import com.hooke.trace.manager.TraceParamManager;
import com.hooke.util.TraceUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

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

    Logger logger = LoggerFactory.getLogger(TraceAspect.class);

    @Override
    public Object trace(JoinPoint joinPoint) {

        long start = System.currentTimeMillis();
        Object result = null;

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        //处理Trace注解的方法
        if (method.isAnnotationPresent(Trace.class)){
            Object traceId =  TraceInheritParamManager.gettraceParam("traceId");
            Trace traceAnnotation = method.getAnnotation(Trace.class);
            Object processName = TraceInheritParamManager.gettraceParam("processName");
            Object globalProcessStep = TraceInheritParamManager.gettraceParam("processStep");
            String sessionId = (String) TraceInheritParamManager.gettraceParam("sessionId");
            if(processName == null){
                processName = traceAnnotation.processName();
                TraceInheritParamManager.settraceParam("processName",traceAnnotation.processName());
            }
            if (traceId == null ){
                traceId = UUID.randomUUID();
                TraceInheritParamManager.settraceParam("traceId",traceId);
                TraceParamManager.settraceParam("traceId",traceId);
                TraceParamManager.settraceParam("processName",TraceInheritParamManager.gettraceParam("processName"));
            }
            if (globalProcessStep == null){
                globalProcessStep = new AtomicLong(0);
                TraceInheritParamManager.settraceParam("processStep",globalProcessStep);
            }

            long thisProcessStepValue = TraceUtil.getThisProcessStepValue(traceId, (AtomicLong) globalProcessStep);

            //创建输入参数字符串以及返回数据字符串
            StringBuilder argsString = new StringBuilder();
            StringBuilder returnString = new StringBuilder();
            try {
                Object[] args = joinPoint.getArgs();
                Gson gson = new Gson();

                if (args != null && args.length > 0){
                    for(Object arg:args){
                        try {
                            argsString.append(gson.toJson(arg));
                        }catch (Exception e){
                        }
                    }
                }

                result = ((ProceedingJoinPoint) joinPoint).proceed();

                if(result != null){
                    returnString.append(gson.toJson(result));
                }

            } catch (Throwable throwable) {
                throwable.printStackTrace();
                logger.error("",throwable);
            }
            long end = System.currentTimeMillis();

            logger.info("threadId:" + Thread.currentThread().getId() +
                    " triceId:" + traceId +
                    " sessionId:" + sessionId +
                    " ProcessName:" + processName +
                    " ProcessStep:" + TraceUtil.Long2TraceStepString(thisProcessStepValue) +
                    " Joinpoint:" + joinPoint +
                    " argsJson:" + argsString +
                    " returnJson:" + returnString +
                    " cost:"+ (end - start) + " ms!");

        }

        return result;
    }


}
