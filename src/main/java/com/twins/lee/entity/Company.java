package com.twins.lee.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("company")
public class Company {
    public static int Finished = 1;
    @TableField("id")
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("credit_code")
    private String creditCode;
    @TableField("user_name")
    private String userName;
    @TableField("id_number")
    private String idNumber;
    @TableField("bank_number")
    private String bankNumber;
    @TableField("mobile")
    private String mobile;
    @TableField("card_a")
    private String cardA;
    @TableField("card_b")
    private String cardB;
    @TableField("business_license")
    private String businessLicense;
    @TableField("asset_proof")
    private String assetProof;
    @TableField("status")
    private int status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCardA() {
        return cardA;
    }

    public void setCardA(String cardA) {
        this.cardA = cardA;
    }

    public String getCardB() {
        return cardB;
    }

    public void setCardB(String cardB) {
        this.cardB = cardB;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getAssetProof() {
        return assetProof;
    }

    public void setAssetProof(String assetProof) {
        this.assetProof = assetProof;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
