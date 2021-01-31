package com.wolfman.travel.controller;

import com.wolfman.travel.bean.Favorite;
import com.wolfman.travel.bean.PageBean;
import com.wolfman.travel.bean.Route;
import com.wolfman.travel.bean.User;
import com.wolfman.travel.service.impl.FavoriteServiceImpl;
import com.wolfman.travel.service.impl.RouteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;


@Controller
public class RouteController {

    @Autowired
    RouteServiceImpl routeService;

    @Autowired
    FavoriteServiceImpl favoriteService;

    @GetMapping("/user/routeList")
    public String routeList() {
        return "routeList";
    }


    @GetMapping("/user/routeDetail")
    public ModelAndView routeDetail(int rid, ModelAndView modelAndView) {
        Route routeDetail = routeService.findOne(rid);
        modelAndView.setViewName("routeDetail");
        modelAndView.addObject("routeDetail", routeDetail);
        return modelAndView;
    }

    @ResponseBody
    @GetMapping("/user/routeDetail/findOne")
    public Route routeFindOne(int rid) {
        Route routeDetail = routeService.findOne(rid);

        return routeDetail;
    }


    @ResponseBody
    @GetMapping("route/isFavorite")
    public boolean routeIsFavorite(String rid, HttpSession session) {
        //2. 获取当前登录的用户 user
        User user = (User) session.getAttribute("loginUser");
        String uid = String.valueOf(user.getUid());
        //3. 调用FavoriteService查询是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);
        return flag;
    }

    @GetMapping("/route/addFavorite")
    @ResponseBody
    public boolean addFavorite(String rid, HttpSession session) {
        //2. 获取当前登录的用户
        User user = (User) session.getAttribute("loginUser");
        int uid = user.getUid();//用户id

        //4. 查询该地址收藏数量
        int favoriteCount = routeService.findCount(rid);

        //5. 将数量+1
        favoriteCount++;
        routeService.update(rid, favoriteCount);
        Date date = new Date();
        //6. 添加favorite表中的信息
        favoriteService.add(rid, uid, date);
        return true;
    }

    @GetMapping("/route/deleteFavorite")
    @ResponseBody
    public boolean deleteFavorite(String rid, HttpSession session) {
        //2. 获取当前登录的用户
        User user = (User) session.getAttribute("loginUser");
        int uid = user.getUid();//用户id

        //4. 查询该地址收藏数量
        int favoriteCount = routeService.findCount(rid);

        //5. 将数量+1
        favoriteCount--;

        routeService.update(rid, favoriteCount);

        //6. 删除favorite表中的信息
        favoriteService.delete(rid, uid);
        return true;
    }


    @ResponseBody
    @GetMapping("/user/routeList/page")
    public PageBean<Route> routeListPageQuery(String cid, String currentPage, String rName, String pageSize) {
        int cidInt = 0;
        if (cid != null && cid.length() > 0 && !"null".equals(cid)) {
            cidInt = Integer.parseInt(cid);
        }
        int currentPageInt = 0;//当前页码，如果不传递，则默认为第一页
        if (currentPage != null && currentPage.length() > 0) {
            currentPageInt = Integer.parseInt(currentPage);
        } else {
            currentPageInt = 1;
        }
        int pageSizeInt;//每页显示条数，如果不传递，默认每页显示5条记录
        if (pageSize != null && pageSize.length() > 0) {
            pageSizeInt = Integer.parseInt(pageSize);
        } else {
            pageSizeInt = 5;
        }
        //3. 调用service查询PageBean对象
        PageBean<Route> pb = routeService.pageQuery(cidInt, currentPageInt, pageSizeInt, rName);
        return pb;
    }

    @GetMapping("/route/FavoritesPageQuery")
    @ResponseBody
    public PageBean<Route> FavoritesPageQuery(String uid, String currentPage, String rName, String pageSize) {

        List<Favorite> favorites = favoriteService.findFavorites(uid);

        int currentPageInt = 0;//当前页码，如果不传递，则默认为第一页
        if (currentPage != null && currentPage.length() > 0) {
            currentPageInt = Integer.parseInt(currentPage);
        } else {
            currentPageInt = 1;
        }
        int pageSizeInt = 0;//每页显示条数，如果不传递，默认每页显示5条记录
        if (pageSize != null && pageSize.length() > 0) {
            pageSizeInt = Integer.parseInt(pageSize);
        } else {
            pageSizeInt = 8;
        }
        //3. 调用service查询PageBean对象
        PageBean<Route> pb = routeService.FavoritesPageQuery(currentPageInt, pageSizeInt, favorites, rName);

        System.out.println(pb);
        return pb;
    }


    @ResponseBody
    @GetMapping("/route/FavoritesRankPageQuery")
    public PageBean<Route> FavoritesRankPageQuery(String currentPage, String lowPrice, String highPrice, String pageSize, String rName) {

        int currentPageInt;//当前页码，如果不传递，则默认为第一页
        if (currentPage != null && currentPage.length() > 0) {
            currentPageInt = Integer.parseInt(currentPage);
        } else {
            currentPageInt = 1;
        }

        int pageSizeInt = 0;//每页显示条数，如果不传递，默认每页显示5条记录
        if (pageSize != null && pageSize.length() > 0) {
            pageSizeInt = Integer.parseInt(pageSize);
        } else {
            pageSizeInt = 8;
        }
        //3. 调用service查询PageBean对象
        PageBean<Route> pb = routeService.FavoritesRankPageQuery(currentPageInt, pageSizeInt, rName, lowPrice, highPrice);

        return pb;

    }

}
