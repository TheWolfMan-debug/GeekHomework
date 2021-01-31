package com.wolfman.travel.service.impl;


import com.wolfman.travel.bean.Category;
import com.wolfman.travel.mapper.CategoryMapper;
import com.wolfman.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }
}
