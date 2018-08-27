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

    private static HashMap<Integer, String> map = new HashMap<Integer, String>();

    static {
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
    }

    @Test
    public void test1() {
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
    public void test2() {
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
    public void test3() {
        HashMap<Long, String> map = new HashMap<>();
        map.put(1L, "ABC");
        map.put(2L, "KK");
        map.put(3L, "LV");
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }

    /**
     * 对map里面的value做操作
     */
    @Test
    public void test4() {
        List<String> list = Arrays.asList("2", "4");
        val realList = new ArrayList<>();
        HashMap<Long, List<String>> map = new HashMap<>();
        map.put(1L, Arrays.asList("1", "2"));
        map.put(2L, Arrays.asList("1", "2", "3"));
        map.put(3L, Arrays.asList("1", "2", "3", "4"));
        map.put(4L, Arrays.asList("1", "2", "3", "4"));
        map.forEach((k, v) -> {
            List<String> collect = v.stream().distinct().filter(str -> !list.contains(str)).collect(Collectors.toList());
            System.out.println(k + ":" + collect);
        });

    }

    /**
     * getOrDefault 方法
     * 如果指定的key存在，则返回该key对应的value，如果不存在，则返回指定的值
     */
    @Test
    public void testOrDefault() {
        System.out.println(map.getOrDefault(3, "3"));
        System.out.println(map.getOrDefault(4, "d"));
    }

    /**
     * forEach 方法
     */
    @Test
    public void testForEach() {
        map.forEach((k, v) -> System.out.println("[" + k + "," + v + "]"));
    }

    /**
     * replaceAll 方法
     * 替换Map中所有Entry的value值，这个值由旧的key和value计算得出，接收参数 (K, V) -> V
     * 主要是修改value的值
     */
    @Test
    public void testReplaceAll() {
        map.replaceAll((key, value) -> (key + 1) + value);
        map.forEach((key, value) -> System.out.println("[" + key + "," + value + "]"));
    }

    /**
     * putIfAbsent 方法
     * 如果key关联的value不存在，则关联新的value值，返回key关联的旧的值
     */
    @Test
    public void testPutlfAbsent() {
        map.putIfAbsent(3, "d");
        map.putIfAbsent(4, "d");
        System.out.println(map.get(3));
        System.out.println(map.get(4));
    }

    /**
     * remove 方法
     * 接收2个参数，key和value，如果key关联的value值与指定的value值相等（equals)，则删除这个元素
     */
    @Test
    public void testRemove() {
        map.remove(1, "b");
        // 未删除成功， 输出 a
        System.out.println(map.get(1));
        map.remove(2, "b");
        // 未删除成功， 输出 a
        System.out.println(map.get(2));
    }

    /**
     * replace(K key, V oldValue, V newValue) 方法
     * 如果key关联的值与指定的oldValue的值相等，则替换成新的newValue
     */
    @Test
    public void testReplace() {
        map.replace(3, "a", "z");
        System.out.println(map.get(3));
        map.replace(1, "a", "z");

    }

    /**
     * replace(K key, V value) 方法
     * 如果map中存在key，则替换成value值，否则返回nul
     */
    @Test
    public void testReplaceKey() {
        map.replace(1, "aa");
        // 替换成功，输出新的值， aa
        System.out.println(map.get(1));
        map.replace(4, "d");
        // 不存在key为4， 输出 null
        System.out.println(map.get(4));
    }

    /**
     * computeIfAbsent 方法
     * 如果指定的key不存在，则通过指定的K -> V计算出新的值设置为key的值
     */
    @Test
    public void testComputeIfAbsent() {
        map.computeIfAbsent(1, key -> key + " computed");
        // 存在key为1，则不进行计算，输出值 a
        System.out.println(map.get(1));
        map.computeIfAbsent(4, key -> key + " computed");
        // 不存在key为4，则进行计算，输出值 4 computed
        System.out.println(map.get(4));
    }

    /**
     * computeIfPresent 方法
     * 如果指定的key存在，则根据旧的key和value计算新的值newValue, 如果newValue不为null，
     * 则设置key新的值为newValue, 如果newValue为null, 则删除该key的值
     * <p>
     * if (map.get(key) != null) {
     * V oldValue = map.get(key);
     * V newValue = remappingFunction.apply(key, oldValue);
     * if (newValue != null)
     * map.put(key, newValue);
     * else
     * map.remove(key);
     * }
     */
    @Test
    public void testComputeIfPresent() {
        map.computeIfPresent(1, (key, value) -> (key + 1) + value);
        //存在key为1， 则根据旧的key和value计算新的值，输出 2a
        System.out.println(map.get(1));
        map.computeIfPresent(2, (key, value) -> null);
        //存在key为2， 根据旧的key和value计算得到null，删除该值，输出 null
        System.out.println(map.get(2));
    }

    /**
     * merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) 方法
     * 如果指定的key不存在，则设置指定的value值，否则根据key的旧的值oldvalue，value计算出新的值newValue,
     * 如果newValue为null, 则删除该key，否则设置key的新值newValue
     */
    @Test
    public void testMerge() {
        // 存在key为1， 输出 a merge
        System.out.println(map.merge(1, "merge", (oldValue, newValue) -> oldValue + newValue));
        // 新值为null，删除key，输出 null
        System.out.println(map.merge(1, "merge", (oldValue, newValue) -> null));
        // 输出 " merge"
        System.out.println(map.merge(4, "merge", (oldValue, newValue) -> oldValue + newValue));
    }
}
