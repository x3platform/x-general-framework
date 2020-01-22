package com.x3platform.apps.filters;

import static com.x3platform.Constants.HTTP_CONTENT_TYPE_APPLICATION_JSON;
import static com.x3platform.Constants.HTTP_METHOD_POST;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ruanyu
 */
public class ApplicationMethodFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) {
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    // 只处理 method=POST, content-type=application/json 的内容
    if ( HTTP_METHOD_POST.equalsIgnoreCase(request.getMethod())
      && HTTP_CONTENT_TYPE_APPLICATION_JSON.equalsIgnoreCase(request.getContentType())) {
      // 将 HttpServletRequest 封装支持多次获取 InputStream 返回请求内容
      // 配合 ApplicationMethodAccessInterceptor 使用
      ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
      chain.doFilter(requestWrapper, response);
      return;
    }

    chain.doFilter(req, res);
  }

  @Override
  public void destroy() {
  }
}
