package com.example.smartcampus.bean.statistics;

/**
 * @author 关鑫
 * @date 2021/8/10 8:42 星期二
 */
public class GetClassSchedule {

    /**
     * number : 1
     * classid : 20
     * teacher : 138
     * name : 尤美琳
     * course : 语文
     * id : 286
     */

    private String number;
    private String classid;
    private String teacher;
    private String name;
    private String course;
    private String id;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GetClassSchedule{" +
                "number='" + number + '\'' +
                ", classid='" + classid + '\'' +
                ", teacher='" + teacher + '\'' +
                ", name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
