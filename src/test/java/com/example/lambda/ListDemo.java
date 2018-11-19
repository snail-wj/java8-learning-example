package com.example.lambda;

import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author WJ
 * @date 2018/7/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ListDemo {

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
        List<Apple> filterList = appleList.stream().filter(a -> a.getName().equals("香蕉123")).collect(Collectors.toList());
        List<Apple> filterList2 = appleList.stream().filter(a -> a.getId() == 1).collect(Collectors.toList());
        System.out.println(filterList.size());
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
     * 获取某一属性
     */
    @Test
    public void propertyName(){
        List<String> list1 = appleList.stream().map(Apple::getName).collect(Collectors.toList());
        System.out.println(list1);
        //去重操作
        List<String> list2 = appleList.stream().map(Apple::getName).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        System.out.println(list2);
        //字符转变操作
        List<String> list3 = appleList.stream().map(apple -> apple.getName()).collect(Collectors.toList());
        System.out.println(list3);
    }


    /**
     * Collections.singletonList的用法
     */
    @Test
    public void testSingleton(){
        List<String> list = Collections.singletonList("a");
        //会报错，singletonList方法返回一个不可变的集合包含指定对象
//        list.add("b");
        System.out.println(list);
    }

    /**
     * 多个map的操作
     */
    @Test
    public void testMultiMap(){
        List<String> list = Arrays.asList("student1", "student2", "student3");
        List<Teacher> teacher = list.stream().map(name -> new Student(name))
                .map(student -> new Teacher("teacher", Arrays.asList(student))).collect(Collectors.toList());
        System.out.println(teacher);
    }

    @Test
    public void testListByFirst(){
        Optional<Apple> first = appleList.stream().findFirst();
        System.out.println(first.get().getName());
    }


}
