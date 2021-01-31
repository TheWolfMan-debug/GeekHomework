package com.wolfman.travel.service.impl;

import com.wolfman.travel.bean.*;
import com.wolfman.travel.mapper.FavoriteMapper;
import com.wolfman.travel.mapper.RouteImgMapper;
import com.wolfman.travel.mapper.RouteMapper;
import com.wolfman.travel.mapper.SellerMapper;
import com.wolfman.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    RouteMapper routeMapper;

    @Autowired
    RouteImgMapper routeImgMapper;

    @Autowired
    SellerMapper sellerMapper;

    @Autowired
    FavoriteMapper favoriteMapper;


    @Override
    public List<Route> findAllRoutes() {
        return routeMapper.findAll();
    }

    @Override
    public Route findOne(int rid) {
        //1.根据id去route表中查询route对象
        Route route = routeMapper.findOne(rid);

        //2.根据route的id 查询图片集合信息
        List<RouteImg> routeImgList = routeImgMapper.findByRid(rid);

        //2.2将集合设置到route对象
        route.setRouteImgList(routeImgList);

        //3.根据route的sid（商家id）查询商家对象
        Seller seller = sellerMapper.findById(route.getSid());
        route.setSeller(seller);

        //4. 查询收藏次数
        int count = favoriteMapper.findCountByRid(rid);
        route.setCount(count);

        return  route;
    }


    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rName) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);

        //设置总记录数
        int totalCount = routeMapper.findTotalCount(cid, rName);
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;//开始的记录数
        List<Route> list = routeMapper.findByPage(cid, start, pageSize, rName);
        pb.setList(list);

        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public int findCount(String rid) {
        return routeMapper.findCount(rid);
    }

    @Override
    public void update(String rid, int favoriteCount) {
        routeMapper.update( rid,favoriteCount);
    }

    @Override
    public PageBean<Route> FavoritesPageQuery(int currentPage, int pageSize, List<Favorite> favorites, String rName) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);
        //如果为空，直接返回
        if (favorites.size() == 0) {
            pb.setTotalCount(0);
            pb.setTotalPage(0);
            return pb;
        }


        //设置总记录数
        int totalCount = routeMapper.findTotalCountByName(rName,favorites);
        pb.setTotalCount(totalCount);

        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;//开始的记录数
        List<Route> list = routeMapper.findFavoritesByPage(start, pageSize,favorites,rName);
        pb.setList(list);

        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public PageBean<Route> FavoritesRankPageQuery(int currentPage, int pageSize, String rName, String lowPrice, String highPrice) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);

        //设置总记录数
        int totalCount = routeMapper.findFavoritesRankTotalCount(rName,lowPrice,highPrice);
        pb.setTotalCount(totalCount);

        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;//开始的记录数
        List<Route> list = routeMapper.findFavoritesRankByPage(start, pageSize, rName,lowPrice,highPrice);


        pb.setList(list);

        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);

        return  pb;
    }
}
