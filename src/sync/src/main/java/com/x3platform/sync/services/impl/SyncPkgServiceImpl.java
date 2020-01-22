package com.x3platform.sync.services.impl;

import static com.x3platform.Constants.TEXT_DEFAULT;

import com.x3platform.KernelContext;
import com.x3platform.data.DataQuery;
import com.x3platform.data.DynamicDataSourceContextHolder;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.sync.SyncContext;
import com.x3platform.sync.SyncSerializer;
import com.x3platform.sync.configuration.SyncConfigurationView;
import com.x3platform.sync.mappers.SyncPkgMapper;
import com.x3platform.sync.models.SyncPkg;
import com.x3platform.sync.models.SyncQueue;
import com.x3platform.sync.models.SyncSetting;
import com.x3platform.sync.services.SyncPkgService;
import com.x3platform.util.ByteUtil;
import com.x3platform.util.DateUtil;
import com.x3platform.util.DirectoryUtil;
import com.x3platform.util.FileUtil;
import com.x3platform.util.StringUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ruanyu
 */
@Service
public class SyncPkgServiceImpl implements SyncPkgService {

  private static final String DIGITAL_NUMBER_KEY_CODE = "Table_Sync_Pkg_Key_Id";

  /**
   * 启动的时候 需要注入，如果不注入可能失败
   */
  @Autowired
  RabbitTemplate rabbitTemplate;

  @Autowired
  private SyncSerializer serializer = null;

  /**
   * 数据提供器
   */
  @Autowired
  private SyncPkgMapper provider = null;

  // -------------------------------------------------------
  // 保存 删除
  // -------------------------------------------------------

  /**
   * 保存记录
   *
   * @param entity {@link SyncPkg} 实例的详细信息
   * @return {@link SyncPkg} 实例的详细信息
   */
  @Override
  public int add(SyncPkg entity) {
    int affectedRows = -1;

    String id = entity.getId();

    if (StringUtil.isNullOrEmpty(id)) {
      throw new NullPointerException("必须填写对象标识");
    }

    if (provider.selectByPrimaryKey(id) == null) {
      affectedRows = provider.insert(entity);
    }

    KernelContext.getLog().debug("save entity id:'{}', affectedRows:{}", id, affectedRows);

    return 0;
  }

  /**
   * 删除记录
   *
   * @param id 实例的标识
   */
  @Override
  public int remove(String id) {
    int affectedRows = provider.deleteByPrimaryKey(id);

    KernelContext.getLog().debug("delete entity id:'{}', affectedRows:{}", id, affectedRows);

    return 0;
  }

  // -------------------------------------------------------
  // 查询
  // -------------------------------------------------------

  /**
   * 查询某条记录
   *
   * @param id 标识
   * @return {@link SyncPkg} 实例的详细信息
   */
  @Override
  public SyncPkg findOne(String id) {
    return provider.selectByPrimaryKey(id);
  }

  /**
   * 查询所有相关记录
   *
   * @param query 数据查询参数
   * @return 所有相关 {@link SyncPkg} 实例的详细信息
   */
  @Override
  public List<SyncPkg> findAll(DataQuery query) {
    return provider.findAll(query.getMap());
  }

  // -------------------------------------------------------
  // 发送更新包
  // -------------------------------------------------------

  /**
   * 获取最后一个更新包的结束时间
   *
   * @return 时间
   */
  @Override
  public LocalDateTime getLastPkgEndDate() {
    LocalDateTime endDate = provider.getLastPkgEndDate();
    if (endDate == null) {
      endDate = DateUtil.getDefaultLocalDateTime();
    }

    // 输出最近一个更新包结束时间
    KernelContext.getLog().info("last package date:{}(end)", endDate);

    return endDate;
  }

  /**
   * 创建和发送更新包
   */
  @Override
  public int createAndSend() {
    LocalDateTime beginDate = getLastPkgEndDate();

    LocalDateTime endDate = LocalDateTime.now();

    int result = 0;

    try {
      result = createPackages(beginDate, endDate);
    } catch (FileNotFoundException ex) {
      KernelContext.getLog().error("FileNotFoundException", ex);
      return 1;
    }

    if (result > 0) {
      return result;
    }

    result = send(beginDate);

    return result;
  }

  /**
   * 创建更新包
   */
  @Override
  public int createPackages(LocalDateTime beginDate, LocalDateTime endDate) throws FileNotFoundException {
    // -- update ocr_key_set_config set organization_id = '00000000-0000-0000-0000-000000000001' where organization_id is null;
    // -- update ocr_key_set_config a INNER JOIN ocr_key_set_config b ON a.id = b.id set a.modified_date = b.last_update_time;
    // id varchar(36), organization_id varchar(36), modified_date - datetime

    // sync/2019/4Q/11/18/[APPLICATION-ID]/[TABLE-NAME]/[PKG-DATA];
    // 生成数据包名称

    String generalApplicationId = SyncConfigurationView.getInstance().getGeneralApplicationId();

    int maxSubListLength = 100; // SyncConfigurationView.getInstance().getGeneralApplicationId()

    List<String> applicationIds = SyncContext.getInstance().getSyncQueueService().getApplicationIds();

    List<SyncSetting> settings = SyncContext.getInstance().getSyncSettingService().getSettings();

    if (!applicationIds.contains(generalApplicationId)) {
      applicationIds.add(generalApplicationId);
    }

    // 回收的数据包标识
    // 如果生成的数据记录为零, 则回收数据包标识待下次使用。
    String recoveredPkgId = "";

    for (String applicationId : applicationIds) {
      // 设置名称为 key 的数据源
      DynamicDataSourceContextHolder.clearDataSourceKey();

      String pkgId = StringUtil.isNullOrEmpty(recoveredPkgId) ?
        DigitalNumberContext.generate(DIGITAL_NUMBER_KEY_CODE) : recoveredPkgId;

      // 更新包路径
      String pkgPath = SyncConfigurationView.getInstance().getDataFolder() + "/"
        + DirectoryUtil.formatTimePath(LocalDateTime.now()) + applicationId + "/" + pkgId + "/";

      // 设置更新包信息
      SyncPkg pkg = new SyncPkg();

      pkg.setId(pkgId);
      pkg.setApplicationId(applicationId);
      pkg.setPath(pkgPath);
      pkg.setBeginDate(beginDate);
      pkg.setEndDate(endDate);
      pkg.setIsSend(0);

      int recordCount = 0;

      for (SyncSetting setting : settings) {
        // 数据表信息
        String tableName = setting.getTableName();

        if (TEXT_DEFAULT.equals(setting.getOriginDataSourceName())) {
          // 设置为默认数据源
          DynamicDataSourceContextHolder.clearDataSourceKey();
        } else {
          if (!DynamicDataSourceContextHolder.containsDataSourceKey(setting.getOriginDataSourceName())) {
            KernelContext.getLog().error("data source:{} not exists.", setting.getOriginDataSourceName());
            return 1;
          }
          // 设置名称为 key 的数据源
          DynamicDataSourceContextHolder.setDataSourceKey(setting.getOriginDataSourceName());
        }

        // 创建识别配置更新包
        List<Map> list = null;

        if (setting.getSyncType() == 0 && generalApplicationId.equals(applicationId)) {
          list = fetchData(tableName, setting.getFields(), beginDate, endDate);
        } else if (setting.getSyncType() == 1) {
          list = fetchData(applicationId, tableName, setting.getFields(), setting.getModifiedDateField(),
            setting.getApplicationIdField(), beginDate, endDate);
        }

        // 如果没有符合条件的数据
        if (list == null || list.isEmpty()) {
          continue;
        }

        recordCount += list.size();

        long dataIndex = 1;

        List<List<Map>> subLists = splitList(list, maxSubListLength);

        for (List<Map> item : subLists) {
          // 创建目录
          DirectoryUtil.create(pkgPath + "/" + tableName);

          File file = new File(StringUtil.format("{}/{}/{}", pkgPath, tableName, dataIndex++));

          OutputStream stream = new FileOutputStream(file);

          Map<String, Object> result = new HashMap<String, Object>();

          result.put("data_source_name", setting.getTargetDataSourceName());
          result.put("table_name", tableName);
          result.put("data", item);

          serializer.serialize(stream, result);
        }
      }

      // 还原数据源
      DynamicDataSourceContextHolder.clearDataSourceKey();

      if (recordCount > 0) {
        // 记录总数
        pkg.setRecordCount(recordCount);
        add(pkg);
      } else if (recordCount == 0) {
        recoveredPkgId = pkgId;
      }
    }

    return 0;
  }

  /**
   * 将列表按照指定的长度拆分成多个子列表
   */
  public static <T> List<List<T>> splitList(List<T> list, int length) {
    List<List<T>> result = new ArrayList<List<T>>();
    int count = list.size() / length;
    // 剩余长度
    int left = list.size() % length;
    for (int i = 0; i <= count; i++) {
      List<T> subList = null;
      if (i == count) {
        // 处理最后一个子列表的数据
        subList = list.subList(i * length, i * length + left);
      } else {
        subList = list.subList(i * length, length * (i + 1));
      }
      result.add(subList);
    }

    return result;
  }

  @Override
  public List<Map> fetchData(String tableName, String fields, LocalDateTime beginDate, LocalDateTime endDate) {
    return provider.fetchData(tableName, fields, "modified_date", beginDate, endDate);
  }

  @Override
  public List<Map> fetchData(String tableName, String fields, String modifiedDateField, LocalDateTime beginDate,
    LocalDateTime endDate) {
    return provider.fetchData(tableName, fields, modifiedDateField, beginDate, endDate);
  }

  @Override
  public List<Map> fetchData(String applicationId, String tableName, String fields,
    String modifiedDateField, String applicationIdField, LocalDateTime beginDate, LocalDateTime endDate) {
    return provider.fetchDataByApplicationId(applicationId, tableName, fields,
      modifiedDateField, applicationIdField, beginDate, endDate);
  }

  @Override
  public int send() {
    LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
    return send(date);
  }

  @Override
  public int send(LocalDateTime startDate) {
    String generalApplicationId = SyncConfigurationView.getInstance().getGeneralApplicationId();

    List<SyncQueue> queues = SyncContext.getInstance().getSyncQueueService().getQueues();

    List<SyncPkg> list = provider.findAllByStartDate(startDate);

    for (SyncPkg item : list) {
      for (SyncQueue queue : queues) {
        // 验证数据包的范围
        // 通用数据 ApplicationId = GeneralApplicationId
        // 专有数据 ApplicationId = queue.getApplicationId()
        if (!generalApplicationId.equals(item.getApplicationId())
          && !queue.getApplicationId().equals(item.getApplicationId())) {
          continue;
        }

        File pkgPath = new File(item.getPath());

        File[] files = pkgPath.listFiles();

        if (files != null) {
          // 遍历所有的文件和目录
          // 第一层为表信息
          for (File file : files) {
            if (file.isDirectory()) {
              File[] dataFiles = file.listFiles();
              if (dataFiles != null) {
                // 第二层为数据文件信息
                for (File dataFile : dataFiles) {
                  // 发送数据
                  sendData(queue.getQueueName(), dataFile.getAbsolutePath());
                }
              }
            }
          }
        }

        SyncContext.getInstance().getSyncQueueService().setLastPkgId(queue.getId(), item.getId());
      }

      provider.setSend(item.getId(), 1);
    }

    return 0;
  }

  /**
   * 发送某个更新包之后的更新包
   *
   * @param queueId 队列标识
   * @param startPkgId 起始的更新包标识
   * @return 消息代码
   */
  @Override
  public int send(String queueId, String startPkgId) {
    String generalApplicationId = SyncConfigurationView.getInstance().getGeneralApplicationId();

    SyncQueue queue = SyncContext.getInstance().getSyncQueueService().findOne(queueId);

    if (queue == null) {
      throw new NullPointerException();
    }

    List<SyncPkg> list = provider.findAllByStartPkgId(startPkgId);

    for (SyncPkg item : list) {
      // 验证数据包的范围
      // 通用数据 ApplicationId = GeneralApplicationId
      // 专有数据 ApplicationId = queue.getApplicationId()
      if (!generalApplicationId.equals(item.getApplicationId())
        && !queue.getApplicationId().equals(item.getApplicationId())) {
        continue;
      }

      File pkgPath = new File(item.getPath());

      File[] files = pkgPath.listFiles();

      if (files != null) {
        // 遍历所有的文件和目录
        // 第一层为表信息
        for (File file : files) {
          if (file.isDirectory()) {
            File[] dataFiles = file.listFiles();
            if (dataFiles != null) {
              // 第二层为数据文件信息
              for (File dataFile : dataFiles) {
                // 发送数据
                sendData(queue.getQueueName(), dataFile.getAbsolutePath());
              }
            }
          }
        }
      }

      SyncContext.getInstance().getSyncQueueService().setLastPkgId(queue.getId(), item.getId());

      provider.setSend(item.getId(), 1);
    }

    return 0;
  }

  /**
   * 发送数据
   *
   * @param queueName 队列名称
   * @param filePath 文件路径
   */
  @Override
  public int sendData(String queueName, String filePath) {
    try {
      byte[] fileByte = FileUtil.toBytes(filePath);

      MessageProperties messageProperties = new MessageProperties();

      // 设置消息是否持久化，Persistent 表示持久化，Non-persistent 表示不持久化。
      messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
      messageProperties.setContentType("UTF-8");

      Message message = new Message(fileByte, messageProperties);

      CorrelationData data = new CorrelationData();

      rabbitTemplate.convertAndSend(queueName, message, data);

      KernelContext.getLog().debug("send data, table name:{}, file path:{}.", queueName, filePath);
    } catch (IOException ex) {
      KernelContext.getLog().error("IOException", ex);
      return 1;
    }

    return 0;
  }

  // -------------------------------------------------------
  // 接收更新包
  // -------------------------------------------------------

  @Override
  public int receive() {
    String receiveQueueNanme = SyncConfigurationView.getInstance().getReceiveQueueName();

    Message message = rabbitTemplate.receive(receiveQueueNanme);

    byte[] buffer = message.getBody();

    syncFromPackPage(ByteUtil.toStream(buffer));

    return 0;
  }

  @Override
  public int syncFromPackPage(InputStream stream) {
    Map result = serializer.deserialize(stream, Map.class);

    String dataSourceName = result.get("data_source_name").toString();
    String tableName = result.get("table_name").toString();

    List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");

    KernelContext.getLog().debug("sync data:{table name:{}, row count:{}}.", tableName, data.size());

    if (TEXT_DEFAULT.equals(dataSourceName)) {
      // 设置为默认数据源
      DynamicDataSourceContextHolder.clearDataSourceKey();
    } else {
      if (!DynamicDataSourceContextHolder.containsDataSourceKey(dataSourceName)) {
        KernelContext.getLog().error("data source:{} not exists.", dataSourceName);
        return 1;
      }
      // 设置名称为 key 的数据源
      DynamicDataSourceContextHolder.setDataSourceKey(dataSourceName);
    }

    for (Map<String, Object> item : data) {
      provider.syncData(tableName, item.get("id").toString(), item);
    }

    KernelContext.getLog().debug("sync data:{table name:{}} finished.", tableName);

    return 0;
  }
}
