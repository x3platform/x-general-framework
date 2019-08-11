package com.x3platform.connect;

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
public class ConnectContextTests {

  @Test
  public void testLoad() {
    ConnectContext instance = ConnectContext.getInstance();

    assertNotNull("ConnectService is not null.",
      instance.getConnectService());
    assertNotNull("ConnectAuthorizationCodeService is not null.",
      instance.getConnectAuthorizationCodeService());
    assertNotNull("ConnectAccessTokenService is not null.",
      instance.getConnectAccessTokenService());
    assertNotNull("ConnectCallService is not null.",
      instance.getConnectCallService());
  }
}
