package com.example.smartcampus.bean.statistics;

import java.io.Serializable;

public class GetProvinceRecruitStudentNumber implements Serializable {
    
    /**
     * overseasStudentNum : 14
     * id : 1
     * provinceName : 河北省
     * enrollStudentNum : 67
     * provinceId : 1
     */
    
    private int overseasStudentNum;
    private String id;
    private String provinceName;
    private int enrollStudentNum;
    private String provinceId;
    
    public int getOverseasStudentNum() {
        return overseasStudentNum;
    }
    
    public void setOverseasStudentNum(int overseasStudentNum) {
        this.overseasStudentNum = overseasStudentNum;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getProvinceName() {
        return provinceName;
    }
    
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
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
