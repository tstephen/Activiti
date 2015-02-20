/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.spring.boot;

import org.activiti.rest.common.application.ContentTypeResolver;
import org.activiti.rest.common.application.DefaultContentTypeResolver;
import org.activiti.rest.security.BasicAuthenticationProvider;
import org.activiti.rest.service.api.RestResponseFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Auto-configuration and starter for the Activiti REST APIs.
 *
 * @author Joram Barrez
 * @author Josh Long
 */
@Configuration
@AutoConfigureAfter(SecurityAutoConfiguration.class)
@ConditionalOnClass(name = {"org.activiti.rest.service.api.RestUrls", "org.springframework.web.servlet.DispatcherServlet"})
public class RestApiAutoConfiguration {

  @Bean
  public RestResponseFactory restResponseFactory() {
    RestResponseFactory restResponseFactory = new RestResponseFactory();
    return restResponseFactory;
  }

  @Bean
  public ContentTypeResolver contentTypeResolver() {
    ContentTypeResolver resolver = new DefaultContentTypeResolver();
    return resolver;
  }
  
  @Bean
  public ObjectMapper objectMapper() {
    // To avoid instantiating and configuring the mapper everywhere
    ObjectMapper mapper = new ObjectMapper();
    return mapper;
  }
  
  @Configuration
  @ComponentScan({"org.activiti.rest.exception", "org.activiti.rest.service.api"}) 
  public static class ComponentScanRestResourcesConfiguration {
  	
  	// The component scan cannot be on the root configuration, it would trigger
  	// always even if the condition is evaluating to false.
  	// Hence, this 'dummy' configuration
  	
  }

  @Configuration
  @ConditionalOnClass(name = {"org.activiti.rest.service.api.RestUrls", "org.springframework.web.servlet.DispatcherServlet"})
  @EnableWebSecurity
  @EnableWebMvcSecurity
  public static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
      return new BasicAuthenticationProvider();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
        .authenticationProvider(authenticationProvider())
        .csrf().disable()
        .authorizeRequests()
          .anyRequest().authenticated()
          .and()
        .httpBasic();
    }
  }

  
}
