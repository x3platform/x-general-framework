package com.x3platform.security.interceptors;

import static com.x3platform.Constants.HTTP_METHOD_OPTIONS;

import com.x3platform.KernelContext;
import com.x3platform.globalization.I18n;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.HttpContextUtil;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * HTTP 身份验证拦截器
 *
 * @author ruanyu
 */
public class HttpAuthenticationInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws IOException {
    if (HTTP_METHOD_OPTIONS.equalsIgnoreCase(request.getMethod())) {
      return true;
    }

    // 获取当前用户信息
    if (KernelContext.getCurrent().getUser() == null) {
      HttpContextUtil.writeException(response, I18n.getExceptions().text("code_http_status_unauthorized"),
        I18n.getExceptions().text("text_http_status_unauthorized"));
      return false;
    } else {
      return true;
    }
  }
}
