package com.twins.lee.common;

import com.twins.lee.entity.Company;

/**
 * 获得企业信息
 */
public  class CompanyTool {
    public static Company getCompany() {
        Company company = new Company();
        company.setId(1l);
        return company;
    }
}
