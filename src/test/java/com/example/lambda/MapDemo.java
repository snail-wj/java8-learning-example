package com.example.lambda;

import org.junit.Test;

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
}
