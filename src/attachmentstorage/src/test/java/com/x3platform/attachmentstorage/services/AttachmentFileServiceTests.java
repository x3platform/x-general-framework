package com.x3platform.attachmentstorage;

import com.x3platform.KernelContext;
import com.x3platform.attachmentstorage.services.IAttachmentFileService;
import com.x3platform.attachmentstorage.util.UploadFileUtil;
import com.x3platform.data.DataQuery;
import com.x3platform.digitalNumber.DigitalNumberContext;
import com.x3platform.util.DateUtil;
import com.x3platform.util.StringUtil;
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
public class AttachmentFileServiceTests {

  //-------------------------------------------------------
  // 测试内容
  //-------------------------------------------------------

  @Test
  public void testSave() {
    IAttachmentFileService service = AttachmentStorageContext.getInstance().getAttachmentFileService();

//    IAttachmentParentObject parent = new AttachmentParentObject(StringUtil.toUuid(),
//      "TestObject",
//      KernelContext.parseObjectType(AttachmentFileInfo.class),
//      "test");

    IAttachmentFileInfo param = new AttachmentFileInfo();

    param.setId(UploadFileUtil.newIdentity());
    param.setAttachmentName("test_" + DateUtil.getTimestamp());
    param.setFileType(".txt");
    param.setEntityClassName("TestObject");
    param.setEntityId(StringUtil.toUuid());
    param.setAttachmentFolder("test");

    service.save(param);

    assertNotNull(param);
  }

  @Test
  public void testFindOne() {
    IAttachmentFileService service = AttachmentStorageContext.getInstance().getAttachmentFileService();

    // IAttachmentParentObject parent = new AttachmentParentObject(StringUtil.toUuid(),
    //   "TestObject",
    //   KernelContext.parseObjectType(AttachmentFileInfo.class),
    //    "test");

    IAttachmentFileInfo param = new AttachmentFileInfo();

    param.setId("test-" + DigitalNumberContext.generate("Key_RunningNumber"));
    param.setAttachmentName("test-" + StringUtil.toRandom(8));
    param.setFileType(".tXT");
    param.setEntityClassName("TestObject");
    param.setEntityId(StringUtil.toUuid());
    param.setAttachmentFolder("test");

    service.save(param);

    param = service.findOne(param.getId());

    assertNotNull(param);
  }

  @Test
  public void testFindAll() {
    List<IAttachmentFileInfo> list = AttachmentStorageContext.getInstance().getAttachmentFileService().findAll(new DataQuery());

    assertNotNull(list);
  }

  @Test
  public void testSetValid() {
    IAttachmentFileService service = AttachmentStorageContext.getInstance().getAttachmentFileService();
    String attachmentFileId = "20150415152823240893301";

    service.setValid(
      "X3Platform.Plugins.Cost.Model.CostInfo, X3Platform.Plugins.Cost",
      "79398250-ca7a-4983-9d9b-f36c90bbcf05",
      attachmentFileId);

    IAttachmentFileInfo file = service.findOne(attachmentFileId);

    assertNotNull(file);
  }
}
