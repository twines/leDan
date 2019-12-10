package com.twins.lee.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.twins.lee.common.CompanyTool;
import com.twins.lee.entity.Company;
import com.twins.lee.entity.Shipping;
import com.twins.lee.entity.ShippingImage;
import com.twins.lee.mapper.CompanyMapper;
import com.twins.lee.mapper.ShippingImageMapper;
import com.twins.lee.mapper.ShippingMapper;
import com.twins.lee.response.Response;
import com.twins.lee.utilites.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shipping")
public class ShippingController {
    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    ShippingMapper shippingMapper;

    @Autowired
    ShippingImageMapper shippingImageMapper;

    @GetMapping("add")
    public String index() {
        Company company = companyMapper.selectByUserId(Utility.userId());
        if ( company != null) {
            //不能重复完善信息
            if (company.getStatus() == 0) {
                return "redirect:/";
            }
        }
        return "/shipping/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> add(
            @RequestParam(value = "taxBill[]") List<String> taxBill,
            @RequestParam(value = "entryBill[]") List<String> entryBill,
            @RequestParam(value = "logisticsBill[]") List<String> logisticsBill,
            @RequestParam(value = "tradeBill[]") List<String> tradeBill) {
        Map<String, Object> map = new HashMap<>();
        Company company = CompanyTool.getCompany();
        Shipping shipping = new Shipping();
        shipping.setUserId(company.getUserId());
        shipping.setType(CompanyTool.LoanOfShipping);

        int result = shippingMapper.insert(shipping);
        if (result > 0) {//先创建一个货代id，然后插入数据
            Long shippingId = shipping.getId();
            String taxBills = String.join(",", taxBill);
            String entryBills = String.join(",", entryBill);
            String logisticsBills = String.join(",", logisticsBill);
            String tradeBills = String.join(",", tradeBill);

            ShippingImage shippingImage = new ShippingImage();
            shippingImage.setUserId(company.getUserId());
            shippingImage.setShippingId(shippingId);
            shippingImage.setTaxBill(taxBills);
            shippingImage.setExchangeImg(entryBills);
            shippingImage.setLogisticsBill(logisticsBills);
            shippingImage.setTradeContract(tradeBills);

            result = shippingImageMapper.insert(shippingImage);
            if (result > 0) {
                return Response.success(shippingImage);
            } else {
                return Response.error("货贷申请异常");
            }
        } else {
            return Response.error("货贷申请异常");
        }
    }

    @GetMapping("/list")
    public String shippingList() {
        return "/shipping/list";
    }

    @GetMapping("/list.json")
    @ResponseBody
    public Map<String, Object> getShippingList() {
        Map<String, Object> map = new HashMap<>();
        IPage<Shipping> shippingIPage = new Page<>(1, 15);
        IPage<Shipping> shippingIPageList = shippingMapper.selectPage(shippingIPage, new QueryWrapper<Shipping>().orderByDesc("id").eq("type", 1));
        return Response.success(shippingIPageList);
    }
}