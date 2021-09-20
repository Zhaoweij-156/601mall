package com.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mall.dao")
public class OnlineShoppingMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShoppingMallApplication.class, args);
    }

}
