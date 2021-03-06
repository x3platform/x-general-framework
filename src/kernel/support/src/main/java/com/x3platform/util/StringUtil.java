package com.x3platform.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串处理辅助类
 */
public class StringUtil {

  /**
   * 将文本信息转为 Unicode 编码
   *
   * @param text 文本信息
   * @return
   */
  public static String unicodeEncode(String text) {
    StringBuilder outString = new StringBuilder();

    char[] chars = text.toCharArray();

    for (char ch : chars) {
      outString.append("\\u" + String.format("%04x", new Integer(ch)));
    }

    return outString.toString();
  }

  static Pattern unicodeRegex = Pattern.compile("([\\w]+)|(\\\\u([\\w]{4}))");

  /**
   * 将 Unicode 编码转为文本信息
   *
   * @param text Unicode 编码文本信息
   * @return
   */
  public static String unicodeDecode(String text) {
    StringBuilder outString = new StringBuilder();

    Matcher matcher = unicodeRegex.matcher(text);

    // char ch;

    // Charset charset = Charset.forName("UTF-16");

    while (matcher.find()) {

      // char[] codes = new char[2];

      String code = matcher.group(2).substring(2);

      // codes[0] = (char)Integer.parseInt(word.substring(2), 16);
      // codes[1] = (char)Integer.parseInt(word.substring(0, 2), 16);

      // CharBuffer charBuffer = charset.decode(ByteBuffer.wrap(codes));
      // CharBuffer charBuffer = CharBuffer.wrap(codes);

      outString.append((char) Integer.parseInt(code, 16));
    }

    /*
     if (matches != null && matches.size() > 0)
     {
      for (Match match : matches)
      {
        byte[] codes = new byte[2];

        String word = match.Value.substring(2);

        codes[0] = (byte)Integer.parseInt(word.substring(2), 16);
        codes[1] = (byte)Integer.parseInt(word.substring(0, 2), 16);

        outString.append(Encoding.Unicode.GetString(codes));
      }

    outString.toString();
  }
  */

    return outString.toString();
  }


  public static boolean isNullOrEmpty(String text) {
    return StringUtils.isEmpty(text) || StringUtils.isBlank(text);
  }

  public static String substring(String text, int start, int length) {
    return StringUtils.substring(text, start, start + length);
  }

  /**
   * 对输入的字符串格式化，得到首字母为大写的字符床. e.g. user -> User.
   *
   * @param text 需要处理的字符串
   * @return
   */
  public static String toFirstUpper(String text) {
    if (isNullOrEmpty(text)) {
      return "";
    }

    return text.substring(0, 1).toUpperCase() + text.substring(1);
  }

  /**
   * 对输入的字符格式化，得到首字母为小写的字符串. 例如 User -> user.
   *
   * @param text 需要处理的字符串
   * @return
   */
  public static String toFirstLower(String text) {
    if (isNullOrEmpty(text)) {
      return "";
    }

    return text.substring(0, 1).toLowerCase() + text.substring(1);
  }

  /**
   * 转换JSON 格式字符串中的特殊字符.
   *
   * @param text 文本信息
   * @return 文本信息
   */
  public static String toSafeJson(String text) {
    text = nullTo(text);

    StringBuilder outString = new StringBuilder(text.length());
    for (int i = 0; i < text.length(); i++) {
      char ch = text.charAt(i);

      if (Character.isISOControl(ch)) {
        int ich = (int) ch;
        outString.append("\\u" + String.format("%04x", ich));
        continue;
      } else if (ch == '\"' || ch == '\'' || ch == '\\') {
        outString.append('\\');
      }

      outString.append(ch);
    }

    return outString.toString();
  }

  /**
   * 处理SQL格式中的非法字符
   *
   * @param text 文本信息
   * @return
   */
  public static String toSafeSQL(String text) {
    String[] removeTags = {};
    return toSafeSQL(text, removeTags);
  }

  /**
   * 处理SQL格式中的非法字符
   *
   * @param text         文本信息
   * @param removeQuotes 移除引号
   * @return
   */
  public static String toSafeSQL(String text, boolean removeQuotes) {
    String[] removeTags = removeQuotes ? new String[]{"'"} : new String[]{};

    return toSafeSQL(text, removeTags);
  }

  /**
   * 处理SQL格式中的非法字符
   *
   * @param text       文本信息
   * @param removeTags
   * @return
   */
  public static String toSafeSQL(String text, String[] removeTags) {
    //-------------------------------------------------------
    // 1.替换一个单引号为两个单引号;
    // 2.替换两个井号为一个单引号;
    // 3.移除自定义标签;
    //-------------------------------------------------------

    if (StringUtil.isNullOrEmpty(text)) {
      return "";
    }

    // 替换一个单引号为两个单引号;
    text = text.replace(";", "").replace("'", "''").replace("--", "''--''");
    /*
    // 替换两个井号为一个单引号;
    // 字符串两边必须留白
    Regex regex = new Regex("##(.*?)##");

    MatchCollection matches = regex.Matches(text);

    String matchValue = "";

    for (int i = 0; i < matches.size(); i++) {
      matchValue = matches.getItem(i).Value.substring(2, 2 + matches.getItem(i).Value.length() - 4).trim();

      // 内容中禁止以 OR 或者 -- 开始和结束
      if (!Regex.IsMatch(matchValue.toUpperCase(), "^(OR|--)\\s+|\\s+(OR|--])$")) {
        text = text.replace(matches.getItem(i).Value, String.format("'%1$s'", matchValue));
      }
    }

    // 移除自定义标签
    for (String removeTag : removeTags) {
      if (matchValue.indexOf(removeTag) != 0) {
        text = text.replace(removeTag, "");
      }
    }
    */
    return text;
  }

  //-------------------------------------------------------
  // 随机字符串处理
  //-------------------------------------------------------

  /**
   * 取得一个随机的字符串
   *
   * @param length 字符串的长度
   * @return 长为length的随机的字符串
   */
  public static String toRandom(int length) {
    return toRandom("abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ", length);
  }

  /**
   * 取得一个随机的字符串
   *
   * @param chars  指定字符
   * @param length 字符串的长度
   * @return 长为length的随机的字符串
   */
  public static String toRandom(String chars, int length) {
    if (length <= 0) {
      return "";
    }

    Random random = new Random(UUID.randomUUID().hashCode());

    char[] buffer = new char[length];

    for (int i = 0; i < length; i++) {
      int index = (int) (random.nextDouble() * chars.length());
      buffer[i] = chars.charAt(index);
    }

    return new String(buffer);
  }

  //-------------------------------------------------------
  // Base64 字符串处理
  //-------------------------------------------------------

  /**
   * 加密为 ASCII 编码方式的 Base64 字符串，如果是其他编码方式的请设置 codepage
   *
   * @param text 文本信息
   * @return
   */
  public static String toBase64(String text) {
    // Encode data on your side using BASE64
    // byte[] bytesEncoded = Base64.encodeBase64(str.getBytes());
    // System.out.println("encoded value is " + new String(bytesEncoded));

    // Decode data on other side, by processing encoded data
    // byte[] valueDecoded = Base64.decodeBase64(bytesEncoded);
    // System.out.println("Decoded value is " + new String(valueDecoded));

    char[] chars = text.toCharArray();

    byte[] bytes = new byte[chars.length];

    for (int i = 0; i < chars.length; i++) {
      if (chars[i] >= 0 && chars[i] <= 255) {
        bytes[i] = (byte) chars[i];
      }
    }

    // return Convert.ToBase64String(bytes);
    byte[] bytesEncoded = Base64.encodeBase64(bytes);
    return new String(bytesEncoded);
  }

  /**
   * 加密为 Base64 字符串
   *
   * @param text        文本信息
   * @param charsetName 字符集信息
   * @return Base64 字符串
   */
  public static String toBase64(String text, String charsetName) {
    String encode = "";

    ByteBuffer buffer = Charset.forName(charsetName).encode(text);

    try {
      byte[] bytesEncoded = Base64.encodeBase64(buffer.array());
      encode = new String(bytesEncoded);
    } catch (java.lang.Exception ex) {
      encode = text;
    }

    return encode;
  }

  /**
   * 解密为 ASCII 编码方式的 Base64 字符串，如果是其他编码方式的请设置 charsetName
   *
   * @param base64Text
   * @return 字符串
   */
  public static String fromBase64(String base64Text) {
    // 补末尾的等号
    int count = 4 - base64Text.length() % 4;

    for (int i = 0; i < count && count < 4; i++) {
      base64Text += "=";
    }

    byte[] bytes = Base64.decodeBase64(base64Text);

    StringBuilder outString = new StringBuilder();

    for (byte b : bytes) {
      if (b >= 0 && b <= 255) {
        outString.append((char) b);
      }
    }

    return outString.toString();
  }

  /**
   * 解密为 ASCII 编码方式的 Base64 字符串，如果是其他编码方式的请设置 charsetName
   *
   * @param base64Text
   * @return Base64 格式字符串
   */
  public static String fromBase64(String base64Text, String charsetName) {
    // 补末尾的等号
    int count = 4 - base64Text.length() % 4;

    for (int i = 0; i < count && count < 4; i++) {
      base64Text += "=";
    }

    String decode = "";

    byte[] bytes = Base64.decodeBase64(base64Text);

    try {
      decode = Charset.forName(charsetName).decode(ByteBuffer.wrap(bytes)).toString();
    } catch (java.lang.Exception e) {
      decode = base64Text;
    }

    return decode;
  }

  /**
   * 格式化日期
   *
   * @param date
   * @return
   */
  public static String toDate(String date) {
    try {
      return toDate(java.time.LocalDateTime.parse(date));
    } catch (java.lang.Exception e) {
      //当日期格式无法识别,转换失败,返回原始数据.
      return date;
    }
  }

  /**
   * 格式化日期
   *
   * @param date 时间对象
   * @return 返回 yyyy-MM-dd 格式的字符串
   */
  public static String toDate(java.time.LocalDateTime date) {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return format.format(date);
  }

  /**
   * 格式化时间
   *
   * @param date
   * @return
   */
  public static String toTime(java.time.LocalDateTime date) {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return format.format(date);
  }

  /**
   * 智能日期格式
   *
   * @param date
   * @return
   */
  public static String toSmartTime(java.time.LocalDateTime date) {
    //
    // 1.今年以前的时间显示 "yyyy年"
    //
    // 2.今年的时间显示为 "MM月dd日"
    //
    // 3.当天的时间显示为 "HH:mm:ss"
    //
    // 4.其他时间 显示为 "yyyy-MM-dd HH:mm:ss"
    //

    DateFormat format = null;

    if (date.getYear() < java.time.LocalDateTime.now().getYear()) {
      format = new SimpleDateFormat("yyyy年");

      return format.format(date);
    } else {
      if (java.time.LocalDateTime.now().getDayOfYear() == date.getDayOfYear()) {
        format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
        // return date.toString("HH:mm:ss");
      } else if (java.time.LocalDateTime.now().getYear() == date.getYear()) {
        format = new SimpleDateFormat("MM月dd日");
        return format.format(date);
        // return date.toString("MM月dd日");
      } else {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
        // return date.toString("yyyy-MM-dd HH:mm:ss");
      }
    }
  }

  /**
   * 格式化日期
   *
   * @param date
   * @return
   */
  public static String toDateFormat(java.time.LocalDateTime date, String pattern) {
    return toDateFormat(DateUtil.toDate(date), pattern);
  }

  public static String toDateFormat(Date date, String pattern) {
    DateFormat format = new SimpleDateFormat(pattern);
    return format.format(date);
  }

  /**
   * 生成一个Uuid格式字符串
   *
   * @return
   */
  public static String toUuid() {
    return toUuid(UUID.randomUUID());
  }

  /**
   * 生成一个Uuid格式字符串
   *
   * @param uuid
   * @return
   */
  public static String toUuid(UUID uuid) {
    //
    // 说明符      返回值的格式
    //
    // N             32 位：
    //               xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    //
    // D             由连字符分隔的 32 位数字：
    //               xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
    //
    // B             括在大括号中、由连字符分隔的 32 位数字：
    //               {xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx}
    //
    // P             括在圆括号中、由连字符分隔的 32 位数字：
    //               (xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx)
    //

    return UUIDUtil.toString(uuid, "N");
  }

  private static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
    "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
    "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
    "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
    "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
    "W", "X", "Y", "Z"};

  public static String to8DigitUuid() {
    // 通过随机生成32位UUID，由于 UUID 都为十六进制，所以将 UUID 分成 8 组，
    // 每4个为一组，然后通过模 62 操作，结果作为索引取出字符.
    String uuid = toUuid();

    StringBuffer shortBuffer = new StringBuffer();

    for (int i = 0; i < 8; i++) {
      String code = uuid.substring(i * 4, i * 4 + 4);
      int x = Integer.parseInt(code, 16);
      // 0x3E = 62
      shortBuffer.append(chars[x % 0x3E]);
    }

    return shortBuffer.toString();
  }

  /**
   * 格式化字符串
   *
   * @param stringFormat
   * @param args
   * @return
   */
  public static String format(String stringFormat, String... args) {
    for (int i = 0; i < args.length; i++) {
      stringFormat = stringFormat.replaceFirst("\\{\\}", args[i]);
    }

    return stringFormat;
  }

  public static String format(String stringFormat, Object... args) {
    for (int i = 0; i < args.length; i++) {
      stringFormat = stringFormat.replaceFirst("\\{\\}", String.valueOf(args[i]));
    }

    return stringFormat;
  }

  //-------------------------------------------------------
  // 空字符串处理
  //-------------------------------------------------------

  /**
   * 默认的空值转换, 把 null 转换为 "" .
   *
   * @param text 文本信息
   * @return 处理后的文本信息
   */
  public static String nullTo(String text) {
    return nullTo(text, "");
  }

  /**
   * 空值转换，把 null 转换为 replaceText. e.g. NullTo(null,"ok") 返回 "ok", NullTo("1","ok")返回"1"
   *
   * @param text        文本信息
   * @param replaceText 文本为空时替换的文本信息
   * @return 处理后的文本信息
   */
  public static String nullTo(String text, String replaceText) {
    return (text == null) ? replaceText : text;
  }

  /**
   * 空字符串转换，把 null 或 "" 转换为 replaceText. e.g. NullOrEmptyTo(null,"ok")返回"ok", NullOrEmptyTo("","ok")返回"ok".
   *
   * @param text        文本信息
   * @param replaceText 文本为空时替换的文本信息
   * @return 处理后的文本信息
   */
  public static String nullOrEmptyTo(String text, String replaceText) {
    return (StringUtil.isNullOrEmpty(text)) ? replaceText : text;
  }

  /**
   * 清除文本最后的标记
   *
   * @param text     需处理的字符
   * @param trimText 标签
   * @return 字符串
   */
  public static StringBuilder trimEnd(StringBuilder text, String trimText) {
    if (text.length() == 0) {
      return text;
    }

    return (substring(text.toString(), text.length() - trimText.length(), trimText.length()).equals(trimText)) ? text.delete(text.length() - trimText.length(), text.length() - trimText.length() + trimText.length()) : text;
  }

  /**
   * 清除文本最后的标记
   *
   * @param text     需处理的字符
   * @param trimText 标签
   * @return 字符串
   */
  public static String trimEnd(String text, String trimText) {
    if (text.length() == 0) {
      return text;
    }

    return (substring(text, text.length() - trimText.length(), trimText.length()).equals(trimText)) ? text.substring(0, text.length() - trimText.length()) : text;
  }

  /**
   * @param inputN  输入字符串
   * @param pattern 需要格式化格式
   * @return 将数字转换成字符串需要个格式
   */
  public static String numberToStr(int inputN, String pattern) {
    String result = "";
    if (StringUtil.isNullOrEmpty(pattern)) {
      result = String.valueOf(inputN);
    } else {
      DecimalFormat df = new DecimalFormat(pattern);
      result = df.format(inputN);
    }
    return result;
  }

  public static void main(String[] args) {
    System.out.println(numberToStr(13, "0000"));
  }

}
