package com.x3platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import com.x3platform.CacheBuffer.*;
// import com.x3platform.Collections.*;
// import com.x3platform.Configuration.*;
// import com.x3platform.Membership.*;
// import com.x3platform.Security.Authentication.*;

import com.x3platform.util.StringUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Timer;

/**
 * 核心环境
 */
public final class KernelContext implements IContext {
  /**
   * 日志记录器
   */
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * 日志记录
   */
  public static Logger getLog() {
    return getCurrent().logger;
  }

  private Timer timer = new Timer();

  private static volatile KernelContext instance = null;

  private static Object lockObject = new Object();

  /**
   * 当前信息
   */
  public static KernelContext getCurrent() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          instance = new KernelContext();
        }
      }
    }

    return instance;
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

  // public IAccountInfo getUser() {
  //   return this.getAuthenticationManagement().GetAuthUser();
  // }

  // private KernelConfiguration configuration = null;

  /**
   * 配置信息
   */
  // public KernelConfiguration getConfiguration() {
  //  return configuration;
  // }
  ///#endregion

  ///#region 属性:AuthenticationManagement
  // private IAuthenticationManagement authenticationManagement = null;

  /**
   * 验证管理
   */
  // public IAuthenticationManagement getAuthenticationManagement() {
  //   return authenticationManagement;
  // }
  private KernelContext() {
    this.Reload();
  }

  /**
   * 重新加载
   */
  @Override
  public void Reload() {
    // this.configuration = KernelConfigurationView.Instance.Configuration;

    // String authenticationManagementType = KernelConfigurationView.Instance.AuthenticationManagementType;

    // this.authenticationManagement = (IAuthenticationManagement) CreateObject(authenticationManagementType);

    // -------------------------------------------------------
    // 设置定时器
    // -------------------------------------------------------

    // 定时清理临时目录下的临时文件 设置为 3 天
    /*
    if (!(new java.io.File(KernelConfigurationView.Instance.ApplicationTempPathRoot)).isDirectory()) {
      DirectoryHelper.Create(KernelConfigurationView.Instance.ApplicationTempPathRoot);
    }

    timer.Enabled = true;

    // 每天检测一次目录
    timer.Interval = 24 * 60 * 60 * 1000;

    timer.Elapsed += (Object sender, ElapsedEventArgs e) ->
    {
      try {
        String[] files = Directory.GetFiles(KernelConfigurationView.Instance.ApplicationTempPathRoot, "*.*", SearchOption.AllDirectories);

        for (String file : files) {
          java.time.LocalDateTime createdTime = File.GetCreationTime(file);

          if (createdTime.plusDays(KernelConfigurationView.Instance.ApplicationTempFileRemoveTimerInterval).compareTo(java.time.LocalDateTime.now()) < 0) {
            (new java.io.File(file)).delete();

            logger.Info("Delete expired temporary file:" + file);
          }
        }
      } catch (RuntimeException ex) {
        logger.Error(ex);
      }
    };

    timer.Start();
    */
  }

  // -------------------------------------------------------
  // 工具函数
  // -------------------------------------------------------

  /**
   * 创建对象
   *
   * @param type
   * @return
   */
  public static Object CreateObject(String type) {
    return CreateObject(type, new Object[]{});
  }

  /**
   * 创建对象
   *
   * @param type
   * @param args
   * @return
   */
  public static Object CreateObject(String type, Object... args) {
    if (StringUtil.isNullOrEmpty(type)) {
      return null;
    }

    Class objectType = null;

    try {
      objectType = Class.forName(type);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    if (objectType == null) {
      return null;
    }

    // return System.Activator.CreateInstance(objectType, args);

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
   * @param type
   * @return 格式:com.x3platform.KernelContext,com.x3platform
   */
  public static String ParseObjectType(java.lang.Class type) {
    if (type == null) {
      return null;
    }

    // String assemblyQualifiedName = type.AssemblyQualifiedName;
    String assemblyQualifiedName = type.getName();

    int length = assemblyQualifiedName.indexOf(',', assemblyQualifiedName.indexOf(',') + 1);

    return assemblyQualifiedName.substring(0, length);
  }
}
