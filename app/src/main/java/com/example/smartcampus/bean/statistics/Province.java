package com.example.smartcampus.bean.statistics;

public class Province {


    /**
     * id : 19
     * provinceName : 云南省
     * provinceId : 19
     */

    private String id;
    private String provinceName;
    private String provinceId;

    @Override
    public String toString() {
        return "Province{" +
            "id='" + id + '\'' +
            ", provinceName='" + provinceName + '\'' +
            ", provinceId='" + provinceId + '\'' +
            '}';
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
