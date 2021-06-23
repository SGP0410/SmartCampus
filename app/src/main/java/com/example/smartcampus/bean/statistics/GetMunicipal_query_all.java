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
    
    private String overseasStudentNum;
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
