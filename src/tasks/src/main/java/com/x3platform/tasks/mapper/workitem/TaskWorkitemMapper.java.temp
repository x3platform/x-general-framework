package com.x3platform.tasks.mapper.workitem;

import com.x3platform.tasks.model.workitem.TaskWorkitemModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface TaskWorkitemMapper {

  TaskWorkitemModel selectTask(String id);

  List<TaskWorkitemModel> queryAllTasK(String receiver_id);

  int countTask(String receiver_id);

  void updateRead(String id);

  //  void clearTask(String type);

  void addTask(TaskWorkitemModel taskWorkitemModel);

  void updateStatus(String id);

}
