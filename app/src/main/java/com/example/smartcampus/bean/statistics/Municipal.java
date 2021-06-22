package com.example.smartcampus.bean.statistics;

public class Municipal {


    /**
     * overseasStudentNum : 1
     * poorStudent : 1
     * eliteStudent : 0
     * municipalName : 济南市
     * id : 119
     * enrollStudentNum : 83
     * provinceId : 11
     */

    private String overseasStudentNum;
    private int poorStudent;
    private int eliteStudent;
    private String municipalName;
    private String id;
    private String enrollStudentNum;
    private String provinceId;

    public String getOverseasStudentNum() {
        return overseasStudentNum;
    }

    public void setOverseasStudentNum(String overseasStudentNum) {
        this.overseasStudentNum = overseasStudentNum;
    }

    public int getPoorStudent() {
        return poorStudent;
    }

    public void setPoorStudent(int poorStudent) {
        this.poorStudent = poorStudent;
    }

    public int getEliteStudent() {
        return eliteStudent;
    }

    public void setEliteStudent(int eliteStudent) {
        this.eliteStudent = eliteStudent;
    }

    public String getMunicipalName() {
        return municipalName;
    }

    public void setMunicipalName(String municipalName) {
        this.municipalName = municipalName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnrollStudentNum() {
        return enrollStudentNum;
    }

    public void setEnrollStudentNum(String enrollStudentNum) {
        this.enrollStudentNum = enrollStudentNum;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
