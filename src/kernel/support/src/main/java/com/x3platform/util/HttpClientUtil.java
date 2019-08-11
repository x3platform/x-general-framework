package com.x3platform.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * HTTP 请求辅助函数
 */
public class HttpClientUtil {

  private RequestConfig requestConfig = RequestConfig.custom()
    .setSocketTimeout(15000)
    .setConnectTimeout(15000)
    .setConnectionRequestTimeout(15000).build();

  private static HttpClientUtil instance = null;

  public HttpClientUtil() {
  }

  public static HttpClientUtil getInstance() {
    if (instance == null) {
      instance = new HttpClientUtil();
    }

    return instance;
  }

  /**
   * 发送 post请求
   *
   * @param httpUrl 地址
   * @return 返回响应信息
   */
  public String sendHttpPost(String httpUrl) {
    // 创建httpPost
    HttpPost httpPost = new HttpPost(httpUrl);

    return sendHttpPost(httpPost);
  }

  /**
   * 发送 post请求
   *
   * @param httpUrl 地址
   * @param params  参数(格式:key1=value1&amp;key2=value2)
   * @return 返回响应信息
   */
  public String sendHttpPost(String httpUrl, String params) {
    // 创建httpPost
    HttpPost httpPost = new HttpPost(httpUrl);
    try {
      // 设置参数
      StringEntity stringEntity = new StringEntity(params, "UTF-8");
      stringEntity.setContentType("application/x-www-form-urlencoded");
      httpPost.setEntity(stringEntity);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sendHttpPost(httpPost);
  }

  /**
   * 发送 post请求
   *
   * @param httpUrl 地址
   * @param maps    参数
   * @return 返回响应信息
   */
  public String sendHttpPost(String httpUrl, Map<String, String> maps) {
    HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
    // 创建参数队列
    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    for (String key : maps.keySet()) {
      nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
    }
    try {
      httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return sendHttpPost(httpPost);
  }

  /**
   * 发送 post请求（带文件）
   *
   * @param url       地址
   * @param maps      参数
   * @param fileLists 附件
   * @return 返回响应信息
   */
  public String sendHttpPost(String url, Map<String, String> maps, List<File> fileLists) {
    HttpPost httpPost = new HttpPost(url);// 创建httpPost
    MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
    if (maps != null) {
      for (String key : maps.keySet()) {
        meBuilder.addPart(key, new StringBody(maps.get(key), ContentType.TEXT_PLAIN));
      }
    }
    if (fileLists != null) {
      for (File file : fileLists) {
        FileBody fileBody = new FileBody(file);
        meBuilder.addPart("files", fileBody);
      }
    }
    HttpEntity reqEntity = meBuilder.build();
    httpPost.setEntity(reqEntity);
    return sendHttpPost(httpPost);
  }

  /**
   * 发送  请求
   *
   * @param httpPost
   * @return 返回响应信息
   */
  private String sendHttpPost(HttpPost httpPost) {
    CloseableHttpClient httpClient = null;
    CloseableHttpResponse response = null;
    HttpEntity entity = null;
    String responseContent = null;
    try {
      // 创建默认的httpClient实例.
      httpClient = HttpClients.createDefault();
      httpPost.setConfig(requestConfig);
      // 执行请求
      response = httpClient.execute(httpPost);
      entity = response.getEntity();
      responseContent = EntityUtils.toString(entity, "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        // 关闭连接,释放资源
        if (response != null) {
          response.close();
        }
        if (httpClient != null) {
          httpClient.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return responseContent;
  }

  public String sendJsonHttpPost(String url, String json) {

    CloseableHttpClient httpclient = HttpClients.createDefault();
    String responseInfo = null;
    try {
      HttpPost httpPost = new HttpPost(url);
      httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
      ContentType contentType = ContentType.create("application/json", CharsetUtils.get("UTF-8"));
      httpPost.setEntity(new StringEntity(json, contentType));
      CloseableHttpResponse response = httpclient.execute(httpPost);
      HttpEntity entity = response.getEntity();
      int status = response.getStatusLine().getStatusCode();
      if (status >= 200 && status < 300) {
        if (null != entity) {
          responseInfo = EntityUtils.toString(entity);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        httpclient.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return responseInfo;
  }

  /**
   * 发送 GET 请求
   *
   * @param url URL 地址
   * @return 返回响应信息
   */
  public String sendHttpGet(String url) {
    // 创建 GET 请求
    HttpGet httpGet = new HttpGet(url);

    return sendHttpGet(httpGet);
  }

  /**
   * 发送 GET 请求 https
   *
   * @param url URL 地址
   * @return 返回响应信息
   */
  public String sendHttpsGet(String url) {
    // 创建 GET 请求
    HttpGet httpGet = new HttpGet(url);

    return sendHttpsGet(httpGet);
  }

  /**
   * 发送Get请求
   *
   * @param httpGet
   * @return 返回响应信息
   */
  private String sendHttpGet(HttpGet httpGet) {
    CloseableHttpClient httpClient = null;
    CloseableHttpResponse response = null;
    HttpEntity entity = null;
    String responseContent = null;
    try {
      // 创建默认的httpClient实例.
      httpClient = HttpClients.createDefault();
      httpGet.setConfig(requestConfig);
      // 执行请求
      response = httpClient.execute(httpGet);
      entity = response.getEntity();
      responseContent = EntityUtils.toString(entity, "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        // 关闭连接,释放资源
        if (response != null) {
          response.close();
        }
        if (httpClient != null) {
          httpClient.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return responseContent;
  }

  /**
   * 发送Get请求Https
   *
   * @param httpGet
   * @return 返回响应信息
   */
  private String sendHttpsGet(HttpGet httpGet) {
    CloseableHttpClient httpClient = null;
    CloseableHttpResponse response = null;
    HttpEntity entity = null;
    String responseContent = null;
    try {
      // 创建默认的httpClient实例.
      PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader
        .load(new URL(httpGet.getURI().toString()));
      DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
      httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();
      httpGet.setConfig(requestConfig);
      // 执行请求
      response = httpClient.execute(httpGet);
      entity = response.getEntity();
      responseContent = EntityUtils.toString(entity, "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        // 关闭连接,释放资源
        if (response != null) {
          response.close();
        }
        if (httpClient != null) {
          httpClient.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return responseContent;
  }

  public static void main(String[] args) {
    HttpClientUtil http = new HttpClientUtil();
    Map map = new HashMap();
    map.put("messageid", "test00201612210002");
    map.put("clientid", "test00");
    map.put("index_id", "买方会员");
    map.put("threshold", 0.9);
    List<String> data = new ArrayList<String>();
    data.add("wo xiang cha xun jin tian de yao pin jia ge lie biao");
    map.put("data", data);

    String json = JSON.toJSONString(map);

    String reply = http.sendJsonHttpPost("http://11.11.40.63:7777/algor/simclassify", json);
    System.out.println("reply->" + reply);

    String jsonStr = "{'买方会员->合同管理->器械合同->器械合同列表':0.707,'买方会员->合同管理->器械合同->维护器械合同目录':0.707,'买方会员->合同管理->药品合同->维护药品合同目录':0.707}";

    LinkedHashMap<String, String> jsonMap = JSON.parseObject(jsonStr,
      new TypeReference<LinkedHashMap<String, String>>() {
      });
    for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
      System.out.println(entry.getKey() + ":" + entry.getValue());
    }
  }
}
