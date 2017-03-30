package com.hooke.trace.cache;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author huke <huke@tiantanhehe.com>
 * @version V1.0
 * @see:
 * @since:
 * @date 2017/2/16
 */
@Data
public class TraceNode {
    private long threadId;
    private long processStepId;


    public TraceNode(long processStepId, long threadId) {
        this.processStepId = processStepId;
        this.threadId = threadId;
    }

    public TraceNode() {
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public long getProcessStepId() {
        return processStepId;
    }

    public void setProcessStepId(long processStepId) {
        this.processStepId = processStepId;
    }
}
