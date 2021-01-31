package com.wolfman.travel.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;


public class CheckCodeUtils {

    public static boolean checkCode(HttpServletRequest request, HttpServletResponse response)
    {
        //验证校验
        String check = request.getParameter("check");
        //从sesion中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次
//        System.out.println("系统的验证码："+checkcode_server);
//        System.out.println("输入的验证码："+check);
//        System.out.println("验证码是否正确："+(checkcode_server != null && checkcode_server.equalsIgnoreCase(check)));

        return checkcode_server != null && checkcode_server.equalsIgnoreCase(check);
    }

    /**
     * 产生4位随机字符串
     */
    public static String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<=4;i++){
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }


}
