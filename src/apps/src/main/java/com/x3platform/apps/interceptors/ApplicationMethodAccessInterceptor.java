package com.x3platform.apps.interceptors;

import static com.x3platform.Constants.HTTP_CONTENT_TYPE_APPLICATION_JSON;
import static com.x3platform.Constants.HTTP_METHOD_POST;
import static com.x3platform.Constants.REQUEST_URI_API_ROOT;
import static com.x3platform.Constants.REQUEST_URI_ROOT;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.GenericException;
import com.x3platform.KernelContext;
import com.x3platform.apps.AppsContext;
import com.x3platform.apps.AppsSecurity;
import com.x3platform.apps.models.Application;
import com.x3platform.apps.models.ApplicationMethod;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.Account;
import com.x3platform.util.HttpContextUtil;
import com.x3platform.util.StringUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 应用方法访问拦截器
 *
 * @author ruanyu
 */
public class ApplicationMethodAccessInterceptor implements HandlerInterceptor {

  private static final String ERROR_URI = "/error";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws IOException {
    // Interceptor 只有返回 true 才会继续向下执行，返回 false 取消当前请求
    String uri = request.getRequestURI();
    // 只处理 method=POST, content-type=application/json 的内容
    if (HTTP_METHOD_POST.equalsIgnoreCase(request.getMethod())
      && HTTP_CONTENT_TYPE_APPLICATION_JSON.equalsIgnoreCase(request.getContentType())) {
      if (ERROR_URI.equals(uri)) {
        return true;
      }
      // 设置方法名称
      String methodName = "";

      // 移除 /api/ 前缀信息
      if (uri.startsWith(REQUEST_URI_API_ROOT)) {
        methodName = uri.substring(REQUEST_URI_API_ROOT.length());
      } else if (uri.startsWith(REQUEST_URI_ROOT)) {
        methodName = uri.substring(REQUEST_URI_ROOT.length());
      }

      methodName = methodName.replace("/", ".");

      ApplicationMethod method = AppsContext.getInstance().getApplicationMethodService().findOneByName(methodName);

      if (method == null) {
        return true;
      } else {
        // 验证方法的作用范围
        int effectScope = method.getEffectScope();

        if (effectScope == 1) {
          // 允许所有人可以访问
        } else {
          // 应用方法所属的应用信息
          Application application = method.getApplication();

          // 当前用户信息
          Account account = KernelContext.getCurrent() == null ? null : KernelContext.getCurrent().getUser();

          if (effectScope == 2 && account == null) {
            // 需要【登录用户】以上级别权限才能调用此方法
            throw new GenericException(I18n.getExceptions().text("code_web_api_method_need_elevated_privileges"),
              I18n.getExceptions().format("text_web_api_method_need_elevated_privileges", methodName, "登录用户"));
          } else if (effectScope == 4 && !(AppsSecurity.isMember(account, application.getApplicationName())
            || AppsSecurity.isReviewer(account, application.getApplicationName()) || AppsSecurity
            .isAdministrator(account, application.getApplicationName()))) {
            // 需要【应用可访问成员】以上级别权限才能调用此方法
            throw new GenericException(I18n.getExceptions().text("code_web_api_method_need_elevated_privileges"),
              I18n.getExceptions().format("text_web_api_method_need_elevated_privileges", methodName, "应用可访问成员"));
          } else if (effectScope == 8 && !(AppsSecurity.isReviewer(account, application.getApplicationName())
            || AppsSecurity.isAdministrator(account, application.getApplicationName()))) {
            // 需要【应用审查员】以上级别权限才能调用此方法
            throw new GenericException(I18n.getExceptions().text("code_web_api_method_need_elevated_privileges"),
              I18n.getExceptions().format("text_web_api_method_need_elevated_privileges", methodName, "应用审查员"));
          } else if (effectScope == 16 && !AppsSecurity.isAdministrator(account, application.getApplicationName())) {
            // 需要【应用管理员】以上级别权限才能调用此方法
            throw new GenericException(I18n.getExceptions().text("code_web_api_method_need_elevated_privileges"),
              I18n.getExceptions().format("text_web_api_method_need_elevated_privileges", methodName, "应用管理员"));
          }
        }

        // 验证方法的参数
        String optionsText = method.getOptions();
        JSONObject options = JSON.parseObject(optionsText);
        // mappingParams 映射参数
        String body = HttpContextUtil.getBodyString(request);
        // String body = "";
        if (StringUtil.isNullOrEmpty(body)) {
          return true;
        }

        JSONObject req = JSON.parseObject(body);
        // 验证 requiredParams 必填参数
        if (options.containsKey("requiredParams")) {
          String[] requiredParams = options.getString("requiredParams").split(",");
          for (String requiredParam : requiredParams) {
            if (req.containsKey(requiredParam)) {
              continue;
            }

            HttpContextUtil.writeException(response,
              I18n.getExceptions().text("code_general_param_is_required"),
              I18n.getExceptions().format("text_general_param_is_required", requiredParam));

            return false;
          }
        }
      }
    }
    return true;
  }
}

