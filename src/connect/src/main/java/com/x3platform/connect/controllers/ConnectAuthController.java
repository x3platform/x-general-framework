package com.x3platform.connect.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.InternalLogger;
import com.x3platform.KernelContext;
import com.x3platform.cachebuffer.CachingManager;
import com.x3platform.configuration.KernelConfigurationView;
import com.x3platform.connect.ConnectContext;
import com.x3platform.connect.configuration.ConnectConfigurationView;
import com.x3platform.connect.models.Connect;
import com.x3platform.connect.models.ConnectAccessToken;
import com.x3platform.connect.models.ConnectAuthorizationCode;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.globalization.I18n;
import com.x3platform.membership.Account;
import com.x3platform.membership.MembershipManagement;
import com.x3platform.messages.MessageObject;
import com.x3platform.security.Encrypter;
import com.x3platform.security.authentication.LoginType;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用连接器
 *
 * @author ruanyu
 */
@RestController("com.x3platform.connect.controllers.ConnectAuthController")
@RequestMapping("/api/connect/auth")
public class ConnectAuthController {
  
  private static final String CACHE_KEY_AUTH_FAIL_PREFIX = "x3platform:connect:auth:fail:";
  private static final String CACHE_KEY_CAPTCHA_PREFIX = "x3platform:security:captcha:";
  
  // -------------------------------------------------------
  // 接口地址:/api/connect/authorize
  // -------------------------------------------------------
  
  /**
   * 获取授权码
   *
   * @return 响应的数据内容
   */
  @RequestMapping("/authorize")
  public final String getAuthorizeCode(@RequestBody String data) {
    StringBuilder outString = new StringBuilder();
    // 调试日志
    InternalLogger.getLogger().info("request data:" + data);
    JSONObject req = JSON.parseObject(data);
    String clientId = StringUtil.nullTo(req.getString("clientId"), "");
    String redirectUri = req.getString("redirectUri");
    String responseType = req.getString("responseType");
    String scope = req.getString("scope");
    String loginName = req.getString("loginName");
    String password = req.getString("password");
    String captchaId = req.getString("captchaId");
    String captchaCode = req.getString("captchaCode");
    
    if (StringUtil.isNullOrEmpty(loginName) || StringUtil.isNullOrEmpty(password)) {
      if (StringUtil.isNullOrEmpty(responseType)) {
        return MessageObject.stringify(I18n.getExceptions().text("code_connect_user_or_password_is_empty"),
          I18n.getExceptions().text("text_connect_user_or_password_is_empty"));
      } else {
        // HttpContentTypeHelper.SetValue("html");
        return createLoginPage(clientId, redirectUri, responseType, scope);
      }
    } else {
      // 启用图片验证码
      if (ConnectConfigurationView.getInstance().getEnableCaptcha()) {
        if (StringUtil.isNullOrEmpty(captchaId) || StringUtil.isNullOrEmpty(captchaCode)
          || !CachingManager.contains(CACHE_KEY_CAPTCHA_PREFIX + captchaId)) {
          // 验证码信息无效
          return MessageObject.stringify(I18n.getExceptions().text("code_security_captcha_invalid"),
            I18n.getExceptions().text("text_security_captcha_invalid"));
        }
        
        // 缓存中的验证码信息
        String captchaValue = (String) CachingManager.get(CACHE_KEY_CAPTCHA_PREFIX + captchaId);
        // 移除缓存中的验证码, 确保只用一次.
        CachingManager.delete(CACHE_KEY_CAPTCHA_PREFIX + captchaId);
        
        if (!captchaCode.toLowerCase().equals(captchaValue.toLowerCase())) {
          // 验证码信息无效
          return MessageObject.stringify(I18n.getExceptions().text("code_security_captcha_invalid"),
            I18n.getExceptions().text("text_security_captcha_invalid"));
        }
      }
      
      if (password.startsWith("{RSA}")) {
        // 加密后的密码长度小于 30 的直接返回错误。
        if (password.substring(5).length() < 30) {
          return MessageObject.stringify(I18n.getExceptions().text("code_connect_user_or_password_is_incorrect"),
            I18n.getExceptions().text("text_connect_user_or_password_is_incorrect"));
        }
        password = Encrypter.decryptRsa(password.substring(5));
      }
      
      int authFailCount = 0;
      String key = CACHE_KEY_AUTH_FAIL_PREFIX + loginName;
      
      if (CachingManager.contains(key)) {
        authFailCount = (int) CachingManager.get(key);
      }
      
      if (ConnectConfigurationView.getInstance().getAuthFailLimit() <= authFailCount) {
        // 刷新缓存的有效时间
        CachingManager.set(key, authFailCount, ConnectConfigurationView.getInstance().getAuthFailDuration());
        
        return MessageObject.stringify(I18n.getExceptions().text("code_connect_auth_fail_limit"),
          I18n.getExceptions().format("text_connect_auth_fail_limit",
            String.valueOf(ConnectConfigurationView.getInstance().getAuthFailLimit()),
            String.valueOf(ConnectConfigurationView.getInstance().getAuthFailDuration())));
      }
      
      Account account = KernelContext.getCurrent().getAuthenticationManagement().login(loginName, password, true);
      
      if (account == null) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        
        if (loginName.length() == 11 && Pattern.compile(regex).matcher(loginName).matches()) {
          // 手机号登录
          account = KernelContext.getCurrent().getAuthenticationManagement().login(LoginType.MOBILE, loginName, password, true);
        } else if (loginName.contains("@")) {
          // 邮箱登录
          account = KernelContext.getCurrent().getAuthenticationManagement().login(LoginType.EMAIL, loginName, password, true);
        }
      }
      
      if (account == null) {
        // 记录输入密码错误次数
        CachingManager.set(key, ++authFailCount, ConnectConfigurationView.getInstance().getAuthFailDuration());
        
        if (StringUtil.isNullOrEmpty(responseType)) {
          return MessageObject.stringify(I18n.getExceptions().text("code_connect_user_or_password_is_incorrect"),
            I18n.getExceptions().text("text_connect_user_or_password_is_incorrect"));
        } else {
          // 输出登录页面
          // 设置输出的内容类型，默认为 html 格式。
          return createLoginPage(clientId, redirectUri, responseType, scope);
        }
      } else {
        // 判断用户是否被禁用
        if (account.getStatus() == 0) {
          return MessageObject.stringify(I18n.getExceptions().text("code_connect_disable_is_incorrect"),
            I18n.getExceptions().text("text_connect_disable_is_incorrect"));
        }
        // 获取当前用户信息
        KernelContext.getLog().info("{}({}) Authentication Success.", account.getGlobalName(), account.getLoginName());
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
        ConnectAccessToken token = ConnectContext.getInstance().getConnectAccessTokenService().write(clientId, account.getId());
        
        // 设置会话信息
        // ConnectAccessToken token = ConnectContext.getInstance().getConnectAccessTokenService()
        //   .findOneByAccountId(clientId, account.getId());
        
        String sessionId = token.getId();
        
        KernelContext.getCurrent().getAuthenticationManagement().addSession(clientId, sessionId, account);
        
        String code = ConnectContext.getInstance().getConnectAuthorizationCodeService()
          .getAuthorizationCode(clientId, account);
        
        // responseType == null 则输出令牌信息
        if (StringUtil.isNullOrEmpty(responseType)) {
          outString.append("{\"data\":" + JSON.toJSONString(token) + ",");
          outString.append(MessageObject.stringify("0", "登陆成功。", true) + "}");
          
          String callback = req.getString("callback");
          
          return StringUtil.isNullOrEmpty(callback) ? outString.toString()
            : callback + "(" + outString.toString() + ")";
        } else if (responseType.equals("code")) {
          if (StringUtil.isNullOrEmpty(redirectUri)) {
            return "{\"data\":{\"code\":\"" + code + "\"}," +
              MessageObject.stringify("0", "授权成功。", true) + "}";
          } else {
            // HttpContext.Current.Response.Redirect(CombineUrlAndAuthorizationCode(redirectUri, code));
          }
          return "";
        } else if (responseType.equals("token")) {
          if (StringUtil.isNullOrEmpty(redirectUri)) {
            return "{\"data\":{\"token\":\"" + token.getId() + "\"}," +
              MessageObject.stringify("0", "授权成功。", true) + "}";
          } else {
            // HttpContext.Current.Response.Redirect(CombineUrlAndAccessToken(redirectUri, token));
          }
        } else {
          // HttpContext.Current.Response.Redirect(CombineUrlAndAuthorizationCode(redirectUri, code));
        }
      }
    }
    outString.append(MessageObject.stringify("0", "登陆成功。"));
    
    return outString.toString();
  }
  
  /**
   * @return 退出登录，删除登录日志;
   */
  @RequestMapping("/logout")
  public final String logout(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);
    // 是否需要设置跳转 链接地址， 不需要的话如何处理
    String accessToken = req.getString("accessToken");
    String redirectUri = req.getString("redirectUri");
    String responseType = req.getString("responseType");
    
    if (StringUtil.isNullOrEmpty(accessToken)) {
      accessToken = KernelContext.getCurrent().getAuthenticationManagement().getIdentityValue();
    }
    
    // 设置登录退出时间和添加事件
    StringBuilder outString = new StringBuilder();
    
    try {
      KernelContext.getCurrent().getAuthenticationManagement().removeSession(accessToken);
      
      ConnectContext.getInstance().getConnectAccessTokenService().delete(accessToken);
      
      return MessageObject.stringify("0", "成功退出。");
    } catch (Exception ex) {
      ex.printStackTrace();
      KernelContext.getLog().error("Exception", ex);
      return MessageObject.stringify("-1", "退出失败，请重试。");
    }
  }
  
  
  /**
   * 合并 Url 地址和授权码
   */
  private String combineUrlAndAuthorizationCode(String redirectUri, String code) {
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
  private String combineUrlAndAccessToken(String redirectUri, ConnectAccessToken token) {
    if (redirectUri == null) {
      redirectUri = "";
    }
    
    if (redirectUri.indexOf("?") == -1 && redirectUri.indexOf("&") == -1) {
      return redirectUri + "?token=" + token.getId() + "&expires_in=" + token.getExpiresIn() + "&refresh_token=" + token
        .getRefreshToken();
    } else if (redirectUri.indexOf("?") > -1 && redirectUri.indexOf("&") == -1) {
      return redirectUri + "&token=" + token.getId() + "&expires_in=" + token.getExpiresIn();
    } else {
      return redirectUri + "&token=" + token.getId() + "&expires_in=" + token.getExpiresIn();
    }
  }
  
  // -------------------------------------------------------
  // 接口地址:/api/connect/auth/token
  // -------------------------------------------------------
  
  /***
   * 获取访问令牌
   *
   * @param request 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/token")
  public String getAccessToken(HttpServletRequest request) {
    String code = request.getParameter("code");
    
    ConnectAuthorizationCode authorizationCode = ConnectContext.getInstance().
      getConnectAuthorizationCodeService().findOne(code);
    
    if (authorizationCode == null) {
      return MessageObject.stringify("1", "not find");
    }
    
    // ConnectAccessToken connectAccessToken = ConnectContext.getInstance().getConnectAccessTokenService()
    //   .findOneByAccountId(authorizationCode.getAppKey(), authorizationCode.getAccountId());
    ConnectAccessToken connectAccessToken = ConnectContext.getInstance().getConnectAccessTokenService()
      .write(authorizationCode.getAppKey(), authorizationCode.getAccountId());
    
    if (connectAccessToken == null) {
      return MessageObject.stringify("1", "not find");
    }
    
    StringBuilder outString = new StringBuilder();
    
    outString.append("{\"data\":{");
    outString.append("\"accessToken\":\"" + connectAccessToken.getId() + "\",");
    outString.append("\"tokenType\":\"bearer\",");
    outString.append("\"expiresIn\":\"" + connectAccessToken.getExpiresIn() + "\",");
    outString.append("\"refreshToken\":\"" + connectAccessToken.getRefreshToken() + "\" ");
    outString.append("}," + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");
    
    return outString.toString();
  }
  
  // -------------------------------------------------------
  // 接口地址:/api/connect/auth/refresh
  // -------------------------------------------------------
  
  /**
   * 获取详细信息
   *
   * @param request 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/refresh")
  public String RefreshAccessToken(HttpServletRequest request) {
    
    String clientId = request.getParameter("clientId");
    String refreshToken = request.getParameter("refreshToken");
    
    LocalDateTime expireDate = LocalDateTime.now().plusSeconds(ConnectConfigurationView.getInstance().getSessionTimeLimit());
    
    ConnectAccessToken connectAccessToken = ConnectContext.getInstance().getConnectAccessTokenService()
      .findOneByRefreshToken(clientId, refreshToken);
    
    if (connectAccessToken == null) {
      return MessageObject.stringify("1", "access token not find");
    }
    
    ConnectContext.getInstance().getConnectAccessTokenService().refresh(clientId, refreshToken, expireDate);
    
    connectAccessToken = ConnectContext.getInstance().getConnectAccessTokenService()
      .findOne(connectAccessToken.getId());
    
    StringBuilder outString = new StringBuilder();
    
    outString.append("{\"data\":{");
    outString.append("\"accessToken\":\"" + connectAccessToken.getId() + "\",");
    outString.append("\"tokenType\":\"bearer\",");
    outString.append("\"expiresIn\":\"" + connectAccessToken.getExpiresIn() + "\",");
    outString.append("\"refreshToken\":\"" + connectAccessToken.getRefreshToken() + "\" ");
    outString.append("}," + MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}");
    
    return outString.toString();
  }
  
  // -------------------------------------------------------
  // 接口地址:/api/connect/auth/people
  // -------------------------------------------------------
  
  /**
   * 获取详细信息
   *
   * @param request 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/me")
  public String Me(HttpServletRequest request, @RequestBody String data) {
    // 访问令牌
    String accessToken = "";
    // 授权
    String authorization = request.getHeader("Authorization");
    
    // 如果存在 Authorization 数据则优先读取读取 HTTP 请求的 Authorization 信息
    if (authorization == null) {
      accessToken = request.getParameter("accessToken");
      
      // 增加调用方式 支持 POST 方式 { "accessToken":"xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx" }
      if (accessToken == null) {
        JSONObject req = JSON.parseObject(data);
        accessToken = req.getString("accessToken");
      }
    } else {
      if (authorization.indexOf("Bearer") == 0) {
        // Bearer Token
        accessToken = authorization.substring(7);
      } else {
        accessToken = authorization;
      }
    }
    
    if (StringUtil.isNullOrEmpty(accessToken)) {
      return MessageObject.stringify(I18n.getExceptions().text("code_general_param_is_null"),
        I18n.getExceptions().format("text_general_param_is_null", "accessToken"));
    }
    
    ConnectAccessToken token = ConnectContext.getInstance().getConnectAccessTokenService().findOne(accessToken);
    
    if (token == null) {
      KernelContext.getLog().warn("access token:{}, token not find.", accessToken);
      return MessageObject.stringify(I18n.getExceptions().text("code_http_status_unauthorized"),
        I18n.getExceptions().text("text_http_status_unauthorized"));
    }
    
    Account account = MembershipManagement.getInstance().getAccountService().findOne(token.getAccountId());
    
    if (account == null) {
      KernelContext.getLog().warn("account id:{}, account not find.", token.getAccountId());
      return MessageObject.stringify(I18n.getExceptions().text("code_http_status_unauthorized"),
        I18n.getExceptions().text("text_http_status_unauthorized"));
    }
    
    return "{\"data\":" + ToPeopleJson(account) + "," +
      MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
  
  // -------------------------------------------------------
  // 接口地址:/api/connect/auth/people
  // -------------------------------------------------------
  
  /**
   * 获取详细信息
   *
   * @param request 请求的数据内容
   * @return 响应的数据内容
   */
  @RequestMapping("/people")
  public String people(HttpServletRequest request, @RequestBody String data) {
    // GET /api/connect/auth/people?id=${id}
    
    JSONObject req = JSON.parseObject(data);
    
    String id = request.getParameter("id");
    
    // 增加调用方式 支持 POST 方式 { "id":"xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx" }
    if (id == null) {
      id = req.getString("id");
    }
    
    Account account = MembershipManagement.getInstance().getAccountService().findOne(id);
    
    if (account == null) {
      return MessageObject.stringify("1", "people not find, args[id:" + id + "]");
    }
    
    return "{\"data\":" + ToPeopleJson(account) + "," +
      MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
  
  /**
   * 将人员信息格式化为Json格式
   *
   * @param account 帐号信息
   */
  private String ToPeopleJson(Account account) {
    StringBuilder outString = new StringBuilder();
    
    outString.append("{");
    outString.append("\"id\":\"" + StringUtil.toSafeJson(account.getId()) + "\",");
    outString.append("\"name\":\"" + StringUtil.toSafeJson(account.getName()) + "\",");
    outString.append("\"loginName\":\"" + StringUtil.toSafeJson(account.getLoginName()) + "\",");
    outString.append("\"type\":\"" + account.getType() + "\",");
    outString.append("\"displayName\":\"" + StringUtil.toSafeJson(account.getDisplayName()) + "\",");
    outString.append("\"certifiedAvatar\":\"" + StringUtil.toSafeJson(account.getCertifiedAvatarView()) + "\",");
    outString.append("\"status\":\"" + account.getStatus() + "\"");
    outString.append("}");
    
    return outString.toString();
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
    outString.append(
      "<title>" + connect.getName() + "需要接入验证 - " + KernelConfigurationView.getInstance().getSystemName() + "</title>");
    outString.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
    outString.append(
      "<link rel=\"stylesheet\" media=\"all\" href=\"/resources/styles/default/login.css\" type=\"text/css\" />");
    outString.append("<link rel=\"shortcut icon\" href=\"/favorite.ico\" />");
    outString.append("</head>");
    outString.append("<body>");
    outString.append(
      "<form id=\"authForm\" name=\"authForm\" method=\"POST\" action=\"/api/connect.auth.authorize.aspx?clientId="
        + clientId + (StringUtil.isNullOrEmpty(redirectUri) ? "&redirectUri=" + connect.getRedirectUri()
        : "&redirectUri=" + redirectUri) + (StringUtil.isNullOrEmpty(responseType) ? "&responseType=code"
        : "&responseType=" + responseType) + (StringUtil.isNullOrEmpty(scope) ? "" : "&scope=" + scope) + "\" >");
    outString.append("<input id=\"xml\" name=\"xml\" type=\"hidden\" value=\"\" />");
    outString.append("<div class=\"window-login-main-wrapper\" style=\"width:100%;\" >");
    outString.append("<div class=\"window-login-form-wrapper\" style=\"margin:4px auto 0 auto; float:none;\" >");
    outString.append("<div class=\"window-login-form-container\" >");
    outString.append("<div class=\"window-login-form-input\" >");
    outString.append("<span>帐号</span> ");
    outString.append(
      "<input id=\"loginName\" maxlength=\"20\" type=\"text\" class=\"window-login-input-style\" value=\"\" />");
    outString.append("</div>");
    outString.append("<div class=\"window-login-form-input\" >");
    outString.append("<span>密码</span>");
    outString.append(
      "<input id=\"password\" maxlength=\"20\" type=\"password\" class=\"window-login-input-style\" value=\"\" />");
    outString.append("</div>");
    
    // outString.Append("<div class=\"window-login-form-remember-me\" >");
    // outString.Append("<a href=\"/public/forgot-password.aspx\" target=\"_blank\" >忘记登录密码？</a>");
    // outString.Append("<input id=\"remember\" name=\"remember\" type=\"checkbox\" > <span>记住登录状态</span>");
    // outString.Append("</div>");
    outString.append("<div class=\"window-login-button-wrapper\" >");
    outString.append(
      "<div class=\"window-login-button-submit\" ><a id=\"btnSubmit\" href=\"javascript:if (document.getElementById('loginName').value == '' || document.getElementById('password').value == '') {alert('必须填写帐号和密码。'); return;}; document.getElementById('xml').value = '<?xml version=&quot;1.0&quot; encoding=&quot;utf-8&quot; ?><root><loginName><![CDATA[' + document.getElementById('loginName').value + ']]></loginName><password><![CDATA[' + document.getElementById('password').value + ']]></password></root>'; document.getElementById('authForm').submit();\" >登录</a></div>");
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
