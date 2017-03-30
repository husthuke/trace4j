package com.hooke.trace.cache; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 

/** 
* TraceTree Tester. 
* 
* @author <Authors name> 
* @since 
* @version 1.0 
*/ 
public class TestTraceTree { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: addTraceNode(long threadId, long processStepId) 
* 
*/ 
@Test
public void testAddTraceNode() throws Exception { 
//TODO: Test goes here...
    TraceTree traceTree = new TraceTree();

    TraceTree addTraceTree = traceTree.addTraceNode(1,1);

    addTraceTree = traceTree.addTraceNode(1,129);

    addTraceTree = traceTree.addTraceNode(2,16384);

    int number = traceTree.getChildNumber(1);

    number = traceTree.getChildNumber(2);

    return;

} 


} 
