package com.wolfman.travel.config.webConfig;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器配置，登陆检查
 */
public class InterceptorConfig implements HandlerInterceptor {
    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取当前登录的用户
        Object user = request.getSession().getAttribute("loginUser");
        if(user == null){
            //未登陆，返回登陆页面
            request.setAttribute("errorMsg","没有权限请先登陆");
            //转发到登录页面
            request.getRequestDispatcher("/user/login").forward(request,response);
            return false;
        }else{
            //已登陆，放行请求
            return true;
        }

    }
}
