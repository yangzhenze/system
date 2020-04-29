package com.swsk.web.util;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zzy
 * @Date 2019-07-18 16:02
 */
public class HttpUtil {

    private static final String AJAX = "XMLHttpRequest";

    public static HttpServletRequest getRequest(){
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        return servletRequestAttributes.getRequest();
    }

    public static HttpServletResponse getResponse(){
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        return servletRequestAttributes.getResponse();
    }

    public static String getIp(){
        return HttpUtil.getRealIp(HttpUtil.getRequest());
    }

    public static String getMethod() {
        HttpServletRequest request = HttpUtil.getRequest();
        if (null == request) {
            return null;
        }
        return request.getMethod();
    }

    public static String getRequestParams(){
        Map<String, String[]> parameterMap= HttpUtil.getRequest().getParameterMap();
        return JSON.toJSONString(parameterMap);
    }

    public static String getRequestParams(HttpServletRequest request){
        Map<String, String[]> parameterMap= request.getParameterMap();
        return JSON.toJSONString(parameterMap);
    }

    public static String getRequestUrl() {
        HttpServletRequest request = HttpUtil.getRequest();
        if (null == request) {
            return null;
        }
        return request.getRequestURL().toString();
    }

    public static String getRequestUrl(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        return request.getRequestURL().toString();
    }


    public static boolean isRequestAjax(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        if(null != requestedWith && requestedWith.equals(AJAX)){
            return true;
        }
        return false;
    }


    public static String getCookie(HttpServletRequest request,String cookieName){
        //获取cookie中的openId
        String cookie = "";
        Cookie[] cookies = request.getCookies();
        if(null != cookies){
            for(Cookie c:cookies){
                if(cookieName.equals(c.getName())){
                    cookie = c.getValue();
                    break;
                }
            }
        }
        return cookie;
    }

    /**
     * 获取真实IP
     *
     * @param request
     * @return
     */
    private static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        return checkIp(ip) ? ip : (
                checkIp(ip = request.getHeader("Proxy-Client-IP")) ? ip : (
                        checkIp(ip = request.getHeader("WL-Proxy-Client-IP")) ? ip :
                                request.getRemoteAddr()));
    }

    /**
     * 校验IP
     *
     * @param ip
     * @return
     */
    private static boolean checkIp(String ip) {
        return !StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip);
    }
}
