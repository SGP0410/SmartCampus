package com.example.smartcampus.bean.statistics;

import java.io.Serializable;

public class GetMunicipalMenAndWomenNumberAll implements Serializable {
    
    
    /**
     * woman : 0
     * overseasStudentNum : 1
     * municipalName : 济南市
     * id : 119
     * man : 2
     * enrollStudentNum : 83
     * provinceId : 11
     */
    
    private int woman;
    private String overseasStudentNum;
    private String municipalName;
    private String id;
    private int man;
    private String enrollStudentNum;
    private String provinceId;
    
    public int getWoman() {
        return woman;
    }
    
    public void setWoman(int woman) {
        this.woman = woman;
    }
    
    public String getOverseasStudentNum() {
        return overseasStudentNum;
    }
    
    public void setOverseasStudentNum(String overseasStudentNum) {
        this.overseasStudentNum = overseasStudentNum;
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
    
    public int getMan() {
        return man;
    }
    
    public void setMan(int man) {
        this.man = man;
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
