package com.bluemsun.service;

import com.bluemsun.entity.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2019/3/1.
 */
public class SMTest {
    List<Student> list = new ArrayList<>();

    @Test
    public void insertStudent() throws Exception {
        Student student = new Student();
        student.setId(2017013052);
        student.setGender(true);
        student.setName("weizh");
        student.setBirDate("19970815");
        Student student2 = new Student();
        student2.setId(201701);
        student2.setGender(true);
        student2.setName("weizh2");
        student2.setBirDate("19970815");
        Student student3 = new Student();
        student3.setId(2017013055);
        student3.setGender(true);
        student3.setName("weizh3");
        student3.setBirDate("19970815");
        list.add(student);
        list.add(student2);
        list.add(student3);
        //按学号递增排序
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("id:" + list.get(i).getId() + "===== name:" + list.get(i).getName());
        }

    }

    @Test
    public void updateStudent() throws Exception {
        Student student = new Student();
        student.setId(2017013052);
        student.setGender(true);
        student.setName("weizh");
        student.setBirDate("19970815");
        Student student2 = new Student();
        student2.setId(201701);
        student2.setGender(true);
        student2.setName("weizh2");
        student2.setBirDate("19970815");
        Student student3 = new Student();
        student3.setId(2017013055);
        student3.setGender(true);
        student3.setName("weizh3");
        student3.setBirDate("19970815");
        list.add(student);
        list.add(student2);
        list.add(student3);

        int id = 2017013052;//要修改学生的id

        //前台传来的修改后学生实体json字符串
        String studenStr = "{\"id\":2017013052,\"name\":\"weizh33333333\",\"birDate\":\"19970815\",\"gender\":true}";

        ObjectMapper mapper = new ObjectMapper();
        Student newstudent = null;
        newstudent = mapper.readValue(studenStr, Student.class);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == student.getId()) {
                //修改该学生信息
                list.get(i).setBirDate(newstudent.getBirDate());
                list.get(i).setName(newstudent.getName());
                list.get(i).setGender(newstudent.isGender());
                break;
            }
        }
        //按学号递增排序  重新输出。
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("id:" + list.get(i).getId() + "===== name:" + list.get(i).getName());
        }

    }

    @Test
    public void deleteStudent() throws Exception {
        Student student = new Student();
        student.setId(2017013052);
        student.setGender(true);
        student.setName("weizh");
        student.setBirDate("19970815");
        Student student2 = new Student();
        student2.setId(201701);
        student2.setGender(true);
        student2.setName("weizh2");
        student2.setBirDate("19970815");
        Student student3 = new Student();
        student3.setId(2017013055);
        student3.setGender(true);
        student3.setName("weizh3");
        student3.setBirDate("19970815");
        list.add(student);
        list.add(student2);
        list.add(student3);

        //要删除学生id
        String id = "2017013052";
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == Integer.parseInt(id)) {
                index = i;
                break;
            }
        }
        list.remove(index);
        //按学号递增排序  重新输出。
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("id:" + list.get(i).getId() + "===== name:" + list.get(i).getName());
        }

    }
    @Test
    public void searchByName() throws Exception {
        Student student = new Student();
        student.setId(2017013052);
        student.setGender(true);
        student.setName("weizh");
        student.setBirDate("19970815");
        Student student2 = new Student();
        student2.setId(201701);
        student2.setGender(true);
        student2.setName("weizh2");
        student2.setBirDate("19970815");
        Student student3 = new Student();
        student3.setId(2017013055);
        student3.setGender(true);
        student3.setName("weizh3");
        student3.setBirDate("19970815");
        list.add(student);
        list.add(student2);
        list.add(student3);

        //要查找的学生姓名
        String name= "weizh3";
        List<Student> students=new ArrayList<>();

        for (int i = 0; i <list.size() ; i++) {
            if(name.equals(list.get(i).getName())){
                students.add(list.get(i));
            }
        }

        //按学号递增排序  重新输出。
        Collections.sort(list);
        for ( int i = 0; i < students.size(); i++) {
            if (students.size() > 0) {
                System.out.println("id:" + students.get(i).getId() + "===== name:" + students.get(i).getName());

            }
        }

    }
}