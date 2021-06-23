package com.example.smartcampus.bean.statistics;

import java.util.List;

public class Provinces {
    
    private String id;
    private String provinceName;
    private String provinceId;
    private List<GetMunicipalMenAndWomenNumberAll> getMunicipalMenAndWomenNumberAll;
    
    public List<GetMunicipalMenAndWomenNumberAll> getGetMunicipalMenAndWomenNumberAll() {
        return getMunicipalMenAndWomenNumberAll;
    }
    
    public void setGetMunicipalMenAndWomenNumberAll(
        List<GetMunicipalMenAndWomenNumberAll> getMunicipalMenAndWomenNumberAll) {
        this.getMunicipalMenAndWomenNumberAll = getMunicipalMenAndWomenNumberAll;
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
