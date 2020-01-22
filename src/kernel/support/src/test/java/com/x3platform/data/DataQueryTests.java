package com.x3platform.data;

import com.x3platform.SpringContext;

import com.x3platform.configuration.KernelConfiguration;
import com.x3platform.data.DataQuery;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.x3platform.configuration.KernelConfigurationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

public class DataQueryTests {

  @Test
  public void testCreate() {
    DataQuery query = null;

    query = DataQuery.create("{scene:'test',length:100,'where':[{name:'name',value:'hello'}]}");

    assertNotNull("query is not null.", query);
    assertEquals("query.getLength() == 100.", 100, query.getLength());
    assertEquals("query.getVariables().get(\"scene\") == test.", "test", query.getVariables().get("scene"));

    query = DataQuery.create("<scene>test</scene><length>100</length>");

    assertNotNull("query is not null.", query);
    assertEquals("query.getLength() == 100.", 100, query.getLength());
    assertEquals("query.getVariables().get(\"scene\") == test.", "test", query.getVariables().get("scene"));
  }

  @Test
  public void testGetMap() {
    DataQuery query = null;

    query = DataQuery.create("<scene>test_get_map</scene><length>100</length>");

    assertNotNull("query is not null.", query);

    Map map = query.getMap();

    assertNotNull("map is not null.", map);
    assertTrue("map.get(\"length\") ==== 100.", map.size() > 0);
    assertEquals("map.get(\"length\") == 100.", 100, map.get("length"));
    assertEquals("map.get(\"var_scene\") == test_get_map.", "test_get_map", map.get("var_scene"));
  }
}
