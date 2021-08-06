package com.example.smartcampus.bean.statistics;

public class Major {

    private String id;
    private String majorName;
    private String collegeId;

    public Major() {

    }

    public Major(String id, String majorName, String collegeId) {
        this.id = id;
        this.majorName = majorName;
        this.collegeId = collegeId;
    }

    @Override
    public String toString() {
        return "Major{" +
                "id='" + id + '\'' +
                ", majorName='" + majorName + '\'' +
                ", collegeId='" + collegeId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }
}
