package com.wolfman.travel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//mybatis包扫描
@MapperScan(value = "com.wolfman.travel.mapper")
//支持缓存
@EnableCaching
@SpringBootApplication
public class TravelApplication {

    /**
     * main方法
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(TravelApplication.class, args);
    }

}
