package com.x3platform.connect.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.KernelContext;
import com.x3platform.configuration.KernelConfigurationView;
import com.x3platform.connect.ConnectContext;
import com.x3platform.connect.models.ConnectAccessToken;
import com.x3platform.connect.models.ConnectAuthorizationCode;
import com.x3platform.connect.models.Connect;
import com.x3platform.data.DataPaging;
import com.x3platform.data.DataPagingUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.Account;
import com.x3platform.messages.MessageObject;
import com.x3platform.security.authentication.AuthenticationManagement;
import com.x3platform.util.IPQueryUtil;
import com.x3platform.util.StringUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author xiaofei
 * @version V1.0
 * @company 阿米智能
 * @FileName CategoryController
 * @Package com.cisdigroup.datamanager.controllers
 * @Description 数据源分类控制类
 * @create 2018-08-17 16:37
 */
@RestController("com.x3platform.connect.controllers.ConnectOAuth2Controller")
@RequestMapping("/api/connect/oauth2")
public class ConnectOAuth2Controller {

  // -------------------------------------------------------
  // OAuth 2.0 验证
  // -------------------------------------------------------
  //
  // The OAuth 2.0 Authorization Framework
  // https://tools.ietf.org/html/rfc6749
  //
  // The OAuth 2.0 Authorization Framework: Bearer Token Usage
  // https://tools.ietf.org/html/rfc6750
  //
  // -------------------------------------------------------
  // 接口地址:/api/connect/oauth2/authorize
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @return 返回操作结果
   */
  @RequestMapping("/authorize")
  public final String getAuthorizeCode(@RequestBody String data) {
    StringBuilder outString = new StringBuilder();

    JSONObject req = JSON.parseObject(data);

    String clientId = req.getString("client_id");
    String redirectUri = req.getString("redirect_uri");
    String responseType = req.getString("response_type");
    String scope = req.getString("scope");

    String loginName = req.getString("username");
    String password = req.getString("password");

    if (StringUtil.isNullOrEmpty(loginName) || StringUtil.isNullOrEmpty(password)) {
      // HttpContentTypeHelper.SetValue("html");

      return createLoginPage(clientId, redirectUri, responseType, scope);
    } else {
      Account account = KernelContext.getCurrent().getAuthenticationManagement().login(loginName, password, true);

      if (account == null) {
        if (StringUtil.isNullOrEmpty(responseType)) {
          outString.append("{\"message\":{\"returnCode\":1,\"value\":\"帐号或者密码错误。\"}}");
          return outString.toString();
        } else {
          // 输出登录页面
          // 设置输出的内容类型，默认为 html 格式。

          // HttpContentTypeHelper.SetValue("html");
          return createLoginPage(clientId, redirectUri, responseType, scope);
        }
      } else {
        // 获取当前用户信息
        // Account account = KernelContext.getCurrent().getAuthenticationManagement().getAuthUser();

        // 检验是否有授权码
        if (!ConnectContext.getInstance().getConnectAuthorizationCodeService().isExist(clientId, account.getId())) {
          ConnectAuthorizationCode authorizationCode = new ConnectAuthorizationCode();

          authorizationCode.setId(DigitalNumberContext.generate("Key_32DigitGuid"));
          authorizationCode.setAppKey(clientId);
          authorizationCode.setAccountId(account.getId());
          authorizationCode.setAuthorizationScope(StringUtil.isNullOrEmpty(scope) ? "public" : scope);

          ConnectContext.getInstance().getConnectAuthorizationCodeService().save(authorizationCode);
        }

        // 设置访问令牌
        ConnectContext.getInstance().getConnectAccessTokenService().write(clientId, account.getId());

        // 设置会话信息
        ConnectAccessToken token = ConnectContext.getInstance().getConnectAccessTokenService().findOneByAccountId(clientId, account.getId());

        String sessionId = token.getAccountId() + "-" + token.getId();

        KernelContext.getCurrent().getAuthenticationManagement().addSession(clientId, sessionId, account);

//        HttpAuthenticationCookieSetter.SetUserCookies(sessionId);

        String code = ConnectContext.getInstance().getConnectAuthorizationCodeService().getAuthorizationCode(clientId, account);

        // responseType == null 则输出令牌信息
        if (StringUtil.isNullOrEmpty(responseType)) {
          outString.append("{\"access_token\":\"" + token.getId() + "\",");
          outString.append("\"token_type\":\"bearer\",");
          outString.append("\"expires_in\":\"" + token.getExpiresIn() + "\",");
          outString.append("\"refresh_token\":\"" + token.getRefreshToken() + "\"}");

          String callback = req.getString("callback");

          return StringUtil.isNullOrEmpty(callback) ? outString.toString() : callback + "(" + outString.toString() + ")";
        } else if (responseType.equals("code")) {
          // HttpContext.Current.Response.Redirect(CombineUrlAndAuthorizationCode(redirectUri, code));
          return "";
        } else if (responseType.equals("token")) {
          // HttpContext.Current.Response.Redirect(CombineUrlAndAccessToken(redirectUri, token));
        } else {
          // HttpContext.Current.Response.Redirect(CombineUrlAndAuthorizationCode(redirectUri, code));
        }
      }
    }

    outString.append("{\"message\":{\"returnCode\":0,\"value\":\"执行成功。\"}}");

    return outString.toString();
  }

  ///#region 私有函数:CombineUrlAndAuthorizationCode(string redirectUri, string code)

  /**
   * 合并Url地址和授权码
   */
  private String CombineUrlAndAuthorizationCode(String redirectUri, String code) {
    if (redirectUri.indexOf("?") == -1 && redirectUri.indexOf("&") == -1) {
      return redirectUri + "?code=" + code;
    } else if (redirectUri.indexOf("?") > -1 && redirectUri.indexOf("&") == -1) {
      return redirectUri + "&code=" + code;
    } else {
      return redirectUri + "&code=" + code;
    }
  }

  /**
   * 合并Url地址和访问令牌
   */
  private String CombineUrlAndAccessToken(String redirectUri, ConnectAccessToken token) {
    if (redirectUri == null) {
      redirectUri = "";
    }

    if (redirectUri.indexOf("?") == -1 && redirectUri.indexOf("&") == -1) {
      return redirectUri + "?token=" + token.getId() + "&expires_in=" + token.getExpiresIn() + "&refresh_token=" + token.getRefreshToken();
    } else if (redirectUri.indexOf("?") > -1 && redirectUri.indexOf("&") == -1) {
      return redirectUri + "&token=" + token.getId() + "&expires_in=" + token.getExpiresIn();
    } else {
      return redirectUri + "&token=" + token.getId() + "&expires_in=" + token.getExpiresIn();
    }
  }

  // -------------------------------------------------------
  // 工具函数
  // -------------------------------------------------------

  /**
   * 创建登录页面信息
   *
   * @return 返回操作结果
   */
  private String createLoginPage(String clientId, String redirectUri, String responseType, String scope) {
    // 测试地址
    // http://x10.x3platform.com/api/connect.oauth2.authorize.aspx?client_id=a70633f6-b37a-4e91-97a0-597d708fdcef&redirect_uri=https://x10.x3platform.com/api/connect.auth.back.aspx%3fclient_id%3da70633f6-b37a-4e91-97a0-597d708fdcef&response_type=code
    // http://x10.x3platform.com/api/connect.oauth2.authorize.aspx?client_id=a70633f6-b37a-4e91-97a0-597d708fdcef&redirect_uri=https://x10.x3platform.com/api/connect.auth.back.aspx%3fclient_id%3da70633f6-b37a-4e91-97a0-597d708fdcef&response_type=token

    StringBuilder outString = new StringBuilder();

    Connect connect = ConnectContext.getInstance().getConnectService().findOne(clientId);

    outString.append("<!DOCTYPE HTML>" + "\r\n");

    outString.append("<html>");
    outString.append("<head>");
    outString.append("<title>" + connect.getName() + "需要接入验证 - " + KernelConfigurationView.getInstance().getSystemName() + "</title>");
    outString.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
    outString.append("<link rel=\"stylesheet\" media=\"all\" href=\"/resources/styles/default/login.css\" type=\"text/css\" />");
    outString.append("<link rel=\"shortcut icon\" href=\"/favorite.ico\" />");
    outString.append("</head>");

    outString.append("<body>");

    outString.append("<form id=\"authForm\" name=\"authForm\" method=\"POST\" action=\"/api/connect.auth.authorize.aspx?clientId=" + clientId + (StringUtil.isNullOrEmpty(redirectUri) ? "&redirectUri=" + connect.getRedirectUri() : "&redirectUri=" + redirectUri) + (StringUtil.isNullOrEmpty(responseType) ? "&responseType=code" : "&responseType=" + responseType) + (StringUtil.isNullOrEmpty(scope) ? "" : "&scope=" + scope) + "\" >");

    outString.append("<input id=\"xml\" name=\"xml\" type=\"hidden\" value=\"\" />");

    outString.append("<div class=\"window-login-main-wrapper\" style=\"width:100%;\" >");
    outString.append("<div class=\"window-login-form-wrapper\" style=\"margin:4px auto 0 auto; float:none;\" >");
    outString.append("<div class=\"window-login-form-container\" >");
    outString.append("<div class=\"window-login-form-input\" >");
    outString.append("<span>帐号</span> ");
    outString.append("<input id=\"loginName\" maxlength=\"20\" type=\"text\" class=\"window-login-input-style\" value=\"\" />");
    outString.append("</div>");
    outString.append("<div class=\"window-login-form-input\" >");
    outString.append("<span>密码</span>");
    outString.append("<input id=\"password\" maxlength=\"20\" type=\"password\" class=\"window-login-input-style\" value=\"\" />");
    outString.append("</div>");

    // outString.Append("<div class=\"window-login-form-remember-me\" >");
    // outString.Append("<a href=\"/public/forgot-password.aspx\" target=\"_blank\" >忘记登录密码？</a>");
    // outString.Append("<input id=\"remember\" name=\"remember\" type=\"checkbox\" > <span>记住登录状态</span>");
    // outString.Append("</div>");
    outString.append("<div class=\"window-login-button-wrapper\" >");
    outString.append("<div class=\"window-login-button-submit\" ><a id=\"btnSubmit\" href=\"javascript:if (document.getElementById('loginName').value == '' || document.getElementById('password').value == '') {alert('必须填写帐号和密码。'); return;}; document.getElementById('xml').value = '<?xml version=&quot;1.0&quot; encoding=&quot;utf-8&quot; ?><root><loginName><![CDATA[' + document.getElementById('loginName').value + ']]></loginName><password><![CDATA[' + document.getElementById('password').value + ']]></password></root>'; document.getElementById('authForm').submit();\" >登录</a></div>");
    // outString.Append("<div class=\"window-login-loading\" style=\"display:none;\" ><img src=\"/resources/images/loading.gif\" alt=\"登录中\" /></div>");
    outString.append("</div>");
    // outString.Append("<div class=\"window-login-form-bottom\" >");
    // outString.Append("<a href=\"#\" >注册新帐号</a>");
    // outString.Append("</div>");
    ;

    outString.append("</div>");
    outString.append("</div>");
    outString.append("</div>");

    outString.append("</form>");
    outString.append("</body>");
    outString.append("</html>");

    return outString.toString();
  }

  /**
   * 验证后的回调页面
   *
   * @param req 请求对象
   * @return 返回操作结果
   */
  public String callback(JSONObject req) {
    // https://x10.x3platform.com/api/connect.auth.callback.aspx?clientId=a70633f6-b37a-4e91-97a0-597d708fdcef&code=75266c29f9e3497480e5ddc6cfa38b8c;
    // https://x10.x3platform.com/api/connect.auth.callback.aspx?clientId=a70633f6-b37a-4e91-97a0-597d708fdcef&code=75266c29f9e3497480e5ddc6cfa38b8c;

//    String clientId = XmlHelper.Fetch("clientId", doc);
//
//    String code = XmlHelper.Fetch("code", doc);
//    String grantType = XmlHelper.Fetch("grant_type", doc);
//
//    String token = XmlHelper.Fetch("token", doc);
//
//    Connect connect = ConnectContext.Instance.ConnectService.FindOneByAppKey(clientId);
//
//    AjaxRequestData requestData = new AjaxRequestData();
//
//    if (tangible.DotNetToJavaStringHelper.isNullOrEmpty(token))
//    {
//      if (!tangible.DotNetToJavaStringHelper.isNullOrEmpty(code) && grantType.equals("authorization_code"))
//      {
//        // code => token
//        requestData.ActionUri = new Uri(ConnectConfigurationView.Instance.ApiHostName + "/api/connect.oauth2.token.aspx?code=" + code);
//
//        requestData.Args.Add("client_id", clientId);
//        requestData.Args.Add("client_secret", connect.AppSecret);
//        requestData.Args.Add("grant_type", "authorization_code");
//      }
//      else if (!tangible.DotNetToJavaStringHelper.isNullOrEmpty(code) && grantType.equals("refresh_token"))
//      {
//        requestData.ActionUri = new Uri(ConnectConfigurationView.Instance.ApiHostName + "/api/connect.oauth2.token.aspx?code=" + code);
//
//        requestData.Args.Add("client_id", clientId);
//        requestData.Args.Add("client_secret", connect.AppSecret);
//        requestData.Args.Add("grant_type", "refresh_token");
//      }
//
//      String responseText = AjaxRequest.Request(requestData);
//
//      JsonObject responseObject = JsonObjectConverter.Deserialize(responseText);
//
//      token = ((JsonPrimary)responseObject["access_token"]).Value.toString();
//    }
//
//    HttpContext.Current.Response.Cookies["connect$token"].Value = token;
//    HttpContext.Current.Response.Cookies["connect$authType"].Value = "oauth2";
//
//    requestData.ActionUri = new Uri(ConnectConfigurationView.Instance.ApiHostName + "/api/connect.auth.me.aspx?access_token=" + token);
//
//    requestData.Args.Clear();
//
//    return AjaxRequest.Request(requestData);
    return "";
  }
}
