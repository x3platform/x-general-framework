/*
package com.x3platform.data.storages.services;

import com.alibaba.fastjson.JSON;
import com.x3platform.data.storages.DataStorageContext;
import com.x3platform.data.storages.models.DataStorageSchema;
import com.x3platform.tests.TestConstants;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataStorageSchemaTests {

  @Autowired
  @Qualifier("com.x3platform.data.storages.services.DataStorageSchemaService")
  DataStorageSchemaService service;

  @Test
  public void testFindOne() {
    DataStorageSchema result = service.findOne("default");

    assertNotNull("result is not null.", result);
  }

  @Test
  public void testFindOneByApplicationId() {
    DataStorageSchema result = service.findOneByApplicationId(TestConstants.APPLICATION_ID);

    assertNotNull("result is not null.", result);
  }

  @Test
  public void testFindOneByApplicationName() {
    DataStorageSchema result = service.findOneByApplicationName(TestConstants.APPLICATION_NAME);

    assertNotNull("result is not null.", result);
  }
}
*/
