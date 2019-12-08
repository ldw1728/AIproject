package com.AIProject.howstoday;

import java.io.Serializable;
import java.util.ArrayList;

public class BoardItem implements Serializable {

    private String email;
    private String title;
    private String desc;
    private String date;
    private String userIMG;
    private String score;
    private int evalcount;
    private String bikey;
    private ArrayList<String> recommendedlist;

    public BoardItem(){
        recommendedlist = new ArrayList<>();
    }

    public BoardItem(String email, String title, String desc, String date, String userIMG,String score){
        this.email = email;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.userIMG = userIMG;
        this.score = score;
        this.evalcount = 1;
        this.recommendedlist = new ArrayList<>();

    }

    public ArrayList<String> getRecommendedList() {
        return recommendedlist;
    }

    public void setRecommendedList(ArrayList<String> recommendedList) {
        this.recommendedlist = recommendedList;
    }

    public String getBIKey() {
        return bikey;
    }

    public void setBIKey(String BIKey) {
        this.bikey = BIKey;
    }

    public int getEvalCount() {
        return evalcount;
    }

    public void setEvalCount(int evalCount) {
        this.evalcount = evalCount;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUserIMG() {
        return userIMG;
    }

    public void setUserIMG(String userIMG) {
        this.userIMG = userIMG;
    }
}
