package com.x3platform.ldap.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.x3platform.ldap.configuration.LdapConfigurationView;
import com.x3platform.ldap.interop.UserUtil;
import com.x3platform.util.StringUtil;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import javax.naming.directory.DirContext;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserUtilTests {

  // 不适合做自动化单元测试

  @Test
  public void testGetPassword() {
    String password = LdapConfigurationView.getInstance().getPassword();
    assertNotNull(password);
  }

  @Ignore
  @Test
  public void testConfirmPassword() {
    UserUtil user = new UserUtil();

    user.confirmPassword("test1", "123456");

    DirContext context = user.getDirContext();

    assertNotNull(context);
  }

  @Ignore
  @Test
  public void testVerifySHA() throws NoSuchAlgorithmException {
    UserUtil user = new UserUtil();

    boolean result = user.verifySHA("1234567", "{SSHA}/RrbvGwwprM8a1n3thPyNn97TJRFZFFyb0x2cWd0cHc=");

    assertTrue(result);
  }
}
