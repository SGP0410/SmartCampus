package com.example.smartcampus.bean.statistics;

public class College {

    private String id;
    private String collegeName;
    private String collegeId;

    public College() {
    }

    public College(String id, String collegeName, String collegeId) {
        this.id = id;
        this.collegeName = collegeName;
        this.collegeId = collegeId;
    }

    @Override
    public String toString() {
        return "College{" +
                "id='" + id + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", collegeId='" + collegeId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }
}
