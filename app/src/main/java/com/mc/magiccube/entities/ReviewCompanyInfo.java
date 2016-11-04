package com.mc.magiccube.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 公司信息
 */
public class ReviewCompanyInfo implements Serializable {
    public static final long serialVersionUID = 1L;
    @SerializedName("companyID")
    public long id;                 // 公司ID
    @SerializedName("logo")
    public String logo = "";        // 公司LOGO
    @SerializedName("name")
    public String name = "";        // 公司全称
    @SerializedName("shortName")
    public String shortName = "";   // 公司简称
    @SerializedName("industry")
    public int industry;            // 公司所属行业
    @SerializedName("charact")
    public int character;           // 公司性质
    @SerializedName("scale")
    public int scale;               // 公司规模
    @SerializedName("stage")
    public int stage;               // 发展阶段
    @SerializedName("benefit")
    public String benefit = "";     // 公司福利
    @SerializedName("intro")
    public String intro = "";       // 公司简介
    @SerializedName("city")
    public String city = "";        // 所在城市
    @SerializedName("address")
    public String address = "";     // 公司地址
    @SerializedName("location")
    public String location = "";    // 公司位置
    @SerializedName("website")
    public String website = "";     // 公司官网
    @SerializedName("contact")
    public String contact = "";     // 联系人手机号
    @SerializedName("email")
    public String email = "";       // 企业邮箱
    @SerializedName("status")
    public int status;              // 公司状态
    @SerializedName("creatorID")
    public long creator;            // 公司创建人ID
    @SerializedName("manager")
    public long manager;            // 公司管理者ID

    @Override
    public String toString() {
        return "ReviewCompanyInfo{" +
                "id=" + id +
                ", logo='" + logo + '\'' +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", industry=" + industry +
                ", character=" + character +
                ", scale=" + scale +
                ", stage=" + stage +
                ", benefit='" + benefit + '\'' +
                ", intro='" + intro + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", location='" + location + '\'' +
                ", website='" + website + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", creator=" + creator +
                ", manager=" + manager +
                '}';
    }
}
