package com.wolfman.travel.service.impl;


import com.wolfman.travel.bean.User;
import com.wolfman.travel.mapper.UserMapper;
import com.wolfman.travel.service.UserService;
import com.wolfman.travel.util.MailUtil;
import com.wolfman.travel.util.Md5Util;
import com.wolfman.travel.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper; //注入userMapper

    @Autowired
    MailUtil mailUtil;

    @Override
    public boolean register(User user, HttpServletRequest request) {
        //1.根据用户名查询用户对象
        User u = userMapper.findByUsername(user.getUsername());
        //判断u是否为null
        if (u != null) {
            //用户名存在，注册失败
            return false;
        }
        //2.保存用户信息
        //2.1设置激活码，唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        //2.3将用户密码加密
        try {
            user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
            userMapper.save(user);
            //文件内容
            String content = "<a href='http://localhost:8888/user/active?code=" + user.getCode() + "'>点击激活【黑马旅游网】</a>";
            mailUtil.sendMail(user.getEmail(), content);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


    @Override
    public User login(User user) {
        User u;
        try {
            u = userMapper.findByUsernameAndPassword(user.getUsername(), Md5Util.encodeByMd5(user.getPassword()));
        } catch (Exception e) {
            return null;
        }
        return u;
    }

    /**
     * 激活用户
     *
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userMapper.findByCode(code);
        if (user != null) {
            //2.调用dao的修改激活状态的方法
            userMapper.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }




}
