package com.example.lambda;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        Apple apple1 = new Apple(1, "苹果", new BigDecimal("3.25"), 10);
        Apple apple2 = new Apple(1, "苹果", new BigDecimal("1.35"), 20);
        Apple apple3 = new Apple(2, "香蕉", new BigDecimal("2.89"), 30);
        Apple apple4 = new Apple(3, "荔枝", new BigDecimal("9.99"), 40);

        appleList = Arrays.asList(apple1, apple2, apple3, apple4);

    }

    /**
     * List -> Map
     */
    @Test
    public void listToMapTest() {
        Map<Integer, Apple> appleMap = appleList.stream().collect(Collectors.toMap(Apple::getId, a -> a, (p1, p2) -> p1));
        System.out.println(appleMap);
    }

    /**
     * 分组
     */
    @Test
    public void groupByTest() {
        Map<Integer, List<Apple>> groupById = appleList.stream().collect(Collectors.groupingBy(Apple::getId));
        Map<String, List<Apple>> groupById2 = appleList.stream().collect(Collectors.groupingBy(Apple::getName));
        System.out.println(groupById);
        System.out.println(groupById2);
    }

    /**
     * 过滤filter
     */
    @Test
    public void filterTest() {
        List<Apple> filterList = appleList.stream().filter(a -> a.getName().equals("香蕉")).collect(Collectors.toList());
        List<Apple> filterList2 = appleList.stream().filter(a -> a.getId() == 1).collect(Collectors.toList());
        System.out.println(filterList);
        System.out.println(filterList2);
        System.out.println(appleList);
    }

    /**
     * 求和
     */
    @Test
    public void addTest() {
        //计算总金额
        BigDecimal totalMoney = appleList.stream().map(Apple::getMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(totalMoney);

        //计算数量
        int sum = appleList.stream().mapToInt(Apple::getNum).sum();
        System.out.println(sum);
    }

    /**
     * 分组
     * id1 [num1, num2]
     * id2 [num1]
     */
    @Test
    public void groupBy1Test() {
        Map<Integer, List<Integer>> groupByIdThenByNum = appleList.stream().collect(Collectors.groupingBy(Apple::getId, Collectors.mapping(Apple::getNum, Collectors.toList())));
        System.out.println(groupByIdThenByNum);
    }

    @Test
    public void groupBy2Test(){
        Map<String, List<BigDecimal>> collect = appleList.stream().collect(Collectors.groupingBy(Apple::getName, Collectors.mapping(Apple::getMoney, Collectors.toList())));
        System.out.println(collect);
    }

    /**
     * 数组去重
     */
    @Test
    public void distinctTest(){
        String[] strings = {"a", "b", "c", "b", null};
        List<String> strings1 = Arrays.stream(strings).filter(Objects::nonNull).map(String::trim).distinct().collect(Collectors.toList());
        System.out.println(strings1);

    }
}
