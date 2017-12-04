package org.deeplearning.java;

import org.deeplearning.one.Student;

public class Test {

    public static void main(String[] args) {
        Student student = new Student("stu", true);
        student.setMarried(false);
        System.out.println(student.getName());
        System.out.println(student.isMarried());
        System.out.println(student.isMarried());
    }
}
