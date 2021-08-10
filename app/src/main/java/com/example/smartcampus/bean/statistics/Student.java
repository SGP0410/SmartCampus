package com.example.smartcampus.bean.statistics;

public class Student {
    /**
     * id
     * name
     * municipalId
     * povertyStudent
     * sex
     * grade
     * majorId
     * wordNatureId
     * yu
     * shu
     * wai
     * schoolCard
     * password
     * idCard
     * address
     * age
     * dateOfBirth
     * nationality
     * face
     */
    private String id;
    private String name;
    private String municipalId;
    private String povertyStudent;
    private String sex;
    private String grade;
    private String majorId;
    private String wordNatureId;
    private String yu;
    private String shu;
    private String wai;
    private String schoolCard;
    private String password;
    private String idCard;
    private String address;
    private String age;
    private String dateOfBirth;
    private String nationality;
    private String face;

    public Student() {
    }

    public Student(String id, String name, String municipalId, String povertyStudent, String sex, String grade, String majorId, String wordNatureId, String yu, String shu, String wai, String schoolCard, String password, String idCard, String address, String age, String dateOfBirth, String nationality, String face) {
        this.id = id;
        this.name = name;
        this.municipalId = municipalId;
        this.povertyStudent = povertyStudent;
        this.sex = sex;
        this.grade = grade;
        this.majorId = majorId;
        this.wordNatureId = wordNatureId;
        this.yu = yu;
        this.shu = shu;
        this.wai = wai;
        this.schoolCard = schoolCard;
        this.password = password;
        this.idCard = idCard;
        this.address = address;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
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

    public String getMunicipalId() {
        return municipalId;
    }

    public void setMunicipalId(String municipalId) {
        this.municipalId = municipalId;
    }

    public String getPovertyStudent() {
        return povertyStudent;
    }

    public void setPovertyStudent(String povertyStudent) {
        this.povertyStudent = povertyStudent;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getWordNatureId() {
        return wordNatureId;
    }

    public void setWordNatureId(String wordNatureId) {
        this.wordNatureId = wordNatureId;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", municipalId='" + municipalId + '\'' +
                ", povertyStudent='" + povertyStudent + '\'' +
                ", sex='" + sex + '\'' +
                ", grade='" + grade + '\'' +
                ", majorId='" + majorId + '\'' +
                ", wordNatureId='" + wordNatureId + '\'' +
                ", yu='" + yu + '\'' +
                ", shu='" + shu + '\'' +
                ", wai='" + wai + '\'' +
                ", schoolCard='" + schoolCard + '\'' +
                ", password='" + password + '\'' +
                ", idCard='" + idCard + '\'' +
                ", address='" + address + '\'' +
                ", age='" + age + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", nationality='" + nationality + '\'' +
                ", face='" + face + '\'' +
                '}';
    }
}
