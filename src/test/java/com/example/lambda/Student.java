package com.example.lambda;

import lombok.Data;
import lombok.ToString;

/**
 * @author WJ
 * @date 2018/8/6
 */
@Data
@ToString
public class Student {
    public String name;
    public Integer age;

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Student(String name) {
        this.name = name;
    }
}
