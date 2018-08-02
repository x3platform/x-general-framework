package com.x3platform.tasks.services;

import com.alibaba.fastjson.JSON;
import com.x3platform.tasks.TasksContext;
import com.x3platform.tasks.controllers.TaskWorkItemController;
import com.x3platform.tasks.models.TaskWorkItemInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Test
  public void testGetUnfinishedQuantities() {
    ITaskWorkItemService service = TasksContext.getInstance().getTaskWorkItemService();

    List<HashMap<Integer, Integer>> results = service.getUnfinishedQuantities("00000000-0000-0000-0000-000000000001");

    String text = JSON.toJSONString(results);

    assertNotNull("result is not null.", text);
  }


  @Test
  public void testFindAllByReceiverId() {
    ITaskWorkItemService service = TasksContext.getInstance().getTaskWorkItemService();

    List<TaskWorkItemInfo> results = service.findAllByReceiverId("00000000-0000-0000-0000-000000000001", new HashMap<String, Object>());

    String text = JSON.toJSONString(results);

    assertNotNull("result is not null.", text);
  }

}
