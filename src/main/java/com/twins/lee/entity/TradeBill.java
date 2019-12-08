package com.twins.lee.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("trade_bills")
public class TradeBill {
    private int id;
    @TableField("user_id")
    private int userId;
    @TableField("tax_bill")
    private String taxBill;
    @TableField("logistics_bill")
    private String logisticsBill;
    @TableField("insurance_bill")
    private String insuranceBill;
    @TableField("customs_bill")
    private String customsBill;
    @TableField("trade_contract_bill")
    private String tradeContractBill;

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

    public String getTaxBill() {
        return taxBill;
    }

    public void setTaxBill(String taxBill) {
        this.taxBill = taxBill;
    }

    public String getLogisticsBill() {
        return logisticsBill;
    }

    public void setLogisticsBill(String logisticsBill) {
        this.logisticsBill = logisticsBill;
    }

    public String getInsuranceBill() {
        return insuranceBill;
    }

    public void setInsuranceBill(String insuranceBill) {
        this.insuranceBill = insuranceBill;
    }

    public String getCustomsBill() {
        return customsBill;
    }

    public void setCustomsBill(String customsBill) {
        this.customsBill = customsBill;
    }

    public String getTradeContractBill() {
        return tradeContractBill;
    }

    public void setTradeContractBill(String tradeContractBill) {
        this.tradeContractBill = tradeContractBill;
    }
}
