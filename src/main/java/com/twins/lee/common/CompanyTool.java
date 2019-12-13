package com.twins.lee.common;

import com.twins.lee.entity.Company;
import com.twins.lee.utilites.Utility;

/**
 * 获得企业信息
 */
public class CompanyTool {
    public static Company getCompany() {
        Company company = new Company();
        company.setId(1L);
        company.setUserId((long) Utility.userId());
        return company;
    }

    public static final int LoanOfShipping = 1;//货贷

    public static final int OcrTypeOfCardA = 1;//身份证识别
}
