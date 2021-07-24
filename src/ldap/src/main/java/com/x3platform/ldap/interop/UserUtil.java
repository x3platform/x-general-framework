package com.x3platform.ldap.interop;

import com.x3platform.InternalLogger;
import com.x3platform.KernelContext;
import com.x3platform.ldap.configuration.LdapConfigurationView;
import com.x3platform.util.StringUtil;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.logging.Level;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 用户辅助工具
 *
 * @author ruanyu
 */
public class UserUtil {

  private DirContext context;

  // LDAP 驱动
  private static final String LDAP_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

  private Logger logger = InternalLogger.getLogger();
  
  // 通过连接 LDAP 服务器对用户进行认证，返回 LDAP 对象
  public DirContext getDirContext() {
    // 模拟用户名
    String username = LdapConfigurationView.getInstance().getUsername();
    // 验证次数
    Hashtable env = new Hashtable();
    env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_FACTORY);
    env.put(Context.PROVIDER_URL, LdapConfigurationView.getInstance().getHost());
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    // cn=属于哪个组织结构名称，ou=某个组织结构名称下等级位置编号
    env.put(Context.SECURITY_PRINCIPAL, StringUtil.format("cn={},{}",
      username, LdapConfigurationView.getInstance().getBase()));
    env.put(Context.SECURITY_CREDENTIALS, LdapConfigurationView.getInstance().getPassword());
    // env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_PRINCIPAL);
    try {
      // 连接 LDAP 进行认证
      context = new InitialDirContext(env);
      // System.out.println("认证成功");
      logger.info("【{}】用户于【{}】登陆 LDAP 成功",
        username, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    } catch (javax.naming.AuthenticationException e) {
      System.out.println("认证失败");
    } catch (NamingException err) {
      logger.info("--------->>【" + username + "】用户验证失败.");
    } catch (Exception e) {
      System.out.println("认证出错:");
      logger.error(e.toString());
      e.printStackTrace();
    }

    return context;
  }
  
  /**
   * 确认密码
   *
   * @param name 帐号名称
   * @param password 密码
   * @return 值: 0 成功 | 1 失败
   */
  public int confirmPassword(String name, String password) {
    Hashtable env = new Hashtable();
    env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_FACTORY);
    env.put(Context.PROVIDER_URL, LdapConfigurationView.getInstance().getHost());
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, StringUtil.format("cn={},{},{}",
      name, LdapConfigurationView.getInstance().getCorporationUserFolderRoot(),
      LdapConfigurationView.getInstance().getBase()));
    env.put(Context.SECURITY_CREDENTIALS, password);
    try {
      DirContext context = null;
      try {
        context = new InitialDirContext(env);
        return 0;
      } catch (AuthenticationException e) {
        logger.error(e.toString());
        return 1;
      } finally {
        if (context != null) {
          context.close();
        }
      }
    } catch (NamingException e) {
      logger.error(e.toString());
    }

    return 1;
  }
  
  public int setPassword(String loginName, String password) {
    return 0;
  }
  
  /**
   * 将输入用户和密码进行加密算法后验证
   */
  @SuppressWarnings(value = "unchecked")
  public boolean verifySHA(String password, String ldapPassword) throws NoSuchAlgorithmException {
    // MessageDigest 提供了消息摘要算法，如 MD5 或 SHA，的功能，这里 LDAP 使用的是SHA-1
    MessageDigest md = MessageDigest.getInstance("SHA-1");

    // 取出加密字符
    if (ldapPassword.startsWith("{SSHA}")) {
      ldapPassword = ldapPassword.substring(6);
    } else if (ldapPassword.startsWith("{SHA}")) {
      ldapPassword = ldapPassword.substring(5);
    }

    // 解码 BASE64
    byte[] ldapPasswordBytes = Base64.decode(ldapPassword);
    byte[] shacode;
    byte[] salt;

    // 前 20 位是 SHA-1 加密段，20 位后是最初加密时的随机明文
    if (ldapPasswordBytes.length <= 20) {
      shacode = ldapPasswordBytes;
      salt = new byte[0];
    } else {
      shacode = new byte[20];
      salt = new byte[ldapPasswordBytes.length - 20];
      System.arraycopy(ldapPasswordBytes, 0, shacode, 0, 20);
      System.arraycopy(ldapPasswordBytes, 20, salt, 0, salt.length);
    }

    // 把用户输入的密码添加到摘要计算信息
    md.update(password.getBytes());
    // 把随机明文添加到摘要计算信息
    md.update(salt);

    // 按 SSHA 把当前用户密码进行计算
    byte[] passwordBytes = md.digest();

    // 返回校验结果
    return MessageDigest.isEqual(shacode, passwordBytes);
  }

  /**
   * 添加用户
   */
  public boolean add(String loginName, String password, String name) {
    boolean success = false;
    try {
      context = getDirContext();
      BasicAttributes attrsbu = new BasicAttributes();
      BasicAttribute objclassSet = new BasicAttribute("objectclass");
      objclassSet.add("person");
      objclassSet.add("top");
      objclassSet.add("organizationalPerson");
      objclassSet.add("inetOrgPerson");
      attrsbu.put(objclassSet);
      attrsbu.put("sn", name);
      attrsbu.put("uid", loginName);
      attrsbu.put("userPassword", password);
      context.createSubcontext("cn=" + name + ",ou=Peoples", attrsbu);
      context.close();
      return true;
    } catch (NamingException ex) {
      try {
        if (context != null) {
          context.close();
        }
      } catch (NamingException namingException) {
        namingException.printStackTrace();
      }
      logger.info("--------->>添加用户失败");
    }
    return false;
  }

  // 修改密码
  public boolean updatePasswordLdap(String account, String password) {
    boolean success = false;
    try {
      context = getDirContext();
      ModificationItem[] modificationItem = new ModificationItem[1];
      modificationItem[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
        new BasicAttribute("userPassword", password));
      context.modifyAttributes("cn=" + account + ",ou=People", modificationItem);
      context.close();
      return true;
    } catch (NamingException ex) {
      try {
        if (context != null) {
          context.close();
        }
      } catch (NamingException namingException) {
        namingException.printStackTrace();
      }
      logger.info("--------->>修改密码失败");
    }
    return success;
  }

  /**
   * 删除用户
   */
  public boolean remove(String account) {
    try {
      context = getDirContext();
      context.destroySubcontext("cn=" + account);
    } catch (Exception ex) {
      try {
        if (context != null) {
          context.close();
        }
      } catch (NamingException namingException) {
        namingException.printStackTrace();
      }
      logger.info("--------->>删除用户失败");
      return false;
    }
    return true;
  }

  /**
   * 关闭 LDAP 服务器连接
   */
  public void closeCtx() {
    try {
      context.close();
    } catch (NamingException ex) {
      logger.info("--------->> 关闭LDAP连接失败");
    }
  }
}
