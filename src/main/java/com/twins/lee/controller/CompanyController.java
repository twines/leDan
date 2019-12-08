package com.twins.lee.controller;

import com.twins.lee.common.CompanyTool;
import com.twins.lee.entity.Company;
import com.twins.lee.mapper.CompanyMapper;
import com.twins.lee.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyMapper companyMapper;

    @GetMapping("/auth")
    public String auth() {
        return "/company/auth";
    }

    @PostMapping("/doAuth")
    @ResponseBody
    public Map<String, Object> aoAuth(String creditCode,
                                      String userName,
                                      String idNumber,
                                      String bankNumber,
                                      String mobile,
                                      String cardA,
                                      String cardB,
                                      String businessLicense,
                                      @RequestParam(value = "assetProof[]") List<String> assetProof) {
        Company company = CompanyTool.getCompany();

        company.setCreditCode(creditCode);
        company.setUserName(userName);
        company.setIdNumber(idNumber);
        company.setBankNumber(bankNumber);
        company.setMobile(mobile);
        company.setCardA(cardA);
        company.setCardB(cardB);
        company.setBusinessLicense(businessLicense);

        company.setAssetProof(String.join(",", assetProof));
        company.setStatus(1);

        int result = companyMapper.insert(company);
        if (result > 0) {
            return Response.success(company);
        }else {
            return Response.error("企业信息完善异常,请刷新浏览器重试");
        }

//        Map<String, Object> map = new HashMap<>();
//        return Response.success(map);
    }
}
