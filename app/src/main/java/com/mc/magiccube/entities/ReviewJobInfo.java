package com.mc.magiccube.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * 职位信息
 */
public class ReviewJobInfo implements Serializable {
    public static final long serialVersionUID = 1L;
    @SerializedName("jobID")
    public long id;                         // 职位ID
    @SerializedName("firstJobFunc")
    public String firstJobFunc = "";        // 职能类别_一级
    @SerializedName("secondJobFunc")
    public String secondJobFunc = "";       // 职能类别_二级
    @SerializedName("thirdJobFunc")
    public String thirdJobFunc = "";        // 职能类别_三级
    @SerializedName("jobName")
    public String jobName = "";             // 职位名称
    @SerializedName("workCharact")
    public int workCharacter;               // 工作性质
    @SerializedName("jobLocation")
    public String jobLocation = "";         // 工作位置
    @SerializedName("minSalary")
    public int minSalary;                   // 最低月薪
    @SerializedName("maxSalary")
    public int maxSalary;                   // 最高月薪
    @SerializedName("desc")
    public String desc = "";                // 职位描述
    @SerializedName("jobTemptation")
    public String jobTemptation = "";       // 职位诱惑
    @SerializedName("city")
    public String city = "";                // 工作城市
    @SerializedName("location")
    public String location = "";            // 工作地点
    @SerializedName("minDegree")
    public int minDegree;                   // 最低学历要求
    @SerializedName("minExp")
    public int minExp;                      // 最低工作经验
    @SerializedName("jobTop")
    public int jobTop;                      // 职位置顶权重
    @SerializedName("createTime")
    public Date createTime;                 // 职位创建时间
    @SerializedName("updateTime")
    public Date updateTime;                 // 职位更新时间
    @SerializedName("jobType")
    public int jobType;                     // 职位类型
    @SerializedName("status")
    public int status;                      // 发布状态
    @SerializedName("refreshTime")
    public Date refreshTime;                // 职位刷新时间
    @SerializedName("jobSource")
    public int jobSource;                   // 职位来源
    @SerializedName("interviewTime")
    public String interviewTime = "";       // 面试时间 多个用,号隔开
    @SerializedName("interviewLocation")
    public String interviewLocation = "";   // 面试地址
    @SerializedName("contact")
    public String contact = "";             // 联系方式

    @Override
    public String toString() {
        return "ReviewJobInfo{" +
                "id=" + id +
                ", firstJobFunc='" + firstJobFunc + '\'' +
                ", secondJobFunc='" + secondJobFunc + '\'' +
                ", thirdJobFunc='" + thirdJobFunc + '\'' +
                ", jobName='" + jobName + '\'' +
                ", workCharacter=" + workCharacter +
                ", jobLocation='" + jobLocation + '\'' +
                ", minSalary=" + minSalary +
                ", maxSalary=" + maxSalary +
                ", desc='" + desc + '\'' +
                ", jobTemptation='" + jobTemptation + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", minDegree=" + minDegree +
                ", minExp=" + minExp +
                ", jobTop=" + jobTop +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", jobType=" + jobType +
                ", status=" + status +
                ", refreshTime=" + refreshTime +
                ", jobSource=" + jobSource +
                ", interviewTime='" + interviewTime + '\'' +
                ", interviewLocation='" + interviewLocation + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
