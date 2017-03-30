package com.hooke.trace.cache;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 对traceStep步骤进行封装
 * @author huke <huke@tiantanhehe.com>
 * @version V1.0
 * @see:
 * @since:
 * @date 2017/2/22
 */
@Data
public class TraceStep implements Cloneable{
    public static int MAX_DEPTH = 8;

    private AtomicLong[] processSteps;

    public TraceStep(AtomicLong[] processSteps) {
        this.processSteps = processSteps;
    }

    public TraceStep() {
        processSteps = new AtomicLong[MAX_DEPTH];
    }

    public String getTraceStepSequence(){
        if (processSteps == null) {
            return "";
        }
        int i = 0;
        String stepSeq = "";
        while ((i < MAX_DEPTH) && (processSteps[i] != null)){
            Long step = processSteps[i].get();
            stepSeq += step + ".";
            i++;
        }

        return stepSeq;
    }

    //后续可以通过super的方式重些clone方法
    public Object clone(){
        AtomicLong[] cloneProcessSteps = new AtomicLong[MAX_DEPTH];
        int i = 0;
        while ((i < MAX_DEPTH) && (processSteps[i] != null)){
            cloneProcessSteps[i] = new AtomicLong(processSteps[i].get());
            i++;
        }
        return new TraceStep(cloneProcessSteps);
    }

}
