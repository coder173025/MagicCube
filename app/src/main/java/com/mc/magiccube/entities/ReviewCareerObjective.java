package com.mc.magiccube.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 求职意向
 */
public class ReviewCareerObjective implements Serializable {
    public static final long serialVersionUID = 1L;
    @SerializedName("userID")
    public long id;                     // 用户ID
    @SerializedName("status")
    public int status;                  // 求职状态
    @SerializedName("minSalary")
    public int minSalary;               // 期望工资_最小 1 ~ 100
    @SerializedName("maxSalary")
    public int maxSalary;               // 期望工资_最大 1 ~ 100
    @SerializedName("province")
    public String province = "";        // 期望工作地点_省
    @SerializedName("city")
    public String city = "";            // 期望工作地点_市
    @SerializedName("district")
    public String district = "";        // 期望工作地点_区
    @SerializedName("firstJobFunc")
    public String firstJobFunc = "";    // 职能类别_一级
    @SerializedName("secondJobFunc")
    public String secondJobFunc = "";   // 职能类别_二级
    @SerializedName("thirdJobFunc")
    public String thirdJobFunc = "";    // 职能类别_三级
    @SerializedName("exptPosition")
    public String exptPosition = "";    // 期望职位
    @SerializedName("exptType")
    public int exptType;                // 求职类型
    @SerializedName("exptIndustry")
    public long exptIndustry;           // 期望行业

    @Override
    public String toString() {
        return "ReviewCareerObjective{" +
                "id=" + id +
                ", status=" + status +
                ", minSalary=" + minSalary +
                ", maxSalary=" + maxSalary +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", firstJobFunc='" + firstJobFunc + '\'' +
                ", secondJobFunc='" + secondJobFunc + '\'' +
                ", thirdJobFunc='" + thirdJobFunc + '\'' +
                ", exptPosition='" + exptPosition + '\'' +
                ", exptType=" + exptType +
                ", exptIndustry=" + exptIndustry +
                '}';
    }
}
