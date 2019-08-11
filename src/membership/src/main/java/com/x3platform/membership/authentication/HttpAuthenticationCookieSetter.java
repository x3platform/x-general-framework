package com.x3platform.membership.authentication;

import com.x3platform.KernelContext;
import com.x3platform.configuration.KernelConfigurationView;
import com.x3platform.membership.configuration.MembershipConfigurationView;
import com.x3platform.util.HttpContextUtil;

/**
 * 验证的HttpCookie设置器
 */
public final class HttpAuthenticationCookieSetter {
  /**
   * 设置当前用户信息
   */
  public static void setUserCookies(String accountIdentity) {
    // 名称
    String identityName = KernelContext.getCurrent().getAuthenticationManagement().getIdentityName();
    // 域信息
    String domain = "";

    // 单点登录功能启用状态下, 查找相关的根域名信息
    if (MembershipConfigurationView.getInstance().getSingleSignOn()) {
      domain = parseDomain();
    }

    HttpContextUtil.setCookie(identityName, accountIdentity, domain, true);
  }

  /**
   * 清除当前用户信息
   */
  public static void clearUserCookies() {
    // 名称
    String identityName = KernelContext.getCurrent().getAuthenticationManagement().getIdentityName();
    // 所属域信息
    String domain = "";

    // 单点登录功能启用状态下, 查找相关的根域名信息
    if (MembershipConfigurationView.getInstance().getSingleSignOn()) {
      domain = parseDomain();
    }

    HttpContextUtil.setCookie(identityName, null, domain, 0, true);
  }

  /**
   * 分析域信息
   *
   * @return
   */
  public static String parseDomain() {
    String serverName = HttpContextUtil.getRequest().getServerName();
    if (serverName == null) {
      serverName = KernelConfigurationView.getInstance().getDomain();
    }
    return parseDomain(serverName);
  }

  /**
   * 分析域信息
   *
   * @param serverName
   * @return
   */
  public static String parseDomain(String serverName) {
    String domain = serverName;

//    IPAddress address = IPAddress.None;
//
//    tangible.RefObject<System.Net.IPAddress> tempRef_address = new tangible.RefObject<System.Net.IPAddress>(address);
//    if (IPAddress.TryParse(serverName, tempRef_address)) {
//      address = tempRef_address.argValue;
//      return domain;
//    } else {
//      address = tempRef_address.argValue;
//    }

    int point = 0;

    if (serverName.lastIndexOf(".") == -1) {
      // 没有点的情况
      // => 不做处理, 设置为默认路径
    } else {
      point = serverName.lastIndexOf(".");

      if (serverName.substring(0, point).lastIndexOf(".") == -1) {
        // 只有一个点的情况
        // => 例如 workspace.com 直接取 workspace.com

        domain = serverName;
      } else {
        // 有两个或两个以上的点的情况
        // => my.workspace.com 取 workspace.com

        point = serverName.substring(0, point).lastIndexOf(".") + 1;

        domain = serverName.substring(point, serverName.length());
      }
    }

    return domain;
  }
}
