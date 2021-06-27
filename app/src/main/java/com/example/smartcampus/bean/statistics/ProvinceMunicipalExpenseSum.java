package com.example.smartcampus.bean.statistics;

import java.util.List;

public class ProvinceMunicipalExpenseSum {


    /**
     * id : 1
     * provinceName : 河北省
     * municipalExpenditure : [{"expenditure":2341,"overseasStudentNum":"0","municipalName":"石家庄市","id":"1","enrollStudentNum":"4","provinceId":"1"},{"expenditure":1206,"overseasStudentNum":"0","municipalName":"唐山市","id":"2","enrollStudentNum":"10","provinceId":"1"},{"expenditure":0,"overseasStudentNum":"2","municipalName":"秦皇岛市","id":"3","enrollStudentNum":"4","provinceId":"1"},{"expenditure":2734,"overseasStudentNum":"3","municipalName":"邯郸市","id":"4","enrollStudentNum":"9","provinceId":"1"},{"expenditure":2855,"overseasStudentNum":"3","municipalName":"邢台市","id":"5","enrollStudentNum":"4","provinceId":"1"},{"expenditure":2358,"overseasStudentNum":"3","municipalName":"保定市","id":"6","enrollStudentNum":"8","provinceId":"1"},{"expenditure":2043,"overseasStudentNum":"3","municipalName":"张家口市","id":"7","enrollStudentNum":"8","provinceId":"1"},{"expenditure":5280,"overseasStudentNum":"0","municipalName":"承德市","id":"8","enrollStudentNum":"2","provinceId":"1"},{"expenditure":3512,"overseasStudentNum":"0","municipalName":"沧州市","id":"9","enrollStudentNum":"6","provinceId":"1"},{"expenditure":1104,"overseasStudentNum":"0","municipalName":"廊坊市","id":"10","enrollStudentNum":"10","provinceId":"1"},{"expenditure":4474,"overseasStudentNum":"0","municipalName":"衡水市","id":"11","enrollStudentNum":"2","provinceId":"1"}]
     * provinceId : 1
     */

    private String id;
    private String provinceName;
    private String provinceId;
    private List<MunicipalExpenditureBean> municipalExpenditure;

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

    public List<MunicipalExpenditureBean> getMunicipalExpenditure() {
        return municipalExpenditure;
    }

    public void setMunicipalExpenditure(List<MunicipalExpenditureBean> municipalExpenditure) {
        this.municipalExpenditure = municipalExpenditure;
    }

    public static class MunicipalExpenditureBean {

        /**
         * expenditure : 2341
         * overseasStudentNum : 0
         * municipalName : 石家庄市
         * id : 1
         * enrollStudentNum : 4
         * provinceId : 1
         */

        private int expenditure;
        private String overseasStudentNum;
        private String municipalName;
        private String id;
        private String enrollStudentNum;
        private String provinceId;

        public int getExpenditure() {
            return expenditure;
        }

        public void setExpenditure(int expenditure) {
            this.expenditure = expenditure;
        }

        public String getOverseasStudentNum() {
            return overseasStudentNum;
        }

        public void setOverseasStudentNum(String overseasStudentNum) {
            this.overseasStudentNum = overseasStudentNum;
        }

        public String getMunicipalName() {
            return municipalName;
        }

        public void setMunicipalName(String municipalName) {
            this.municipalName = municipalName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEnrollStudentNum() {
            return enrollStudentNum;
        }

        public void setEnrollStudentNum(String enrollStudentNum) {
            this.enrollStudentNum = enrollStudentNum;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }
    }
}
