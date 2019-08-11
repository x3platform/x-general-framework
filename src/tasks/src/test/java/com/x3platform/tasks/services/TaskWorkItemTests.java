package com.x3platform.tasks.services;

import com.alibaba.fastjson.JSON;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.tasks.TasksContext;
import com.x3platform.tasks.models.TaskWorkItem;
import com.x3platform.tests.TestConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

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
  public void testGetUnfinishedQuantities() {
    List<HashMap<Integer, Integer>> results = service.getUnfinishedQuantities(TestConstants.GENERAL_ACCOUNT_ID);

    String text = JSON.toJSONString(results);

    assertNotNull("result is not null.", text);
  }


  @Test
  public void testFindAllByReceiverId() {
    DataQuery query = new DataQuery();

    List<TaskWorkItem> results = service.findAllByReceiverId(TestConstants.GENERAL_ACCOUNT_ID, query);

    String text = JSON.toJSONString(results);

    assertNotNull("result is not null.", text);
  }
}
