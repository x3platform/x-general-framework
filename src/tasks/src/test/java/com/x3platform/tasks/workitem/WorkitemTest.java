package com.x3platform.tasks.workitem;

import com.x3platform.tasks.controller.workitem.TaskWorkitemController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class WorkitemTest {
  @Autowired
  private TaskWorkitemController taskWorkitemController;

  @Test
  public void testSelectTaskWorkitem(){
    System.out.println(taskWorkitemController.selectTaskWorkitem("16c108d5-8e57-4032-babf-0730bede211d").toString());
  }
}
