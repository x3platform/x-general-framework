package com.x3platform.tasks.mapper;

import com.x3platform.tasks.model.TaskWorkitemModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TaskWorkitemMapper {

  TaskWorkitemModel selectTask(String id);
  List<TaskWorkitemMapper> queryTasK(String type);
  
}
