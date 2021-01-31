package com.wolfman.travel.mapper;

import com.wolfman.travel.bean.Favorite;
import com.wolfman.travel.bean.Route;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface RouteMapper {

    @Select("select * from tab_route")
    List<Route> findAll();

    @Select("select * from tab_route where rid = #{id}")
    Route findOne(int rid);

    @Select({"<script>"+
            "select count(*) from tab_route "+
            "<where> " +
            "<if test='cid !=0'>"+
            "and cid=#{cid} "+
            "</if>" +
            "<if test='rName !=\"\" and rName!=\"null\"'> "+
            "and rname like concat('%',#{rName},'%') "+
            "</if>" +
            "</where>" +
            "</script>"})
    int findTotalCount(int cid, String rName);


    @Select({"<script>"+
            "select * from tab_route "+
            "<where>"+
            "<if test='cid !=0'>"+
            "and cid= #{cid} "+
            "</if>" +
            "<if test='rName !=\"\" and rName!=\"null\"'> "+
            "and rname like concat('%',#{rName},'%') "+
            "</if>" +
            "limit #{start} , #{pageSize} "+
            "</where>" +
            "</script>"})
    List<Route> findByPage(int cid, int start, int pageSize, String rName);


    @Select("SELECT COUNT FROM tab_route WHERE rid=#{rid}")
    int findCount(String rid);

    @Select("UPDATE tab_route SET COUNT=#{favoriteCount} WHERE rid=#{rid}")
    void update(String rid, int favoriteCount);



    @Select({"<script>"+
            "SELECT COUNT(*) FROM tab_route "+
            "<where>"+
            "<when test='favorites != null and favorites.size() > 0'>" +
            "<foreach item='item' collection='favorites' open=' and rid in (' separator=',' close=') '>"+
            "#{item.rid}"+
            "</foreach>"+
            "</when>" +
            "<if test='rName !=\"\" and rName!=\"null\"'> "+
            "and rname like concat('%',#{rName},'%') "+
            "</if>" +
            "</where>" +
            "</script>"})
    int findTotalCountByName(String rName, List<Favorite> favorites);


    @Select({"<script>"+
            "SELECT * FROM tab_route "+
            "<where>"+
            "<when test='favorites != null and favorites.size() > 0'>" +
            "<foreach item='item' collection='favorites' open=' and rid in (' separator=',' close=') '>"+
            "#{item.rid}"+
            "</foreach>"+
            "</when>" +
            "<if test='rName !=\"\" and rName!=\"null\"'>"+
            "and rname like concat('%',#{rName},'%') "+
            "</if>" +
            "limit #{start} , #{pageSize} "+
            "</where>" +
            "</script>"})
    List<Route> findFavoritesByPage(int start, int pageSize, List<Favorite> favorites, String rName);



    @Select({"<script>"+
            "SELECT COUNT(*) FROM tab_route "+
            "<where>"+
            "<if test='rName !=\"\" and rName!=\"null\"'>"+
            "and rname like concat('%',#{rName},'%') "+
            "</if>" +
            "<if test='lowPrice !=\"\" and lowPrice!=\"null\"'> "+
            "and price > #{lowPrice} "+
            "</if>" +
            "<if test='highPrice !=\"\" and highPrice!=\"null\"'> "+
            "and #{highPrice} > price "+
            "</if>" +
            "</where>" +
            "</script>"})
    int findFavoritesRankTotalCount(String rName, String lowPrice, String highPrice);


    @Select({"<script>"+
            "SELECT * FROM tab_route "+
            "<where>"+
            "<if test='rName !=\"\" and rName!=\"null\"'>"+
            "and rname like concat('%',#{rName},'%') "+
            "</if>" +
            "<if test='lowPrice !=\"\" and lowPrice!=\"null\"'> "+
            "and price > #{lowPrice} "+
            "</if>" +
            "<if test='highPrice !=\"\" and highPrice!=\"null\"'> "+
            "and #{highPrice} > price "+
            "</if>"+
            "</where>"+
            "order by count desc limit #{start} , #{pageSize} "+
            "</script>"})
    List<Route> findFavoritesRankByPage(int start, int pageSize, String rName, String lowPrice, String highPrice);

}
