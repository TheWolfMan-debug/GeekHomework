package com.wolfman.travel.Component;

import com.wolfman.travel.bean.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Component
public final class LoginComponent {

    public Map loginCheck(User u, Map map, HttpSession session) {
        //判断用户对象是否为null
        if (u == null) {
            //用户名密码或错误
            map.put("flag", false);
            map.put("errorMsg", "用户名密码或错误");
        }
        //判断用户是否激活
        if (u != null && !"Y".equals(u.getStatus())) {
            //用户尚未激活
            map.put("flag", false);
            map.put("errorMsg", "您尚未激活，请激活");
        }

        //判断登录成功
        if (u != null && "Y".equals(u.getStatus())) {
            session.setAttribute("loginUser", u);//登录成功标记
            //登录成功
            map.put("flag", true);
        }
        return map;
    }

}
