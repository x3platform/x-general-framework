package com.x3platform.attachmentstorage.services;

import com.x3platform.KernelContext;
import com.x3platform.attachmentstorage.AttachmentFile;
import com.x3platform.attachmentstorage.AttachmentStorageContext;
import com.x3platform.attachmentstorage.GeneralAttachmentFile;
import com.x3platform.attachmentstorage.services.AttachmentFileService;
import com.x3platform.attachmentstorage.util.UploadFileUtil;
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
public class AttachmentFileServiceTests {

  //-------------------------------------------------------
  // 测试内容
  //-------------------------------------------------------

  @Test
  public void testSave() {
    AttachmentFileService service = AttachmentStorageContext.getInstance().getAttachmentFileService();

    AttachmentFile param = new GeneralAttachmentFile();

    param.setId(UploadFileUtil.newIdentity());
    param.setAttachmentName("test_" + DateUtil.getTimestamp());
    param.setFileType(".txt");
    param.setEntityClassName("TestObject");
    param.setEntityId(StringUtil.toUuid());
    param.setAttachmentFolder("test");
    param.setVirtualPath("test/2015/2Q/4/20150415154516159470246.txt");

    service.save(param);

    assertNotNull(param);
  }

  @Test
  public void testFindOne() {
    AttachmentFileService service = AttachmentStorageContext.getInstance().getAttachmentFileService();

    AttachmentFile param = new GeneralAttachmentFile();

    param.setId("test-" + DigitalNumberContext.generate("Key_RunningNumber"));
    param.setAttachmentName("test-" + StringUtil.toRandom(8));
    param.setFileType(".tXT");
    param.setEntityClassName("TestObject");
    param.setEntityId(StringUtil.toUuid());
    param.setAttachmentFolder("test");
    param.setVirtualPath("test/2015/2Q/4/20150415154516159470246.txt");

    service.save(param);

    param = service.findOne(param.getId());

    assertNotNull(param);
  }

  @Test
  public void testFindAll() {
    List<AttachmentFile> list = AttachmentStorageContext.getInstance().getAttachmentFileService().findAll(new DataQuery());

    assertNotNull(list);
  }

  @Test
  public void testSetValid() {
    AttachmentFileService service = AttachmentStorageContext.getInstance().getAttachmentFileService();

    String attachmentFileId = "20150415152823240893301";

    service.setValid(
      "X3Platform.Plugins.Cost.Model.CostInfo, X3Platform.Plugins.Cost",
      "79398250-ca7a-4983-9d9b-f36c90bbcf05",
      attachmentFileId);

    AttachmentFile file = service.findOne(attachmentFileId);

    assertNotNull(file);
  }
}
