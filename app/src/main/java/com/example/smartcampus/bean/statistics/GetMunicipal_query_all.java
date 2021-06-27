package com.example.smartcampus.bean.statistics;

import java.io.Serializable;

public class GetMunicipal_query_all implements Serializable {
    
    /**
     * overseasStudentNum : 0
     * municipalName : 石家庄市
     * id : 1
     * enrollStudentNum : 4
     * provinceId : 1
     */
    
    private int overseasStudentNum;
    private String municipalName;
    private String id;
    private int enrollStudentNum;
    private String provinceId;
    
    public int getOverseasStudentNum() {
        return overseasStudentNum;
    }
    
    public void setOverseasStudentNum(int overseasStudentNum) {
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
    
    public int getEnrollStudentNum() {
        return enrollStudentNum;
    }
    
    public void setEnrollStudentNum(int enrollStudentNum) {
        this.enrollStudentNum = enrollStudentNum;
    }
    
    public String getProvinceId() {
        return provinceId;
    }
    
    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
