package com.klmall.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @program: klmall
 * @description: decimall 测试类
 * @author: kang.lu
 * @create: 2018-05-04 21:01
 **/
public class BigDecimalTest {

    @Test
    public void test1(){
        System.out.println(0.05+0.01);
        System.out.println(1.0-0.12);
        System.out.println(4.015*100);
        System.out.println(123.3/100);
    }

    @Test
    public void test2(){
        BigDecimal b1 = new BigDecimal(0.05);
        BigDecimal b2 = new BigDecimal(0.01);
        System.out.println(b1.add(b2));
    }

    @Test
    public void test3(){
        BigDecimal b1 = new BigDecimal("0.05");
        BigDecimal b2 = new BigDecimal("0.01");
        System.out.println(b1.add(b2));
    }
}
