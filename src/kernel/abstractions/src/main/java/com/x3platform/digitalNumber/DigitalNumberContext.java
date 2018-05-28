package com.x3platform.digitalNumber;

import com.x3platform.SpringContext;
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

  @Autowired
  private IDigitalNumberService m_DigitalNumberService = null;

  /**
   * 流水号服务提供者
   */
  public final IDigitalNumberService getDigitalNumberService() {
    return m_DigitalNumberService;
  }

  private DigitalNumberContext() {
    Restart();
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
  public int Restart() {
    try {
      this.Reload();

      // 自增重启次数计数器
      this.restartCount++;
    } catch (RuntimeException ex) {
      // KernelContext.Log.Error(ex.getMessage(), ex);
      throw ex;
    }

    return 0;
  }
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
  ///#endregion

  private void Reload() {
    if (this.restartCount > 0) {
      // KernelContext.Log.Info(String.format(I18n.Strings["application_is_reloading"], DigitalNumberConfiguration.ApplicationName));

      // 重新加载配置信息
      // DigitalNumberConfigurationView.Instance.Reload();
    } else {
      // KernelContext.Log.Info(String.format(I18n.Strings["application_is_loading"], DigitalNumberConfiguration.ApplicationName));
    }

    // 创建对象构建器(Spring.NET)
    // String springObjectFile = DigitalNumberConfigurationView.Instance.Configuration.keySet()["SpringObjectFile"].Value;

    // SpringObjectBuilder objectBuilder = SpringObjectBuilder.Create(DigitalNumberConfiguration.ApplicationName, springObjectFile);

    // 创建数据服务对象
    // this.m_DigitalNumberService = objectBuilder.<IDigitalNumberService>GetObject(IDigitalNumberService.class);
    this.m_DigitalNumberService = SpringContext.getBean(IDigitalNumberService.class);
    // KernelContext.Log.Info(String.format(I18n.Strings["application_is_successfully_loaded"], DigitalNumberConfiguration.ApplicationName));
  }

  /**
   * 生成通用的流水编号
   *
   * @param name
   * @return
   */
  public static String Generate(String name) {
    return getInstance().getDigitalNumberService().generate(name);
  }

  /**
   * 根据自定义的编号前缀生成的流水编号
   */
  public static String GenerateCodeByPrefixCode(String entityTableName, String prefixCode) {
    return getInstance().getDigitalNumberService().generateCodeByPrefixCode(entityTableName, prefixCode, "{prefix}{code}");
  }

  /**
   * 根据自定义的编号前缀生成的流水编号
   */
  public static String GenerateCodeByPrefixCode(String entityTableName, String prefixCode, int incrementCodeLength) {
    return getInstance().getDigitalNumberService().generateCodeByPrefixCode(entityTableName, prefixCode, "{prefix}{code:" + incrementCodeLength + "}");
  }

  /**
   * 根据自定义的编号前缀生成的流水编号
   */
  public static String GenerateCodeByCategoryId(String entityTableName, String entityCategoryTableName, String entityCategoryId) {
    return getInstance().getDigitalNumberService().generateCodeByCategoryId(entityTableName, entityCategoryTableName, entityCategoryId, "{prefix}{code}");
  }

  /**
   * 根据自定义的编号前缀生成的流水编号
   */
  public static String GenerateCodeByCategoryId(String entityTableName, String entityCategoryTableName, String entityCategoryId, int incrementCodeLength) {
    return getInstance().getDigitalNumberService().generateCodeByCategoryId(entityTableName, entityCategoryTableName, entityCategoryId, "{prefix}{code:" + incrementCodeLength + "}");
  }

  /**
   * 根据自定义的编号前缀生成日期流水编号
   */
  public static String GenerateDateCodeByPrefixCode(String entityTableName, String prefixCode) {
    return getInstance().getDigitalNumberService().generateCodeByPrefixCode(entityTableName, prefixCode, "{prefix}{date}{code}");
  }

  /**
   * 根据自定义的编号前缀生成日期流水编号
   */
  public static String GenerateDateCodeByPrefixCode(String entityTableName, String prefixCode, int incrementCodeLength) {
    return getInstance().getDigitalNumberService().generateCodeByPrefixCode(entityTableName, prefixCode, "{prefix}{date}{code:" + incrementCodeLength + "}");
  }

  /**
   * 根据自定义的编号前缀生成日期流水编号
  public static String GenerateDateCodeByPrefixCode(GenericSqlCommand command, String entityTableName, String prefixCode) {
    return getInstance().getDigitalNumberService().GenerateCodeByPrefixCode(command, entityTableName, prefixCode, "{prefix}{date}{code}");
  }
   */

  /**
   * 根据自定义的编号前缀生成日期流水编号
  public static String GenerateDateCodeByPrefixCode(GenericSqlCommand command, String entityTableName, String prefixCode, int incrementCodeLength) {
    return getInstance().getDigitalNumberService().GenerateCodeByPrefixCode(command, entityTableName, prefixCode, "{prefix}{date}{code:" + incrementCodeLength + "}");
  }
   */

  /**
   * 根据自定义的编号前缀生成的流水编号
   */
  public static String GenerateDateCodeByCategoryId(String entityTableName, String entityCategoryTableName, String entityCategoryId) {
    return getInstance().getDigitalNumberService().generateCodeByCategoryId(entityTableName, entityCategoryTableName, entityCategoryId, "{prefix}{date}{code}");
  }

  /**
   * 根据自定义的编号前缀生成的流水编号
   */
  public static String GenerateDateCodeByCategoryId(String entityTableName, String entityCategoryTableName, String entityCategoryId, int incrementCodeLength) {
    return getInstance().getDigitalNumberService().generateCodeByCategoryId(entityTableName, entityCategoryTableName, entityCategoryId, "{prefix}{date}{code:" + incrementCodeLength + "}");
  }

  /**
   * 根据自定义的编号前缀生成的流水编号
  public static String GenerateDateCodeByCategoryId(GenericSqlCommand command, String entityTableName, String entityCategoryTableName, String entityCategoryId) {
    return getInstance().getDigitalNumberService().GenerateCodeByCategoryId(command, entityTableName, entityCategoryTableName, entityCategoryId, "{prefix}{date}{code}");
  }
   */

  /**
   * 根据自定义的编号前缀生成的流水编号
  public static String GenerateDateCodeByCategoryId(GenericSqlCommand command, String entityTableName, String entityCategoryTableName, String entityCategoryId, int incrementCodeLength) {
    return getInstance().getDigitalNumberService().GenerateCodeByCategoryId(command, entityTableName, entityCategoryTableName, entityCategoryId, "{prefix}{date}{code:" + incrementCodeLength + "}");
  } */
}
