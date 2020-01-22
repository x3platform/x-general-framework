package com.x3platform;

import com.x3platform.configuration.KernelConfigurationView;
import com.x3platform.membership.Account;
import com.x3platform.security.authentication.AuthenticationManagement;
import com.x3platform.util.StringUtil;
import java.lang.reflect.InvocationTargetException;
import org.slf4j.Logger;

/**
 * 核心环境
 */
public final class KernelContext implements Context {

  /**
   * 日志记录器
   */
  private Logger logger = InternalLogger.getLogger();

  /**
   * 日志记录
   */
  public static Logger getLog() {
    return getCurrent().logger;
  }

  private static volatile KernelContext sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 当前信息
   */
  public static KernelContext getCurrent() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new KernelContext();
        }
      }
    }

    return sInstance;
  }

  /**
   * 名称
   */
  @Override
  public String getName() {
    return "核心环境";
  }

  /**
   * 用户信息
   */
  public Account getUser() {
    return getAuthenticationManagement().getAuthUser();
  }

  private AuthenticationManagement authenticationManagement = null;

  /**
   * 验证管理
   */
  public AuthenticationManagement getAuthenticationManagement() {
    return authenticationManagement;
  }

  private KernelContext() {
    reload();
  }

  /**
   * 重新加载
   */
  @Override
  public void reload() {
    String authenticationManagementType = KernelConfigurationView.getInstance().getAuthenticationManagementType();

    authenticationManagement = (AuthenticationManagement) createObject(authenticationManagementType);

    logger.info("AuthenticationManagementType:{}", authenticationManagementType);
  }

  // -------------------------------------------------------
  // 工具函数
  // -------------------------------------------------------

  /**
   * 创建对象
   */
  public static Object createObject(String type) {
    return createObject(type, new Object[]{});
  }

  /**
   * 创建对象
   */
  public static Object createObject(String type, Object... args) {
    if (StringUtil.isNullOrEmpty(type)) {
      return null;
    }

    Class objectType = null;

    try {
      objectType = Class.forName(type);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      InternalLogger.getLogger().error(e.toString());
    }

    if (objectType == null) {
      return null;
    }

    // 参数类型数组
    Class[] parameterTypes = new Class[args.length];

    for (int i = 0; i < args.length; i++) {
      parameterTypes[i] = args[i].getClass();
    }

    // 根据参数类型获取相应的构造函数
    java.lang.reflect.Constructor constructor = null;

    try {
      constructor = objectType.getConstructor(parameterTypes);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }

    try {
      //根据获取的构造函数和参数，创建实例
      return constructor.newInstance(args);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * 解析对象类型
   *
   * @return 格式: com.x3platform.KernelContext
   */
  public static String parseObjectType(Class type) {
    if (type == null) {
      return null;
    }

    String assemblyQualifiedName = type.getName();

    return assemblyQualifiedName;
  }
}
