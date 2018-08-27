package com.example.collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WJ
 * @date 2018/08/03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectionTest {

    /**
     * 数组去重
     */
    @Test
    public void distinctTest() {
        String[] strings = {"a", "b", "c", "b", null};
        List<String> strings1 = Arrays.stream(strings).filter(Objects::nonNull).map(String::trim).distinct().collect(Collectors.toList());
        System.out.println(strings1);

    }

    /**
     * map的排序操作
     */
    @Test
    public void mapToSortTest() {
        Map<String, Integer> smap = new HashMap<>();
        smap.put("1", 11);
        smap.put("3", 33);
        smap.put("2", 22);

        //1.8 以前
        List<Map.Entry<String, Integer>> list1 = new ArrayList<>();
        list1.addAll(smap.entrySet());
        Collections.sort(list1, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue() - o2.getValue();
            }
        });
        for (Map.Entry<String, Integer> entry : list1) {
            System.out.println("key:" + entry.getKey() + ",value:" + entry.getValue());
        }

        //1.8 使用Lambda 表达式
        List<Map.Entry<String, Integer>> list2 = new ArrayList<>(smap.entrySet());
        list2.sort(Comparator.comparing(Map.Entry::getValue));
        list2.forEach(entry ->
                System.out.println("key:" + entry.getKey() + ",value:" + entry.getValue())
        );
    }
}
