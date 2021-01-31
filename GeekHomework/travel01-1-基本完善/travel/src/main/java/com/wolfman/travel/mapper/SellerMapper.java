package com.wolfman.travel.mapper;

import com.wolfman.travel.bean.Seller;
import org.apache.ibatis.annotations.Select;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

public interface SellerMapper {
    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Select("select * from tab_seller where sid = #{id}")
    Seller findById(int id);
}
