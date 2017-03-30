package com.hooke.trace.cache;

import com.hooke.util.TraceUtil;
import lombok.Data;

import java.util.LinkedList;

/**
 * The type Trace tree.
 *
 * @author huke <huke@tiantanhehe.com>
 * @version V1.0
 * @see:
 * @since:
 * @date 2017 /2/15
 */
@Data
public class TraceTree {

    private TraceNode data;

    private static long ERROR = -1L;

//    private TraceTree trunkTree;

    private TraceTree parentTree;

    private LinkedList<TraceTree> childTraceTree;

    /**
     * Instantiates a new Trace tree.
     */
    public TraceTree(){
        this.data = new TraceNode(0,1);
        this.childTraceTree = new LinkedList<TraceTree>();

    }

    /**
     * Instantiates a new Trace tree.
     *
     * @param data the data
     */
    public TraceTree(TraceNode data){
        this.data = data;
        this.childTraceTree = new LinkedList<TraceTree>();
    }

    /**
     * Instantiates a new Trace tree.
     *
     * @param data           the data
     * @param childTraceTree the child trace tree
     */
    public TraceTree(TraceNode data, LinkedList<TraceTree> childTraceTree) {
        this.data = data;
        this.childTraceTree = childTraceTree;
    }

    /**
     * Instantiates a new Trace tree.
     *
     * @param threadId the thread id
     */
    public TraceTree(long threadId){
        TraceNode traceNode = new TraceNode();
        traceNode.setProcessStepId(1);
        traceNode.setThreadId(threadId);
        this.data = traceNode;
        this.childTraceTree = new LinkedList<TraceTree>();
    }

//    public boolean addTraceNode(long threadId,long processStepId){
//
//        TraceTree pTraceTree = this;
//        long trunkProcessStepId = 1;
//        while ((processStepId / TraceUtil.PRIME_BASE) > 0) {
//            if (pTraceTree.trunkTree == null) {
//                TraceNode traceNode = new TraceNode();
//                traceNode.setThreadId(threadId);
//                traceNode.setProcessStepId(trunkProcessStepId * TraceUtil.PRIME_BASE + 1);
//                pTraceTree.trunkTree = new TraceTree(traceNode);
//
//
//            }
//                processStepId = processStepId / TraceUtil.PRIME_BASE;
//                pTraceTree = pTraceTree.trunkTree;
//        }
//
//        for (int i = 0; i < pTraceTree.childTraceTree.size(); i++) {
//
//        }
//
//
//        return false;
//
//    }

    public int getChildNumber(long processStepId){

        TraceTree traceTree = findTraceNode(childTraceTree,processStepId);

        if(traceTree == null || traceTree.childTraceTree == null){
            return 0;
        }else {
            return traceTree.childTraceTree.size();
        }


    }

    /**
     * 查找TraceTree中指定流程步骤的节点
     *
     * @param childTraceTree the child trace tree
     * @param processStepId  the process step id
     * @return the trace tree
     */
    public TraceTree findTraceNode(LinkedList<TraceTree> childTraceTree,long processStepId){
        if (childTraceTree == null || childTraceTree.size() == 0) {
            return null;
        }


        if(processStepId < childTraceTree.getFirst().data.getProcessStepId() || processStepId > childTraceTree.getLast().data.getProcessStepId()){
            return null;
        }

        return childTraceTree.get((int) (processStepId - childTraceTree.getFirst().data.getProcessStepId()));


    }


    /**
     * Add trace node trace tree.
     *
     * @param threadId      the thread id
     * @param processStepId the process step id
     * @return the trace tree
     */
    public TraceTree addTraceNode(long threadId,long processStepId){

        if(processStepId != 0 && processStepId % TraceUtil.PRIME_BASE == 0){
            return null;
        }

        TraceTree pTraceTree = this;
        if(processStepId == 0){
            return pTraceTree;
        }

        TraceTree traceTreeParent;

        traceTreeParent = addTraceNode(threadId,processStepId / TraceUtil.PRIME_BASE);

        TraceTree traceTree = findTraceNode(traceTreeParent.childTraceTree,processStepId);

        if(traceTree == null){
            TraceNode traceNode = new TraceNode();
            traceNode.setThreadId(threadId);
            traceNode.setProcessStepId(processStepId);
            traceTree = new TraceTree(traceNode);
            traceTree.parentTree = traceTreeParent;
            traceTreeParent.childTraceTree.add(traceTree);
        }

        return  traceTree;
    }
}
