package com.x3platform.config;

import com.x3platform.apps.filters.ApplicationMethodFilter;
import com.x3platform.tasks.NotificationProvider;
import com.x3platform.tasks.notifications.EmailNotificationProvider;
import com.x3platform.tasks.services.TaskHistoryItemService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@Configurable
public class AppConfig {

  @Bean("com.x3platform.tasks.notificationProviders")
  public List<NotificationProvider> notificationProviders() {
    List<NotificationProvider> list = new ArrayList<NotificationProvider>();
    
    list.add(new EmailNotificationProvider());

    return list;
  }
}
