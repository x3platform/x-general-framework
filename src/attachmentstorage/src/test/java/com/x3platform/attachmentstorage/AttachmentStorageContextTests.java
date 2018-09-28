package com.x3platform.attachmentstorage;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AttachmentStorageContextTests {

  @Test
  public void testLoad() {
    AttachmentStorageContext instance = AttachmentStorageContext.getInstance();

    assertNotNull("AttachmentFileService is not null.",
      instance.getAttachmentFileService());
  }
}
