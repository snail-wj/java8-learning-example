package com.example.lambda;

import lombok.Data;

/**
 * @author WJ
 * @date 2018/8/6
 */
@Data
public class Student {
    public String name;
    public Integer age;

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
