package com.x3platform.categoryindexes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

public class TextCategoryIndexTests {
  @Test
  public void testLoad() {
    CategoryIndexWriter writer = new CategoryIndexWriter("选择类别");

    writer.read("一级类别01/二级类别01/三级类别01");
    writer.read("一级类别01/二级类别02/三级类别02");
    writer.read("一级类别01\\二级类别03\\三级类别03");
    writer.read("一级类别02\\二级类别01\\三级类别01");
    writer.read("一级类别02\\二级类别02\\三级类别02");
    writer.read("一级类别02\\二级类别03\\三级类别03");
    //writer.read("11\\113\\123");
    //writer.read("11\\11\\11");
    //writer.read("11\\1223\\122");
    //writer.read("122\\12234");
    writer.read("22\\12\\2345");

    CategoryIndex index = writer.write();

    assertNotNull("index is not null.", index);
  }


  @Test
  public void testInit() {
    CategoryIndex index = new TextCategoryIndex("一级类别01\\二级类别01-01\\三级类别01-01-01");

    assertNotNull("index is not null.", index);
  }
}
