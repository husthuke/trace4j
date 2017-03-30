package com.hooke.util;

import com.hooke.trace.cache.TraceTree;
import com.hooke.trace.manager.TraceCacheManager;
import com.hooke.trace.manager.TraceInheritParamManager;
import com.hooke.trace.manager.TraceParamManager;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 流程跟踪处理工具类.
 *
 * @author huke <huke@tiantanhehe.com>
 * @version V1.0
 * @see:
 * @since:
 * @date 2017 /2/15
 */
public class TraceUtil {

    /**
     * 素数常量
     */
    public static int PRIME_BASE = 127;

    /**
     * 将整形数值装换为流程编号
     *
     * @param processStep the process step
     * @return the string
     */
    public static String Long2TraceStepString(long processStep){
        long step = processStep;
        if(step < 0){
            return null;
        }

        String stepString = "";
        while((step/ PRIME_BASE) > 0){
            stepString = step% PRIME_BASE + "." + stepString;
            step = (step - step% PRIME_BASE)/ PRIME_BASE;
        }
        stepString = step + "." + stepString;

        return stepString;

    }

    /**
     * 根据traceid以及全局的步骤获取当前线程的step值
     *
     * @param traceId           the trace id
     * @param globalProcessStep the global process step
     * @return the this process step value
     */
    public static long getThisProcessStepValue(Object traceId, AtomicLong globalProcessStep) {
        Object localProcessStep;
        long thisProcessStepValue = 0L;
        TraceTree traceTree;

        if((traceId != null) && (TraceParamManager.gettraceParam("traceId") == null)){
            //启动了子线程进行分支流程处理
            TraceParamManager.settraceParam("traceId", TraceInheritParamManager.gettraceParam("traceId"));
            TraceParamManager.settraceParam("processName",TraceInheritParamManager.gettraceParam("processName"));

            AtomicLong thisProcessStep = new AtomicLong(((AtomicLong)TraceInheritParamManager.gettraceParam("processStep")).get());
            thisProcessStep.updateAndGet(n->n*PRIME_BASE);
            //获取当前主干流程中子分支的个数
            traceTree = TraceCacheManager.getInstance().getTraceTree((UUID) traceId);
            if(traceTree != null){

                synchronized (traceTree){
                    int childNumber = traceTree.getChildNumber(thisProcessStep.get()/ PRIME_BASE);
                    thisProcessStep.addAndGet(childNumber + 1);
                    TraceParamManager.settraceParam("processStep",thisProcessStep);
                    localProcessStep = TraceParamManager.gettraceParam("processStep");
                    thisProcessStepValue = ((AtomicLong)localProcessStep).get();
                    traceTree.addTraceNode(Thread.currentThread().getId(),thisProcessStepValue);
                }
            }


        }else{
            //增加全局step，把全局复制给local
            globalProcessStep.incrementAndGet();
//                localProcessStep = new AtomicLong(((AtomicLong)globalProcessStep).get());
            TraceParamManager.settraceParam("processStep",new AtomicLong(globalProcessStep.get()));

            localProcessStep = TraceParamManager.gettraceParam("processStep");
            thisProcessStepValue = ((AtomicLong)localProcessStep).get();
            //如果Trace缓存中不存在该traceid对应的缓存，则创建缓存
            traceTree = TraceCacheManager.getInstance().getTraceTree((UUID) traceId);

            if( traceTree == null){
                traceTree = new TraceTree();
                TraceCacheManager.getInstance().putTraceTree((UUID) traceId,traceTree);
            }

            synchronized (traceTree) {

                traceTree.addTraceNode(Thread.currentThread().getId(), thisProcessStepValue);
            }

        }
        return thisProcessStepValue;
    }

    /**
     * Main.工具类测试程序
     *
     * @param args the args
     */
    public static void main(String[] args){
        String step = Long2TraceStepString(127);
        System.out.println(step);
    }
}
