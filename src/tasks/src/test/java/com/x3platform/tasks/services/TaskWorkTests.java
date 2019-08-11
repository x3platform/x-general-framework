package com.x3platform.tasks.services;

import com.alibaba.fastjson.JSON;
import com.x3platform.tasks.TasksContext;
import com.x3platform.tasks.controllers.TaskWorkItemController;
import com.x3platform.tasks.models.TaskWorkItem;
import org.junit.Ignore;
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
public class TaskWorkTests {

  @Test
  public void testSend() {
    // ITaskWorkService service = TasksContext.getInstance().getTaskWorkService();
//
//     service.send("aa","aa","xx",
//      "sss","xxx",
//      "","0001","xxx");
//    List<HashMap<Integer, Integer>> results = service.getUnfinishedQuantities("00000000-0000-0000-0000-000000000001");

  //  String text = JSON.toJSONString(results);

   // assertNotNull("result is not null.", text);
  }
}
