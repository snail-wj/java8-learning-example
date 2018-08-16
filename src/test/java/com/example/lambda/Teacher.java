package com.example.lambda;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author WJ
 * @date 2018/8/6
 */
@Data
@ToString
public class Teacher {

    public String name;
    public List<Student> studentList;

    public Teacher(String name, List<Student> studentList) {
        this.name = name;
        this.studentList = studentList;
    }

    public boolean isStudents(){
        return studentList!=null;
    }
}
