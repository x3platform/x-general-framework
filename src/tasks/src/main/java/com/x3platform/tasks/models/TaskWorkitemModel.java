package com.x3platform.tasks.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TaskWorkitemModel {

  private String id;
  private String application_id;
  private String task_code;
  private String type;
  private String title;
  private String content;
  private String tags;
  private String sender_id;
  private String receiver_id;
  private byte is_read;
  private long status;
  private Date finish_time;
  private Date created_date;


  public void setId(String id) {
    this.id = id;
  }

  public String getApplication_id() {
    return application_id;
  }

  public void setApplication_id(String application_id) {
    this.application_id = application_id;
  }

  public String getTask_code() {
    return task_code;
  }

  public void setTask_code(String task_code) {
    this.task_code = task_code;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public String getSender_id() {
    return sender_id;
  }

  public void setSender_id(String sender_id) {
    this.sender_id = sender_id;
  }

  public String getReceiver_id() {
    return receiver_id;
  }

  public void setReceiver_id(String receiver_id) {
    this.receiver_id = receiver_id;
  }

  public byte getIs_read() {
    return is_read;
  }

  public void setIs_read(byte is_read) {
    this.is_read = is_read;
  }

  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }

  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
  public Date getFinish_time() {
    return finish_time;
  }

  public void setFinish_time(Date finish_time) {
    this.finish_time = finish_time;
  }

  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
  public Date getCreated_date() {
    return created_date;
  }

  public void setCreated_date(Date created_date) {
    this.created_date = created_date;
  }

  public String getId() {
    return id;
  }


}

