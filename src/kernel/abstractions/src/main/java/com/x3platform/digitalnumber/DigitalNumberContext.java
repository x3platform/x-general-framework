package com.x3platform.digitalnumber;

import com.x3platform.KernelContext;
import com.x3platform.SpringContext;
import com.x3platform.data.GenericSqlCommand;
import com.x3platform.digitalnumber.configuration.DigitalNumberConfiguration;
import com.x3platform.digitalnumber.configuration.DigitalNumberConfigurationView;
import com.x3platform.digitalnumber.services.DigitalNumberService;
import com.x3platform.globalization.I18n;
import com.x3platform.plugins.CustomPlugin;

import java.util.List;

/**
 * 流水号上下文环境
 *
 * @author ruanyu
 */
public class DigitalNumberContext extends CustomPlugin {

  @Override
  public String getName() {
    return "流水号";
  }

  private static volatile DigitalNumberContext sInstance = null;

  private static Object lockObject = new Object();

  /**
   * 实例
   *
   * @return 流水号上下文环境
   */
  public static DigitalNumberContext getInstance() {
    if (sInstance == null) {
      synchronized (lockObject) {
        if (sInstance == null) {
          sInstance = new DigitalNumberContext();
        }
      }
    }

    return sInstance;
  }

  private DigitalNumberService mDigitalNumberService = null;

  /**
   * 流水号服务提供者
   */
  public final DigitalNumberService getDigitalNumberService() {
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
   * 重启
   *
   * @return 消息代码 0-重启成功, 大于0-重启失败.
   */
  @Override
  public int restart() {
    try {
      reload();
      // 自增重启次数计数器
      restartCount++;
    } catch (RuntimeException ex) {
      KernelContext.getLog().error(ex.getMessage(), ex);
      throw ex;
    }

    return 0;
  }

  private void reload() {
    if (restartCount > 0) {
      KernelContext.getLog().info(I18n.getStrings().text("application_is_reloading"),
        DigitalNumberConfiguration.APPLICATION_NAME);

      // 重新加载配置信息
      DigitalNumberConfigurationView.getInstance().reload();
    } else {
      KernelContext.getLog().info(I18n.getStrings().text("application_is_loading"),
        DigitalNumberConfiguration.APPLICATION_NAME);
    }

    // 创建数据服务对象
    mDigitalNumberService = SpringContext
      .getBean("com.x3platform.digitalnumber.services.DigitalNumberService", DigitalNumberService.class);

    KernelContext.getLog().info(I18n.getStrings().text("application_is_successfully_loaded"),
      DigitalNumberConfiguration.APPLICATION_NAME);
  }

  /**
   * 生成通用的流水编号
   */
  public static String generate(String name) {
    return getInstance().getDigitalNumberService().generate(name);
  }

  /**
   * 生成多个通用的流水编号
   * @param name 规则名称
   * @param length 编码数量
   */
  public static List<String> generates(String name, int length) {
    return getInstance().getDigitalNumberService().generates(name, length);
  }

  /**
   * 根据自定义的编号前缀生成的流水编号
   */
  public static String generateCodeByPrefixCode(String entityTableName, String prefixCode) {
    return getInstance().getDigitalNumberService()
      .generateCodeByPrefixCode(entityTableName, prefixCode, "{prefix}{code}");
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
  public static String generateCodeByCategoryId(String entityTableName, String entityCategoryTableName,
                                                String entityCategoryId) {
    return getInstance().getDigitalNumberService()
      .generateCodeByCategoryId(entityTableName, entityCategoryTableName, entityCategoryId, "{prefix}{code}");
  }

  /**
   * 根据自定义的编号前缀生成的流水编号
   */
  public static String generateCodeByCategoryId(String entityTableName, String entityCategoryTableName,
                                                String entityCategoryId, int incrementCodeLength) {
    return getInstance().getDigitalNumberService()
      .generateCodeByCategoryId(entityTableName, entityCategoryTableName, entityCategoryId,
        "{prefix}{code:" + incrementCodeLength + "}");
  }

  /**
   * 根据自定义的编号前缀生成日期流水编号
   */
  public static String generateDateCodeByPrefixCode(String entityTableName, String prefixCode) {
    return getInstance().getDigitalNumberService()
      .generateCodeByPrefixCode(entityTableName, prefixCode, "{prefix}{date}{code}");
  }

  /**
   * 根据自定义的编号前缀生成日期流水编号
   */
  public static String generateDateCodeByPrefixCode(String entityTableName, String prefixCode,
                                                    int incrementCodeLength) {
    return getInstance().getDigitalNumberService()
      .generateCodeByPrefixCode(entityTableName, prefixCode, "{prefix}{date}{code:" + incrementCodeLength + "}");
  }

  /**
   * 根据自定义的编号前缀生成日期流水编号
   */
  public static String generateDateCodeByPrefixCode(GenericSqlCommand command, String entityTableName,
                                                    String prefixCode) {
    return getInstance().getDigitalNumberService().generateCodeByPrefixCode(command, entityTableName,
      prefixCode, "{prefix}{date}{code}");
  }

  /**
   * 根据自定义的编号前缀生成日期流水编号
   */
  public static String generateDateCodeByPrefixCode(GenericSqlCommand command, String entityTableName,
                                                    String prefixCode, int incrementCodeLength) {
    return getInstance().getDigitalNumberService().generateCodeByPrefixCode(command, entityTableName,
      prefixCode, "{prefix}{date}{code:" + incrementCodeLength + "}");
  }

  /**
   * 根据自定义的编号前缀生成的流水编号
   */
  public static String generateDateCodeByCategoryId(String entityTableName, String entityCategoryTableName,
                                                    String entityCategoryId) {
    return getInstance().getDigitalNumberService()
      .generateCodeByCategoryId(entityTableName, entityCategoryTableName, entityCategoryId, "{prefix}{date}{code}");
  }

  /**
   * 根据自定义的编号前缀生成的流水编号
   */
  public static String generateDateCodeByCategoryId(String entityTableName, String entityCategoryTableName,
                                                    String entityCategoryId, int incrementCodeLength) {
    return getInstance().getDigitalNumberService()
      .generateCodeByCategoryId(entityTableName, entityCategoryTableName, entityCategoryId,
        "{prefix}{date}{code:" + incrementCodeLength + "}");
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
