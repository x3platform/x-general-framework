package com.x3platform.categoryIndexes;

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

    writer.Read("一级类别01/二级类别01/三级类别01");
    writer.Read("一级类别01/二级类别02/三级类别02");
    writer.Read("一级类别01\\二级类别03\\三级类别03");
    writer.Read("一级类别02\\二级类别01\\三级类别01");
    writer.Read("一级类别02\\二级类别02\\三级类别02");
    writer.Read("一级类别02\\二级类别03\\三级类别03");
    //writer.Read("11\\113\\123");
    //writer.Read("11\\11\\11");
    //writer.Read("11\\1223\\122");
    //writer.Read("122\\12234");
    writer.Read("22\\12\\2345");

    ICategoryIndex index = writer.Write();

    assertNotNull("index is not null.", index);
  }


  @Test
  public void testInit() {
    ICategoryIndex index = new TextCategoryIndex("一级类别01\\二级类别01-01\\三级类别01-01-01");

    assertNotNull("index is not null.", index);
  }
}
