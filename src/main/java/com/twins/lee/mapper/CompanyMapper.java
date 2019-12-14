package com.twins.lee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.twins.lee.entity.Company;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyMapper extends BaseMapper<Company> {
    Company selectByUserId(Long userId);
}
