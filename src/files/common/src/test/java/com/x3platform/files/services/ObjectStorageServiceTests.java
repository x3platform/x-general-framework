package com.x3platform.files.services;

import com.x3platform.KernelContext;
import com.x3platform.files.DistributedObjectStorageFile;
import com.x3platform.files.ObjectStorageFile;
import com.x3platform.files.ObjectStorageContext;
import com.x3platform.files.DistributedObjectStorageFile;
import com.x3platform.files.services.ObjectStorageService;
import com.x3platform.files.util.ObjectStorageFileUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalnumber.DigitalNumberContext;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ObjectStorageServiceTests {

  //-------------------------------------------------------
  // 测试内容
  //-------------------------------------------------------

  @Test
  public void testSave() {
    ObjectStorageService service = ObjectStorageContext.getInstance().getObjectStorageService();

    String id = ObjectStorageFileUtil.newIdentity();

    ObjectStorageFile param = new DistributedObjectStorageFile();

    param.setId(id);
    param.setFileName("test_" + DateUtil.getTimestamp());
    param.setFileType(".txt");
    param.setEntityClassName("TestObject");
    param.setEntityId(StringUtil.toUuid());
    param.setFileFolder("test");
    param.setVirtualPath("test/2015/2Q/4/20150415154516159470246.txt");

    service.save(param);

    assertNotNull(param);

    service.delete(id);
  }

  @Test
  public void testFindOne() {
    ObjectStorageService service = ObjectStorageContext.getInstance().getObjectStorageService();

    String id = "test-" + DigitalNumberContext.generate("Key_RunningNumber");

    ObjectStorageFile param = new DistributedObjectStorageFile();

    param.setId(id);
    param.setFileEntityClassName("com.x3platform.files.testobject");
    param.setFileName("test-" + StringUtil.toRandom(8));
    param.setFileType(".txt");
    param.setEntityClassName("com.x3platform.files.testobject");
    param.setEntityId(StringUtil.toUuid());
    param.setFileFolder("test");
    param.setVirtualPath("test/2015/2Q/4/20150415154516159470246.txt");

    service.save(param);

    param = service.findOne(param.getId());

    assertNotNull(param);

    service.delete(id);
  }

  @Test
  public void testFindAll() {
    List<ObjectStorageFile> list = ObjectStorageContext.getInstance().getObjectStorageService().findAll(new DataQuery());

    assertNotNull(list);
  }

  @Test
  public void testSetValid() {
    ObjectStorageService service = ObjectStorageContext.getInstance().getObjectStorageService();
  
    String fileId = "19700101000000000000001";
  
    service.setValid(
      "com.x3platform.plugins.articles.models.Article",
      "00000000-0000-0000-0000-000000000001",
      fileId);
  
    ObjectStorageFile file = service.findOne(fileId);

    assertNotNull(file);
  }
}
