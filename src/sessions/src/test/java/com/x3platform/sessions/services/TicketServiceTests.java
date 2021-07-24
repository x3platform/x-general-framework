package com.x3platform.sessions.services;

import static com.x3platform.Constants.TEXT_EMPTY;
import static com.x3platform.tests.TestConstants.GENERAL_ACCOUNT_LOGIN_NAME;
import static com.x3platform.tests.TestConstants.TEST_ID_PREFIX;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.alibaba.fastjson.parser.ParserConfig;
import com.x3platform.Constants;
import com.x3platform.membership.Account;
import com.x3platform.sessions.SessionTicketStorageStrategy;
import com.x3platform.sessions.SessionsContext;
import com.x3platform.sessions.Ticket;
import com.x3platform.tests.TestConstants;
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
  public void testWrite() {
    ParserConfig.getGlobalInstance().addAccept("com.x3platform.");
    
    TicketService service = SessionsContext.getInstance().getTicketService();
    
    SessionTicketStorageStrategy strategy = new SessionTicketStorageStrategy();
    
    String ticketId = TEST_ID_PREFIX + StringUtil.toDateFormat(LocalDateTime.now(), "yyyyMMddHHmmss");
    
//    Ticket param = new Ticket();
//
//    param.setTicketId(ticketId);
//
//    param.setTicketValue(GENERAL_ACCOUNT_LOGIN_NAME);
//
//    param.setAccountObject(
//      "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\r\n<accountObject><id>fbe67a6e-4c6c-439e-b54b-f62af23d30ba</id><loginName>jiqiliang</loginName><name>吉其亮</name><ip>127.0.0.1</ip></accountObject>");
//
//    param.setAccountObjectType("com.x3platform.membership.models.AccountInfo");
//
//    param.setIP("255.255.255.255");
//
//    param.setValidFrom(LocalDateTime.now());
//    param.setValidTo(LocalDateTime.now());
//    param.setCreatedDate(LocalDateTime.now());
//
    Account account = mock(Account.class);
    
    when(account.getId()).thenReturn("fbe67a6e-4c6c-439e-b54b-f62af23d30ba");
    when(account.getLoginName()).thenReturn("tester");
    when(account.getName()).thenReturn("测试者");
    when(account.getIP()).thenReturn("127.0.0.1");
    when(account.getAuthorizationObjectType()).thenReturn("com.x3platform.membership.models.AccountInfo");
  
    service.write(strategy, TEXT_EMPTY, ticketId, account);
    
    Ticket result = service.findByTicketId(ticketId);
    
    assertTrue(ticketId.equals(result.getTicketId()));
    
    service.delete(ticketId);
  }
}
