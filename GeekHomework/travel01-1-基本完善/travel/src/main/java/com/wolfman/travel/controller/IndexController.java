package com.wolfman.travel.controller;

import com.wolfman.travel.bean.Route;
import com.wolfman.travel.service.impl.RouteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class IndexController {

    @Autowired
    RouteServiceImpl routeService;

    @GetMapping("/user/index")
    public String index(Model model) {
        List<Route> allRoutes = routeService.findAllRoutes();
        List<Route> Routes01 = getRoutes(4, allRoutes);
        List<Route> Routes02 = getRoutes(4, allRoutes);
        List<Route> Routes03 = getRoutes(4, allRoutes);
        List<Route> Routes04 = getRoutes(6, allRoutes);
        List<Route> Routes05 = getRoutes(6, allRoutes);

        model.addAttribute("Routes01",Routes01);
        model.addAttribute("Routes02",Routes02);
        model.addAttribute("Routes03",Routes03);

        model.addAttribute("Routes04",Routes04);
        model.addAttribute("Routes05",Routes05);
        return "index";
    }

    private List<Route> getRoutes(int n,List<Route> allRoutes)
    {
        List<Route> routes = new LinkedList<>();
        int index;
        for(int i=1;i<=n;i++)
        {
            index = (int) Math.floor(Math.random() * 1000 % 513);
            routes.add(allRoutes.get(index));
        }
        return routes;
    }


}
