package com.wolfman.travel.controller;

import com.wolfman.travel.bean.Category;
import com.wolfman.travel.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CategoryController{

    @Autowired
    CategoryServiceImpl categoryService;

    @GetMapping("/category/findAll")
    @ResponseBody
    public List<Category> findAll(){
        List<Category> all = categoryService.findAll();
        return all;
    }

}
