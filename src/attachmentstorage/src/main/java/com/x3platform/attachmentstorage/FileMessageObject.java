package com.x3platform.attachmentstorage;

import com.x3platform.messages.MessageObject;

/**
 * company 阿米智能
 * FileName FileMessageObject
 * Package com.x3platform.attachmentstorage.util
 * Description 文件上传下载，消息返回类
 * author xueqian.huang@amisbook.com
 * create 2018-10-09 9:42
 * version V1.0
 */
public class FileMessageObject {
  private String code;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  private String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  private Object data;

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
