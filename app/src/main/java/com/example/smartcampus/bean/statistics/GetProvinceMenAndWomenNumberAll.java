package com.example.smartcampus.bean.statistics;

import java.io.Serializable;


/**
 * 获取各省的男女学生数量
 */
public class GetProvinceMenAndWomenNumberAll implements Serializable {
    
    /**
     * woman : 16
     * id : 1
     * provinceName : 河北省
     * man : 27
     * provinceId : 1
     */
    
    private String id;
    private String provinceName;
    private int man;
    private int woman;
    private String provinceId;
    
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
    
    public int getMan() {
        return man;
    }
    
    public void setMan(int man) {
        this.man = man;
    }
    
    public int getWoman() {
        return woman;
    }
    
    public void setWoman(int woman) {
        this.woman = woman;
    }
    
    public String getProvinceId() {
        return provinceId;
    }
    
    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
