package com.wolfman.travel.service.impl;


import com.wolfman.travel.bean.Favorite;
import com.wolfman.travel.mapper.FavoriteMapper;
import com.wolfman.travel.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Override
    public boolean isFavorite(String rid, String uid) {
        Favorite favorite = null;
        if (rid != null && !"".equals(rid)) {
            favorite = favoriteMapper.findByRidAndUid(rid, uid);
        }
        return favorite != null;//如果对象有值，则为true，反之，则为false
    }

    @Override
    public void add(String rid, int uid, Date date) {
        try {
            favoriteMapper.add(rid, uid, date);
        } catch (Exception e) {

        }
    }

    @Override
    public void delete(String rid, int uid) {
        favoriteMapper.delete(rid,uid);
    }

    @Override
    public List<Favorite> findFavorites(String uid) {
        return favoriteMapper.findFavoritesByUid(uid);
    }
}
