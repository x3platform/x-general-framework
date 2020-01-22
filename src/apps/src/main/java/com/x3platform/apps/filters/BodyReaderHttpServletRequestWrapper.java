package com.x3platform.apps.filters;

import com.x3platform.util.HttpContextUtil;
import java.io.InputStream;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 重写HttpServletRequestWrapper
 *
 * @author ruanyu
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

  private final byte[] body;

  public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
    super(request);
    body = HttpContextUtil.getBodyString(request).getBytes(Charset.forName("UTF-8"));
  }

  @Override
  public BufferedReader getReader() throws IOException {
    return new BufferedReader(new InputStreamReader(getInputStream()));
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {

    final ByteArrayInputStream bais = new ByteArrayInputStream(body);

    return new ServletInputStream() {

      @Override
      public int read() {
        return bais.read();
      }

      @Override
      public boolean isFinished() {
        return false;
      }

      @Override
      public boolean isReady() {
        return false;
      }

      @Override
      public void setReadListener(ReadListener readListener) {

      }
    };
  }
}
