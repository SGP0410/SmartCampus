package com.example.smartcampus.bean.statistics;

public class ProvinceStudentSource {


    /**
     * poorStudent : 9
     * eliteStudent : 20
     * id : 11
     * provinceName : 山东省
     * provinceId : 11
     */

    private int poorStudent;
    private int eliteStudent;
    private String id;
    private String provinceName;
    private String provinceId;

    @Override
    public String toString() {
        return "ProvinceStudentSource{" +
            "poorStudent=" + poorStudent +
            ", eliteStudent=" + eliteStudent +
            ", id='" + id + '\'' +
            ", provinceName='" + provinceName + '\'' +
            ", provinceId='" + provinceId + '\'' +
            '}';
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

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
