package com.bluemsun.web;

import com.bluemsun.entity.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2019/2/26.
 */
@Controller
@RequestMapping("/student")
public class StudentManager {

     private Student student1; // 编号12  类属性初始化
     private List<Student> list1;
    private static Logger logger = LoggerFactory.getLogger(StudentManager.class);//编号59 日志处理

    /**
     * 进入程序
     * @param request
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> loginController(HttpServletRequest request) {
        Map<String, Object> moudelMap = new HashMap<String, Object>();
        List<Student> studentList=new ArrayList<>();
        request.getSession().setAttribute("studentList",studentList);
        moudelMap.put("state", 1);
        moudelMap.put("msg", "进入成功");
        return moudelMap;
    }

    /**
     * 插入学生
     * @param request
     * @return moudelMap
     */
    @RequestMapping(value = "/insertStudent", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> insertStudent(HttpServletRequest request) {
        Map<String, Object> moudelMap = new HashMap<String, Object>();
        List<Student> list=new ArrayList<>();
         list=( List<Student>)request.getSession().getAttribute("studentList");
         if(list.size()>20){
             moudelMap.put("state", -1);
             moudelMap.put("errMsg","数组已满");
             return  moudelMap;
         }
        String studentStr = request.getParameter( "studentStr");
        ObjectMapper mapper = new ObjectMapper();
        Student student = null;
        try {
            student = mapper.readValue(studentStr, Student.class);
            //判断学号是否溢出
            if(reverse2(student.getId())==1){
                moudelMap.put("state", -1);
                moudelMap.put("errMsg", "您输入的学号过大");
                return moudelMap;
            }
        } catch (IOException e) {
            moudelMap.put("state", -1);
            moudelMap.put("errMsg", "您输入的数据不合法");
            e.printStackTrace();
            logger.error(e.getMessage());
            return moudelMap;
        }
        for (int i = 0; i <list.size() ; i++) {
            if(list.get(i).getId()==student.getId()){
                moudelMap.put("state", -1);
                moudelMap.put("errMsg","该学号已存在");
                return  moudelMap;
            }
        }
        list.add(student);
        request.getSession().setAttribute("studentList",list);
        moudelMap.put("state", 1);
        moudelMap.put("msg","添加成功");
        list=null;// 编号60 局部变量使用完置
        return moudelMap;
    }

    /**
     * 显示所有学生
     * @param request
     * @return moudelMap
     */
    @RequestMapping(value = "/showAllStudents", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> showAllStudents(HttpServletRequest request) {
        Map<String, Object> moudelMap = new HashMap<String, Object>();
        List<Student> list=( List<Student>)request.getSession().getAttribute("studentList");
        //按学号顺序排序
        Collections.sort(list);

        if (list.size()>0){
            moudelMap.put("state", 1);
            moudelMap.put("studentList", list);
        }else{
            moudelMap.put("state", -1);
            moudelMap.put("errMsg", "暂无学生信息");
        }
        list=null;//编号60 局部变量使用完置空

        return moudelMap;
    }

    /**
     * 删除学生
     * @param request
     * @return moudelMap
     */
    @RequestMapping(value = "/deleteStudent", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> deleteStudent(HttpServletRequest request) {
        Map<String, Object> moudelMap = new HashMap<String, Object>();
        List<Student> list=( List<Student>)request.getSession().getAttribute("studentList");
         String id= request.getParameter("id");
         try {
             int index=0;
             for(int i=0;i<list.size();i++){
                 if(list.get(i).getId()==Integer.parseInt(id)){
                     index=i;
                     break;
                 }else{

                 }
             }
             list.remove(index);
         }catch (Exception ex){
             logger.error(ex.getMessage());//编号59 错误日志记录
         }
        request.getSession().setAttribute("studentList",list);
        moudelMap.put("state", 1);
        moudelMap.put("msg","删除成功");

        return moudelMap;
    }

    /**
     * 按姓名查询某个学生
     * @param request
     * @return moudelMap
     */
    @RequestMapping(value = "/searchByName", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> searchByName(HttpServletRequest request) {
        Map<String, Object> moudelMap = new HashMap<String, Object>();
        List<Student> list=( List<Student>)request.getSession().getAttribute("studentList");
        String name= request.getParameter("name");
        List<Student> students=new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            if(name.equals(list.get(i).getName())){
                students.add(list.get(i));
            }
        }
        if(students.size()>0){
            moudelMap.put("state", 1);
            moudelMap.put("students", students);
        }else{
            moudelMap.put("state", -1);
            moudelMap.put("errMsg", "未找到该学生");
        }
        students=null;// 编号29 局部变量使用后置空
        return moudelMap;
    }

    /**
     * 修改学生
     * @param request
     * @return moudelMap
     */
    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> updateStudent(HttpServletRequest request) {
        Map<String, Object> moudelMap = new HashMap<String, Object>();
        List<Student> list=( List<Student>)request.getSession().getAttribute("studentList");
        String studentStr = request.getParameter( "studentStr");
        ObjectMapper mapper = new ObjectMapper();
        Student student = null;
        try {
            student = mapper.readValue(studentStr, Student.class);
        } catch (IOException e) {
            moudelMap.put("state", -1);
            moudelMap.put("errMsg", e.getMessage());
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        for (int i = 0; i <list.size() ; i++) {
            if(list.get(i).getId()==student.getId()){
                //修改该学生信息
                list.get(i).setBirDate(student.getBirDate());
                list.get(i).setName(student.getName());
                list.get(i).setGender(student.isGender());
                break;
            }else{

            } //编号80
        }
        request.getSession().setAttribute("studentList",list);
        moudelMap.put("state", 1);
        moudelMap.put("msg","修改成功");
        list=null;//使用完的对象置空。 特别是集合对象
        return moudelMap;

    }

    /**
     * 退出程序
     * @param request
     * @return moudelMap
     */
    @RequestMapping(value = "/quite", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> quite(HttpServletRequest request) {
        Map<String, Object> moudelMap = new HashMap<String, Object>();
        request.getSession().invalidate();
        moudelMap.put("state", 1);
        moudelMap.put("msg", "退出成功");
        return moudelMap;
    }


    // 判断学号溢出 溢出的话返回1   编号20反复进行了int返回值判断是否定义了函数来处理？
    public int reverse2(int x) {
        double ans=0;
        int flag=1;
        if(x<0) //18 单个条件语句去掉{ }
            flag=-1;
        x=x*flag;
        while(x>0) {
            ans = 10 * ans + x % 10;
            x = x / 10;
        }
        if(ans>Integer.MAX_VALUE){//判断是否溢出
            System.out.println(ans);
            return 0;
        }
        else
            return (int)(flag*ans);

    }
}

