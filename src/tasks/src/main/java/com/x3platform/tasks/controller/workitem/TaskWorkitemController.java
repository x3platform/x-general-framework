package com.x3platform.tasks.controller.workitem;

import com.x3platform.tasks.model.workitem.TaskWorkitemModel;
import com.x3platform.tasks.service.workitem.FindTaskWorkitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SelectTaskWorkitemController {

  @Autowired
  private FindTaskWorkitemService findTaskWorkitemService;

  /**
   * 根据id精确查询相关信息
   */
  @RequestMapping("/task/selecttask")
  public TaskWorkitemModel selectTaskWorkitem(String id) {
    return findTaskWorkitemService.selectTask(id);
  }

}
