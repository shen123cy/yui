package com.github.kahlkn.sagiri.test.entity;

/**
 * Entity factory.
 * @author Kahle
 */
public class EntityFactory {

    public static Person getPerson(String name) {
        Person person = new Person();
        person.setName(name);
        person.setAge(22);
        person.setSex(1);
        person.setHeight(180d);
        person.setWeight(140d);
        return person;
    }

    public static Student getStudent(String name) {
        Student student = new Student();
        student.setName(name);
        student.setAge(19);
        student.setSex(1);
        student.setHeight(170d);
        student.setWeight(140d);
        student.setStudentId(319909017789L);
        student.setSchoolName("Mars Human Studies School");
        student.setNationalLanguageScore(87);
        student.setMathematicsScore(50);
        student.setEnglishScore(88);
        return student;
    }

}
