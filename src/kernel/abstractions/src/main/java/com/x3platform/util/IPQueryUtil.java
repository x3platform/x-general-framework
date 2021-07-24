package com.x3platform.util;

import com.x3platform.InternalLogger;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * IP 地址辅助类
 */
public class IPQueryUtil {
  public static String getClientIP() {
    RequestAttributes ra = RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
    return getClientIP(request);
  }
  
  /**
   * 获取客户端 IP
   */
  public static String getClientIP(HttpServletRequest request) {
    // 返回 X-FORWARDED-FOR
    String ip = request.getHeader("X-Forwarded-For");
    if (!StringUtil.isNullOrEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
      InternalLogger.getLogger().debug("Header X-Forwarded-For:{}", ip);
      // 多次反向代理后会有多个 ip 值，第一个 ip 才是真实 ip
      int index = ip.indexOf(",");
      if (index != -1) {
        return ip.substring(0, index);
      } else {
        return ip;
      }
    }
    // 返回 X-REAL-IP
    ip = request.getHeader("X-Real-IP");
    if (!StringUtil.isNullOrEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
      InternalLogger.getLogger().debug("Header X-Real-IP:{}", ip);
      return ip;
    }
    // 返回 REMOTE_ADDR
    return request.getRemoteAddr();
  }
}
