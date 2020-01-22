package com.x3platform.sessions.services;

import static org.junit.Assert.assertTrue;

import com.x3platform.sessions.SessionsContext;
import com.x3platform.sessions.Ticket;
import com.x3platform.util.StringUtil;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketServiceTests {

  @Test
  public void testInsert() {
    TicketService service = SessionsContext.getInstance().getTicketService();

    String ticketId = TEST_ID_PREFIX + StringUtil.toDateFormat(LocalDateTime.now(), "yyyyMMddHHmmss");

    Ticket param = new Ticket();

    param.setTicketId(ticketId);

    param.setTicketValue(GENERAL_ACCOUNT_LOGIN_NAME);

    param.setAccountObject(
      "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\r\n<accountObject><id>fbe67a6e-4c6c-439e-b54b-f62af23d30ba</id><loginName>jiqiliang</loginName><name>吉其亮</name><ip>127.0.0.1</ip></accountObject>");

    param.setAccountObjectType("com.x3platform.membership.models.AccountInfo");

    param.setIP("255.255.255.255");

    param.setValidFrom(LocalDateTime.now());
    param.setValidTo(LocalDateTime.now());
    param.setCreatedDate(LocalDateTime.now());

    service.insert(param);

    Ticket result = service.findByTicketId(param.getTicketId());

    assertTrue(param.getTicketId().equals(result.getTicketId()));

    service.delete(ticketId);
  }
}
