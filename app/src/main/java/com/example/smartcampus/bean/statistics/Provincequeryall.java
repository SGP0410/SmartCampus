package com.example.smartcampus.bean.statistics;

import java.io.Serializable;

public class Provincequeryall implements Serializable {

    /**
     * id : 1
     * provinceName : 河北省
     * provinceId : 1
     */

    private String id;
    private String provinceName;
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

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
