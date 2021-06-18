package com.example.smartcampus.bean.statistics;

import java.io.Serializable;

public class GetCollegeMenAndWomenNumberAll implements Serializable {
    
    
    /**
     * collegeName : 汽车工程系
     * woman : 53
     * collegeId : 1
     * id : 1
     * man : 122
     */
    
    private String collegeName;
    private int woman;
    private String collegeId;
    private String id;
    private int man;
    
    public String getCollegeName() {
        return collegeName;
    }
    
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
    
    public int getWoman() {
        return woman;
    }
    
    public void setWoman(int woman) {
        this.woman = woman;
    }
    
    public String getCollegeId() {
        return collegeId;
    }
    
    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
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
}
