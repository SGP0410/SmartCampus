package com.example.smartcampus.bean.statistics;

/**
 * @author 关鑫
 * @date 2021/8/17 9:10 星期二
 */
public class Feedback {

    /**
     * msg : 333
     * schoolCard : 10001002
     * id : 10
     * state : 已提交
     * time : 2021-08-09-06:49:10
     * title : 123
     */

    private String msg;
    private String schoolCard;
    private String id;
    private String state;
    private String time;
    private String title;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSchoolCard() {
        return schoolCard;
    }

    public void setSchoolCard(String schoolCard) {
        this.schoolCard = schoolCard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
