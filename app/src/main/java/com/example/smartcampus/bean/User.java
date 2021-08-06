package com.example.smartcampus.bean;


public class User {

    /**
     *             "wordNatureId": "3",                    "msg": "操作成功",
     *             "shu": "28",                            "password": "123345",
     *             "povertyStudent": "0",                  "code": "200",
     *             "majorId": "13",                        "sex": "男",
     *             "sex": "男",                             "collegeId": "8",
     *             "grade": "2018级",                       "name": "戚家兴",
     *             "name": "陈强",                          "schoolCard": "1000101012",
     *             "schoolCard": "10001001",               "id": "3",
     *             "id": "1",                              "status": "辅导员"
     *             "municipalId": "273",
     */

    private String name;
    private String schoolCard;
    private String password;
    private String status;
    private String sex;
    private String collegName;
    private String idCard;
    private String address;
    private String dateOfBirth;
    private String nationality;
    private String age;
    private String face;
    private String yu;
    private String shu;
    private String wai;

    public User() {
    }

    public User(String name, String schoolCard, String password, String status, String sex, String collegName, String idCard, String address, String dateOfBirth, String nationality, String age, String face, String yu, String shu, String wai) {
        this.name = name;
        this.schoolCard = schoolCard;
        this.password = password;
        this.status = status;
        this.sex = sex;
        this.collegName = collegName;
        this.idCard = idCard;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.age = age;
        this.face = face;
        this.yu = yu;
        this.shu = shu;
        this.wai = wai;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolCard() {
        return schoolCard;
    }

    public void setSchoolCard(String schoolCard) {
        this.schoolCard = schoolCard;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCollegName() {
        return collegName;
    }

    public void setCollegName(String collegName) {
        this.collegName = collegName;
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

    public String getYu() {
        return yu;
    }

    public void setYu(String yu) {
        this.yu = yu;
    }

    public String getShu() {
        return shu;
    }

    public void setShu(String shu) {
        this.shu = shu;
    }

    public String getWai() {
        return wai;
    }

    public void setWai(String wai) {
        this.wai = wai;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", schoolCard='" + schoolCard + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", sex='" + sex + '\'' +
                ", collegName='" + collegName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", nationality='" + nationality + '\'' +
                ", age='" + age + '\'' +
                ", face='" + face + '\'' +
                ", yu='" + yu + '\'' +
                ", shu='" + shu + '\'' +
                ", wai='" + wai + '\'' +
                '}';
    }
}
