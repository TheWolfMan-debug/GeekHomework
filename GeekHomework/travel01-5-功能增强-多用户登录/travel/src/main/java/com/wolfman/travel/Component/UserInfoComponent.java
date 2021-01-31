package com.wolfman.travel.Component;

import com.wolfman.travel.bean.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Component
public final class UserInfoComponent {


    /**
     * 在客户端保存用户的信息
     *
     * @param response
     * @param u
     */
    public void saveUserInCookie(HttpServletResponse response, User u) {
        Cookie cookie = new Cookie("userUid", String.valueOf(u.getUid()));
        //设置cookie的存活时间 一个小时
        cookie.setMaxAge(60 * 60);
        //设置路径
        cookie.setPath("/");
        //添加cookie
        response.addCookie(cookie);
    }

    /**
     * 在服务器端保存用户信息
     *
     * @param session
     * @param u
     */
    public void saveUserInSession(HttpSession session, User u) {
        //获取onlineUsers
        List<User> onlineUsers = (List<User>) session.getAttribute("onlineUsers");
        if (onlineUsers == null || onlineUsers.size() == 0) {
            //如果session中没有onlineUsers，则创建并添加
            List<User> userList = new LinkedList<>();
            userList.add(u);
            session.setAttribute("onlineUsers", userList);
        } else {
            //如果不为空，则直接添加
            onlineUsers.add(u);
            session.setAttribute("onlineUsers", onlineUsers);
        }
        session.setMaxInactiveInterval(60 * 60);
        System.out.println("登陆时保存user：" + session.getAttribute("onlineUsers"));
    }


    public void destroyCookieAndSession(HttpServletRequest request, HttpServletResponse response) {
        //获取session
        HttpSession session = request.getSession();
        //获取所有Cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            //如果用户之前已经勾选了记住我
            if ("userUid".equals(cookie.getName())) {
                //获取用户信息
                String uid = cookie.getValue();
                //销毁session中该用户的信息
                destroySession(uid, session);
                System.out.println("销毁cookie中的userUid：" + uid);
                //销毁cookie中的用户信息
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
    }


    private void destroySession(String uid, HttpSession session) {

        List<User> users = (List<User>) session.getAttribute("onlineUsers");
        for (User user : users) {
            if (uid.equals(String.valueOf(user.getUid()))) {
                users.remove(user);
                session.setAttribute("onlineUsers", users);
                System.out.println("销毁session中的用户" + user);
                break;
            }
        }

    }


    public User findOneUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println("当前在线的用户：" + session.getAttribute("onlineUsers"));
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                //如果session存在该用户，返回
                if ("userUid".equals(cookie.getName())) {
                    //获取用户信息
                    String uid = cookie.getValue();
                    List<User> users = (List<User>) session.getAttribute("onlineUsers");
                    if (users != null && users.size() != 0) {
                        for (User user : users) {
                            if (String.valueOf(user.getUid()).equals(uid)) {
                                return user;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


}


