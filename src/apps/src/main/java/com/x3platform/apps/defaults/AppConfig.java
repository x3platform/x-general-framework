package com.x3platform.apps.defaults;

import com.x3platform.KernelContext;
import com.x3platform.apps.services.IApplicationService;
import com.x3platform.apps.services.imp.ApplicationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("com.x3platform.apps.defaults.AppConfig")
public class AppConfig {

    @Bean("com.x3platform.apps.services.IApplicationService")
    public IApplicationService iApplicationService() {
        return new ApplicationService();
    }
}
