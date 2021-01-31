package com.wolfman.travel.config.dataConfig;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Method;
import java.util.Arrays;

//@Configuration
public class MyCacheConfig {
    @Bean("myKeyGenerator")
    public KeyGenerator myKeyGenerator() {
        return new KeyGenerator(){
            @Override
            public Object generate(Object target, Method method, Object... params) {
                System.out.println("我的策略生成器。。。");
                return method.getName()+"["+ Arrays.asList(params).toString()+target+"]";
            }
        };
    }
}