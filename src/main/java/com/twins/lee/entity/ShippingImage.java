package com.twins.lee.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.data.annotation.Id;

@TableName("shipping_images")
public class ShippingImage {
    @Id
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("shipping_id")
    private Long shippingId;
    @TableField("tax_bill")
    private String taxBill;
    @TableField("customs_bill")
    private String customsBill;
    @TableField("logistics_bill")
    private String logisticsBill;
    @TableField("insurance_bill")
    private String insuranceBill;
    @TableField("trade_contract")
    private String tradeContract;
    @TableField("exchange_img")
    private String exchangeImg;
    @TableField("tax_over")
    private String taxOver;

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

    public String getTaxBill() {
        return taxBill;
    }

    public void setTaxBill(String taxBill) {
        this.taxBill = taxBill;
    }

    public String getCustomsBill() {
        return customsBill;
    }

    public void setCustomsBill(String customsBill) {
        this.customsBill = customsBill;
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

    public String getTradeContract() {
        return tradeContract;
    }

    public void setTradeContract(String tradeContract) {
        this.tradeContract = tradeContract;
    }

    public String getExchangeImg() {
        return exchangeImg;
    }

    public void setExchangeImg(String exchangeImg) {
        this.exchangeImg = exchangeImg;
    }

    public String getTaxOver() {
        return taxOver;
    }

    public void setTaxOver(String taxOver) {
        this.taxOver = taxOver;
    }

    public Long getShippingId() {
        return shippingId;
    }

    public void setShippingId(Long shippingId) {
        this.shippingId = shippingId;
    }
}
