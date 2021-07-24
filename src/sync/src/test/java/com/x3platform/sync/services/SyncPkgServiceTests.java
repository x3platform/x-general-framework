package com.x3platform.sync.services;

import static org.junit.Assert.assertNotNull;

import com.x3platform.sync.SyncSerializer;
import com.x3platform.sync.configuration.SyncConfigurationView;
import com.x3platform.sync.serialization.JsonSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ruanyu
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SyncPkgServiceTests {

  @Autowired
  @Qualifier("com.x3platform.sync.services.SyncPkgService")
  SyncPkgService service;

  @Test
  public void testSerialize() throws FileNotFoundException {
    SyncSerializer serializer = new JsonSerializer();

    File file = new File("test-serialize-result.txt");

    OutputStream stream = new FileOutputStream(file);

    Map entity = buildSyncPkg();

    serializer.serialize(stream, entity);

    assertNotNull("a", "");
  }

  @Ignore
  @Test
  public void testDeserialize() throws FileNotFoundException {
    SyncSerializer serializer = new JsonSerializer();

    File file = new File("test-serialize-result.txt");

    InputStream input = new FileInputStream(file);

    Map entity = serializer.deserialize(input, Map.class);

    assertNotNull(entity);
  }

  public Map buildSyncPkg() {
    Map<String, Object> entity = new HashMap<String, Object>();

    entity.put("table_name", "[CompanyName]");
    entity.put("data", "dbd");

    return entity;
  }

  @Ignore
  @Test
  public void testCreatePackages() throws FileNotFoundException {
    LocalDateTime beginDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
    LocalDateTime endDate = beginDate.plusDays(1);

    service.createPackages(beginDate, endDate);
  }

  @Ignore
  @Test
  public void testSend() {
    LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));

    service.send(date);
  }

  @Ignore
  @Test
  public void testCreateAndSend() {
    service.createAndSend();
  }

  @Ignore
  @Test
  public void testSendData() {
    // String organizationId = "10000003-0000-0000-0000-000000000000";
    String queueName = SyncConfigurationView.getInstance().getReceiveQueueName();
    String filePath = "E:/repositories/common/x-general-framework/src/sync/target/test-classes/sync/2019/4Q/11/00000000-0000-0000-0000-000000000001/2019112200000002/ocr_key_set_config/1";

    service.sendData(queueName, filePath);
  }

  @Ignore
  @Test
  public void testReceive() {
    service.receive();
  }

  @Ignore
  @Test
  public void testSyncFromPackPage() throws FileNotFoundException {
    File file = new File(
      "sync/2019/4Q/11/00000000-0000-0000-0000-000000000001/2019112100000028/ocr_key_set_config#5");

    InputStream input = new FileInputStream(file);

    service.syncFromPackPage(input);
  }
}
