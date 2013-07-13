package com.greejoy.sample.hessian.server.impl;

import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextLoader;

public class MybatisScanner implements BeanFactoryAware,BeanFactoryPostProcessor,ContextLoader{

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("数据库数据库00000000000000");
		DefaultListableBeanFactory bf = (DefaultListableBeanFactory) beanFactory;
		BeanDefinitionBuilder userBeanDefinitionBuilder = BeanDefinitionBuilder  
                .genericBeanDefinition(MapperFactoryBean.class);  
		userBeanDefinitionBuilder.addPropertyValue("mapperInterface", "com.greejoy.rbac.repository.UserRepository");
		System.out.println(bf.containsBean("sqlSessionFactory")+"纳尼");
		userBeanDefinitionBuilder.addPropertyValue("sqlSessionFactory",bf.getBean("sqlSessionFactory"));
        bf.registerBeanDefinition("userRepository",  userBeanDefinitionBuilder.getRawBeanDefinition());
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory bf) throws BeansException {
		System.out.println("数据库数据库");
	}

	

	@Override
	public String[] processLocations(Class<?> clazz, String... locations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationContext loadContext(String... locations) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
