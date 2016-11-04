package com.mc.magiccube.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * 个人信息
 */
public class ReviewUserInfo implements Serializable {
    public static final long serialVersionUID = 1L;
    /**
     * 个人信息_帐号
     */
    // （以下为账号基本信息）
    @SerializedName("account")
    public String account = "";                     // 帐号
    @SerializedName("password")
    public String password = "";                    // 密码
    @SerializedName("passwordSalt")
    public String passwordSalt = "";                // 密码salt(盐)值
    @SerializedName("accountStatus")
    public int accountStatus;                       // 帐号状态
    // （以下为帐号最后登录相关）
    @SerializedName("lastLoginTime")
    public Date lastLoginTime;                      // 帐号最后登录时间 yyyy-MM-dd HH:mm:ss
    @SerializedName("lastLoginIdentity")
    public int lastLoginIdentity;                   // 帐号最后登录身份
    @SerializedName("lastLoginPlatform")
    public int lastLoginPlatform;                   // 帐号最后登录平台
    @SerializedName("lastLoginPromoChannel")
    public String lastLoginPromoChannel = "";       // 帐号最后登录推广渠道
    @SerializedName("lastLoginPromoCode")
    public String lastLoginPromoCode = "";          // 帐号最后登录推广渠道对应code
    @SerializedName("lastLoginIP")
    public String lastLoginIP = "";                 // 帐号最后登录IP
    @SerializedName("lastLoginVersion")
    public String lastLoginVersion = "";            // 帐号最后登录版本
    @SerializedName("lastLoginMethod")
    public int lastLoginMethod;                     // 帐号最后登录方式
    @SerializedName("lastLoginGPSCoord")
    public String lastLoginGPSCoordinate = "";      // 帐号最后登录GPS坐标
    @SerializedName("lastLoginGPSTime")
    public Date lastLoginGPSTime;                   // 帐号最后登录经纬度收集时间 yyyy-MM-dd HH:mm:ss
    // （以下为求职者最后登录相关）
    @SerializedName("jhLastLoginTime")
    public Date jhLastLoginTime;                    // 求职者最后登录时间 yyyy-MM-dd HH:mm:ss
    @SerializedName("jhLastLoginPlatform")
    public int jhLastLoginPlatform;                 // 求职者最后登录平台
    @SerializedName("jhLastLoginIP")
    public String jhLastLoginIP = "";               // 求职者最后登录IP
    @SerializedName("jhLastLoginVersion")
    public String jhLastLoginVersion = "";          // 求职者最后登录版本
    @SerializedName("jhLastLoginMethod")
    public int jhLastLoginMethod;                   // 求职者最后登录方式
    @SerializedName("jhLastLoginGPSCoord")
    public String jhLastLoginGPSCoordinate = "";    // 求职者最后登录GPS坐标
    @SerializedName("jhLastLoginGPSTime")
    public Date jhLastLoginGPSTime;                 // 求职者最后登录经纬度收集时间 yyyy-MM-dd HH:mm:ss
    // （以下为招聘者最后登录相关）
    @SerializedName("hrLastLoginTime")
    public Date hrLastLoginTime;                    // 招聘者最后登录时间 yyyy-MM-dd HH:mm:ss
    @SerializedName("hrLastLoginPlatform")
    public int hrLastLoginPlatform;                 // 招聘者最后登录平台
    @SerializedName("hrLastLoginIP")
    public String hrLastLoginIP = "";               // 招聘者最后登录IP
    @SerializedName("hrLastLoginVersion")
    public String hrLastLoginVersion = "";          // 招聘者最后登录版本
    @SerializedName("hrLastLoginMethod")
    public int hrLastLoginMethod;                   // 招聘者最后登录方式
    @SerializedName("hrLastLoginGPSCoord")
    public String hrLastLoginGPSCoordinate = "";    // 招聘者最后登录GPS坐标
    @SerializedName("hrLastLoginGPSTime")
    public Date hrLastLoginGPSTime;                 // 招聘者最后登录经纬度收集时间 yyyy-MM-dd HH:mm:ss
    // （以下为账号注册相关）
    @SerializedName("regTime")
    public Date regTime;                            // 帐号注册时间 yyyy-MM-dd HH:mm:ss
    @SerializedName("regPlatform")
    public int regPlatform;                         // 帐号注册平台
    @SerializedName("regPromoChannel")
    public String regPromoChannel = "";             // 帐号注册推广渠道
    @SerializedName("regPromoCode")
    public String regPromoCode = "";                // 帐号注册推广渠道对应code
    @SerializedName("regIP")
    public String regIP = "";                       // 帐号注册IP
    @SerializedName("regVersion")
    public String regVersion = "";                  // 帐号注册版本
    @SerializedName("regGPSCoord")
    public String regGPSCoordinate = "";            // 帐号注册GPS坐标
    // （以下为完善招聘者信息相关）
    @SerializedName("hrCompleteInfoTime")
    public Date hrCompleteInfoTime;                 // 完善招聘者信息时间 yyyy-MM-dd HH:mm:ss
    @SerializedName("hrCompleteInfoPlatform")
    public int hrCompleteInfoPlatform;              // 完善招聘者信息平台
    @SerializedName("hrCompleteInfoIP")
    public String hrCompleteInfoIP = "";            // 完善招聘者信息IP
    @SerializedName("hrCompleteInfoVersion")
    public String hrCompleteInfoVersion = "";       // 完善招聘者信息版本
    @SerializedName("hrCompleteInfoGPSCoord")
    public String hrCompleteInfoGPSCoordinate = ""; // 完善招聘者信息GPS坐标
    @SerializedName("hrCompleteInfoGPSTime")
    public Date hrCompleteInfoGPSTime;              // 完善招聘者信息经纬度收集时间 yyyy-MM-dd HH:mm:ss
    // （以下为完善求职者信息相关）
    @SerializedName("jhCompleteInfoTime")
    public Date jhCompleteInfoTime;                 // 完善求职者信息时间 yyyy-MM-dd HH:mm:ss
    @SerializedName("jhCompleteInfoPlatform")
    public int jhCompleteInfoPlatform;              // 完善求职者信息平台
    @SerializedName("jhCompleteInfoIP")
    public String jhCompleteInfoIP = "";            // 完善求职者信息IP
    @SerializedName("jhCompleteInfoVersion")
    public String jhCompleteInfoVersion = "";       // 完善求职者信息版本
    @SerializedName("jhCompleteInfoGPSCoord")
    public String jhCompleteInfoGPSCoordinate = ""; // 完善求职者信息GPS坐标
    @SerializedName("jhCompleteInfoGPSTime")
    public Date jhCompleteInfoGPSTime;              // 完善求职者信息经纬度收集时间 yyyy-MM-dd HH:mm:ss
    /**
     * 个人信息_资料
     */
    // (以下为公共信息）
    @SerializedName("userID")
    public long userID;                             // 用户ID
    @SerializedName("name")
    public String name = "";                        // 姓名
    @SerializedName("avatar")
    public int avatar;                              // 头像地址
    // （以下为求职者信息）
    @SerializedName("jhIdentityStatus")
    public int jhIdentityStatus;                    // 求职者身份状态
    @SerializedName("gender")
    public int gender;                              // 性别
    @SerializedName("birthdate")
    public String birthdate = "";                   // 出生日期
    @SerializedName("marriage")
    public int marriage;                            // 婚姻状态
    @SerializedName("education")
    public int education;                           // 学历 可回填
    @SerializedName("joinWorkDate")
    public String joinWorkDate = "";                // 参加工作时间 年月
    @SerializedName("email")
    public String email = "";                       // 个人邮箱
    @SerializedName("mobile")
    public String mobile = "";                      // 个人联系方式
    @SerializedName("province")
    public String province = "";                    // 当前所在地_省
    @SerializedName("city")
    public String city = "";                        // 当前所在地_市
    @SerializedName("district")
    public String district = "";                    // 当前所在地_区
    @SerializedName("qq")
    public String qq = "";                          // QQ
    @SerializedName("wechat")
    public String wechat = "";                      // 微信号
    @SerializedName("resumeUpdateTime")
    public Date resumeUpdateTime;                   // 简历更新时间 yyyy-MM-dd HH:mm:ss
    @SerializedName("website")
    public String website = "";                     // 个人网站
    // （以下为招聘者信息）
    @SerializedName("hrIdentityStatus")
    public int hrIdentityStatus;                    // 招聘者身份状态
    @SerializedName("companyID")
    public long companyID;                          // 公司ID
    @SerializedName("position")
    public String position = "";                    // 招聘者的职位
    @SerializedName("isHRAuth")
    public int isHRAuth;                            // HR认证状态
    @SerializedName("hrEmail")
    public String hrEmail = "";                     // 接收简历邮箱
    @SerializedName("telNr")
    public String telNumber = "";                   // 座机号
    /**
     * 个人信息_设置
     */
    @SerializedName("allowContactDay")
    public String allowContactDay = "";             // 接听电话/视频日 周一～周日，多选用,号分隔
    @SerializedName("allowContactStartTime")
    public Date allowContactStartTime;              // 接听电话/视频开始时间 yyyy-MM-dd HH:mm:ss
    @SerializedName("allowContactEndTime")
    public Date allowContactEndTime;                // 接听电话/视频结束时间 yyyy-MM-dd HH:mm:ss
    @SerializedName("allowHideResume")
    public int allowHideResume;                     // 是否隐藏简历
    @SerializedName("allowPushJob")
    public int allowPushJob;                        // 同意推送职位
    @SerializedName("allowSearch")
    public int allowSearch;                         // 是否允许查询
    @SerializedName("allowSMSNotify")
    public int allowSMSNotify;                      // 短信通知设置
    @SerializedName("allowWechatNotify")
    public int allowWechatNotify;                   // 微信通知设置
    /**
     * 个人信息_推荐算法需要使用字段
     */
    @SerializedName("manualTag")
    public String manualTag = "";                   // 手工标签（人工）
    @SerializedName("algoTag")
    public String algoTag = "";                     // 自动标签（算法）
    @SerializedName("algoCategoryCode")
    public String algoCategoryCode = "";            // 三级职能编码（回填）
    @SerializedName("algoCategoryName")
    public String algoCategoryName = "";            // 三级职能名称（回填）
    @SerializedName("resumeSignatureCode")
    public String resumeSignatureCode = "";         // 算法特征值
    @SerializedName("resumeQualityPro")
    public int resumeQualityPro;                    // 简历质量（产品规则）
    @SerializedName("resumeQualityAglo")
    public double resumeQualityAglo;                // 简历质量（算法打分）
    @SerializedName("commercialization")
    public double commercialization;                // 商业化打分
    @SerializedName("resumeType")
    public int resumeType;                          // 简历所属业务线
//    @SerializedName("commercialization")
//    public int commercialization;                   // 商业化打分
    @SerializedName("dayjccnt")
    public int dayjccnt;                            // 同天-同职能-同hr下的职位数量

    @Override
    public String toString() {
        return "ReviewUserInfo{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", passwordSalt='" + passwordSalt + '\'' +
                ", accountStatus=" + accountStatus +
                ", lastLoginTime=" + lastLoginTime +
                ", lastLoginIdentity=" + lastLoginIdentity +
                ", lastLoginPlatform=" + lastLoginPlatform +
                ", lastLoginPromoChannel='" + lastLoginPromoChannel + '\'' +
                ", lastLoginPromoCode='" + lastLoginPromoCode + '\'' +
                ", lastLoginIP='" + lastLoginIP + '\'' +
                ", lastLoginVersion='" + lastLoginVersion + '\'' +
                ", lastLoginMethod=" + lastLoginMethod +
                ", lastLoginGPSCoordinate='" + lastLoginGPSCoordinate + '\'' +
                ", lastLoginGPSTime=" + lastLoginGPSTime +
                ", jhLastLoginTime=" + jhLastLoginTime +
                ", jhLastLoginPlatform=" + jhLastLoginPlatform +
                ", jhLastLoginIP='" + jhLastLoginIP + '\'' +
                ", jhLastLoginVersion='" + jhLastLoginVersion + '\'' +
                ", jhLastLoginMethod=" + jhLastLoginMethod +
                ", jhLastLoginGPSCoordinate='" + jhLastLoginGPSCoordinate + '\'' +
                ", jhLastLoginGPSTime=" + jhLastLoginGPSTime +
                ", hrLastLoginTime=" + hrLastLoginTime +
                ", hrLastLoginPlatform=" + hrLastLoginPlatform +
                ", hrLastLoginIP='" + hrLastLoginIP + '\'' +
                ", hrLastLoginVersion='" + hrLastLoginVersion + '\'' +
                ", hrLastLoginMethod=" + hrLastLoginMethod +
                ", hrLastLoginGPSCoordinate='" + hrLastLoginGPSCoordinate + '\'' +
                ", hrLastLoginGPSTime=" + hrLastLoginGPSTime +
                ", regTime=" + regTime +
                ", regPlatform=" + regPlatform +
                ", regPromoChannel='" + regPromoChannel + '\'' +
                ", regPromoCode='" + regPromoCode + '\'' +
                ", regIP='" + regIP + '\'' +
                ", regVersion='" + regVersion + '\'' +
                ", regGPSCoordinate='" + regGPSCoordinate + '\'' +
                ", hrCompleteInfoTime=" + hrCompleteInfoTime +
                ", hrCompleteInfoPlatform=" + hrCompleteInfoPlatform +
                ", hrCompleteInfoIP='" + hrCompleteInfoIP + '\'' +
                ", hrCompleteInfoVersion='" + hrCompleteInfoVersion + '\'' +
                ", hrCompleteInfoGPSCoordinate='" + hrCompleteInfoGPSCoordinate + '\'' +
                ", hrCompleteInfoGPSTime=" + hrCompleteInfoGPSTime +
                ", jhCompleteInfoTime=" + jhCompleteInfoTime +
                ", jhCompleteInfoPlatform=" + jhCompleteInfoPlatform +
                ", jhCompleteInfoIP='" + jhCompleteInfoIP + '\'' +
                ", jhCompleteInfoVersion='" + jhCompleteInfoVersion + '\'' +
                ", jhCompleteInfoGPSCoordinate='" + jhCompleteInfoGPSCoordinate + '\'' +
                ", jhCompleteInfoGPSTime=" + jhCompleteInfoGPSTime +
                ", userID=" + userID +
                ", name='" + name + '\'' +
                ", avatar=" + avatar +
                ", jhIdentityStatus=" + jhIdentityStatus +
                ", gender=" + gender +
                ", birthdate='" + birthdate + '\'' +
                ", marriage=" + marriage +
                ", education=" + education +
                ", joinWorkDate='" + joinWorkDate + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", qq='" + qq + '\'' +
                ", wechat='" + wechat + '\'' +
                ", resumeUpdateTime=" + resumeUpdateTime +
                ", website='" + website + '\'' +
                ", hrIdentityStatus=" + hrIdentityStatus +
                ", companyID=" + companyID +
                ", position='" + position + '\'' +
                ", isHRAuth=" + isHRAuth +
                ", hrEmail='" + hrEmail + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", allowContactDay='" + allowContactDay + '\'' +
                ", allowContactStartTime=" + allowContactStartTime +
                ", allowContactEndTime=" + allowContactEndTime +
                ", allowHideResume=" + allowHideResume +
                ", allowPushJob=" + allowPushJob +
                ", allowSearch=" + allowSearch +
                ", allowSMSNotify=" + allowSMSNotify +
                ", allowWechatNotify=" + allowWechatNotify +
                ", manualTag='" + manualTag + '\'' +
                ", algoTag='" + algoTag + '\'' +
                ", algoCategoryCode='" + algoCategoryCode + '\'' +
                ", algoCategoryName='" + algoCategoryName + '\'' +
                ", resumeSignatureCode='" + resumeSignatureCode + '\'' +
                ", resumeQualityPro=" + resumeQualityPro +
                ", resumeQualityAglo=" + resumeQualityAglo +
                ", commercialization=" + commercialization +
                ", resumeType=" + resumeType +
                ", dayjccnt=" + dayjccnt +
                '}';
    }
}
