package com.example.smartcampus.bean.statistics;

public class Teacher {

    /**
     * id
     * name
     * sex
     * schoolCard
     * collegeId
     * password
     * status
     * idCard
     * address
     * dateOfBirth
     * nationality
     * age
     * face
     * face
     */

    private String id;
    private String name;
    private String sex;
    private String schoolCard;
    private String collegeId;
    private String password;
    private String status;
    private String idCard;
    private String address;
    private String dateOfBirth;
    private String nationality;
    private String age;
    private String face;

    public Teacher() {
    }

    public Teacher(String id, String name, String sex, String schoolCard, String collegeId, String password, String status, String idCard, String address, String dateOfBirth, String nationality, String age, String face) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.schoolCard = schoolCard;
        this.collegeId = collegeId;
        this.password = password;
        this.status = status;
        this.idCard = idCard;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.age = age;
        this.face = face;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSchoolCard() {
        return schoolCard;
    }

    public void setSchoolCard(String schoolCard) {
        this.schoolCard = schoolCard;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", schoolCard='" + schoolCard + '\'' +
                ", collegeId='" + collegeId + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", idCard='" + idCard + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", nationality='" + nationality + '\'' +
                ", age='" + age + '\'' +
                ", face='" + face + '\'' +
                '}';
    }
}
