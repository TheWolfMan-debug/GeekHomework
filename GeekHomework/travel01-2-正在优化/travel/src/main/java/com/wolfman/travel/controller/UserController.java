package com.wolfman.travel.controller;

import com.wolfman.travel.bean.PageBean;
import com.wolfman.travel.bean.Route;
import com.wolfman.travel.bean.User;
import com.wolfman.travel.service.impl.FavoriteServiceImpl;
import com.wolfman.travel.service.impl.RouteServiceImpl;
import com.wolfman.travel.service.impl.UserServiceImpl;
import com.wolfman.travel.util.CheckCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;


@Controller
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    RouteServiceImpl routeService;

    @Autowired
    FavoriteServiceImpl favoriteService;


    /**
     * 跳转到routeList页面
     * @return
     */
    @GetMapping("/user/routeList")
    public String routeList() {
        return "routeList";
    }


    /**
     * 查询路线详情
     * @param rid
     * @param modelAndView
     * @return
     */
    @GetMapping("/user/routeDetail")
    public ModelAndView routeDetail(int rid, ModelAndView modelAndView) {
        //根据rid查询路线详情
        Route routeDetail = routeService.findOne(rid);
        //设置视图
        modelAndView.setViewName("routeDetail");
        //添加数据
        modelAndView.addObject("routeDetail", routeDetail);
        return modelAndView;
    }

    /**
     * 根据rid查询路线详情
     * @param rid
     * @return
     */
    @ResponseBody
    @GetMapping("/user/routeDetail/findOne")
    public Route routeFindOne(int rid) {
        Route routeDetail = routeService.findOne(rid);
        return routeDetail;
    }


    /**
     * 跳转到login页面
     * @return
     */
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }


    /**
     * 用户登录功能
     * @param user
     * @param session
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/user/login")
    @ResponseBody
    public Map userLogin(User user, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Map map = new LinkedHashMap<String, Object>();
        //确认验证码
        if (!CheckCodeUtils.checkCode(request, response)) {
            map.put("flag", false);
            map.put("errorMsg", "验证码错误");
            return map;
        }
        //调用Service查询用户信息
        User u = userService.login(user);
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

    /**
     * 退出功能
     * @param session
     * @return
     */
    @GetMapping("/user/exit")
    public String exit(HttpSession session) {
        //销毁session数据
        session.invalidate();
        return "redirect:login";
    }


    /**
     * 跳转到register页面
     * @return
     */
    @GetMapping("/user/register")
    public String stepToUserRegister() {
        return "register";
    }

    /**
     * 用户登录检查
     * @param user
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/user/register")
    @ResponseBody
    public Map userRegister(User user, HttpServletResponse response, HttpServletRequest request) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        //确认验证码
        if (!CheckCodeUtils.checkCode(request, response)) {
            map.put("errorMsg", "验证码错误");
            map.put("flag", false);
            return map;
        }
        //调用service完成注册
        boolean flag = userService.register(user, request);
        //响应结果
        if (flag) {
            //注册成功
            map.put("flag", true);
        } else {
            //注册失败
            map.put("errorMsg", "注册失败！");
            map.put("flag", false);
        }
        return map;
    }

    /**
     * 用户激活功能
     * @param code
     * @param request
     * @param response
     */
    @GetMapping("/user/active")
    public void userActive(String code, HttpServletRequest request, HttpServletResponse response) {
        if (code != null) {
            //调用service完成激活
            boolean flag = userService.active(code);
            //判断标记
            String msg;
            if (flag) {
                //激活成功
                String contextPath = request.getContextPath();
                System.out.println(contextPath);
                msg = "激活成功，请<a href=\"" + contextPath + "/user/login\">登录</a>";

            } else {
                //激活失败
                msg = "激活失败，请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            try {
                response.getWriter().write(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 跳转到registerOk页面
     * @param user
     * @return
     */
    @GetMapping("/user/registerOk")
    public String registerOk(User user) {
        return "registerOk";
    }

    /**
     * 判断当前用户是否登录
     * @param session
     * @return
     */
    @GetMapping("/user/findOne")
    @ResponseBody
    public User findOne(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        return loginUser;
    }




}
