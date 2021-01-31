package com.wolfman.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyFavoriteController {

    @GetMapping("/user/myFavorite")
    public String myFavorite()
    {
        return "myFavorite";
    }

}
