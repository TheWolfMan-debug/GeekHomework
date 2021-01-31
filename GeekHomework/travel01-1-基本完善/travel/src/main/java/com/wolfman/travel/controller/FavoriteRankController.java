package com.wolfman.travel.controller;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FavoriteRankController {

    @GetMapping("/user/favoriteRank")
    public String favoriteRank()
    {
        return "favoriteRank";
    }

}
