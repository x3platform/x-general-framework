package com.x3platform.tasks.mappers;

import com.x3platform.tasks.models.TaskWorkitemModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface TaskWorkitemMapper {

  /**
   * 根据ID查询出待办信息的详情
   * @param id
   * @return
   */
  TaskWorkitemModel selectTask(String id);

  /**
   * 根据receiver_id(接收人id)查出所有待办信息
   * @param receiver_id
   * @return
   */
  List<TaskWorkitemModel> queryAllTasK(String receiver_id);


  /**
   * 根据receiver_id(接收人id)统计出该接收人待办信息条数
   * @param receiver_id
   * @return
   */
  int countTask(String receiver_id);


  /**
   * 根据id修改待办信息的阅读状态
   * @param id
   */
  void updateRead(String id);

  //  void clearTask(String type);

  /**
   * 添加待办信息
   * @param taskWorkitemModel
   */
   void addTask(TaskWorkitemModel taskWorkitemModel);

  /**
   * 根据id将待办信息修改为完成
   * @param id
   */
  void updateStatus(String id);

}
