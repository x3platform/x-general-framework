package com.x3platform.data.commons.services;

import static org.junit.Assert.assertNotNull;

import com.x3platform.data.commons.models.DataStorageNode;
import java.util.List;
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
  @Qualifier("com.x3platform.data.commons.services.DataStorageNodeService")
  DataStorageNodeService service;

  @Test
  public void testGetDefault() {
    DataStorageNode result = service.getDefault();

    assertNotNull("result is not null.", result);
  }

  @Test
  public void testFindOne() {
    DataStorageNode result = service.findOne("default");

    assertNotNull("result is not null.", result);
  }

  @Test
  public void testFindAllBySchemaId() {
    List<DataStorageNode> list = service.findAllBySchemaId("00000000-0002-0001-0000-000000000000");

    assertNotNull("result is not null.", list);
  }

  @Test
  public void testFindAllByApplicationId() {
    List<DataStorageNode> list = service.findAllByApplicationId("00000000-0002-0001-0000-000000000000");

    assertNotNull("result is not null.", list);
  }
}
