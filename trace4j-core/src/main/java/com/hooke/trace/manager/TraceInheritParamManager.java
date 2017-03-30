package com.hooke.trace.manager;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取当前线程传递的参数值
 *
 * @author huke <huke@tiantanhehe.com>
 * @version V1.0
 * @see:
 * @since:
 * @date 2016 /11/17
 */
public class TraceInheritParamManager {
    private static InheritableThreadLocal<Map<String, Object>> traceParams = new InheritableThreadLocal<Map<String, Object>>() {

        protected synchronized Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }
    };

    /**
     * 获取指定名称的控制面参数.
     *
     * @param name the name
     * @return the control panel param
     */
    public static Object gettraceParam(String name) {

        return traceParams.get().get(name);

    }

    /**
     * 设置参数
     *
     * @param name  the name
     * @param value the value
     * @return the control panel param
     */
    public static Object settraceParam(String name, Object value) {

        return traceParams.get().put(name, value);

    }

    public static InheritableThreadLocal<Map<String, Object>> gettraceParams() {
        return traceParams;
    }

    public static void settraceParams(InheritableThreadLocal<Map<String, Object>> traceParams) {
        TraceInheritParamManager.traceParams = traceParams;
    }

    public static void clear(){
        traceParams.get().clear();
    }
}
