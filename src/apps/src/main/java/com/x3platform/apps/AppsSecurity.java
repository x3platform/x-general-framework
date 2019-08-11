package com.x3platform.apps;

import com.x3platform.apps.models.Application;
import com.x3platform.membership.Account;

import java.util.*;

/**
 * 应用安全管理
 */
public final class AppsSecurity {
  private static Map<String, Application> applicationCache = null;

  private static Object lockObject = new Object();

  /**
   * 查找应用的信息
   */
  public static Application findApplication(String applicationName) {
    Application application = null;

    synchronized (lockObject) {
      if (applicationCache == null) {
        applicationCache = new HashMap<String, Application>();
      }

      if (applicationCache.containsKey(applicationName)) {
        application = applicationCache.get(applicationName);
      } else {
        application = AppsContext.getInstance().getApplicationService().findOneByApplicationName(applicationName);

        if (application != null) {
          applicationCache.put(applicationName, application);
        }
      }
    }

    return application;
  }

  /**
   * 重置应用管理员
   */
  public static void resetApplicationAdministrators(String applicationName) {
    synchronized (lockObject) {
      if (applicationCache != null && applicationCache.containsKey(applicationName)) {
        applicationCache.remove(applicationName);
      }
    }
  }
  /**
   * 判断用户是否是应用的默认管理员
   */
  public static boolean isAdministrator(Account account, String applicationName) {
    Application application = findApplication(applicationName);
    return application == null ? false : AppsContext.getInstance().getApplicationService().isAdministrator(account, application.getId());
  }

  /**
   * 判断用户是否是应用的默认审查员
   */
  public static boolean isReviewer(Account account, String applicationName) {
    Application application = findApplication(applicationName);

    return application == null ? false : AppsContext.getInstance().getApplicationService().isReviewer(account, application.getId());
  }

  /**
   * 判断用户是否是应用的默认可访问成员
   */
  public static boolean isMember(Account account, String applicationName) {
    Application application = findApplication(applicationName);

    if (application == null) {
      return false;
    }

    // 管理员 默认是应用的成员
    if (AppsContext.getInstance().getApplicationService().isAdministrator(account, application.getId())) {
      return true;
    }

    // 审查员 默认是应用的成员
    if (AppsContext.getInstance().getApplicationService().isReviewer(account, application.getId())) {
      return true;
    }

    // 默认的普通成员情况
    return AppsContext.getInstance().getApplicationService().isMember(account, application.getId());
  }

  /**
   * 判断用户是否是拥有授权的功能的
   */
  public static boolean hasAuthorizedFeature(Account account, String applicationFeatureName) {
    // TODO 待处理
    //
    Application application = findApplication(applicationFeatureName);

    if (application == null) {
      return false;
    }

    // 管理员 默认是应用的成员
    if (AppsContext.getInstance().getApplicationService().isAdministrator(account, application.getId())) {
      return true;
    }

    // 审查员 默认是应用的成员
    if (AppsContext.getInstance().getApplicationService().isReviewer(account, application.getId())) {
      return true;
    }

    // 默认的普通成员情况
    return AppsContext.getInstance().getApplicationService().isMember(account, application.getId());
  }
}
