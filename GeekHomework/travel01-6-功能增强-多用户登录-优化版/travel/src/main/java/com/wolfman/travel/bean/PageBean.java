package com.wolfman.travel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 分页对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean<T> {

    private int totalCount;//总记录数
    private int totalPage;//总页数
    private int currentPage;//当前页码
    private int pageSize;//每页显示的条数
    private List<T> list;//每页显示的数据集合

}
