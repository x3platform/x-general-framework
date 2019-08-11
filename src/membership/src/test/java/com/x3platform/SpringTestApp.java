package com.x3platform.membership;

import com.x3platform.data.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.web.WebAppConfiguration;

// @SpringBootApplication(excludeName = {"com.x3platform.membership.controllers.OrganizationUnitController"})
@SpringBootApplication()
@ComponentScan({"com.x3platform.configuration", "com.x3platform"})
@MapperScan({"com.x3platform"})
@Import(DynamicDataSourceRegister.class)
public class SpringTestApp {

}
