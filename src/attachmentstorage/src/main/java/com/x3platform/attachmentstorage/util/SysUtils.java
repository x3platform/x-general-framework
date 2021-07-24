package com.x3platform.attachmentstorage.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author lvluo
 *
 */
public class SysUtils {
	private static final Logger log = LoggerFactory.getLogger(SysUtils.class);

	private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

	private static String mobileNumRexp="^[1][3,4,5,7,8][0-9]{9}$";
	private static String webAppRootPath;

	public static String getWebAppRootPath() {
		return webAppRootPath;
	}

	public static void setWebAppRootPath(String webAppRootPath) {
		SysUtils.webAppRootPath = webAppRootPath;
	}

	public static String getTempPath() {
		return webAppRootPath+"temp/";
	}

	public static String nullString(String value) {
		return value==null?"":value.trim();
	}

	public static String nullString(String value,String nullDefault){
		return value==null || value.trim()==""?nullDefault:value.trim();
	}

	public static String nullString(Short value) {
		return value==null?"":value.toString();
	}

	public static String nullString(Integer value) {
		return value==null?"":value.toString();
	}

	public static String nullString(Double value) {
		if (value==null) {
      return "";
    } else {
			DecimalFormat df = new DecimalFormat("#,###.00");
			return df.format(value);
		}
	}


	public static String nullString(BigDecimal value) {
		if (value==null) {
      return "";
    } else {
			DecimalFormat df = new DecimalFormat("#,###.00");
			return df.format(value);
		}
	}


	public static String nullString(Long value) {
		return value==null?"":value.toString();
	}

	public static Double ifNull(Double value1, Double value2) {
		return value1==null?value2:value1;
	}

	public static Float ifNull(Float value1, Float value2) {
		return value1==null?value2:value1;
	}

	public static Byte ifNull(Byte value1, Byte value2) {
		return value1==null?value2:value1;
	}

	public static Long ifNull(Long value1, Long value2) {
		return value1==null?value2:value1;
	}

	public static Integer ifNull(Integer value1, Integer value2) {
		return value1==null?value2:value1;
	}

	public static String ifNull(String value1, String value2) {
		return value1==null?value2:value1;
	}

	public static Boolean ifNull(Boolean value1, Boolean value2) {
		return value1==null?value2:value1;
	}

	public static Short ifNull(Short value1, Short value2) {
		return value1==null?value2:value1;
	}

	public static Boolean isNull(String value){
		return value==null || value.trim().length()==0;
	}

	public static Boolean isNotNull(String value){
		return !isNull(value);
	}

	public static String appendStopChar(String value){
		String v=SysUtils.nullString(value);
		if (SysUtils.isNotNull(v) && !v.endsWith("。") && !v.endsWith(".") && !v.endsWith("！") && !v.endsWith("!")
				&& !v.endsWith("？") && !v.endsWith("?") && !v.endsWith("；") && !v.endsWith(";")) {
      v=v+"。";
    }
		return v;
	}

	public static Double parseDoubleExceptionAsNull(String value){
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			log.error(e.getMessage(),e);
			return null;
		}
	}

	public static Double parseDoubleExceptionAsZero(String value){
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			log.error(e.getMessage(),e);
			return 0.0;
		}
	}

	public static Integer parseIntegerExceptionAsNull(String value){
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			log.error(e.getMessage(),e);
			return null;
		}
	}

	public static Integer parseIntegerExceptionAsZero(String value){
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			log.error(e.getMessage(),e);
			return 0;
		}
	}

	public static String trimNotNull(String value){
		if (value!=null) {
      return value.trim();
    } else {
      return value;
    }
	}

	/**
	 * 获取当前日期
	 *
	 * @return 字符串格式的的当前日期
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	/**
	 * 获取当前时间
	 *
	 * @return 字符串格式的的当前时间
	 */
	public static String getCurrentTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * 获取当前时间
	 *
	 * @return 字符串格式的的当前时间
	 */
	public static String getCurrentFmtTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}


	/**
	 * 计算时间差（单位毫秒）
	 *
	 * @param before
	 * @param after
	 * @return 正常：计算后的非-1的值，异常：-1
	 */
	public static long getTimeDiff(String before, String after) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beforeTime = null;
		Date afterTime = null;
		try {
			beforeTime = sdf.parse(before);
			afterTime = sdf.parse(after);
		} catch (ParseException e) {
			return -1;
		}

		return afterTime.getTime() - beforeTime.getTime();
	}

	/**
	 * 计算时间差（单位毫秒）
	 *
	 * @param before
	 * @param after
	 * @return 正常：计算后的非-1的值，异常：-1
	 */
	public static long getTimeDiff(Date before, Date after) {
		long diff = 0;
		try {
			diff = after.getTime() - before.getTime();
		} catch (Exception e) {
			diff = -1;
		}
		return diff;
	}

	public static String rightTrimZero(String str, int trimBitsPerTime) {
		int bits = Math.abs(trimBitsPerTime);
		String s = nullString(str).trim();
		if (s.length() > 0 && bits > 0 && s.length() > bits) {
			String zs = "";
			if (s.length() % bits > 0) {
        s += "0";
      }
			for (int i = 0; i < bits; i++) {
        zs += "0";
      }
			while (s.endsWith(zs)) {
        s = s.substring(0, s.length() - bits);
      }
		}
		return s;
	}

	public static boolean isChineseChar(String str) {
		boolean result = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			result = true;
		}
		return result;
	}

	public static String cutOffString(String value,Integer length){
		String v=nullString(value);
		if (v.length()>length) {
      v=v.substring(0,length-3)+"...";
    }
		return v;
	}

	public static String removeChineseChar(String str){
		return str.replaceAll("[\\u4e00-\\u9fa5]", "");
	}



	/**
	 * 将字符串转换成ASCII码
	 *
	 * @param cnStr
	 * @return String
	 */
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		// 将字符串转换成字节序列
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// 将每个字符转换成ASCII码
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff) + " ");
		}
		return strBuf.toString();
	}


	/**
	 * 生成固定长度的随机数
	 *
	 * @param strLength
	 * @return
	 */
	public static String getFixLenthString(int strLength) {

		Random rm = new Random();
		double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
		String fixLenthString = String.valueOf(pross);
		return fixLenthString.substring(1, strLength + 1);
	}

	/**
	 * 手机号验证
	 *
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		return matchExp(mobileNumRexp,str);
	}

	/**
	 * 电话号码验证
	 *
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		String exp="";
		if (str.length() > 9) {
      str="^[0][1-9]{2,3}-[0-9]{5,10}$";// 验证带区号的
    } else {
      str="^[1-9]{1}[0-9]{5,8}$"; // 验证没有区号的
    }
		return matchExp(exp,str);
	}

	/**
	 * 验证身份证
	 *
	 * @param str the str
	 * @return the boolean
	 */
	public static boolean isID(String str) {
		if (SysUtils.isNull(str) || str.length()!=15 || str.length()!=18) {
      return false;
    }
		String exp="";
		if (str.length()==15) {
      exp="^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    } else {
      exp="^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
    }
		return matchExp(exp,str);
	}

	/**
	 * 验证护照
	 *
	 * @param str the str
	 * @return the boolean
	 */
	public static boolean isPassport(String str) {
		return matchExp("^[a-zA-Z]{5,17}$",str) || matchExp("^[a-zA-Z0-9]{5,17}$",str);
	}

	/**
	 * 验证军官证
	 *
	 * @param str the str
	 * @return the boolean
	 */
	public static boolean isOfficerCertificate(String str){
		return matchExp("^[\\u4E00-\\u9FA5]字第[0-9a-zA-Z]+号$",str);
	}

	/**
	 * 验证统一社会信用代码
	 *
	 * @param str the str
	 * @return the boolean
	 */
	public static boolean isUniformCreditCode(String str,String regionCode){
		String v=SysUtils.nullString(str);
		String r=SysUtils.nullString(regionCode);
		boolean b=matchExp("[^_IOZSVa-z\\W]{2}\\d{6}[^_IOZSVa-z\\W]{10}",v);
		return b && v.substring(2,4).equalsIgnoreCase(regionCode.substring(0,2));
	}

	/**
	 * 验证组织机构代码
	 *
	 * @param str the str
	 * @return the boolean
	 */
	public static boolean isOrgCode(String str){
		return matchExp("^[a-zA-Z\\d]{8}\\-[a-zA-Z\\d]$",str);
	}

	private static boolean matchExp(String expStr,String value){
		Pattern p = Pattern.compile(expStr);
		Matcher m = p.matcher(value);
		return m.matches();
	}

	public static boolean compareNumberString(String firstString, String secondString) {
		char[] chars1 = firstString.toCharArray();
		char[] chars2 = secondString.toCharArray();
		int length1 = chars1.length;
		int length2 = chars2.length;
		int maxLength = length1 > length2 ? length1 : length2;
		for (int i = 0; i < maxLength; i++) {
			int value1 = -1;
			int value2 = -1;
			if (i < length1) {
				value1 = chars1[i];
			}

			if (i < length2) {
				value2 = chars2[i];
			}

			if (value1 < value2) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public static String encode(String str, String charset) {
		String content = null;
		try {
			content = new String(str.getBytes("ISO-8859-1"), charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return content;
	}


	public static int hashCodeOfStringArray(String[] stringArray) {
		if (stringArray == null) {
			return 0;
		}
		int hashCode = 17;
		for (int i = 0; i < stringArray.length; i++) {
			String value = stringArray[i];
			hashCode = hashCode * 31 + (value == null ? 0 : value.hashCode());
		}
		return hashCode;
	}

	public static boolean nullOrEmpty(String input) {
		return input == null || "".equals(input.trim());
	}

	public static String convertEncode(String str) {
		if (nullOrEmpty(str)) {
			return null;
		}
		try {
			return new String(str.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public String stringReaderToString(StringReader reader) {
		StringBuilder builder = new StringBuilder();
		char[] buffer = new char[128];
		int length = -1;
		try {
			while ((length = reader.read(buffer)) != -1) {
				if (buffer.length != length) {
					System.arraycopy(buffer, 0, buffer, 0, length);
				}
				builder.append(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
		return builder.toString();
	}

	public static String replaceFirst(String content, String target, String replacement) {
		int index = content.indexOf(target);
		if (index < 0) {
			return content;
		}
		StringBuilder builder = new StringBuilder();
		String front = content.substring(0, index);
		String last = content.substring(index + target.length());
		return builder.append(front).append(replacement).append(last).toString();
	}

	public static String UUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String arrayToString(String[] values,String separator){
		StringBuffer s=new StringBuffer("");
		for (String value:values){
			s.append(value+separator);
		}
		if (s.length()>0) {
      s.delete(s.length()-separator.length(), s.length());
    }
		return s.toString();
	}

	public static Method getGetMethod(Class objectClass, String fieldName){
		Method[] methods=objectClass.getMethods();
		String methodName="get"+fieldName;
		for (Method m:methods){
			if (m.getName().equalsIgnoreCase(methodName)){
				return m;
			}
		}
		return null;
	}

	public static String fillLeftChar(String value,char fillChar,int len){
		String v=nullString(value);
		if (v.length()>=len) {
      return value;
    } else{
			char[] chars=new char[len-v.length()];
			Arrays.fill(chars, fillChar);
			return new String(chars)+v;
		}
	}

	public static String fillRightChar(String value,char fillChar,int len){
		String v=nullString(value);
		if (v.length()>=len) {
      return value;
    } else{
			char[] chars=new char[len-v.length()];
			Arrays.fill(chars, fillChar);
			return v+new String(chars);
		}
	}

	public static String formatDouble(Double value){
		return String.format("%.2f",value);
	}

	public static String formatDecimal(BigDecimal value,int scale){
		return value.setScale(scale,BigDecimal.ROUND_HALF_UP).toString();
	}

	public static String formatDecimal(BigDecimal value){
		return formatDecimal(value,2);
	}



	public static void deleteFile(String fileName){
		if (!isNull(fileName)){
			File file=new File(fileName);
			if (file.isFile() && file.exists()) {
        file.delete();
      }
		}
	}

	public static BigDecimal castNumberAsBigDecimal(Object result) {
		BigDecimal value=BigDecimal.ZERO;
		if (result instanceof Integer) {
      value=BigDecimal.valueOf(((Integer)result));
    } else if (result instanceof Float) {
      value=BigDecimal.valueOf(((Float)result));
    } else if (result instanceof Long) {
      value=BigDecimal.valueOf(((Long)result));
    } else if (result instanceof BigInteger) {
      value=BigDecimal.valueOf(((BigInteger)result).longValue());
    } else if (result instanceof Short) {
      value=BigDecimal.valueOf(((Short)result));
    } else if (result instanceof Byte) {
      value=BigDecimal.valueOf(((Byte)result));
    } else {
      value=BigDecimal.valueOf((Double)result);
    }
		return value;
	}

	public static String digitUppercase(BigDecimal number){
		return digitUppercase((number==null?0:number.doubleValue()));
	}

	/**
	 * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零
	 * 要用到正则表达式
	 */
	public static String digitUppercase(double number){
		String fraction[] = {"角", "分"};
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = {{"元", "万", "亿"},
				{"", "拾", "佰", "仟"}};

		String head = number < 0? "负": "";
		number = Math.abs(number);

		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			double f1= new BigDecimal(number).setScale(2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(10 * Math.pow(10, i))).doubleValue();
			s += (digit[(int) (Math.floor(f1) % 10)] + fraction[i]).replaceAll("(零.)+", "");
		}
		if(s.length()<1){
			s = "整";
		}
		int integerPart = (int)Math.floor(number);

		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p ="";
			for (int j = 0; j < unit[1].length && number > 0; j++) {
				p = digit[integerPart%10]+unit[1][j] + p;
				integerPart = integerPart/10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
		}
		return head + s.replaceAll("(零.)*零元", "元").replaceFirst("^(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
	}

	public static Integer intIdsSum(String intIds,String splitChar){
		String[] ids=intIds.split(splitChar);
		Integer result=0;
		for (String id : ids) {
			try {
				result=result+Integer.parseInt(id);
			} catch (NumberFormatException e) {
				log.error(e.getMessage(),e);
				return null;
			}
		}
		return result;
	}

	public static String bytesToHexString(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static byte[] hexStringToBytes(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static String fileToBase64String(String file)
	{
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(file);
			data = new byte[in.available()];
			in.read(data);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
			return null;
		} finally{
			if (in!=null) {
        try {
          in.close();
        } catch (IOException e) {
          log.error(e.getMessage(),e);
          return null;
        }
      }
		}
		if (data!=null){
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(data);
		}
		else {
      return null;
    }
	}

	public static byte[] base64StringToBytes(String base64Value){
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] data = new byte[0];
		try {
			data = decoder.decodeBuffer(base64Value);
			for(int i=0;i<data.length;++i)
			{
				if(data[i]<0)
				{//调整异常数据
					data[i]+=256;
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(),e);
			return null;
		}
		return data;
	}

	public static Boolean base64StringToFile(String base64Value,String destinationDir,String fileName){
		byte[] data=base64StringToBytes(base64Value);
		if (data==null) {
      return false;
    }
		if (SysUtils.isNull(destinationDir) || SysUtils.isNull(fileName)) {
      return false;
    }
		String fName=destinationDir.toString();
		if (!fName.endsWith(File.separator)) {
      fName=fName+File.separator;
    }
		fName=fName+fileName;
		OutputStream out = null;
		try {
			out = new FileOutputStream(fName);
			out.write(data);
			out.flush();
			return true;
		} catch (IOException e) {
			log.error(e.getMessage(),e);
			return false;
		} finally {
			if (out!=null) {
        try {
          out.close();
        } catch (IOException e) {
          log.error(e.getMessage(),e);
          return false;
        }
      }
		}
	}

	public static String deleteIllegalCharInFileName(String fileName,String replaceChar){
		Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|]");
		Matcher matcher = pattern.matcher(fileName);
		return matcher.replaceAll(replaceChar);
	}

	public static Integer getIntegerFromMap(Map<String,Object> map, String key, Integer nullDefaultValue){
		if (map==null || SysUtils.isNull(key)) {
      return nullDefaultValue;
    }
		Object value=map.get(key);
		if (value==null) {
      return nullDefaultValue;
    }
		try{
			if (value instanceof Integer) {
        return (Integer)value;
      } else if (value instanceof Float) {
        return ((Float)value).intValue();
      } else if (value instanceof Double) {
        return ((Double)value).intValue();
      } else if (value instanceof Long) {
        return ((Long)value).intValue();
      } else {
        return Integer.parseInt(String.valueOf(value));
      }
		}catch (Exception e){
			return nullDefaultValue;
		}
	}

	public static Double getDoubleFromMap(Map<String,Object> map, String key, Double nullDefaultValue){
		if (map==null || SysUtils.isNull(key)) {
      return nullDefaultValue;
    }
		Object value=map.get(key);
		if (value==null) {
      return nullDefaultValue;
    }
		try{
			if (value instanceof Double) {
        return (Double)value;
      } else if (value instanceof Float) {
        return ((Float)value).doubleValue();
      } else if (value instanceof Integer) {
        return ((Integer)value).doubleValue();
      } else if (value instanceof Long) {
        return ((Long)value).doubleValue();
      } else {
        return Double.parseDouble(String.valueOf(value));
      }
		}catch (Exception e){
			return nullDefaultValue;
		}
	}

	public static Float getFloatFromMap(Map<String,Object> map, String key, Float nullDefaultValue){
		if (map==null || SysUtils.isNull(key)) {
      return nullDefaultValue;
    }
		Object value=map.get(key);
		if (value==null) {
      return nullDefaultValue;
    }
		try{
			if (value instanceof Float) {
        return (Float)value;
      } else if (value instanceof Double) {
        return ((Double)value).floatValue();
      } else if (value instanceof Integer) {
        return ((Integer)value).floatValue();
      } else if (value instanceof Long) {
        return ((Long)value).floatValue();
      } else {
        return Float.parseFloat(String.valueOf(value));
      }
		}catch (Exception e){
			return nullDefaultValue;
		}
	}

	public static Long getLongFromMap(Map<String,Object> map, String key, Long nullDefaultValue){
		if (map==null || SysUtils.isNull(key)) {
      return nullDefaultValue;
    }
		Object value=map.get(key);
		if (value==null) {
      return nullDefaultValue;
    }
		try{
			if (value instanceof Long) {
        return (Long)value;
      } else if (value instanceof Integer) {
        return ((Integer)value).longValue();
      } else if (value instanceof Double) {
        return ((Double)value).longValue();
      } else if (value instanceof Float) {
        return ((Float)value).longValue();
      } else {
        return Long.parseLong(String.valueOf(value));
      }
		}catch (Exception e){
			return nullDefaultValue;
		}
	}

	public static BigDecimal getBigDecimalFromMap(Map<String,Object> map, String key, BigDecimal nullDefaultValue){
		if (map==null || SysUtils.isNull(key)) {
      return nullDefaultValue;
    }
		Object value=map.get(key);
		if (value==null) {
      return nullDefaultValue;
    }
		try{
			if (value instanceof  BigDecimal) {
        return (BigDecimal) value;
      } else if (value instanceof Integer) {
        return new BigDecimal(((Integer) value).toString());
      } else if (value instanceof Long) {
        return new BigDecimal(((Long) value).toString());
      } else {
        return new BigDecimal(String.valueOf(value));
      }
		}catch (Exception e){
			return nullDefaultValue;
		}
	}

	public static Byte getByteFromMap(Map<String,Object> map, String key, Byte nullDefaultValue){
		if (map==null || SysUtils.isNull(key)) {
      return nullDefaultValue;
    }
		Object value=map.get(key);
		if (value==null) {
      return nullDefaultValue;
    }
		try{
			if (value instanceof Byte) {
        return (Byte)value;
      } else if (value instanceof Integer) {
        return ((Integer)value).byteValue();
      } else if (value instanceof Long) {
        return ((Long)value).byteValue();
      } else if (value instanceof Double) {
        return ((Double)value).byteValue();
      } else if (value instanceof Float) {
        return ((Float)value).byteValue();
      } else {
        return Byte.parseByte(String.valueOf(value));
      }
		}catch (Exception e){
			return nullDefaultValue;
		}
	}

	public static Boolean getBoolFromMap(Map<String,Object> map, String key, Boolean nullDefaultValue){
		if (map==null || SysUtils.isNull(key)) {
      return nullDefaultValue;
    }
		Object value=map.get(key);
		if (value==null) {
      return nullDefaultValue;
    }
		try{
			if (value instanceof  Boolean) {
        return (Boolean) value;
      } else {
        return Boolean.parseBoolean(String.valueOf(value));
      }
		}catch (Exception e){
			return nullDefaultValue;
		}
	}

	public static String getStringFromMap(Map<String,Object> map, String key, String nullDefaultValue){
		if (map==null || SysUtils.isNull(key)) {
      return nullDefaultValue;
    }
		Object value=map.get(key);
		if (value==null) {
      return nullDefaultValue;
    } else {
      return SysUtils.nullString(String.valueOf(value));
    }
	}


	public static Date getDateFromMap(Map<String,Object> map, String key,String dateFromat, Date nullDefaultValue){
		if (map==null || SysUtils.isNull(key)) {
      return nullDefaultValue;
    }
		Object value=map.get(key);
		if (value==null) {
      return nullDefaultValue;
    } else {
			try{
				if (value instanceof Date) {
          return (Date)value;
        } else{
					SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
					return sdf.parse(String.valueOf(value));
				}
			}catch (Exception e){
				return nullDefaultValue;
			}
		}
	}

	public static Date getDateFromMap(Map<String,Object> map, String key, Date nullDefaultValue){
		return getDateFromMap(map,key,"yyyy-MM-dd",nullDefaultValue);
	}

	public static String getUuid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
