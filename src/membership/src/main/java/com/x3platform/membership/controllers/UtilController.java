package com.x3platform.membership.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x3platform.globalization.I18n;
import com.x3platform.messages.MessageObject;
import com.x3platform.util.PinYinUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 辅助工具 API 接口
 */
@Lazy
@RestController("com.x3platform.membership.controllers.UtilController")
@RequestMapping("/api/membership/util")
public class UtilController {

  /**
   * 将文字转成拼音
   *
   * @return 响应的内容数据
   */
  @RequestMapping("/pinyin")
  public final String toPinyin(@RequestBody String data) {
    JSONObject req = JSON.parseObject(data);

    String text = req.getString("text");

    String result = PinYinUtil.toHanyuPinyin(text);

    return "{\"data\":" + JSON.toJSONString(result) + "," +
      MessageObject.stringify("0", I18n.getStrings().text("msg_query_success"), true) + "}";
  }
}
