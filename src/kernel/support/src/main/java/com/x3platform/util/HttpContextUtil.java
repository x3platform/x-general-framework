package com.x3platform.util;

import com.x3platform.messages.MessageObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

  /**
   * 获取请求 Body 内容 注: ServletRequest.getInputStream() 流只允许读取一次, 如果需要多次读取需要其他方法配合
   */
  public static String getBodyString(ServletRequest request) {
    StringBuilder sb = new StringBuilder();
    InputStream inputStream = null;
    BufferedReader reader = null;
    try {
      inputStream = request.getInputStream();
      reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
      String line = "";
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return sb.toString();
  }

  /**
   * 输出异常信息
   *
   * @param response HTTP 响应对象
   * @param code 消息代码
   * @param message 消息描述
   */
  public static void writeException(HttpServletResponse response, String code, String message) throws IOException {
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setCharacterEncoding("utf-8");
    response.setContentType("application/json; charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().write(MessageObject.stringify(code, message));
    response.getWriter().close();
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
