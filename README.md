# 框架项目介绍
本框架项目为了解决中小型企业级java项目调用流程的性能追踪问题，提供便捷快速的调用流程追踪日志记录，同时可以记录每个调用函数的耗时。
对于采用executorServic开发的多线程程序同样可以进行追踪，能够加快排查线上多线程问题。
固然目前有比如Zipkin，Drapper这些分布式追踪系统，能够全面的追踪分布式系统的调用链路，但是对于中小型的企业项目搭建及维护成本过高。
地址：https://github.com/husthuke/trace4j

# Qucik Start
在使需要开启追踪日志的项目中引用trace4j-core工程。
```
    <dependency>
        <groupId>com.hooke</groupId>
        <artifactId>trace4j-core</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
```

为需要进行日志追踪的方法增加如下注解
```
    @Trace(processName = "test",startStep = true)
    public void doSomeThing1(){
        System.out.println("doSomeThing1");
    }
```

结合spring aspect机制定义业务aop
```
     <!--Aspect的方式实现trace监测-->
     <bean id="traceAspect" class="com.hooke.trace.aop.spring.TraceAspect">
     </bean>

     <aop:config>
         <aop:pointcut id="tracePointcutAspect"
                       expression="execution(* com.hooke.biz..*(..))"/>
         <aop:aspect id="traceSpringAspect" ref="traceAspect">
             <aop:around
                     pointcut-ref="tracePointcutAspect"
                     method="trace"/>
         </aop:aspect>
     </aop:config>
```


追踪日志格式
```
    "threadId:" + 调用线程ID +
    " triceId:" + 追踪ID +
    " sessionId:" + 会话ID +
    " ProcessName:" + 追踪流程名称 +
    " ProcessStep:" + 追踪路程步骤 +
    " Joinpoint:" + aop连接点信息 +
    " argsJson:" + 方法入参信息 +
    " returnJson:" + 方法返回信息 +
    " cost:"+ 调用耗时 + " ms!"
```

运行结果示例：
```
1579 [main] INFO  c.h.trace.aop.spring.TraceAspect - threadId:1 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:1. Joinpoint:execution(void com.hooke.biz.TraceTest.doSomeThing1()) argsJson: returnJson: cost:882 ms!
1589 [main] INFO  c.h.trace.aop.spring.TraceAspect - threadId:1 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:2. Joinpoint:execution(void com.hooke.biz.TraceTest.doSomeThing1()) argsJson: returnJson: cost:0 ms!
1590 [main] INFO  c.h.trace.aop.spring.TraceAspect - threadId:1 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:3. Joinpoint:execution(void com.hooke.biz.TraceTest.doSomeThing2()) argsJson: returnJson: cost:1 ms!
1603 [main] INFO  c.h.trace.aop.spring.TraceAspect - threadId:1 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:4. Joinpoint:execution(int com.hooke.biz.TraceTest.doSomeThing3(String)) argsJson:"testmethod3" returnJson:0 cost:13 ms!
1764 [Thread-3] INFO  c.h.trace.aop.spring.TraceAspect - threadId:14 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:4.2. Joinpoint:execution(void com.hooke.biz.TraceTest.doSomeThing2()) argsJson: returnJson: cost:1 ms!
1764 [Thread-1] INFO  c.h.trace.aop.spring.TraceAspect - threadId:12 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:4.3. Joinpoint:execution(void com.hooke.biz.TraceTest.doSomeThing2()) argsJson: returnJson: cost:15 ms!
1764 [Thread-2] INFO  c.h.trace.aop.spring.TraceAspect - threadId:13 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:4.1. Joinpoint:execution(void com.hooke.biz.TraceTest.doSomeThing2()) argsJson: returnJson: cost:15 ms!
1866 [Thread-4] INFO  c.h.trace.aop.spring.TraceAspect - threadId:15 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:4.4. Joinpoint:execution(void com.hooke.biz.TraceTest.doSomeThing2()) argsJson: returnJson: cost:0 ms!
1964 [Thread-5] INFO  c.h.trace.aop.spring.TraceAspect - threadId:16 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:4.5. Joinpoint:execution(void com.hooke.biz.TraceTest.doSomeThing2()) argsJson: returnJson: cost:0 ms!
2066 [Thread-6] INFO  c.h.trace.aop.spring.TraceAspect - threadId:17 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:4.6. Joinpoint:execution(void com.hooke.biz.TraceTest.doSomeThing2()) argsJson: returnJson: cost:0 ms!
2177 [Thread-7] INFO  c.h.trace.aop.spring.TraceAspect - threadId:18 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:4.7. Joinpoint:execution(void com.hooke.biz.TraceTest.doSomeThing2()) argsJson: returnJson: cost:1 ms!
2275 [Thread-8] INFO  c.h.trace.aop.spring.TraceAspect - threadId:19 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:4.8. Joinpoint:execution(void com.hooke.biz.TraceTest.doSomeThing2()) argsJson: returnJson: cost:0 ms!
2403 [Thread-9] INFO  c.h.trace.aop.spring.TraceAspect - threadId:20 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:4.9. Joinpoint:execution(void com.hooke.biz.TraceTest.doSomeThing2()) argsJson: returnJson: cost:0 ms!
2492 [Thread-10] INFO  c.h.trace.aop.spring.TraceAspect - threadId:21 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:4.10. Joinpoint:execution(void com.hooke.biz.TraceTest.doSomeThing2()) argsJson: returnJson: cost:0 ms!
2591 [Thread-11] INFO  c.h.trace.aop.spring.TraceAspect - threadId:22 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:4.11. Joinpoint:execution(void com.hooke.biz.TraceTest.doSomeThing2()) argsJson: returnJson: cost:0 ms!
2700 [Thread-12] INFO  c.h.trace.aop.spring.TraceAspect - threadId:23 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:4.12. Joinpoint:execution(void com.hooke.biz.TraceTest.doSomeThing2()) argsJson: returnJson: cost:0 ms!
2800 [main] INFO  c.h.trace.aop.spring.TraceAspect - threadId:1 triceId:887c67ff-7681-4fea-8e9f-e7d3838766f9 sessionId:null ProcessName:test ProcessStep:5. Joinpoint:execution(void com.hooke.biz.TraceTest.doSomeThing2()) argsJson: returnJson: cost:0 ms!
```