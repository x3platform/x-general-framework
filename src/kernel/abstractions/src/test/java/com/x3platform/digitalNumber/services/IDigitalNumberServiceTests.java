package com.x3platform.digitalNumber.services;

import com.x3platform.data.DataQuery;
import com.x3platform.digitalNumber.DigitalNumberContext;
import com.x3platform.digitalNumber.models.DigitalNumberInfo;
import com.x3platform.digitalNumber.services.IDigitalNumberService;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
// @WebAppConfiguration
public class IDigitalNumberServiceTests {

  @Autowired
  private IDigitalNumberService service;

  @Test
  public void testFindOne() {
    DigitalNumberInfo entity = service.findOne("Key_Guid");

    assertNotNull("result is not null.", entity);
    assertEquals("result.name is \"Key_Guid\".", "Key_Guid", entity.getName());
    assertEquals("result.expression is \"{guid}\".", "{guid}", entity.getExpression());
  }

  @Test
  public void testFindAll() {

    IDigitalNumberService service = DigitalNumberContext.getInstance().getDigitalNumberService();

    List<DigitalNumberInfo> list = service.findAll();

    assertNotNull("result is not null.", list);

    DataQuery query = new DataQuery();

    query.setLength(5);

    list = service.findAll(query);

    assertEquals("result is not null.", 5, list.size());
  }

  /**
   * 测试 保存
   */
  @Test
  public void testSave() {
    String name = "test_" + DateUtil.getTimestamp();

    DigitalNumberInfo param = new DigitalNumberInfo();

    param.setName(name);
    param.setExpression("{guid}");
    param.setSeed(0);

    service.save(param);

    // 更新数据
    param.setName(name);
    param.setExpression("{int:seed:8}");
    param.setSeed(999);

    service.save(param);

    // 删除数据
    service.delete(name);
  }

  /**
   * 测试 生成流水号
   */
  @Test
  public void testGenerate() {
    String result = null;

    result = service.generate("Key_32DigitGuid");

    assertFalse(StringUtil.isNullOrEmpty(result));

    result = service.generate("Key_Guid");

    assertFalse(StringUtil.isNullOrEmpty(result));

    result = service.generate("Key_Nonce");

    assertFalse(StringUtil.isNullOrEmpty(result));

    result = service.generate("Key_RunningNumber");

    assertFalse(StringUtil.isNullOrEmpty(result));

    result = service.generate("Key_Session");

    assertFalse(StringUtil.isNullOrEmpty(result));

    result = service.generate("Key_Timestamp");

    assertFalse(StringUtil.isNullOrEmpty(result));
  }
}
