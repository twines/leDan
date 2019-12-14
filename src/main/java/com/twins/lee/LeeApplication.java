package com.twins.lee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement//开启事务管理
@SpringBootApplication
@MapperScan("com.twins.lee.mapper")//与dao层的@Mapper二选一写上即可(主要作用是扫包)
public class LeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeeApplication.class, args);
    }

}
