package com.x3platform.digitalNumber;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.digitalNumber.configuration.DigitalNumberConfiguration;
import com.x3platform.digitalNumber.configuration.DigitalNumberConfigurationView;
import com.x3platform.globalization.I18n;
import org.springframework.beans.factory.annotation.Autowired;

import com.x3platform.plugins.*;

// import com.x3platform.digitalNumber.Configuration.*;
import com.x3platform.digitalNumber.services.IDigitalNumberService;
// import Common.Logging.*;
// import X3Platform.Globalization.*;

/**
 * 流水号上下文环境
 */
public class DigitalNumberContext extends CustomPlugin {
  @Override
  public String getName() {
    return "流水号";
  }

  private static volatile DigitalNumberContext instance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   */
  public static DigitalNumberContext getInstance() {
    if (instance == null) {
      synchronized (lockObject) {
        if (instance == null) {
          instance = new DigitalNumberContext();
        }
      }
    }

    return instance;
  }

  private IDigitalNumberService mDigitalNumberService = null;

  /**
   * 流水号服务提供者
   */
  public final IDigitalNumberService getDigitalNumberService() {
    return mDigitalNumberService;
  }

  private DigitalNumberContext() {
    restart();
  }

  /**
   * 重启次数计数器
   */
  private int restartCount = 0;

  /**
   * 重启插件
   *
   * @return 返回信息. =0代表重启成功, >0代表重启失败.
   */
  @Override
  public int restart() {
    try {
      this.reload();

      // 自增重启次数计数器
      this.restartCount++;
    } catch (RuntimeException ex) {
      // KernelContext.Log.Error(ex.getMessage(), ex);
      throw ex;
    }

    return 0;
  }

  private void reload() {
    if (this.restartCount > 0) {
      KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_reloading"), DigitalNumberConfiguration.ApplicationName));

      // 重新加载配置信息
      DigitalNumberConfigurationView.getInstance().reload();
    } else {
      KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_loading"), DigitalNumberConfiguration.ApplicationName));
    }

    // 创建数据服务对象
    // this.m_DigitalNumberService = objectBuilder.<IDigitalNumberService>GetObject(IDigitalNumberService.class);
    this.mDigitalNumberService = SpringContext.getBean("digitalNumberService", IDigitalNumberService.class);

    KernelContext.getLog().info(String.format(I18n.getStrings().text("application_is_successfully_loaded"), DigitalNumberConfiguration.ApplicationName));
  }

  /**
   * 生成通用的流水编号
   *
   * @param name
   * @return
   */
  public static String generate(String name) {
    return getInstance().getDigitalNumberService().generate(name);
  }

  /**
   * 根据自定义的编号前缀生成的流水编号
   */
  public static String generateCodeByPrefixCode(String entityTableName, String prefixCode) {
    return getInstance().getDigitalNumberService().generateCodeByPrefixCode(entityTableName, prefixCode, "{prefix}{code}");
  }

  /**
   * 根据自定义的编号前缀生成的流水编号
   */
  public static String generateCodeByPrefixCode(String entityTableName, String prefixCode, int incrementCodeLength) {
    return getInstance().getDigitalNumberService().generateCodeByPrefixCode(entityTableName, prefixCode, "{prefix}{code:" + incrementCodeLength + "}");
  }

  /**
   * 根据自定义的编号前缀生成的流水编号
   */
  public static String generateCodeByCategoryId(String entityTableName, String entityCategoryTableName, String entityCategoryId) {
    return getInstance().getDigitalNumberService().generateCodeByCategoryId(entityTableName, entityCategoryTableName, entityCategoryId, "{prefix}{code}");
  }

  /**
   * 根据自定义的编号前缀生成的流水编号
   */
  public static String generateCodeByCategoryId(String entityTableName, String entityCategoryTableName, String entityCategoryId, int incrementCodeLength) {
    return getInstance().getDigitalNumberService().generateCodeByCategoryId(entityTableName, entityCategoryTableName, entityCategoryId, "{prefix}{code:" + incrementCodeLength + "}");
  }

  /**
   * 根据自定义的编号前缀生成日期流水编号
   */
  public static String generateDateCodeByPrefixCode(String entityTableName, String prefixCode) {
    return getInstance().getDigitalNumberService().generateCodeByPrefixCode(entityTableName, prefixCode, "{prefix}{date}{code}");
  }

  /**
   * 根据自定义的编号前缀生成日期流水编号
   */
  public static String generateDateCodeByPrefixCode(String entityTableName, String prefixCode, int incrementCodeLength) {
    return getInstance().getDigitalNumberService().generateCodeByPrefixCode(entityTableName, prefixCode, "{prefix}{date}{code:" + incrementCodeLength + "}");
  }

  /**
   * 根据自定义的编号前缀生成日期流水编号
   public static String generateDateCodeByPrefixCode(GenericSqlCommand command, String entityTableName, String prefixCode) {
   return getInstance().getDigitalNumberService().GenerateCodeByPrefixCode(command, entityTableName, prefixCode, "{prefix}{date}{code}");
   }
   */

  /**
   * 根据自定义的编号前缀生成日期流水编号
   public static String generateDateCodeByPrefixCode(GenericSqlCommand command, String entityTableName, String prefixCode, int incrementCodeLength) {
   return getInstance().getDigitalNumberService().GenerateCodeByPrefixCode(command, entityTableName, prefixCode, "{prefix}{date}{code:" + incrementCodeLength + "}");
   }
   */

  /**
   * 根据自定义的编号前缀生成的流水编号
   */
  public static String generateDateCodeByCategoryId(String entityTableName, String entityCategoryTableName, String entityCategoryId) {
    return getInstance().getDigitalNumberService().generateCodeByCategoryId(entityTableName, entityCategoryTableName, entityCategoryId, "{prefix}{date}{code}");
  }

  /**
   * 根据自定义的编号前缀生成的流水编号
   */
  public static String generateDateCodeByCategoryId(String entityTableName, String entityCategoryTableName, String entityCategoryId, int incrementCodeLength) {
    return getInstance().getDigitalNumberService().generateCodeByCategoryId(entityTableName, entityCategoryTableName, entityCategoryId, "{prefix}{date}{code:" + incrementCodeLength + "}");
  }

  /**
   * 根据自定义的编号前缀生成的流水编号
   public static String generateDateCodeByCategoryId(GenericSqlCommand command, String entityTableName, String entityCategoryTableName, String entityCategoryId) {
   return getInstance().getDigitalNumberService().GenerateCodeByCategoryId(command, entityTableName, entityCategoryTableName, entityCategoryId, "{prefix}{date}{code}");
   }
   */

  /**
   * 根据自定义的编号前缀生成的流水编号
   public static String generateDateCodeByCategoryId(GenericSqlCommand command, String entityTableName, String entityCategoryTableName, String entityCategoryId, int incrementCodeLength) {
   return getInstance().getDigitalNumberService().GenerateCodeByCategoryId(command, entityTableName, entityCategoryTableName, entityCategoryId, "{prefix}{date}{code:" + incrementCodeLength + "}");
   } */
}
