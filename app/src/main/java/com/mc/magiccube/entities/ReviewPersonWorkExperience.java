package com.mc.magiccube.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 个人工作经历
 */
public class ReviewPersonWorkExperience implements Serializable {
    public static final long serialVersionUID = 1L;
    @SerializedName("type")
    public String type = "";        // 职能类别 三级职能
    @SerializedName("name")
    public String name = "";        // 职位名称
    @SerializedName("desc")
    public String desc = "";        // 职位描述
    @SerializedName("salary")
    public int salary;              // 薪资
    @SerializedName("companyName")
    public String companyName = ""; // 公司名称
    @SerializedName("industry")
    public int industry;            // 公司行业
    @SerializedName("workStartTime")
    public int workStartTime;       // 在职起始时间 年月
    @SerializedName("workEndTime")
    public int workEndTime;         // 在职结束时间 年月
    @SerializedName("department")
    public String department = "";  // 所属部门
    @SerializedName("scale")
    public int scale;               // 公司规模
    @SerializedName("charact")
    public int character;           // 公司性质
    @SerializedName("workDesc")
    public String workDesc = "";    // 工作内容

    @Override
    public String toString() {
        return "ReviewPersonWorkExperience{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", salary=" + salary +
                ", companyName='" + companyName + '\'' +
                ", industry=" + industry +
                ", workStartTime=" + workStartTime +
                ", workEndTime=" + workEndTime +
                ", department='" + department + '\'' +
                ", scale=" + scale +
                ", character=" + character +
                ", workDesc='" + workDesc + '\'' +
                '}';
    }
}
