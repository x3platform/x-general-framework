package com.x3platform.data.storages.services;

import static org.junit.Assert.assertNotNull;

import com.x3platform.data.storages.models.DataStorageNode;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataStorageNodeTests {

  @Autowired
  @Qualifier("com.x3platform.data.storages.services.DataStorageNodeService")
  DataStorageNodeService service;

  @Test
  public void testGetDefault() {
    DataStorageNode result = service.getDefault();

    assertNotNull("result is not null.", result);
  }

  @Ignore
  @Test
  public void testFindOne() {
    DataStorageNode result = service.findOne("default");

    assertNotNull("result is not null.", result);
  }

}
