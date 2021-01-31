package com.wolfman.travel.service;


import com.wolfman.travel.bean.Favorite;
import com.wolfman.travel.bean.PageBean;
import com.wolfman.travel.bean.Route;

import java.util.List;

public interface RouteService {


    List<Route> findAllRoutes();

    Route findOne(int rid);

    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rName);

    int findCount(String rid);

    void update(String rid, int favoriteCount);

    PageBean<Route> FavoritesPageQuery(int currentPageInt, int pageSizeInt, List<Favorite> favorites, String rName);

    PageBean<Route> FavoritesRankPageQuery(int currentPageInt, int pageSizeInt, String rName, String lowPrice, String highPrice);
}
