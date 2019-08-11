package com.x3platform.attachmentstorage.fastdfs;

import com.x3platform.attachmentstorage.configuration.AttachmentStorageConfigurationView;
import com.x3platform.attachmentstorage.fastdfs.FastDFSFile;
import com.x3platform.attachmentstorage.FileMessageObject;
import com.x3platform.attachmentstorage.util.SysUtils;
import com.x3platform.globalization.I18n;
import com.x3platform.util.PathUtil;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Lazy
@Component
public class FastDFSFileManager {
  private static Logger log = LoggerFactory.getLogger(FastDFSFileManager.class);

  //private static final String FAST_DFS_CONF_FILE = FileManager.class.getResource("/").getPath() + "/fdfs_client.conf";

  static {
    // 获取默认的配置文件
    // ClassPathResource cpr = new ClassPathResource(CLIENT_CONFIG_FILE);
    ClassPathResource cpr = new ClassPathResource(AttachmentStorageConfigurationView.getInstance().getFastDFSClientConfigFile());
    try {
      String path = cpr.getFile().getAbsolutePath();
      log.info("fast dfs config file:" + path);
      if (PathUtil.getExtension(path).equals(".properties")) {
        ClientGlobal.initByProperties(path);
      } else {
        ClientGlobal.init(path);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private final long serialVersionUID = 1L;

  private TrackerClient trackerClient = new TrackerClient();
  private TrackerServer trackerServer;
  private StorageServer storageServer;
  private StorageClient storageClient;

  /**
   * 初始化连接
   */
  private void initConnectionn() throws IOException {
    trackerServer = trackerClient.getConnection();
    storageClient = new StorageClient(trackerServer, storageServer);
  }

  public String upload(byte[] file, String extName) {
    NameValuePair[] meta_list = new NameValuePair[1];
    meta_list[0] = new NameValuePair("author", AttachmentStorageConfigurationView.getInstance().getDefaultFileAuthor());
    String[] uploadResults = null;
    try {
      initConnectionn();
      uploadResults = storageClient.upload_file(file, extName, meta_list);
    } catch (IOException e) {
      log.error("IO Exception when uploadind the file(byte[]): ", e);
    } catch (Exception e) {
      log.error("Non IO Exception when uploadind the file(byte[]): ", e);
    }
    if (uploadResults == null) {
      log.error("upload file fail, error code: " + storageClient.getErrorCode());
      return null;
    }
    String groupName = uploadResults[0];
    String remoteFileName = uploadResults[1];
    String fileAbsolutePath = groupName + AttachmentStorageConfigurationView.getInstance().getPathSeparator() + remoteFileName;
    if (log.isDebugEnabled()) {
      log.debug("upload file successfully! remoteFileName:" + fileAbsolutePath);
    }
    return fileAbsolutePath;
  }

  /**
   * 上传文件
   */
  public String[] upload(FastDFSFile file) {
    log.debug("file name: " + file.getName() + " file length: " + file.getContent().length);
    NameValuePair[] meta_list = new NameValuePair[1];
    meta_list[0] = new NameValuePair("author", AttachmentStorageConfigurationView.getInstance().getDefaultFileAuthor());
    String[] results = null;
    try {
      initConnectionn();
      results = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
    } catch (IOException e) {
      log.error("IO Exception when uploadind the file: " + file.getName(), e);
    } catch (Exception e) {
      log.error("Non IO Exception when uploadind the file: " + file.getName(), e);
    }
    if (results == null) {
      log.error("upload file fail, error code: " + storageClient.getErrorCode());
      return null;
    }
    return results;

    // String groupName = results[0];
    // String remoteFileName = results[1];
        /*String fileAbsolutePath = PROTOCOL + trackerServer.getInetSocketAddress().getHostName() + PORT_SEPARATOR
				+ TRACKER_NGNIX_PORT + SEPARATOR + groupName + SEPARATOR + remoteFileName;*/
    // String fileAbsolutePath = groupName + AttachmentStorageConfigurationView.getInstance().getPathSeparator() + remoteFileName;
    // log.debug("upload file successfully!!! remoteFileName:" + fileAbsolutePath);
    // return fileAbsolutePath;
  }

  public String upload(String localFileName) {
    if (log.isDebugEnabled())
      log.debug("File Name: " + localFileName);
    NameValuePair[] meta_list = new NameValuePair[1];
    meta_list[0] = new NameValuePair("author", AttachmentStorageConfigurationView.getInstance().getDefaultFileAuthor());
    String[] uploadResults = null;
    try {
      initConnectionn();
      int index = localFileName.lastIndexOf(".");
      String extName = "";
      if (index >= 0) extName = localFileName.substring(index + 1);
      uploadResults = storageClient.upload_file(localFileName, extName, meta_list);
    } catch (IOException e) {
      log.error("IO Exception when uploadind the file: " + localFileName, e);
    } catch (Exception e) {
      log.error("Non IO Exception when uploadind the file: " + localFileName, e);
    }
    if (uploadResults == null) {
      log.error("upload file fail, error code: " + storageClient.getErrorCode());
      return null;
    }
    String groupName = uploadResults[0];
    String remoteFileName = uploadResults[1];
		/*String fileAbsolutePath = PROTOCOL + trackerServer.getInetSocketAddress().getHostName() + PORT_SEPARATOR
				+ TRACKER_NGNIX_PORT + SEPARATOR + groupName + SEPARATOR + remoteFileName;*/
    String fileAbsolutePath = groupName + AttachmentStorageConfigurationView.getInstance().getPathSeparator() + remoteFileName;
    if (log.isDebugEnabled())
      log.debug("upload file successfully!!!   remoteFileName:" + fileAbsolutePath);
    return fileAbsolutePath;
  }

  /**
   * 获取文件
   *
   * @param groupName      文件组
   * @param remoteFileName 远程文件
   */
  public FileInfo getFile(String groupName, String remoteFileName) {
    try {
      initConnectionn();
      return storageClient.get_file_info(groupName, remoteFileName);
    } catch (IOException e) {
      log.error("IO Exception: Get File from Fast DFS failed", e);
    } catch (Exception e) {
      log.error("Non IO Exception: Get File from Fast DFS failed", e);
    }
    return null;
  }

  /**
   * @param groupName      文件组
   * @param remoteFileName 远程文件
   *                       文件下载
   */
  public FileMessageObject downloadAsBytes(String groupName, String remoteFileName) {
    FileMessageObject resultMap = new FileMessageObject();
    try {
      initConnectionn();
      byte[] result = storageClient.download_file(groupName, remoteFileName);
      resultMap.setMessage(I18n.getStrings().text("msg_file_download_group_exit"));
      resultMap.setData(result);
      return resultMap;
    } catch (IOException e) {
      resultMap.setMessage(I18n.getStrings().text("msg_file_download_io_exception"));
      log.error(I18n.getStrings().text("msg_file_download_io_exception"), e);
    } catch (Exception e) {
      resultMap.setMessage(I18n.getStrings().text("msg_file_download_non_exception"));
      log.error(I18n.getStrings().text("msg_file_download_non_exception"), e);
    }
    resultMap.setCode("-1");
    return resultMap;
  }

  public FileMessageObject download(String groupName, String remoteFileName, String savePath) {
    FileMessageObject resultMap = new FileMessageObject();
    try {
      initConnectionn();
      String fName = SysUtils.nullString(remoteFileName);
      int index = fName.lastIndexOf(".");
      String extName = "";
      if (index >= 0 && index < fName.length() - 1) {
        extName = "." + fName.substring(index + 1);
      }
      fName = savePath + UUID.randomUUID().toString() + extName;
      int result = -1, i = 3;
      while (result != 0 && i-- > 0) {
        result = storageClient.download_file(groupName, remoteFileName, fName);
      }
      if (result == 0) {
        resultMap.setCode("0");
        resultMap.setMessage(I18n.getStrings().text("msg_file_download_success"));
        resultMap.setData(fName);
        return resultMap;
      } else {
        resultMap.setCode("-1");
        resultMap.setMessage(I18n.getStrings().text("msg_file_download_failed"));
        return resultMap;
      }
    } catch (IOException e) {
      resultMap.setMessage(I18n.getStrings().text("msg_file_download_io_exception"));
      log.error(I18n.getStrings().text("msg_file_download_io_exception"), e);
    } catch (Exception e) {
      resultMap.setMessage(I18n.getStrings().text("msg_file_download_non_exception"));
      log.error(I18n.getStrings().text("msg_file_download_non_exception"), e);
    }
    resultMap.setCode("-1");

    return resultMap;
  }

  public void deleteFile(String groupName, String remoteFileName) throws Exception {
    initConnectionn();
    storageClient.delete_file(groupName, remoteFileName);
  }

  public StorageServer[] getStoreStorages(String groupName) throws IOException {
    initConnectionn();
    return trackerClient.getStoreStorages(trackerServer, groupName);
  }

  public ServerInfo[] getFetchStorages(String groupName, String remoteFileName) throws IOException {
    initConnectionn();
    return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
  }
}
