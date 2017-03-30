package com.hooke;

import com.hooke.biz.TraceTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author huke <huke@tiantanhehe.com>
 * @version V1.0
 * @see:
 * @since:
 * @date 2017/2/12
 */
public class Main {

    public static void main(String[] args){
//        TraceTest traceTest = new TraceTest();
//        traceTest.doSomeThing1();
//        traceTest.doSomeThing2();
//        traceTest.doSomeThing3("testmethod3");
//        traceTest.doSomeThing4("testmethod4");

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-applicationContext.xml");

        TraceTest traceTest = (TraceTest) context.getBean("traceTest");

        traceTest.doSomeThing1();
//        new Thread(()->traceTest.doSomeThing2()).start();
        traceTest.doSomeThing1();
//        new Thread(()->traceTest.doSomeThing2()).start();
        traceTest.doSomeThing2();
//        new Thread(()->traceTest.doSomeThing2()).start();
        traceTest.doSomeThing3("testmethod3");
//        traceTest.doSomeThing4("testmethod4");

        new Thread(()->traceTest.doSomeThing2()).start();

        new Thread(()->traceTest.doSomeThing2()).start();
//
//
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
//            executorService.submit(() -> {
//                traceTest.doSomeThing2();
////                new Thread(()->traceTest.doSomeThing2()).start();
//            });
            new Thread(()->traceTest.doSomeThing2()).start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        traceTest.doSomeThing2();

//
//
//
        executorService.shutdown();

    }
}
