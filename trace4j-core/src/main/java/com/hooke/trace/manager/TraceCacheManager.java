package com.hooke.trace.manager;

import com.hooke.trace.cache.TraceTree;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huke <huke@tiantanhehe.com>
 * @version V1.0
 * @see:
 * @since:
 * @date 2017/2/15
 */
public class TraceCacheManager {


    private static TraceCacheManager traceCacheManager;
    private ConcurrentHashMap<UUID,TraceTree> traceTreeConcurrentHashMap;

    private TraceCacheManager(){
        traceTreeConcurrentHashMap = new ConcurrentHashMap<UUID,TraceTree>();
    }

    //可以进行双检查优化
    public static synchronized TraceCacheManager getInstance(){

        if(traceCacheManager == null){
            traceCacheManager = new TraceCacheManager();
        }

        return traceCacheManager;
    }

    public TraceTree putTraceTree(UUID traceId, TraceTree traceTree){
        return traceTreeConcurrentHashMap.put(traceId,traceTree);
    }

    public TraceTree getTraceTree(UUID traceId){
        if (traceId == null) {
            return null;
        }

        return traceTreeConcurrentHashMap.get(traceId);
    }

    public TraceTree removeTraceTree(UUID traceId){
        if (traceId == null) {
            return null;
        }

        return traceTreeConcurrentHashMap.remove(traceId);
    }


}
