package com.x3platform.tasks.models;

import java.util.*;

///#region Using Libraries

// import com.x3platform.CacheBuffer.*;
// import com.x3platform.Membership.*;
// import com.x3platform.Messages.*;
// import com.x3platform.Util.*;
import com.x3platform.ISerializedObject;
import com.x3platform.messages.IMessageObject;
import com.x3platform.util.DateUtil;
import org.dom4j.Element;
///#endregion

/**
 * 任务信息
 */
public class TaskWorkInfo implements IMessageObject, ISerializedObject {
  ///#region 构造函数:TaskWorkInfo()

  /**
   * 默认构造函数
   */
  public TaskWorkInfo() {
    this.mCreateDate = java.time.LocalDateTime.now();
  }
  ///#endregion

  ///#region 构造函数:TaskWorkInfo(string applicationId)

  /**
   * 默认构造函数
   */
  public TaskWorkInfo(String applicationId) {
    this();
    this.mApplicationId = applicationId;
  }
  ///#endregion

  ///#region 属性:Id
  private String mId;

  /**
   * 标识
   */
  public final String getId() {
    return mId;
  }

  public final void setId(String value) {
    mId = value;
  }
  ///#endregion

  ///#region 属性:ApplicationId
  private String mApplicationId;

  /**
   */
  public final String getApplicationId() {
    return mApplicationId;
  }

  public final void setApplicationId(String value) {
    mApplicationId = value;
  }
  ///#endregion

  ///#region 属性:TaskCode
  private String mTaskCode;

  /**
   * 任务编号
   */
  public final String getTaskCode() {
    return mTaskCode;
  }

  public final void setTaskCode(String value) {
    mTaskCode = value;
  }
  ///#endregion

  ///#region 属性:Type
  private String mType;

  /**
   * 类型 "1"表示审批类待办,点击后不会马上消失. "4"表示通知类待办,点击后马上消失.
   */
  public final String getType() {
    return mType;
  }

  public final void setType(String value) {
    mType = value;
  }
  ///#endregion

  ///#region 属性:Title
  private String mTitle;

  /**
   * 标题
   */
  public final String getTitle() {
    return mTitle;
  }

  public final void setTitle(String value) {
    mTitle = value;
  }
  ///#endregion

  ///#region 属性:Content
  private String mContent;

  /**
   * 内容
   */
  public final String getContent() {
    return mContent;
  }

  public final void setContent(String value) {
    mContent = value;
  }
  ///#endregion

  ///#region 属性:Tags
  private String mTags;

  /**
   */
  public final String getTags() {
    return mTags;
  }

  public final void setTags(String value) {
    mTags = value;
  }
  ///#endregion

  ///#region 属性:SenderId
  private String mSenderId;

  /**
   */
  public final String getSenderId() {
    return mSenderId;
  }

  public final void setSenderId(String value) {
    mSenderId = value;
  }
  ///#endregion

  // 接收者信息

  ///#region 属性:ReceiverGroup
  private List<TaskWorkReceiverInfo> mReceiverGroup = null;

  /**
   * 接收者
   */
  public final List<TaskWorkReceiverInfo> getReceiverGroup() {
    if (mReceiverGroup == null) {
      mReceiverGroup = new ArrayList<TaskWorkReceiverInfo>();
    }

    return mReceiverGroup;
  }
  ///#endregion

  ///#region 属性:CreateDate
  private java.time.LocalDateTime mCreateDate = java.time.LocalDateTime.MIN;

  /**
   */
  public final java.time.LocalDateTime getCreateDate() {
    return mCreateDate;
  }

  public final void setCreateDate(java.time.LocalDateTime value) {
    mCreateDate = value;
  }
  ///#endregion

  // -------------------------------------------------------
  // 显式实现 ICacheable
  // -------------------------------------------------------

  ///#region 属性:Expires
  private java.time.LocalDateTime mExpires = java.time.LocalDateTime.now().plusHours(6);

  /**
   * 过期时间
   */
  private java.time.LocalDateTime getExpires() {
    return mExpires;
  }

  private void setExpires(java.time.LocalDateTime value) {
    mExpires = value;
  }
  ///#endregion

  // -------------------------------------------------------
  // Xml 元素的导入和导出
  // -------------------------------------------------------

  ///#region 函数:Deserialize(XmlElement element)

  /**
   * 根据Xml元素加载对象
   *
   * @param element Xml元素
   */
  public final void deserialize(Element element) {
    StringBuilder outString = new StringBuilder();

    this.setApplicationId(element.selectSingleNode("applicationId").getText());
    this.setTaskCode(element.selectSingleNode("taskCode").getText());

/*
    // 设置任务标识
    this.setId(TasksContext.Instance.TaskWorkService.GetIdsByTaskCodes(this.getApplicationId(), this.getTaskCode()));

    if (tangible.DotNetToJavaStringHelper.isNullOrEmpty(this.getId())) {
      this.setId(StringHelper.ToGuid());
    }

    this.setType(element.SelectSingleNode("type").InnerText);
    this.setTitle(element.SelectSingleNode("title").InnerText);
    this.setContent(element.SelectSingleNode("content").InnerText);
    this.setTags(element.SelectSingleNode("tags").InnerText);

    // 设置发送人

    IAccountInfo account = null;

    account = MembershipManagement.Instance.AccountService[element.SelectSingleNode("sender").InnerText];

    this.setSenderId((account == null) ? element.SelectSingleNode("sender").InnerText + "(unkown)" : account.Id);

    // 设置接收人

    outString.append("<receiver>");

    TaskWorkReceiverInfo item = null;

    String[] keys = element.SelectSingleNode("receiver").InnerText.split("[,]", -1);

    String[] temp;

    for (String key : keys) {
      if (!tangible.DotNetToJavaStringHelper.isNullOrEmpty(key) && key.contains("#")) {
        temp = key.split("[#]", -1);

        item = new TaskWorkReceiverInfo();
        item.TaskId = this.getId();
        item.Status = Integer.parseInt(temp[0]);

        account = MembershipManagement.Instance.AccountService[temp[1]];

        item.ReceiverId = (account == null) ? temp[1] : account.Id;

        // 1 代表任务完 , 插入任务完成时间.
        if (item.Status == 1) {
          item.FinishTime = java.time.LocalDateTime.parse(temp[2]);
        }

        this.getReceiverGroup().add(item);
      }
    }

    this.setCreateDate(java.time.LocalDateTime.parse(element.SelectSingleNode("createDate").InnerText));
  */
  }

  /**
   * 根据对象导出Xml元素
   *
   * @return
   */
  public final String serializable() {
    return serializable(false, false);
  }

  /**
   * 根据对象导出Xml元素
   *
   * @param displayComment      显示注释
   * @param displayFriendlyName 显示友好名称
   * @return
   */
  public String serializable(boolean displayComment, boolean displayFriendlyName) {
    StringBuilder outString = new StringBuilder();

    outString.append("<task>");
    outString.append(String.format("<applicationId><![CDATA[%1$s]]></applicationId>", this.getApplicationId()));
    outString.append(String.format("<taskCode><![CDATA[%1$s]]></taskCode>", this.getTaskCode()));
    outString.append(String.format("<title><![CDATA[%1$s]]></title>", this.getTitle()));
    outString.append(String.format("<type><![CDATA[%1$s]]></type>", this.getType()));
    outString.append(String.format("<content><![CDATA[%1$s]]></content>", this.getContent()));
    outString.append(String.format("<tags><![CDATA[%1$s]]></tags>", this.getTags()));
    outString.append(String.format("<sender><![CDATA[%1$s]]></sender>", this.getSenderId()));

    outString.append("<receiver>");

    for (TaskWorkReceiverInfo item : this.getReceiverGroup()) {
      if (item.getStatus() == 1) {
        outString.append(String.format("%1$s#%2$s#%3$s,", item.getStatus(), item.getReceiverId(), item.getFinishTime()));
      } else {
        outString.append(String.format("%1$s#%2$s,", item.getStatus(), item.getReceiverId()));
      }
    }

    outString.append("</receiver>");

    outString.append(String.format("<createDate><![CDATA[%1$s]]></createDate>", this.getCreateDate()));

    outString.append("</task>");

    return outString.toString();
  }

  /**
   * 获取任务工作项信息列表
   *
   * @return
   */
  public final List<TaskWorkItemInfo> getTaskWorkItems() {
    List<TaskWorkItemInfo> list = new ArrayList<TaskWorkItemInfo>();

    for (TaskWorkReceiverInfo receiver : this.getReceiverGroup()) {
      TaskWorkItemInfo item = new TaskWorkItemInfo();

      item.setId(this.getId());
      item.setApplicationId(this.getApplicationId());
      item.setTaskCode(this.getTaskCode());
      item.setType(this.getType());
      item.setTitle(this.getTitle());
      item.setContent(this.getContent());
      item.setTags(this.getTags());
      item.setSenderId(this.getSenderId());

      item.setReceiverId(receiver.getReceiverId());
      item.setStatus(receiver.getStatus());
      item.setIsRead(receiver.getIsRead());
      item.setFinishTime(receiver.getFinishTime());

      item.setCreatedDate(this.getCreateDate());

      list.add(item);
    }

    return list;
  }

  // -------------------------------------------------------
  // 增加和移除接收者信息
  // -------------------------------------------------------

  /**
   * 增加接收者信息
   *
   * @param receiverId 接收者标识
   */
  public final void addReceiver(String receiverId) {
    this.addReceiver(receiverId, false, 0, DateUtil.getDefaultTime());
  }

  /**
   * 增加接收者信息
   *
   * @param receiverId 接收者标识
   * @param isRead     是否已读
   * @param status     状态
   * @param finishTime 完成时间
   */
  public final void addReceiver(String receiverId, boolean isRead, int status, java.time.LocalDateTime finishTime) {
    TaskWorkReceiverInfo receiver = new TaskWorkReceiverInfo();

    receiver.setReceiverId(receiverId);
    receiver.setStatus(status);
    receiver.setIsRead(isRead);
    receiver.setFinishTime(finishTime);

    this.getReceiverGroup().add(receiver);
  }

  /**
   * 移除接收者信息
   *
   * @param receiverId 接收者标识
   */
  public final void removeReceiver(String receiverId) {
    for (int i = 0; i < this.getReceiverGroup().size(); i++) {
      if (receiverId.equals(this.getReceiverGroup().get(i).getReceiverId())) {
        this.getReceiverGroup().remove(i);
      }
    }
  }
}
