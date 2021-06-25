package com.example.smartcampus.bean.statistics;

import java.io.Serializable;

public class GetWorkNatureNamequeryall implements Serializable {

    /**
     * workNatureName : 行政
     * id : 1
     */

    private String workNatureName;
    private String id;

    public String getWorkNatureName() {
        return workNatureName;
    }

    public void setWorkNatureName(String workNatureName) {
        this.workNatureName = workNatureName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}