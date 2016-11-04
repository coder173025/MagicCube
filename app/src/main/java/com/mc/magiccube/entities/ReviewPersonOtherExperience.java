package com.mc.magiccube.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * 个人其它经历
 */
public class ReviewPersonOtherExperience implements Serializable {
    public static final long serialVersionUID = 1L;
    @SerializedName("advantage")
    public String advantage = "";   // 个人优势
    @SerializedName("selfEval")
    public String selfEval = "";    // 自我评价
    @SerializedName("language")
    public String language = "";    // 掌握语言 多个用,号分割
    @SerializedName("resumeCreateTime")
    public Date resumeCreateTime;   // 简历创建时间
    @SerializedName("resumeSource")
    public int resumeSource;        // 简历来源
    @SerializedName("skill")
    public String skill = "";       // 专业技能

    @Override
    public String toString() {
        return "ReviewPersonOtherExperience{" +
                "advantage='" + advantage + '\'' +
                ", selfEval='" + selfEval + '\'' +
                ", language='" + language + '\'' +
                ", resumeCreateTime=" + resumeCreateTime +
                ", resumeSource=" + resumeSource +
                ", skill='" + skill + '\'' +
                '}';
    }
}
