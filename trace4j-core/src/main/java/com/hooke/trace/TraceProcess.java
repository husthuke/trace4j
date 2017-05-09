package com.hooke.trace;

import com.google.gson.Gson;
import com.hooke.trace.manager.TraceInheritParamManager;
import com.hooke.trace.manager.TraceParamManager;
import com.hooke.util.TraceUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 流程处理方法，供其他Advice调用
 *
 * @author huke <huke@tiantanhehe.com>
 * @version V1.0
 * @see:
 * @since:
 * @date 2017 /4/11
 */
public class TraceProcess {
    /**
     * The constant TRACE_ID.
     */
    public static final String TRACE_ID = "traceId";
    /**
     * The constant PROCESS_NAME.
     */
    public static final String PROCESS_NAME = "processName";
    private static Logger logger = LoggerFactory.getLogger(TraceProcess.class);
    private static Gson gson = new Gson();

    /**
     * Trace object.
     *
     * @param joinPoint the join point
     * @return the object
     */
    public static Object trace(JoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        Object result = null;

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        //处理Trace注解的方法
        if (method.isAnnotationPresent(Trace.class)) {
            Object traceId = TraceInheritParamManager.gettraceParam(TRACE_ID);
            Trace traceAnnotation = method.getAnnotation(Trace.class);
            Object processName = TraceInheritParamManager.gettraceParam(PROCESS_NAME);
            Object globalProcessStep = TraceInheritParamManager.gettraceParam("processStep");
            String sessionId = (String) TraceInheritParamManager.gettraceParam("sessionId");
            if (processName == null) {
                processName = traceAnnotation.processName();
                TraceInheritParamManager.settraceParam(PROCESS_NAME, traceAnnotation.processName());
            }
            if (traceId == null) {
                traceId = UUID.randomUUID();
                TraceInheritParamManager.settraceParam(TRACE_ID, traceId);
                TraceParamManager.settraceParam(TRACE_ID, traceId);
                TraceParamManager.settraceParam(PROCESS_NAME, TraceInheritParamManager.gettraceParam(PROCESS_NAME));
            }
            if (globalProcessStep == null) {
                globalProcessStep = new AtomicLong(0);
                TraceInheritParamManager.settraceParam("processStep", globalProcessStep);
            }

            long thisProcessStepValue = TraceUtil.getThisProcessStepValue(traceId, (AtomicLong) globalProcessStep);

            //创建输入参数字符串以及返回数据字符串

            String argsString = getArgsJson(joinPoint.getArgs());

            StringBuilder returnString = new StringBuilder();
            result = getResult((ProceedingJoinPoint) joinPoint);

            if (result != null) {
                returnString.append(gson.toJson(result));
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
                    " cost:" + (end - start) + " ms!");

        }

        return result;
    }

    private static Object getResult(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            result = joinPoint.proceed();

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            logger.error(throwable.toString(), throwable);
        }
        return result;
    }

    private static String getArgsJson(Object[] args) {
        StringBuilder argsString = new StringBuilder();

        if (args != null && args.length > 0) {
            for (Object arg : args) {
                try {
                    argsString.append(gson.toJson(arg));
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.toString(), e);
                }
            }
        }

        return argsString.toString();

    }


}
