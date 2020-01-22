package com.x3platform.tasks.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.alibaba.fastjson.JSON;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.tasks.models.TaskWorkItem;
import com.x3platform.tests.TestConstants;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskWorkItemTests {

  @Autowired
  @Qualifier("com.x3platform.tasks.services.TaskWorkItemService")
  private TaskWorkItemService service;

  @Test
  public void testSend() {
    String taskCode = DigitalNumberContext.generate("Key_Guid");

    service.send(TestConstants.APPLICATION_ID, taskCode, "1",
      "测试任务信息-" + taskCode, "http://x3platform.com/test_url",
      "测试", TestConstants.SUPER_ACCOUNT_ID, TestConstants.GENERAL_ACCOUNT_ID);

    String receiverId = DigitalNumberContext.generate("Key_Guid");

    service.sendAppendRange(TestConstants.APPLICATION_ID, taskCode, receiverId);

    service.deleteByTaskCode(TestConstants.APPLICATION_ID, taskCode);
  }

  @Test
  public void testSetFinished() {
    String taskCode;

    TaskWorkItem workItem;

    // 测试 setFinished(receiverId);
    taskCode = DigitalNumberContext.generate("Key_Guid");

    service.send(TestConstants.APPLICATION_ID, taskCode, "1",
      "测试任务信息-" + taskCode, "http://x3platform.com/test_url",
      "测试", TestConstants.SUPER_ACCOUNT_ID, TestConstants.GENERAL_ACCOUNT_ID);

    service.setFinished(TestConstants.GENERAL_ACCOUNT_ID);

    workItem = service.findOneByTaskCode(TestConstants.APPLICATION_ID, taskCode, TestConstants.GENERAL_ACCOUNT_ID);

    assertEquals(1, workItem.getStatus());

    service.deleteByTaskCode(TestConstants.APPLICATION_ID, taskCode);

    // setFinishedByReceiverIdAndType
    taskCode = DigitalNumberContext.generate("Key_Guid");

    service.send(TestConstants.APPLICATION_ID, taskCode, "1",
      "测试setFinishedByReceiverIdAndType-" + taskCode, "http://x3platform.com/test_url",
      "测试", TestConstants.SUPER_ACCOUNT_ID, TestConstants.GENERAL_ACCOUNT_ID);

    service.setFinishedByType(TestConstants.GENERAL_ACCOUNT_ID, "1");

    workItem = service.findOneByTaskCode(TestConstants.APPLICATION_ID, taskCode, TestConstants.GENERAL_ACCOUNT_ID);

    assertEquals(1, workItem.getStatus());

    service.deleteByTaskCode(TestConstants.APPLICATION_ID, taskCode);
  }

  @Test
  public void testGetUnfinishedQuantities() {
    List<HashMap<Integer, Integer>> results = service.getUnfinishedQuantities(TestConstants.GENERAL_ACCOUNT_ID);

    String text = JSON.toJSONString(results);

    assertNotNull("result is not null.", text);
  }

  @Test
  public void testFindAllByReceiverId() {
    List<TaskWorkItem> results = service.findAllByReceiverId(TestConstants.GENERAL_ACCOUNT_ID);

    String text = JSON.toJSONString(results);

    assertNotNull("result is not null.", text);
  }
}
