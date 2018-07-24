package com.example.example.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author WJ
 * @date 2018/7/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTest1 {

    private static List<Apple> appleList;

    static {
        Apple apple1 =  new Apple(1,"苹果1",new BigDecimal("3.25"),10);
        Apple apple2 = new Apple(1,"苹果2",new BigDecimal("1.35"),20);
        Apple apple3 =  new Apple(2,"香蕉",new BigDecimal("2.89"),30);
        Apple apple4 =  new Apple(3,"荔枝",new BigDecimal("9.99"),40);

        appleList = Arrays.asList(apple1, apple2, apple3,apple4);
    }

    @Test
    public void listToMapTest(){
        Map<Integer, Apple> appleMap = appleList.stream().collect(Collectors.toMap(Apple::getId, a -> a, (p1, p2) -> p1));
        System.out.println(appleMap);
    }
}
