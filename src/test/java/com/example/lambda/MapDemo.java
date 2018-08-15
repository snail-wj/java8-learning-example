package com.example.lambda;

import lombok.val;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WJ
 * @date 2018/8/6
 */
public class MapDemo {

    @Test
    public void test1(){
        HashMap<String, List<String>> map = new HashMap<>();
        List<String> list1 = Arrays.asList("a");
        List<String> list2 = Arrays.asList("a", "b");
        List<String> list3 = Arrays.asList("a", "b", "c");
        List<String> list4 = Arrays.asList("a", "b", "c", "d", null);
        map.put("a", list1);
        map.put("b", list2);
        map.put("c", list3);
        map.put("d", list4);
        List<String> list = map.getOrDefault("d", Collections.emptyList()).stream().filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(list);
    }

    @Test
    public void test2(){
        HashMap<String, List<Teacher>> map = new HashMap<>();
        List<Student> student1 = Arrays.asList(new Student("a", 1));
        List<Student> student2 = Arrays.asList(new Student("a", 1), new Student("a", 1), new Student("b", 2), new Student("b", 2));
        List<Student> student3 = Arrays.asList(new Student("a", 1), new Student("a", 1), new Student("b", 2), new Student("b", 2), new Student("c", 3), new Student("c", 3));

        map.put("a", Arrays.asList(new Teacher("aTeacher", student1)));
        map.put("b", Arrays.asList(new Teacher("bTeacher", student2)));
        map.put("c", Arrays.asList(new Teacher("cTeacher", student3)));

        List<Student> studentList = map.getOrDefault("c", Collections.emptyList()).stream().filter(Objects::nonNull)
                .filter(teacher -> teacher.isStudents())
                .flatMap(teacher -> teacher.getStudentList().stream())
                .distinct()
                .collect(Collectors.toList());

        System.out.println(studentList);

    }

    /**
     * 分别对k,v进行操作
     */
    @Test
    public void test3(){
        HashMap<Long, String> map = new HashMap<>();
        map.put(1L, "ABC");
        map.put(2L, "KK");
        map.put(3L, "LV");
        map.forEach((k,v) -> System.out.println(k + "=" + v));
    }

    /**
     * 对map里面的value做操作
     */
    @Test
    public void test4(){
        List<String> list = Arrays.asList("2", "4");
        val realList = new ArrayList<>();
        HashMap<Long, List<String>> map = new HashMap<>();
        map.put(1L,Arrays.asList("1", "2"));
        map.put(2L,Arrays.asList("1", "2", "3"));
        map.put(3L,Arrays.asList("1", "2", "3", "4"));
        map.put(4L,Arrays.asList("1", "2", "3", "4"));
        map.forEach((k,v) ->{
            List<String> collect = v.stream().distinct().filter(str -> !list.contains(str)).collect(Collectors.toList());
            System.out.println(k + ":" + collect);
        });

    }
}
