package com.x3platform.tasks.services;

import com.x3platform.tasks.mappers.TaskWorkitemMapper;
import com.x3platform.tasks.models.TaskWorkitemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskWorkitemService {

  @Autowired
  private TaskWorkitemMapper taskWorkitemMapper;

  /**
   * 根据ID查询出待办信息的详情
   * @param id
   * @return
   */
  public TaskWorkitemModel selectTask(String id) {

    return taskWorkitemMapper.selectTask(id);
  }

  /**
   * 添加待办信息
   * @param taskWorkitemModel
   */
  public void addTask(TaskWorkitemModel taskWorkitemModel) {
    taskWorkitemMapper.addTask(taskWorkitemModel);
  }

  /**
   * 根据receiver_id(接收人id)查出所有待办信息
   * @param receiver_id
   * @return
   */
  public List<TaskWorkitemModel> queryAllTasK(String receiver_id) {
    return taskWorkitemMapper.queryAllTasK(receiver_id);
  }

  /**
   * 根据receiver_id(接收人id)统计出该接收人待办信息条数
   * @param receiver_id
   * @return
   */
  public int countTask(String receiver_id) {
    return taskWorkitemMapper.countTask(receiver_id);
  }

  /**
   * 根据id修改待办信息的阅读状态
   * @param id
   */
  public void updateRead(String id) {
    taskWorkitemMapper.updateRead(id);
  }

  /**
   * 根据id将待办信息修改为完成
   * @param id
   */
  public void updateStatus(String id){
    taskWorkitemMapper.updateStatus(id);
  }


}

