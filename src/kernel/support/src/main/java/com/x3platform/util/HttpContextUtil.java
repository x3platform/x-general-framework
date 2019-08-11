package com.x3platform.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * HTTP 请求辅助函数
 */
public class HttpContextUtil {
  public static HttpServletRequest getRequest() {
    RequestAttributes ra = RequestContextHolder.getRequestAttributes();
    return ((ServletRequestAttributes) ra).getRequest();
  }

  public static HttpServletResponse getResponse() {
    RequestAttributes ra = RequestContextHolder.getRequestAttributes();
    return ((ServletRequestAttributes) ra).getResponse();
  }

  public static Map<String, Cookie> getCookieMap() {
    HttpServletRequest request = getRequest();
    return getCookieMap(request);
  }

  public static Map<String, Cookie> getCookieMap(HttpServletRequest request) {
    Map<String, Cookie> map = new HashMap<String, Cookie>();
    Cookie[] cookies = request.getCookies();
    if (null != cookies) {
      for (Cookie cookie : cookies) {
        map.put(cookie.getName(), cookie);
      }
    }
    return map;
  }

  public static String getCookie(String name) {
    HttpServletRequest request = getRequest();
    return getCookie(request, name);
  }

  public static String getCookie(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name)) {
          return cookie.getValue();
        }
      }
    }

    return null;
  }

  public static void setCookie(String name, String value) {
    setCookie(name, value, "", "/", -1, false);
  }

  public static void setCookie(String name, String value, boolean httpOnly) {
    setCookie(name, value, "", "/", -1, httpOnly);
  }

  public static void setCookie(String name, String value, String domain, boolean httpOnly) {
    setCookie(name, value, domain, "/", -1, httpOnly);
  }

  public static void setCookie(String name, String value, String domain, int maxAge, boolean httpOnly) {
    setCookie(name, value, domain, "/", -1, httpOnly);
  }

  public static void setCookie(String name, String value, String domain, String path, int maxAge, boolean httpOnly) {
    Cookie cookie = new Cookie(name, value);

    if (!StringUtil.isNullOrEmpty(domain) && !domain.equals("localhost")) {
      cookie.setDomain(domain);
    }
    if (!StringUtil.isNullOrEmpty(path)) {
      cookie.setPath(path);
    }
    if (maxAge >= 0) {
      cookie.setMaxAge(maxAge);
    }
    cookie.setHttpOnly(httpOnly);

    setCookie(cookie);
  }

  public static void setCookie(Cookie cookie) {
    HttpServletResponse response = getResponse();
    setCookie(response, cookie);
  }

  public static void setCookie(HttpServletResponse response, Cookie cookie) {
    response.addCookie(cookie);
  }
}
