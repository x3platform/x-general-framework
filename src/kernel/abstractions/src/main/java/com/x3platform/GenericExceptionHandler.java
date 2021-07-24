package com.x3platform;

import com.x3platform.util.StringUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * 通常的异常信息
 */
@RestControllerAdvice(annotations = RestController.class)
public class GenericExceptionHandler {

  /**
   * 处理 Controller 层面的 GenericException
   */
  @ExceptionHandler(GenericException.class)
  @ResponseBody
  public String handleAllExceptions(GenericException ex) {
    InternalLogger.getLogger().error("GenericException", ex);
    ex.printStackTrace();
    return ex.toString();
  }

  /**
   * 处理所有的 Controller 层面的异常
   */
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public Map handleAllExceptions(Exception ex, WebRequest request) {
    // 处理所有未处理的异常信息
    String exceptionName = ex.getClass().getName();
    InternalLogger.getLogger().error("UnhandledException:" + exceptionName, ex);
    ex.printStackTrace();
    Map<String, Object> map = new HashMap<>();
    map.put("code", "1");
    if (ex.getLocalizedMessage() == null) {
      map.put("message", StringUtil.format("Exception:{}, Please contact the administrator.", exceptionName));
    } else {
      map.put("message", ex.getLocalizedMessage());
    }
    return map;
  }
}
