package com.mc.magiccube.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 个人教育经历
 */
public class ReviewPersonEducationExperience implements Serializable {
    public static final long serialVersionUID = 1L;
    @SerializedName("school")
    public String school = "";      // 学校
    @SerializedName("major")
    public String major = "";       // 专业
    @SerializedName("education")
    public int education;           // 学历
    @SerializedName("startTime")
    public int startTime;           // 开始时间 年月
    @SerializedName("endTime")
    public int endTime;             // 结束时间 年月
    @SerializedName("education")
    public int unifyEnroll;         // 是否统招 跟学历联动
    @SerializedName("schoolExp")
    public String schoolExp = "";   // 在校经历

    @Override
    public String toString() {
        return "ReviewPersonEducationExperience{" +
                "school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", education=" + education +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", unifyEnroll=" + unifyEnroll +
                ", schoolExp='" + schoolExp + '\'' +
                '}';
    }
}
