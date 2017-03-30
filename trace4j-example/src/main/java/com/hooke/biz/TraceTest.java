package com.hooke.biz;

import com.hooke.trace.Trace;
import org.springframework.stereotype.Component;

/**
 * @author huke <huke@tiantanhehe.com>
 * @version V1.0
 * @see:
 * @since:
 * @date 2017/2/12
 */
@Component("traceTest")
public class TraceTest {

    @Trace(processName = "test",startStep = true)
    public void doSomeThing1(){
        System.out.println("doSomeThing1");
    }

    @Trace(processName = "test")
    public void doSomeThing2(){
        System.out.println("doSomeThing2");
    }

    @Trace(processName = "test")
    public int doSomeThing3(String test){
        System.out.println("doSomeThing3" + test);
        return 0;
    }

    @Trace(processName = "test")
    public String doSomeThing4(String test){
        System.out.println("doSomeThing4" + test);
        return test;
    }

}
