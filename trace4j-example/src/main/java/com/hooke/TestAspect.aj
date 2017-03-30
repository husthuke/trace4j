package com.hooke;

/**
 *
 * @see:
 * @since:
 * @author huke <huke@tiantanhehe.com>
 * @version V1.0
 * @date 2017/2/12 
 */
public aspect TestAspect {

//    Logger logger = LoggerFactory.getLogger(TestAspect.class);
//
//    Object around():execution(* com.hooke.biz..*(..)){
//        System.out.println("----------start process----------");
//        long start = System.currentTimeMillis();
//        Object result = null;
//
//        Signature signature = thisJoinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Method method = methodSignature.getMethod();
//
//        if(method.isAnnotationPresent(Trace.class)){
//            Object traceId =  TraceInheritParamManager.gettraceParam("traceId");
//            Trace traceAnno = method.getAnnotation(Trace.class);
//            Object processName = TraceInheritParamManager.gettraceParam("processName");
//
//            if(processName == null){
//                TraceInheritParamManager.settraceParam("processName",traceAnno.processName());
//            }
//
//            if(traceId == null || !traceAnno.processName().equals(((String) TraceInheritParamManager.gettraceParam("processName")))){
//                traceId = UUID.randomUUID();
//                TraceInheritParamManager.settraceParam("traceId",traceId);
//            }
//
//
//
//            String sessionId = (String) TraceInheritParamManager.gettraceParam("sessionId");
//
//
//            Object processStep = TraceInheritParamManager.gettraceParam("processStep");
//
//            if(processStep == null){
//                processStep = new Integer(0);
//
//            }else {
//                processStep = ((Integer)processStep) + 1 ;
//            }
//
//            TraceInheritParamManager.settraceParam("processStep",processStep);
//
//            StringBuilder argsString = new StringBuilder();
//            StringBuilder returnString = new StringBuilder();
//
//            System.out.println("tracing");
//            try {
//                Object[] args = thisJoinPoint.getArgs();
//                Gson gson = new Gson();
//
//                if (args != null && args.length > 0){
//                    for(Object arg:args){
//                        try {
//                            argsString.append(gson.toJson(arg));
//                        }catch (Exception e){
//                        }
//                    }
//                }
//
//                result = proceed();
//
//                if(result != null){
//                    returnString.append(gson.toJson(result));
//                }
//
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//            }
//            long end = System.currentTimeMillis();
////            System.out.println("triceId:" + traceId +
////                    " sessionId:" + sessionId +
////                    " ProcessName:" + processName +
////                    " ProcessStep:" + processStep +
////                    " Joinpoint:" + thisJoinPoint +
////                    " argsJson:" + argsString +
////                    " returnJson:" + returnString +
////                    " cost:"+ (end - start) + " ms!");
//
//            logger.info("triceId:" + traceId +
//                    " sessionId:" + sessionId +
//                    " ProcessName:" + processName +
//                    " ProcessStep:" + processStep +
//                    " Joinpoint:" + thisJoinPoint +
//                    " argsJson:" + argsString +
//                    " returnJson:" + returnString +
//                    " cost:"+ (end - start) + " ms!");
//
//        }
//
//        System.out.println("----------end process------------");
//        return result;
//    }
}
