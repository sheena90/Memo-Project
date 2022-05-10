package com.sheena.memo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sheena.memo.common.FileManagerService;
import com.sheena.memo.common.PermissionInterceptor;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer{
	
	@Autowired
	private PermissionInterceptor interceptor;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		// /images/~~~
		
		registry.addResourceHandler("/images/**")
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH);
	}
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
		.addPathPatterns("/**") // 어떤 경로(path)가 인터셉터를 거쳐서 수행 될지 설정
		.excludePathPatterns("/static/**", "/images/**", "/user/sign_out"); // 처리하지 않을 path
	}
}
