package com.kevin;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by kevin on 17-6-20.
 */
public class MockitoTest {

    @Test
    public void test1(){

        List mockList = mock(List.class);
        mockList.add("one");
        mockList.add("2");
        mockList.add("3");
        mockList.add("4");

        verify(mockList).add("one");
        verify(mockList).add("2");

        when( mockList.get(0) ).thenReturn("get 0 at first").thenReturn("get 0 again");
        when( mockList.get(1) ).thenThrow( new RuntimeException("get 1 Exception") );
        when( mockList.get(2) ).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                Object mock = invocationOnMock.getMock();
                return "called with arguments: " + args;
            }
        });

        //doNothing().when( mockList ).clear();
        //doReturn(" return result ").when( mockList ).get(3);
        //doThrow( new Exception(" you get 3 ")).when( mockList ).get(3);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "self Answer";
            }
        }).when( mockList ).get(3);


        System.out.println( mockList.get(0) );
        System.out.println( mockList.get(0) );
        //System.out.println( mockList.get(1) );
        System.out.println( mockList.get(2) );
        System.out.println( mockList.get(3) );


        verify(mockList, atLeast(1)).get(0);
        verify(mockList, atMost(2)).get(0);
        verify(mockList, times(2)).get(0);
        //verify(mockList, never()).get(0);

        InOrder inOrder = inOrder(mockList);
        inOrder.verify(mockList).add("one");
        inOrder.verify(mockList).add("2");

        //spy的意思是你可以修改某个真实对象的某些方法的行为特征，而不改变他的基本行为特征，这种策略的使用跟AOP有点类似
        List list = new LinkedList();
        list.add("1");
        System.out.println( list.size() );
        List spy = spy( list );
        when( spy.size() ).thenReturn(100);
        System.out.println( spy.size() );








    }

}
