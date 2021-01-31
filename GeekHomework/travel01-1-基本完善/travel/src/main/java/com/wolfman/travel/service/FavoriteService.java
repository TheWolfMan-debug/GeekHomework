package com.wolfman.travel.service;


import com.wolfman.travel.bean.Favorite;

import java.util.Date;
import java.util.List;

public interface FavoriteService {

    boolean isFavorite(String rid, String uid);

    void add(String rid, int uid, Date date);

    void delete(String rid, int uid);

    List<Favorite> findFavorites(String uid);
}
