package com.x3platform.util;

import org.springframework.context.ApplicationContext;

public class ApplicationContextUtil {

	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		ApplicationContextUtil.context = context;
	}
}
