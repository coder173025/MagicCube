package com.mc.magiccube.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 个人项目经历
 */
public class ReviewPersonProjectExperience implements Serializable {
    public static final long serialVersionUID = 1L;
    @SerializedName("projectName")
    public String projectName = ""; // 项目名称
    @SerializedName("projectRole")
    public String projectRole = ""; // 项目角色
    @SerializedName("projectDesc")
    public String projectDesc = ""; // 项目描述
    @SerializedName("startTime")
    public int startTime;           // 开始时间 年月
    @SerializedName("achieve")
    public String achieve = "";     // 业绩
    @SerializedName("dutyDesc")
    public String dutyDesc = "";    // 职责描述
    @SerializedName("tool")
    public String tool = "";        // 使用工具
    @SerializedName("endTime")
    public int endTime;             // 结束时间 年月

    @Override
    public String toString() {
        return "ReviewPersonProjectExperience{" +
                "projectName='" + projectName + '\'' +
                ", projectRole='" + projectRole + '\'' +
                ", projectDesc='" + projectDesc + '\'' +
                ", startTime=" + startTime +
                ", achieve='" + achieve + '\'' +
                ", dutyDesc='" + dutyDesc + '\'' +
                ", tool='" + tool + '\'' +
                ", endTime=" + endTime +
                '}';
    }
}
