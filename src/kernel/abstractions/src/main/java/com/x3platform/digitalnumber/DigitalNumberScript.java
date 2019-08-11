package com.x3platform.digitalnumber;

import com.x3platform.RefObject;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import com.x3platform.util.UUIDUtil;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 流水号脚本
 */
public class DigitalNumberScript {
  /**
   * 运行流水号前缀脚本
   *
   * @param expression
   * @param prefixCode
   * @return
   */
  public static String runPrefixScript(String expression, String prefixCode) {
    return runPrefixScript(expression, prefixCode, java.time.LocalDateTime.now());
  }

  /**
   * 运行流水号前缀脚本
   *
   * @param expression
   * @param prefixCode
   * @param updateDate
   * @return
   */
  public static String runPrefixScript(String expression, String prefixCode, java.time.LocalDateTime updateDate) {
    String result = null;

    // 处理前缀标签
    expression = StringUtil.isNullOrEmpty(prefixCode) ? expression.replace("{prefix}", "") : expression.replace("{prefix}", "{tag:" + prefixCode + "}");

    // 拆分为子表达式列表
    List<String[]> subexpressions = splitExpression(expression);

    for (String[] subexpression : subexpressions) {
      // 忽略需要自增种子的表达式类型: code | int | dailyIncrement
      if ("code".equals(subexpression[0]) || "int".equals(subexpression[0]) || "dailyIncrement".equals(subexpression[0])) {
        continue;
      }

      result += analyzeExpression(subexpression, updateDate, new RefObject<Integer>(0));
    }

    return result;
  }

  /**
   * @param expression
   * @param seed
   * @return
   */
  public static String runScript(String expression, RefObject<Integer> seed) {
    return runScript(expression, java.time.LocalDateTime.now(), seed);
  }

  /**
   * @param expression
   * @param updateDate
   * @param seed
   * @return
   */
  public static String runScript(String expression, java.time.LocalDateTime updateDate, RefObject<Integer> seed) {
    return runScript(expression, "", updateDate, seed);
  }

  /**
   * @param expression
   * @param updateDate
   * @param seed
   * @return
   */
  public static String runScript(String expression, String prefixCode, java.time.LocalDateTime updateDate, RefObject<Integer> seed) {
    String result = "";

    // 处理前缀标签
    expression = StringUtil.isNullOrEmpty(prefixCode) ? expression.replace("{prefix}", "") : expression.replace("{prefix}", "{tag:" + prefixCode + "}");

    // 种子自增
    seed.value++;

    // 拆分为子表达式列表
    List<String[]> subexpressions = splitExpression(expression);

    for (String[] subexpression : subexpressions) {
      result += analyzeExpression(subexpression, updateDate, seed);
    }

    return result;
  }

  /**
   * @param expression
   * @return
   */
  private static List<String[]> splitExpression(String expression) {
    // 表达式 示例
    // {dailyIncrement:seed:6}
    // {date:yyyyMMdd}{tag:-}{int:seed}
    List<String[]> subexpressions = new ArrayList<String[]>();

    String[] list = expression.split("[}]", -1);

    for (String item : list) {
      if (item.length() < 2) {
        continue;
      }

      subexpressions.add(item.substring(1, item.length()).split("[:]", -1));
    }

    return subexpressions;
  }

  /**
   * 分析表达式
   *
   * @param subexpression
   * @param updateDate
   * @param seed
   * @return 结果
   */
  private static String analyzeExpression(String[] subexpression, LocalDateTime updateDate,
    RefObject<Integer> seed) {
    switch (subexpression[0]) {
      // 标签类型
      case "tag":
        return subexpression[1];

      case "date":
        // 日期类型
        String pattern = (subexpression.length == 2) ? subexpression[1] : "yyyyMMdd";
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));

      case "timestamp":
        // 时间戳
        return String.valueOf(DateUtil.getTimestamp());

      case "guid":
        // Guid类型
        if (subexpression.length == 1) {
          return UUIDUtil.toString(UUID.randomUUID());
        } else if (subexpression.length == 3) {
          if ("U".equals(subexpression[2].toUpperCase())) {
            // U = Upper
            return UUIDUtil.toString(UUID.randomUUID(), subexpression[1]).toUpperCase();
          } else if ("L".equals(subexpression[2].toUpperCase())) {
            // L = Lower
            return UUIDUtil.toString(UUID.randomUUID(), subexpression[1]).toLowerCase();
          } else {
            return UUIDUtil.toString(UUID.randomUUID(), subexpression[1]);
          }
        } else {
          if ("32digits".equals(subexpression[1].toUpperCase())) {
            return UUIDUtil.toString(UUID.randomUUID(), "N");
          } else {
            return UUIDUtil.toString(UUID.randomUUID(), subexpression[1]);
          }
        }

        // 随机字符串
      case "random":
        if (subexpression.length == 3) {
          return StringUtil.toRandom(subexpression[1], Integer.parseInt(subexpression[2]));
        } else {
          return StringUtil.toRandom(Integer.parseInt(subexpression[1]));
        }

      case "int":
        if (subexpression.length == 3) {
          return paddingZero(seed.value, Integer.parseInt(subexpression[2]));
        } else {
          return String.valueOf(seed);
        }

      case "dailyIncrement":
        // 每日自增型数字
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (updateDate.format(formatter).equals(LocalDateTime.now().format(formatter))) {
          if (subexpression.length == 3) {
            return paddingZero(seed.value, Integer.parseInt(subexpression[2]));
          } else {
            return String.valueOf(seed);
          }
        } else {
          if (subexpression.length == 3) {
            seed.value = 1;
            return paddingZero(seed.value, Integer.parseInt(subexpression[2]));
          } else {
            return "1";
          }
        }

      case "code":
        // 整数类型自增型数字
        if (subexpression.length == 2) {
          return paddingZero(seed.value, Integer.parseInt(subexpression[1]));
        } else {
          return paddingZero(seed.value, 3);
        }

      default:
        return "UnkownExpression";
    }
  }

  /**
   * 数字补零
   *
   * @param seed   因子
   * @param length 长度
   * @return
   */
  private static String paddingZero(int seed, int length) {
    String zero = "";

    for (int i = 0; i < length; i++) {
      zero += "0";
    }

    DecimalFormat decimalFormat = new DecimalFormat(zero);

    return StringUtil.isNullOrEmpty(zero) ? String.valueOf(seed) : decimalFormat.format(seed);
  }
}
