package com.twins.lee.controller;

import com.twins.lee.entity.AssetBill;
import com.twins.lee.entity.Author;
import com.twins.lee.entity.TradeBill;
import com.twins.lee.mapper.AssetBillMapper;
import com.twins.lee.mapper.AuthorMapper;
import com.twins.lee.mapper.TradeBillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AssetBillMapper assetBillMapper;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private TradeBillMapper tradeBillMapper;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("auth/index");
        return modelAndView;
    }


    @PostMapping("/doAuth")
    @ResponseBody
    public Map<String, String> doAuth(@RequestParam Map<String, String> person, @RequestParam(value = "balanceBill[]", required = false) String[] balanceBill) {
        Map<String, Object> map = new HashMap<>();
        TradeBill tradeBill = new TradeBill();
        tradeBill.setUserId(1);
        tradeBill.setTaxBill(person.get("bill"));
        tradeBill.setLogisticsBill(person.get("logisticsBill"));
        tradeBill.setInsuranceBill(person.get("insuranceBill"));
        tradeBill.setCustomsBill(person.get("customsBill"));
        tradeBill.setTradeContractBill(person.get("tradeBill"));
        tradeBillMapper.insert(tradeBill);

        Author author = new Author();
        author.setSocialCode(person.get("code"));
        author.setLegalPersonName(person.get("userName"));
        author.setIdCardName(person.get("name"));
        author.setIdCardSerial(person.get("idCode"));
        author.setBusinessLicenseBill(person.get("businessImg"));
        author.setIdCardDown(person.get("cardA"));
        author.setIdCardUp(person.get("cardB"));
        author.setUserId(1);
        authorMapper.insert(author);


        if (balanceBill != null && balanceBill.length > 0) {
            for (String bill : balanceBill) {
                AssetBill assetBill = new AssetBill();
                assetBill.setUserId(1);
                assetBill.setAssetBill(bill);
                assetBillMapper.insert(assetBill);
            }
        }
        return person;
    }
}
