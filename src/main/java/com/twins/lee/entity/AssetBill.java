package com.twins.lee.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("asset_bills")
public class AssetBill {
    @TableId
    private int id;
    @TableField("user_id")
    private int userId;
    @TableField("asset_bill")
    private String assetBill;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAssetBill() {
        return assetBill;
    }

    public void setAssetBill(String assetBill) {
        this.assetBill = assetBill;
    }
}
